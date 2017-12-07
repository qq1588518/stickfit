package io.github.xinyangpan.persistent.dao;

import java.util.Map;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.google.common.collect.Maps;

import io.github.xinyangpan.persistent.po.ExerciseTypePo;

public interface ExerciseTypeDao extends PagingAndSortingRepository<ExerciseTypePo, Long> {

	@Override
	@Cacheable("ExerciseTypePoById")
	ExerciseTypePo findOne(Long id);

	default Map<Long, ExerciseTypePo> id2ExerciseTypePo() {
		Iterable<ExerciseTypePo> all = this.findAll();
		return Maps.uniqueIndex(all, ExerciseTypePo::getId);
	}

}
