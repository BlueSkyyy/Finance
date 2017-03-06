package cn.com.xbed.common.key;/**
 * Created by Administrator on 2017/3/6.
 */

import cn.com.xbed.common.source.DataSourceSwitcher;

import java.lang.annotation.*;

/**
 * @Description:设置只读数据源的自定义注解
 * @author：Tom
 * @create 2017-03-06 17:50
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Only {

    String name() default DataSourceSwitcher.MASTER_DATA_SOURCE;

    public static String master = "dataSource1";

    public static String slave1 = "dataSource2";

    public static String slave2 = "dataSource3";
}
