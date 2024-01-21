$(function () {
    TradeBlock.init();
});

var TradeBlock = {
    init: function () {
        TradeBlockMVC.View.initControl();
        TradeBlockMVC.View.bindEvent();
    }
};

var TradeBlockCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/activity/hundred2/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
};

var TradeBlockMVC = {
    URLs: {
        list: {
            url: TradeBlockCommon.baseUrl + 'tradeBlock/list',
            method: 'GET'
        },
        add: {
            url: TradeBlockCommon.baseUrl + 'tradeBlock/add',
            method: 'POST'
        },
        edit: {
            url: TradeBlockCommon.baseUrl + 'tradeBlock/edit',
            method: 'POST'
        },
        remove: {
            url: TradeBlockCommon.baseUrl + 'tradeBlock/remove',
            method: 'POST'
        }
    },
    Model: {},
    View: {
        initControl: function () {

            $('#trade-block-dlg').dialog({
                closed: true,
                modal: false,
                width: 600,
                height: 400,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#trade-block-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: function () {
                        TradeBlockMVC.Controller.save();
                    }
                }]
            });

            $('#trade-block-datagrid').datagrid({
                method: 'GET',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                sortOrder: 'desc',
                pageSize: 50,
                pageNumber: 1,
                url: TradeBlockMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    text: '刷新',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#trade-block-datagrid')
                    }
                }, '-', {
                    iconCls: 'icon-add',
                    text: '新增',
                    handler: function () {
                        TradeBlockMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit',
                    text: '修改',
                    handler: function () {
                        TradeBlockMVC.Controller.modify();
                    }
                }, '-', {
                    iconCls: 'icon-remove',
                    text: '删除',
                    handler: function () {
                        TradeBlockMVC.Controller.remove();
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
                        field: 'email',
                        title: '邮箱',
                        width: 100
                    },
                    {
                        field: 'mobile',
                        title: '手机号',
                        width: 100
                    },{
                        field: 'createdDate',
                        title: '创建时间',
                        width: 100,
                        formatter: function (value) {
                            return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                        }
                    },{
                        field: 'updatedDate',
                        title: '更新时间',
                        width: 100,
                        formatter: function (value) {
                            return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                        }
                    }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return TradeBlockMVC.Controller.modify();
                },
                onLoadSuccess: function () { //加载完成后,设置选中第一项

                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });
        },
        bindEvent: function () {
        }
    },
    Controller: {
        add: function () {
            $('#action').val('save');
            var options = TradeBlockMVC.Util.getOptions();
            options.title = '新增黑名单纪录';
            EasyUIUtils.openAddDlg(options);
        },
        save: function () {
            var options = {
                gridId: null,
                gridUrl: TradeBlockMVC.URLs.list.url,
                dlgId: "#trade-block-dlg",
                formId: "#trade-block-form",
                method: 'POST',
                url: null,
                callback: function () {
                }
            };

            var action = $('#action').val();
            if (action === 'save') {
                options.url = TradeBlockMVC.URLs.add.url;
            } else if (action === 'update') {
                options.url = TradeBlockMVC.URLs.edit.url;
            }
            options.gridId = '#trade-block-datagrid';
            return EasyUIUtils.save(options);
        },
        modify: function () {
            var row = $('#trade-block-datagrid').datagrid('getSelected');
            if (row) {
                $('#action').val('update');
                var options = TradeBlockMVC.Util.getOptions();
                options.iconCls = 'icon-edit';
                options.data = row;
                options.title = '修改[' + options.data.userId + ']';
                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#trade-block-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: TradeBlockMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#trade-block-datagrid',
                    gridUrl: TradeBlockMVC.URLs.list.url,
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
                dlgId: '#trade-block-dlg',
                formId: '#trade-block-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                gridId: null
            };
        }
    }
};