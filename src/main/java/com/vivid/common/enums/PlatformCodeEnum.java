package com.vivid.common.enums;


public enum PlatformCodeEnum {

	SUCCESS("10000", "SUCCESS"),
	
	SAVE_ERROR("10001", "SAVE FAILED"),

	DELETE_ERROR("10002", "DELETE FAILED"),

	PARAM_IS_NULL("10003", "PARAM IS NULL"), 
	
	RESULT_IS_NULL("10086", "RESULT IS NULL"),

	SYSTEM_ERROR("90000", "SYSTEM ERROR"),

	TOKEN_EXPIRED("99997","TOKEN EXPIRED"),
	
	TOKEN_IS_NULL("99998","TOKEN IS NULL"),
	
	TOKEN_ERROR("99999","TOKEN INVALID");
	

	private String code;

	private String value;

	PlatformCodeEnum(String code, String value) {
		this.code = code;
		this.value = value;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	

}
