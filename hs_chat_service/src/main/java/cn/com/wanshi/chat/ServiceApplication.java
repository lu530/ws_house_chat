package cn.com.wanshi.chat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author zzc
 */
@SpringBootApplication(scanBasePackages = {"cn.com.wanshi.chat"})
@EnableTransactionManagement
@EnableAspectJAutoProxy(proxyTargetClass = true)
@MapperScan("cn.com.wanshi.chat.*.mapper")
public class ServiceApplication extends WebMvcConfigurerAdapter {
    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
        System.out.println("启动完成 swagger访问地址:http://127.0.0.1:8080/doc.html" );
    }

    @Override
     public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
         configurer.favorPathExtension(false);
     }
}
