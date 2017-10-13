package io.github.xinyangpan.vo;

import java.util.List;

import io.github.xinyangpan.persistent.po.CustomerPo;
import io.github.xinyangpan.persistent.po.GroupPo;

public class GroupVo {
	private GroupPo group;
	private CustomerPo owner;
	private List<CustomerPo> members;

	public GroupVo() {
		super();
	}

	public GroupVo(GroupPo group, CustomerPo owner) {
		super();
		this.group = group;
		this.owner = owner;
	}

	public GroupVo(GroupPo group, CustomerPo owner, List<CustomerPo> members) {
		super();
		this.group = group;
		this.owner = owner;
		this.members = members;
	}

	@Override
	public String toString() {
		return String.format("GroupVo [group=%s, owner=%s, members=%s]", group, owner, members);
	}

	public GroupPo getGroup() {
		return group;
	}

	public void setGroup(GroupPo group) {
		this.group = group;
	}

	public CustomerPo getOwner() {
		return owner;
	}

	public void setOwner(CustomerPo owner) {
		this.owner = owner;
	}

	public List<CustomerPo> getMembers() {
		return members;
	}

	public void setMembers(List<CustomerPo> members) {
		this.members = members;
	}

}
