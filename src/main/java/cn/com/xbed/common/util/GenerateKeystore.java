package cn.com.xbed.common.util;/**
 * Created by YUAN on 2017/1/12.
 *
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Java生成HTTPS证书
 *
 * @author fomeiherz
 * @Package cn.com.xbed.common.util
 * @Title GenerateKeystore
 * @create 2017-01-12 9:59
 */
public class GenerateKeystore extends Thread {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void run() {
        try {
            while (true) {
                String store_pass = "445566";
                String key_pass = "445566";
                String src_store_pass = "445566";
                String key_store_file = "/etc/letsencrypt/live/gate.xbed.com.cn/gate.keystore";
                String pkcs_12_file = "/etc/letsencrypt/live/gate.xbed.com.cn/pkcs.p12";
                String domain_url = "gate.xbed.com.cn";
                pkcs12ToKeystore(store_pass, key_pass, src_store_pass, key_store_file, pkcs_12_file, domain_url);
                
                { //删除原有的文件
                    File file = new File(key_store_file);
                    if(file.exists()) {
                        file.deleteOnExit();
                    }
                }
                
                
                Thread.sleep(24 * 3600 * 1000); //休眠24小时
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    /**
     * 参考链接：https://maximilian-boehm.com/hp2121/Create-a-Java-Keystore-JKS-from-Let-s-Encrypt-Certificates.htm
     *
     * description : 把letsencrypt的HTTPS证书pkcs12转换为Java的keystore
     */
    public void pkcs12ToKeystore(String store_pass, String key_pass, String src_store_pass, String key_store_file, String pkcs_12_file, String domain_url) {
        String[] command = new String[]{
                "/usr/local/java/bin/keytool",
                "-importkeystore",
                "-deststorepass",
                store_pass,
                "-destkeypass",
                key_pass,
                "-destkeystore",
                key_store_file,
                "-srckeystore",
                pkcs_12_file,
                "-srcstoretype",
                "PKCS12",
                "-srcstorepass",
                src_store_pass,
                "-alias",
                domain_url,
        };
        execCommand(command);
    }
    
    /**
     * 执行String[]命令
     * @param cmd
     */
    public void execCommand(String[] cmd) {
        for (int i = 0; i < cmd.length; i++) {
            logger.info(cmd[i] + " ");
        }
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 执行String命令
     * @param arstringCommand
     */
    public void execCommand(String arstringCommand) {
        try {
            Runtime.getRuntime().exec(arstringCommand);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 生成密钥keystore
     */
    public void genkey() {
        String[] arstringCommand = new String[]{
                "cmd ", "/k",
                "start", // cmd Shell命令  
                "keytool",
                "-genkeypair", // -genkey表示生成密钥  
                //"-validity", // -validity指定证书有效期(单位：天)，这里是36000天  
                //"36500",
                //"-keysize",//     指定密钥长度  
                //"1024",
                "-alias", // -alias指定别名，这里是ss  
                "\"tomcat\"",
                "-keyalg", // -keyalg 指定密钥的算法 (如 RSA DSA（如果不指定默认采用DSA）)  
                "RSA",
                "-keystore", // -keystore指定存储位置，这里是d:/demo.keystore  
                "E:\\yuan\\tomcat.keystore",
                "-dname",// CN=(名字与姓氏), OU=(组织单位名称), O=(组织名称), L=(城市或区域名称),  
                // ST=(州或省份名称), C=(单位的两字母国家代码)"  
                "CN=www.xbed.gateway.dev.com.cn, OU=Xbed, O=IT, L=GZ, ST=GD, C=CN",
                "-storepass", // 指定密钥库的密码(获取keystore信息所需的密码)  
                "tomcat",
                "-keypass",// 指定别名条目的密码(私钥的密码)  
                "tomcat",
                "-v"// -v 显示密钥库中的证书详细信息  
        };
        execCommand(arstringCommand);
    }

    /**
     * 导出cert证书文件
     */
    public void export() {
        String[] arstringCommand = new String[]{
                "cmd ", "/k",
                "start", // cmd Shell命令  
                "keytool",
                "-export", // - export指定为导出操作   
                "-keystore", // -keystore指定keystore文件，这里是d:/demo.keystore  
                "E:\\yuan\\tomcat.keystore",
                "-alias", // -alias指定别名，这里是ss  
                "tomcat",
                "-file",//-file指向导出路径  
                "E:\\yuan\\tomcat.cer",
                "-storepass",// 指定密钥库的密码  
                "tomcat"

        };
        execCommand(arstringCommand);
    }

    public static void main(String[] args) {
        GenerateKeystore generateKeystore = new GenerateKeystore();
        generateKeystore.genkey();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        generateKeystore.export();
    }
}
