package com.william.service.impl;

import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.gzh.auth.TokenUtils;
import com.william.dao.UserDao;
import com.william.exception.BusinessException;
import com.william.model.ResponseBean;
import com.william.model.Token;
import com.william.model.User;
import com.william.model.dto.UserDTO;
import com.william.model.enum_.ErrorCodes;
import com.william.service.UserService;
import com.william.utils.BeanUtils;
import com.william.utils.DateUtils;
import com.william.utils.ErrorUtil;
import com.william.utils.HashUtils;
import com.william.utils.MathUtils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao userDao;
	@Resource(name = "tokenCache")
	private Cache tokenCache;
	
	@Override
	public UserDTO rucRegister(UserDTO userDTO) {
		//注册
		User user = registerInternal(userDTO);
		UserDTO newUserDTO = BeanUtils.copyAs(user, UserDTO.class);
		newUserDTO.setClientType(userDTO.getClientType());
		Token token = procToken(newUserDTO, false);
		return newUserDTO;
	}
	private Token procToken(UserDTO userDTO, boolean deleteOld) {

		Integer userId = userDTO.getId();
		
		Byte clientType = userDTO.getType();

		if (null == clientType || UserDTO.CLIENTTYPE_WX.equals(clientType)) {
			return null;
		}
		String tokenId = TokenUtils.getTokenId();

		Token userToken = new Token();
		userToken.setCreateTime(new Date());

		if (deleteOld) {
			// 删除原令牌
			Byte[] clientTypes = null;
			if (UserDTO.CLIENTTYPE_IOS.equals(clientType) 
					|| UserDTO.CLIENTTYPE_WX.equals(clientType)
					|| UserDTO.CLIENTTYPE_ANDROID.equals(clientType) 
					) {

				userToken.setExpireTime(DateUtils.addDays(userToken.getCreateTime(), 29));

				clientTypes = new Byte[3];
				clientTypes[0] = UserDTO.CLIENTTYPE_IOS;
				clientTypes[1] = UserDTO.CLIENTTYPE_ANDROID;
				clientTypes[2] = UserDTO.CLIENTTYPE_WX;
			} else if (UserDTO.CLIENTTYPE_WEB.equals(clientType) 
					) {


				userToken.setExpireTime(com.william.utils.DateUtils.addDays(userToken.getCreateTime(), 29));

				clientTypes = new Byte[1];
				clientTypes[0] = UserDTO.CLIENTTYPE_WEB;
			}

			List<Token> userTokens = userDao.listUserTokens(userId, clientTypes, true);
			for (Token item : userTokens) {
				userDao.delete(item);
				tokenCache.remove(item.getTokenId());
			}
		}else {
			userToken.setExpireTime(com.william.utils.DateUtils.addDays(userToken.getCreateTime(), 29));
		}

		// 保存令牌
		System.out.println(userToken.getExpireTime());
		userToken.setUserId(userId);
		userToken.setTokenId(tokenId);
		userToken.setClientType(clientType);

		userDao.save(userToken);

		// 添加到缓存
		userDTO.setTokenId(tokenId);
		//		userDTO.setUserState(0);			//TODO 该值什么时候赋予

		Element element = new Element(tokenId, userDTO);

		tokenCache.put(element);

		return userToken;
	}
	/**
	 * 注册
	 * @param userDTO
	 * @return
	 */
	private User registerInternal(UserDTO userDTO) {
		String username = userDTO.getUsername();
		User user = new User(true);
		String salt = MathUtils.getRandomString(20);
		userDTO.setType(userDTO.getClientType());
		BeanUtils.copyPropertiesIgnoreNull(userDTO, user);
		user.setLoginPassword((HashUtils.getMd5((userDTO.getLoginPassword() + salt).getBytes())));
		user.setSaltLogin(salt);
		userDao.save(user);
		
		if (StringUtils.hasText(username)) {
			boolean exist = userDao.checkUserNoUniqueness(user.getId(), username, null);
			if (exist) {
				throw new BusinessException(ErrorUtil.USER_EXIST, ErrorUtil.USER_EXIST_MSG);
			}
		}
		
		return user;
	}
	@Override
	public UserDTO loginToken(UserDTO loginInfo) {
		UserDTO userDTO = loginInternal(loginInfo);
		Token token = procToken(userDTO, true);
		return userDTO;
	}
	/**
	 * 登录
	 * @param loginInfo
	 * @return
	 */
	private UserDTO loginInternal(UserDTO loginInfo) {

		String loginName = loginInfo.getUsername();
		String password = loginInfo.getLoginPassword();
		User user = userDao.getUserByUserNo(loginName, null);
		if (user == null) {
			throw new BusinessException(ErrorUtil.USER_MISTAKE, ErrorUtil.USER_MISTAKE_MSG);
		}

		System.out.println(user.getLoginPassword());
		String pass = HashUtils.getMd5((password + user.getSaltLogin()).getBytes());
		if (!pass.equals(user.getLoginPassword())) {
			throw new BusinessException(ErrorUtil.USER_MISTAKE, ErrorUtil.USER_MISTAKE_MSG);
		}
		Byte clientType = loginInfo.getClientType();	

		UserDTO userDTO = BeanUtils.copyAs(user, UserDTO.class,"loginPassword","saltLogin","payPassword","saltPay","delTf","openId");
		userDTO.setClientType(clientType);

		return userDTO;
	}
	@Override
	public UserDTO getUserByTokenId(String tokenId) {
		Element element = tokenCache.get(tokenId);

		UserDTO userDTO;
		if (element == null) {
			Token userToken = userDao.getUserTokenByTokenId(tokenId);

			if (userToken == null) {
				return null;
			}

			User user = userDao.get(User.class, userToken.getId());

			if (user == null) {
				return null;
			}

			userDTO = BeanUtils.copyAs(user, UserDTO.class);
			element = new Element(tokenId, userDTO);
			tokenCache.put(element);
		} else {
			userDTO = (UserDTO) element.getObjectValue();
		}
		return userDTO;
	}
	@Override
	public void getBackPwd(String userName, String newPassword) {
		User user = this.userDao.getUserByUserNo(userName, new Byte("1"));
		if(ObjectUtils.isEmpty(user)) {
			throw new BusinessException(ErrorCodes.USER_NOT_EXIST.getCode(), ErrorCodes.USER_NOT_EXIST.getMsg());
		}
		this.userDao.updatePwd(userName, HashUtils.getMd5((newPassword + user.getSaltLogin()).getBytes()));
	}
	@Override
	public void getBackPayPwd(String userName, String newPayPassword) {
		User user = this.userDao.getUserByUserNo(userName, new Byte("1"));
		if(ObjectUtils.isEmpty(user)) {
			throw new BusinessException(ErrorCodes.USER_NOT_EXIST.getCode(), ErrorCodes.USER_NOT_EXIST.getMsg());
		}
		if(StringUtils.isEmpty(user.getPayPassword())) {
			String salt = MathUtils.getRandomString(20);
			this.userDao.updatePayPassword(userName, HashUtils.getMd5((newPayPassword + user.getSaltPay()).getBytes()), salt);
		}else {
			this.userDao.updatePayPassword(userName, HashUtils.getMd5((newPayPassword + user.getSaltPay()).getBytes()), null);
		}
	}
	@Override
	public UserDTO updateNickname(String userName, String nickName) {
		User user = this.userDao.getUserByUserNo(userName, new Byte("1"));
		if(ObjectUtils.isEmpty(user)) {
			throw new BusinessException(ErrorCodes.USER_NOT_EXIST.getCode(), ErrorCodes.USER_NOT_EXIST.getMsg());
		}
		this.userDao.updateNickname(userName, nickName);
		user.setNickName(nickName);
		UserDTO userDTO = BeanUtils.copyAs(user, UserDTO.class);
		userDTO.setPayPassword(null);
		userDTO.setLoginPassword(null);
		userDTO.setSaltLogin(null);
		userDTO.setSaltPay(null);
		return userDTO;
	}
	@Override
	public UserDTO updateHeadIamge(String userName, String headImageUrl) {
		User user = this.userDao.getUserByUserNo(userName, new Byte("1"));
		if(ObjectUtils.isEmpty(user)) {
			throw new BusinessException(ErrorCodes.USER_NOT_EXIST.getCode(), ErrorCodes.USER_NOT_EXIST.getMsg());
		}
		user.setAvatar(headImageUrl);
		user.setModifyTime(new Date(System.currentTimeMillis()));
		this.userDao.update(user);
		UserDTO userDTO = BeanUtils.copyAs(user, UserDTO.class);
		userDTO.setPayPassword(null);
		userDTO.setLoginPassword(null);
		userDTO.setSaltLogin(null);
		userDTO.setSaltPay(null);
		userDTO.setOpenId(null);
		return userDTO;
	}
	@Override
	public void updateUserInfo(UserDTO userDTO) {
		User user = this.userDao.getUserByUserNo(userDTO.getUsername(), new Byte("1"));
		if(ObjectUtils.isEmpty(user)) {
			throw new BusinessException(ErrorCodes.USER_NOT_EXIST.getCode(), ErrorCodes.USER_NOT_EXIST.getMsg());
		}
		user.setAvatar(userDTO.getAvatar());
		user.setBirthday(userDTO.getBirthday());
		user.setSex(userDTO.getSex());
		user.setAge(userDTO.getAge());
		user.setNickName(userDTO.getNickName());
		user.setFrozen(userDTO.getFrozen());
		user.setModifyTime(new Date(System.currentTimeMillis()));
		user.setEmail(userDTO.getEmail());
		this.userDao.update(user);
	}
	@Override
	public UserDTO getUserByUserName(String UserName) {
		User user = this.userDao.getUserByUserNo(UserName, new Byte("1"));
		if(ObjectUtils.isEmpty(user)) {
			return null;
		}
		UserDTO userDTO = BeanUtils.copyAs(user, UserDTO.class);
		return userDTO;
	}

}
