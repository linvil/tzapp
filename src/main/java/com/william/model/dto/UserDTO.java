package com.william.model.dto;

import com.william.model.User;

public class UserDTO extends User{
	
	public static final Byte STATUS_OK = (byte)1;												// 正常状态
	public static final Byte STATUS_LOCK = (byte)0;    											// 锁定
	
	public static final Byte CLIENTTYPE_WEB = (byte)1; 											// web登录
	public static final Byte CLIENTTYPE_IOS = (byte)2;    										// IOS用户登录
	public static final Byte CLIENTTYPE_ANDROID = (byte)3; 										// 安卓用户登录
	public static final Byte CLIENTTYPE_WX = (byte)4;    										// 微信登录
	
	private String tokenId;

	private Byte clientType;
	
	
	
	public Byte getClientType() {
		return clientType;
	}

	public void setClientType(Byte clientType) {
		this.clientType = clientType;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

}
