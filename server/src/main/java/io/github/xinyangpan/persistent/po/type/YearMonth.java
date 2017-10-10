package io.github.xinyangpan.persistent.po.type;

import java.time.LocalDate;
import java.util.Date;

public class YearMonth {
	private int year;
	private int month;

	public YearMonth(int year, int month) {
		this.year = year;
		this.month = month;
	}

	public YearMonth(int yearMonth) {
		year = yearMonth / 100;
		month = yearMonth - year * 100;
	}
	
	public static YearMonth of(Date date) {
		return of(LocalDate.from(date.toInstant()));
	}
	
	public static YearMonth of(LocalDate localDate) {
		return new YearMonth(localDate.getYear(), localDate.getMonthValue());
	}
	
	public static YearMonth now() {
		LocalDate localDate = LocalDate.now();
		return new YearMonth(localDate.getYear(), localDate.getMonthValue());
	}

	public int toInt() {
		return year * 100 + month;
	}

	@Override
	public String toString() {
		return String.format("YearMonth [year=%s, month=%s]", year, month);
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

}
