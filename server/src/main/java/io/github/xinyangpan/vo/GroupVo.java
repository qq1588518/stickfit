package io.github.xinyangpan.vo;

import io.github.xinyangpan.persistent.po.CustomerPo;
import io.github.xinyangpan.persistent.po.GroupPo;

public class GroupVo {
	private GroupPo group;
	private CustomerPo owner;

	public GroupVo() {
		super();
	}

	public GroupVo(GroupPo group, CustomerPo owner) {
		super();
		this.group = group;
		this.owner = owner;
	}

	@Override
	public String toString() {
		return String.format("GroupVo [group=%s, owner=%s]", group, owner);
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

}
