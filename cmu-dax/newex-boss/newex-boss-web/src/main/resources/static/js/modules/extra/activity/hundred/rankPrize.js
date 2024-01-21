$(function () {
    RankPrize.init();
});

var RankPrize = {
    init: function () {
        RankPrizeMVC.View.initControl();
        RankPrizeMVC.View.bindEvent();
    }
};

var RankPrizeCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/activity/hundred2/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/'
};

var RankPrizeMVC = {
    URLs: {
        list: {
            url: RankPrizeCommon.baseUrl + 'rankPrize/list',
            method: 'GET'
        },
        add: {
            url: RankPrizeCommon.baseUrl + 'rankPrize/add',
            method: 'POST'
        },
        edit: {
            url: RankPrizeCommon.baseUrl + 'rankPrize/edit',
            method: 'POST'
        },
        remove: {
            url: RankPrizeCommon.baseUrl + 'rankPrize/remove',
            method: 'POST'
        },
        getBrokerList: {
            url: RankPrizeCommon.brokerBaseUrl + 'list'
        }
    },
    Model: {},
    View: {
        initControl: function () {

            $("#brokerId").combobox({
                url: RankPrizeMVC.URLs.getBrokerList.url,
                method: 'get',
                valueField: "id",
                textField: "brokerName",
                editable: true,
                value: '',
                prompt: '选择券商',
                panelHeight: 300
            });

            $('#rank-prize-dlg').dialog({
                closed: true,
                modal: false,
                width: 600,
                height: 400,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#rank-prize-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: function () {
                        RankPrizeMVC.Controller.save();
                    }
                }]
            });

            $('#rank-prize-datagrid').datagrid({
                method: 'GET',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                sortOrder: 'desc',
                pageSize: 50,
                pageNumber: 1,
                url: RankPrizeMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    text: '刷新',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#rank-prize-datagrid')
                    }
                }, '-', {
                    iconCls: 'icon-add',
                    text: '新增',
                    handler: function () {
                        RankPrizeMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit',
                    text: '修改',
                    handler: function () {
                        RankPrizeMVC.Controller.modify();
                    }
                }, '-', {
                    iconCls: 'icon-remove',
                    text: '删除',
                    handler: function () {
                        RankPrizeMVC.Controller.remove();
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
                        field: 'term',
                        title: '期数',
                        width: 100,
                        sortable: true
                    }, {
                        field: 'brokerId',
                        title: '券商',
                        width: 100,
                        sortable: true
                    }, {
                        field: 'fromIndex',
                        title: '排名最小值',
                        width: 100
                    }, {
                        field: 'toIndex',
                        title: '排名最大值',
                        width: 100
                    },
                    {
                        field: 'description',
                        title: '奖品描述',
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
                    return RankPrizeMVC.Controller.modify();
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
            var options = RankPrizeMVC.Util.getOptions();
            options.title = '新增排行榜奖品';
            EasyUIUtils.openAddDlg(options);
        },
        save: function () {
            var options = {
                gridId: null,
                gridUrl: RankPrizeMVC.URLs.list.url,
                dlgId: "#rank-prize-dlg",
                formId: "#rank-prize-form",
                method: 'POST',
                url: null,
                callback: function () {
                }
            };

            var action = $('#action').val();
            if (action === 'save') {
                options.url = RankPrizeMVC.URLs.add.url;
            } else if (action === 'update') {
                options.url = RankPrizeMVC.URLs.edit.url;
            }
            options.gridId = '#rank-prize-datagrid';
            return EasyUIUtils.save(options);
        },
        modify: function () {
            var row = $('#rank-prize-datagrid').datagrid('getSelected');
            if (row) {
                $('#action').val('update');
                var options = RankPrizeMVC.Util.getOptions();
                options.iconCls = 'icon-edit';
                options.data = row;
                options.title = '修改[' + options.data.id + ']';
                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#rank-prize-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: RankPrizeMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#rank-prize-datagrid',
                    gridUrl: RankPrizeMVC.URLs.list.url,
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
                dlgId: '#rank-prize-dlg',
                formId: '#rank-prize-form',
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