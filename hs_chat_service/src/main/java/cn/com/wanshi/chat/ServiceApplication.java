package cn.com.wanshi.chat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author zzc
 */
@SpringBootApplication(scanBasePackages = {"cn.com.wanshi.chat"})
@EnableTransactionManagement
@MapperScan("cn.com.wanshi.chat.*.mapper")
public class ServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
        System.out.println("启动完成 swagger访问地址:http://127.0.0.1:8080/doc.html" );
    }
}
