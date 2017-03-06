package cn.com.xbed.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: List工具类
 * @author 蔡俊杰 114603043@qq.com
 * @date 2014年3月4日 下午2:17:32
 */
public class ListUtil {
	/**
	 * 从List中获取第一个元素
	 * @param <T>
	 * @param list 集合
	 * @return 
	 * @return Object 集合里面的第一个元素
	 */
	public static <T> T getFirstOne(List<T> list){
		T obj = null;
		if(list!=null && list.size()>0){
			obj = list.get(0);
		}
		return obj;
	}
	/**
	 * @Description: 判断list是不是空的
	 * @author 蔡俊杰 114603043@qq.com
	 * @date 2014年3月4日 下午2:16:45
	 * @param list 集合
	 * @return boolean 如果是null或者是空的，都返回true;否则返回false;
	 */
	public static boolean isEmpty(List<?> list){
		return null==list||list.isEmpty();
	}
	
	public static List createList(){
		return new ArrayList();
	}
}
