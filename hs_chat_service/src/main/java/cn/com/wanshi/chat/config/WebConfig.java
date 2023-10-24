package cn.com.wanshi.chat.config;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

/**
 * @author zengjia, zhangjuntao
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 初始化转换器
        FastJsonHttpMessageConverter fastConvert = new FastJsonHttpMessageConverter();

        // 初始化一个转换器配置
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.BrowserCompatible,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.DisableCircularReferenceDetect);
        // 序列化配置
        fastJsonConfig.setSerializeConfig(getSerializeConfig());

        // 将配置设置给转换器并添加到HttpMessageConverter转换器列表中
        fastConvert.setFastJsonConfig(fastJsonConfig);

        // 设置默认编码为UTF-8
        fastConvert.setDefaultCharset(StandardCharsets.UTF_8);

        converters.add(fastConvert);
    }

    @Bean
    public RestTemplate restTemplate(){
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(6000);
        factory.setConnectTimeout(6000);
        return new RestTemplate(factory);
    }

    public static SerializeConfig getSerializeConfig() {
        SerializeConfig serializeConfig = SerializeConfig.globalInstance;
        // 解决大数转json精度丢失的问题，统一大数按字符串的形式返回
        serializeConfig.put(BigInteger.class, ToStringSerializer.instance);
        serializeConfig.put(Long.class, LongToStringSerializer.instance);
        serializeConfig.put(Long.TYPE, LongToStringSerializer.instance);
        serializeConfig.put(BigDecimal.class, BigDecimalToStringSerializer.instance);
        // Date类型默认返回long时间戳， 如果Date为null则返回0
        serializeConfig.put(Date.class, DateToLongSerializer.instance);
        return serializeConfig;
    }
}
