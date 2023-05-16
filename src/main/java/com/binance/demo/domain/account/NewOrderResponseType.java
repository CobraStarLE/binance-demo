package com.binance.demo.domain.account;

import com.binance.api.client.domain.account.NewOrderResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Desired response type of NewOrder requests.
 * @see NewOrderResponse
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public enum NewOrderResponseType {
    ACK,
    RESULT,
    FULL
}

