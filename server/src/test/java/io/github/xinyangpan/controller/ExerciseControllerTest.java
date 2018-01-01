package io.github.xinyangpan.controller;

import java.util.List;

import org.junit.Test;

import io.github.xinyangpan.persistent.po.type.YearMonth;

public class ExerciseControllerTest {
	private ExerciseController ExerciseController = new ExerciseController();

	@Test
	public void test() {
		List<YearMonth> yearMonths = ExerciseController.historyRange();
		System.out.println(yearMonths);
	}

}
