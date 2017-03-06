package cn.com.xbed.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.Map.Entry;


public class JsonUtil {

	/**
	 * 判断JSONObject是否不为空
	 * @param json
	 * @return
	 */
	public static boolean isNotBlank(JSONObject json) {
		if(null != json && json.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断JSONArray是否不为空
	 * @param json
	 * @return
	 */
	public static boolean isNotBlank(JSONArray json) {
		if(null != json && json.size() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 自定义JSONObject的键值对
	 * @param targetJsonObject	源数据
	 * @param args	留下的key数组
	 * @return
	 */
	public static JSONObject toCustomJsonObject(JSONObject targetJsonObject, String... args) {

		if (null != targetJsonObject) 
		{
			List<String> removeKeyList = new ArrayList<String>();
			List<String> argsList = Arrays.asList(args);
			for (Entry<String, Object> entry : targetJsonObject.entrySet())
			{
				if (!argsList.contains(entry.getKey()))
				{
					removeKeyList.add(entry.getKey());
				}
			}
			
			for (String removeKey : removeKeyList)
			{
				targetJsonObject.remove(removeKey);
			}
			
			return targetJsonObject;
		}
		return targetJsonObject;
	}
	
	
	/**
	 * 自定义JSONArray的键值对
	 * @param tagetJsonArray	源数据
	 * @param args	留下的key数组
	 * @return	
	 */
	public static JSONArray toCustomJsonArray(JSONArray tagetJsonArray, String... args) {
		
		if (null != tagetJsonArray && 0 < tagetJsonArray.size()) 
		{
			List<String> argsList = Arrays.asList(args);
			List<String> removeKeyList = new ArrayList<String>();
			
			for (Entry<String, Object> entry : tagetJsonArray.getJSONObject(0).entrySet())
			{
				if (!argsList.contains(entry.getKey()))
				{
					removeKeyList.add(entry.getKey());
				}
			}
			
			for (int i = 0; i < tagetJsonArray.size(); i++) {
				
				JSONObject dataJsonObject = tagetJsonArray.getJSONObject(i);
				for (String removeKey : removeKeyList)
				{
					dataJsonObject.remove(removeKey);
				}
			}
			
			return tagetJsonArray;
		}
		return tagetJsonArray;
	}
	/**
	 * 自定义List的对象属性
	 * @param targetList	源数据
	 * @param args	留下的属性名称
	 * @return
	 */
	public static <T> JSONArray toCustomList(List<T> targetList, String... args) {
		return toCustomJsonArray(JSONArray.parseArray(JSONArray.toJSONString(targetList)), args);
	}
	/**
	 * 自定义Map的键值对
	 * @param targetMap	源数据
	 * @param args	留下的key数组
	 * @return
	 */
	public static JSONObject toCustomMap(Map<String, Object> targetMap, String... args) {
		return toCustomJsonObject(JSONObject.parseObject(JSONObject.toJSONString(targetMap)), args);
	}
	
	public static void main(String[] args) {
		JSONArray array = new JSONArray();
		JSONObject obj1 = new JSONObject();
		obj1.put("key1", "a1");
		obj1.put("key2", "a2");
		obj1.put("key3", "a3");
		JSONObject obj2 = new JSONObject();
		obj2.put("key1", "b1");
		obj2.put("key2", "b2");
		obj2.put("key3", "b3");
		array.add(obj1);
		array.add(obj2);
		
		JSONObject object = new JSONObject();
		object.put("array", array);
		object.put("name", "cjj");
		
		System.out.println(JSONObject.toJSONString(object));
		
		//过滤代码的模板
		toCustomJsonArray(object.getJSONArray("array"), "key1","key3");
		toCustomJsonObject(object, "array");
		
		System.out.println(JSONObject.toJSONString(object));
	}
	/**
	 * 将一个 JavaBean 对象转化为一个 Map
	 * @param bean
	 * @return
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Map convertBean(Object bean)
			throws IntrospectionException, IllegalAccessException, InvocationTargetException {
		Class type = bean.getClass();
		Map returnMap = new HashMap();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);

		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					returnMap.put(propertyName, "");
				}
			}
		}
		return returnMap;
	}
}
