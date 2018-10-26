package com.william.common.model;

/**
 * <pre>
 * <b>报文体.</b>
 * <b>Description:</b> 
 *    返回结果集统一封装类，可以为任何数据类型
 * </pre>
 */
public final class Body extends Base {

	/**  
	 * @Fields serialVersionUID : TODO
	 */ 
	private static final long serialVersionUID = 1L;

	/** 请求参数或者返回参数 	选填 ***/
	private Object paraORrest;
	/** 分页符合条件总数 	选填***/
	private long   totalCount;

	public Body() {
		super();
	}
	
	/**
	 *<pre>
	 *<b>构造方法.</b>
	 *<b>Description:</b> 
	 *@param paraORrest
	 *</pre>
	 */
	public Body(Object paraORrest) {
		super();
		this.paraORrest = paraORrest;
	}

	/**
	 *<pre>
	 *<b>构造方法.</b>
	 *<b>Description:</b> 
	 *    
	 *@param paraORrest
	 *@param totalCount
	 *</pre>
	 */
	public Body(Object paraORrest, long totalCount) {
		super();
		this.paraORrest = paraORrest;
		this.totalCount = totalCount;
	}
	
	/**
	 *<pre>
	 *<b>返回参数.</b>
	 *<b>Description:</b> 
	 *    被调用者返回参数结果集，可以为任意对象。
	 *@return Object
	 *</pre>
	 */
	public Object getParaORrest() {
		return paraORrest;
	}

	public void setParaORrest(Object paraORrest) {
		this.paraORrest = paraORrest;
	}

	/**
	 *<pre>
	 *<b>总数.</b>
	 *<b>Description:</b> 
	 *    符合查询条件的总数
	 *@return long
	 *</pre>
	 */
	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	
	
}
