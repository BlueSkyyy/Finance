package cn.com.xbed.common.util;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 一般通用类
 *
 * @author mdw 2008-11-17
 */
public class CommonUtil {
	


	public static void main( String[] args ){
		Set<String> list = new HashSet<String>();
		for (int i = 0; i < 200000; i++) {
			list.add(getUUID());
			list.add("01");
		}
		
		System.out.println(list.size());
	}
	
	/**
	 * 
	* @Title: fromtStr 
	* @Description: TODO 格式化 字符串 
	* @param strs  参数格式 为 045
	* @return 
	* @return String    返回 正常字符 45
	* @author dao
	* @date 2013年11月10日 下午8:30:01
	 */
	public static String fromtStr( String strs ){
		int i=0;
		if(!isEmpty(strs) && strs != null){
			i = Integer.parseInt(strs);
			return i+"";
		}
		return "";
	}
	
	/**
	 * 
	* @Title: fromtMin 
	* @Description: TODO 格式化 返回三位数
	* @param minstr 分钟
	* @return 
	* @return String    返回 000
	* @author dao
	* @date 2013年11月10日 下午8:04:15
	 */
	public static String fromtMin( String minstr ){
		String[] retrunstr= {"0","0","0"};
		
		if( !isEmpty(minstr) && minstr != null ){
			char[] cars = minstr.toCharArray();
			int t=0;
			for( int i = retrunstr.length-cars.length; i<retrunstr.length ; i ++){
					retrunstr[i] = cars[t]+"";
					t++;
			}
		}
		return strings(retrunstr);
	}
	
	/**
	 * 
	* @Title: strings 
	* @Description: TODO 将数组转成 字符串
	* @param str
	* @return 
	* @return String    
	* @author dao
	* @date 2013年11月10日 下午8:22:37
	 */
	public static String strings(String[] str){
		String strs="";
		for( int i = 0; i < str.length; i++ ){
			strs +=str[i];
		}
		return strs;
	}
	
	/**
	 * 判断传入对象是否为空或值为空
	 *
	 * @param obj 需要判断是否为空的对象
	 * @return true-如果对象为空，false-如果对象非空
	 */
	public static boolean isEmpty(Object obj) {
		// 判断是否为空
		if (obj == null)
			return true;
		// ----------------根据各种对象类型判断是否值为空--------------
		if (obj instanceof String)
			return ((String) obj).trim().equals("");
		if (obj instanceof Collection) {
			Collection coll = (Collection) obj;
			return coll.size() == 0;
		}
		if (obj instanceof Map) {
			Map map = (Map) obj;
			return map.size() == 0;
		}
		if (obj.getClass().isArray())
			return Array.getLength(obj) == 0;
		else
			return false;
	}

	/**
	 * 组织html业务，显示值，供自定义标签用
	 *
	 * @param value 源字符串
	 * @param len 需要显示的源字符串长度
	 * @return 按 len 长度截取后的字符串
	 */
	public static String showValue(String value, int len) {
		// 判断是否为空
		if (isEmpty(value))
			return "&nbsp;";
		// 根据长度取值，并构建html语句
		if (value.length() <= len) {
			return value;
		} else {
			StringBuffer buf = new StringBuffer((new StringBuilder(
					"<span title='")).append(value).append("'>").toString());
			buf.append(value.substring(0, len));
			buf.append("...</span>");
			return buf.toString();
		}
	}

	/**
	 * 产生时间形式的uuid方法+后4位随机数
	 *
	 * @return 时间形式的uuid + 后4位随机数组成的字符串
	 */
	public static String getUUID() {
		// 定义uuid
		String uuId = "0";
		// 定义时间格式
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String tempId = sf.format(new Date());
		// 构造uuid
		if (Long.parseLong(uuId) >= Long.parseLong(tempId))
			uuId = (new StringBuilder(String.valueOf(Long.parseLong(uuId) + 1L)))
					.toString();
		else
			uuId = tempId;

		// 返回
		return uuId + get8RandomNum();
	}
	
	/**
	 * 产生时间形式的uuid方法+后4位随机数
	 *
	 * @return 时间形式的uuid + 后4位随机数组成的字符串
	 */
	public static String getPhoneUUID() {
		// 定义uuid
		String uuId = "0";
		// 定义时间格式
		SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String tempId = sf.format(new Date());
		// 构造uuid
		if (Long.parseLong(uuId) >= Long.parseLong(tempId))
			uuId = (new StringBuilder(String.valueOf(Long.parseLong(uuId) + 1L)))
					.toString();
		else
			uuId = tempId;

		// 返回
		return uuId + get4RandomNum();
	}
	
	/**
	 * 生成4位随机数
	 *
	 * @return 8位随机数
	 */
	public static String get4RandomNum() {
		Random random = new Random();
		String randomNum = "" + random.nextInt(10) + random.nextInt(10)
				+ random.nextInt(10) + random.nextInt(10);
		return randomNum;
	}


	/**
	 * 检查传入值是否为null并处理方法，null则转为空值，否则为其身
	 *
	 * @param s 
	 *            为字符串类
	 * @return 等传入参数为 null 时返回 "" ,否则返回源字符串本身
	 */
	public static String jugeAndFixNull(String s) {
		// 判断s是否为null，是则返回空串
		if (s == null) {
			return "";
		} else {// 否则返回其自身
			return s;
		}
	}

	/**
	 * 将一个字符串数组转换为long数组，by weiqiang.yang
	 *
	 * @param strArr 字符串数组
	 * @return 长整形数组
	 */
	public static Long[] strArrToLongArr(String[] strArr) {
		Long[] retArr = new Long[strArr.length];
		for (int i = 0; i < strArr.length; i++) {
			retArr[i] = Long.parseLong(strArr[i]);
		}
		return retArr;
	}

	/**
	 * 生成4位随机数
	 *
	 * @return 8位随机数
	 */
	public static String get8RandomNum() {
		Random random = new Random();
		String randomNum = "" + random.nextInt(10) + random.nextInt(10)
				+ random.nextInt(10) + random.nextInt(10)+ random.nextInt(10)+ random.nextInt(10)
				+ random.nextInt(10)+ random.nextInt(10);
		return randomNum;
	}
	
	public static String get6RandomNum() {
		Random random = new Random();
		String randomNum = "" + random.nextInt(10) + random.nextInt(10)
				+ random.nextInt(10) + random.nextInt(10)+ random.nextInt(10)+ random.nextInt(10);
		return randomNum;
	}
	
	/**
	 * 
	* @Title: cutStringTime
	* @Description: 因为从数据库中获取的时间格式为2013-11-05 17:26:39.0，要截取后面的".0"
	* @param @param time
	* @param @return  
	* @return String    
	* @author miketho
	* @date 2013年11月10日 上午8:43:58
	 */
	public static String cutStringTime(String time){
		if(time!=null&&!"".equals(time)&&!"null".equals(time)){
			time = time.trim();
			return time.substring(0, (time.length()-2));
		}
		return "";
	}
	/**
	 * 
	* @Title: strToInteger
	* @Description:将字符串转成Integer类型
	* @param @param num
	* @param @return  
	* @return Integer    
	* @author miketho
	* @date 2013年12月9日 下午2:24:48
	 */
	public static Integer strToInteger(String num){
		Integer count=0;
		if(!isEmpty(num)){
			if(num.endsWith(".0"))
			{
				num=num.substring(0, num.length()-2);
				count=Integer.parseInt(num);
			}else
			{
				count=Integer.parseInt(num);
			}
		}
		return count;
	}
	/**
	 * 
	* @Title: getUserate
	* @Description: 两字串符转换计算比率
	* @param @param str1 除数
	* @param @param str2被除数
	* @param @return  
	* @return String    
	* @author miketho
	* @date 2013年12月9日 下午3:01:42
	 */
	public static String changeUserate(String str1,String str2){
		double d=0;
		double d1=0;
		double d2=0;
		String result="";
		if(!isEmpty(str1)){
			 d1 = Double.parseDouble(str1);
		}
		if(!isEmpty(str1)){
			 d2 = Double.parseDouble(str2);
		}
		if(d2!=0){
			 result = String.format("%.2f", d1/d2);
		}
		return result;
	}
	/**
	 * 
	* @Title: changeSimpleTime
	* @Description: 将2013-01-12 12:12:32.0 转化为12:12 这个方法用于获取当前咪表费率的接口中
	* @param @param time
	* @param @return  
	* @return String    
	* @author miketho
	* @date 2013年12月17日 下午4:09:37
	 */
	public static String changeSimpleTime(String time){
		try {
			if (!isEmpty(time)) {
				time = time.substring(11, 16);
			}else{
				time="";
			}
		} catch (Exception e) {
			time="";
		}
		return time;
	}
	
	/**
	 * 
	* @Title: getTimeSimple
	* @Description: 将09:12  转化为0912格式
	* @param @param time
	* @param @return  
	* @return String    
	* @author miketho
	 * @return 
	 * @return 
	* @date 2013年12月21日 下午3:55:38
	 */
	public static String changeTime(String time){
		try {
			if(!isEmpty(time)){
				time=time.substring(0,2)+time.substring(3,5);
			}else{
				time="";
			}
		} catch (Exception e) {
			time="";
		}
		return time;
	}

	
}
