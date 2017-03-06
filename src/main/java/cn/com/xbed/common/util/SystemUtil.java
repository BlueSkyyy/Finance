package cn.com.xbed.common.util;


import cn.com.xbed.common.exception.BusinessException;
import cn.com.xbed.common.util.response.HttpStatusCode;
import cn.com.xbed.common.util.response.ServiceCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * 系统工具类
* @Title: SystemUtil.java 
* @Package cn.com.xbed.util 
* @Description: TODO
* @author porridge  
* @date 2015年11月27日 上午10:25:44 
* @version V1.0
 */
public class SystemUtil {
	static Logger logger = LoggerFactory.getLogger(SystemUtil.class);
	
	public final static String weChat = "WeChat";
	public final static String ios = "iOS";
	public final static String android = "android";
	public final static String miniProgram = "miniProgram";

	public static void main(String[] args) {
		//开始时间
		Calendar begin = Calendar.getInstance();
		begin.set(Calendar.HOUR_OF_DAY, 0);
		//结束时间
		Calendar end = Calendar.getInstance();
		begin.set(Calendar.MONTH, 12);
		//计算时间
		System.out.println(getLockBeginAndEndDate(begin.getTime(), end.getTime()));
	}

	/**
	 * 时间计算逻辑
	 * 开始时间偏移六个小时
	 * 结束时间偏移十二个小时
	 * 比如 2015-11-02 07:00 - 2015-11-03 12:00 一天时间 2015-11-02
	 * 比如 2015-11-02 08:00 - 2015-11-03 06:00 一天时间 2015-11-02
	 * 比如 2015-11-02 12:00 - 2015-11-03 05:00 一天时间 2015-11-02
	 * 比如 2015-11-02 15:00 - 2015-11-03 14:00 二天时间 2015-11-02 - 2015-11-03
	 * @param checkinTime 开始时间
	 * @param checkoutTime 结束时间
	 * @return
	 */
	public static Map<String, Date> getLockBeginAndEndDate(Date checkinTime, Date checkoutTime) {
		try {
			Date newBeginDate = DateUtil.addSecounds(DateUtil.addHours(checkinTime, -6), -1);
			Date newEndDate =   DateUtil.addSecounds(DateUtil.addHours(checkoutTime, -12), -1);

			Map<String, Date> map = new HashMap<>(2);
			map.put("beginDate", DateUtil.trimTimeFromDate(newBeginDate));
			map.put("endDate", DateUtil.trimTimeFromDate(newEndDate));
			return map;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获得请求地址ip
	 * @param httpservletrequest
	 * @return
	 */
	public static String getClientIP(HttpServletRequest httpservletrequest) {
	    if (httpservletrequest == null)
	        return null;
	    String s = httpservletrequest.getHeader("X-Forwarded-For");
	    if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s))
	        s = httpservletrequest.getHeader("Proxy-Client-IP");
	    if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s))
	        s = httpservletrequest.getHeader("WL-Proxy-Client-IP");
	    if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s))
	        s = httpservletrequest.getHeader("HTTP_CLIENT_IP");
	    if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s))
	        s = httpservletrequest.getHeader("HTTP_X_FORWARDED_FOR");
	    if (s == null || s.length() == 0 || "unknown".equalsIgnoreCase(s))
	        s = httpservletrequest.getRemoteAddr();
	    return s;
	}
	/**
	 * 合并二个对象,将 obj2的值合并到 obj1里边去
	 * @param obj1
	 * @param obj2
	 * @return
	 */
	public static void mergeBean(Object obj1, Object obj2){
		if(obj1.getClass() != obj2.getClass()){
			throw new BusinessException(HttpStatusCode.OK, ServiceCode.SERVER_ERROR, "不是同一个对象.无法合并");
		}
		try {
			Field[] f1 = obj1.getClass().getDeclaredFields();
			Field[] f2 = obj2.getClass().getDeclaredFields();
			for(int i=0;i<f2.length;i++){
				f2[i].setAccessible(true);
				f1[i].setAccessible(true);
				if(f2[i].get(obj2) != null && f1[i].get(obj1) == null){
					f1[i].set(obj1, f2[i].get(obj2));
				}
			}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 复制bean 允许不同对象复制,　只要他们之间有相同类型的属性名即可
	 * @param obj1 待复制的对象
	 * @param cls  需要复制的对象 class
	 */
	@SuppressWarnings("unchecked")
	public static <T> T copyBean(Object obj1, Class<?> cls){
		try {
			Object obj2 = cls.newInstance();
			Field[] f1s = obj1.getClass().getDeclaredFields();
			Field[] f2s = obj2.getClass().getDeclaredFields();
			for(int i=0;i<f1s.length;i++){
				Field f1 = f1s[i];
				for(int j=0;j<f2s.length;j++){
					Field f2 = f2s[j];
					if(f1.getName().equals(f2.getName()) && f1.getType() == f2.getType()){
						f1.setAccessible(true);
						f2.setAccessible(true);
						f2.set(obj2, f1.get(obj1));
					}
				}
			}
			return (T)obj2;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("finally")
	public static String MD5(String password){
		MessageDigest md5;
		StringBuffer result = new StringBuffer();
		try {
			md5 = MessageDigest.getInstance("MD5");
	        byte[] bytes = md5.digest(password.getBytes());
	        for(byte b : bytes)
	        {
	            String temp = Integer.toHexString(b & 0xff);
	            if(temp.length() == 1){
	                result.append("0" + temp);
	            }else{
	            	result.append(temp);
	            }
	        }
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}finally{
			return result.toString();
		}
	}
	/**
	 * 将以逗号隔开的数字转为int[]
	 * @param strs
	 * @return
	 */
	public static Integer[] stringConvertToIntegerArray(String strs){
		if(strs == null)
			return new Integer[]{};
		String[] tempRoomId = strs.split(",");
		List<Integer> listRoomId = new ArrayList<Integer>();
		for(String id:tempRoomId){
			if(id.matches("\\d*") == false){
				return null;
			}
			listRoomId.add(Integer.valueOf(id));
		}
		if(listRoomId.size() != 0)
			return listRoomId.toArray(new Integer[listRoomId.size()]);
		else
			return new Integer[]{};
	}
	
	public static String arrayToStr(Integer[] ints){
		StringBuffer stb = new StringBuffer();
		for(Integer i:ints){
			stb.append(i + ",");
		}
		if(stb.length() > 0)
			return stb.substring(0, stb.length() - 1);
		else 
			return null;
	}
	
	public static List<Integer> stringConvertToIntegerList(String strs){
		if(strs == null)
			return new ArrayList<Integer>();
		String[] temp = strs.split(",");
		List<Integer> list = new ArrayList<Integer>();
		for(String id:temp){
			if(id.matches("\\d*") == false){
				return null;
			}
			list.add(Integer.valueOf(id));
		}
		return list;
	}
	
	public static boolean indexOfIntegerArray(List<Integer> lists, Integer[] ints){
		if(lists == null || lists.size() == 0 || ints ==null || ints.length == 0)
			return false;
		
		int exist = 0;
		for(Integer parentI:lists){
			for(Integer sonI:ints){
				if(parentI.intValue() == sonI.intValue())
					++exist;
			}
		}
		return ints.length == exist ? true : false;
	}

	public static String getPicName(String prefix) {
		//时间戳 + 随机数
		String ramNum = prefix + "_" + (int) (Math.random() * 10) + "" + (int) (Math.random() * 10) + (int) (Math.random() * 10);
		return (new Date().getTime()) + "_" + ramNum;
	}
}
