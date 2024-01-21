$(function(){
	// 复选框
    $('input').iCheck({
      checkboxClass: 'icheckbox_square-blue',
      radioClass: 'iradio_square-blue',
      increaseArea: '20%' // optional
    });
    
	// 登录.规则校验
	var loginFormValid = $("#loginForm").validate({
		errorElement : 'span',  
        errorClass : 'help-block',
        focusInvalid : true,  
        rules : {  
        	userName : {  
        		required : true
            },  
            password : {  
            	required : true
            } 
        }, 
        messages : {  
        	userName : {  
                required :"请输入登录账号."
            },  
            password : {
            	required :"请输入登录密码."
            }
        }, 
		highlight : function(element) {  
            $(element).closest('.form-group').addClass('has-error');  
        },
        success : function(label) {  
            label.closest('.form-group').removeClass('has-error');  
            label.remove();  
        },
        errorPlacement : function(error, element) {  
            element.parent('div').append(error);  
        },
        submitHandler : function(form) {
			$.post(base_url + "/login", $("#loginForm").serialize(), function(data, status) {
				if (data.code == "200") {
                    layer.msg('登录成功');
                    setTimeout(function(){
                        window.location.href = base_url+"/jobinfo";
                    }, 500);
                    /*layer.open({
                        title: '系统提示',
                        content: '登录成功',
                        icon: '1',
                        end: function(layero, index){
                            window.location.href = base_url;
                        }
                    });*/
				} else {
                    layer.open({
                        title: '系统提示',
                        content: (data.msg || "登录失败"),
                        icon: '2'
                    });
				}
			});
		}
	});
});