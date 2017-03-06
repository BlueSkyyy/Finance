package cn.com.xbed.common.source;
/**
 * @Description:读写库的切换
 * @author：Tom
 * @create 2017-03-06 15:07
 **/
public class DataSourceSwitcher {

    /**
     * 主库，读写库，只一个
     */
    public static final String MASTER_DATA_SOURCE = "master";
    /**
     * 从库，只读库，可有多个
     */
    public static final String[] SLAVE_DATA_SOURCE = {"slave"};
    /**
     * 线程本地环境
     */
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    /**
     * 设置数据源
     * @param customerType
     */
    public static final synchronized void setCustomerType(String customerType) {
        contextHolder.set(customerType);
    }

    /**
     * 获取数据源
     * @return
     */
    public static String getCustomerType(){
        return contextHolder.get();
    }

    /**
     * 设置主库
     */
    public static void setMaster(){
        setCustomerType(MASTER_DATA_SOURCE);
    }

    /**
     * 设置从库
     */
    public static void setSlave(){
        setCustomerType(SLAVE_DATA_SOURCE[0]);
    }

    /**
     * 清除数据源
     */
    public static void clearCustomerType(){
        contextHolder.remove();
    }

}
