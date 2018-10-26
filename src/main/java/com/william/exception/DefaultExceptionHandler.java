package com.william.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;

public class DefaultExceptionHandler implements HandlerExceptionResolver {

	private Logger logger = Logger.getLogger(getClass());

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		ModelAndView mv = new ModelAndView();

		FastJsonJsonView view = new FastJsonJsonView();
		Map<String, Object> attributes = new HashMap<String, Object>();

		if (ex instanceof BusinessException) {
			int code = ((BusinessException) ex).getCode();
			attributes.put("status", code);
			attributes.put("error", ex.getMessage());

			logger.error("[" + code + "]" + ex.getMessage() + "\n    at " + ex.getStackTrace()[0].toString());
		} else {
			attributes.put("status", 9999);
			attributes.put("error", "系统错误");

			logger.error(ex.getMessage(), ex);
		}

		view.setAttributesMap(attributes);
		mv.setView(view);

		return mv;
	}

}
