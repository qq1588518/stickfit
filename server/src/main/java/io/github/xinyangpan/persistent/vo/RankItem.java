package io.github.xinyangpan.persistent.vo;

public class RankItem {
	private String username;
	private int count;
	
	public RankItem() {
	}
	
	public RankItem(String username, int count) {
		super();
		this.username = username;
		this.count = count;
	}

	@Override
	public String toString() {
		return String.format("CustomerPo [username=%s, count=%s]", username, count);
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

}
