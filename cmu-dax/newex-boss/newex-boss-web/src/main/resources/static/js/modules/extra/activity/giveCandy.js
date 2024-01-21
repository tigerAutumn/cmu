$(function () {
    GiveCandy.init();
});

var GiveCandy = {
    init: function () {
        giveCandyMVC.View.initControl();
        giveCandyMVC.View.bindEvent();
        giveCandyMVC.View.bindValidate();
    }
};

var giveCandyCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/activity/giveCandy',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/'

};

var giveCandyMVC = {
    URLs: {
        excelupdate: {
            url: giveCandyCommon.baseUrl + '/file-upload',
            method: 'POST'
        },
        Search: {
            url: giveCandyCommon.baseUrl + '/searchActivity',
            method: 'GET'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {

            $("#excelfile").filebox({
                onChange: function () {
                    giveCandyMVC.Util.excelupdate();
                }, prompt: '选择excel', buttonText: '&nbsp;选&nbsp;择&nbsp;',
            });


        },
        bindEvent: function () {
            $('#btn-search').bind('click', giveCandyMVC.Util.searchactivity);
        },
        bindValidate: function () {
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#user-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return giveCandyMVC.Controller.edit();
            }
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#user-dlg',
                formId: '#user-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                gridId: null,
            };
        },
        searchactivity: function () {
            var activityid = $("#activityid").textbox('getValue');
            $.ajax({
                url: giveCandyMVC.URLs.Search.url + '?activityid=' + activityid,
                type: 'GET',
                data: "",
                async: false,
                success: function (data) {
                    if (data.data === null) {
                        $("#all").html('调用远程接口失败');
                    } else {
                        $("#all").html('本活动：共发枚币' + data.data.data["amount"] + '' + "    共有" + data.data.data["count"] + "记录");
                    }
                },
                error: function (data) {
                    if (data.responseJSON.code == "403") {
                        $.messager.alert('提示', data.responseJSON.msg, 'info');

                    } else {
                        $.messager.alert('提示', 'error', 'info');
                    }
                    //上传失败时显示上传失败信息
                }
            });
        },
        excelupdate: function () {
            $("#ms").html("");
            $.messager.confirm('请再次确认信息', '模板格式对吗，里边的数据对吗，如果不对点取消，对的话点确认!', function (r) {
                if (r) {
                    var file = $("#excelfile").filebox('getValue');
                    var formData = new FormData($("#user-form")[0]);
                    $("#ms").html('提示：解析中...');
                    $.ajax({
                        url: giveCandyMVC.URLs.excelupdate.url,
                        type: 'POST',
                        data: formData,
                        async: false,
                        cache: false,
                        contentType: false,
                        processData: false,
                        success: function (data) {
                            if (data.data === null) {
                                $("#ms").html(data.msg);
                            } else {
                                $("#ms").html('提示：' + data.data);
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

        }
    }
};