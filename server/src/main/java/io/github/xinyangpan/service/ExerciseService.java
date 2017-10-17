package io.github.xinyangpan.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

import io.github.xinyangpan.persistent.dao.CustomerDao;
import io.github.xinyangpan.persistent.dao.ExerciseDao;
import io.github.xinyangpan.persistent.dao.ExerciseTypeDao;
import io.github.xinyangpan.persistent.po.CustomerPo;
import io.github.xinyangpan.persistent.po.ExercisePo;
import io.github.xinyangpan.persistent.po.ExerciseTypePo;
import io.github.xinyangpan.persistent.po.type.YearMonth;
import io.github.xinyangpan.vo.ExerciseVo;
import io.github.xinyangpan.vo.MonthSummary;
import io.github.xinyangpan.vo.Rank;
import io.github.xinyangpan.vo.RankEntry;

@Service
@Transactional
public class ExerciseService {
	@Autowired
	private ExerciseDao exerciseDao;
	@Autowired
	private ExerciseTypeDao exerciseTypeDao;
	@Autowired
	private CustomerDao customerDao;

	public Rank rank(long groupId, YearMonth yearMonth) {
		List<ExercisePo> exercisePos = exerciseDao.findByGroupIdAndMonth(groupId, yearMonth);
		// 
		Map<Long, RankEntry> customerId2RankItem = customerId2RankItem(exercisePos);
		// 
		List<RankEntry> rankEntries = Lists.newArrayList(customerId2RankItem.values());
		Comparator<RankEntry> comparator = Comparator
			.comparing(RankEntry::getJogAmount, Comparator.reverseOrder())
			.thenComparing(RankEntry::getCount, Comparator.reverseOrder())
			.thenComparingLong(RankEntry::getLastId);
		Collections.sort(rankEntries, comparator);
		// 
		Rank rank = new Rank();
		rank.setRankEntries(rankEntries);
		rank.setYearMonth(yearMonth);
		return rank;
	}

	private Map<Long, RankEntry> customerId2RankItem(List<ExercisePo> exercisePos) {
		//
		Map<Long, CustomerPo> id2CustomerPo = id2CustomerPo(exercisePos);
		//
		Map<Long, RankEntry> customerId2RankItem = Maps.newHashMap();
		for (ExercisePo exercisePo : exercisePos) {
			long customerId = exercisePo.getCustomerId();
			RankEntry rankEntry = customerId2RankItem.get(customerId);
			if (rankEntry == null) {
				String username = id2CustomerPo.get(exercisePo.getCustomerId()).getUsername();
				rankEntry = new RankEntry(customerId, username, 1, exercisePo.getId());
				customerId2RankItem.put(customerId, rankEntry);
			} else {
				rankEntry.setCount(rankEntry.getCount() + 1);
				rankEntry.setLastId(Math.max(exercisePo.getId(), rankEntry.getLastId()));
			}
			if (exercisePo.getTypeId() == 1) {
				rankEntry.setJogAmount(rankEntry.getJogAmount().add(exercisePo.getAmount()));
			}
		}
		return customerId2RankItem;
	}

	private Map<Long, CustomerPo> id2CustomerPo(List<ExercisePo> exercisePos) {
		Set<Long> customerIds = exercisePos.stream().map(ExercisePo::getCustomerId).collect(Collectors.toSet());
		List<CustomerPo> customerPos = customerDao.findByIdIn(customerIds);
		return Maps.uniqueIndex(customerPos, CustomerPo::getId);
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
		exerciseVo.setDescription(String.format("%s日  %s%s%s", date.getDate(), description, amount, unit));
		if (exercisePo.getTypeId() == 1) {
			if (amount.compareTo(new BigDecimal("42"))>=0) {
				exerciseVo.setTag("全马");
			} else if (amount.compareTo(new BigDecimal("21"))>=0) {
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
