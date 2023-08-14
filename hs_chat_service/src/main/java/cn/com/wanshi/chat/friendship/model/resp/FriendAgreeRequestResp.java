package cn.com.wanshi.chat.friendship.model.resp;


import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @author admin
 */
@Data
@Builder
public class FriendAgreeRequestResp {


    @ApiModelProperty(value = "1 已经送达 3 已经被拉黑")
    private Integer status;

    private String message;


}
