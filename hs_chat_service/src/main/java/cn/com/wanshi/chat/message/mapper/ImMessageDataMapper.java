package cn.com.wanshi.chat.message.mapper;

import cn.com.wanshi.chat.message.entity.ImMessageData;
import cn.com.wanshi.chat.message.model.req.ImMessageCountReq;
import cn.com.wanshi.chat.message.model.resp.ImMessageCountResp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author zzc
 * @since 2023-10-08
 */
public interface ImMessageDataMapper extends BaseMapper<ImMessageData> {

    /**
     * 消息统计
     * @param req
     * @return
     */
    List<ImMessageCountResp> messageCount(ImMessageCountReq req);
}
