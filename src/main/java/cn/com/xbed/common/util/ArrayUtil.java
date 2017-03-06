package cn.com.xbed.common.util;

import com.alibaba.fastjson.JSONArray;

/**
 * Created by YUAN on 2016/12/5.
 * 数组的工具类
 */
public class ArrayUtil {

    /**
     * 数组转换为String [1,2,3] => 1,2,3
     *
     * @param array
     * @return
     */
    public static String arr2String(Object[] array) {
        String ret = "";
        for (Object sub : array) {
            ret += sub + ",";
        }
        if (ret.trim().length() > 0) {
            return ret.substring(0, ret.length() - 1);
        }
        return ret;
    }

    /**
     * 数组转换为String [1,2,3] => 1,2,3
     *
     * @param array
     * @return
     */
    public static String jsonArr2String(JSONArray array) {
        String ret = "";
        for (Object sub : array) {
            ret += sub + ",";
        }
        if (ret.trim().length() > 0) {
            return ret.substring(0, ret.length() - 1);
        }
        return ret;
    }

    public static void main(String[] args) {
        JSONArray arr = new JSONArray();
        arr.add("1");
        arr.add("2");
        arr.add("3");
        System.out.println(jsonArr2String(arr));
    }
}
