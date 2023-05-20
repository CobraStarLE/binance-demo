package com.binance.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * K线图数据
 * @TableName CandlestickEvent
 */
@TableName(value ="CandlestickStream")
public class CandlestickStream implements Serializable {
    /**
     * 事件类型
     */
    @TableField(value = "event_type")
    private String eventType;

    /**
     *  事件时间
     */
    @TableField(value = "event_time")
    private Long eventTime;

    /**
     * 交易对
     */
    @TableField(value = "symbol")
    private String symbol;

    /**
     * K线的起始时间
     */
    @TableField(value = "open_time")
    private Long openTime;

    /**
     * K线的结束时间
     */
    @TableField(value = "close_time")
    private Long closeTime;

    /**
     * 第一笔成交价
     */
    @TableField(value = "open")
    private String open;

    /**
     * 最高成交价
     */
    @TableField(value = "high")
    private String high;

    /**
     * 最低成交价
     */
    @TableField(value = "low")
    private String low;

    /**
     * 末一笔成交价
     */
    @TableField(value = "close")
    private String close;

    /**
     * 成交量
     */
    @TableField(value = "volume")
    private String volume;

    /**
     * K线间隔
     */
    @TableField(value = "intervalId")
    private String intervalid;

    /**
     * 第一笔成交ID
     */
    @TableField(value = "first_trade_id")
    private Long firstTradeId;

    /**
     * 末一笔成交ID
     */
    @TableField(value = "last_trade_id")
    private Long lastTradeId;

    /**
     * 成交额
     */
    @TableField(value = "quote_asset_volume")
    private String quoteAssetVolume;

    /**
     * 成交笔数
     */
    @TableField(value = "number_of_trades")
    private Long numberOfTrades;

    /**
     * 主动买入的成交量
     */
    @TableField(value = "buy_base_volume")
    private String buyBaseVolume;

    /**
     * 主动买入的成交额
     */
    @TableField(value = "buy_quote_volume")
    private String buyQuoteVolume;

    /**
     * K线是否完结(是否已经开始下一根K线)
     */
    @TableField(value = "bar_final")
    private Boolean barFinal;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 事件类型
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * 事件类型
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    /**
     *  事件时间
     */
    public Long getEventTime() {
        return eventTime;
    }

    /**
     *  事件时间
     */
    public void setEventTime(Long eventTime) {
        this.eventTime = eventTime;
    }

    /**
     * 交易对
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * 交易对
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    /**
     * K线的起始时间
     */
    public Long getOpenTime() {
        return openTime;
    }

    /**
     * K线的起始时间
     */
    public void setOpenTime(Long openTime) {
        this.openTime = openTime;
    }

    /**
     * K线的结束时间
     */
    public Long getCloseTime() {
        return closeTime;
    }

    /**
     * K线的结束时间
     */
    public void setCloseTime(Long closeTime) {
        this.closeTime = closeTime;
    }

    /**
     * 第一笔成交价
     */
    public String getOpen() {
        return open;
    }

    /**
     * 第一笔成交价
     */
    public void setOpen(String open) {
        this.open = open;
    }

    /**
     * 最高成交价
     */
    public String getHigh() {
        return high;
    }

    /**
     * 最高成交价
     */
    public void setHigh(String high) {
        this.high = high;
    }

    /**
     * 最低成交价
     */
    public String getLow() {
        return low;
    }

    /**
     * 最低成交价
     */
    public void setLow(String low) {
        this.low = low;
    }

    /**
     * 末一笔成交价
     */
    public String getClose() {
        return close;
    }

    /**
     * 末一笔成交价
     */
    public void setClose(String close) {
        this.close = close;
    }

    /**
     * 成交量
     */
    public String getVolume() {
        return volume;
    }

    /**
     * 成交量
     */
    public void setVolume(String volume) {
        this.volume = volume;
    }

    /**
     * K线间隔
     */
    public String getIntervalid() {
        return intervalid;
    }

    /**
     * K线间隔
     */
    public void setIntervalid(String intervalid) {
        this.intervalid = intervalid;
    }

    /**
     * 第一笔成交ID
     */
    public Long getFirstTradeId() {
        return firstTradeId;
    }

    /**
     * 第一笔成交ID
     */
    public void setFirstTradeId(Long firstTradeId) {
        this.firstTradeId = firstTradeId;
    }

    /**
     * 末一笔成交ID
     */
    public Long getLastTradeId() {
        return lastTradeId;
    }

    /**
     * 末一笔成交ID
     */
    public void setLastTradeId(Long lastTradeId) {
        this.lastTradeId = lastTradeId;
    }

    /**
     * 成交额
     */
    public String getQuoteAssetVolume() {
        return quoteAssetVolume;
    }

    /**
     * 成交额
     */
    public void setQuoteAssetVolume(String quoteAssetVolume) {
        this.quoteAssetVolume = quoteAssetVolume;
    }

    /**
     * 成交笔数
     */
    public Long getNumberOfTrades() {
        return numberOfTrades;
    }

    /**
     * 成交笔数
     */
    public void setNumberOfTrades(Long numberOfTrades) {
        this.numberOfTrades = numberOfTrades;
    }

    /**
     * 主动买入的成交量
     */
    public String getBuyBaseVolume() {
        return buyBaseVolume;
    }

    /**
     * 主动买入的成交量
     */
    public void setBuyBaseVolume(String buyBaseVolume) {
        this.buyBaseVolume = buyBaseVolume;
    }

    /**
     * 主动买入的成交额
     */
    public String getBuyQuoteVolume() {
        return buyQuoteVolume;
    }

    /**
     * 主动买入的成交额
     */
    public void setBuyQuoteVolume(String buyQuoteVolume) {
        this.buyQuoteVolume = buyQuoteVolume;
    }

    /**
     * K线是否完结(是否已经开始下一根K线)
     */
    public Boolean getBarFinal() {
        return barFinal;
    }

    /**
     * K线是否完结(是否已经开始下一根K线)
     */
    public void setBarFinal(Boolean barFinal) {
        this.barFinal = barFinal;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        CandlestickStream other = (CandlestickStream) that;
        return (this.getEventType() == null ? other.getEventType() == null : this.getEventType().equals(other.getEventType()))
            && (this.getEventTime() == null ? other.getEventTime() == null : this.getEventTime().equals(other.getEventTime()))
            && (this.getSymbol() == null ? other.getSymbol() == null : this.getSymbol().equals(other.getSymbol()))
            && (this.getOpenTime() == null ? other.getOpenTime() == null : this.getOpenTime().equals(other.getOpenTime()))
            && (this.getCloseTime() == null ? other.getCloseTime() == null : this.getCloseTime().equals(other.getCloseTime()))
            && (this.getOpen() == null ? other.getOpen() == null : this.getOpen().equals(other.getOpen()))
            && (this.getHigh() == null ? other.getHigh() == null : this.getHigh().equals(other.getHigh()))
            && (this.getLow() == null ? other.getLow() == null : this.getLow().equals(other.getLow()))
            && (this.getClose() == null ? other.getClose() == null : this.getClose().equals(other.getClose()))
            && (this.getVolume() == null ? other.getVolume() == null : this.getVolume().equals(other.getVolume()))
            && (this.getIntervalid() == null ? other.getIntervalid() == null : this.getIntervalid().equals(other.getIntervalid()))
            && (this.getFirstTradeId() == null ? other.getFirstTradeId() == null : this.getFirstTradeId().equals(other.getFirstTradeId()))
            && (this.getLastTradeId() == null ? other.getLastTradeId() == null : this.getLastTradeId().equals(other.getLastTradeId()))
            && (this.getQuoteAssetVolume() == null ? other.getQuoteAssetVolume() == null : this.getQuoteAssetVolume().equals(other.getQuoteAssetVolume()))
            && (this.getNumberOfTrades() == null ? other.getNumberOfTrades() == null : this.getNumberOfTrades().equals(other.getNumberOfTrades()))
            && (this.getBuyBaseVolume() == null ? other.getBuyBaseVolume() == null : this.getBuyBaseVolume().equals(other.getBuyBaseVolume()))
            && (this.getBuyQuoteVolume() == null ? other.getBuyQuoteVolume() == null : this.getBuyQuoteVolume().equals(other.getBuyQuoteVolume()))
            && (this.getBarFinal() == null ? other.getBarFinal() == null : this.getBarFinal().equals(other.getBarFinal()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getEventType() == null) ? 0 : getEventType().hashCode());
        result = prime * result + ((getEventTime() == null) ? 0 : getEventTime().hashCode());
        result = prime * result + ((getSymbol() == null) ? 0 : getSymbol().hashCode());
        result = prime * result + ((getOpenTime() == null) ? 0 : getOpenTime().hashCode());
        result = prime * result + ((getCloseTime() == null) ? 0 : getCloseTime().hashCode());
        result = prime * result + ((getOpen() == null) ? 0 : getOpen().hashCode());
        result = prime * result + ((getHigh() == null) ? 0 : getHigh().hashCode());
        result = prime * result + ((getLow() == null) ? 0 : getLow().hashCode());
        result = prime * result + ((getClose() == null) ? 0 : getClose().hashCode());
        result = prime * result + ((getVolume() == null) ? 0 : getVolume().hashCode());
        result = prime * result + ((getIntervalid() == null) ? 0 : getIntervalid().hashCode());
        result = prime * result + ((getFirstTradeId() == null) ? 0 : getFirstTradeId().hashCode());
        result = prime * result + ((getLastTradeId() == null) ? 0 : getLastTradeId().hashCode());
        result = prime * result + ((getQuoteAssetVolume() == null) ? 0 : getQuoteAssetVolume().hashCode());
        result = prime * result + ((getNumberOfTrades() == null) ? 0 : getNumberOfTrades().hashCode());
        result = prime * result + ((getBuyBaseVolume() == null) ? 0 : getBuyBaseVolume().hashCode());
        result = prime * result + ((getBuyQuoteVolume() == null) ? 0 : getBuyQuoteVolume().hashCode());
        result = prime * result + ((getBarFinal() == null) ? 0 : getBarFinal().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", eventType=").append(eventType);
        sb.append(", eventTime=").append(eventTime);
        sb.append(", symbol=").append(symbol);
        sb.append(", openTime=").append(openTime);
        sb.append(", closeTime=").append(closeTime);
        sb.append(", open=").append(open);
        sb.append(", high=").append(high);
        sb.append(", low=").append(low);
        sb.append(", close=").append(close);
        sb.append(", volume=").append(volume);
        sb.append(", intervalid=").append(intervalid);
        sb.append(", firstTradeId=").append(firstTradeId);
        sb.append(", lastTradeId=").append(lastTradeId);
        sb.append(", quoteAssetVolume=").append(quoteAssetVolume);
        sb.append(", numberOfTrades=").append(numberOfTrades);
        sb.append(", buyBaseVolume=").append(buyBaseVolume);
        sb.append(", buyQuoteVolume=").append(buyQuoteVolume);
        sb.append(", barFinal=").append(barFinal);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}