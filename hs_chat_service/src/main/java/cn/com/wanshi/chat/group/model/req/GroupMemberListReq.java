package cn.com.wanshi.chat.group.model.req;

import cn.com.wanshi.chat.common.model.BaseReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * @author zzc
 */
@Data
public class GroupMemberListReq extends BaseReq {




    /**
     * 群Id编号
     */
    @NotBlank
    @ApiModelProperty(value = "群Id编号")
    private String groupId;







}
