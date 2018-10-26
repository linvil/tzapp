package com.william.common.model;

import java.io.Serializable;

/**
 * <pre>
 * <b>公共返回对象.</b>
 * <b>Description:</b> 
 *    包含返回的公共信息（Header）、返回参数（Body）、成功标示（Footer）
 * </pre>
 */
public final class RPCResult implements Serializable {

	/**  
	 * @Fields serialVersionUID : TODO
	 */ 
	private static final long serialVersionUID = 1L;

	/** 请求公共参数 		  必填 **/
	private Header header = new Header();
	
	/** 返回参数信息 		  选填 **/
	private Body body;
	
	/** 返回状态对象 		  必填 **/
	private Footer footer = new Footer();

	/**
	 *<pre>
	 *<b>构造方法.</b>
	 *<b>Description:</b> 
	 *    
	 *</pre>
	 */
	public RPCResult(){
		super();
	}
	
	/**
	 *<pre>
	 *<b>构造方法.</b>
	 *<b>Description:</b> 
	 *    
	 *@param body 报文体
	 *</pre>
	 */
	public RPCResult(Body body) {
		super();
		this.body = body;
	}
	
	/**
	 *<pre>
	 *<b>构造方法.</b>
	 *<b>Description:</b> 
	 *    
	 *@param header 返回报文头
	 *@param body 返回报文体
	 *</pre>
	 */
	public RPCResult(Header header, Body body) {
		super();
		this.header = header;
		this.body = body;
	}
	
	/**
	 *<pre>
	 *<b>构造方法.</b>
	 *<b>Description:</b> 
	 *    
	 *@param header 返回报文头
	 *@param footer 操作标识
	 *</pre>
	 */
	public RPCResult(Header header,Footer footer){
		super();
		this.header=header;
		this.footer=footer;
	}
	
	/**
	 *<pre>
	 *<b>构造方法.</b>
	 *<b>Description:</b> 
	 *    
	 *@param header 返回报文头
	 *@param body 返回报文体
	 *@param footer 操作标识
	 *</pre>
	 */
	public RPCResult(Header header,Body body,Footer footer){
		super();
		this.header=header;
		this.body=body;
		this.footer=footer;
	}
	
	/**
	 *<pre>
	 *<b>获取返回报文头.</b>
	 *<b>Description:</b> 
	 *    
	 *@return Header
	 *</pre>
	 */
	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	/**
	 *<pre>
	 *<b>返回报文体.</b>
	 *<b>Description:</b> 
	 *    
	 *@return Body
	 *</pre>
	 */
	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	/**
	 *<pre>
	 *<b>返回操作标识.</b>
	 *<b>Description:</b> 
	 *    
	 *@return Footer
	 *</pre>
	 */
	public Footer getFooter() {
		return footer;
	}

	public void setFooter(Footer footer) {
		this.footer = footer;
	}
	
}
