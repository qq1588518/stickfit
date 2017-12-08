package io.github.xinyangpan.persistent.po.converter;

import javax.persistence.AttributeConverter;

import io.github.xinyangpan.persistent.po.type.YearMonth;

public class YearMonthConverter implements AttributeConverter<YearMonth, Integer> {

	@Override
	public Integer convertToDatabaseColumn(YearMonth attribute) {
		return attribute.getIntValue();
	}

	@Override
	public YearMonth convertToEntityAttribute(Integer dbData) {
		return YearMonth.of(dbData);
	}

}
