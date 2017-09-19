package io.github.xinyangpan.persistent.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import io.github.xinyangpan.persistent.po.ExerciseTypePo;

public interface ExerciseTypeDao extends PagingAndSortingRepository<ExerciseTypePo, Long> {

}
