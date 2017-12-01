package io.github.xinyangpan.controller;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.github.xinyangpan.persistent.po.type.YearMonth;

class ExerciseControllerTest {

	@Test
	void testHistoryRange() {
		ExerciseController exerciseController = new ExerciseController();
		List<YearMonth> historyRange = exerciseController.historyRange();
		System.out.println(historyRange);
		Assertions.assertNotNull(historyRange);
	}

}
