package com.william.model;

public final class ResponseBean {

	private int status = 0;
	private Object data;
	private String error = "";

	public ResponseBean() {
	}

	public ResponseBean(Object data) {
		this.data = data;
	}

	public ResponseBean(Object data, int status) {
		this.data = data;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
