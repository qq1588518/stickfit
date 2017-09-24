package io.github.xinyangpan.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.xinyangpan.service.ExerciseService;
import io.github.xinyangpan.vo.CurrentMonthHistory;

@RestController
@RequestMapping("/exercise")
public class ExerciseController {
	@Autowired
	private ExerciseService exerciseService;

	@GetMapping("/currentMonthHistory")
	public CurrentMonthHistory currentMonthHistory(long customerId) {
		return exerciseService.currentMonthHistory(customerId);
	}

	@GetMapping("/deleteExercisesByIds")
	public void deleteExercisesByIds(ArrayList<Long> ids) {
		exerciseService.deleteExercisesByIds(ids);
	}

}
