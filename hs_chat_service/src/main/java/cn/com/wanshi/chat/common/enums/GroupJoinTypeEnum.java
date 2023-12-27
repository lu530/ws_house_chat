package cn.com.wanshi.chat.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 群人员里面的角色枚举
 * 加入方式 0创建人 1 搜索群, 2 好友拉, 3 扫描进群
 * @author zzc
 */
@Getter
@AllArgsConstructor
public enum GroupJoinTypeEnum {


    /**
     * 搜索进群
     */
    CREATOR(0),
    SEARCH(1),
    FRIEND_RECOMMEND(2),
    SCAN_CODE(3),
    ;


    private Integer value;

}
