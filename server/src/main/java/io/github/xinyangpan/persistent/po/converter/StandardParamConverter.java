package io.github.xinyangpan.persistent.po.converter;

import javax.persistence.AttributeConverter;

import io.github.xinyangpan.persistent.po.type.StandardParam;

public class StandardParamConverter implements AttributeConverter<StandardParam, String> {

	@Override
	public String convertToDatabaseColumn(StandardParam attribute) {
		return attribute.toDbValue();
	}

	@Override
	public StandardParam convertToEntityAttribute(String dbData) {
		return new StandardParam(dbData);
	}

}
