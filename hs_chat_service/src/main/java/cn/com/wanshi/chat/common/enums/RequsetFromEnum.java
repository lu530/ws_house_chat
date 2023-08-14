package cn.com.wanshi.chat.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zzc
 */

@Getter
@AllArgsConstructor
public enum RequsetFromEnum {

    /**
     * 自己发起的好友申请
     */
    FROM_MYSELF(1),
    FROM_OTHER(2);
    /**
     * 值
     */
    public final int value;



}
