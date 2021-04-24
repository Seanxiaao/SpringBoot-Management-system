<script>
    function onBridgeReady(){
        WeixinJSBridge.invoke(
            'getBrandWCPayRequest', {
                "appId":"${payResponse.appId}",
                "timeStamp":"${payResponse.timeStamp}",
                "nonceStr":"${payResponse.nonceStr}",
                "package":"${payResponse.packAge}",
                "signType":"MD5",
                "paySign":"${payResponse.paySign}"
            },
            function(res){
                location.href = "${returnUrl}";
            }
        );
    }
    if (typeof WeixinJSBridge == "undefined"){
        if( document.addEventListener ){
            document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
        }else if (document.attachEvent){
            document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
            document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
        }
    }else{
        onBridgeReady();
    }
</script>