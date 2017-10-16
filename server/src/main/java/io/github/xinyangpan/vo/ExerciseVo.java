package io.github.xinyangpan.vo;

import io.github.xinyangpan.persistent.po.ExercisePo;

public class ExerciseVo {
	private ExercisePo exercisePo;
	private String tag;
	private String description;

	@Override
	public String toString() {
		return String.format("ExerciseVo [exercisePo=%s, tag=%s, description=%s]", exercisePo, tag, description);
	}

	public ExercisePo getExercisePo() {
		return exercisePo;
	}

	public void setExercisePo(ExercisePo exercisePo) {
		this.exercisePo = exercisePo;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
