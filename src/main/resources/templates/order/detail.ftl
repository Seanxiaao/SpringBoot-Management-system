<html>
<#include "../common/header.ftl">

<body>
<div id="wrapper" class="toggled">

    <#include "../common/nav.ftl">

    <div id="page-content-wrapper">
        <div class="container">
            <div class="row clearfix">
                <div class="col-md-4 column">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>Order id</th>
                            <th>Order Total Amount</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${orderDTO.orderId}</td>
                            <td>${orderDTO.orderAmount}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <#--订单详情表数据-->
                <div class="col-md-12 column">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th>Product Id</th>
                            <th>Product Name</th>
                            <th>Price</th>
                            <th>Amount</th>
                            <th>Total Amount</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list orderDTO.orderDetailList as orderDetail>
                            <tr>
                                <td>${orderDetail.productId}</td>
                                <td>${orderDetail.productName}</td>
                                <td>${orderDetail.productPrice}</td>
                                <td>${orderDetail.productAmount}</td>
                                <td>${orderDetail.productAmount * orderDetail.productPrice}</td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>

                <#--操作-->
                <div class="col-md-12 column">
                    <#if orderDTO.getOrderStatusEnum().message == "new order">
                        <a href="/sell/seller/order/finish?orderId=${orderDTO.orderId}" type="button" class="btn btn-default btn-primary">Finish Order</a>
                        <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}" type="button" class="btn btn-default btn-danger">Cancel Order</a>
                    </#if>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
</html>