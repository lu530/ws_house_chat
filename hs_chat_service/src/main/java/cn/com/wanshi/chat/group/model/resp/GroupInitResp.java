package cn.com.wanshi.chat.group.model.resp;


import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author admin
 */
@Data
@Builder
public class GroupInitResp {


    /**
     * 群名称
     */
    @ApiModelProperty(value = "群名称")
    private String groupName;


    @ApiModelProperty(value = "群头像")
    private String photo;


    @ApiModelProperty(value = "群id")
    private String groupId;





}
