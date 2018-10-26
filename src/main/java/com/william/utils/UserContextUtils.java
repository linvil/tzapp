package com.william.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.william.model.dto.UserDTO;
import com.william.service.UserService;

@Component
public class UserContextUtils {

	private static ThreadLocal<UserDTO> users = new ThreadLocal<>();
	//private static ThreadLocal<AdminDTO> admins = new ThreadLocal<>();

	private static UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		UserContextUtils.userService = userService;
	}

	public static UserDTO getUser(String tokenId) {
		UserDTO user = userService.getUserByTokenId(tokenId);
		users.set(user);
		return user;
	}

	public static void setUser(UserDTO userDTO) {
		users.set(userDTO);
	}

	public static UserDTO getCurrentUser() {
		return users.get();
	}

	public static Integer getCurrentUserId() {
		UserDTO userDTO = getCurrentUser();

		return userDTO == null ? null : userDTO.getId();
	}

	/*public static void setAdmin(AdminDTO adminDTO) {
		admins.set(adminDTO);
	}

	public static AdminDTO getCurrentAdmin() {
		return admins.get();
	}
	

	public static Integer getCurrentAdminId() {
		AdminDTO adminDTO = getCurrentAdmin();

		return adminDTO == null ? null : adminDTO.getId();
	}
*/

}
