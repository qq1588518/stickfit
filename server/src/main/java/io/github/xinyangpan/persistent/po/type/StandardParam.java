package io.github.xinyangpan.persistent.po.type;

public class StandardParam {
	private final String[] params;

	public StandardParam(String paramString) {
		params = paramString.split(",");
	}

	public String toDbValue() {
		return String.join(",", params);
	}

	/**
	 * starting from 0
	 * 
	 * @param index
	 * @return
	 */
	public String getParam(int index) {
		return params[index];
	}

}
