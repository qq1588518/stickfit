package io.github.xinyangpan.persistent.dao;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.PagingAndSortingRepository;

import io.github.xinyangpan.persistent.po.ExerciseTypePo;

public interface ExerciseTypeDao extends PagingAndSortingRepository<ExerciseTypePo, Long> {

	@Override
	@Cacheable("ExerciseTypePoById")
	ExerciseTypePo findOne(Long id);

}
