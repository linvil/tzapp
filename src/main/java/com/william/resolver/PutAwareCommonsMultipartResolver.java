package com.william.resolver;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

public class PutAwareCommonsMultipartResolver extends CommonsMultipartResolver {

	@Override
	public boolean isMultipart(HttpServletRequest request) {
		return (request != null && isMultipartContent(request));
	}

	public static final boolean isMultipartContent(HttpServletRequest request) {
		if (!"POST".equalsIgnoreCase(request.getMethod()) && !"PUT".equalsIgnoreCase(request.getMethod())) {
			return false;
		}

		return FileUploadBase.isMultipartContent(new ServletRequestContext(request));
	}

}
