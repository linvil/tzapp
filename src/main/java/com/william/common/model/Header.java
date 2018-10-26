package com.william.common.model;

import com.alibaba.fastjson.JSON;
import com.william.utils.HashUtils;

/**
 * <pre>
 * <b>报文头.</b>
 * <b>Description:</b> 
 *    消息传输公共信息
 * </pre>
 */
public final class Header extends Base {

	/**  
	 * @Fields serialVersionUID : 
	 */ 
	private static final long serialVersionUID = 1L;

	/** 请求流水号码 	      必填 **/
	private String reqSeqNo;
	
	/** 当前渠道号		   必填 **/
	private String reqChannel;
	
	private String desChannel;
	
	/** 客户端请求目标时间	   必填		calendar.getTimeInMillis() **/
	private Long   reqTime = 0L;
	
	/** 服务端处理完成时间    选填	calendar.getTimeInMillis() **/
	private Long   desTime = 0L;
	
	private String tokenId;
	
	/** 校验数据是否篡改           **/
	private String mac;
	
	/** 备用字段1                            选填 **/
	private String rsv1;
	
	
	public String getReqSeqNo() {
		return reqSeqNo;
	}

	public void setReqSeqNo(String reqSeqNo) {
		this.reqSeqNo = reqSeqNo;
	}

	public String getReqChannel() {
		return reqChannel;
	}

	public void setReqChannel(String reqChannel) {
		this.reqChannel = reqChannel;
	}

	public String getDesChannel() {
		return desChannel;
	}

	public void setDesChannel(String desChannel) {
		this.desChannel = desChannel;
	}

	public Long getReqTime() {
		return reqTime;
	}

	public void setReqTime(Long reqTime) {
		this.reqTime = reqTime;
	}

	public Long getDesTime() {
		return desTime;
	}

	public void setDesTime(Long desTime) {
		this.desTime = desTime;
	}

	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getRsv1() {
		return rsv1;
	}

	public void setRsv1(String rsv1) {
		this.rsv1 = rsv1;
	}

	public Header(String reqSeqNo, String reqChannel, String desChannel, Long reqTime, Long desTime, String tokenId,
			Body body, String rsv1) {
		super();
		this.reqSeqNo = reqSeqNo;
		this.reqChannel = reqChannel;
		this.desChannel = desChannel;
		this.reqTime = reqTime;
		this.desTime = desTime;
		this.tokenId = tokenId;
		this.mac = HashUtils.getSha1(JSON.toJSONString(body).getBytes());
		this.rsv1 = rsv1;
	}

	public Header(String reqSeqNo, String reqChannel, String desChannel, Long reqTime, Long desTime, String tokenId,
			String mac, String rsv1) {
		super();
		this.reqSeqNo = reqSeqNo;
		this.reqChannel = reqChannel;
		this.desChannel = desChannel;
		this.reqTime = reqTime;
		this.desTime = desTime;
		this.tokenId = tokenId;
		this.mac = mac;
		this.rsv1 = rsv1;
	}

	public Header() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
