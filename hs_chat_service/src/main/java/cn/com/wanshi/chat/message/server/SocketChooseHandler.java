package cn.com.wanshi.chat.message.server;
 
/**
 * @author 500007
 * @ClassName:
 * @Description:
 * @date 2023年06月30日 21:29:17
 */
 
import cn.com.wanshi.chat.common.constants.General;
import cn.com.wanshi.chat.common.utils.AppContextHelper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
 
import javax.annotation.Resource;
import java.util.List;
 
/**
 * @author dzx
 * @ClassName:
 * @Description:  协议初始化解码器.用来判定实际使用什么协议，以用来处理前端websocket或者后端netty客户端的连接或通信
 * @date 2023年06月30日 21:31:24
 */
@Component
@Slf4j
public class SocketChooseHandler extends ByteToMessageDecoder {
    /** 默认暗号长度为23 */
    private static final int MAX_LENGTH = 23;
    /** WebSocket握手的协议前缀 */
    private static final String WEBSOCKET_PREFIX = "GET /";
    @Resource
    private AppContextHelper springContextUtil;
 
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        String protocol = getBufStart(in);
        if (protocol.startsWith(WEBSOCKET_PREFIX)) {
            AppContextHelper.getBean(PipelineAdd.class).websocketAdd(ctx);
 
            //对于 webSocket ，不设置超时断开
            ctx.pipeline().remove(IdleStateHandler.class);
//            ctx.pipeline().remove(LengthFieldBasedFrameDecoder.class);
            this.putChannelType(ctx.channel().id(), true);
        }else{
            this.putChannelType(ctx.channel().id(), false);
        }
        in.resetReaderIndex();
        ctx.pipeline().remove(this.getClass());
    }
 
    private String getBufStart(ByteBuf in){
        int length = in.readableBytes();
        if (length > MAX_LENGTH) {
            length = MAX_LENGTH;
        }
 
        // 标记读位置
        in.markReaderIndex();
        byte[] content = new byte[length];
        in.readBytes(content);
        return new String(content);
    }
 
    /**
     *
     * @param channelId
     * @param type
     */
    public void putChannelType(ChannelId channelId,Boolean type){
        if (General.CHANNEL_TYPE_MAP.containsKey(channelId)) {
            log.info("Socket------客户端【" + channelId + "】是否websocket协议："+type);
        } else {
            //保存连接
            General.CHANNEL_TYPE_MAP.put(channelId, type);
            log.info("Socket------客户端【" + channelId + "】是否websocket协议："+type);
        }
    }
}
 