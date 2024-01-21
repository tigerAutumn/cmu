$(function () {
    TimeConfig.init();
});

var TimeConfig = {
    init: function () {
        TimeConfigMVC.View.initControl();
        TimeConfigMVC.View.bindEvent();
    }
};

var TimeConfigCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/activity/hundred2/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
};

var TimeConfigMVC = {
    URLs: {
        list: {
            url: TimeConfigCommon.baseUrl + 'timeConfig/list',
            method: 'GET'
        },
        saveOrUpdate: {
            url: TimeConfigCommon.baseUrl + 'timeConfig/saveOrUpdate',
            method: 'POST'
        }
    },
    Model: {},
    View: {
        initControl: function () {
            TimeConfigMVC.Controller.loadTimeConfig();
        },
        bindEvent: function () {
            $('#timeConfigBtn').on('click', function () {
                TimeConfigMVC.Controller.saveOrUpdate();
            });
        }
    },
    Controller: {
        saveOrUpdate: function () {
            if ($('#time-config-form').form('validate')) {
                var data = $('#time-config-form').serialize();
                var url = TimeConfigMVC.URLs.saveOrUpdate.url;
                $.ajax({
                    url: url,
                    type: 'POST',
                    async: false,
                    data: data,
                    dataType: 'json',
                    success: function (result) {
                        if (result.code === "403") {
                            $.messager.show({
                                title: '错误',
                                msg: result.msg
                            });
                        } else {
                            console.log('更新活动时间成功');
                            TimeConfigMVC.Controller.loadTimeConfig();
                            EasyUIUtils.showMsg(result.msg || "操作成功");
                        }
                    },
                    error: function (result) {
                        if (result.responseJSON.code === "403") {
                            $.messager.show({
                                title: '错误',
                                msg: result.responseJSON.msg
                            });
                        } else {
                            $.messager.alert('提示', 'error', 'info');
                        }
                    }
                });
            }
        },
        loadTimeConfig: function () {
            $.ajax({
                url: TimeConfigMVC.URLs.list.url + "?v=" + Math.random(),
                type: 'GET',
                async: false,
                dataType: 'json',
                success: function (result) {
                    console.log(result.data);
                    if (result.data) {
                        $('#time-config-form').form('load', {
                            rushEndDate: moment(new Date(result.data.rushEndDate)).format("YYYY-MM-DD HH:mm:ss"),
                            rushStartDate: moment(new Date(result.data.rushStartDate)).format("YYYY-MM-DD HH:mm:ss"),
                            wheelEndDate: moment(new Date(result.data.wheelEndDate)).format("YYYY-MM-DD HH:mm:ss"),
                            wheelStartDate: moment(new Date(result.data.wheelStartDate)).format("YYYY-MM-DD HH:mm:ss")
                        });
                    } else {
                        console.log('无活动时间配置');
                    }
                },
                error: function (result) {
                    if (result.code === "403") {
                        $.messager.show({
                            title: '错误',
                            msg: result.msg
                        });
                    } else {
                        $.messager.alert('提示', 'error', 'info');
                    }
                }
            });
        }
    },
    Util: {}
};