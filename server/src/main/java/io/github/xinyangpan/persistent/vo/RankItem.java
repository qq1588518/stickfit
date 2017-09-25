package io.github.xinyangpan.persistent.vo;

public class RankItem {
	private String username;
	private int count;
	private long lastId;
	
	public RankItem() {
	}
	
	public RankItem(String username, int count, long lastId) {
		super();
		this.username = username;
		this.count = count;
		this.lastId = lastId;
	}

	@Override
	public String toString() {
		return String.format("RankItem [username=%s, count=%s, lastId=%s]", username, count, lastId);
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

}
