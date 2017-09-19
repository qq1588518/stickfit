package io.github.xinyangpan.vo;

import java.util.List;

import io.github.xinyangpan.persistent.po.ExercisePo;

public class CurrentMonthHistory {
	private List<ExercisePo> exercisePos;
	private String summary;

	@Override
	public String toString() {
		return String.format("CurrentMonthHistory [exercisePos=%s, summary=%s]", exercisePos, summary);
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

}
