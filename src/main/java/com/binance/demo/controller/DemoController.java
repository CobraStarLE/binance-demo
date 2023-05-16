package com.binance.demo.controller;

import com.binance.connector.client.SpotClient;
import com.binance.connector.futures.client.impl.CMFuturesClientImpl;
import com.binance.connector.futures.client.impl.UMFuturesClientImpl;
import com.binance.demo.domain.market.TickerPrice;
import com.binance.demo.util.JsonUtil;
import com.binance.demo.util.TimestampUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.LinkedHashMap;
import java.util.List;

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
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", symbol);
        parameters.put("side", "BUY");
        parameters.put("type", "MARKET");
        parameters.put("quantity", 1);
        parameters.put("timestamp", TimestampUtil.getUnixTimeStamp()-1000);
        String resp=spot_client.createTrade().newOrder(parameters);
        System.out.println(resp);
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
