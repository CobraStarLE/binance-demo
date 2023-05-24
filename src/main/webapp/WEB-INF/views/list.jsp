<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!doctype html>
<html lang="zh-CN">
<head>
    <base href="<%=basePath%>">
    <!-- 必须的 meta 标签 -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap 的 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">

    <title>虚拟货币</title>
</head>
<body>
<div class="container">
    <div class="card">
        <div class="card-header">列表</div>
        <div class="card-body">
            <table class="table table-striped">
                <thead class="table-header">
                    <tr>
                        <th scope="col">序号</th>
                        <th scope="col">交易对</th>
                        <th scope="col">价格</th>
                        <th scope="col">操作</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="tp" items="${list}" varStatus="status">
                        <tr>
                            <th scope="row">${status.index+1}</th>
                            <th>${tp.symbol}</th>
                            <td>${tp.price}</td>
                            <td>
                                <button type="button" class="btn btn-primary" onclick="binance.buy('${tp.symbol}')">买入</button>
                                <button type="button" class="btn btn-primary" onclick="binance.sell('${tp.symbol}')">卖出</button>
                                <button type="button" class="btn btn-primary" onclick="binance.kline('${tp.symbol}')">K线图</button>
                            </td>
                        </tr>
                    </c:forEach>
                    <form>
                        <input id="input_base" type="hidden" value="<%=basePath%>">
                    </form>

                </tbody>
            </table>
        </div>
    </div>
</div>

<!-- JavaScript 文件是可选的。从以下两种建议中选择一个即可！ -->

<!-- 选项 1：jQuery 和 Bootstrap 集成包（集成了 Popper） -->
<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-7ymO4nGrkm372HoSbq1OY2DP4pEZnMiA+E0F3zPr+JQQtQ82gQ1HPY3QIVtztVua" crossorigin="anonymous"></script>
<script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<!-- 选项 2：Popper 和 Bootstrap 的 JS 插件各自独立 -->
<!--
<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js" integrity="sha384-Lge2E2XotzMiwH69/MXB72yLpwyENMiOKX8zS8Qo7LDCvaBIWGL+GlRQEKIpYR04" crossorigin="anonymous"></script>
-->
<script src="<%=basePath%>/js/common.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function(){

    });
</script>
</body>
</html>