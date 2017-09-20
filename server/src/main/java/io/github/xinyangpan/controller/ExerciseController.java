package io.github.xinyangpan.controller;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import io.github.xinyangpan.core.CoreUtils;
import io.github.xinyangpan.persistent.dao.ExerciseDao;
import io.github.xinyangpan.persistent.dao.ExerciseTypeDao;
import io.github.xinyangpan.persistent.po.ExercisePo;
import io.github.xinyangpan.persistent.po.ExerciseTypePo;
import io.github.xinyangpan.vo.CurrentMonthHistory;

@Controller
public class ExerciseController {
	private ExerciseDao exerciseDao;
	private ExerciseTypeDao exerciseTypeDao;

	@GetMapping("/currentMonthHistory")
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
