package com.snw.client.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snw.client.event.AssignmentResultEvent;
import com.snw.client.event.PostResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GetStream {

    private static final Logger log = LoggerFactory.getLogger(GetStream.class);

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    //监听的
    @KafkaListener(topics = {"result-out-0", "assignment-result-out-0"}, groupId = "group_id")
    public void listen(ConsumerRecord<String, byte[]> record) {
        try {
            String topic = record.topic();
            byte[] message = record.value();

           if (topic.equals("result-out-0")) {
                PostResult event = objectMapper.readValue(message, PostResult.class);
                handleResult(event);
            } else if (topic.equals("assignment-result-out-0")) {
                AssignmentResultEvent event = objectMapper.readValue(message, AssignmentResultEvent.class);
                handleAssignmentResult(event);
            } else {
                log.info("Unknown event type received: " + new String(message));
            }
        } catch (Exception e) {
            log.error("Failed to process message", e);
        }
    }


    private void handleResult(PostResult event) {
        messagingTemplate.convertAndSend("/topic/handleResult", event);
        log.info("Received handleResult event: " + event);
    }

    // 新增方法处理AssignmentResultEvent事件
    private void handleAssignmentResult(AssignmentResultEvent event) {
        messagingTemplate.convertAndSend("/topic/assignmentResult", event);
        log.info("Received AssignmentResult event: " + event);
    }
}
