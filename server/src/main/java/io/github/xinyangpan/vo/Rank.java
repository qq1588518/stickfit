package io.github.xinyangpan.vo;

import java.util.List;

import io.github.xinyangpan.persistent.po.type.YearMonth;

public class Rank {
	private List<RankEntry> rankEntries;
	private YearMonth yearMonth;
	private String summary;
	
	public Rank() {
	}
	
	public Rank(List<RankEntry> rankEntries, YearMonth yearMonth) {
		super();
		this.rankEntries = rankEntries;
		this.yearMonth = yearMonth;
		this.summary = String.format("共%s人打卡", rankEntries.size());
	}

	@Override
	public String toString() {
		return String.format("Rank [rankEntries=%s, yearMonth=%s, summary=%s]", rankEntries, yearMonth, summary);
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

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

}
