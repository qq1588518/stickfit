package io.github.xinyangpan.core;

import java.util.Calendar;
import java.util.Date;

public class CoreUtils {
	
	// 1 -> JAN
	public static int getMonth(Date date) {
		Calendar instance = Calendar.getInstance();
		instance.setTime(date);
		return instance.get(Calendar.MONTH) + 1;
	}
	
	public static int getMonth() {
		Calendar instance = Calendar.getInstance();
		return instance.get(Calendar.MONTH) + 1;
	}
	
}
