package cn.com.wanshi.chat.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 群人员里面的角色枚举
 * 0 普通成员, 1 管理员, 2 群主， 3 禁言
 * @author zzc
 */
@Getter
@AllArgsConstructor
public enum GroupRoleEnum {

    /**
     * 0 普通成员
     */
    NORMAL(0),

    ADMINISTRATORS(1),
    GROUP_LEADER(2),
    PROHIBITION(3),
    ;


    private Integer value;







}
