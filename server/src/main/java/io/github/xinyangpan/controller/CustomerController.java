package io.github.xinyangpan.controller;

import java.util.Collection;

import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.xinyangpan.persistent.po.CustomerPo;
import io.github.xinyangpan.persistent.po.type.YearMonth;
import io.github.xinyangpan.service.CoreService;
import io.github.xinyangpan.service.CustomerService;
import io.github.xinyangpan.service.CustomerStatusService;
import io.github.xinyangpan.vo.CustomerStatusVo;
import io.github.xinyangpan.vo.Session;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	private static final Logger log = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private CoreService coreService;
	@Autowired
	private CustomerService customerService;
	@Autowired
	private CustomerStatusService customerStatusService;

	@GetMapping("/login")
	public CustomerPo login(String code, String username) {
		log.info("login start - code={}, username={}", code, username);
		String appId = "wx7697e61e4407f57a";
		String secret = "672fa10aea63e42a39a683c65267dca2";
		Session session = coreService.jscode2session(appId, secret, code);
		log.info("jscode2session: {}", session);
		return customerService.login(session.getOpenId(), username);
	}

	@GetMapping("/update")
	public CustomerPo update(Long customerId, String username) {
		return customerService.update(customerId, username);
	}

	@GetMapping("/statusList")
	public Collection<CustomerStatusVo> statusList(long groupId, Integer yearMonthInt) {
		return customerStatusService.customerId2CustomerStatusVo(groupId, YearMonth.of(yearMonthInt)).values();
	}

	@GetMapping("/takeLeave")
	public void takeLeave(long customerId, Integer yearMonthInt, Boolean leave) {
		YearMonth yearMonth = YearMonth.of(yearMonthInt);
		boolean isLeave = BooleanUtils.isTrue(leave);
		if (isLeave) {
			customerStatusService.setLeave(customerId, yearMonth);
		} else {
			customerStatusService.setNotLeave(customerId, yearMonth);
		}
	}

}
