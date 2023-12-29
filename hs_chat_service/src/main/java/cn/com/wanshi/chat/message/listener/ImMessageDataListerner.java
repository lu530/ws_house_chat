package cn.com.wanshi.chat.message.listener;


import cn.com.wanshi.chat.common.constants.TopicNameConstants;
import cn.com.wanshi.chat.message.model.resp.ImMessageResp;
import cn.com.wanshi.chat.message.service.IImMessageDataService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * im 消息监听器
 * @author zhongzhicheng
 */
@Component
@Slf4j
public class ImMessageDataListerner {


    @Autowired
    IImMessageDataService imMessageDataService;


    /**
     * TODO 需要集成mq重试
     * @param record
     */
    @KafkaListener(topics = TopicNameConstants.MQ_WS_IM_MESSAGE_TOPIC)
    public void immessage(ConsumerRecord<?, ?> record) {

        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            log.info("收到即时消息数据，消息实体:{}", kafkaMessage.get());
            String immessageStr = kafkaMessage.get().toString();
            ImMessageResp imMessageResp = JSONObject.parseObject(immessageStr, ImMessageResp.class);
            imMessageDataService.addImMessageData(imMessageResp);
        }
    }


}
