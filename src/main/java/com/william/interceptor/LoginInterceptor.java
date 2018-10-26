package com.william.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.william.exception.BusinessException;
import com.william.model.dto.UserDTO;
import com.william.utils.ErrorUtil;
import com.william.utils.UserContextUtils;


public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// 清楚原先绑定的信息
		//UserContextUtils.setUser(null);
		//UserContextUtils.setAdmin(null);

		// 获取html页面时直接通过, 不匹配权限(目前都是单页面)
		String uri = request.getServletPath();
		if (uri.startsWith("/html")) {
			return true;
		}

		String tokenId = request.getParameter("App_Token");

		UserDTO user = null;
		//AdminDTO admin = null;

		String method = request.getMethod();
		System.out.println("====method====" + method);

		// tokenId 不为NULL时以tokenId为主
		System.out.println("====tokenId====" + tokenId);
		if (tokenId == null) {
			HttpSession session = request.getSession(false);

			if (session != null) {
				user = (UserDTO) session.getAttribute("user");
				if (null == user || null == user.getId() || user.getId().equals(0)) {
					user = null;
				} else {
					UserContextUtils.setUser(user);
				}

				if (null == user) {/*
					admin = (AdminDTO) session.getAttribute("admin");
					if (null == admin || null == admin.getId() || admin.getId().equals(0)) {
						admin = null;
					} else {
						UserContextUtils.setAdmin(admin);
					}
				*/}

			} else {
				user = null;
				//admin = null;

			}
		} else {
			user = UserContextUtils.getUser(tokenId);
			//admin = null;
		}

		if (null == user) {
			if (uri.matches("^/api/v\\d+/user(?!(/admin/verify)|(/verify))(.*)")) {
				throw new BusinessException(ErrorUtil.USER_TOKEN_EXPIRE, ErrorUtil.USER_TOKEN_EXPIRE_MSG);
			} else if (uri.matches("^/user(?!(/admin/verify)|(/verify))(.*)")) {
				response.sendRedirect(request.getContextPath() + "/html/user/login.html");
			}
		}

	/*	if (null == admin) {
			if (uri.matches("^/api/v\\d+/admin(?!(/verify))(.*)")) {
				throw new BusinessException(101, "登录超时，请重新登录");
			} else if (uri.matches("^/admin(?!(/verify))(.*)")) {
				response.sendRedirect(request.getContextPath() + "/html/admin/#/login");
				return false;
			}
		} else {
			// 权限认证
			String apiUrl = null;
			Pattern pattern = Pattern.compile("^/api/v\\d+/admin(?!/verify)/(.*)");
			Matcher matcher = pattern.matcher(uri);
			if (matcher.find()) {
				apiUrl = matcher.group(1);
				if (!ContextUtils.checkAccess(admin.getId(), apiUrl, method)) {
					throw new BusinessException(102, "权限不足");
				}
			}
		}
*/
		
	return true;	
	
	}


	}
