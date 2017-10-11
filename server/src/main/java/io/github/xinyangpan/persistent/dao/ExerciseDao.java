package io.github.xinyangpan.persistent.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import io.github.xinyangpan.persistent.po.ExercisePo;
import io.github.xinyangpan.persistent.po.type.YearMonth;

public interface ExerciseDao extends PagingAndSortingRepository<ExercisePo, Long> {

	List<ExercisePo> findByCustomerIdAndMonthOrderByTimeAsc(long customerId, YearMonth yearMonth);

	List<ExercisePo> findByGroupIdAndMonth(long groupId, YearMonth yearMonth);

	void deleteByCustomerId(long customerId);
	
	void deleteByGroupId(long groupId);

}
