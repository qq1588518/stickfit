package io.github.xinyangpan.persistent.po;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class ExerciseTypePo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotNull
	private String name;
	@NotNull
	private String description;
	@NotNull
	private String unit;
	@NotNull
	private BigDecimal min;
	@NotNull
	@Column(name="`order`")
	private int order;
	
	public ExerciseTypePo(String name, String description, String unit, BigDecimal min, int order) {
		super();
		this.name = name;
		this.description = description;
		this.unit = unit;
		this.min = min;
		this.order = order;
	}

	@Override
	public String toString() {
		return String.format("ExerciseTypePo [id=%s, name=%s, description=%s, unit=%s, min=%s, order=%s]", id, name, description, unit, min, order);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public BigDecimal getMin() {
		return min;
	}

	public void setMin(BigDecimal min) {
		this.min = min;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

}
