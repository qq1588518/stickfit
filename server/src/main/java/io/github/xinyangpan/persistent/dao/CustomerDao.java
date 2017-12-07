package io.github.xinyangpan.persistent.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.google.common.collect.Maps;

import io.github.xinyangpan.persistent.po.CustomerPo;

public interface CustomerDao extends PagingAndSortingRepository<CustomerPo, Long> {

	CustomerPo findByOpenId(String openId);

	List<CustomerPo> findByIdIn(Collection<Long> customerIds);

	List<CustomerPo> findByGroupIdAndUsernameIsNotNull(@Param("groupId") Long groupId);

	List<CustomerPo> findByGroupId(@Param("groupId") Long groupId);

	default Map<Long, CustomerPo> id2CustomerPo(long groupId) {
		List<CustomerPo> customerPos = this.findByGroupIdAndUsernameIsNotNull(groupId);
		return Maps.uniqueIndex(customerPos, CustomerPo::getId);
	}

}
