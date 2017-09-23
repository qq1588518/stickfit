package io.github.xinyangpan.service;

import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.xinyangpan.persistent.dao.CustomerDao;
import io.github.xinyangpan.persistent.po.CustomerPo;

@Service
@Transactional
public class CustomerService {
	@Autowired
	private CustomerDao customerDao;

	public CustomerPo login(String openId) {
		CustomerPo customerPo = customerDao.findByOpenId(openId);
		if (customerPo == null) {
			// first time
			return this.createCustomer(openId, null);
		} else {
			return customerPo;
		}
	}

	public CustomerPo update(String openId, String username) {
		CustomerPo customerPo = customerDao.findByOpenId(openId);
		if (customerPo == null) {
			// first time
			return this.createCustomer(openId, username);
		} else {
			if (!Objects.equals(customerPo.getUsername(), username)) {
				customerPo.setUsername(username);
				customerPo = customerDao.save(customerPo);
			}
			return customerPo;
		}
	}

	private CustomerPo createCustomer(String openId, String username) {
		CustomerPo customerPo = new CustomerPo();
		customerPo.setUsername(username);
		customerPo.setOpenId(openId);
		return customerDao.save(customerPo);
	}

}
