package com.william.utils;


import java.io.File;
import java.io.FileOutputStream;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.william.exception.FrameworkException;
import com.william.utils.Constants;


public class HttpClientUtils {

	private static CloseableHttpClient httpClient;

	static {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(100);

		httpClient = HttpClients.custom().setConnectionManager(cm).build();
	}

	public static CloseableHttpClient getHttpClient() {
		return httpClient;
	}

	public static String get(String url) {
		HttpGet httpGet = new HttpGet(url);

		try {
			CloseableHttpResponse resp = httpClient.execute(httpGet);
			String respStr;

			try {
				StatusLine sl = resp.getStatusLine();
				if (sl.getStatusCode() != 200) {
					throw new FrameworkException("访问" + url + "返回状态[" + sl.getStatusCode() + "]错误");
				}

				HttpEntity entity = resp.getEntity();

				respStr = EntityUtils.toString(entity, Constants.DEFAULT_CHARSET);
			} finally {
				resp.close();
			}

			return respStr;
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
	}

	public static String post(String url, JSONObject msg) {
		HttpPost httpPost = new HttpPost(url);
		String content = JSON.toJSONString(msg);
		StringEntity entity = new StringEntity(content, Constants.DEFAULT_CHARSET);
		httpPost.setEntity(entity);

		try {
			CloseableHttpResponse resp = httpClient.execute(httpPost);
			String respStr;

			try {
				StatusLine sl = resp.getStatusLine();
				if (sl.getStatusCode() != 200) {
					throw new FrameworkException("访问" + url + "返回状态[" + sl.getStatusCode() + "]错误");
				}

				HttpEntity respEntity = resp.getEntity();

				respStr = EntityUtils.toString(respEntity, Constants.DEFAULT_CHARSET);
			} finally {
				resp.close();
			}

			return respStr;
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
	}

	public static File download2File(String url) {
		HttpGet httpGet = new HttpGet(url);

		try {
			CloseableHttpResponse resp = httpClient.execute(httpGet);

			try {
				StatusLine sl = resp.getStatusLine();
				if (sl.getStatusCode() != 200) {
					throw new FrameworkException("访问" + url + "返回状态[" + sl.getStatusCode() + "]错误");
				}

				String disposition = resp.getFirstHeader("Content-disposition").getValue();

				String fileName = disposition.substring(22, disposition.length() - 1);

				File file = File.createTempFile("tmp", fileName);

				HttpEntity entity = resp.getEntity();

				FileOutputStream fos = new FileOutputStream(file);

				entity.writeTo(fos);

				fos.close();

				EntityUtils.consume(entity);

				return file;
			} finally {
				resp.close();
			}
		} catch (Exception e) {
			throw new FrameworkException(e);
		}
	}
	

}
