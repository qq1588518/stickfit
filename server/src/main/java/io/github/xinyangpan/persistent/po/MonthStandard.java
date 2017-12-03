package io.github.xinyangpan.persistent.po;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.github.xinyangpan.core.standard.Standard;
import io.github.xinyangpan.core.standard.StandardEnum;
import io.github.xinyangpan.persistent.po.converter.StandardParamConverter;
import io.github.xinyangpan.persistent.po.converter.YearMonthConverter;
import io.github.xinyangpan.persistent.po.type.StandardParam;
import io.github.xinyangpan.persistent.po.type.YearMonth;

@Entity
public class MonthStandard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long groupId;
	@Convert(converter = YearMonthConverter.class)
	private YearMonth month;
	@Enumerated(EnumType.STRING)
	private StandardEnum standardEnum;
	@Convert(converter = StandardParamConverter.class)
	private StandardParam standardParam;
	private long pioneerId;

	public Standard getStandard() {
		return this.standardEnum.getStandard(standardParam);
	}

	@Override
	public String toString() {
		return String.format("MonthStandard [id=%s, groupId=%s, month=%s, standardEnum=%s, standardParam=%s, pioneerId=%s]", id, groupId, month, standardEnum, standardParam, pioneerId);
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

	public long getPioneerId() {
		return pioneerId;
	}

	public void setPioneerId(long pioneerId) {
		this.pioneerId = pioneerId;
	}

}
