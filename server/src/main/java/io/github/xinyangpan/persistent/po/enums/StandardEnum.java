package io.github.xinyangpan.persistent.po.enums;

import java.math.BigDecimal;

import io.github.xinyangpan.persistent.po.type.StandardParam;
import io.github.xinyangpan.service.standard.JogAmountOrMixStandard;
import io.github.xinyangpan.service.standard.JogAmountStandard;
import io.github.xinyangpan.service.standard.MixedStandard;
import io.github.xinyangpan.service.standard.Standard;

public enum StandardEnum {
	JogAmount {
		@Override
		public Standard getStandard(StandardParam standardParam) {
			BigDecimal jogAmount = new BigDecimal(standardParam.getParam(0));
			return new JogAmountStandard(jogAmount);
		}
	},
	Mixed {
		@Override
		public Standard getStandard(StandardParam standardParam) {
			BigDecimal jogAmount = new BigDecimal(standardParam.getParam(0));
			int count = Integer.parseInt(standardParam.getParam(1));
			int jogCount = Integer.parseInt(standardParam.getParam(2));
			return new MixedStandard(jogAmount, count, jogCount);
		}
	},
	JogAmountOrMix {
		@Override
		public Standard getStandard(StandardParam standardParam) {
			BigDecimal jogAmount = new BigDecimal(standardParam.getParam(0));
			BigDecimal jogAmountMix = new BigDecimal(standardParam.getParam(1));
			int count = Integer.parseInt(standardParam.getParam(2));
			int jogCount = Integer.parseInt(standardParam.getParam(3));
			return new JogAmountOrMixStandard(jogAmount, jogAmountMix, count, jogCount);
		}
	};

	public abstract Standard getStandard(StandardParam standardParam);

}
