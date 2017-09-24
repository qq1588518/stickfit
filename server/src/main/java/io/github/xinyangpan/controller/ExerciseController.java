package io.github.xinyangpan.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

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

	@PostMapping("/deleteExercisesByIds")
	public void deleteExercisesByIds(String ids) {
		List<Long> idList = Arrays.stream(ids.split(",")).map(s -> Long.parseLong(s)).collect(Collectors.toList());
		System.out.println("deleteExercisesByIds" + idList);
		exerciseService.deleteExercisesByIds(Lists.newArrayList(idList));
	}

}
