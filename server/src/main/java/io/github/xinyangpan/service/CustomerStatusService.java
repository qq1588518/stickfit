package io.github.xinyangpan.service;

import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;

import io.github.xinyangpan.core.BusinessUtils;
import io.github.xinyangpan.core.StatusEnum;
import io.github.xinyangpan.persistent.dao.CustomerDao;
import io.github.xinyangpan.persistent.dao.CustomerStatusDao;
import io.github.xinyangpan.persistent.po.CustomerPo;
import io.github.xinyangpan.persistent.po.CustomerStatusPo;
import io.github.xinyangpan.persistent.po.type.YearMonth;
import io.github.xinyangpan.vo.CustomerStatusVo;

@Service
@Transactional
public class CustomerStatusService {
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private CustomerStatusDao customerStatusDao;

	public Map<Long, CustomerStatusVo> customerId2CustomerStatusVo(long groupId, YearMonth yearMonth) {
		Map<Long, CustomerPo> id2CustomerPo = customerDao.id2CustomerPo(groupId);
		Map<Long, CustomerStatusVo> customerId2RankItem = Maps.newHashMap(Maps.transformValues(id2CustomerPo, t -> this.convert(t, yearMonth)));
		List<CustomerStatusPo> customerStatusPos = customerStatusDao.findByGroupIdAndMonth(groupId, yearMonth);
		// 
		for (CustomerStatusPo customerStatusPo : customerStatusPos) {
			CustomerStatusVo customerStatusVo = customerId2RankItem.get(customerStatusPo.getCustomerId());
			customerStatusVo.setLeave(BusinessUtils.isLeave(customerStatusPo));
		}
		return customerId2RankItem;
	}

	public void setLeave(long customerId, YearMonth yearMonth) {
		CustomerStatusPo customerStatusPo = customerStatusDao.findByCustomerIdAndMonth(customerId, yearMonth);
		if (customerStatusPo == null) {
			CustomerPo customerPo = customerDao.findOne(customerId);
			customerStatusPo = new CustomerStatusPo();
			customerStatusPo.setCustomerId(customerId);
			customerStatusPo.setGroupId(customerPo.getGroupId());
			customerStatusPo.setMonth(yearMonth);
		}
		customerStatusPo.setStatusEnum(StatusEnum.LEAVE);
		customerStatusDao.save(customerStatusPo);
	}


	public void setNotLeave(long customerId, YearMonth yearMonth) {
		CustomerStatusPo customerStatusPo = customerStatusDao.findByCustomerIdAndMonth(customerId, yearMonth);
		if (customerStatusPo == null) {
			return;
		} else {
			customerStatusDao.delete(customerStatusPo);
		}
	}


	private CustomerStatusVo convert(CustomerPo customerPo, YearMonth yearMonth) {
		CustomerStatusVo customerStatusVo = new CustomerStatusVo();
		customerStatusVo.setYearMonth(yearMonth);
		customerStatusVo.setCustomerPo(customerPo);
		return customerStatusVo;
	}
	
}
