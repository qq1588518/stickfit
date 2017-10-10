package io.github.xinyangpan.vo;

import java.math.BigDecimal;

public class RankItem {
	private long customerId;
	private String username;
	private int count;
	private long lastId;
	private BigDecimal jogAmount = BigDecimal.ZERO;

	public RankItem() {
	}

	public RankItem(long customerId, String username, int count, long lastId) {
		super();
		this.customerId = customerId;
		this.username = username;
		this.count = count;
		this.lastId = lastId;
	}

	@Override
	public String toString() {
		return String.format("RankItem [customerId=%s, username=%s, count=%s, lastId=%s, jogAmount=%s]", customerId, username, count, lastId, jogAmount);
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

	public void setUsername(String customerName) {
		this.username = customerName;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public long getLastId() {
		return lastId;
	}

	public void setLastId(long lastId) {
		this.lastId = lastId;
	}

	public BigDecimal getJogAmount() {
		return jogAmount;
	}

	public void setJogAmount(BigDecimal jogAmount) {
		this.jogAmount = jogAmount;
	}

}
