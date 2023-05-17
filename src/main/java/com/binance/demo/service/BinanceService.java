package com.binance.demo.service;

import com.binance.connector.client.SpotClient;
import com.binance.demo.domain.account.Account;
import com.binance.demo.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;

@Service
public class BinanceService {

    @Autowired
    private SpotClient spot_client;

    public Account getTradeAccount() {
        LinkedHashMap<String, Object> parameters = new LinkedHashMap<>();
        String resp = spot_client.createTrade().account(parameters);
        Account account= JsonUtil.json2Pojo(resp,Account.class);
        return account;
    }

}
