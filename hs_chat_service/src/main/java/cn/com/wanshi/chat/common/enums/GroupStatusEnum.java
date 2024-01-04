package cn.com.wanshi.chat.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 好友关系表 的 状态 1正常 2删除
 * @author zzc
 */
@Getter
@AllArgsConstructor
public enum GroupStatusEnum {

    /**
     * 群状态 0正常 1解散
     */
    NORMAL(0),

    DISSOLUTION(1);

    /**
     * 群类型
     */
    private Integer value;
}
