package io.github.xinyangpan.controller;

import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import io.github.xinyangpan.persistent.dao.CustomerDao;
import io.github.xinyangpan.persistent.po.CustomerPo;

@Controller
public class RegisterController {
	private CustomerDao customerDao;

	@GetMapping("/login")
	public CustomerPo login(String openId, String username) {
		CustomerPo customerPo = customerDao.findByOpenId(openId);
		if (customerPo == null) {
			// first time
			return this.register(openId, username);
		} else {
			if (!Objects.equals(customerPo.getUsername(), username)) {
				customerPo.setUsername(username);
				customerPo = customerDao.save(customerPo);
			}
			return customerPo;
		}
	}

	private CustomerPo register(String openId, String username) {
		CustomerPo customerPo = new CustomerPo();
		customerPo.setUsername(username);
		customerPo.setOpenId(openId);
		return customerPo;
	}

}
