$(function() {

	// remove
	$('.remove').on('click', function(){
		var id = $(this).attr('id');

		layer.confirm('确认删除用户?', {icon: 3, title:'系统提示'}, function(index){
			layer.close(index);

			$.ajax({
				type : 'POST',
				url : base_url + '/jobusers/remove',
				data : {"id":id},
				dataType : "json",
				success : function(data){
					if (data.code == 200) {
						layer.open({
							title: '系统提示',
							content: '删除成功',
							icon: '1',
							end: function(layero, index){
								window.location.reload();
							}
						});
					} else {
						layer.open({
							title: '系统提示',
							content: (data.msg || "删除失败"),
							icon: '2'
						});
					}
				},
			});
		});

	});

	// jquery.validate 自定义校验 “英文字母开头，只含有英文字母、数字和下划线”
	jQuery.validator.addMethod("myValid01", function(value, element) {
		var length = value.length;
		var valid = /^[a-z][a-zA-Z0-9-]*$/;
		return this.optional(element) || valid.test(value);
	}, "限制以小写字母开头，由小写字母、数字和下划线组成");

	$('.add').on('click', function(){
		$('#addModal').modal({backdrop: false, keyboard: false}).modal('show');
	});
	var addModalValidate = $("#addModal .form").validate({
		errorElement : 'span',
		errorClass : 'help-block',
		focusInvalid : true,
		rules : {
            userName : {
				required : true,
				rangelength:[3,64],
				myValid01 : true
			},
            password : {
                required : true
            }
		},
		messages : {
            userName : {
				required :"请输入“用户名”",
				rangelength:"用户名长度限制为4~64",
				myValid01: "限制以小写字母开头，由小写字母、数字和中划线组成"
			},
            password : {
                required :"请输入“密码”",
                rangelength:"密码长度限制为4~64",
                myValid01: "限制以小写字母开头，由小写字母、数字和中划线组成"
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
			$.post(base_url + "/jobusers/save",  $("#addModal .form").serialize(), function(data, status) {
				if (data.code == "200") {
					$('#addModal').modal('hide');
					layer.open({
						title: '系统提示',
						content: '新增成功',
						icon: '1',
						end: function(layero, index){
							window.location.reload();
						}
					});
				} else {
					layer.open({
						title: '系统提示',
						content: (data.msg || "新增失败"),
						icon: '2'
					});
				}
			});
		}
	});
	$("#addModal").on('hide.bs.modal', function () {
		$("#addModal .form")[0].reset();
		addModalValidate.resetForm();
		$("#addModal .form .form-group").removeClass("has-error");
	});

	// 注册方式，切换
	$("#addModal input[name=addressType], #updateModal input[name=addressType]").click(function(){
		var addressType = $(this).val();
		var $addressList = $(this).parents("form").find("textarea[name=addressList]");
		if (addressType == 0) {
            $addressList.css("background-color", "#eee");	// 自动注册
            $addressList.attr("readonly","readonly");
			$addressList.val("");
		} else {
            $addressList.css("background-color", "white");
			$addressList.removeAttr("readonly");
		}
	});

	// update
	$('.update').on('click', function(){
		$("#updateModal .form input[name='id']").val($(this).attr("id"));
		$("#updateModal .form input[name='userName']").val($(this).attr("userName"));


		$('#updateModal').modal({backdrop: false, keyboard: false}).modal('show');
	});
	var updateModalValidate = $("#updateModal .form").validate({
		errorElement : 'span',
		errorClass : 'help-block',
		focusInvalid : true,
		rules : {
            id : {
                required : true,
            },
			userName : {
				required : true,
				rangelength:[4,64],
				myValid01 : true
			}
		},
		messages : {
			appName : {
				required :"请输入“用户名”"
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
			$.post(base_url + "/jobusers/update",  $("#updateModal .form").serialize(), function(data, status) {
				if (data.code == "200") {
					$('#addModal').modal('hide');

					layer.open({
						title: '系统提示',
						content: '更新成功',
						icon: '1',
						end: function(layero, index){
							window.location.reload();
						}
					});
				} else {
					layer.open({
						title: '系统提示',
						content: (data.msg || "更新失败"),
						icon: '2'
					});
				}
			});
		}
	});
	$("#updateModal").on('hide.bs.modal', function () {
		$("#updateModal .form")[0].reset();
		addModalValidate.resetForm();
		$("#updateModal .form .form-group").removeClass("has-error");
	});

	
});
