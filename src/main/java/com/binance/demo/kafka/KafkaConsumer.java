package com.binance.demo.kafka;

import com.binance.demo.domain.event.CandlestickEvent;
import com.binance.demo.util.JsonUtil;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @KafkaListener(topics = "my-topic", groupId = "my-group-id")
    public void consume(String message) {
        System.out.println("Received message: " + message);
        CandlestickEvent event=JsonUtil.json2Pojo(message, CandlestickEvent.class);
        System.out.println();
    }

}