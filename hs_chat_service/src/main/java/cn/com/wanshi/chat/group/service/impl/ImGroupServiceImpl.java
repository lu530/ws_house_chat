package cn.com.wanshi.chat.group.service.impl;

import cn.com.wanshi.chat.group.entity.ImGroup;
import cn.com.wanshi.chat.group.mapper.ImGroupMapper;
import cn.com.wanshi.chat.group.model.req.GroupInitReq;
import cn.com.wanshi.chat.group.model.resp.GroupInitResp;
import cn.com.wanshi.chat.group.service.IImGroupService;
import cn.com.wanshi.common.ResponseVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 群消息表 服务实现类
 * </p>
 *
 * @author zzc
 * @since 2023-12-15
 */
@Service
public class ImGroupServiceImpl extends ServiceImpl<ImGroupMapper, ImGroup> implements IImGroupService {

    @Override
    public ResponseVO<GroupInitResp> groupInit(GroupInitReq req) {



        return null;
    }
}
