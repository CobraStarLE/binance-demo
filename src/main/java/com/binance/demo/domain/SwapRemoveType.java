package com.binance.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum SwapRemoveType {
    SINGLE, COMBINATION
}
