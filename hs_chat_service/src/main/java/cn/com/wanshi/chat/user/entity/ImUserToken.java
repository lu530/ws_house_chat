package cn.com.wanshi.chat.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * TOKEN
 * </p>
 *
 * @author zzc
 * @since 2023-07-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="ImUserToken对象", description="TOKEN")
public class ImUserToken implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "主键ID")
    @TableField("TOCKEN_ID")
    private String tockenId;

    @ApiModelProperty(value = "用户ID")
    @TableField("USER_ID")
    private String userId;

    @ApiModelProperty(value = "TOKEN")
    @TableField("TOKEN")
    private String token;

    @ApiModelProperty(value = "生成时间")
    @TableField("CREATE_TIME")
    private Date createTime;

    @ApiModelProperty(value = "状态是否有效 0为禁用 1为有效")
    @TableField("STATUS")
    private Integer status;

    @ApiModelProperty(value = "来源 IOS ANDROID  WX")
    @TableField("CLIENT_TYPE")
    private String clientType;

    @ApiModelProperty(value = "设备ID")
    @TableField("CUID")
    private String cuid;

    @ApiModelProperty(value = "登录方式 : 0  其他登录方式 ; 1 一键登录")
    @TableField("LOGIN_TYPE")
    private Integer loginType;


}
