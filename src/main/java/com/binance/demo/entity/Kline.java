package com.binance.demo.entity;

public class Kline {

    // 交易对
    private String symbol;

    // 忽略此参数
    private String B;
    // 这根K线期间末一笔成交价
    private String c;
    // 这根K线期间第一笔成交ID
    private Long f;
    // 这根K线期间最高成交价
    private String h;
    // K线间隔
    private String i;
    // 这根K线期间末一笔成交ID
    private Long L;
    // 这根K线期间成交笔数
    private Long n;
    // 这根K线期间第一笔成交价
    private String o;
    // 这根K线期间成交额
    private String q;
    // 交易对
    private String s;
    // 这根K线的结束时间
    private Long T;
    // 这根K线期间成交量
    private String v;
    // 这根K线是否完结(是否已经开始下一根K线)
    private Boolean x;

    public String getB() {
        return B;
    }

    public void setB(String b) {
        B = b;
    }

    public Long getF() {
        return f;
    }

    public void setF(Long f) {
        this.f = f;
    }

    public String getH() {
        return h;
    }

    public void setH(String h) {
        this.h = h;
    }

    public String getI() {
        return i;
    }

    public void setI(String i) {
        this.i = i;
    }

    public Long getL() {
        return L;
    }

    public void setL(Long l) {
        L = l;
    }

    public Long getN() {
        return n;
    }

    public void setN(Long n) {
        this.n = n;
    }

    public String getO() {
        return o;
    }

    public void setO(String o) {
        this.o = o;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public String getS() {
        return s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public Long getT() {
        return T;
    }

    public void setT(Long t) {
        T = t;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    public Boolean getX() {
        return x;
    }

    public void setX(Boolean x) {
        this.x = x;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
