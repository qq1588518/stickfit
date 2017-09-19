package io.github.xinyangpan.persistent.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import io.github.xinyangpan.persistent.po.ExercisePo;

public interface ExerciseDao extends PagingAndSortingRepository<ExercisePo, Long> {

	List<ExercisePo> findByCustomerIdAndMonthOrderByTimeAsc(long customerId, int month);

}
