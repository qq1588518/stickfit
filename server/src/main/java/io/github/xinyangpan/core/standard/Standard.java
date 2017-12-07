package io.github.xinyangpan.core.standard;

import java.math.BigDecimal;

import io.github.xinyangpan.persistent.po.MonthStandard;
import io.github.xinyangpan.vo.RankEntry;

public interface Standard {
	public static final JogAmountOrMixStandard DEFAULT_STANDARD = new JogAmountOrMixStandard(new BigDecimal("180"), new BigDecimal("110"), 15, 9);

	boolean eval(RankEntry rankEntry);

	public static Standard getStandard(MonthStandard monthStandard) {
		if (monthStandard == null) {
			return DEFAULT_STANDARD;
		} else {
			return monthStandard.getStandard();
		}
	}

}
