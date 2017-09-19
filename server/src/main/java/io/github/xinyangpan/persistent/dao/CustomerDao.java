package io.github.xinyangpan.persistent.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import io.github.xinyangpan.persistent.po.CustomerPo;

public interface CustomerDao extends PagingAndSortingRepository<CustomerPo, Long> {

	CustomerPo findByOpenId(String openId);
	
}
