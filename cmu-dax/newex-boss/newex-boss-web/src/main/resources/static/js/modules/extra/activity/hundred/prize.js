$(function () {
    Prize.init();
});

var Prize = {
    init: function () {
        PrizeMVC.View.initControl();
        PrizeMVC.View.bindEvent();
    }
};

var PrizeCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/activity/hundred/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
};

var PrizeMVC = {
    URLs: {
        list: {
            url: PrizeCommon.baseUrl + 'prize/list',
            method: 'GET'
        },
        add: {
            url: PrizeCommon.baseUrl + 'prize/add',
            method: 'POST'
        },
        edit: {
            url: PrizeCommon.baseUrl + 'prize/edit',
            method: 'POST'
        },
        remove: {
            url: PrizeCommon.baseUrl + 'prize/remove',
            method: 'POST'
        }
    },
    Model: {},
    View: {
        initControl: function () {

            $('#prize-dlg').dialog({
                closed: true,
                modal: false,
                width: 600,
                height: 400,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#prize-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: function () {
                        PrizeMVC.Controller.save();
                    }
                }]
            });

            $('#prize-datagrid').datagrid({
                method: 'GET',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                sortOrder: 'desc',
                pageSize: 50,
                pageNumber: 1,
                url: PrizeMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    text: '刷新',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#prize-datagrid')
                    }
                }, '-', {
                    iconCls: 'icon-add',
                    text: '新增',
                    handler: function () {
                        PrizeMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit',
                    text: '修改',
                    handler: function () {
                        PrizeMVC.Controller.modify();
                    }
                }, '-', {
                    iconCls: 'icon-remove',
                    text: '删除',
                    handler: function () {
                        PrizeMVC.Controller.remove();
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
                        field: 'level',
                        title: '奖品等级',
                        width: 100
                    }, {
                        field: 'desc',
                        title: '奖品描述',
                        width: 100
                    },
                    {
                        field: 'probability',
                        title: '中奖率',
                        width: 100,
                        formatter: function (val) {
                            return val + '%';
                        }
                    },
                    {
                        field: 'dayLimit',
                        title: '每日数量上限',
                        width: 100
                    },
                    {
                        field: 'allLimit',
                        title: '总数量上限',
                        width: 100
                    },
                    {
                        field: 'grant',
                        title: '当天发放数量',
                        width: 100
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
                    return PrizeMVC.Controller.modify();
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
            var options = PrizeMVC.Util.getOptions();
            options.title = '新增奖品纪录';
            EasyUIUtils.openAddDlg(options);
        },
        save: function () {
            var options = {
                gridId: null,
                gridUrl: PrizeMVC.URLs.list.url,
                dlgId: "#prize-dlg",
                formId: "#prize-form",
                method: 'POST',
                url: null,
                callback: function () {
                }
            };

            var action = $('#action').val();
            if (action === 'save') {
                options.url = PrizeMVC.URLs.add.url;
            } else if (action === 'update') {
                options.url = PrizeMVC.URLs.edit.url;
            }
            options.gridId = '#prize-datagrid';
            return EasyUIUtils.save(options);
        },
        modify: function () {
            var row = $('#prize-datagrid').datagrid('getSelected');
            if (row) {
                $('#action').val('update');
                var options = PrizeMVC.Util.getOptions();
                options.iconCls = 'icon-edit';
                options.data = row;
                options.title = '修改[' + options.data.desc + ']';
                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#prize-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: PrizeMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#prize-datagrid',
                    gridUrl: PrizeMVC.URLs.list.url,
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
                dlgId: '#prize-dlg',
                formId: '#prize-form',
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