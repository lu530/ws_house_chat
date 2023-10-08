package cn.com.wanshi.chat.message.service.impl;

import cn.com.wanshi.chat.message.entity.ImMessageData;
import cn.com.wanshi.chat.message.mapper.ImMessageDataMapper;
import cn.com.wanshi.chat.message.service.IImMessageDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author zzc
 * @since 2023-10-08
 */
@Service
public class ImMessageDataServiceImpl extends ServiceImpl<ImMessageDataMapper, ImMessageData> implements IImMessageDataService {

}
