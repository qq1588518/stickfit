package io.github.xinyangpan.core.standard;

import java.math.BigDecimal;

import io.github.xinyangpan.persistent.po.type.StandardParam;

public enum StandardEnum {
	Count {
		@Override
		public Standard getStandard(StandardParam standardParam) {
			int count = Integer.parseInt(standardParam.getParam(0));
			return new CountStandard(count);
		}
	},
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
