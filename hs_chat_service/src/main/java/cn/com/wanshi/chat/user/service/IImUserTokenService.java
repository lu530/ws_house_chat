package cn.com.wanshi.chat.user.service;

import cn.com.wanshi.chat.user.entity.ImUserToken;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * TOKEN 服务类
 * </p>
 *
 * @author zzc
 * @since 2023-07-19
 */
public interface IImUserTokenService extends IService<ImUserToken> {

    String createToken(String userId, String clientType, String cuid);

}
