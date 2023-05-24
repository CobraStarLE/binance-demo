var basePath=function (){
    return $("#input_base").val();
}

var binance = {
    url: {
        buy: basePath()+'/buy',
        sell: basePath()+'/sell',
        to_klines: basePath()+'/to/klinePage'
    },
    buy: function (symbol) {
        $.post(binance.url.buy + "?symbol=" + symbol, function (result) {
            alert(result.error);
        });
    },
    sell: function (symbol) {
        $.post(binance.url.sell + "?symbol=" + symbol, function (result) {
            alert(result.error);
        });
    },
    kline: function (symbol) {
        var action=binance.url.to_klines+"?symbol=" + symbol;
        var tempwindow=window.open('_blank');
        tempwindow.location=action;
    }
}