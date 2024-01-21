var PersonalSettingsUtils = {
    google: function () {
        var userId = $("#userId").val();
        $.messager.confirm('请再次确认信息', '解绑Google验证码？', function (r) {
            if (r) {
                var tmpl = UsersInfoMVC.URLs.google.url;
                var data = {
                    userId: userId,
                };
                var url = juicer(tmpl, data);
                $.ajax({
                    url: url,
                    type: 'put',
                    data: {},
                    success: function (data) {
                        //$("#report-detail-dlg").dialog('close');
                        //EasyUIUtils.reloadDatagrid('#user-datagrid');
                    },
                    error: function (data) {
                        if (data.responseJSON.code == "403") {
                            $.messager.alert('提示', data.responseJSON.msg, 'info');

                        } else {
                            $.messager.alert('提示', 'error', 'info');
                        }
                    }
                });

            }
        });

    },
    mobile: function () {
        var userId = $("#userId").val();
        $.messager.confirm('请再次确认信息', '解绑手机？', function (r) {
            if (r) {
                var tmpl = UsersInfoMVC.URLs.mobile.url;
                var data = {
                    userId: userId,
                };
                var url = juicer(tmpl, data);
                $.ajax({
                    url: url,
                    type: 'put',
                    data: {},
                    success: function (data) {
                        //$("#report-detail-dlg").dialog('close');
                        //EasyUIUtils.reloadDatagrid('#user-datagrid');
                    },
                    error: function (data) {
                        if (data.responseJSON.code == "403") {
                            $.messager.alert('提示', data.responseJSON.msg, 'info');

                        } else {
                            $.messager.alert('提示', 'error', 'info');
                        }
                    }
                });

            }
        });

    },
    updateC2CUserType: function (userId) {
        $.ajax({
            url: UsersInfoMVC.URLs.getUserInfo.url,
            type: 'POST',
            data: {userId: userId},
            success: function (data) {
                if (data.code == 0) {
                    if (data.data.disabled) {
                        $("#report-c2c-userType").text("冻结");
                        $("#c2cfreeze").hide();
                        $("#c2cupfreeze").show();

                    } else {
                        $("#report-c2c-userType").text("未冻结");
                        $("#c2cfreeze").show();
                        $("#c2cupfreeze").hide();
                    }
                    if (data.data.isCertifiedUser) {
                        $("#report-c2c-type").text("已开通");
                        $("#c2ccancelBttOne").show();
                        $("#c2ccompleteBttOne").hide();
                    } else {
                        $("#report-c2c-type").text("未开通");
                        $("#c2ccancelBttOne").hide();
                        $("#c2ccompleteBttOne").show();
                    }
                }

            },
            error: function (data) {
                if (data.responseJSON.code == "403") {
                    $.messager.alert('提示', data.responseJSON.msg, 'info');

                } else {
                    $.messager.alert('提示', 'error', 'info');
                }
            }
        });
    },
    C2CUserType: function (type) {
        var userId = $("#userId").val();
        var msg = "是否禁止？";
        var url;
        if (type == 0) {
            var msg = "是否禁止？";
            url = UsersInfoMVC.URLs.c2cBusinessFreeze.url;
        } else {
            var msg = "是否开通？";
            url = UsersInfoMVC.URLs.c2cBusinessUpFreeze.url;
        }
        $.messager.confirm('请再次确认信息', msg, function (r) {
            if (r) {
                $.ajax({
                    url: url,
                    type: 'POST',
                    data: {userId: userId},
                    success: function (data) {
                        if (data.msg == "success") {
                            PersonalSettingsUtils.updateC2CUserType(userId);
                        } else {
                            $.messager.alert('提示', data.msg, 'info');
                        }
                    },
                    error: function (data) {
                        if (data.responseJSON.code == "403") {
                            $.messager.alert('提示', data.responseJSON.msg, 'info');

                        } else {
                            $.messager.alert('提示', 'error', 'info');
                        }
                    }
                });

            }
        });
    },
    freezeEmail24Hours: function () {
        var userId = $("#userId").val();
        $.messager.confirm('请再次确认信息', '解除用户24小时修改不能提现的冻结？', function (r) {
            if (r) {
                var unfreezeUrl = juicer(UsersInfoMVC.URLs.freezeEmail24Hours.url, {userId: userId});
                $.ajax({
                    url: unfreezeUrl,
                    type: 'post',
                    success: function (data) {
                        $("#report-detail-dlg").dialog('close');
                        $.messager.alert('success', 'success', 'success');
                    },
                    error: function (data) {
                        if (data.responseJSON.code == "403") {
                            $.messager.alert('提示', data.responseJSON.msg, 'info');

                        } else {
                            $.messager.alert('提示', 'error', 'info');
                        }
                    }
                });

            }
        });

    },
    frozen: function (bizType, checked) {
        var flag = 0;
        if (checked) {
            flag = 1;
        }

        if (bizType === 'all') {
            if (UsersInfoMVC.Model.frozen === flag) {
                return;
            } else {
                UsersInfoMVC.Model.frozen = flag;
            }
        } else if (bizType === 'spot') {
            if (UsersInfoMVC.Model.spotFrozenFlag === flag) {
                return;
            } else {
                UsersInfoMVC.Model.spotFrozenFlag = flag;
            }
        } else if (bizType === 'c2c') {
            if (UsersInfoMVC.Model.c2cFrozenFlag === flag) {
                return;
            } else {
                UsersInfoMVC.Model.c2cFrozenFlag = flag;
            }
        } else if (bizType === 'contracts') {
            if (UsersInfoMVC.Model.contractsFrozenFlag === flag) {
                return;
            } else {
                UsersInfoMVC.Model.contractsFrozenFlag = flag;
            }
        } else if (bizType === 'asset') {
            if (UsersInfoMVC.Model.assetFrozenFlag === flag) {
                return;
            } else {
                UsersInfoMVC.Model.assetFrozenFlag = flag;
            }
        }

        var userId = $("#userId").val();
        var freezeUrl = juicer(UsersInfoMVC.URLs.freezeBiz.url, {userId: userId});
        var unfreezeUrl = juicer(UsersInfoMVC.URLs.unfreezeBiz.url, {userId: userId});
        var url = checked ? freezeUrl : unfreezeUrl;
        var options = {
            url: url,
            data: {
                bizType: bizType,
                userId: userId,
                remark: ''
            }
        };

        $.post(options.url, options.data, function (result) {
            if (result.code) {
                return $.messager.show({
                    title: '错误',
                    msg: result.msg
                });
            } else {
                // PersonalSettingsUtils.loadFrozenSwitch(userId);
            }
        }, 'json')
            .error(function (data) {
                if (data.responseJSON.code == "403") {
                    $.messager.show({
                        title: '错误',
                        msg: data.responseJSON.msg
                    });
                } else {
                    $.messager.alert('提示', data.responseJSON.msg, 'error');
                }
            });
    },
    loadFrozenSwitch: function (userId) {
        var url = juicer(UsersInfoMVC.URLs.detail.url, {userId: userId});

        $.ajax({
            url: url,
            type: 'get',
            success: function (result) {
                if (result.code) {
                    return $.messager.show({
                        title: '错误',
                        msg: result.msg
                    });
                } else {
                    var detail = result.data;

                    UsersInfoMVC.Model.frozen = detail.frozen;
                    UsersInfoMVC.Model.spotFrozenFlag = detail.spotFrozenFlag;
                    UsersInfoMVC.Model.c2cFrozenFlag = detail.c2cFrozenFlag;
                    UsersInfoMVC.Model.contractsFrozenFlag = detail.contractsFrozenFlag;
                    UsersInfoMVC.Model.assetFrozenFlag = detail.assetFrozenFlag;

                    $('#frozenSwitch').switchbutton(detail.frozen ? 'check' : 'uncheck');
                    $('#spotFrozenSwitch').switchbutton(detail.spotFrozenFlag ? 'check' : 'uncheck');
                    $('#c2cFrozenSwitch').switchbutton(detail.c2cFrozenFlag ? 'check' : 'uncheck');
                    $('#contractsFrozenSwitch').switchbutton(detail.contractsFrozenFlag ? 'check' : 'uncheck');
                    $('#assetFrozenSwitch').switchbutton(detail.assetFrozenFlag ? 'check' : 'uncheck');
                }

            },
            error: function (data) {
                if (data.responseJSON.code == "403") {
                    $.messager.alert('提示', data.responseJSON.msg, 'error');
                } else {
                    $.messager.alert('提示', data.responseJSON.msg, 'error');
                }
            }
        });
    }
};