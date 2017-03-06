package cn.com.xbed.common.source;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Hashtable;
import java.util.Map;

/**
 * @Description:基础数据源配置
 * @author：Tom
 * @create 2017-03-06 14:19
 **/
@Configuration
public class BaseDataSource {
    @Autowired
    private Environment env;

    /**
     * 动态数据源
     * @param masterDataSource
     * @param slaveDataSource
     * @return
     */
    @Bean(name = "dynamicDataSource")
    @Primary
    public DynamicDataSource dynamicDataSource(
            @Qualifier("masterDataSource")DataSource masterDataSource,
            @Qualifier("slaveDataSource")DataSource slaveDataSource){
        DynamicDataSource source = new DynamicDataSource();
        Map<Object, Object> map = new Hashtable<>();
        map.put("master", masterDataSource);
        map.put("slave", slaveDataSource);
        source.setTargetDataSources(map);
        source.setDefaultTargetDataSource(masterDataSource);
        return source;
    }

    /**
     * 主数据源
     * @return
     */
    @Bean(name = "masterDataSource")
    public DataSource masterDataSource(){
        ComboPooledDataSource cpds = new ComboPooledDataSource(true);
        cpds.setDataSourceName("master");
        cpds.setJdbcUrl(env.getProperty("datasource.write.url"));
        try {
            cpds.setDriverClass(env.getProperty("datasource.write.driver-class-name"));
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        cpds.setUser(env.getProperty("datasource.write.username"));
        cpds.setPassword(env.getProperty("datasource.write.password"));
        cpds.setMaxPoolSize(Integer.valueOf(env.getProperty("datasource.write.maxPoolSize")));//最大连接数
        cpds.setMinPoolSize(Integer.valueOf(env.getProperty("datasource.write.minPoolSize")));//最小连接数
        cpds.setInitialPoolSize(Integer.valueOf(env.getProperty("datasource.write.initialPoolSize")));//初始化连接数
        cpds.setTestConnectionOnCheckin(true);
        /**
         * 时间范围内未使用，则丢弃该连接　
         * mysql 默认是八个钟丢弃未使用的连接
         * show variables like '%timeout';  查询mysql 根据 wait_timeout 参数来优化
         */
        cpds.setMaxIdleTime(Integer.valueOf(env.getProperty("datasource.write.maxIdleTime")));
        return cpds;
    }

    /**
     * 辅数据源
     * @return
     */
    @Bean(name = "slaveDataSource")
    public DataSource slaveDataSource(){
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDataSourceName("slave");
        cpds.setJdbcUrl(env.getProperty("datasource.read.url"));
        try {
            cpds.setDriverClass(env.getProperty("datasource.read.driver-class-name"));
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        cpds.setUser(env.getProperty("datasource.read.username"));
        cpds.setPassword(env.getProperty("datasource.read.password"));
        cpds.setMaxPoolSize(Integer.valueOf(env.getProperty("datasource.read.maxPoolSize")));//最大连接数
        cpds.setMinPoolSize(Integer.valueOf(env.getProperty("datasource.read.minPoolSize")));//最小连接数
        cpds.setInitialPoolSize(Integer.valueOf(env.getProperty("datasource.read.initialPoolSize")));//初始化连接数
        cpds.setTestConnectionOnCheckin(true);
        /**
         * 时间范围内未使用，则丢弃该连接　
         * mysql 默认是八个钟丢弃未使用的连接
         * show variables like '%timeout';  查询mysql 根据 wait_timeout 参数来优化
         */
        cpds.setMaxIdleTime(Integer.valueOf(env.getProperty("datasource.write.maxIdleTime")));
        return cpds;
    }

}
