package io.github.xinyangpan.persistent.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import io.github.xinyangpan.persistent.po.CustomerStatusPo;
import io.github.xinyangpan.persistent.po.type.YearMonth;

public interface CustomerStatusDao extends PagingAndSortingRepository<CustomerStatusPo, Long> {

	List<CustomerStatusPo> findByGroupIdAndMonth(long groupId, YearMonth yearMonth);

}
