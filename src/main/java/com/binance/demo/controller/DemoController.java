package com.binance.demo.controller;

import com.binance.api.client.domain.account.AssetBalance;
import com.binance.connector.client.SpotClient;
import com.binance.connector.futures.client.impl.CMFuturesClientImpl;
import com.binance.connector.futures.client.impl.UMFuturesClientImpl;
import com.binance.demo.domain.account.Account;
import com.binance.demo.domain.market.TickerPrice;
import com.binance.demo.dto.HttpResult;
import com.binance.demo.service.BinanceService;
import com.binance.demo.util.JsonUtil;
import com.binance.demo.util.TimestampUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping(method = {RequestMethod.GET,RequestMethod.POST} )
public class DemoController {

    @Autowired
    private CMFuturesClientImpl cm_client;

    @Autowired
    private UMFuturesClientImpl um_client;

    @Autowired
    private SpotClient spot_client;

    @Autowired
    private BinanceService binance_service;

    /**
     * 现货列表
     *
     * @param model
     * @return
     */
    @GetMapping("/list")
    public String list(Model model) {
        List<AssetBalance> assetBalances = null;
        try {
            Account account = binance_service.getTradeAccount();
            assetBalances = account.getBalances();
        }
        catch (Exception e) {
        }
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        String resp = spot_client.createMarket().tickerSymbol(parameters);
        List<TickerPrice> list = JsonUtil.json2List(resp, TickerPrice.class);
        if(ObjectUtils.isNotEmpty(assetBalances)){
            List<String> asserts= assetBalances.stream().map(AssetBalance::getAsset).collect(Collectors.toList());
            list=list.stream().filter(o->isContainsSymbol(o.getSymbol(),asserts)).collect(Collectors.toList());
        }

        model.addAttribute("list", list);
        return "list";
    }

    @PostMapping("/buy")
    @ResponseBody
    public HttpResult buy(String symbol) {
        String time_resp = spot_client.createMarket().time();
        String server_time = JsonUtil.getJSONStringValue(time_resp, "serverTime");//服务器时间
        Long local_time = TimestampUtil.getUnixTimeStamp();//本地时间
        log.info("服务器时间[{}]与本地时间[{}]相差了{}毫秒。", server_time, local_time, Long.valueOf(server_time) - local_time);
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", symbol);
        parameters.put("side", "BUY");
        parameters.put("type", "MARKET");
        parameters.put("quantity", 1);
        parameters.put("timestamp", server_time);
        String resp = null;
        try {
            resp = spot_client.createTrade().newOrder(parameters);
            log.info("买入结果:{}", resp);
            return new HttpResult<>(true,"买入成功！");
        }catch (Exception e) {
            return new HttpResult(false,e.getMessage());
        }
    }


    @PostMapping("/sell")
    @ResponseBody
    public HttpResult sell(String symbol) {
        String time_resp = spot_client.createMarket().time();
        String server_time = JsonUtil.getJSONStringValue(time_resp, "serverTime");//服务器时间
        Long local_time = TimestampUtil.getUnixTimeStamp();//本地时间
        log.info("服务器时间[{}]与本地时间[{}]相差了{}毫秒。", server_time, local_time, Long.valueOf(server_time) - local_time);
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", symbol);
        parameters.put("side", "SELL");
        parameters.put("type", "MARKET");
        parameters.put("quantity", 1);
        parameters.put("timestamp", server_time);
        try {
            String resp = spot_client.createTrade().newOrder(parameters);
            log.info("卖出结果:{}", resp);
            return new HttpResult<>(true,"卖出成功！");
        }catch (Exception e) {
            return new HttpResult(false,e.getMessage());
        }
    }

    @RequestMapping (value = "/klines",method = RequestMethod.POST)
    @ResponseBody
    public List<String[]> klines(String symbol) {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", symbol);
        parameters.put("interval", "1d");
        String resp = spot_client.createMarket().klines(parameters);
        System.out.println("-------");
        System.out.println(resp);
        System.out.println("-------");

        List<String[]> list = JsonUtil.json2Obj(resp, new TypeReference<List<String[]>>() {});
        System.out.println(list.size());
        System.out.println();

        return list;
    }

    @RequestMapping (value = "/to/klinePage",method = RequestMethod.GET)
    public ModelAndView toklinesPage(String symbol) {
        ModelAndView mv=new ModelAndView("kline");
        mv.addObject("symbol",symbol);
        return mv;
    }

    private boolean isContainsSymbol(String symbol,List<String> asserts) {
        for (String _assert : asserts){
            if(symbol.indexOf(_assert)==0||symbol.lastIndexOf(_assert)>0){
                return true;
            }
        }

        return false;
    }
}
