package io.github.xinyangpan.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

//参数	说明
//openid	用户唯一标识
//session_key	会话密钥
//unionid	用户在开放平台的唯一标识符。本字段在满足一定条件的情况下才返回。具体参看UnionID机制说明
@JsonInclude(Include.NON_NULL)
public class Session {
	@JsonProperty("openid")
	private String openId;
	@JsonProperty("session_key")
	private String sessionKey;
	@JsonProperty("unionid")
	private String unionId;
	@JsonProperty("expires_in")
	private int expiresIn;
	

	@Override
	public String toString() {
		return String.format("Session [openId=%s, sessionKey=%s, unionId=%s, expiresIn=%s]", openId, sessionKey, unionId, expiresIn);
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}

}
