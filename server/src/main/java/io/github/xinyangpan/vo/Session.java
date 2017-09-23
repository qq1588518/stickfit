package io.github.xinyangpan.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

//参数	说明
//openid	用户唯一标识
//session_key	会话密钥
//unionid	用户在开放平台的唯一标识符。本字段在满足一定条件的情况下才返回。具体参看UnionID机制说明
// {"errcode":43003,"errmsg":"require https, hints: [ req_id: MChChA0124th40 ]"}
public class Session {
	@JsonProperty("openid")
	private String openId;
	@JsonProperty("session_key")
	private String sessionKey;
	@JsonProperty("unionid")
	private String unionId;
	@JsonProperty("expires_in")
	private int expiresIn;
	// error
	private int errcode;
	private String errmsg;

	@Override
	public String toString() {
		return String.format("Session [openId=%s, sessionKey=%s, unionId=%s, expiresIn=%s, errcode=%s, errmsg=%s]", openId, sessionKey, unionId, expiresIn, errcode, errmsg);
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

	public int getErrcode() {
		return errcode;
	}

	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

}
