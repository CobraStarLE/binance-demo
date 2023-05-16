package com.binance.demo.config;

import com.binance.connector.client.SpotClient;
import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.connector.futures.client.impl.CMFuturesClientImpl;
import com.binance.connector.futures.client.impl.FuturesClientImpl;
import com.binance.connector.futures.client.impl.UMFuturesClientImpl;
import com.binance.connector.futures.client.utils.ProxyAuth;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.net.Proxy;

@Configuration
public class BinanceConfiguration {

    @Value("${binance.test.API-KEY}")
    private String binance_api_key;

    @Value("${binance.test.SECRET}")
    private String binance_secret;

    @Value("${binance.proxy.enable:false}")
    private boolean proxy_enable;

    @Value("${binance.proxy.ip:127.0.0.1}")
    private String proxy_ip;

    @Value("${binance.proxy.port}")
    private int proxy_port;

    @Value("${binance.proxy.username}")
    private String proxy_username;

    @Value("${binance.proxy.password}")
    private String proxy_passsword;

    /**
     * U本位合约客户端
     */
    @Bean
    public UMFuturesClientImpl um_client() {
        UMFuturesClientImpl client = new UMFuturesClientImpl(binance_api_key, binance_secret);
        client.setShowLimitUsage(true);
        setProxy(client);
        return client;
    }

    /**
     * 币本位合约客户端
     */
    @Bean
    public CMFuturesClientImpl cm_client() {
        CMFuturesClientImpl client = new CMFuturesClientImpl(binance_api_key, binance_secret);
        client.setShowLimitUsage(true);
        setProxy(client);
        return client;
    }

    /**
     * 现货交易客户端
     */
    @Bean
    public SpotClient spot_client() {
        SpotClientImpl client = new SpotClientImpl(binance_api_key, binance_secret);
        setProxy(client);
        return client;
    }

    private void setProxy(SpotClientImpl client){
        if (proxy_enable) {
            Proxy proxyConn = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxy_ip, proxy_port));
            Authenticator auth = null;
            if(StringUtils.isNotBlank(proxy_username)){
                auth = (route, response) -> {
                    if (response.request().header("Proxy-Authorization") != null) {
                        return null; // Give up, we've already failed to authenticate.
                    }
                    String credential = Credentials.basic(proxy_username, proxy_passsword);
                    return response.request().newBuilder().header("Proxy-Authorization", credential).build();
                };
            }
            com.binance.connector.client.utils.ProxyAuth  proxy = new com.binance.connector.client.utils.ProxyAuth (proxyConn, auth);
            client.setProxy(proxy);
        }
    }

    private void setProxy(FuturesClientImpl client){
        if (proxy_enable) {
            Proxy proxyConn = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxy_ip, proxy_port));
            Authenticator auth = null;
            if(StringUtils.isNotBlank(proxy_username)){
                auth = (route, response) -> {
                    if (response.request().header("Proxy-Authorization") != null) {
                        return null; // Give up, we've already failed to authenticate.
                    }
                    String credential = Credentials.basic(proxy_username, proxy_passsword);
                    return response.request().newBuilder().header("Proxy-Authorization", credential).build();
                };
            }
            ProxyAuth proxy = new ProxyAuth(proxyConn, auth);
            client.setProxy(proxy);
        }
    }




}
