package io.github.xinyangpan.persistent.po;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import io.github.xinyangpan.core.StatusEnum;
import io.github.xinyangpan.persistent.po.converter.YearMonthConverter;
import io.github.xinyangpan.persistent.po.type.YearMonth;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "customerId", "month" }))
public class CustomerStatusPo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private long customerId;
	private long groupId;
	@Convert(converter = YearMonthConverter.class)
	private YearMonth month;
	@Enumerated(EnumType.STRING)
	private StatusEnum statusEnum;

	@Override
	public String toString() {
		return String.format("CustomerStatusPo [id=%s, customerId=%s, groupId=%s, month=%s, statusEnum=%s]", id, customerId, groupId, month, statusEnum);
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

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public YearMonth getMonth() {
		return month;
	}

	public void setMonth(YearMonth month) {
		this.month = month;
	}

	public StatusEnum getStatusEnum() {
		return statusEnum;
	}

	public void setStatusEnum(StatusEnum statusEnum) {
		this.statusEnum = statusEnum;
	}

}
