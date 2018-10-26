package com.william.exception;

public class BusinessException extends FrameworkException {

	private int code;

	private static final long serialVersionUID = 1L;

	public BusinessException() {
		super();
	}

	public BusinessException(int code, String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		this.code = code;
	}

	public BusinessException(int code, String arg0, Throwable arg1) {
		super(arg0, arg1);
		this.code = code;
	}

	public BusinessException(int code, String arg0) {
		super(arg0);
		this.code = code;
	}

	public BusinessException(Throwable arg0) {
		super(arg0);
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}
