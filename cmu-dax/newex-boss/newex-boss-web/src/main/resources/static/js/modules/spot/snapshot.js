$(function () {
    Snapshot.init();
});

var Snapshot = {
    init: function () {
        UserSnapshotMVC.View.initControl();
        UserSnapshotMVC.View.bindEvent();
        UserSnapshotMVC.View.bindValidate();

    },
    addTab: function (id, title, url, iconCls, closable) {
        HomeIndexMVC.Controller.addTab(id, title, url, iconCls, closable === undefined ? true : closable)
    }
};

var UserSnapshotCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/spot/snapshot/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    baseReportUrl: BossGlobal.ctxPath + '/views/modules/spot/snapshot-details',
    commonUrl: BossGlobal.ctxPath + '/v1/boss/common/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/'

};

var UserSnapshotMVC = {
    URLs: {
        add: {
            url: UserSnapshotCommon.baseUrl + 'add',
            method: 'POST'
        },
        list: {
            url: UserSnapshotCommon.baseUrl + 'list',
            method: 'GET'
        },
        subList: {
            url: UserSnapshotCommon.baseUrl + 'subList',
            method: 'GET'
        },
        Report: {
            url: UserSnapshotCommon.baseReportUrl + '',
            method: 'GET'
        },
        remove: {
            url: UserSnapshotCommon.baseUrl + 'remove',
            method: 'POST'
        },
        currency: {
            url: UserSnapshotCommon.commonUrl + 'spot/currency'
        },
        getBrokerList: {
            url: UserSnapshotCommon.brokerBaseUrl + 'list'
        }

    },
    Model: {

        marginOpen: {},
        roles: {}
    },
    View: {
        initControl: function () {
            $("#currency").tagbox({
                url: UserSnapshotMVC.URLs.currency.url,
                method: 'get',
                valueField: 'id',
                textField: 'symbol',
                limitToList: true,
                hasDownArrow: true,
                prompt: '选择币种'
            });

            $('#user-dlg').dialog({
                closed: true,
                modal: false,
                width: 600,
                height: 350,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#user-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: UserSnapshotMVC.Controller.save
                }]
            });

            $("#brokerId").combobox({
                url: UserSnapshotMVC.URLs.getBrokerList.url,
                method: 'get',
                valueField: "id",
                textField: "brokerName",
                editable: true,
                value: '',
                prompt: '选择券商',
                panelHeight: 300
            });

            $('#sub-dlg').dialog({
                closed: true,
                modal: false,
                width: 1000,
                height: 600,
                title: '子任务列表信息',
                iconCls: 'icon-info',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#sub-dlg").dialog('close');
                    }
                }]
            });

            $('#user-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: false,
                pageSize: 20,
                url: UserSnapshotMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        UserSnapshotMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#user-datagrid');
                    }
                }, '-', {
                    iconCls: 'icon-remove1',
                    handler: function () {
                        UserSnapshotMVC.Controller.remove();
                    }
                }, '-', {
                    text: '查询导出',
                    iconCls: 'icon-preview',
                    handler: UserSnapshotMVC.Controller.preview
                }],
                loadFilter: function (src) {
                    if (!src.code) {
                        return src.data;
                    }
                    return $.messager.alert('失败', src.msg, 'error');
                },
                columns: [[
                    {
                        field: 'id',
                        title: '任务ID',
                        width: 100,
                        sortable: true,

                    }, {
                        field: 'taskJobName',
                        title: '快照任务名称',
                        width: 150
                    }, {
                        field: 'taskType',
                        title: '任务类型',
                        width: 150,
                        formatter: function (val) {
                            if (val == 1) {
                                return "一次性任务"
                            }
                            else if (val == 2) {
                                return "周期性"
                            }
                        }
                    }, {
                        field: 'calender',
                        title: '任务周期',
                        width: 150,
                        formatter: function (val) {
                            if (val == 5) {
                                return "每日执行"
                            } else if (val == 2) {
                                return "每月执行"
                            }
                        }
                    }, {
                        field: 'period',
                        title: '任务周期',
                        width: 150
                    }, {
                        field: 'status',
                        title: '任务状态',
                        width: 150,
                        sortable: true,
                        formatter: function (value, row, index) {

                            if (value === 0) {
                                return "未处理";
                            }
                            if (value === 1) {
                                return "处理中";
                            }
                            if (value === 2) {
                                return "处理完成";
                            }
                            if (value === 3) {
                                return "超时";
                            }
                        }

                    }, {
                        field: 'startTime',
                        title: '任务开始时间',
                        width: 150

                    }, {
                        field: 'endTime',
                        title: '任务结束时间',
                        width: 150

                    }]],
                onDblClickRow: function (rowIndex, rowData) {
                    if (rowData.taskType == 2) {
                        UserSnapshotMVC.Controller.subInfo(rowData.id);
                    }
                },
                onLoadSuccess: function () { //加载完成后,设置选中第一项

                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });
            $('#sub-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: false,
                pageSize: 20,
                // url: UserSnapshotMVC.URLs.subList.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#sub-datagrid');
                    }
                }, '-', {
                    text: '查询导出',
                    iconCls: 'icon-preview',
                    handler: UserSnapshotMVC.Controller.subPreview
                }],
                loadFilter: function (src) {
                    if (!src.code) {
                        return src.data;
                    }
                    return $.messager.alert('失败', src.msg, 'error');
                },
                columns: [[
                    {
                        field: 'id',
                        title: '任务ID',
                        width: 100,
                        sortable: true

                    }, {
                        field: 'taskJobName',
                        title: '快照任务名称',
                        width: 150
                    }, {
                        field: 'status',
                        title: '任务状态',
                        width: 150,
                        sortable: true,
                        formatter: function (value, row, index) {

                            if (value === 0) {
                                return "未处理";
                            }
                            if (value === 1) {
                                return "处理中";
                            }
                            if (value === 2) {
                                return "处理完成";
                            }
                            if (value === 3) {
                                return "超时";
                            }
                        }

                    }, {
                        field: 'startTime',
                        title: '任务开始时间',
                        width: 150

                    }, {
                        field: 'endTime',
                        title: '任务结束时间',
                        width: 150

                    }]],
                onDblClickRow: function (rowIndex, rowData) {
                    UserSnapshotMVC.Controller.preview();
                },
                onLoadSuccess: function () { //加载完成后,设置选中第一项

                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });
        },
        bindEvent: function () {
            // $('#btn-search').bind('click', UserSnapshotMVC.Controller.find);
        },
        bindValidate: function () {
        }
    },
    Controller: {
        addTab: function (id, title, url, iconCls, closable) {
            if ($('#main-tabs').tabs('exists', title)) {
                $('#main-tabs').tabs('select', title);
            } else {
                var content = '<iframe scrolling="auto" frameborder="0"  src="' + url
                    + '" style="width:100%;height:99%;"></iframe>';
                $('#main-tabs').tabs('add', {
                    id: id,
                    title: title,
                    content: content,
                    closable: closable,
                    iconCls: iconCls
                });
            }
        },
        doOption: function (index, name) {
            $('#user-datagrid').datagrid('selectRow', index);

            if (name === "preview") {
                return UserSnapshotMVC.Controller.preview();
            }

        },
        add: function () {
            var options = UserSnapshotMVC.Util.getOptions();
            options.title = '新增快照任务';
            EasyUIUtils.openAddDlg(options);
            $('#calender').combobox('readonly', false);
            $('#period').combobox('readonly', false);
            $('#endTime').datetimebox('readonly', false);
            $('#brokerId').combobox('readonly', false);


        },
        remove: function () {
            var row = $('#user-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: UserSnapshotMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#user-datagrid',
                    gridUrl: UserSnapshotMVC.URLs.list.url,
                    callback: function (rows) {
                        EasyUIUtils.reloadDatagrid('#user-datagrid');
                    }
                };
                EasyUIUtils.remove(options);
            }
        },
        preview: function () {
            UserSnapshotMVC.Util.isRowSelected(function (row) {
                if (row.taskType == 1) {
                    var url = UserSnapshotMVC.URLs.Report.url + '?taskid=' + row.id;
                    parent.HomeIndex.addTab(row.id + 999, "任务详情" + row.id, url, "");
                } else {
                    $.messager.alert('info', '这是周期性任务，不能直接导出全部子任务数据');
                }
            });
        },
        subPreview: function () {
            UserSnapshotMVC.Util.isSubRowSelected(function (row) {
                var url = UserSnapshotMVC.URLs.Report.url + '?taskid=' + row.id;
                parent.HomeIndex.addTab(row.id + 999, "任务详情" + row.id, url, "");
            });
        },
        subInfo: function (parentId) {
            $("#sub-dlg").dialog('open');
            $('#sub-datagrid').datagrid({
                url: UserSnapshotMVC.URLs.subList.url + '?parentId=' + parentId,
                method: 'get'
            })
        },
        radioClick: function (val) {
            if (val == 1) {
                $('#calender').combobox('readonly', true);
                $('#period').combobox('readonly', true);
                $('#endTime').datetimebox('readonly', true);
            } else {
                $('#calender').combobox('readonly', false);
                $('#period').combobox('readonly', false);
                $('#endTime').datetimebox('readonly', false);
            }
        },
        save: function () {
            var action = $('#modal-action').val();


            var options = {
                gridId: null,
                gridUrl: UserSnapshotMVC.URLs.list.url,
                dlgId: "#user-dlg",
                formId: "#user-form",
                url: null,
                callback: function () {
                }
            };

            if (action === "edit") {
                var row = $('#user-datagrid').datagrid('getSelected');
                //取得ID传给后台
                options.url = UserSnapshotMVC.URLs.edit.url + '?id=' + row.id;
            }
            else {
                //添加操作
                options.url = UserSnapshotMVC.URLs.add.url;
                options.gridId = '#user-datagrid';
            }
            options.callback = function (result) {
                if (!result.code) {
                    EasyUIUtils.reloadDatagrid('#user-datagrid');
                }
            }
            //修改后刷新列表
            EasyUIUtils.save(options);

            return;

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
        isRowSelected: function (func) {
            var row = $('#user-datagrid').datagrid('getSelected');
            if (row) {
                func(row);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        isSubRowSelected: function (func) {
            var row = $('#sub-datagrid').datagrid('getSelected');
            if (row) {
                func(row);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        }
    }
};