package io.github.xinyangpan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.xinyangpan.service.ExerciseService;
import io.github.xinyangpan.vo.CurrentMonthHistory;

@RestController
public class ExerciseController {
	@Autowired
	private ExerciseService exerciseService;

	@GetMapping("/currentMonthHistory")
	public CurrentMonthHistory currentMonthHistory(long customerId) {
		return exerciseService.currentMonthHistory(customerId);
	}

}
