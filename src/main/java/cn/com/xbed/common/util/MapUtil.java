package cn.com.xbed.common.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: Map常用工具
 * @author 蔡俊杰 114603043@qq.com
 * @date 2014年3月4日 下午1:52:06
 */
public class MapUtil {
	/**
	 * @Description: 构造一个新的Map，并且直接设置一个元素到map
	 * @author 蔡俊杰 114603043@qq.com
	 * @date 2014年3月4日 下午1:51:35
	 * @param key
	 * @param value
	 * @return
	 */
	public static Map<String, Object> createMapByOne(String key, Object value){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(key, value);
		return map;
	}
	
	public static Map<String, String> createMapStrByOne(String key, String value){
		Map<String, String> map = new HashMap<String, String>();
		map.put(key, value);
		return map;
	}
	/**
	 * 创建一个HashMap<String, Object>
	 * @return
	 */
	public static Map<String, Object> createMap(){
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}
	/**
	 * 创建一个HashMap<String, String>
	 * @return
	 */
	public static Map<String, String> createMapStr(){
		Map<String, String> map = new HashMap<String, String>();
		return map;
	}
	/**
	 * @Description: 判断map是不是空的
	 * @author 蔡俊杰 114603043@qq.com
	 * @date 2014年3月4日 下午2:20:03
	 * @param map Map
	 * @return boolean 如果map是null或者map里面一个元素都没有，则返回true;否则返回false;
	 */
	public static boolean isEmpty(Map<?, ?> map){
		return null==map||map.isEmpty();
	}
	/**
     * 将一个 JavaBean 对象转化为一个  Map
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的  Map 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map convertBean(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
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

	/**
     * 将一个 JavaBean 对象转化为一个  Map
     * @param bean 要转化的JavaBean 对象
     * @return 转化出来的  Map 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map convertBeanWithDateToStr(Object bean)
            throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        Class type = bean.getClass();
        Map returnMap = new HashMap();
        BeanInfo beanInfo = Introspector.getBeanInfo(type);

        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();
            if (!propertyName.equals("class")) {
                Method readMethod = descriptor.getReadMethod();
                if(null!=readMethod){
                	Object result = readMethod.invoke(bean, new Object[0]);
                    if (null!=result && !"".equals(result)) {
                    	if(result instanceof java.util.Date){
                    		
                			returnMap.put(propertyName, DateUtil.convertDateToStr((java.util.Date)result, DateUtil.DEFAULT_DATE_TIME_FORMAT));
                			
                    	} else {
                    		returnMap.put(propertyName, result);
                    	}
                    } else {
                        returnMap.put(propertyName, "");
                    }
                }
            }
        }
        returnMap.remove("callbacks");
        return returnMap;
    }
	
	/**
     * 将一个 Map 对象转化为一个 JavaBean
     * @param type 要转化的类型
     * @param map 包含属性值的 map
     * @return 转化出来的 JavaBean 对象
     * @throws IntrospectionException 如果分析类属性失败
     * @throws IllegalAccessException 如果实例化 JavaBean 失败
     * @throws InstantiationException 如果实例化 JavaBean 失败
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败
     */
    @SuppressWarnings("rawtypes")
	public static Object convertMap(Class type, Map map)
            throws IntrospectionException, IllegalAccessException,
            InstantiationException, InvocationTargetException {
        BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
        Object obj = type.newInstance(); // 创建 JavaBean 对象

        // 给 JavaBean 对象的属性赋值
        PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
        for (int i = 0; i< propertyDescriptors.length; i++) {
            PropertyDescriptor descriptor = propertyDescriptors[i];
            String propertyName = descriptor.getName();

            if (map.containsKey(propertyName)) {
                // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
                Object value = map.get(propertyName);

                Object[] args = new Object[1];
                args[0] = value;

                descriptor.getWriteMethod().invoke(obj, args);
            }
        }
        return obj;
    }
}
