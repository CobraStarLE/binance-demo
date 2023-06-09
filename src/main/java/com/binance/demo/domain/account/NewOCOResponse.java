package com.binance.demo.domain.account;

import com.binance.api.client.domain.ContingencyType;
import com.binance.api.client.domain.OCOOrderStatus;
import com.binance.api.client.domain.OCOStatus;
import com.binance.api.client.domain.account.OrderList;
import com.binance.api.client.domain.account.OrderReport;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewOCOResponse extends OrderList {

    private Long orderListId;
    private ContingencyType contingencyType;
    private OCOStatus listStatusType;
    private OCOOrderStatus listOrderStatus;
    private String listClientOrderId;
    private Long transactionTime;
    private String symbol;
    private List<com.binance.api.client.domain.account.OrderReport> orderReports;

    // Getters
    public Long getOrderListId() {
        return this.orderListId;
    }

    public ContingencyType getContingencyType() {
        return this.contingencyType;
    }

    public OCOStatus getListStatusType() {
        return this.listStatusType;
    }

    public OCOOrderStatus getListOrderStatus() {
        return this.listOrderStatus;
    }

    public String getListClientOrderId() {
        return this.listClientOrderId;
    }

    public Long getTransactionTime() {
        return this.transactionTime;
    }

    public String getSymbol() {
        return this.symbol;
    }

    public List<com.binance.api.client.domain.account.OrderReport> getOrderReports() {
        return orderReports;
    }

    // Setter
    public void setOrderListId(Long orderListId) {
        this.orderListId = orderListId;
    }

    public void setContingencyType(ContingencyType contingencyType) {
        this.contingencyType = contingencyType;
    }

    public void setListStatusType(OCOStatus listStatusType) {
        this.listStatusType = listStatusType;
    }

    public void setListOrderStatus(OCOOrderStatus listOrderStatus) {
        this.listOrderStatus = listOrderStatus;
    }

    public void setListClientOrderId(String listClientOrderId) {
        this.listClientOrderId = listClientOrderId;
    }

    public void setTransactionTime(Long transactionTime) {
        this.transactionTime = transactionTime;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setOrderReports(List<OrderReport> orderReports) {
        this.orderReports = orderReports;
    }

}
