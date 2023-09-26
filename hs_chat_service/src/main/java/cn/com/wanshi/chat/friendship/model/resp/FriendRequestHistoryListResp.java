package cn.com.wanshi.chat.friendship.model.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zzc
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public  class FriendRequestHistoryListResp extends FriendResp{

        @ApiModelProperty(value = "好友申请ID")
        private Long friendshipRequestId;

        @ApiModelProperty(value = "审批状态 1同意 2拒绝 3拉入黑名单")
        private Integer approveStatus;


        @ApiModelProperty(value = "好友名称备注")
        private String friendNickName;

        @ApiModelProperty(value = "申请说明")
        private String applyReason;

        @ApiModelProperty(value = "好友来源 1账号 2 邮箱 3 手机号 4推荐 5扫描")
        private Integer addSource;

        @ApiModelProperty(value = "1 自己请求添加 2 对方请求添加 ")
        private Integer requsetFrom;

        @ApiModelProperty(value = "是否是三天内 1是 0 否")
        private Integer inThreeDayFlay;


        @ApiModelProperty(value = "生成时间")
        private Date createTime;

    
}