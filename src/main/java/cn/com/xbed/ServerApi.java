package cn.com.xbed;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @author：Tom
 * @create 2017-03-03 13:37
 **/
@Configuration
@EnableAutoConfiguration
@ComponentScan("cn.com.xbed")
public class ServerApi {
    public static void main(String[] args) {
        SpringApplication.run(ServerApi.class, args);
    }
}