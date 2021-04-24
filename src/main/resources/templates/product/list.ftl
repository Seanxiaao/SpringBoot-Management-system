<html>
<#include "/common/header.ftl">

<body>

<div id="wrapper" class="toggled">
    <#include "/common/nav.ftl">
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>Product id</th>
                            <th>Name</th>
                            <th>Photo</th>
                            <th>Price</th>
                            <th>Storage</th>
                            <th>Description</th>
                            <th>Introduction</th>
                            <th>Category</th>
                            <th>Create Time</th>
                            <th>Modify Time</th>
                            <th colspan="2">Action</th>
                        </tr>
                        </thead>
                        <tbody>

                        <#list productInfoPage.content as productInfo>
                            <tr>
                                <td>${productInfo.productId}</td>
                                <td>${productInfo.productName}</td>
                                <#if productInfo.productIcon??>
                                    <td><img height="100" width="100" src="${productInfo.productIcon}" alt=""></td>
                                <#else>
                                    <td><img height="100" width="100" src="" alt=""></td>
                                </#if>
                                <td>${productInfo.productPrice}</td>
                                <td>${productInfo.productStock}</td>
                                <td>${productInfo.productDescription}</td>
                                <td>${productInfo.categoryType}</td>
                                <td>${productInfo.createTime}</td>
                                <td>${productInfo.updateTime}</td>
                                <td><a href="/sell/seller/product/index?productId=${productInfo.productId}">Modify</a></td>
                                <td>
                                    <#if productInfo.getProductStatusEnum().statusDetails == "available product">
                                        <a href="/sell/seller/product/offsale?productId=${productInfo.productId}">Discontinue</a>
                                    <#else>
                                        <a href="/sell/seller/product/onsale?productId=${productInfo.productId}">Launch</a>
                                    </#if>
                                </td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>

                <#--分页-->
                <div class="col-md-12 column">
                    <ul class="pagination pull-right">
                        <#if currentPage lte 1>
                            <li class="disabled"><a href="#">Prev</a></li>
                        <#else>
                            <li><a href="/sell/seller/product/list?page=${currentPage - 1}&size=${size}">Prev</a></li>
                        </#if>

                        <#list 1..productInfoPage.getTotalPages() as index>
                            <#if currentPage == index>
                                <li class="disabled"><a href="#">${index}</a></li>
                            <#else>
                                <li><a href="/sell/seller/product/list?page=${index}&size=${size}">${index}</a></li>
                            </#if>
                        </#list>

                        <#if currentPage gte productInfoPage.getTotalPages()>
                            <li class="disabled"><a href="#">Next</a></li>
                        <#else>
                            <li><a href="/sell/seller/product/list?page=${currentPage + 1}&size=${size}">Next</a></li>
                        </#if>
                    </ul>
                </div>
            </div>
        </div>
    </div>

</div>

<#--&lt;#&ndash;弹窗&ndash;&gt;-->
<#--<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">-->
<#--    <div class="modal-dialog">-->
<#--        <div class="modal-content">-->
<#--            <div class="modal-header">-->
<#--                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>-->
<#--                <h4 class="modal-title" id="myModalLabel">-->
<#--                    提醒-->
<#--                </h4>-->
<#--            </div>-->
<#--            <div class="modal-body">-->
<#--                你有新的订单-->
<#--            </div>-->
<#--            <div class="modal-footer">-->
<#--                <button onclick="javascript:document.getElementById('notice').pause()" type="button" class="btn btn-default" data-dismiss="modal">关闭</button>-->
<#--                <button onclick="location.reload()" type="button" class="btn btn-primary">查看新的订单</button>-->
<#--            </div>-->
<#--        </div>-->
<#--    </div>-->
<#--</div>-->

<#--&lt;#&ndash;播放音乐&ndash;&gt;-->
<#--<audio id="notice" loop="loop">-->
<#--    <source src="/sell/mp3/song.mp3" type="audio/mpeg" />-->
<#--</audio>-->

<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script src="https://cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<#--<script>-->
<#--    var websocket = null;-->
<#--    if('WebSocket' in window) {-->
<#--        websocket = new WebSocket('ws://sell.natapp4.cc/sell/webSocket');-->
<#--    }else {-->
<#--        alert('该浏览器不支持websocket!');-->
<#--    }-->

<#--    websocket.onopen = function (event) {-->
<#--        console.log('建立连接');-->
<#--    }-->

<#--    websocket.onclose = function (event) {-->
<#--        console.log('连接关闭');-->
<#--    }-->

<#--    websocket.onmessage = function (event) {-->
<#--        console.log('收到消息:' + event.data)-->
<#--        //弹窗提醒, 播放音乐-->
<#--        $('#myModal').modal('show');-->

<#--        document.getElementById('notice').play();-->
<#--    }-->

<#--    websocket.onerror = function () {-->
<#--        alert('websocket通信发生错误！');-->
<#--    }-->

<#--    window.onbeforeunload = function () {-->
<#--        websocket.close();-->
<#--    }-->

<#--</script>-->

</body>
</html>