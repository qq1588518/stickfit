package io.github.xinyangpan.vo;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

import io.github.xinyangpan.persistent.po.ExerciseTypePo;
import io.github.xinyangpan.persistent.po.type.YearMonth;

public class MonthSummary {
	private YearMonth yearMonth;
	private List<ExerciseVo> exerciseVos;
	private String summary;

	public void generateSummary(Map<Long, ExerciseTypePo> id2ExerciseTypePo) {
		String summary = String.format("打卡%s次.", exerciseVos.size());
		if (exerciseVos.isEmpty()) {
			this.summary = summary;
			return;
		}
		Multimap<Long, ExerciseVo> index = Multimaps.index(exerciseVos, exercisePo -> exercisePo.getExercisePo().getTypeId());
		Map<Long, BigDecimal> treeMap = new TreeMap<>(Maps.transformValues(index.asMap(), MonthSummary::sumAllAmount));
		// 
		List<String> summaryByType = treeMap.entrySet().stream().map(e -> {
			ExerciseTypePo exerciseTypePo = id2ExerciseTypePo.get(e.getKey());
			String amount = e.getValue().stripTrailingZeros().toPlainString();
			return String.format("%s%s%s", exerciseTypePo.getDescription(), amount, exerciseTypePo.getUnit());
		}).collect(Collectors.toList());
		this.summary = String.format("%s 一共%s.", summary, Joiner.on(", ").join(summaryByType).toString());
	}

	private static BigDecimal sumAllAmount(Collection<ExerciseVo> pos) {
		return pos.stream().map(po -> po.getExercisePo().getAmount()).reduce((a, b) -> a.add(b)).get();
	}

	@Override
	public String toString() {
		return String.format("MonthSummary [yearMonth=%s, exercisePos=%s, summary=%s]", yearMonth, exerciseVos, summary);
	}

	public List<ExerciseVo> getExerciseVos() {
		return exerciseVos;
	}

	public void setExerciseVos(List<ExerciseVo> exercisePos) {
		this.exerciseVos = exercisePos;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public YearMonth getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(YearMonth yearMonth) {
		this.yearMonth = yearMonth;
	}

}
