package cn.com.wanshi.chat.user.dao;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

/**
 * @author: zhongzhicheng
 * @description: 数据库用户token实体类
 **/

@Data
@TableName("im_user_data")
public class ImUserTokenEntity {


    /**
     * 主键ID
     */
    @Id
    private String tockenId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * TOKEN
     */
    private String token;

    /**
     * 生成时间
     */
    private Date creatTime;

    /**
     * 状态是否有效 0为禁用 1为有效
     */
    private Integer status;

    /**
     * 来源 IOS ANDROID  WX
     */
    private String clientType;

    /**
     * 设备ID
     */
    private String cuId;

    private Long id;

    /**
     * 登录方式 : 0  其他登录方式 ; 1 一键登录';
     */
    private Integer loginType;




}
