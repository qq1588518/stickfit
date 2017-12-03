package io.github.xinyangpan.vo;

import java.util.List;

import io.github.xinyangpan.persistent.po.type.YearMonth;

public class Rank {
	private YearMonth yearMonth;
	private List<RankEntry> rankEntries;
	private String pioneer;
	
	public Rank() {
	}
	
	public Rank(List<RankEntry> rankEntries, YearMonth yearMonth) {
		this();
		this.rankEntries = rankEntries;
		this.yearMonth = yearMonth;
	}

	public String getSummary() {
		String pioneerDesc = "";
		if (pioneer != null) {
			pioneerDesc = String.format(" - 本月先锋: %s", pioneer);
		}
		return String.format("共%s人打卡%s", rankEntries.size(), pioneerDesc);
	}
	
	@Override
	public String toString() {
		return String.format("Rank [yearMonth=%s, rankEntries=%s, pioneer=%s]", yearMonth, rankEntries, pioneer);
	}

	public List<RankEntry> getRankEntries() {
		return rankEntries;
	}

	public void setRankEntries(List<RankEntry> rankEntries) {
		this.rankEntries = rankEntries;
	}

	public YearMonth getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(YearMonth yearMonth) {
		this.yearMonth = yearMonth;
	}

	public String getPioneer() {
		return pioneer;
	}

	public void setPioneer(String pioneer) {
		this.pioneer = pioneer;
	}

}
