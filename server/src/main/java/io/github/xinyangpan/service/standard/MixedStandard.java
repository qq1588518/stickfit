package io.github.xinyangpan.service.standard;

import java.math.BigDecimal;

import io.github.xinyangpan.vo.RankEntry;

public class MixedStandard implements Standard {
	private BigDecimal jogAmount;
	private int count;
	private int jogCount;

	public MixedStandard(BigDecimal jogAmount, int count, int jogCount) {
		super();
		this.jogAmount = jogAmount;
		this.count = count;
		this.jogCount = jogCount;
	}

	@Override
	public boolean eval(RankEntry rankEntry) {
		return rankEntry.getJogAmount().compareTo(jogAmount) >= 0 && rankEntry.getCount() >= count && rankEntry.getJogCount() >= jogCount;
	}

}
