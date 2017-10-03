package io.github.xinyangpan.core;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;

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

	// yyyyMM
	public static int getYearMonth() {
		Calendar instance = Calendar.getInstance();
		String yyyyMM = DateFormatUtils.format(instance, "yyyyMM");
		return Integer.parseInt(yyyyMM);
	}

	// yyyyMM
	public static int getYearMonth(Date date) {
		Calendar instance = Calendar.getInstance();
		instance.setTime(date);
		String yyyyMM = DateFormatUtils.format(instance, "yyyyMM");
		return Integer.parseInt(yyyyMM);
	}
	
	public static int getYearMonth(Integer year, Integer month) {
		if (year == null || month == null) {
			return getYearMonth();
		}
		return year * 100 + month;
	}

	public static void main(String[] args) {
		int yearMonth = getYearMonth(new Date());
		System.out.println(yearMonth);
	}

}
