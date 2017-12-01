package io.github.xinyangpan.core.standard;

import io.github.xinyangpan.vo.RankEntry;

public class CountStandard implements Standard {
	private int count;

	public CountStandard(int count) {
		this.count = count;
	}

	@Override
	public boolean eval(RankEntry rankEntry) {
		return rankEntry.getCount() >= count;
	}

}
