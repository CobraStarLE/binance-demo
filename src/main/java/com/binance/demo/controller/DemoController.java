package com.binance.demo.controller;

import com.binance.connector.client.SpotClient;
import com.binance.connector.futures.client.impl.CMFuturesClientImpl;
import com.binance.connector.futures.client.impl.UMFuturesClientImpl;
import com.binance.demo.domain.market.TickerPrice;
import com.binance.demo.util.JsonUtil;
import com.binance.demo.util.TimestampUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@Controller
public class DemoController {

    @Autowired
    private CMFuturesClientImpl cm_client;

    @Autowired
    private UMFuturesClientImpl um_client;

    @Autowired
    private SpotClient spot_client;

    /**
     * 现货列表
     * @param model
     * @return
     */
    @GetMapping ("/")
    public String list(Model model) {
        String resp=spot_client.createMarket().tickerSymbol(new LinkedHashMap<>());
        List<TickerPrice> list= JsonUtil.json2List(resp, TickerPrice.class);
        model.addAttribute("list",list);
        return "list";
    }

    @PostMapping("/buy")
    public void order(String symbol){
        String time_resp=spot_client.createMarket().time();
        String server_time=JsonUtil.getJSONStringValue(time_resp,"serverTime");//服务器时间
        Long local_time=TimestampUtil.getUnixTimeStamp();//本地时间
        log.info("服务器时间[{}]与本地时间[{}]相差了{}毫秒。",server_time,local_time,Long.valueOf(server_time)-local_time);
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", symbol);
        parameters.put("side", "BUY");
        parameters.put("type", "MARKET");
        parameters.put("quantity", 1);
        parameters.put("timestamp", server_time);
        String resp=spot_client.createTrade().newOrder(parameters);
       log.info("买入结果:{}",resp);
    }

    @PostMapping ("/klines")
    public void klines(String symbol){
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", symbol);
        parameters.put("interval", "5m");
        String resp=um_client.market().klines(parameters);
        String data=JsonUtil.getJSONStringValue(resp,"data");
        System.out.println("-------");
        System.out.println(data);
        System.out.println("-------");

        List<String[]> list=JsonUtil.json2Obj(data, new TypeReference<List<String[]>>() {});
        System.out.println(list.size());
         System.out.println();
    }
}
