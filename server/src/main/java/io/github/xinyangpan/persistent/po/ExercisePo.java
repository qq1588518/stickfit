package io.github.xinyangpan.persistent.po;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.github.xinyangpan.persistent.po.converter.YearMonthConverter;
import io.github.xinyangpan.persistent.po.type.YearMonth;

@Entity
public class ExercisePo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long customerId;
	private long groupId;
	private long typeId;
	@Convert(converter = YearMonthConverter.class)
	private YearMonth month;
	private Date time;
	private BigDecimal amount;

	@Override
	public String toString() {
		return String.format("ExercisePo [id=%s, customerId=%s, groupId=%s, typeId=%s, month=%s, time=%s, amount=%s]", id, customerId, groupId, typeId, month, time, amount);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public long getTypeId() {
		return typeId;
	}

	public void setTypeId(long typeId) {
		this.typeId = typeId;
	}

	public YearMonth getMonth() {
		return month;
	}

	public void setMonth(YearMonth month) {
		this.month = month;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
		this.month = YearMonth.of(time);
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

}
