package cn.com.wanshi.chat.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author Leslie.Lam
 * @create 2019-08-14 20:30
 **/
@Component
public class AppContextHelper implements ApplicationContextAware, EnvironmentAware {

    private static ApplicationContext applicationContext;

    private static Environment environment;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AppContextHelper.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        AppContextHelper.environment = environment;
    }

    public static <T> T getBean(Class<T> clazz){
        return applicationContext.getBean(clazz);
    }




    public static <T> T getBean(String name, Class<T> clazz){
        return applicationContext.getBean(name,clazz);
    }

    public static String getAppName(){
        return environment.getProperty("spring.application.name","old_school");
    }

}
