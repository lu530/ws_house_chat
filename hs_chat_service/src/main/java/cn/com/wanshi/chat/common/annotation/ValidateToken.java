package cn.com.wanshi.chat.common.annotation;


import cn.com.wanshi.chat.common.model.BaseReq;

import java.lang.annotation.*;

/**
 * 校验用户token,方法上必须有一个继承于{@link BaseReq}的对象
 *
 * @author zhifeng.zhou
 * @description checkToken 注：只作用在controller对外提供的方法
 * @date 2020/12/2 19:26
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidateToken {
    /**
     * 是否根据token，取得UserInfoDto
     *
     * @return
     */
    boolean needUserInfo() default false;

    boolean checkToken() default true;

}
