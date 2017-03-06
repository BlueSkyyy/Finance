package cn.com.xbed.common.source;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @Description:
 * @author：Tom
 * @create 2017-03-06 15:03
 **/
public class DynamicDataSource extends AbstractRoutingDataSource{
    @Override
    protected Object determineCurrentLookupKey() {
        if(logger.isDebugEnabled()){
            logger.debug("" + DataSourceSwitcher.getCustomerType());
        }
        return DataSourceSwitcher.getCustomerType();
    }
}
