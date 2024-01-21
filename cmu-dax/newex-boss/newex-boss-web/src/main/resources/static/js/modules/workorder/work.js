$(function () {
    WorkMenu.init();
});

var WorkMenu = {
    init: function () {
        WorkMVC.View.initControl();
        WorkMVC.View.bindEvent();
        WorkMVC.View.bindValidate();
        WorkMVC.View.initData();

    }
};

var WorkCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/workorder/work/',
    workOrderUserUrl: BossGlobal.ctxPath + '/v1/boss/users/usersInfo/',
    assetBaseUrl: BossGlobal.ctxPath + '/v1/boss/asset/currency/',
    userBaseUrl: BossGlobal.ctxPath + '/v1/boss/c2c/order/',
    getAll: BossGlobal.ctxPath + '/v1/boss/admin/users/',
    file: BossGlobal.ctxPath + '/v1/boss/upload/extra/',
    groupsBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/groups/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/'
};

var WorkMVC = {
    URLs: {
        list: {
            url: WorkCommon.baseUrl + 'list',
            method: 'GET'
        },
        user: {
            url: WorkCommon.baseUrl + 'getUserInfo',
            method: 'GET'
        },
        workDetails: {
            url: WorkCommon.baseUrl + 'workDetails',
            method: 'GET'
        },
        updateType: {
            url: WorkCommon.baseUrl + 'updateType',
            method: 'GET'
        },
        getAll: {
            url: WorkCommon.getAll + 'getAll',
            method: 'GET'
        },
        assetRecord: {
            url: WorkCommon.assetBaseUrl + "getUserWorkRecord",
            method:
                'GET'
        },
        getSellInfo: {
            url: WorkCommon.userBaseUrl + 'getSellInfo',
            method: 'GET'
        },
        transmit: {
            url: WorkCommon.baseUrl + 'transmit',
            method: 'GET'
        },
        add: {
            url: WorkCommon.baseUrl + 'add',
            method: 'GET'
        },
        upload: {
            url: WorkCommon.file + 'upload',
            method: 'GET'
        },
        Solve: {
            url: WorkCommon.baseUrl + 'Solve',
            method: 'POST'
        },
        wait: {
            url: WorkCommon.baseUrl + 'wait',
            method: 'POST'
        },
        save: {
            url: WorkCommon.baseUrl + 'attachFile',
            method: 'POST'
        },
        workOrderUserInfo: {
            url: WorkCommon.workOrderUserUrl,
            method: 'GET'
        },
        getAdminGroupTree: {
            url: WorkCommon.groupsBaseUrl + 'getGroupTree',
            method: 'GET'
        },
        getAdminGroupUser: {
            url: WorkCommon.getAll + 'getByGroupId',
            method: 'GET'
        }
    },
    Model: {
        marginOpen: {}
    },
    View: {
        initControl: function () {
            $.get(WorkMVC.URLs.getAdminGroupTree.url, "", function (_data) {
                $("#groupId").combotree({
                    data: _data.data,
                    valueField: "id",
                    required: true,
                    textField: "text",
                    panelHeight: 300,
                    editable: true,
                    onSelect: function (record) {
                        WorkMVC.Controller.getorgan(record.id);
                    }
                });
            });

            $('#reset-work-dlg').dialog({
                closed: true,
                modal: false,
                width: 560,
                height: 220,
                iconCls: '',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#reset-work-dlg").dialog('close');

                    }
                }, {
                    text: '转发工单',
                    iconCls: 'icon-save',
                    handler: WorkMVC.Controller.saveTransmit
                }]
            });

            $('#tabl').tabs({
                border: false,
                onSelect: function (title) {
                    var tab = $('#tabl').tabs('getSelected');
                    var index = $('#tabl').tabs('getTabIndex', tab);
                    $('#tabIndex').val(index);
                    if (index == 0) {
                        $('#mine-tab1').datagrid({
                            method: 'get',
                            fit: false,
                            fitColumns: true,
                            singleSelect: true,
                            pagination: true,
                            rownumbers: false,
                            pageSize: 10,
                            url: WorkMVC.URLs.list.url + '?status=1',
                            toolbar: [],
                            loadFilter: function (src) {
                                if (!src.code) {
                                    return src.data;
                                }
                                return $.messager.alert('失败', src.msg, 'error');
                            },
                            columns: [[
                                {
                                    field: 'id',
                                    title: '工单ID',
                                    hidden: true,
                                    width: 100,

                                }, {
                                    field: 'userId',
                                    title: '用户ID',
                                    hidden: true,
                                    width: 100,

                                }, {
                                    field: 'content',
                                    title: '已受理数据',
                                    width: 100,
                                    // formatter: function (value, row, index) {
                                    //     alert(value);
                                    //     return "<div style='padding: 10px;'><div style='float: left'>用户名</div><div style='float: right'>3123123123</div><div class='clear'></div><div style='padding-top: 10px;'><div style='float: left;'>提现问题-提现BTC未到账</div><div style='float: right;color: #00bbee;'>待确认</div><div class='clear'></div></div><div style='padding-top: 10px;'>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</div></div>"
                                    // }

                                }]],
                            onDblClickRow: function (rowIndex, rowData) {
                                return WorkMVC.Controller.tab1showDetail();
                            },
                            onLoadError: function (data) {
                                return $.messager.alert('失败', data.responseJSON.msg, 'error');
                            },
                            onLoadSuccess: function () { //加载完成后,设置选中第一项
                                $('.pagination-page-list').hide();
                                $('.pagination-info').hide();
                                $('.pagination-num').width(20);

                            }
                        });
                    }
                    if (index == 1) {
                        $('#mine-tab1').datagrid({
                            method: 'get',
                            fit: false,
                            fitColumns: true,
                            singleSelect: true,
                            pagination: true,
                            rownumbers: false,
                            pageSize: 10,
                            url: WorkMVC.URLs.list.url + '?status=2',
                            toolbar: [],
                            loadFilter: function (src) {
                                if (!src.code) {
                                    return src.data;
                                }
                                return $.messager.alert('失败', src.msg, 'error');
                            },
                            columns: [[
                                {
                                    field: 'id',
                                    title: '工单ID',
                                    hidden: true,
                                    width: 100,

                                }, {
                                    field: 'userId',
                                    title: '用户ID',
                                    hidden: true,
                                    width: 100,

                                }, {
                                    field: 'content',
                                    title: '处理中数据',
                                    width: 100,
                                    // formatter: function (value, row, index) {
                                    //     alert(value);
                                    //     return "<div style='padding: 10px;'><div style='float: left'>用户名</div><div style='float: right'>3123123123</div><div class='clear'></div><div style='padding-top: 10px;'><div style='float: left;'>提现问题-提现BTC未到账</div><div style='float: right;color: #00bbee;'>待确认</div><div class='clear'></div></div><div style='padding-top: 10px;'>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</div></div>"
                                    // }

                                }]],
                            onDblClickRow: function (rowIndex, rowData) {
                                return WorkMVC.Controller.tab1showDetail();
                            },
                            onLoadError: function (data) {
                                return $.messager.alert('失败', data.responseJSON.msg, 'error');
                            },
                            onLoadSuccess: function () { //加载完成后,设置选中第一项
                                $('.pagination-page-list').hide();
                                $('.pagination-info').hide();
                                $('.pagination-num').width(20);

                            }
                        });
                    }
                    if (index == 2) {
                        $('#mine-tab1').datagrid({
                            method: 'get',
                            fit: false,
                            fitColumns: true,
                            singleSelect: true,
                            pagination: true,
                            rownumbers: false,
                            pageSize: 10,
                            url: WorkMVC.URLs.list.url + '?status=5',
                            toolbar: [],
                            loadFilter: function (src) {
                                if (!src.code) {
                                    return src.data;
                                }
                                return $.messager.alert('失败', src.msg, 'error');
                            },
                            columns: [[
                                {
                                    field: 'id',
                                    title: '工单ID',
                                    hidden: true,
                                    width: 100,

                                }, {
                                    field: 'userId',
                                    title: '用户ID',
                                    hidden: true,
                                    width: 100,

                                }, {
                                    field: 'content',
                                    title: '等待中数据',
                                    width: 100,
                                    // formatter: function (value, row, index) {
                                    //     alert(value);
                                    //     return "<div style='padding: 10px;'><div style='float: left'>用户名</div><div style='float: right'>3123123123</div><div class='clear'></div><div style='padding-top: 10px;'><div style='float: left;'>提现问题-提现BTC未到账</div><div style='float: right;color: #00bbee;'>待确认</div><div class='clear'></div></div><div style='padding-top: 10px;'>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</div></div>"
                                    // }

                                }]],
                            onDblClickRow: function (rowIndex, rowData) {
                                return WorkMVC.Controller.tab1showDetail();
                            },
                            onLoadError: function (data) {
                                return $.messager.alert('失败', data.responseJSON.msg, 'error');
                            },
                            onLoadSuccess: function () { //加载完成后,设置选中第一项
                                $('.pagination-page-list').hide();
                                $('.pagination-info').hide();
                                $('.pagination-num').width(20);

                            }
                        });
                    }
                    if (index == 3) {
                        $('#mine-tab1').datagrid({
                            method: 'get',
                            fit: false,
                            fitColumns: true,
                            singleSelect: true,
                            pagination: true,
                            rownumbers: false,
                            pageSize: 10,
                            url: WorkMVC.URLs.list.url + '?status=3',
                            toolbar: [],
                            loadFilter: function (src) {
                                if (!src.code) {
                                    return src.data;
                                }
                                return $.messager.alert('失败', src.msg, 'error');
                            },
                            columns: [[
                                {
                                    field: 'id',
                                    title: '工单ID',
                                    hidden: true,
                                    width: 100,

                                }, {
                                    field: 'userId',
                                    title: '用户ID',
                                    hidden: true,
                                    width: 100,

                                }, {
                                    field: 'content',
                                    title: '待确定数据',
                                    width: 100,
                                    // formatter: function (value, row, index) {
                                    //     alert(value);
                                    //     return "<div style='padding: 10px;'><div style='float: left'>用户名</div><div style='float: right'>3123123123</div><div class='clear'></div><div style='padding-top: 10px;'><div style='float: left;'>提现问题-提现BTC未到账</div><div style='float: right;color: #00bbee;'>待确认</div><div class='clear'></div></div><div style='padding-top: 10px;'>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</div></div>"
                                    // }

                                }]],
                            onDblClickRow: function (rowIndex, rowData) {
                                return WorkMVC.Controller.tab1showDetail();
                            },
                            onLoadError: function (data) {
                                return $.messager.alert('失败', data.responseJSON.msg, 'error');
                            },
                            onLoadSuccess: function () { //加载完成后,设置选中第一项
                                $('.pagination-page-list').hide();
                                $('.pagination-info').hide();
                                $('.pagination-num').width(20);

                            }
                        });
                    }
                    if (index == 4) {
                        $('#mine-tab1').datagrid({
                            method: 'get',
                            fit: false,
                            fitColumns: true,
                            singleSelect: true,
                            pagination: true,
                            rownumbers: false,
                            pageSize: 10,
                            url: WorkMVC.URLs.list.url + '?status=4',
                            toolbar: [],
                            loadFilter: function (src) {
                                if (!src.code) {
                                    return src.data;
                                }
                                return $.messager.alert('失败', src.msg, 'error');
                            },
                            columns: [[
                                {
                                    field: 'id',
                                    title: '工单ID',
                                    hidden: true,
                                    width: 100,

                                }, {
                                    field: 'userId',
                                    title: '用户ID',
                                    hidden: true,
                                    width: 100,

                                }, {
                                    field: 'content',
                                    title: '已解决数据',
                                    width: 100,
                                    // formatter: function (value, row, index) {
                                    //     alert(value);
                                    //     return "<div style='padding: 10px;'><div style='float: left'>用户名</div><div style='float: right'>3123123123</div><div class='clear'></div><div style='padding-top: 10px;'><div style='float: left;'>提现问题-提现BTC未到账</div><div style='float: right;color: #00bbee;'>待确认</div><div class='clear'></div></div><div style='padding-top: 10px;'>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</div></div>"
                                    // }

                                }]],
                            onDblClickRow: function (rowIndex, rowData) {
                                return WorkMVC.Controller.tab1showDetail();
                            },
                            onLoadError: function (data) {
                                return $.messager.alert('失败', data.responseJSON.msg, 'error');
                            },
                            onLoadSuccess: function () { //加载完成后,设置选中第一项
                                $('.pagination-page-list').hide();
                                $('.pagination-info').hide();
                                $('.pagination-num').width(20);

                            }
                        });
                    }

                }
            });

            $('#tab_right').tabs({
                border: false,
                onSelect: function (title) {
                    var userId = $("#userId").val();
                    var tab = $('#tab_right').tabs('getSelected');
                    var index = $('#tab_right').tabs('getTabIndex', tab);

                    if (index == 0) {
                        if (userId != "") {
                            //获得充值记录
                            $('#RedrawMoney-record-datagrid').datagrid({
                                method: 'get',
                                fit: false,
                                autoRowWidth: true,
                                fitColumns: true,
                                singleSelect: true,
                                pagination: false,
                                rownumbers: false,
                                sortOrder: 'desc',
                                pageSize: 50,
                                pageNumber: 1,
                                url: WorkMVC.URLs.assetRecord.url + '?userId=' + userId + '&transferType=withdraw',
                                loadFilter: function (src) {
                                    if (!src.code) {
                                        return src.data;
                                    }
                                    return $.messager.alert('失败', src.msg, 'error');
                                },
                                columns: [[
                                    {
                                        field: 'content',
                                        title: '充值记录',
                                        width: 100,
                                    }
                                ]],
                                onDblClickRow: function () {

                                },
                                onLoadSuccess: function () { //加载完成后,设置选中第一项

                                },
                                onLoadError: function (data) {
                                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                                }
                            });
                        }
                    }
                    if (index == 1) {
                        if (userId != "") {
                            //获得提现记录
                            $('#extract-record-datagrid').datagrid({
                                method: 'get',
                                fit: false,
                                autoRowWidth: true,
                                fitColumns: true,
                                singleSelect: true,
                                pagination: false,
                                rownumbers: false,
                                sortOrder: 'desc',
                                pageSize: 50,
                                pageNumber: 1,
                                url: WorkMVC.URLs.assetRecord.url + '?userId=' + userId + '&transferType=deposit',
                                loadFilter: function (src) {
                                    if (!src.code) {
                                        return src.data;
                                    }
                                    return $.messager.alert('失败', src.msg, 'error');
                                },
                                columns: [[
                                    {
                                        field: 'content',
                                        title: '提现记录',
                                        width: 100,
                                    }
                                ]],
                                onDblClickRow: function () {

                                },
                                onLoadSuccess: function () { //加载完成后,设置选中第一项
                                },
                                onLoadError: function (data) {
                                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                                }
                            });
                        }
                    }
                }
            });

            $('#mine-tab1').datagrid({
                method: 'get',
                fit: false,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: false,
                pageSize: 10,
                url: WorkMVC.URLs.list.url + '?status=1',
                toolbar: [],
                loadFilter: function (src) {
                    if (!src.code) {
                        return src.data;
                    }
                    return $.messager.alert('失败', src.msg, 'error');
                },
                columns: [[
                    {
                        field: 'id',
                        title: '工单ID',
                        hidden: true,
                        width: 100,

                    }, {
                        field: 'userId',
                        title: '用户ID',
                        hidden: true,
                        width: 100,

                    }, {
                        field: 'userName',
                        title: '用户名',
                        hidden: true,
                        width: 100,

                    }, {
                        field: 'MenuDesc',
                        title: '菜单',
                        hidden: true,
                        width: 100,

                    }, {
                        field: 'content',
                        title: '已受理数据',
                        width: 100,
                        // formatter: function (value, row, index) {
                        //     alert(value);
                        //     return "<div style='padding: 10px;'><div style='float: left'>用户名</div><div style='float: right'>3123123123</div><div class='clear'></div><div style='padding-top: 10px;'><div style='float: left;'>提现问题-提现BTC未到账</div><div style='float: right;color: #00bbee;'>待确认</div><div class='clear'></div></div><div style='padding-top: 10px;'>Lorem ipsum dolor sit amet, consectetur adipiscing elit.</div></div>"
                        // }

                    }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return WorkMVC.Controller.tab1showDetail();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                },
                onLoadSuccess: function () { //加载完成后,设置选中第一项
                    $('.pagination-page-list').hide();
                    $('.pagination-info').hide();
                    $('.pagination-num').width(20);

                }
            });


            $('#ds-dlg').dialog({
                closed: true,
                modal: false,
                width: 650,
                height: 200,
                iconCls: '',
                buttons: [
                    {
                        text: '关闭',
                        iconCls: 'icon-no',
                        handler: function () {
                            $("#ds-dlg").dialog('close');
                        }
                    }, {
                        text: '上传',
                        iconCls: 'icon-ok',
                        handler: WorkMVC.Controller.saveupload
                    }]
            });

            $.ajax({
                url: WorkMVC.URLs.user.url,
                type: 'get',
                data: {},
                success: function (data) {
                    $("#username").text(data.name + ":" + data.account);
                    $('#work-combox').combobox('setValue', data.dutyStatus);
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
        bindEvent: function () {
            $('#btn-upload').bind('click', WorkMVC.Controller.upload);
            $('#btn-send-transmit').bind('click', WorkMVC.Controller.transmit);
            $('#btn-send').bind('click', WorkMVC.Controller.btnSend);
            $('#btn-Solve').bind('click', WorkMVC.Controller.btnSolve);
            $('#btn-wait').bind('click', WorkMVC.Controller.btnWait);
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#WorkMenu-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return WorkMVC.Controller.edit();
            }
        },
        getWorkDetail: function (v) {
            var workOrderId = v;
            //获取工单详情
            $.ajax({
                url: WorkMVC.URLs.workDetails.url,
                type: 'get',
                data: {
                    workOrderId: workOrderId
                },
                success: function (data) {

                    var type = 'atalk';
                    var v = "";
                    for (var i = 0; i < data.data.length; i++) {
                        if (data.data[i].type == 0) {
                            type = 'atalk';
                        } else {
                            type = 'btalk';
                        }
                        v += "<div class=" + type + ">" +
                            "<span>" + data.data[i].reply + "</span>" +
                            "<div style='color: #4f4f4f;font-size: 12px;text-align: center;padding-top: 5px;'>" + moment(new Date(data.data[i].createdDate)).format("YYYY-MM-DD HH:mm:ss") + "</div>" +
                            "</div>" +
                            "</div>";
                    }
                    $("#words").html(v);


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
        saveTransmit: function () {
            $.messager.confirm('请再次确认信息', '转发当前工单吗？', function (r) {
                if (r) {
                    var workOrderId = $("#workOrderId").val();
                    var userList = $("#userList").combobox('getValue');
                    var url = WorkMVC.URLs.transmit.url;
                    $.ajax({
                        url: url,
                        type: 'POST',
                        data: {id: workOrderId, userId: userList},
                        success: function (data) {
                            $("#reset-work-dlg").dialog('close');
                        }
                    });
                }
            });

        },

        getorgan: function (id) {
            $.get(WorkMVC.URLs.getAdminGroupUser.url + '?groupId=' + id, "", function (_data) {
                $("#userList").combobox({
                    data: _data.data,
                    valueField: "id",
                    textField: "name",
                    panelHeight: 300,
                    editable: true,
                    onSelect: function (record) {
                        $.tabIndex = $('.userList').index(this);
                        $(".userList").eq($.tabIndex).textbox("setValue", record.id)
                    },
                    //加载完成后,设置选中第一项
                    onLoadSuccess: function () {

                    }
                });

            });
        },

        saveupload: function () {

            var file = $("#file").filebox('getValue');
            var formData = new FormData($("#ds-form")[0]);
            $("#ms").html('提示：上传中...');
            $.ajax({
                url: WorkMVC.URLs.upload.url,
                type: 'POST',
                data: formData,
                async: true,
                cache: false,
                contentType: false,
                processData: false,
                success: function (d) {
                    var data = d.data;
                    WorkMVC.Controller.saveAttchFile(data);
                    $("#ds-dlg").dialog('close');
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

        saveAttchFile: function (d) {
            $("#ms").html("");
            var workOrderId = $("#workOrderId").val();
            var originName = d.fileName;
            $.ajax({
                url: WorkMVC.URLs.save.url,
                type: 'POST',
                data: {
                    workOrderId: workOrderId,
                    originalName: originName,
                    path: "",
                    desc: ""
                },
                success: function (data) {
                    if (data.msg == "success") {
                        $.messager.alert('success', "success", 'success');
                    } else {
                        $.messager.alert('提示', data.msg, 'info');
                    }
                }
            });
        },
        btnSolve: function () {
            var workOrderId = $("#workOrderId").val();
            $.ajax({
                url: WorkMVC.URLs.Solve.url,
                type: 'POST',
                data: {workOrderId: workOrderId},
                success: function (data) {
                    if (data.msg == "success") {
                        $.messager.alert('success', "success", 'success');
                    } else {
                        $.messager.alert('提示', data.msg, 'info');
                    }
                }
            });

        },
        btnWait: function () {
            var workOrderId = $("#workOrderId").val();
            $.ajax({
                url: WorkMVC.URLs.wait.url,
                type: 'POST',
                data: {workOrderId: workOrderId},
                success: function (data) {
                    if (data.msg == "success") {
                        $.messager.alert('success', "success", 'success');
                    } else {
                        $.messager.alert('提示', data.msg, 'info');
                    }
                }
            });

        },
        btnSend: function () {
            var remarks = $("#remarks").textbox('getValue');
            var workOrderId = $("#workOrderId").val();
            if (remarks === "") {
                $.messager.alert('提示', "请输入回复内容", 'info');
            }
            $.ajax({
                url: WorkMVC.URLs.add.url,
                type: 'POST',
                data: {workOrderId: workOrderId, remarks: remarks},
                success: function (data) {
                    if (data.msg == "success") {
                        //成功刷新详情页面
                        WorkMVC.Controller.getWorkDetail(workOrderId);
                        //清空聊天框
                        $("#remarks").textbox('setValue', "");
                    }
                }
            });
        },
        transmit: function () {
            $('#reset-work-dlg').dialog('open').dialog('center');
        },

        upload: function () {
            var options = WorkMVC.Util.getOption();
            options.title = '附件上传';
            EasyUIUtils.openAddDlg(options);
        },
        find: function () {
            var url = WorkMVC.Util.getSearchUrl();
            $("#mine-tab1").datagrid({
                pageNumber: 1,
                pageSize: 50,
                url: url
            });
        },
        tab1showDetail: function () {
            WorkMVC.Util.isRowSelected(function (row) {
                $("#workOrderId").val(row.id);
                $("#userId").val(row.userId);
                var workOrderId = row.id;
                var userId = row.userId;

                //名称
                $('#c_name').text(row.userName);
                $('#c_desc').text(row.menuDesc);

                //获取详情
                WorkMVC.Controller.getWorkDetail(workOrderId);


                //获得充值记录
                $('#RedrawMoney-record-datagrid').datagrid({
                    method: 'get',
                    fit: false,
                    autoRowWidth: true,
                    fitColumns: true,
                    singleSelect: true,
                    pagination: false,
                    rownumbers: false,
                    sortOrder: 'desc',
                    pageSize: 50,
                    pageNumber: 1,
                    url: WorkMVC.URLs.assetRecord.url + '?userId=' + userId + '&transferType=withdraw',
                    loadFilter: function (src) {
                        if (!src.code) {
                            return src.data;
                        }
                        return $.messager.alert('失败', src.msg, 'error');
                    },
                    columns: [[
                        {
                            field: 'content',
                            title: '充值记录',
                            width: 100,
                        }
                    ]],
                    onDblClickRow: function () {

                    },
                    onLoadSuccess: function () { //加载完成后,设置选中第一项

                    },
                    onLoadError: function (data) {
                        return $.messager.alert('失败', data.responseJSON.msg, 'error');
                    }
                });

                //获得提现记录
                $('#extract-record-datagrid').datagrid({
                    method: 'get',
                    fit: false,
                    autoRowWidth: true,
                    fitColumns: true,
                    singleSelect: true,
                    pagination: false,
                    rownumbers: false,
                    sortOrder: 'desc',
                    pageSize: 50,
                    pageNumber: 1,
                    url: WorkMVC.URLs.assetRecord.url + '?userId=' + userId + '&transferType=deposit',
                    loadFilter: function (src) {
                        if (!src.code) {
                            return src.data;
                        }
                        return $.messager.alert('失败', src.msg, 'error');
                    },
                    columns: [[
                        {
                            field: 'content',
                            title: '提现记录',
                            width: 100,
                        }
                    ]],
                    onDblClickRow: function () {

                    },
                    onLoadSuccess: function () { //加载完成后,设置选中第一项
                    },
                    onLoadError: function (data) {
                        return $.messager.alert('失败', data.responseJSON.msg, 'error');
                    }
                });

                var userUrl = WorkMVC.URLs.workOrderUserInfo.url + userId;
                $.getJSON(userUrl, function (d) {
                    var data = d.data;
                    $("#user_id").text(data.id);
                    $("#user_name").text(data.nickname);
                    $("#user_realname").text(data.realName);
                    $("#user_email").text(data.email);
                    $("#user_phone").text(data.mobile);
                });


            });
        },
        updateUser: function (data) {
            var type = $("#work-combox").combobox('getValue');
            $.ajax({
                url: WorkMVC.URLs.updateType.url,
                type: 'get',
                data: {type: type},
                success: function (data) {

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
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#WorkMVC-dlg',
                formId: '#WorkMVC-form',
                actId: '#modal-action',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                gridId: null,
            };
        },

        getOption: function () {
            return {
                dlgId: '#ds-dlg',
                formId: '#ds-form',
                actId: '#modal-action',
                rowId: '#dsId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                gridId: null,
            };
        },
        isRowSelected: function (func) {
            var row = $('#mine-tab1').datagrid('getSelected');
            if (row) {
                func(row);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        getSearchUrl: function () {
            var f = $("#filedName").combobox('getValue');
            var keyword = $("#keyword").textbox('getValue');
            var tabIndex = $("#tabIndex").val();
            var status = 1;
            if (tabIndex == 0) {
                status = 1;
            }
            if (tabIndex == 1) {
                status = 2;
            }
            if (tabIndex == 2) {
                status = 5;
            }
            if (tabIndex == 3) {
                status = 3;
            }
            if (tabIndex == 4) {
                status = 4;
            }
            var filedName;
            if (f === 0) {
                fieldName = 'id';
            } else if (f === 1) {
                fieldName = 'userName';
            } else if (f === 2) {
                fieldName = 'userId'
            }
            return url = WorkMVC.URLs.list.url + '?filedName=' + fieldName + '&keyword=' + keyword + '&status=' + status;

        }
    }
};