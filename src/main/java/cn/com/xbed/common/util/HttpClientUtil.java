package cn.com.xbed.common.util;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

public class HttpClientUtil {
	private static int TIMEOUT = 10000;
	private static String CHARSET = "utf-8";
	final static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

	public static final String getData(String url) {
		return getData(url, null);
	}
	
	public static final String getData(String url, Map<String, String> params, Map<String, String> headers) {
		StringBuffer stb = new StringBuffer();
		if(params != null) {
			for(String key:params.keySet()){
				try{
					if(params.get(key) != null)
						stb.append(String.format("%s=%s&", key,  URLEncoder.encode(params.get(key), CHARSET)));
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		if(url.indexOf("?") == -1)
			url += "?" + stb.toString();
		else
			url += "&" + stb.toString();
		
		return getData(url, headers);
	}

	/**
	 * get 请求
	 * 
	 * @param url
	 *            请求地址
	 * @param headers
	 * @return
	 */
	@SuppressWarnings("finally")
	public static final String getData(String url, Map<String, String> headers) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String result = null;
		Date beginDate = new Date();
		try {
			HttpGet httpget = new HttpGet(url);
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(TIMEOUT).setConnectTimeout(TIMEOUT)
					.build();
			httpget.setConfig(requestConfig);
			if (headers != null) {
				Set<Map.Entry<String, String>> sets = headers.entrySet();
				for (Map.Entry<String, String> map : sets) {
					httpget.setHeader(map.getKey(), map.getValue());
				}
			}
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity, CHARSET);
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (final IOException e) {
				e.printStackTrace();
			}
			logger.info("发起get请求 url:{} result:{} 响应时间:{}毫秒", url, result,
					(new Date().getTime() - beginDate.getTime()));
			return result;
		}
	}

	public static final String postDataBody(String url, Map<String, String> map) {
		return postDataBody(url, map, null);
	}

	@SuppressWarnings("finally")
	public static final String postDataBody(String url,
			Map<String, String> params, Map<String, String> headers) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String result = null;
		Date beginDate = new Date();
		try {
			HttpPost post = new HttpPost(url);
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(TIMEOUT).setConnectTimeout(TIMEOUT)
					.build();
			post.setConfig(requestConfig);

			if (params != null) {
				post.setEntity(new StringEntity(JSON.toJSONString(params),
						CHARSET));
				post.setHeader("Content-type", "application/json");
			}
			if (headers != null) {
				Set<Map.Entry<String, String>> sets = headers.entrySet();
				for (Map.Entry<String, String> set : sets) {
					post.setHeader(set.getKey(), set.getValue());
				}
			}
			CloseableHttpResponse response = httpclient.execute(post);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity, CHARSET);
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (final IOException e) {
				e.printStackTrace();
			}
			logger.info("发起post请求 url:{},params:{},result:{} 响应时间:{}毫秒", url,
					params, result,
					(new Date().getTime() - beginDate.getTime()));
			return result;
		}
	}

	/**
	 * POST 请求
	 * @param url 请求地址
	 * @param params 请求参数
	 * @return
	 */
	public static final String postData(String url, Map<String, String> params) {
		return postData(url, params, null);
	}

	/**
	 * POST 请求
	 * @param url 请求地址
	 * @param params 请求参数
	 * @param headers 请求头          
	 * @return
	 */
	@SuppressWarnings("finally")
	public static final String postData(String url, Map<String, String> params,
			Map<String, String> headers) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String result = null;
		Date beginDate = new Date();
		try {
			HttpPost post = new HttpPost(url);
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(TIMEOUT).setConnectTimeout(TIMEOUT)
					.build();
			post.setConfig(requestConfig);
			if (params != null) {
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				Set<Map.Entry<String, String>> sets = params.entrySet();
				for (Map.Entry<String, String> map : sets) {
					nvps.add(new BasicNameValuePair(map.getKey(), map
							.getValue()));
				}
				post.setEntity(new UrlEncodedFormEntity(nvps, CHARSET));
			}
			if (headers != null) {
				Set<Map.Entry<String, String>> sets = headers.entrySet();
				for (Map.Entry<String, String> map : sets) {
					post.setHeader(map.getKey(), map.getValue());
				}
			}
			CloseableHttpResponse response = httpclient.execute(post);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity, CHARSET);
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (final IOException e) {
				e.printStackTrace();
			}
			logger.info("发起post请求 url:{},params:{},result:{} 响应时间:{}毫秒", url,
					params, result,
					(new Date().getTime() - beginDate.getTime()));
			return result;
		}
	}

	@SuppressWarnings("finally")
	public static final String putData(String url, Map<String, String> params,
			Map<String, String> headers) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String result = null;
		Date beginDate = new Date();
		try {
			if (params != null) {
				Set<Map.Entry<String, String>> sets = params.entrySet();
				StringBuffer sb = new StringBuffer();
				for (Map.Entry<String, String> map : sets) {
					sb.append(map.getKey() + "=" + URLEncoder.encode(map.getValue(), "utf8") + "&");
				}
				url += "?" + sb.toString();
			}
			HttpPut put = new HttpPut(url);
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(TIMEOUT).setConnectTimeout(TIMEOUT)
					.build();
			put.setConfig(requestConfig);
			if (headers != null) {
				Set<Map.Entry<String, String>> sets = headers.entrySet();
				for (Map.Entry<String, String> map : sets) {
					put.setHeader(map.getKey(), map.getValue());
				}
			}
			CloseableHttpResponse response = httpclient.execute(put);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					result = EntityUtils.toString(entity, CHARSET);
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				httpclient.close();
			} catch (final IOException e) {
				e.printStackTrace();
			}
			logger.info("发起put请求 url:{}, result:{} 响应时间:{}毫秒", url, result,
					(new Date().getTime() - beginDate.getTime()));
			return result;
		}
	}

}