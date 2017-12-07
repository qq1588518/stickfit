package io.github.xinyangpan.vo;

import java.math.BigDecimal;

import io.github.xinyangpan.persistent.po.CustomerPo;

public class RankEntry {
	private long customerId;
	private String username;
	private int count;
	private int jogCount;
	private BigDecimal jogAmount = BigDecimal.ZERO;
	private boolean leave;
	private boolean meetStandard;

	public RankEntry() {
	}

	public static RankEntry from(CustomerPo customerPo) {
		RankEntry rankEntry = new RankEntry();
		rankEntry.setCustomerId(customerPo.getId());
		rankEntry.setUsername(customerPo.getUsername());
		rankEntry.setCount(0);
		rankEntry.setJogAmount(BigDecimal.ZERO);
		rankEntry.setJogCount(0);
		return rankEntry;
	}

	public String getDescription() {
		return String.format("%s打卡%s次 - %s公里", username, count, jogAmount);
	}

	public Tag getTag() {
		if (this.isMeetStandard()) {
			return new Tag("达标", "good");
		}
		if (this.isLeave()) {
			return new Tag("请假", "bad");
		}
		return new Tag("", "");
	}

	@Override
	public String toString() {
		return String
			.format("RankEntry [customerId=%s, username=%s, count=%s, jogCount=%s, jogAmount=%s, leave=%s, meetStandard=%s, getDescription()=%s, getTag()=%s]", customerId, username, count, jogCount, jogAmount, leave, meetStandard, getDescription(), getTag());
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public BigDecimal getJogAmount() {
		return jogAmount;
	}

	public void setJogAmount(BigDecimal jogAmount) {
		this.jogAmount = jogAmount;
	}

	public int getJogCount() {
		return jogCount;
	}

	public void setJogCount(int jogCount) {
		this.jogCount = jogCount;
	}

	public boolean isLeave() {
		return leave;
	}

	public void setLeave(boolean leave) {
		this.leave = leave;
	}

	public boolean isMeetStandard() {
		return meetStandard;
	}

	public void setMeetStandard(boolean meetStandard) {
		this.meetStandard = meetStandard;
	}

}
