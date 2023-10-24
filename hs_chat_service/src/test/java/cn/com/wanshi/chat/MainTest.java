package cn.com.wanshi.chat;

import cn.com.wanshi.chat.message.model.req.ImMessageResp;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;

public class MainTest {

    public static void main(String[] args) {
        ImMessageResp message = ImMessageResp.builder().fromId("1324").fromType(1).toType(1).messageTime(new Date()).meesageData("我就是消息啊")
                .realStatus(0).messageType(1).build();
        System.out.println(JSONObject.toJSONString(message));
    }

}
