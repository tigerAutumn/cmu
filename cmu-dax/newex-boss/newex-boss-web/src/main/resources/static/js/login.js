$(function () {
    MembershipLogin.init();
});

var MembershipLogin = {
    init: function () {
        LoginMVC.View.initControl();
        LoginMVC.View.bindEvent();
        LoginMVC.View.bindValidate();
    }
};

var LoginMVC = {
    URLs: {
        login: {
            url: BossGlobal.ctxPath + '/login/auth',
            method: 'POST'
        },
        success: {
            url: BossGlobal.ctxPath + '/home/index',
            method: 'POST'
        }
    },
    View: {
        initControl: function () {
            $("#account").focus();
            $("#login-message-tips").hide();
        },
        bindEvent: function () {
            document.onkeydown = function (e) {
                var evt = e ? e : (window.event ? window.event : null)
                if (evt.keyCode === 13) {
                    LoginMVC.Controller.login();
                }
            };
            $('#btnLogin').click(LoginMVC.Controller.login);
        },
        bindValidate: function () {
            $("#login-form").validate({
                rules: {
                    account: {
                        required: true,
                    },
                    password: {
                        required: true,
                        minlength: 3,
                        maxlength: 20
                    }
                },
                messages: {
                    account: {
                        required: ''
                    },
                    password: {
                        required: '',
                        minlength: 6
                    }
                },
                errorPlacement: function (error, element) {
                    error.insertAfter(element.parent());
                }
            });
        }
    },
    Controller: {
        showTips: function (type, content) {
            var tipsContainer = $('#hintInfo');
            tipsContainer.html(content);
            tipsContainer.show();
        },
        login: function () {
            if ($('#login-form').validate().form()) {
                var data = {
                    "account": $("#account").val(),
                    "password": $("#password").val(),
                    "captcha": $("#captcha").val()
                };
                $.post(LoginMVC.URLs.login.url, data, function (result) {
                    if (result.code) {
                        LoginMVC.Controller.showTips('error', result.msg);
                    } else {
                        LoginMVC.Controller.showTips('success', '登录成功，正在跳转...');
                        window.location = LoginMVC.URLs.success.url;
                    }
                }, 'json').error(function (data) {
                    LoginMVC.Controller.showTips('error', data.responseJSON.msg);
                });
            }
        }
    }
};