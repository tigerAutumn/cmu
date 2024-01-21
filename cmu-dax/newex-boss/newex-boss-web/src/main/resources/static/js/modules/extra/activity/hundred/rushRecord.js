$(function () {
    RushRecord.init();
});

var RushRecord = {
    init: function () {
        RushRecordMVC.View.initControl();
        RushRecordMVC.View.bindEvent();
    }
};

var RushRecordCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/activity/hundred3/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
};

var RushRecordMVC = {
    URLs: {
        list: {
            url: RushRecordCommon.baseUrl + 'rushRecord/list',
            method: 'GET'
        },
        add: {
            url: RushRecordCommon.baseUrl + 'rushRecord/add',
            method: 'POST'
        },
        edit: {
            url: RushRecordCommon.baseUrl + 'rushRecord/edit',
            method: 'POST'
        },
        remove: {
            url: RushRecordCommon.baseUrl + 'rushRecord/remove',
            method: 'POST'
        },
        export: {
            url: RushRecordCommon.baseUrl + 'rushRecord/export',
            method: 'GET'
        }
    },
    Model: {},
    View: {
        initControl: function () {

            $('#rush-record-dlg').dialog({
                closed: true,
                modal: false,
                width: 600,
                height: 400,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#rush-record-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: function () {
                        RushRecordMVC.Controller.save();
                    }
                }]
            });

            $('#rush-record-datagrid').datagrid({
                method: 'GET',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                sortOrder: 'desc',
                pageSize: 50,
                pageNumber: 1,
                url: RushRecordMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    text: '刷新',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#rush-record-datagrid')
                    }
                }, '-', {
                    iconCls: 'icon-add',
                    text: '新增',
                    handler: function () {
                        RushRecordMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit',
                    text: '修改',
                    handler: function () {
                        RushRecordMVC.Controller.modify();
                    }
                }, '-', {
                    iconCls: 'icon-remove',
                    text: '删除',
                    handler: function () {
                        RushRecordMVC.Controller.remove();
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
                        field: 'periodNo',
                        title: '期数',
                        width: 100
                    }, {
                        field: 'rushPrizeId',
                        title: '奖品ID',
                        width: 100
                    },
                    {
                        field: 'currencyId',
                        title: '币种ID',
                        width: 100
                    },
                    {
                        field: 'currencyCode',
                        title: '币种简称',
                        width: 100
                    },
                    {
                        field: 'currencyAmount',
                        title: '币种数量',
                        width: 100
                    },
                    {
                        field: 'status',
                        title: '状态',
                        width: 100,
                        formatter: function (value) {
                            if (value === 1) {
                                return "处理中";
                            } else if (value === 2) {
                                return "成功";
                            } else if (value === 3) {
                                return "失败";
                            }
                        }
                    },
                    {
                        field: 'createdDate',
                        title: '创建时间',
                        width: 100,
                        formatter: function (value) {
                            return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                        }
                    },
                    {
                        field: 'updatedDate',
                        title: '更新时间',
                        width: 100,
                        formatter: function (value) {
                            return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                        }
                    }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return RushRecordMVC.Controller.modify();
                },
                onLoadSuccess: function () { //加载完成后,设置选中第一项

                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });
        },
        bindEvent: function () {
            $('#rush-record-btn-export').on('click', function () {
                var queryParam = RushRecordMVC.Util.getQueryParams();
                window.location.href = RushRecordMVC.URLs.export.url
                    + "?periodNo=" + queryParam.periodNo
                    + "&startTime=" + queryParam.startTime
                    + "&endTime=" + queryParam.endTime
                    + "&status=" + queryParam.status;
            });
            $('#rush-record-btn-search').bind('click', function () {
                var queryParam = RushRecordMVC.Util.getQueryParams();
                $('#rush-record-datagrid').datagrid('load', queryParam);
            });
        }
    },
    Controller: {
        add: function () {
            $('#action').val('save');
            var options = RushRecordMVC.Util.getOptions();
            options.title = '新增抢购活动纪录';
            EasyUIUtils.openAddDlg(options);
        },
        save: function () {
            var options = {
                gridId: null,
                gridUrl: RushRecordMVC.URLs.list.url,
                dlgId: "#rush-record-dlg",
                formId: "#rush-record-form",
                method: 'POST',
                url: null,
                callback: function () {
                }
            };

            var action = $('#action').val();
            if (action === 'save') {
                options.url = RushRecordMVC.URLs.add.url;
            } else if (action === 'update') {
                options.url = RushRecordMVC.URLs.edit.url;
            }
            options.gridId = '#rush-record-datagrid';
            return EasyUIUtils.save(options);
        },
        modify: function () {
            var row = $('#rush-record-datagrid').datagrid('getSelected');
            if (row) {
                $('#action').val('update');
                var options = RushRecordMVC.Util.getOptions();
                options.iconCls = 'icon-edit';
                options.data = row;
                options.title = '修改[' + options.data.id + ']';
                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#rush-record-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: RushRecordMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#rush-record-datagrid',
                    gridUrl: RushRecordMVC.URLs.list.url,
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
                dlgId: '#rush-record-dlg',
                formId: '#rush-record-form',
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
            var startTime = $("#rush-record-startTime").datetimebox("getValue");
            var endTime = $("#rush-record-endTime").datetimebox("getValue");
            var periodNo = $("#rush-record-periodNo").textbox("getValue");
            var status = $("#rush-record-status").combobox("getValue");

            var params = {
                periodNo: periodNo,
                startTime: startTime,
                endTime: endTime,
                status: status
            };
            return params;
        }
    }
};