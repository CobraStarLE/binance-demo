create table CandlestickStream
(
    event_type         varchar(20)  not null comment '事件类型',
    event_time         bigint       null comment ' 事件时间',
    symbol             varchar(20)  null comment '交易对',
    open_time          bigint       null comment 'K线的起始时间',
    close_time         bigint       null comment 'K线的结束时间',
    open               varchar(20)  null comment '第一笔成交价',
    high               varchar(20)  null comment '最高成交价',
    low                varchar(20)  null comment '最低成交价',
    close              varchar(20)  null comment '末一笔成交价',
    volume             varchar(255) null comment '成交量',
    intervalId         varchar(255) null comment 'K线间隔',
    first_trade_id     bigint       null comment '第一笔成交ID',
    last_trade_id      bigint       null comment '末一笔成交ID',
    quote_asset_volume varchar(255) null comment '成交额',
    number_of_trades   bigint       null comment '成交笔数',
    buy_base_volume    varchar(255) null comment '主动买入的成交量',
    buy_quote_volume   varchar(255) null comment '主动买入的成交额',
    bar_final          bit          null comment 'K线是否完结(是否已经开始下一根K线)'
)
    comment 'K线图数据';