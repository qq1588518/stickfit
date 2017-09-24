package io.github.xinyangpan.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Joiner;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multiset.Entry;

import io.github.xinyangpan.core.CoreUtils;
import io.github.xinyangpan.persistent.dao.CustomerDao;
import io.github.xinyangpan.persistent.dao.ExerciseDao;
import io.github.xinyangpan.persistent.dao.ExerciseTypeDao;
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
		List<RankItem> rank = Lists.newArrayList();
		List<ExercisePo> exercisePos = exerciseDao.findByMonth(month);
		Multiset<Long> multiset = HashMultiset.create();
		for (ExercisePo exercisePo : exercisePos) {
			multiset.add(exercisePo.getCustomerId());
		}
		for (Entry<Long> e : multiset.entrySet()) {
			rank.add(new RankItem(customerDao.findOne(e.getElement()).getUsername(), e.getCount()));
		}
		Collections.sort(rank, Comparator.comparingInt(RankItem::getCount).reversed());
		return rank;
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
		List<String> summaryByType = treeMap.entrySet()
			.stream()
			.map(e -> {
				ExerciseTypePo exerciseTypePo = exerciseTypeDao.findOne(e.getKey());
				return String.format("%s%s%s", exerciseTypePo.getDescription(), e.getValue(), exerciseTypePo.getUnit());
			})
			.collect(Collectors.toList());
		return String.format("%s 一共%s.", summary, Joiner.on(", ").join(summaryByType).toString());
	}

	private BigDecimal sumAllAmount(Collection<ExercisePo> pos) {
		return pos.stream()
			.map(po -> po.getAmount())
			.reduce((a, b) -> a.add(b))
			.get();
	}

}
