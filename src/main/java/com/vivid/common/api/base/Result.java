package com.vivid.common.api.base;

import java.io.Serializable;

import com.vivid.common.enums.PlatformCodeEnum;



public class Result<T> implements Serializable {

	private static final long serialVersionUID = 1L;


	private Status status = Status.OK;

	public enum Status {
		OK, ERROR 
	}

	/**
	 *返回�?
	 */
	private String code = PlatformCodeEnum.SUCCESS.getCode();

	/**
	 * 提示信息
	 */
	private String message = PlatformCodeEnum.SUCCESS.getValue();

	/**
	 * 数据对象
	 */
	private  T data;

	/**
	 * 时间�?
	 */
	private Long date = System.currentTimeMillis();


	public static <T> Result<T> ok() {
		return new Result<T>();
	}

	public static <T> Result<T> ok(String message) {
		Result<T> result = new Result<T>();
		result.setMessage(message);
		return result;
	}

	public static <T> Result<T> ok(PlatformCodeEnum code, T data) {
		Result<T> result = new Result<T>();
		result.setCode(code.getCode());
		result.setMessage(code.getValue());
		result.setData(data);
		return result;
	}

	public static <T> Result<T> ok(T data) {
		Result<T> result = new Result<T>();
		result.setMessage(PlatformCodeEnum.SUCCESS.getValue());
		result.setCode(PlatformCodeEnum.SUCCESS.getCode());
		result.setData(data);
		return result;
	}

	public static <T> Result<T> ok(String message,T data) {
		Result<T> result = new Result<T>();
		result.setMessage(message);
		result.setData(data);
		return result;
	}

	public static <T> Result<T> error(String message) {
		Result<T> result = new Result<T>();
		result.setStatus(Status.ERROR);
		result.setCode(PlatformCodeEnum.SYSTEM_ERROR.getCode());
		result.setMessage(message);
		return result;
	}
	
	public static <T> Result<T> unauth(String message) {
		Result<T> result = new Result<T>();
		result.setStatus(Status.ERROR);
		result.setCode(PlatformCodeEnum.TOKEN_ERROR.getCode());
		result.setMessage(message);
		return result;
	}

	public static <T> Result<T> error(String code,String message) {
		Result<T> result = new Result<T>();
		result.setStatus(Status.ERROR);
		result.setCode(code);
		result.setMessage(message);
		return result;
	}

	public static <T> Result<T> error(PlatformCodeEnum code) {
		Result<T> result = new Result<T>();
		result.setStatus(Status.ERROR);
		result.setCode(code.getCode());
		result.setMessage(code.getValue());
		return result;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}
	
	

}
