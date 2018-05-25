function getBasePath() {
    var location = (window.location+'').split('/');
    var basePath = location[0]+'//'+location[2]+'/'+location[3];
    return basePath;
}



$().ready(function() {
    if(window !=top){
        top.location.href=location.href;
    }
    // 验证码
    $("#captchaImage").click(function(){
        var basePath=getBasePath();
        $(this).attr("src", "/common/captcha.jpg?time="+(new Date()).valueOf());
        return;
    });

    var $loginForm = $("#loginForm");
    var $username = $("#userName");
    var $password = $("#password");
    var $captcha = $("#captcha");
    var $captchaImage = $("#captchaImage");
    var $submit = $(":submit");
    var $loginButton=$("#loginButton");

    $loginButton.click(function(){
        submitForm();
    });

    $(document).keypress(function(e) {
        // 回车键事件
        if(e.which == 13) {
            submitForm();
        }
    });

    // 表单验证、记住用户名
    var validateForm =$loginForm.validate({
        errorClass:'login-main-error',
        rules: {
            userName: {
                required : true,
                minlength : 4
            },
            password: {
                required : true,
                minlength : 4
            },
            captcha: {
                required : true,
                minlength : 4
            }
        },
        messages: {
            userName: {
                required : '用户名不能为空',
                minlength :'用户名少于6个字符'
            },
            password: {
                required : '密码不能为空',
                minlength :'密码少于6个字符'
            },
            captcha: {
                required : '验证码不能为空',
                minlength :'验证码少于6个字符'
            }
        }/*,
         submitHandler: function(form) {
        */
    });



    function submitForm(){
        if(validateForm.form()){
            $.ajax({
                url: "/common/getkey.shtml",
                type: "GET",
                dataType: "json",
                cache: false,
                beforeSend: function() {
                    //$loginButton.prop("disabled", true);
                },
                success: function(data) {
                    var rsaKey = new RSAKey();
                    rsaKey.setPublic(b64tohex(data.modulus), b64tohex(data.exponent));
                    var enPassword = hex2b64(rsaKey.encrypt($password.val()));
                    $.ajax({
                        url: "/login/in.shtml",
                        type: "POST",
                        data: {
                            userName: $username.val(),
                            enPassword: enPassword,
                            captcha: $captcha.val()
                        },
                        dataType: "json",
                        cache: false,
                        success: function(data) {
                            //$submit.prop("disabled", false);
                            if (data.state) {
                                location.href = "/index.shtml";
                            } else {
                                $("#captchaImage").attr("src", "/common/captcha.jpg?time="+(new Date()).valueOf());
                                if(data.code==1002){
                                    var m=$("#captcha-error");
                                    if(m){
                                        m.parent().children('label').remove();
                                    }
                                    $("#captcha").after('<label id="captcha-error" class="login-main-error" for="captcha" style="display: block;">'+data.msg+'</label>');
                                    return;
                                }
                                if(data.code==1004){
                                    var m=$("#password-error");
                                    if(m){
                                        m.parent().children('label').remove();
                                    }
                                    $("#password").after('<label id="password-error" class="login-main-error" for="password" style="display: block;">'+data.msg+'</label>');
                                    return;
                                }
                                var m=$("#userName-error");
                                if(m){
                                    m.parent().children('label').remove();
                                }
                                $("#userName").after('<label id="userName-error" class="login-main-error" for="userName" style="display: block;">'+data.msg+'</label>');
                            }
                        }
                    });
                }
            });
        }
    }
});

