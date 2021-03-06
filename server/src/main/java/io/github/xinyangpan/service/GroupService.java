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
import io.github.xinyangpan.vo.GroupVo;

@Service
@Transactional
public class GroupService {
	@Autowired
	private ExerciseDao exerciseDao;
	@Autowired
	private CustomerDao customerDao;
	@Autowired
	private GroupDao groupDao;

	public GroupVo get(long groupId) {
		GroupPo groupPo = groupDao.findOne(groupId);
		CustomerPo customerPo = customerDao.findOne(groupPo.getOwnerId());
		List<CustomerPo> members = customerDao.findByGroupIdAndUsernameIsNotNull(groupId);
		return new GroupVo(groupPo, customerPo, members);
	}
	
	public void dismiss(long customerId, long groupId) {
		GroupPo groupPo = groupDao.findOne(groupId);
		// is the owner
		Assert.isTrue(groupPo.getOwnerId() == customerId, "Only owner can dismis the group!");
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
		Assert.isTrue(groupPo.getOwnerId() != customerId, "Owner can not leave the group!");
		// set groupId to null to remove from group
		customerPo.setGroupId(null);
		customerDao.save(customerPo);
		// delete all exercise data
		exerciseDao.deleteByCustomerId(customerId);
	}

	public void transfer(long from, long to, long groupId) {
		GroupPo groupPo = groupDao.findOne(groupId);
		// is the owner
		Assert.isTrue(groupPo.getOwnerId() == from, "Only owner can transfer the group!");
		// "to" is in the group
		CustomerPo toCustomerPo = customerDao.findOne(to);
		Assert.isTrue(toCustomerPo.getGroupId() == groupId, "Next owner must be in the group!");
		// change
		groupPo.setOwnerId(to);
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

	public void create(GroupPo groupPo) {
		GroupPo newGroupPo = groupDao.save(groupPo);
		//
		CustomerPo customerPo = customerDao.findOne(newGroupPo.getOwnerId());
		customerPo.setGroupId(newGroupPo.getId());
		customerDao.save(customerPo);
	}

}
