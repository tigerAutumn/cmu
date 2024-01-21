$(function () {
    WinRecord.init();
});

var WinRecord = {
    init: function () {
        WinRecordMVC.View.initControl();
        WinRecordMVC.View.bindEvent();
    }
};

var WinRecordCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/activity/hundred/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/'
};

var WinRecordMVC = {
    URLs: {
        list: {
            url: WinRecordCommon.baseUrl + 'record/winning/list',
            method: 'GET'
        },
        add: {
            url: WinRecordCommon.baseUrl + 'record/winning/add',
            method: 'POST'
        },
        edit: {
            url: WinRecordCommon.baseUrl + 'record/winning/edit',
            method: 'POST'
        },
        remove: {
            url: WinRecordCommon.baseUrl + 'record/winning/remove',
            method: 'POST'
        },
        export: {
            url: WinRecordCommon.baseUrl + 'record/winning/export',
            method: 'GET'
        }
    },
    Model: {},
    View: {
        initControl: function () {

            $('#winRecord-dlg').dialog({
                closed: true,
                modal: false,
                width: 600,
                height: 400,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#winRecord-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: function () {
                        WinRecordMVC.Controller.save()
                    }
                }]
            });

            $('#winRecord-datagrid').datagrid({
                method: 'GET',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                sortOrder: 'desc',
                pageSize: 50,
                pageNumber: 1,
                url: WinRecordMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    text: '刷新',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#winRecord-datagrid')
                    }
                }, '-', {
                    iconCls: 'icon-add',
                    text: '添加',
                    handler: function () {
                        WinRecordMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit',
                    text: '修改',
                    handler: function () {
                        WinRecordMVC.Controller.modify();
                    }
                }, '-', {
                    iconCls: 'icon-remove',
                    text: '删除',
                    handler: function () {
                        WinRecordMVC.Controller.remove();
                    }
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
                        title: 'ID',
                        width: 100,
                        sortable: true
                    }, {
                        field: 'userId',
                        title: '用户ID',
                        width: 100
                    }, {
                        field: 'username',
                        title: '用户名',
                        width: 100
                    }, {
                        field: 'level',
                        title: '奖品等级',
                        width: 100
                    }, {
                        field: 'desc',
                        title: '奖品描述',
                        width: 100
                    }, {
                        field: 'createdDate',
                        title: '创建时间',
                        width: 100,
                        formatter: function (value) {
                            return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                        }
                    }, {
                        field: 'updatedDate',
                        title: '更新时间',
                        width: 100,
                        formatter: function (value) {
                            return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                        }
                    }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return WinRecordMVC.Controller.modify();
                },
                onLoadSuccess: function () { //加载完成后,设置选中第一项

                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });
        },
        bindEvent: function () {
            $('#winRecord-btn-export').on('click', function () {
                var queryParam = WinRecordMVC.Util.getQueryParams();
                window.location.href = WinRecordMVC.URLs.export.url
                    + "?startTime=" + queryParam.startTime
                    + "&endTime=" + queryParam.endTime
                    + "&username=" + queryParam.username
                    + "&level=" + queryParam.level;
            });
            $('#winRecord-btn-search').on('click', function () {
                var queryParam = WinRecordMVC.Util.getQueryParams();
                $('#winRecord-datagrid').datagrid('load', queryParam);
            });
        }
    },
    Controller: {
        add: function () {
            $('#action').val('save');
            var options = WinRecordMVC.Util.getOptions();
            options.title = '新增中奖纪录';
            EasyUIUtils.openAddDlg(options);
        },
        save: function () {
            var options = {
                gridId: null,
                gridUrl: WinRecordMVC.URLs.list.url,
                dlgId: "#winRecord-dlg",
                formId: "#winRecord-form",
                method: 'POST',
                url: null,
                callback: function () {
                }
            };

            var action = $('#action').val();
            if (action === 'save') {
                options.url = WinRecordMVC.URLs.add.url;
            } else if (action === 'update') {
                options.url = WinRecordMVC.URLs.edit.url;
            }
            options.gridId = '#winRecord-datagrid';
            return EasyUIUtils.save(options);
        },
        modify: function () {
            var row = $('#winRecord-datagrid').datagrid('getSelected');
            if (row) {
                $('#action').val('update');
                var options = WinRecordMVC.Util.getOptions();
                options.iconCls = 'icon-edit';
                options.data = row;
                options.title = '修改[' + options.data.desc + ']';
                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#winRecord-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: WinRecordMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#winRecord-datagrid',
                    gridUrl: WinRecordMVC.URLs.list.url,
                    callback: function (rows) {
                    }
                };
                EasyUIUtils.remove(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#winRecord-dlg',
                formId: '#winRecord-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                gridId: null
            };
        },
        getQueryParams: function () {
            var startTime = $("#winRecord-startTime").datetimebox("getValue");
            var endTime = $("#winRecord-endTime").datetimebox("getValue");
            var username = $("#winRecord-username").textbox("getValue");
            var level = $("#winRecord-level").textbox("getValue");

            var params = {
                startTime: startTime,
                endTime: endTime,
                username: username,
                level: level
            };

            return params;
        }
    }
};