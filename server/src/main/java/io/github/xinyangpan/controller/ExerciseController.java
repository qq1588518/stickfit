package io.github.xinyangpan.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import io.github.xinyangpan.persistent.po.type.YearMonth;
import io.github.xinyangpan.persistent.vo.RankItem;
import io.github.xinyangpan.service.ExerciseService;
import io.github.xinyangpan.vo.CurrentMonthHistory;

@RestController
@RequestMapping("/exercise")
public class ExerciseController {
	@Autowired
	private ExerciseService exerciseService;

	@GetMapping("/currentMonthHistory")
	public CurrentMonthHistory currentMonthHistory(long customerId) {
		return exerciseService.currentMonthHistory(customerId, YearMonth.now());
	}

	@GetMapping("/monthHistory")
	public CurrentMonthHistory monthHistory(long customerId, int year, int month) {
		return exerciseService.currentMonthHistory(customerId, new YearMonth(year, month));
	}

	@GetMapping("/historyRange")
	public List<YearMonth> historyRange() {
		LocalDate start = LocalDate.parse("2017-09-01");
		LocalDate now = LocalDate.now();
		LocalDate current = start;
		// 
		List<LocalDate> localDates = Lists.newArrayList();
		while (true) {
			if (current.getMonthValue() >= now.getMonthValue()) {
				break;
			}
			localDates.add(current);
			current = current.plusMonths(1);
		}
		return localDates.stream().map(YearMonth::of).collect(Collectors.toList());
	}

	@GetMapping("/deleteExercisesByIds")
	public void deleteExercisesByIds(String ids) {
		List<Long> idList = Arrays.stream(ids.split(",")).map(s -> Long.parseLong(s)).collect(Collectors.toList());
		exerciseService.deleteExercisesByIds(Lists.newArrayList(idList));
	}

	@GetMapping("/rank")
	public List<RankItem> rank() {
		return exerciseService.rank(YearMonth.now());
	}

	@GetMapping("/historyRank")
	public List<RankItem> historyRank(int year, int month) {
		return exerciseService.rank(new YearMonth(year, month));
	}

}
