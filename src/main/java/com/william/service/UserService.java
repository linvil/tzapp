package com.william.service;

import com.william.model.dto.UserDTO;

public interface UserService {

	UserDTO rucRegister(UserDTO userDTO);
	
	UserDTO loginToken(UserDTO loginInfo);

	UserDTO getUserByTokenId(String tokenId);
	
	void getBackPwd(String userName,String newPassword);

	void getBackPayPwd(String userName,String newPayPassword);
	
	UserDTO updateNickname(String userName,String nickName);
	
	UserDTO updateHeadIamge(String userName,String headImageUrl);
	
	void updateUserInfo(UserDTO userDTO);
	
	UserDTO getUserByUserName(String UserName);

}
