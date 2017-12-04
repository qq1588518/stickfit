package io.github.xinyangpan.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import io.github.xinyangpan.core.StatusEnum;
import io.github.xinyangpan.core.standard.JogAmountOrMixStandard;
import io.github.xinyangpan.core.standard.Standard;
import io.github.xinyangpan.persistent.dao.CustomerDao;
import io.github.xinyangpan.persistent.dao.CustomerStatusDao;
import io.github.xinyangpan.persistent.dao.ExerciseDao;
import io.github.xinyangpan.persistent.dao.ExerciseTypeDao;
import io.github.xinyangpan.persistent.dao.MonthStandardDao;
import io.github.xinyangpan.persistent.po.CustomerPo;
import io.github.xinyangpan.persistent.po.CustomerStatusPo;
import io.github.xinyangpan.persistent.po.ExercisePo;
import io.github.xinyangpan.persistent.po.ExerciseTypePo;
import io.github.xinyangpan.persistent.po.MonthStandard;
import io.github.xinyangpan.persistent.po.type.YearMonth;
import io.github.xinyangpan.vo.ExerciseVo;
import io.github.xinyangpan.vo.MonthSummary;
import io.github.xinyangpan.vo.Rank;
import io.github.xinyangpan.vo.RankEntry;

@Service
@Transactional
public class ExerciseService {
	private static final JogAmountOrMixStandard DEFAULT_STANDARD = new JogAmountOrMixStandard(new BigDecimal("180"), new BigDecimal("110"), 15, 9);
	@Autowired
	private ExerciseDao exerciseDao;
	@Autowired
	private ExerciseTypeDao exerciseTypeDao;
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private CustomerStatusDao customerStatusDao;
	@Autowired
	private MonthStandardDao monthStandardDao;
	

	public Rank rank(long groupId, YearMonth yearMonth) {
		List<ExercisePo> exercisePos = exerciseDao.findByGroupIdAndMonth(groupId, yearMonth);
		MonthStandard monthStandard = monthStandardDao.findByGroupIdAndMonth(groupId, yearMonth);
		Standard standard = getStandard(monthStandard);
		Map<Long, CustomerPo> id2CustomerPo = id2CustomerPo(groupId);
		Map<Long, CustomerStatusPo> id2CustomerStatusPo = id2CustomerStatusPo(groupId, yearMonth);
		String pioneer = getPioneer(monthStandard, id2CustomerPo);
		// rankEntries
		Map<Long, RankEntry> customerId2RankItem = customerId2RankItem(exercisePos, id2CustomerPo, id2CustomerStatusPo, standard);
		List<RankEntry> rankEntries = Lists.newArrayList(customerId2RankItem.values());
		Comparator<RankEntry> comparator = Comparator.comparing(RankEntry::getJogAmount, Comparator.reverseOrder()).thenComparing(RankEntry::getCount, Comparator.reverseOrder());
		Collections.sort(rankEntries, comparator);
		// 
		Rank rank = new Rank();
		rank.setYearMonth(yearMonth);
		rank.setRankEntries(rankEntries);
		rank.setPioneer(pioneer);
		return rank;
	}

	private String getPioneer(MonthStandard monthStandard, Map<Long, CustomerPo> id2CustomerPo) {
		if (monthStandard == null) {
			return null;
		}
		Long pioneerId = monthStandard.getPioneerId();
		if (pioneerId == null) {
			return null;
		}
		return id2CustomerPo.get(pioneerId).getUsername();
	}

	private Map<Long, RankEntry> customerId2RankItem(List<ExercisePo> exercisePos, Map<Long, CustomerPo> id2CustomerPo, Map<Long, CustomerStatusPo> id2CustomerStatusPo, Standard standard) {
		//
		Map<Long, RankEntry> customerId2RankItem = Maps.newHashMap(Maps.transformValues(id2CustomerPo, this::convert));
		for (ExercisePo exercisePo : exercisePos) {
			long customerId = exercisePo.getCustomerId();
			RankEntry rankEntry = customerId2RankItem.get(customerId);
			rankEntry.setCount(rankEntry.getCount() + 1);
			if (exercisePo.getTypeId() == 1) {
				rankEntry.setJogAmount(rankEntry.getJogAmount().add(exercisePo.getAmount()));
				rankEntry.setJogCount(rankEntry.getJogCount() + 1);
			}
			if (standard.eval(rankEntry)) {
				rankEntry.setTag("达标");
			}
			if (isLeave(id2CustomerStatusPo, customerId)) {
				rankEntry.setTag("请假");
			}
		}
		return customerId2RankItem;
	}

	private boolean isLeave(Map<Long, CustomerStatusPo> id2CustomerStatusPo, long customerId) {
		CustomerStatusPo customerStatusPo = id2CustomerStatusPo.get(customerId);
		if (customerStatusPo == null) {
			return false;
		}
		return customerStatusPo.getStatusEnum() == StatusEnum.LEAVE;
	}

	private RankEntry convert(CustomerPo customerPo) {
		RankEntry rankEntry = new RankEntry();
		rankEntry.setCustomerId(customerPo.getId());
		rankEntry.setUsername(customerPo.getUsername());
		rankEntry.setCount(0);
		rankEntry.setJogAmount(BigDecimal.ZERO);
		rankEntry.setJogCount(0);
		rankEntry.setTag("");
		return rankEntry;
	}

	private Standard getStandard(MonthStandard monthStandard) {
		if (monthStandard == null) {
			return DEFAULT_STANDARD;
		} else {
			return monthStandard.getStandard();
		}
	}

	private Map<Long, CustomerPo> id2CustomerPo(long groupId) {
		List<CustomerPo> customerPos = customerDao.findByGroupIdAndUsernameIsNotNull(groupId);
		return Maps.uniqueIndex(customerPos, CustomerPo::getId);
	}

	private Map<Long, CustomerStatusPo> id2CustomerStatusPo(long groupId, YearMonth yearMonth) {
		List<CustomerStatusPo> customerStatusPos = customerStatusDao.findByGroupIdAndMonth(groupId, yearMonth);
		return Maps.uniqueIndex(customerStatusPos, CustomerStatusPo::getCustomerId);
	}

	public void deleteExercisesByIds(List<Long> ids) {
		for (Long id : ids) {
			exerciseDao.delete(id);
		}
	}

	public MonthSummary monthHistory(long customerId, YearMonth yearMonth) {
		List<ExercisePo> exercisePos = exerciseDao.findByCustomerIdAndMonthOrderByTimeAsc(customerId, yearMonth);
		List<ExerciseVo> exerciseVos = exercisePos.stream().map(this::transform).collect(Collectors.toList());
		// 
		MonthSummary history = new MonthSummary();
		history.setYearMonth(yearMonth);
		history.setExerciseVos(exerciseVos);
		history.setSummary(this.generateSummary(exercisePos));
		return history;
	}

	@SuppressWarnings("deprecation")
	private ExerciseVo transform(ExercisePo exercisePo) {
		ExerciseTypePo exerciseTypePo = exerciseTypeDao.findOne(exercisePo.getTypeId());
		Date date = exercisePo.getTime();
		String description = exerciseTypePo.getDescription();
		BigDecimal amount = exercisePo.getAmount();
		String unit = exerciseTypePo.getUnit();
		// 
		ExerciseVo exerciseVo = new ExerciseVo();
		exerciseVo.setExercisePo(exercisePo);
		exerciseVo.setDescription(String.format("%s日%s%s%s", date.getDate(), description, amount, unit));
		if (exercisePo.getTypeId() == 1) {
			if (amount.compareTo(new BigDecimal("100")) >= 0) {
				exerciseVo.setTag("超马");
			} else if (amount.compareTo(new BigDecimal("42")) >= 0) {
				exerciseVo.setTag("全马");
			} else if (amount.compareTo(new BigDecimal("21")) >= 0) {
				exerciseVo.setTag("半马");
			} else {
				exerciseVo.setTag("");
			}
		} else {
			exerciseVo.setTag("");
		}
		return exerciseVo;
	}

	private String generateSummary(List<ExercisePo> exercisePos) {
		String summary = String.format("打卡%s次.", exercisePos.size());
		if (exercisePos.isEmpty()) {
			return summary;
		}
		Multimap<Long, ExercisePo> index = Multimaps.index(exercisePos, exercisePo -> exercisePo.getTypeId());
		Map<Long, BigDecimal> treeMap = new TreeMap<>(Maps.transformValues(index.asMap(), pos -> sumAllAmount(pos)));
		// 
		List<String> summaryByType = treeMap.entrySet().stream().map(e -> {
			ExerciseTypePo exerciseTypePo = exerciseTypeDao.findOne(e.getKey());
			String amount = e.getValue().stripTrailingZeros().toPlainString();
			return String.format("%s%s%s", exerciseTypePo.getDescription(), amount, exerciseTypePo.getUnit());
		}).collect(Collectors.toList());
		return String.format("%s 一共%s.", summary, Joiner.on(", ").join(summaryByType).toString());
	}

	private BigDecimal sumAllAmount(Collection<ExercisePo> pos) {
		return pos.stream().map(po -> po.getAmount()).reduce((a, b) -> a.add(b)).get();
	}

}
