package com.william.common.model;

/**
 * <pre>
 * <b>结果对象.</b>
 * <b>Description:</b> 
 *    执行一个远程操作，返回是否成功标识，以及正确/错误码。
 * </pre>
 */
public final class Footer extends Base {

	/**  
	 * @Fields serialVersionUID : TODO
	 */ 
	private static final long serialVersionUID = 1L;

	/** 业务处理完成返回状态码          必填 **/
	private String  msg;
	/** 业务处理完成返回状态		必填 **/
	private String status;

	/**
	 *<pre>
	 *<b>构造方法.</b>
	 *<b>Description:</b> 
	 *    
	 *</pre>
	 */
	public Footer() {
		super();
	}

	/**
	 *<pre>
	 *<b>构造方法.</b>
	 *<b>Description:</b> 
	 *    
	 *@param respCode
	 *</pre>
	 */
	public Footer(String status) {
		super();
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Footer(String msg, String status) {
		super();
		this.msg = msg;
		this.status = status;
	}

}
