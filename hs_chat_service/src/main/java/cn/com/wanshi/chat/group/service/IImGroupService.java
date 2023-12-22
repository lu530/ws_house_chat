package cn.com.wanshi.chat.group.service;

import cn.com.wanshi.chat.group.entity.ImGroup;
import cn.com.wanshi.chat.group.model.req.GroupInitReq;
import cn.com.wanshi.chat.group.model.resp.GroupInitResp;
import cn.com.wanshi.common.ResponseVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 群消息表 服务类
 * </p>
 *
 * @author zzc
 * @since 2023-12-15
 */
public interface IImGroupService extends IService<ImGroup> {

    ResponseVO<GroupInitResp> groupInit(GroupInitReq req);
}
