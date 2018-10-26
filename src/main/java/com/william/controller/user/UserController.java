package com.william.controller.user;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.zip.DataFormatException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gzh.auth.TokenUtils;
import com.william.fastjson.annotation.JsonPropertyFilter;
import com.william.exception.BusinessException;
import com.william.model.ResponseBean;
import com.william.model.dto.UserBankcardDTO;
import com.william.model.dto.UserDTO;
import com.william.model.enum_.ErrorCodes;
import com.william.service.UserBankcardService;
import com.william.service.UserService;
import com.william.utils.ImageUtils;
import com.william.utils.MathUtils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

@Controller
@RequestMapping("api/v1/user")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired
	private UserBankcardService userBankcardService;
	
	@Resource(name = "registerCache")
	private Cache registerCache;

	@Resource(name = "captchaCache")
	private Cache captchaCache;
	private Random random = new Random();

	private DecimalFormat df = new DecimalFormat("000000");
	@ResponseBody
	@RequestMapping(value = "/verify/generateCaptcha",  method = RequestMethod.GET)
	public ResponseBean generateCaptcha(HttpServletResponse response) {
		String captcha = MathUtils.getRandomString(6);

		System.out.println("图形验证码:" + captcha);

		String captchaId = TokenUtils.getTokenId();

		Element element = new Element(captchaId, captcha);
		captchaCache.put(element);

		ResponseBean responseBean = new ResponseBean();
		Map<String,String> map = new HashMap<String,String>();
		map.put("captchaId", captchaId);
		responseBean.setData(map);

		return responseBean;
	}


	@RequestMapping(value = "/verify/captcha", method = RequestMethod.GET)
	public void getCaptcha(HttpServletResponse response,
			@RequestParam(value = "captchaId", required = false) String captchaId
			) {
		// response.setHeader("Pragma", "No-cache");
		// response.setHeader("Cache-Control", "no-cache");
		// response.setDateHeader("Expires", 0);
		// 表明生成的响应是图片
		response.setContentType("image/jpeg");

		Element element = captchaCache.get(captchaId);

		if (element == null) {
			response.setStatus(404);
			return;
		}

		String captcha = (String) element.getObjectValue();
		try {
			ImageUtils.generateCaptcha(captcha, response.getOutputStream());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	//params = "operate=getVerifyCode",
	@ResponseBody
	@RequestMapping(value = "/verify/getVerifyCode",  method = RequestMethod.GET)
	public ResponseBean getVerifyCode(@RequestParam("mobile") String mobile,
			@RequestParam(value = "captchaId", required = false) String captchaId,
			@RequestParam(value = "captcha", required = false) String captcha,
			@RequestParam(value = "type", defaultValue = "0") Integer type) {
		// 判断图形验证码是否正确
		validateCaptcha(captchaId, captcha);
		// 是否已注册
	/*	UserDTO userDTO = userService.getUserByUserNo(mobile, null);
		if (type == 0 && userDTO != null) {
			throw new BusinessException(112, "手机号已注册");
		} else if (type == 1 && userDTO == null) {
			throw new BusinessException(109, "用户不存在");
		}*/

		int verifyNo = random.nextInt(999999);
		String verifyCode = df.format(verifyNo);

		//smsService.sendMessage(mobile, verifyCode);
		System.out.println("发送验证码[" + type + "]:" + verifyCode);

		Element element1 = new Element(mobile, verifyCode);
		registerCache.put(element1);

		ResponseBean responseBean = new ResponseBean();

		return responseBean;
	}


	private void validateCaptcha(String captchaId, String captcha) {
		if (captchaId == null) {
			return;
		}

		Element element = captchaCache.get(captchaId);

		if (element == null) {
			throw new BusinessException(105, "图形验证码已超时，请重新获取");
		}

		String realCaptcha = (String) element.getObjectValue();

		// 从缓存中移除
		captchaCache.removeElement(element);

		if (!realCaptcha.equalsIgnoreCase(captcha)) {
			throw new BusinessException(106, "图形验证码错误");
		}
	}


	private void validateVerifyCode(String mobile, String verify) {
		Element element = registerCache.get(mobile);

		if (element == null) {
			throw new BusinessException(110, "验证码已超时，请重新获取");
		}

		String verifyCode = (String) element.getObjectValue();

		// 从缓存中移除
		registerCache.removeElement(element);

		if (!verifyCode.equals(verify)) {
			throw new BusinessException(111, "验证码错误");
		}
	}

	//params = "operate=checkUser",
	@ResponseBody
	@RequestMapping(value = "/verify/checkUser",  method = RequestMethod.GET)
	public ResponseBean checkUser(@RequestParam("loginName") String loginName) {
	/*	UserDTO userDTO = userService.getUserByUserNo(loginName, null);

		boolean data = true;
		if (userDTO == null) {
			data = false;
		}

		responseBean.setData(data);*/

		ResponseBean responseBean = new ResponseBean();
		return responseBean;
	}
	/**
	 * 发送短信验证码
	 * @param phone
	 * @return
	 */
	@RequestMapping("/verify/sendShortMsgCheckCode")
	@ResponseBody
	public ResponseBean sendShortMsgCheckCode(String phone) {
		//TODO发送短信验证码
		ResponseBean resp = new ResponseBean();
		String captcha = MathUtils.getRandomString(6);
		return resp;
		
	}
	/**
	 * 注册
	 * @param session
	 * @param loginName
	 * @param password
	 * @param clientType
	 * @param registrationId
	 * @param verify
	 * @return
	 * @throws DataFormatException 
	 */
	//params = "operate=",
	@JsonPropertyFilter(clazz = UserDTO.class, value = { "id", "tokenId"})
	@ResponseBody
	@RequestMapping(value = "/verify/register",  method = RequestMethod.POST)
	public ResponseBean register(HttpSession session,
			@RequestParam(value = "loginName", required = true) String loginName,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "clientType", required = true) Byte clientType,
			@RequestParam(value = "verify", required = true) String verify,
			@RequestParam(value = "recommendUser", required = false) String recommendUser) throws DataFormatException {
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(loginName);
		userDTO.setLoginPassword(password);
		userDTO.setClientType(clientType);
		userDTO.setNickName("QB"+loginName.substring(7, 11));
		if(!ObjectUtils.isEmpty(recommendUser)) {
			UserDTO recommendUserInfo = this.userService.getUserByUserName(recommendUser);
			if(ObjectUtils.isEmpty(recommendUserInfo)) {
				throw new BusinessException(ErrorCodes.USER_NOT_EXIST.getCode(), "推荐用户不存在");
			}
			userDTO.setRecommendId(recommendUserInfo.getId());
		}
		userDTO = userService.rucRegister(userDTO);
		
		ResponseBean responseBean = new ResponseBean();
		responseBean.setData(userDTO);
		return responseBean;
		
		/*
		// 校验短信验证码
		//validateVerifyCode(loginName, verify);
		UserDTO userDTO = new UserDTO();

		if (clientType.equals(UserDTO.CLIENTTYPE_WX)) {
			UserDTO user = (UserDTO) session.getAttribute("user");
			if (null == user) {
				throw new BusinessException(132, "请在公众号里进入");
			}
			Integer userState = user.getUserState();
			if (userState == null || userState != 3) {
				throw new BusinessException(132, "状态错误[" + userState + "]，请在公众号里进入");
			}
			
			userDTO.setOpenId(user.getOpenId());
		}
	*/
		}
	/**
	 * 登陆
	 * @param loginName
	 * @param password
	 * @return
	 */
	@ResponseBody
	@JsonPropertyFilter(clazz = UserDTO.class, value = { "id", "tokenId"})
	@RequestMapping(value = "/verify/login", method = RequestMethod.POST)
	public ResponseBean login(
			@RequestParam(value = "loginName", required = true) String loginName,
			@RequestParam(value = "clientType", required = true) Byte clientType,
			@RequestParam(value = "password", required = true) String password
			) {
			// 校验图形验证码
//					validateCaptcha(captchaId, captcha);
			UserDTO loginInfo = new UserDTO();
			loginInfo.setUsername(loginName);
			loginInfo.setLoginPassword(password);
			loginInfo.setClientType(clientType);
			UserDTO userDTO = userService.loginToken(loginInfo);
			ResponseBean responseBean = new ResponseBean();
			responseBean.setData(userDTO);
			return responseBean;
	}
	
	/**
	 * 找回密码
	 * @param userName
	 * @param newPassword
	 * @param verifyCode
	 * @return
	 */
	@RequestMapping("/verify/getBackPwd")
	@ResponseBody
	public ResponseBean getBackPwd(String userName,String newPassword,String verifyCode) {
		//验证短信验证码
		//TODO
//		validateVerifyCode(loginName, verify);
		System.out.println("短信验证码:"+verifyCode+"已验证！");
		this.userService.getBackPwd(userName, newPassword);
		ResponseBean resp = new ResponseBean();
		return resp;
	}
	/**
	 * 找回密码
	 * @param userName
	 * @param newPassword
	 * @param verifyCode
	 * @return
	 */
	@RequestMapping("/verify/getBackPayPwd")
	@ResponseBody
	public ResponseBean getBackPayPwd(String userName,String newPayPassword,String verifyCode) {
		//验证短信验证码
		//TODO
//		validateVerifyCode(loginName, verify);
		System.out.println("短信验证码:"+verifyCode+"已验证！");
		this.userService.getBackPayPwd(userName, newPayPassword);
		ResponseBean resp = new ResponseBean();
		return resp;
	}
	/**
	 * 修改昵称
	 * @param userName
	 * @param nickName
	 * @return
	 */
	@RequestMapping("/verify/updateNickname")
	@ResponseBody
	public ResponseBean updateNickname(String userName,String nickName) {
		UserDTO userDTO = this.userService.updateNickname(userName, nickName);
		ResponseBean resp = new ResponseBean();
		resp.setData(userDTO);
		return resp;
	}
	@RequestMapping("/verify/updateHeadIamge")
	@ResponseBody
	public ResponseBean updateHeadIamge(String userName,String headImageUrl) {
		UserDTO userDTO = this.userService.updateHeadIamge(userName, headImageUrl);
		ResponseBean resp = new ResponseBean();
		resp.setData(userDTO);
		return resp;
	}
	/**
	 * 编辑会员资料
	 * @param userDTO
	 * @return
	 */
	@RequestMapping("/verify/editUserInfo")
	@ResponseBody
	public ResponseBean editUserInfo(UserDTO userDTO) {
		this.userService.updateUserInfo(userDTO);
		ResponseBean resp = new ResponseBean();
		return resp;
	}
	/**
	 * 用户绑定银行卡
	 * @param userName
	 * @param realName
	 * @param phone
	 * @param bankcadrNum
	 * @return
	 */
	@RequestMapping("/verify/bindingBankCard")
	@ResponseBody
	public ResponseBean bindingBankCard(String userName,String realName,String phone,String bankcardNum) {
		//TODO验证银行卡真实性
//		verifyBankcard(realName,String phone,String bankcardNum);
		this.userBankcardService.bindingBankcard(userName, bankcardNum);
		ResponseBean resp = new ResponseBean();
		return resp;
	}
	/**
	 * 修改绑定的银行卡号
	 * @param userName
	 * @param realName
	 * @param phone
	 * @param newBankCard
	 * @param oldBankCard
	 * @return
	 */
//	@RequestMapping("/verify/modifyBankCard")
//	@ResponseBody
//	public ResponseBean modifyBankCard(String userName,String realName , String phone,
//			String newBankCard,String oldBankCard) {
//		//TODO验证银行卡真实性
////		verifyBankcard(realName,String phone,String bankcardNum);
//		this.userBankcardService.update(userName, newBankCard, oldBankCard);
//		ResponseBean resp = new ResponseBean();
//		return resp;
//	}
	/**
	 * 获取银行卡绑定信息
	 * @param userName
	 * @return
	 */
	@RequestMapping("/verify/getBindingBankcardInfo")
	@ResponseBody
	public ResponseBean getBindingBankcardInfo(String userName) {
		List<UserBankcardDTO> list = this.userBankcardService.getBindingBankcardInfo(userName);
		ResponseBean resp = new ResponseBean();
		resp.setData(list);
		return resp;
	}
}
