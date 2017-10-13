package io.github.xinyangpan.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;

import io.github.xinyangpan.persistent.po.type.YearMonth;
import io.github.xinyangpan.service.ExerciseService;
import io.github.xinyangpan.vo.MonthSummary;
import io.github.xinyangpan.vo.RankItem;

@RestController
@RequestMapping("/exercise")
public class ExerciseController {
	@Autowired
	private ExerciseService exerciseService;

	@GetMapping("/historyRange")
	public List<YearMonth> historyRange() {
		LocalDate start = LocalDate.parse("2017-09-01");
		LocalDate now = LocalDate.now();
		LocalDate current = start;
		// 
		List<LocalDate> localDates = Lists.newArrayList();
		while (true) {
			if (current.getMonthValue() > now.getMonthValue()) {
				break;
			}
			localDates.add(current);
			current = current.plusMonths(1);
		}
		return localDates.stream().map(YearMonth::of).collect(Collectors.toList());
	}
	
	@GetMapping("/monthSummary")
	public MonthSummary monthSummary(long customerId, Integer year, Integer month) {
		return exerciseService.monthHistory(customerId, YearMonth.of(year, month));
	}

	@GetMapping("/rank")
	public List<RankItem> rank(Long groupId, Integer year, Integer month) {
		if (groupId == null) {
			return Lists.newArrayList();
		}
		return exerciseService.rank(groupId, YearMonth.of(year, month));
	}

	@GetMapping("/deleteExercisesByIds")
	public void deleteExercisesByIds(String ids) {
		if (StringUtils.isEmpty(ids)) {
			return;
		}
		List<Long> idList = Arrays.stream(ids.split(",")).map(s -> Long.parseLong(s)).collect(Collectors.toList());
		exerciseService.deleteExercisesByIds(Lists.newArrayList(idList));
	}

}
