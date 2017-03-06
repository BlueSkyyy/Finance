package cn.com.xbed.common.listner;


import cn.com.xbed.common.key.Only;
import cn.com.xbed.common.source.DataSourceSwitcher;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @Description:
 * @author：Tom
 * @create 2017-03-06 17:11
 **/
public class ChangeDataSourceListener {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @After("execution(* org.springframework.transaction.interceptor.AbstractFallbackTransactionAttributeSource.getTransactionAttribute(..))")
    public void afterGetTransactionAttribute(JoinPoint jp){
        Object[] args = jp.getArgs();
        if(args == null || args.length != 2){
            return;
        }
        Method method = (Method) args[0];
        if(method.toString().indexOf("cn.com.xbed.business") == -1){
            return;
        }
        Class<?> impl = (Class<?>) args[1];
        changeDataSource(method, impl);
    }

    /**
     * 切换数据源
     * @param method
     * @param targetClass
     */
    private void changeDataSource(Method method, Class<?> targetClass) {
        if(method.toString().indexOf("cn.com.xbed.business") == -1){
            return;
        }
        if(logger.isDebugEnabled()){
            logger.debug("准备切换数据源...............................................");
            logger.debug("method={}", method);
            logger.debug("targetClass={}", targetClass);
        }
        Method[] implMethods = targetClass.getMethods();
        Boolean isOnly = false;
        for (Method implMethod : implMethods) {
            if(method.getName().equals(implMethod.getName())){
                Class<?>[] methodClass = method.getParameterTypes();
                Class<?>[] implMethodClass = method.getParameterTypes();
                if(methodClass.length == methodClass.length){
                    Boolean tmp = true;
                    for (int i = 0; i < methodClass.length; i++) {
                        if(methodClass[1] != implMethodClass[i]){
                            tmp = false;
                            break;
                        }
                    }
                    if(tmp && implMethod.isAnnotationPresent(Only.class)){
                        isOnly = true;
                        break;
                    }
                }
            }
        }
        if(isOnly){
            if(logger.isDebugEnabled()){
                logger.debug("切换成读源....");
            }
            DataSourceSwitcher.setSlave();
        }else {
            if(logger.isDebugEnabled()){
                logger.debug("切换成写源....");
            }
            DataSourceSwitcher.setMaster();
        }

    }
}
