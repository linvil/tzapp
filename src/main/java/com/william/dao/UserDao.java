package com.william.dao;

import java.util.List;

import com.william.model.Token;
import com.william.model.User;

public interface UserDao extends DaoSupport{
	/**
	 * 用户是否存在
	 * @param id
	 * @param userNo
	 * @param status
	 * @return
	 */
	boolean checkUserNoUniqueness(Integer id, String userNo, Byte status);
	
	boolean checkUserNoUniqueness(String userName , int status);
	
	/**
	 * 获取usertokenId列表
	 * @param userId
	 * @param clientTypes
	 * @param delExpire
	 * @return
	 */
	List<Token> listUserTokens(Integer userId, Byte[] clientTypes, boolean delExpire);
	/**
	 * 根据username获取用户
	 * @param loginName
	 * @param status
	 * @return
	 */
	User getUserByUserNo(String loginName, Byte status);
	/** 
	 * 通过tokenId获取用户
	 * @param tokenId
	 * @return
	 */
	Token getUserTokenByTokenId(String tokenId);
	/**
	 * 更新登录密码
	 * @param userName
	 * @param passWord
	 */
	void updatePwd(String userName,String passWord);
	/**
	 * 更新支付密码
	 * @param userName
	 * @param payPassWord
	 */
	void updatePayPassword(String userName,String payPassWord,String saltPay);
	
	/**
	 * 更新昵称
	 * @param userName
	 * @param nickName
	 */
	void updateNickname(String userName,String nickName);
}
