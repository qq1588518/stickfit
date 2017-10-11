package io.github.xinyangpan.service;

import java.util.List;
import java.util.Objects;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import io.github.xinyangpan.persistent.dao.CustomerDao;
import io.github.xinyangpan.persistent.dao.ExerciseDao;
import io.github.xinyangpan.persistent.dao.GroupDao;
import io.github.xinyangpan.persistent.po.CustomerPo;
import io.github.xinyangpan.persistent.po.GroupPo;

@Service
@Transactional
public class GroupService {
	@Autowired
	private ExerciseDao exerciseDao;
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private GroupDao groupDao;

	public void dismiss(long customerId, long groupId) {
		GroupPo groupPo = groupDao.findOne(groupId);
		// is the owner
		Assert.isTrue(groupPo.getOwner() == customerId, "Only owner can dismis the group!");
		// reomve all members
		List<CustomerPo> customerPos = customerDao.findByGroupId(groupId);
		customerPos.forEach(e -> {e.setGroupId(null);});
		customerDao.save(customerPos);
		// delete all group exercise data
		exerciseDao.deleteByGroupId(groupId);
		// delete the group
		groupDao.delete(groupPo);
	}

	public void leave(long customerId, long groupId) {
		GroupPo groupPo = groupDao.findOne(groupId);
		CustomerPo customerPo = customerDao.findOne(customerId);
		// is in the group
		Assert.isTrue(customerPo.getGroupId() != null && customerPo.getGroupId() == groupId, "Not in the group!");
		// is the owner
		Assert.isTrue(groupPo.getOwner() != customerId, "Owner can not leave the group!");
		// set groupId to null to remove from group
		customerPo.setGroupId(null);
		customerDao.save(customerPo);
		// delete all exercise data
		exerciseDao.deleteByCustomerId(customerId);
	}

	public void transfer(long from, long to, long groupId) {
		GroupPo groupPo = groupDao.findOne(groupId);
		// is the owner
		Assert.isTrue(groupPo.getOwner() == from, "Only owner can transfer the group!");
		// "to" is in the group
		CustomerPo toCustomerPo = customerDao.findOne(to);
		Assert.isTrue(toCustomerPo.getGroupId() == groupId, "Next owner must be in the group!");
		// change
		groupPo.setOwner(to);
		groupDao.save(groupPo);
	}

	public void join(long customerId, long groupId, String password) {
		GroupPo groupPo = groupDao.findOne(groupId);
		// check password
		Assert.isTrue(Objects.equals(groupPo.getPassword(), password), "Wrong password!");
		//
		CustomerPo customerPo = customerDao.findOne(customerId);
		customerPo.setGroupId(groupId);
		customerDao.save(customerPo);
	}

}
