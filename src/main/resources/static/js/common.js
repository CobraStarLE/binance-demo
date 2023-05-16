var binance = {
    url: {
        buy: '/buy',
        klines: '/klines'
    },
    buy: function (symbol) {
        $.post(binance.url.buy + "?symbol=" + symbol, function (result) {
            alert('购买成功！');
        });
    },
    kline: function (symbol) {
        $.post(binance.url.klines + "?symbol=" + symbol, function (result) {
        });
    }
}