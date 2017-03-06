package cn.com.xbed.common.util;

import cn.com.xbed.common.bean.HttpApiBean;
import cn.com.xbed.common.constant.HttpMethodConstants;
import cn.com.xbed.common.constant.HttpParamsConstants;
import cn.com.xbed.common.constant.IConstants;
import cn.com.xbed.common.util.response.ResultBean;
import cn.com.xbed.common.util.response.ServiceCode;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

import javax.transaction.SystemException;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class IOUtile {
	public static Logger log = Logger.getLogger(IOUtile.class);
	
	/**
	 * 组装URL<br>
	 * 
	 * @param url
	 *            原始的请求url,不要带问号后的参数部分
	 * @param paramsMap
	 *            键值对的字符串,键值都会进行URL编码,所以key支持有空格的特殊key,value也支持中文. 没有参数可以传null
	 * @return
	 * @throws Exception 
	 */
	public static String getFullUrl(String url, Map<String, Object> paramsMap, Integer httpParamsType) throws Exception {
		//参数格式转化
		String paramsStr = null;
		if (HttpParamsConstants.RESTFULL == httpParamsType){
			paramsStr = getRestfullParams(paramsMap);
		}else if (HttpParamsConstants.JSONDATA == httpParamsType) {
			paramsStr = getJsonDataParams(paramsMap);
		}
		
		//URL、参数拼接
		StringBuilder fullUrl = new StringBuilder(url);
		if (!CommonUtil.isEmpty(paramsStr))
		{
			fullUrl.append("?");
			fullUrl.append(paramsStr);
		}
	
		return fullUrl.toString();
	}
	
	/**
	 * 自动封装各种参数类型的参数字符串	 
	 * @param paramsMap
	 * @param httpParamsType
	 * @return
	 * @throws Exception 
	 */
	public static String getAutoParams(Map<String, Object> paramsMap, Integer httpParamsType) throws Exception {
		
		if (HttpParamsConstants.RESTFULL == httpParamsType)
		{
			return getRestfullParams(paramsMap);
		}else if (HttpParamsConstants.JSONDATA == httpParamsType) 
		{
			return getJsonDataParams(paramsMap);
		}else if (HttpParamsConstants.JSONDATA_DES == httpParamsType) 
		{
			return getJsonDataDesParams(paramsMap);
		}else if (HttpParamsConstants.PHP_MIDDLE == httpParamsType) 
		{
			return PHPUtil.getPHPParams(paramsMap);
		}
		return null;
	}
	
	/**
	 * 转化占位符类型的URL
	 * @param url
	 * @param paramsMap
	 * @return
	 * @throws SystemException
	 */
	public static String transUrlParams(String url, Map<String, Object> paramsMap) throws SystemException {
		
		if (CommonUtil.isEmpty(url))
		{
			throw new SystemException("API地址不能为空");
		} else
		{
			Matcher m=Pattern.compile("\\{(.*?)\\}").matcher(url);
			while(m.find()){
				
				if (CommonUtil.isEmpty(paramsMap))
				{
					throw new SystemException(url + ":接口地址没有找到占位符"+ m.group() +"需要的参数，不允许访问接口！");
				}
				
				Object obj = paramsMap.get(m.group().substring(1, m.group().length()-1).trim());
				if (CommonUtil.isEmpty(obj))
				{
					throw new SystemException(url + ":接口地址没有找到占位符"+ m.group() +"需要的参数，不允许访问接口！");
				} else
				{
					url = url.replace(m.group(), obj.toString());
					paramsMap.remove(m.group().substring(1, m.group().length()-1).trim());
				}
			}
			return url;
		}
	}
	/**
	 * 转化成restfull风格参数(不带问号)
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public static String getRestfullParams(Map<String, Object> params) throws Exception {

		StringBuilder fullUrl = new StringBuilder();
		if (params != null) {
			Set<String> set = params.keySet();
			if (!set.isEmpty()) {
				for (String key : set) {
					Object value = params.get(key);
					if (!CommonUtil.isEmpty(value))
					{
						fullUrl.append(URLEncoder.encode(key, "UTF-8")).append("=")
						.append(URLEncoder.encode(value.toString(), "UTF-8")).append("&");
					}
				}
				if (fullUrl.length() > 0) {
					fullUrl.deleteCharAt(fullUrl.length() - 1);
				}
			}
		}
		return fullUrl.toString();
	}
	/**
	 * 转化成jsonData,附加des加密，参数格式
	 * @param paramsMap
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String getJsonDataDesParams(Map<String, Object> paramsMap) throws UnsupportedEncodingException {
		
		String paramsStr = CommonUtil.isEmpty(paramsMap) ? new JSONObject().toJSONString() : JSONObject.toJSONString(paramsMap);
		String des = URLEncoder.encode(Security.encrypt(paramsStr), "utf-8");
		String md5 = MD5Util.returnStr(paramsStr);
		
		return "json_data="+des+"&xb_key=" + md5;
	}
	
	/**
	 * 转化成jsonData的参数风格(不带问号)
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public static String getJsonDataParams(Map<String, Object> paramsMap) throws Exception {

		StringBuilder sb = new StringBuilder();
		if (CommonUtil.isEmpty(paramsMap))
		{
			sb.append("json_data={}");
		} else
		{
			sb.append("json_data=").append(URLEncoder.encode(JSONObject.toJSONString(paramsMap), "UTF-8"));
		}
		return sb.toString();
	}
	/**
	 * API系统专用的底层接口调用类
	 * @param httpApiBean
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
	public static JSONObject httpRequest(HttpApiBean httpApiBean, Map<String, Object> paramsMap) throws Exception {
		
		String url = httpApiBean.getBaseBean().getHost() + httpApiBean.getRequestUrl();
		//转化URL地址中的占位符
		url = transUrlParams(url, paramsMap);
		
		String paramsStr = null;
		if ( HttpMethodConstants.POST.equals(httpApiBean.getRequestMethod()) )
		{
			paramsStr = getAutoParams(paramsMap, httpApiBean.getHttpParamsType());
		}else 
		{
			url =  getFullUrl(url, paramsMap, httpApiBean.getHttpParamsType());
		}
		long start = System.currentTimeMillis();
		log.info(String.format("访问链接 %s：%s，传入参数：%s。", httpApiBean.getRequestMethod(), url, paramsMap));
		JSONObject resultJson = httpRequest(url, httpApiBean.getRequestMethod(), paramsStr, httpApiBean.getBaseBean().getSecret());
		if(!IConstants.filterLogList.contains(httpApiBean.getRequestUrl())) {
			log.info(String.format("请求服务端：%s，返回报文：%s。", httpApiBean.getRequestUrl(), resultJson.toJSONString()));
		}
		long end = System.currentTimeMillis();
		log.info(String.format("本次请求共耗时：%d ms", (end - start)));
		return resultJson;
	}
	

	/**
	 * 各底层系统返回值处理
	 * @param resultJson
	 * @param httpApiBean
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ResultBean<?> transParamsToResult(JSONObject resultJson, HttpApiBean httpApiBean) {
		if ("xbClean".equals(httpApiBean.getBaseBean().getSysName()) || "appBaseApi".equals(httpApiBean.getBaseBean().getSysName()))
		{
			//丽家会返回值模式
			if ("0000".equals(resultJson.getString("code")))
			{
				return new ResultBean(true, ServiceCode.OK, resultJson.getString("msg"), resultJson.get("data"));
			}else
			{
				return new ResultBean(false, ServiceCode.OTHER_API_ERROR_CODE, resultJson.getString("msg"), resultJson.get("data"));
			}
		}else if ("phpMiddleApi".equals(httpApiBean.getBaseBean().getSysName())) 
		{
			//PHP中间层返回值模式
			if ("2000".equals(resultJson.getString("retcode")))
			{
				return new ResultBean(true, ServiceCode.OK, resultJson.getString("retmsg"), resultJson.get("retval"));
			}else
			{
				return new ResultBean(false, ServiceCode.OTHER_API_ERROR_CODE, resultJson.getString("retmsg"), resultJson.get("retval"));
			}
		}else if ("tagApi".equals(httpApiBean.getBaseBean().getSysName()))
		{
			//标签系统返回值模式
			if ("200".equals(resultJson.getString("retCode")))
			{
				return new ResultBean(true, ServiceCode.OK, resultJson.getString("msg"), resultJson.get("data"));
			}else
			{
				return new ResultBean(false, ServiceCode.OTHER_API_ERROR_CODE, resultJson.getString("msg"), resultJson.get("data"));
			}
		}else
		{
			//标签系统返回值模式
			if ("21020000".equals(resultJson.getString("retCode")))
			{
				return new ResultBean(true, ServiceCode.OK, resultJson.getString("msg"), resultJson.get("data"));
			}else
			{
				return new ResultBean(false, resultJson.getInteger("retCode"), resultJson.getString("msg"), resultJson.get("data"));
			}
		}
	}

	/**
	 * 发起https请求并获取结果
	 * @param fullUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 * @throws SystemException
	 */
	public static JSONObject httpRequest(String fullUrl, String requestMethod, String outputStr, String secret) throws SystemException {
		OutputStream outputStream = null;
		BufferedReader bufferedReader = null;
		InputStreamReader inputStreamReader = null;
		InputStream inputStream = null;
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		
		try {

			URL url = new URL(fullUrl);
			HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
			
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);
			if (!CommonUtil.isEmpty(secret))
			{
				httpUrlConn.setRequestProperty("secret", secret);
			}

			httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
			}

			// 将返回的输入流转换成字符串
			if (HttpURLConnection.HTTP_OK == httpUrlConn.getResponseCode() || 
					HttpURLConnection.HTTP_CREATED == httpUrlConn.getResponseCode() || 
					HttpURLConnection.HTTP_ACCEPTED == httpUrlConn.getResponseCode() )
			{
				inputStream = httpUrlConn.getInputStream();
			}else
			{
				inputStream = httpUrlConn.getErrorStream();
			}
			
			inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			httpUrlConn.disconnect();
			
			if(CommonUtil.isEmpty(buffer.toString()))
			{
				return null;
			}
			
			jsonObject = JSONObject.parseObject(buffer.toString());
		} catch (ConnectException ce) {
			log.error("Server connection timed out. Request link : " + fullUrl,ce);
			throw new SystemException("Server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}. Request link : " + fullUrl,e);
			throw new SystemException("https request error:{}");
		} finally {
			if(null!=outputStream){
				try {
					outputStream.close();
				} catch (IOException e) {
					log.error(e.getMessage(),e);
				}
			}
			if(null!=bufferedReader){
				try {
					bufferedReader.close();
				} catch (IOException e) {
					log.error(e.getMessage(),e);
				}
			}
			if(null!=inputStreamReader){
				try {
					inputStreamReader.close();
				} catch (IOException e) {
					log.error(e.getMessage(),e);
				}
			}
			if(null!=inputStream){
				try {
					inputStream.close();
					inputStream = null;
				} catch (IOException e) {
					log.error(e.getMessage(),e);
				}
			}
		}
		return jsonObject;
	}

	/**
	 * 把字节流转换成字符流
	 * @param in 输入流
	 * @param charset 字符编码
	 */
	public static String convertByteToStr(InputStream in, String charset) {
		if (null == in) {
			return "";
		}
		try {
			BufferedReader br = null;
			if (!charset.equals("") && charset.trim().length() > 0) {
				br = new BufferedReader(new InputStreamReader(in, charset));
			} else {
				br = new BufferedReader(new InputStreamReader(in));
			}
			StringBuffer sb = new StringBuffer("");
			String temp = "";
			while (null != (temp = br.readLine())) {
				sb.append(temp);
			}
			br.close();
			in.close();
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}  catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static void main(String[] args) throws Exception {
//		System.out.println(httpRequest("http://120.25.106.243:6872/api/order/mini?page=1&size=20", "GET", null, "cd3bd78b238d49v34a91fa0171d3a269"));
		Map<String, Object> params = MapUtil.createMap();
		
		params.put("id", 989);
		params.put("name", "abc");
		params.put("key3", 212);
		
//		System.out.println(getFullUrl("http://192.168.1.1/user", params));
//		System.out.println(getJsonDataParams(params));
//		System.out.println(getFullUrl("/api/checkiner/{id}/{name}", params, HttpParamsConstants.OTHERS));
		
//		System.out.println(httpRequest(ToolApiConstants.SENDCODE, MapUtil.createMap()));
		System.out.println(new JSONObject().toJSONString());
	}
	
}
