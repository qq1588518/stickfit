package io.github.xinyangpan.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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

import io.github.xinyangpan.core.CoreUtils;
import io.github.xinyangpan.persistent.dao.CustomerDao;
import io.github.xinyangpan.persistent.dao.ExerciseDao;
import io.github.xinyangpan.persistent.dao.ExerciseTypeDao;
import io.github.xinyangpan.persistent.po.CustomerPo;
import io.github.xinyangpan.persistent.po.ExercisePo;
import io.github.xinyangpan.persistent.po.ExerciseTypePo;
import io.github.xinyangpan.persistent.vo.RankItem;
import io.github.xinyangpan.vo.CurrentMonthHistory;

@Service
@Transactional
public class ExerciseService {
	@Autowired
	private ExerciseDao exerciseDao;
	@Autowired
	private ExerciseTypeDao exerciseTypeDao;
	@Autowired
	private CustomerDao customerDao;

	public List<RankItem> rank(int month) {
		List<ExercisePo> exercisePos = exerciseDao.findByMonth(month);
		// 
		Map<Long, RankItem> customerId2RankItem = customerId2RankItem(exercisePos);
		// 
		List<RankItem> rank = Lists.newArrayList(customerId2RankItem.values());
		Comparator<RankItem> comparator = Comparator
			.comparing(RankItem::getCount, Comparator.reverseOrder())
			.thenComparing(RankItem::getJogAmount, Comparator.reverseOrder())
			.thenComparingLong(RankItem::getLastId);
		Collections.sort(rank, comparator);
		return rank;
	}

	private Map<Long, RankItem> customerId2RankItem(List<ExercisePo> exercisePos) {
		//
		Map<Long, CustomerPo> id2CustomerPo = id2CustomerPo(exercisePos);
		//
		Map<Long, RankItem> customerId2RankItem = Maps.newHashMap();
		for (ExercisePo exercisePo : exercisePos) {
			long customerId = exercisePo.getCustomerId();
			RankItem rankItem = customerId2RankItem.get(customerId);
			if (rankItem == null) {
				String username = id2CustomerPo.get(exercisePo.getCustomerId()).getUsername();
				rankItem = new RankItem(customerId, username, 1, exercisePo.getId());
				customerId2RankItem.put(customerId, rankItem);
			} else {
				rankItem.setCount(rankItem.getCount() + 1);
				rankItem.setLastId(Math.max(exercisePo.getId(), rankItem.getLastId()));
			}
			if (exercisePo.getTypeId() == 1) {
				rankItem.setJogAmount(rankItem.getJogAmount().add(exercisePo.getAmount()));
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

	public CurrentMonthHistory currentMonthHistory(long customerId) {
		List<ExercisePo> exercisePos = exerciseDao.findByCustomerIdAndMonthOrderByTimeAsc(customerId, CoreUtils.getMonth());
		// 
		CurrentMonthHistory history = new CurrentMonthHistory();
		history.setExercisePos(exercisePos);
		history.setSummary(this.generateSummary(exercisePos));
		return history;
	}

	private String generateSummary(List<ExercisePo> exercisePos) {
		String summary = String.format("本月共打卡%s次.", exercisePos.size());
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
