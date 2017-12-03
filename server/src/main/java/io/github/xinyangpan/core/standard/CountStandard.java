package io.github.xinyangpan.core.standard;

import io.github.xinyangpan.vo.RankEntry;

public class CountStandard implements Standard {
	private final int count;

	public CountStandard(int count) {
		this.count = count;
	}

	@Override
	public boolean eval(RankEntry rankEntry) {
		return rankEntry.getCount() >= count;
	}

	@Override
	public String toString() {
		return String.format("CountStandard [count=%s]", count);
	}

}
