package cn.com.xbed.common.util;

import cn.com.xbed.commond.LogTool;
import cn.com.xbed.config.ApplicationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class LogUtil {
	final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private LogTool logTool;
	@Autowired
	private ApplicationConfig config;
	
	public void createLog(final Integer logType,final Integer logLevel, final String logContent){
		createLog(logType, logLevel, logContent, null, null);
    }
	
	public void createLog(final Integer logType,final Integer logLevel, final String logContent,final String logRemark){
		createLog(logType, logLevel, logContent, logRemark, null);
    }
	
	public void createLog(final Integer logType,final Integer logLevel, final String logContent,final String logRemark,final String logCode){
    	new Thread(new Runnable() {
			public void run() {
				try{
					logger.info("报警类型:{} 报警等级:{} 报警内容:{} 报警备注:", logType, logLevel, logContent, logRemark);
					logTool.createLog(config.systemName, logType, logLevel, "0", logContent, logRemark, logCode, new Date());
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}).start();;
    }
}
