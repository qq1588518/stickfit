package io.github.xinyangpan.vo;

import java.math.BigDecimal;

public class RankEntry {
	private long customerId;
	private String username;
	private int count;
	private int jogCount;
	private BigDecimal jogAmount = BigDecimal.ZERO;
	private String tag;

	public RankEntry() {
	}

	public String getDescription() {
		return String.format("%s打卡%s次 - %s公里", username, count, jogAmount);
	}

	@Override
	public String toString() {
		return String.format("RankEntry [customerId=%s, username=%s, count=%s, jogCount=%s, jogAmount=%s, tag=%s, getDescription()=%s]", customerId, username, count, jogCount, jogAmount, getTag(), getDescription());
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

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}
