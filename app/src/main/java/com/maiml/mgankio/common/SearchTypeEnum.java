package com.maiml.mgankio.common;

/**
 * 
 * @author mingliang.mai
 * 
 * 查询status的枚举
 */
public enum SearchTypeEnum {

	OLD("0","加载更多"),
	
	
	NEW("1", "下拉刷新");

	private String code;
	private String message;

	private SearchTypeEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
