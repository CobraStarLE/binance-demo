package com.binance.demo.domain.account;

import com.binance.api.client.constant.BinanceApiConstants;
import com.binance.api.client.domain.ContingencyType;
import com.binance.api.client.domain.OCOOrderStatus;
import com.binance.api.client.domain.OCOStatus;
import com.binance.api.client.domain.account.Order;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderList {

    /**
     * Order id.
     */
    private Long orderListId;

    private ContingencyType contingencyType;

    private OCOStatus listStatusType;

    private OCOOrderStatus listOrderStatus;

    private String listClientOrderId;

    private Long transactionTime;

    private String symbol;

    private List<com.binance.api.client.domain.account.Order> orders;

    public Long getOrderListId() {
        return orderListId;
    }

    public void setOrderListId(Long orderListId) {
        this.orderListId = orderListId;
    }

    public ContingencyType getContingencyType() {
        return contingencyType;
    }

    public void setContingencyType(ContingencyType contingencyType) {
        this.contingencyType = contingencyType;
    }

    public OCOStatus getListStatusType() {
        return listStatusType;
    }

    public void setListStatusType(OCOStatus listStatusType) {
        this.listStatusType = listStatusType;
    }

    public OCOOrderStatus getListOrderStatus() {
        return listOrderStatus;
    }

    public void setListOrderStatus(OCOOrderStatus listOrderStatus) {
        this.listOrderStatus = listOrderStatus;
    }

    public String getListClientOrderId() {
        return listClientOrderId;
    }

    public void setListClientOrderId(String listClientOrderId) {
        this.listClientOrderId = listClientOrderId;
    }

    public Long getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Long transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public List<com.binance.api.client.domain.account.Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceApiConstants.TO_STRING_BUILDER_STYLE)
                .append("orderListId", orderListId)
                .append("contingencyType", contingencyType)
                .append("listStatusType", listStatusType)
                .append("listOrderStatus", listOrderStatus)
                .append("listClientOrderId", listClientOrderId)
                .append("transactionTime", transactionTime)
                .append("symbol", symbol)
                .append("orders", orders)
                .toString();
    }

}