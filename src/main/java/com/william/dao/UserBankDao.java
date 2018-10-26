package com.william.dao;

import java.util.List;

import com.william.model.UserBank;

public interface UserBankDao extends DaoSupport{
	UserBank findUserBankcard(Integer userId,String bankCard);
	List<UserBank> findByUserId(Integer UserId);
}
