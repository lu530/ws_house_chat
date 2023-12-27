package cn.com.wanshi.chat.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 申请加群选项包括如下几种:0 表示禁止任何人申请加入、1 表示需要群主或管理员审批、2 表示允许无需审批自由加入群组
 * @author zzc
 */
@Getter
@AllArgsConstructor
public enum GroupApplyJoinTypeEnum {

    /**
     * 0 表示禁止任何人申请加入
     */
    PROHIBIT(0),

    /**
     * 表示需要群主或管理员审批
     */
    NEED_EXAMINE(1),


    /**
     * 表示允许无需审批自由加入群组
     */
    FREE(2);



    private Integer value;


}
