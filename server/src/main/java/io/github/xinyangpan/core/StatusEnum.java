package io.github.xinyangpan.core;

public enum StatusEnum {
	LEAVE;

	public static boolean isLeave(StatusEnum statusEnum) {
		return statusEnum == StatusEnum.LEAVE;
	}

}
