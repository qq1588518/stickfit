package io.github.xinyangpan.persistent.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import io.github.xinyangpan.persistent.po.ExercisePo;

public interface ExerciseDao extends PagingAndSortingRepository<ExercisePo, Long> {

	List<ExercisePo> findByCustomerIdAndMonthOrderByTimeAsc(long customerId, int yearMonth);
	
	List<ExercisePo> findByMonth(int yearMonth);

//	@Query(value = "select new io.github.xinyangpan.persistent.vo.RankItem(c.username, count(*)) from exercise_po e, customer_po c where e.customer_id=c.id and e.month=?1  group by e.customer_id")
//	List<RankItem> rankByMonth(int month);

}
