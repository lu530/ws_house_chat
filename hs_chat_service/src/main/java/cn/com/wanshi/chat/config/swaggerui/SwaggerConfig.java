package cn.com.wanshi.chat.config.swaggerui;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @description:
 * @author: zhongzhicheng
 * @date: 2023/07/05 11:30
 */
@EnableSwagger2
@EnableWebMvc
@Configuration
public class SwaggerConfig extends WebMvcConfigurerAdapter {
    private static Logger log = LoggerFactory.getLogger(SwaggerConfig.class);
    Properties p = null;
    private static String title;
    private static String desc;
    private static String groupName;
    private static String author;
    private static String version;

    static {
        Properties p = new Properties();

        try {
            if (Thread.currentThread().getContextClassLoader().getResourceAsStream("swagger.properties") != null) {
                InputStreamReader inputStream = new InputStreamReader(Thread.currentThread().getContextClassLoader().getResourceAsStream("swagger.properties"), "UTF-8");
                p.load(inputStream);
            }
        } catch (IOException var2) {
            var2.printStackTrace();
        }

        title = isEmpty(p.getProperty("title")) ? "ws chat API管理" : p.getProperty("title");
        desc = isEmpty(p.getProperty("desc")) ? "ws chat API接口说明文档" : p.getProperty("desc");
        groupName = isEmpty(p.getProperty("groupName")) ? "ws chat API" : p.getProperty("groupName");
        author = isEmpty(p.getProperty("author")) ? "万事屋IT部" : p.getProperty("author");
        version = isEmpty(p.getProperty("version")) ? "1.0.0" : p.getProperty("version");
    }

    public SwaggerConfig() {
    }

    public static void changeTitle(String title2, String groupName2){
        title = title2;
        groupName = groupName2;
    }


    @Bean
    public  Docket wshsApi() {
        log.info("Starting Swagger*****");
        StopWatch watch = new StopWatch();
        watch.start();
        log.info("title: {}, groupName:{}", title, groupName);
        Docket swaggerDocket = (new Docket(DocumentationType.SWAGGER_2))
                .groupName(groupName)
                .apiInfo(apiInfo())
                .enable(true)
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.com.wanshi.chat"))
                .paths(PathSelectors.any())
                .build();
        watch.stop();
        log.info("title: {}, groupName:{}", title, groupName);
        log.info("Started Swagger in {} ms", watch.getTotalTimeMillis());
        return swaggerDocket;
    }

    private  ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(title, desc, version, title, author, (String)null, (String)null);
        return apiInfo;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(new String[]{"swagger-ui.html"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/"});
        registry.addResourceHandler(new String[]{"doc.html"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/"});
        registry.addResourceHandler(new String[]{"/webjars/**"}).addResourceLocations(new String[]{"classpath:/META-INF/resources/webjars/"});
        super.addResourceHandlers(registry);
    }

    private static boolean isEmpty(String str) {
        return str == null || "".equals(str);
    }
}
