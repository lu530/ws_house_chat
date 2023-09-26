package cn.com.wanshi.chat.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 好友关系表 的 状态 1正常 2删除
 * @author zzc
 */
@Getter
@AllArgsConstructor
public enum FriendshipStatusEnum {


    /**
     * 正常
     */
    NORMAL(1),
    /**
     * 删除
     */
    DELETE(2);
    /**
     * 值
     */
    public final int value;
}
