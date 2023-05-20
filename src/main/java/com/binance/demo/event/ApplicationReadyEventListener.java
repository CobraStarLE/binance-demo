package com.binance.demo.event;

import com.binance.connector.client.WebSocketStreamClient;
import com.binance.connector.client.utils.WebSocketCallback;
import com.binance.connector.client.utils.WebSocketConnection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private WebSocketStreamClient spot_ws_client;

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
       klineStream(null);
    }

    private int klineStream( WebSocketCallback failCallback){
        WebSocketCallback defaultCallback = msg -> {};
        WebSocketCallback closeCallback = msg -> {
            System.out.println();
        };
        if(failCallback==null)
            failCallback=msg -> {
                klineStream(null);
            };
        int streamID1 = spot_ws_client.klineStream("btcusdt","5m",defaultCallback,((_event) -> {
            kafkaTemplate.send("my-topic",_event);
        }),closeCallback,failCallback);
        return streamID1;
    }
}
