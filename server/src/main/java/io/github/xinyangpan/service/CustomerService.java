package io.github.xinyangpan.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Preconditions;

import io.github.xinyangpan.persistent.dao.CustomerDao;
import io.github.xinyangpan.persistent.po.CustomerPo;

@Service
@Transactional
public class CustomerService {
	@Autowired
	private CustomerDao customerDao;

	public CustomerPo update(Long customerId, String username) {
		CustomerPo customerPo = customerDao.findOne(customerId);
		customerPo = updateUserNameWhenNull(customerPo, username);
		return customerPo;
	}

	public CustomerPo login(String openId, String username) {
		// 
		Preconditions.checkNotNull(openId);
		// 
		CustomerPo customerPo = customerDao.findByOpenId(openId);
		if (customerPo == null) {
			// first time
			return this.createCustomer(openId, username);
		} else {
			customerPo = updateUserNameWhenNull(customerPo, username);
			return customerPo;
		}
	}

	private CustomerPo updateUserNameWhenNull(CustomerPo customerPo, String username) {
		if (customerPo.getUsername() == null && username != null) {
			customerPo.setUsername(username);
			customerPo = customerDao.save(customerPo);
		}
		return customerPo;
	}

	private CustomerPo createCustomer(String openId, String username) {
		CustomerPo customerPo = new CustomerPo();
		customerPo.setUsername(username);
		customerPo.setOpenId(openId);
		customerPo.setGroupId(1L);
		return customerDao.save(customerPo);
	}

}
