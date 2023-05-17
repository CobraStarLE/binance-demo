var binance = {
    url: {
        buy: '/buy',
        sell: '/sell',
        klines: '/klines'
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
        alert(symbol);
        $.post(binance.url.klines + "?symbol=" + symbol, function (result) {
            alert('购买成功！');
        });
    }
}