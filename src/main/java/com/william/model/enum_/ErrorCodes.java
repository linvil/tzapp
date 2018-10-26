package com.william.model.enum_;

public enum ErrorCodes {
	INFORMATION_NOT_EXIST(103,"该信息不存在"),
	
	USER_EXIST(114,"该用户信息已经存在"),
	
	USER_TOKEN_EXPIRE(101,"登录超时,请重新登录"),
	
	USER_NOT_EXIST(104,"用户不存在"),
	
	USER_MISTAKE(102,"用户名或密码错误");
	
	private String msg;
	private int code;
	private ErrorCodes( int code , String msg) {
		this.msg = msg;
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
}
