$(function () {
    RushPrize.init();
});

var RushPrize = {
    init: function () {
        RushPrizeMVC.View.initControl();
        RushPrizeMVC.View.bindEvent();
    }
};

var RushPrizeCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/activity/hundred3/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
};

var RushPrizeMVC = {
    URLs: {
        list: {
            url: RushPrizeCommon.baseUrl + 'rushPrize/list',
            method: 'GET'
        },
        add: {
            url: RushPrizeCommon.baseUrl + 'rushPrize/add',
            method: 'POST'
        },
        edit: {
            url: RushPrizeCommon.baseUrl + 'rushPrize/edit',
            method: 'POST'
        },
        remove: {
            url: RushPrizeCommon.baseUrl + 'rushPrize/remove',
            method: 'POST'
        },
        export: {
            url: RushPrizeCommon.baseUrl + 'rushPrize/export',
            method: 'GET'
        }
    },
    Model: {},
    View: {
        initControl: function () {

            $('#rush-prize-dlg').dialog({
                closed: true,
                modal: false,
                width: 600,
                height: 400,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#rush-prize-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: function () {
                        RushPrizeMVC.Controller.save();
                    }
                }]
            });

            $('#rush-prize-datagrid').datagrid({
                method: 'GET',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                sortOrder: 'desc',
                pageSize: 50,
                pageNumber: 1,
                url: RushPrizeMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    text: '刷新',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#rush-prize-datagrid')
                    }
                }, '-', {
                    iconCls: 'icon-add',
                    text: '新增',
                    handler: function () {
                        RushPrizeMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit',
                    text: '修改',
                    handler: function () {
                        RushPrizeMVC.Controller.modify();
                    }
                }, '-', {
                    iconCls: 'icon-remove',
                    text: '删除',
                    handler: function () {
                        RushPrizeMVC.Controller.remove();
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
                        field: 'periodNo',
                        title: '期数',
                        width: 100
                    }, {
                        field: 'minHoldAmount',
                        title: '持仓CT的最小量',
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
                        field: 'numbers',
                        title: '份数',
                        width: 100
                    },
                    {
                        field: 'displayNumbers',
                        title: '显示的份数',
                        width: 100
                    },
                    {
                        field: 'rushNumbers',
                        title: '已抢购的份数',
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
                    return RushPrizeMVC.Controller.modify();
                },
                onLoadSuccess: function () { //加载完成后,设置选中第一项

                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });
        },
        bindEvent: function () {
            $('#rush-prize-btn-export').on('click', function () {
                var queryParam = RushPrizeMVC.Util.getQueryParams();
                window.location.href = RushPrizeMVC.URLs.export.url + "?username=" + queryParam.username;
            });
            $('#rush-prize-btn-search').bind('click', function () {
                var queryParam = RushPrizeMVC.Util.getQueryParams();
                $('#rush-prize-datagrid').datagrid('load', queryParam);
            });
        }
    },
    Controller: {
        add: function () {
            $('#action').val('save');
            var options = RushPrizeMVC.Util.getOptions();
            options.title = '新增抢购活动奖品信息';
            EasyUIUtils.openAddDlg(options);
        },
        save: function () {
            var options = {
                gridId: null,
                gridUrl: RushPrizeMVC.URLs.list.url,
                dlgId: "#rush-prize-dlg",
                formId: "#rush-prize-form",
                method: 'POST',
                url: null,
                callback: function () {
                }
            };

            var action = $('#action').val();
            if (action === 'save') {
                options.url = RushPrizeMVC.URLs.add.url;
            } else if (action === 'update') {
                options.url = RushPrizeMVC.URLs.edit.url;
            }
            options.gridId = '#rush-prize-datagrid';
            return EasyUIUtils.save(options);
        },
        modify: function () {
            var row = $('#rush-prize-datagrid').datagrid('getSelected');
            if (row) {
                $('#action').val('update');
                var options = RushPrizeMVC.Util.getOptions();
                options.iconCls = 'icon-edit';
                options.data = row;
                options.title = '修改[' + options.data.userId + ']';
                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#rush-prize-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: RushPrizeMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#rush-prize-datagrid',
                    gridUrl: RushPrizeMVC.URLs.list.url,
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
                dlgId: '#rush-prize-dlg',
                formId: '#rush-prize-form',
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
            var username = $("#rush-prize-username").textbox("getValue");

            var params = {
                username: username
            };
            return params;
        }
    }
};