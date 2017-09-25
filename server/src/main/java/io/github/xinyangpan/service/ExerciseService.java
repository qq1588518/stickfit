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
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

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
		List<ExercisePo> exercisePos = exerciseDao.findByMonth(month);
		Map<Long, RankItem> map = Maps.newHashMap();
		for (ExercisePo exercisePo : exercisePos) {
			long customerId = exercisePo.getCustomerId();
			RankItem rankItem = map.get(customerId);
			if (rankItem == null) {
				rankItem = new RankItem(customerDao.findOne(exercisePo.getCustomerId()).getUsername(), 1, exercisePo.getId());
				map.put(customerId, rankItem);
			} else {
				rankItem.setCount(rankItem.getCount() + 1);
				rankItem.setLastId(Math.max(exercisePo.getId(), rankItem.getLastId()));
			}
		}
		// 
		List<RankItem> rank = Lists.newArrayList(map.values());
		Collections.sort(rank, Comparator.comparingInt(RankItem::getCount).reversed().thenComparing(RankItem::getLastId));
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
