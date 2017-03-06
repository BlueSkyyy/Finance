package cn.com.xbed.common.util;

import com.alibaba.fastjson.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.Map.Entry;

public class PHPUtil {
	// 必填（公钥由接口提供方提供）
    public static final String API_KEY = "98814e50155ca35165068e65ab9cdf97";

    // secret_key
    public static final String SECRET_KEY = "6051f586aed8d7e9b213298a5b7e14ed";

    public static Map<String, Object> getMapFromJson(JSONObject jsonObject) {
        try {
            Map<String, Object> data = new HashMap<String, Object>();
            
            for (Entry<String, Object> entry : jsonObject.entrySet())
			{
            	data.put(entry.getKey(), entry.getValue());
			}
            
            return data;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getApiSign(JSONObject jsonObject) {
        Map<String, Object> data = getMapFromJson(jsonObject);
        if (data == null) {
            return "";
        }

        String apiSign = "";
        List<Entry<String, Object>> dataList = new ArrayList<>(data.entrySet());

        Collections.sort(dataList, new Comparator<Entry<String, Object>>() {
			@Override
			public int compare(Entry<String, Object> o1, Entry<String, Object> o2) {
				 return (o1.getKey()).toString().compareTo(o2.getKey());
			}
        });

        for (Entry<String, Object> item : dataList) {
            apiSign += item.getValue();
        }

        apiSign += SECRET_KEY;

        return MD5Util.returnStr(apiSign);
    }
    
    public static Map<String, String> getJsonData(JSONObject jsonObject) {
    	try
		{
    		jsonObject.put("api_key", API_KEY);
        	jsonObject.put("from", 3);
        	jsonObject.put("api_sign", getApiSign(jsonObject));
        	System.out.println(jsonObject.toJSONString());
        	Map<String, String> params = new HashMap<>();
        	params.put("json_data", URLEncoder.encode(jsonObject.toJSONString(), "UTF-8"));
			return params;
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
    	return null;
    }
    
    public static String getPHPParams(Map<String, Object> paramsMap) {
    	try
		{
    		JSONObject paramsJson = CommonUtil.isEmpty(paramsMap) ? new JSONObject() : JSONObject.parseObject(JSONObject.toJSONString(paramsMap));
    		paramsJson.put("api_key", API_KEY);
    		paramsJson.put("from", 3);
    		paramsJson.put("api_sign", getApiSign(paramsJson));
			return "json_data=" + URLEncoder.encode(paramsJson.toJSONString(), "UTF-8");
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
    	return null;
    }
    
    public static void main(String[] args) {
    	JSONObject jsonObject = new JSONObject();
    	
    	System.out.println(HttpClientUtil.postData("http://apitest.xbed.com.cn/advert/ad", getJsonData(jsonObject)));
	}
    
}
