package io.github.xinyangpan.vo;

import java.util.List;

import io.github.xinyangpan.persistent.po.ExercisePo;
import io.github.xinyangpan.persistent.po.type.YearMonth;

public class MonthSummary {
	private YearMonth yearMonth;
	private List<ExercisePo> exercisePos;
	private String summary;

	@Override
	public String toString() {
		return String.format("MonthSummary [yearMonth=%s, exercisePos=%s, summary=%s]", yearMonth, exercisePos, summary);
	}

	public List<ExercisePo> getExercisePos() {
		return exercisePos;
	}

	public void setExercisePos(List<ExercisePo> exercisePos) {
		this.exercisePos = exercisePos;
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
