package cn.com.wanshi.chat.message.model.resp;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zhongzhicheng
 * 用户消息数量请求体 返回类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImMessageCountResp {

    @ApiModelProperty(value = "发送方id")
    private String fromId;



    @ApiModelProperty(value = "消息数量")
    private Integer count;

}
