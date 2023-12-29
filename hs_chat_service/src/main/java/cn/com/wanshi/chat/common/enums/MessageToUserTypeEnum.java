package cn.com.wanshi.chat.common.enums;


import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息类型 1 普通用户 2 系统公告全部用户 3 群用户
 * @author zzc
 */
@Getter
@AllArgsConstructor
public enum MessageToUserTypeEnum {

    /**
     * 普通用户
     */
    NORMAL_USER(1),

    /**
     * 系统公告全部用户
     */
    SYSTEM_NOTICE(2),


    /**
     * 群用户
     */
    GROUP_USERS(3),
    ;


    /**
     * 值
     */
    public final Integer type;



    public static MessageToUserTypeEnum getEnumByType(Integer type){
        for (MessageToUserTypeEnum typeEnum : MessageToUserTypeEnum.values()) {
            if(type.equals(typeEnum.type)){
                return typeEnum;
            }
        }
        return null;
    }

}
