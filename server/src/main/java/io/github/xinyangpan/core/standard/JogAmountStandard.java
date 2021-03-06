package io.github.xinyangpan.core.standard;

import java.math.BigDecimal;

import io.github.xinyangpan.vo.RankEntry;

public class JogAmountStandard implements Standard {
	private final BigDecimal jogAmount;

	public JogAmountStandard(BigDecimal jogAmount) {
		this.jogAmount = jogAmount;
	}

	@Override
	public boolean eval(RankEntry rankEntry) {
		return rankEntry.getJogAmount().compareTo(jogAmount) >= 0;
	}

	@Override
	public String toString() {
		return String.format("JogAmountStandard [jogAmount=%s]", jogAmount);
	}

}
