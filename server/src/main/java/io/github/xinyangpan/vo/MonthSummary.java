package io.github.xinyangpan.vo;

import java.util.List;

import io.github.xinyangpan.persistent.po.type.YearMonth;

public class MonthSummary {
	private YearMonth yearMonth;
	private List<ExerciseVo> exerciseVos;
	private String summary;

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
