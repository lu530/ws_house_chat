package cn.com.wanshi.chat.common.utils;

import cn.com.wanshi.common.utils.DateUtil;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;

import java.text.DecimalFormat;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author zxj
 * <p>
 * 取序列号类
 */
public class SerialNoUtil implements InitializingBean {
    private static volatile AtomicInteger atomicInteger = new AtomicInteger(0);
    private static String mac;
    private static ThreadLocal<DecimalFormat> threadLocal = ThreadLocal.withInitial(()-> new DecimalFormat("00000"));
    @Autowired
    private StringRedisTemplate redisTemplate;
    
    @Override
    public void afterPropertiesSet() {
        mac = getMac();
    }
	
    private String getMac() {
		Long increment = 0L;
		try {
	        RedisAtomicLong entityIdCounter = new RedisAtomicLong(RedisCacheUtil.getKeyDir()+"getMac",redisTemplate.getConnectionFactory());
	        increment = entityIdCounter.getAndIncrement();
	        if(increment>=99){
	        	entityIdCounter.set(0);
	        }
		} catch (Exception e) {
			increment = Long.valueOf(RandomUtils.nextInt(0, 99));
		}
        DecimalFormat df00 = new DecimalFormat("00");//序列号
        String mac = df00.format(increment);
        System.out.println(RedisCacheUtil.getKeyDir()+"getMac:"+mac);
	    return mac;
    }
	
    /**
     * 生成19位的唯一序列号
     * <p>
     * 根据当前时间12位+2位mac+加5位计数器，一共19位
     *
     * @return 序列号：yyMMddHHmmss + 二位0~99mac  + 5位同一秒内自增数（支持10万并发）
     */
    public static  String getUNID(){
        String currentTime = DateUtil.getCurrentDateString("yyMMddHHmmss");
        //防止溢出
        if (atomicInteger.get() == Integer.MAX_VALUE){
            synchronized (SerialNoUtil.class){
                //double check
                if (atomicInteger.get() == Integer.MAX_VALUE){
                    atomicInteger = new AtomicInteger(0);
                }
            }
        }
        Integer num =  atomicInteger.incrementAndGet() % 100000 ;
        return currentTime + mac + threadLocal.get().format(num);
    }

}
