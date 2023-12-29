package cn.com.wanshi.chat.common.constants;
 
import cn.com.wanshi.chat.group.entity.ImGroupMember;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
 
/**
 * @author 500007
 * @ClassName:
 * @Description:
 * @date 2023年07月02日 19:12:42
 */
public class General {


    /**
     * 管理一个全局map，维护用户Id 到 ChannelId的关系
     */
    public static final ConcurrentHashMap<String, ChannelId> userIdChannelIdHashMap = new ConcurrentHashMap<>();


    /**
     * 管理一个全局map，维护用户ChannelId 到 userId的关系
     */
    public static final ConcurrentHashMap<ChannelId, String> channelIdUserIdHashMap = new ConcurrentHashMap<>();



    /**
     * 管理一个全局map，保存连接进服务端的通道数量
     */
    public static final ConcurrentHashMap<ChannelId, ChannelHandlerContext> CHANNEL_MAP = new ConcurrentHashMap<>();
 
    /**
     * 管理一个全局mao, 保存连接进服务器的各个通道类型
     */
    public static final ConcurrentHashMap<ChannelId, Boolean> CHANNEL_TYPE_MAP = new ConcurrentHashMap<>();


    /**
     * 管理一个全局mao, groupId -> List<members>
     */
    public static final ConcurrentHashMap<String, List<ImGroupMember>> GROUP_TO_MEMBERS_MAP = new ConcurrentHashMap<>();
 
}