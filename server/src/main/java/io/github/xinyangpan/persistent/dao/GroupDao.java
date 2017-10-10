package io.github.xinyangpan.persistent.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import io.github.xinyangpan.persistent.po.GroupPo;

public interface GroupDao extends PagingAndSortingRepository<GroupPo, Long> {
	
}
