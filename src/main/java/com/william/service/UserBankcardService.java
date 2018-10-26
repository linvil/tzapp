package com.william.service;

import java.util.List;

import com.william.model.dto.UserBankcardDTO;

public interface UserBankcardService {
	void bindingBankcard(String userName,String bankcardNum);
	void update(String userName,String newBankCard,String oldBankCard);
	List<UserBankcardDTO> getBindingBankcardInfo(String userName);
}
