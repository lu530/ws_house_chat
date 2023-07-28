package cn.com.wanshi.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

/**
 * @Author ：zhongzhicheng
 * @Date ：2023/6/30
 * @Desc ：会员等级redis key
 */
@Getter
@AllArgsConstructor
public enum RedisKeyConstants {


    //用户token 要有15天
    USER_TOKEN("hs:chat:user:token", 60*24*15L, TimeUnit.MINUTES),
    USER_RECEIPT("hs:chat:user:receipt", 5L, TimeUnit.MINUTES),
    ;

    private final String key;
    private final Long timeout;
    private final TimeUnit unit;


}
