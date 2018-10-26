package com.william.excel;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.servlet.view.AbstractView;

import com.william.exception.FrameworkException;

public class ExcelView extends AbstractView {
	public static final int START = "excel:".length();

	public static final String SYS_PREFIX = "${";
	public static final String SYS_SUFFIX = "}";
	public static final String TEMPLATE_NAME = "${VIEW_NAME}";
	public static final String API_TYPE = "${API_TYPE}";
	public static final String GRID_OBJECT_PROPS = "${GRID_OBJECT_PROPS}";
	private final String viewName;
	private String prefix;
	private String suffix;

	public ExcelView(String viewName) {
		this.viewName = viewName;
		setContentType("application/vnd.ms-excel");
	}

	protected final void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		String fileName = model.get("fileName").toString();
		if (!StringUtils.hasText(fileName)) {
			fileName = "导出";
		} else {
			model.remove("fileName");
		}
		
		buildExcelDocument(model, request, out);

		
		String agent = request.getHeader("USER-AGENT");
		if (null != agent) {
			if (agent.indexOf("MSIE") != -1 || agent.indexOf("Trident") != -1 || agent.indexOf("Edge") != -1) {// IE\Edge
				fileName = URLEncoder.encode(fileName, "UTF8");
			} else if (agent.indexOf("Mozilla") != -1) {// FireFox\Chrome
				fileName = new String(fileName.getBytes(Charset.forName("UTF-8")), Charset.forName("iso-8859-1"));
			}
		}
		response.setHeader("content-disposition", "attachment;filename=" + fileName + ".xls");

		renderWorkbook(out, response);
	}

	protected void buildExcelDocument(Map<String, Object> model, HttpServletRequest request, ByteArrayOutputStream out)
			throws Exception {
		String templateName;
		if (model.containsKey("${VIEW_NAME}")) {
			templateName = (String) model.get("${VIEW_NAME}");
		} else {
			templateName = viewName.substring(START) + suffix;
		}
		if (!StringUtils.isEmpty(prefix)) {
			templateName = prefix + templateName;
		}

		InputStream inp = loadTemplate(request, templateName);
		Context context = createContext(model, request);
		String apiType = (String) model.get("${API_TYPE}");
		try {
			if ("grid".equalsIgnoreCase(apiType)) {
				Object o = model.get("${GRID_OBJECT_PROPS}");
				String objectProps;
				if (o == null) {
					objectProps = "";
				} else {
					if ((o instanceof String)) {
						objectProps = (String) o;
					} else {
						if ((o instanceof String[])) {
							objectProps = Arrays.toString((String[]) o);
						} else
							objectProps = o.toString();
					}
				}
				JxlsHelper.getInstance().processGridTemplate(inp, out, context, objectProps);
			} else {
				JxlsHelper.getInstance().processTemplate(inp, out, context);
			}
		} finally {
			IOUtils.closeQuietly(inp);
		}
	}

	protected Context createContext(Map<String, Object> model, HttpServletRequest request) {
		Context context = new Context();
		for (Map.Entry<String, Object> entry : model.entrySet()) {
			String key = (String) entry.getKey();
			if ((!key.startsWith("${")) || (!key.endsWith("}"))) {
				context.putVar(key, entry.getValue());
			}
		}
		return context;
	}

	protected void renderWorkbook(ByteArrayOutputStream outputStream, HttpServletResponse response) throws IOException {
		response.setContentType(getContentType());
		ServletOutputStream servletOutputStream = response.getOutputStream();
		servletOutputStream.write(outputStream.toByteArray());
		servletOutputStream.flush();
	}

	protected InputStream loadTemplate(HttpServletRequest request, String templateName) {
		ServletContextResource resource = new ServletContextResource(request.getSession().getServletContext(),
				templateName);
		if (!resource.isReadable()) {
			logger.error("模板文件[" + templateName + "]不存在");
			throw new FrameworkException("模板文件[" + templateName + "]不存在");
		}

		InputStream inp = null;
		try {
			inp = resource.getInputStream();
			byte[] bytes = new byte[inp.available()];
			inp.read(bytes);
			return new ByteArrayInputStream(bytes);
		} catch (IOException e) {
			throw new FrameworkException("读取模板文件[" + templateName + "]失败");
		} finally {
			IOUtils.closeQuietly(inp);
		}
	}

	public String getBeanName() {
		return viewName;
	}

	public String getViewName() {
		return viewName;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	@Override
	protected boolean generatesDownloadContent() {
		return true;
	}


}