package cn.com.wanshi.chat.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 好友关系表 的 状态 1正常 2删除
 * @author zzc
 */
@Getter
@AllArgsConstructor
public enum GroupTypeEnum {


    /**
     * 私有的
     */
    PRIVATE(1),

    PUBLIC(2);



    /**
     * 群类型
     */
    private Integer value;





}
