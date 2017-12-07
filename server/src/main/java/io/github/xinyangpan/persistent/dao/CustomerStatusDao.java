package io.github.xinyangpan.persistent.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.google.common.collect.Maps;

import io.github.xinyangpan.persistent.po.CustomerStatusPo;
import io.github.xinyangpan.persistent.po.type.YearMonth;

public interface CustomerStatusDao extends PagingAndSortingRepository<CustomerStatusPo, Long> {

	List<CustomerStatusPo> findByGroupIdAndMonth(long groupId, YearMonth yearMonth);
	
	CustomerStatusPo findByCustomerIdAndMonth(long customerId, YearMonth yearMonth);

	default Map<Long, CustomerStatusPo> id2CustomerStatusPo(long groupId, YearMonth yearMonth) {
		List<CustomerStatusPo> customerStatusPos = this.findByGroupIdAndMonth(groupId, yearMonth);
		return Maps.uniqueIndex(customerStatusPos, CustomerStatusPo::getCustomerId);
	}

}
