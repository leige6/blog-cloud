<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <meta http-equiv="x-ua-compatible" content="IE=edge">
    <title>博客后台首页</title>
    <link rel="shortcut icon" href="/image/favicon.ico" />
    <!--<link rel="stylesheet" type="text/css" href="plugs/layui/css/layui.css">-->
    <link href="/css/login.css" type="text/css" rel="stylesheet" >
   <script type="text/javascript" src="/js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="/js/jquery.validate.js"></script>
    <!--<link rel="stylesheet" type="text/css" href="plugs/easyui/themes/gray/easyui.css" />
    <link rel="stylesheet" type="text/css" href="plugs/easyui/themes/icon.css" />
    <script type="text/javascript" src="plugs/easyui/jquery.easyui.min.js" charset="utf-8"></script>
    <script type="text/javascript" src="plugs/easyui/locale/easyui-lang-zh_CN.js" charset="utf-8"></script>-->
    <script type="text/javascript" src="/js/rsa/jsbn.js"></script>
    <script type="text/javascript" src="/js/rsa/prng4.js"></script>
    <script type="text/javascript" src="/js/rsa/rng.js"></script>
    <script type="text/javascript" src="/js/rsa/rsa.js"></script>
    <script type="text/javascript" src="/js/rsa/base64.js"></script>
   <!-- <script type="text/javascript" src="js/extJs.js"></script>-->
    <script type="text/javascript" src="/js/common.js"></script>
    <script type="text/javascript" src="/js/login.js"></script>
    <script type="text/javascript">
    </script>
</head>
<body>
<div class="head">
<div class="head-inner">
    <div class="logo"></div>
    <h3 class="welcome">
        欢迎登录<br>
        <span style="font-size:16px;font-weight:500;">磊哥博客后台管理系统</span>
    </h3>
</div>
</div>
<div class="main">
    <div class="main-inner">
        <div class="main-left"></div>
        <div class="login">
            <div class="login-head"><span>管理员登录</span></div>
            <div class="login-main">
                <form method="post" id="loginForm">
                <div class="login-main-row">
                    <div class="login-main-row-left">
                        <span class="login-main-row-left-span">账号</span>
                    </div>
                    <div class="login-main-row-right">
                        <input type="text" id="userName" name="userName" class="login-main-input" value="">
                    </div>
                </div>
                <div class="login-main-row">
                    <div class="login-main-row-left">
                        <span class="login-main-row-left-span">密码</span>
                    </div>
                    <div class="login-main-row-right">
                        <input type="password" id="password" name="password" class="login-main-input" value="">
                    </div>
                </div>
                <div class="login-main-row">
                    <div class="login-main-row-left">
                        <span class="login-main-row-left-span">验证码</span>
                    </div>
                    <div class="login-main-row-right login-main-input-code">
                        <input type="text" id="captcha" name="captcha" class="login-main-input">
                    </div>
                    <img id="captchaImage" class="captchaImage" src="common/captcha.jpg">
                </div>
                <div class="login-main-row login-main-row-loginButton">
                    <div class="loginButton-div">
                        <button type="button" id="loginButton" class="loginButton">登    录</button>
                    </div>
                </div>
                </form>
            </div>
        </div>
    </div>
</div>
<div class="foot">
<div class="foot-inner">
    <p><span>Copyright 版权所有&copy;2018 磊哥网络科技有限公司 京ICP备06035821号</span></p>
    <p>
        <a target="_blank" href="http://www.lolabc.cn">网站首页</a>&nbsp;&nbsp;
        |&nbsp;&nbsp;<a target="_blank" href="#"><img src="/image/favicon.ico"></a>&nbsp;&nbsp;
        |&nbsp;&nbsp;<a target="_blank" href="#">关于我们</a>
    </p>
</div>
</div>
</body>
</html>