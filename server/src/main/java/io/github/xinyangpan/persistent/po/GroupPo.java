package io.github.xinyangpan.persistent.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class GroupPo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@NotNull
	private String name;
	private String password;
	@Column(unique = true)
	private long ownerId;
	@Override
	public String toString() {
		return String.format("GroupPo [id=%s, name=%s, password=%s, ownerId=%s]", id, name, password, ownerId);
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

	public long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(long owner) {
		this.ownerId = owner;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}
