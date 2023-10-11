package cn.com.wanshi.chat.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 消息类型(1 文本 2 录音 3 图片 4 视频 5 语音通话  6 视频通话 7 登录 8 登出 9 心跳)
 * @author zzc
 */
@Getter
@AllArgsConstructor
public enum MessageTypeEnum {

    /**
     * 文本
     */
    TEXT(1),

    /**
     * 录音
     */
    SOUND_RECORDING(2),


    /**
     * 图片
     */
    PICTURE(3),

    /**
     * 视频
     */
    VIDEO(4),


    /**
     * 语音通话
     */
    VOICE_CALL(5),

    /**
     * 视频通话
     */
    VIDEO_CALL(6),

    /**
     * 登录
     */
    LOGIN(7),

    /**
     * 登出
     */
   LOGOUT(8),

    /**
     * 心跳
     */
    PING(9);

    /**
     * 值
     */
    public final int type;



    public static MessageTypeEnum getEnumByType(Integer type){
        for (MessageTypeEnum typeEnum : MessageTypeEnum.values()) {
            if(type.equals(typeEnum.type)){
                return typeEnum;
            }
        }
        return null;
    }

}
