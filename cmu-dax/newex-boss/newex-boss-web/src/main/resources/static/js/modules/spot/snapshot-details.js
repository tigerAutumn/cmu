var taskid;
$(function () {
    var url = location.search;
    var Request = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.substr(1)　//去掉?号
        strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
            Request[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
        }
    }
    taskid = Request["taskid"];
    Snapshotdetails.init();


});

var Snapshotdetails = {
    init: function () {
        UserSnapshUserSnapshotDetailsMVC.View.initControl();
        UserSnapshUserSnapshotDetailsMVC.View.bindEvent();
        UserSnapshUserSnapshotDetailsMVC.View.bindValidate();
    }
};

var UserSnapshotDetailsCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/spot/snapshot-details/',
    baseRoleUrl: BossGlobal.ctxPath + '/v1/boss/spot/snapshot-details/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/'
};

var UserSnapshUserSnapshotDetailsMVC = {
    URLs: {

        list: {
            url: UserSnapshotDetailsCommon.baseUrl + 'list',
            method: 'GET'
        },
        exceldown: {
            url: UserSnapshotDetailsCommon.baseUrl + 'info',
            method: 'POST'
        }
    },
    Model: {

        marginOpen: {},
        roles: {}
    },
    View: {
        initControl: function () {

            //初始化
            $("#user-datagrid").datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                pageSize: 20,
                rownumbers: true,
                toolbar: [{
                    iconCls: 'icon-download',
                    text: '点击下载excel文件',
                    handler: function () {
                        UserSnapshUserSnapshotDetailsMVC.Controller.find();
                    }
                }],
                loadFilter: function (src) {
                    if (!src.code) {
                        return src.data;
                    }
                    return $.messager.alert('失败', src.msg, 'error');
                },
                onLoadSuccess: function () {

                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });
            UserSnapshUserSnapshotDetailsMVC.Util.loadDatagrid();


        },
        bindEvent: function () {
            $('#btn-downexcel').bind('click', UserSnapshUserSnapshotDetailsMVC.Controller.find);
        },
        bindValidate: function () {
        }
    },
    Controller: {

        find: function () {
            window.open(UserSnapshUserSnapshotDetailsMVC.URLs.exceldown.url + "?taskid=" + taskid, "_blank", "height=100, width=400, toolbar= no, menubar=no, scrollbars=no, resizable=no, location=no, status=no")
        }
    },
    Util: {
        loadDatagrid: function () {

            $.ajax({
                url: UserSnapshUserSnapshotDetailsMVC.URLs.list.url + '?taskid=' + taskid,
                type: "get",
                success: function (data) {
                    var options = $("#user-datagrid").datagrid("options"); //取出当前datagrid的配置

                    var json = decodeURIComponent(data.data.columns);//解码
                    options.columns = eval(json);
                    options.url = UserSnapshUserSnapshotDetailsMVC.URLs.list.url + '?taskid=' + taskid;
                    $('#user-datagrid').datagrid(options);

                    $('#user-datagrid').datagrid("loadData", data.data.rows);//实例化之后立刻载入数据源,加载本地数据，旧的行会被移除。

                }
            });
        }
    }
};