package cn.com.wanshi.chat.group.model.req;

import cn.com.wanshi.chat.common.model.BaseReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author zzc
 */
@Data
public class GroupInitReq extends BaseReq {


    /**
     * 群名称
     */
    @ApiModelProperty(value = "群名称")
    private String groupName;


    /**
     * 群成员 是userId集合
     */
    @ApiModelProperty(value = "群成员 是userId集合")
    private List<String> GroupMembers;








}
