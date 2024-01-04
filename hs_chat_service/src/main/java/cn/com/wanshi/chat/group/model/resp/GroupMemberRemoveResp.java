package cn.com.wanshi.chat.group.model.resp;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zzc
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupMemberRemoveResp extends GroupInitResp{


    @ApiModelProperty(value = "成功个数")
    private Integer successNum;


}
