package com.william.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.william.dao.UserBankDao;
import com.william.dao.UserDao;
import com.william.exception.BusinessException;
import com.william.model.User;
import com.william.model.UserBank;
import com.william.model.dto.UserBankcardDTO;
import com.william.model.enum_.ErrorCodes;
import com.william.service.UserBankcardService;
import com.william.utils.BeanUtils;

@Service
public class UserBankcardServiceImpl implements UserBankcardService{
	@Autowired
	private UserDao userDao;
	@Autowired
	private UserBankDao userBankDao;
	@Override
	public void bindingBankcard(String userName, String bankcardNum) {
		User user = this.userDao.getUserByUserNo(userName, new Byte("1"));
		if(ObjectUtils.isEmpty(user)) {
			throw new BusinessException(ErrorCodes.USER_NOT_EXIST.getCode(), ErrorCodes.USER_NOT_EXIST.getMsg());
		}
		UserBank userBank = new UserBank(user.getId(), bankcardNum, "");
		this.userBankDao.save(userBank);
	}
	@Override
	public void update(String userName, String newBankCard, String oldBankCard) {
		User user = this.userDao.getUserByUserNo(userName, new Byte("1"));
		if(ObjectUtils.isEmpty(user)) {
			throw new BusinessException(ErrorCodes.USER_NOT_EXIST.getCode(), ErrorCodes.USER_NOT_EXIST.getMsg());
		}
		UserBank userBank = this.userBankDao.findUserBankcard(user.getId(), oldBankCard);
		if(ObjectUtils.isEmpty(userBank)) {
			throw new BusinessException(ErrorCodes.USER_NOT_EXIST.getCode(), "用户绑定银行卡信息不存在");
		}
		userBank.setBankCode(newBankCard);
		this.userBankDao.update(userBank);
	}
	@Override
	public List<UserBankcardDTO> getBindingBankcardInfo(String userName) {
		User user = this.userDao.getUserByUserNo(userName, new Byte("1"));
		if(ObjectUtils.isEmpty(user)) {
			throw new BusinessException(ErrorCodes.USER_NOT_EXIST.getCode(), ErrorCodes.USER_NOT_EXIST.getMsg());
		}
		List<UserBank> list = this.userBankDao.findByUserId(user.getId());
		List<UserBankcardDTO> userBankList = BeanUtils.copyAs(list, UserBankcardDTO.class);
		return userBankList;
	}
}
