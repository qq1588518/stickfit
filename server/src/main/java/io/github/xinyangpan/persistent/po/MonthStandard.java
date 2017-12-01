package io.github.xinyangpan.persistent.po;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.github.xinyangpan.persistent.po.converter.StandardParamConverter;
import io.github.xinyangpan.persistent.po.converter.YearMonthConverter;
import io.github.xinyangpan.persistent.po.enums.StandardEnum;
import io.github.xinyangpan.persistent.po.type.StandardParam;
import io.github.xinyangpan.persistent.po.type.YearMonth;
import io.github.xinyangpan.service.standard.Standard;

@Entity
public class MonthStandard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long groupId;
	@Convert(converter = YearMonthConverter.class)
	private YearMonth month;
	private StandardEnum standardEnum;
	@Convert(converter = StandardParamConverter.class)
	private StandardParam standardParam;

	public Standard getStandard() {
		return this.standardEnum.getStandard(standardParam);
	}

	@Override
	public String toString() {
		return String.format("MonthStandard [id=%s, groupId=%s, month=%s, standardEnum=%s, standardParam=%s]", id, groupId, month, standardEnum, standardParam);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public YearMonth getMonth() {
		return month;
	}

	public void setMonth(YearMonth month) {
		this.month = month;
	}

	public StandardEnum getStandardEnum() {
		return standardEnum;
	}

	public void setStandardEnum(StandardEnum standardEnum) {
		this.standardEnum = standardEnum;
	}

	public StandardParam getStandardParam() {
		return standardParam;
	}

	public void setStandardParam(StandardParam standardParam) {
		this.standardParam = standardParam;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

}
