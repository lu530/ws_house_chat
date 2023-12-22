package cn.com.wanshi.chat.group.service.impl;

import cn.com.wanshi.chat.group.entity.ImGroupMember;
import cn.com.wanshi.chat.group.mapper.ImGroupMemberMapper;
import cn.com.wanshi.chat.group.service.IImGroupMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 群成员关联表 服务实现类
 * </p>
 *
 * @author zzc
 * @since 2023-12-15
 */
@Service
public class ImGroupMemberServiceImpl extends ServiceImpl<ImGroupMemberMapper, ImGroupMember> implements IImGroupMemberService {

}
