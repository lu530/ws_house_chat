package cn.com.wanshi.chat.message.server;


import cn.com.wanshi.chat.common.constants.General;
import cn.com.wanshi.chat.common.constants.TopicNameConstants;
import cn.com.wanshi.chat.common.utils.AppContextHelper;
import cn.com.wanshi.chat.message.model.req.ImMessageReq;
import cn.com.wanshi.chat.message.model.resp.ImMessageResp;
import cn.com.wanshi.chat.message.service.IImMessageDataService;
import cn.com.wanshi.common.ResponseVO;
import cn.com.wanshi.common.enums.YesNoEnum;
import com.alibaba.fastjson.JSONObject;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.kafka.core.KafkaTemplate;

import java.net.InetSocketAddress;
import java.util.Objects;
 
 
/**
 * @author dzx
 * @ClassName:
 * @Description: netty服务端处理类
 * @date 2023年06月30日 21:27:16
 */
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<Object> {

    IImMessageDataService imMessageDataService;


    private KafkaTemplate<String, Object> kafkaTemplate;


    private IImMessageDataService getIImMessageDataService(){
        if(Objects.isNull(imMessageDataService)){
            imMessageDataService = AppContextHelper.getBean(IImMessageDataService.class);
        }
        return imMessageDataService;
    }

    private KafkaTemplate<String, Object> getKafkaTemplate(){
        if(Objects.isNull(kafkaTemplate)){
            kafkaTemplate = AppContextHelper.getBean(KafkaTemplate.class);
        }
        return kafkaTemplate;
    }





    //由于继承了SimpleChannelInboundHandler，这个方法必须实现，否则报错
    //但实际应用中，这个方法没被调用
    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buff = (ByteBuf) msg;
        String strMsg = buff.toString(CharsetUtil.UTF_8);
        if(StringUtils.isEmpty(strMsg)){
            ImMessageReq imMessageReq = JSONObject.parseObject(strMsg, ImMessageReq.class);
        }
        log.info("收到消息内容：" + strMsg);
    }
 
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // WebSocket消息处理
        String strMsg = "";
        if (msg instanceof WebSocketFrame) {
            log.info("WebSocket消息处理************************************************************");
            strMsg = ((TextWebSocketFrame) msg).text().trim();
            log.info("收到webSocket消息：" + strMsg);
        }
        // Socket消息处理
        else if (msg instanceof ByteBuf) {
            log.info("Socket消息处理=================================");
            ByteBuf buff = (ByteBuf) msg;
            strMsg = buff.toString(CharsetUtil.UTF_8).trim();
            log.info("收到socket消息：" + strMsg);
        }

        ResponseVO<ImMessageResp> resp = null;
        if(!StringUtils.isEmpty(strMsg)){
            ImMessageReq imMessageReq = JSONObject.parseObject(strMsg, ImMessageReq.class);
            resp = getIImMessageDataService().handler(imMessageReq, ctx.channel().id());
        }

        this.channelWrite(ctx.channel().id(), resp);
    }




 
    /**
     * 有客户端终止连接服务器会触发此函数
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
 
        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
 
        String clientIp = insocket.getAddress().getHostAddress();
 
        ChannelId channelId = ctx.channel().id();
 
        //包含此客户端才去删除
        if (General.CHANNEL_MAP.containsKey(channelId)) {
            //删除连接
            General.CHANNEL_MAP.remove(channelId);
            log.info("Socket------客户端【" + channelId + "】退出netty服务器[IP:" + clientIp + "--->PORT:" + insocket.getPort() + "]");
            log.info("Socket------连接通道数量: " + General.CHANNEL_MAP.size());
            General.CHANNEL_TYPE_MAP.remove(channelId);

            String userId = General.channelIdUserIdHashMap.get(channelId);
            General.userIdChannelIdHashMap.remove(userId);
            General.channelIdUserIdHashMap.remove(channelId);
        }
    }
 
 
    /**
     * 服务端给客户端发送消息
     */
    public void channelWrite(ChannelId channelId, ResponseVO<ImMessageResp> msg) throws Exception {
 
        ChannelHandlerContext ctx = General.CHANNEL_MAP.get(channelId);
 
        if (ctx == null) {
            log.info("Socket------通道【" + channelId + "】不存在");
            return;
        }
 
        if (msg == null ) {
            log.info("Socket------服务端响应空的消息");
            return;
        }
 
        //将客户端的信息直接返回写入ctx
        log.info("Socket------服务端端返回报文......【" + channelId + "】" + " :" + JSONObject.toJSONString(msg));

        String msgJsonStr = JSONObject.toJSONString(msg);
        ImMessageResp data = msg.getData();
        String toId = msg.getData().getToId();
        ChannelId toUserchannelId = General.userIdChannelIdHashMap.get(toId);
        if(Objects.nonNull(toUserchannelId)){
            General.CHANNEL_MAP.get(toUserchannelId).channel().writeAndFlush(new TextWebSocketFrame(msgJsonStr));
            data.setSendStatus(YesNoEnum.YES.value);
        }else{
            data.setSendStatus(YesNoEnum.NO.value);
        }
        getKafkaTemplate().send(TopicNameConstants.MQ_WS_IM_MESSAGE_TOPIC, JSONObject.toJSONString(data));
           /*    TODO 群发
        //过滤掉当前通道id
        Set<ChannelId> channelIdSet = General.CHANNEL_MAP.keySet().stream().filter(id -> !id.asLongText().equalsIgnoreCase(channelId.asLongText())).collect(Collectors.toSet());
        for (ChannelId id : channelIdSet) {
            //是websocket协议
            Boolean aBoolean = General.CHANNEL_TYPE_MAP.get(id);
            if(aBoolean!=null && aBoolean){
                General.CHANNEL_MAP.get(id).channel().writeAndFlush(new TextWebSocketFrame((String) msg));
            }else {
                ByteBuf byteBuf = Unpooled.copiedBuffer(((String) msg).getBytes());
                General.CHANNEL_MAP.get(id).channel().writeAndFlush(byteBuf);
            }
        }*/
    }
 
    /**
     * 处理空闲状态事件
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
 
        String socketString = ctx.channel().remoteAddress().toString();
 
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                log.info("Socket------Client: " + socketString + " READER_IDLE 读超时");
                ctx.disconnect();
            } else if (event.state() == IdleState.WRITER_IDLE) {
                log.info("Socket------Client: " + socketString + " WRITER_IDLE 写超时");
                ctx.disconnect();
            } else if (event.state() == IdleState.ALL_IDLE) {
                log.info("Socket------Client: " + socketString + " ALL_IDLE 总超时");
                ctx.disconnect();
            }
        }
    }
 
    /**
     * @DESCRIPTION: 发生异常会触发此函数
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        log.error("Socket------" + ctx.channel().id() + " 发生了错误,此连接被关闭" + "此时连通数量: " + General.CHANNEL_MAP.size(),cause);
    }
}