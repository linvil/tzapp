package com.william.excel;

import java.util.Locale;

import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractCachingViewResolver;

@Order(1000)
public class ExcelViewResolver extends AbstractCachingViewResolver {
	public static final String VIEW_PREFIX = "excel:";
	private String prefix;
	private String suffix;

	protected View loadView(String viewName, Locale locale) throws Exception {
		if (!viewName.startsWith("excel:")) {
			return null;
		}
		ExcelView view = new ExcelView(viewName);
		if (!StringUtils.isEmpty(prefix)) {
			view.setPrefix(prefix);
		}
		if (!StringUtils.isEmpty(suffix)) {
			view.setSuffix(suffix);
		}
		return view;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
}
