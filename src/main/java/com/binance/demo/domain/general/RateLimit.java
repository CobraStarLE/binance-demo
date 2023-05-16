package com.binance.demo.domain.general;

import com.binance.api.client.constant.BinanceApiConstants;
import com.binance.api.client.domain.general.RateLimitInterval;
import com.binance.api.client.domain.general.RateLimitType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Rate limits.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RateLimit {

  private com.binance.api.client.domain.general.RateLimitType rateLimitType;

  private com.binance.api.client.domain.general.RateLimitInterval interval;

  private Integer limit;

  public com.binance.api.client.domain.general.RateLimitType getRateLimitType() {
    return rateLimitType;
  }

  public void setRateLimitType(RateLimitType rateLimitType) {
    this.rateLimitType = rateLimitType;
  }

  public com.binance.api.client.domain.general.RateLimitInterval getInterval() {
    return interval;
  }

  public void setInterval(RateLimitInterval interval) {
    this.interval = interval;
  }

  public Integer getLimit() {
    return limit;
  }

  public void setLimit(Integer limit) {
    this.limit = limit;
  }

  @Override
  public String toString() {
    return new ToStringBuilder(this, BinanceApiConstants.TO_STRING_BUILDER_STYLE)
        .append("rateLimitType", rateLimitType)
        .append("interval", interval)
        .append("limit", limit)
        .toString();
  }
}
