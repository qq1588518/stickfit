package io.github.xinyangpan.core.standard;

import java.math.BigDecimal;

import io.github.xinyangpan.vo.RankEntry;

public class JogAmountOrMixStandard implements Standard {
	private final JogAmountStandard jogAmountStandard;
	private final MixedStandard mixedStandard;

	public JogAmountOrMixStandard(BigDecimal jogAmount, BigDecimal jogAmountMix, int count, int jogCount) {
		this.jogAmountStandard = new JogAmountStandard(jogAmount);
		this.mixedStandard = new MixedStandard(jogAmountMix, count, jogCount);
	}

	@Override
	public boolean eval(RankEntry rankEntry) {
		return jogAmountStandard.eval(rankEntry) || mixedStandard.eval(rankEntry);
	}

}
