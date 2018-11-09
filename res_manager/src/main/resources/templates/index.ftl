<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width,initial-scale=1.0">
    <title>index</title>

    <link rel="icon" type="image/ico" href="ico.png"/>

<#--字体-->
    <link href="/plugins/ali-font/iconfont.css" rel="stylesheet"/>

<#--bootstrap css-->
    <link href="https://lib.baomitu.com/twitter-bootstrap/4.1.3/css/bootstrap.min.css" rel="stylesheet">

<#--vue-->
    <script src="https://lib.baomitu.com/vue/2.5.16/vue.js"></script>


<#--jquery 以及 webUplaoder-->
    <script src="https://lib.baomitu.com/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://lib.baomitu.com/webuploader/0.1.1/webuploader.html5only.min.js"></script>


<#--element-->
    <script src="https://lib.baomitu.com/element-ui/2.4.9/index.js"></script>
    <link href="https://lib.baomitu.com/element-ui/2.4.9/theme-chalk/index.css" rel="stylesheet">


</head>

<body>
<div id="app">
    <file-select></file-select>
</div>

</body>

</html>

<script src='/FileSelect.js'></script>

<script>


    var app = new Vue({
        el: '#app'
    })

</script>