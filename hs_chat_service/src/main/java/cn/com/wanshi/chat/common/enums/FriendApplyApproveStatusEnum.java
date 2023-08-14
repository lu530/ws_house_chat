package cn.com.wanshi.chat.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zzc
 */

@Getter
@AllArgsConstructor
public enum FriendApplyApproveStatusEnum {

    /**
     * 同意
     */
    AGREE("同意", 1),

    REFUSE("拒绝", 2),
    UNFRIENDED("拉黑", 3),
    ;

    /**
     *  名字
     */
    public final String name;
    /**
     * 值
     */
    public final int value;
}
