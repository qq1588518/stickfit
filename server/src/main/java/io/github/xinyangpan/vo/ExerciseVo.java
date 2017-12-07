package io.github.xinyangpan.vo;

import java.math.BigDecimal;
import java.util.Date;

import io.github.xinyangpan.persistent.po.ExercisePo;
import io.github.xinyangpan.persistent.po.ExerciseTypePo;

public class ExerciseVo {
	private ExercisePo exercisePo;
	private String tag;
	private String description;

	@SuppressWarnings("deprecation")
	public static ExerciseVo from(ExercisePo exercisePo, ExerciseTypePo exerciseTypePo) {
		Date date = exercisePo.getTime();
		String description = exerciseTypePo.getDescription();
		BigDecimal amount = exercisePo.getAmount();
		String unit = exerciseTypePo.getUnit();
		// 
		ExerciseVo exerciseVo = new ExerciseVo();
		exerciseVo.setExercisePo(exercisePo);
		exerciseVo.setDescription(String.format("%s日%s%s%s", date.getDate(), description, amount, unit));
		if (exercisePo.getTypeId() == 1) {
			if (amount.compareTo(new BigDecimal("100")) >= 0) {
				exerciseVo.setTag("超马");
			} else if (amount.compareTo(new BigDecimal("42")) >= 0) {
				exerciseVo.setTag("全马");
			} else if (amount.compareTo(new BigDecimal("21")) >= 0) {
				exerciseVo.setTag("半马");
			} else {
				exerciseVo.setTag("");
			}
		} else {
			exerciseVo.setTag("");
		}
		return exerciseVo;
	}

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
