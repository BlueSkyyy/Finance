package cn.com.xbed.common.util;

import cn.com.xbed.common.filter.Client;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.Map.Entry;

/**
 * api_sign 签名的工具类
 * @author YUAN
 *
 */
public class ApiSignUtil {
	
	private static Logger logger = LoggerFactory.getLogger(ApiSignUtil.class);
	
	/**
	 * 数字签名验证
	 * @param client 
	 * @param apiSign 
	 * @param params 传入的参数列表
	 * @return 是否验证通过
	 */
	public static boolean authSign(Client client, String apiSign, Map<String, String> params) {
		String secretKey = getSecretKey(client.getSystemName(), client.getPlatform(), client.getVersion(), client.getToken());
		return authSign(params, apiSign, secretKey);
	}
	/**
	 * 获取SecretKey
	 * @param systemName
	 * @param platform
	 * @param version
	 * @param token
	 * @return
	 */
	private static String getSecretKey(String systemName, String systemPlatform, String systemVersion, String token) {
		token = token == null ? "" : token;
		return MD5Util.returnStr(systemName + systemPlatform + systemVersion + token);
	}
	/**
	 * 解签名
	 * @param params 传入的参数列表
	 * @param secretKey 密钥key
	 * @return 是否验证通过
	 */
	public static boolean authSign(Map<String, String> params, String apiSign, String secretKey) {
		if(null == apiSign || "".equals(apiSign.trim())) {
			return false;
		}
		
		//排序
		List<Entry<String, String>> paramsList = new ArrayList<>(params.entrySet());

        Collections.sort(paramsList, new Comparator<Entry<String, String>>() {
			@Override
			public int compare(Entry<String, String> o1, Entry<String, String> o2) {
				 return (o1.getKey()).toString().compareTo(o2.getKey());
			}
        });
        
        String result = "";
        //字符串拼接
		for(Entry<String, String> entry : paramsList) {
			result += entry.getValue();
		}
		result += secretKey;
		String authResult = MD5Util.returnStr(result);
		if(!apiSign.equals(authResult)) {
			logger.warn("计算的字符串：" + result + "；接收到的apiSign：" + apiSign + "；MD5处理后的字符串：" + authResult);
			return false;
		}
		return true;
	}
	
	public static String encryptPhone(String phone) {
		if(StringUtils.isNotBlank(phone) && phone.length() == 11) {
			return  phone.substring(0, 3) +  "****" + phone.substring(8);
		}
		return "";
	}
	
	public static void main(String[] args) {
//		String secretKey = MD5Util.returnStr(null);
//		String secretKey = "abc" + null;
//		System.out.println(secretKey);
		System.out.println(encryptPhone("01234567890"));
		
		//adc0552ed9824eb6a32dee3b76020683
		//900150983cd24fb0d6963f7d28e17f72
	}
}
