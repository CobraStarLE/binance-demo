package com.binance.demo.kafka;

import com.binance.demo.domain.event.CandlestickEvent;
import com.binance.demo.entity.CandlestickStream;
import com.binance.demo.mapper.CandlestickStreamMapper;
import com.binance.demo.util.JsonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @Autowired
    private CandlestickStreamMapper candlestickStreamMapper;

    @KafkaListener(topics = "my-topic", groupId = "my-group-id")
    public void consume(String message) {
        System.out.println("Received message: " + message);
        CandlestickEvent event=JsonUtil.json2Pojo(message, CandlestickEvent.class);
        CandlestickStream stream=new CandlestickStream();
        BeanUtils.copyProperties(event,stream);
        this.candlestickStreamMapper.insert(stream);
        System.out.println();
    }

}