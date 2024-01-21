$(function () {
    TradeRank.init();
});

var TradeRank = {
    init: function () {
        TradeRankMVC.View.initControl();
        TradeRankMVC.View.bindEvent();
    }
};

var TradeRankCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/activity/hundred2/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/'
};

var TradeRankMVC = {
    URLs: {
        list: {
            url: TradeRankCommon.baseUrl + 'tradeRank/list',
            method: 'GET'
        },
        add: {
            url: TradeRankCommon.baseUrl + 'tradeRank/add',
            method: 'POST'
        },
        edit: {
            url: TradeRankCommon.baseUrl + 'tradeRank/edit',
            method: 'POST'
        },
        remove: {
            url: TradeRankCommon.baseUrl + 'tradeRank/remove',
            method: 'POST'
        },
        export: {
            url: TradeRankCommon.baseUrl + 'tradeRank/export',
            method: 'GET'
        },
        getBrokerList: {
            url: TradeRankCommon.brokerBaseUrl + 'list'
        }
    },
    Model: {},
    View: {
        initControl: function () {

            $("#brokerId").combobox({
                url: TradeRankMVC.URLs.getBrokerList.url,
                method: 'get',
                valueField: "id",
                textField: "brokerName",
                editable: true,
                value: '',
                prompt: '选择券商',
                panelHeight: 300
            });

            $('#trade-rank-dlg').dialog({
                closed: true,
                modal: false,
                width: 600,
                height: 400,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#trade-rank-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: function () {
                        TradeRankMVC.Controller.save();
                    }
                }]
            });

            $('#trade-rank-datagrid').datagrid({
                method: 'GET',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                sortOrder: 'desc',
                pageSize: 50,
                pageNumber: 1,
                url: TradeRankMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    text: '刷新',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#trade-rank-datagrid')
                    }
                }, '-', {
                    iconCls: 'icon-add',
                    text: '新增',
                    handler: function () {
                        TradeRankMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit',
                    text: '修改',
                    handler: function () {
                        TradeRankMVC.Controller.modify();
                    }
                }, '-', {
                    iconCls: 'icon-remove',
                    text: '删除',
                    handler: function () {
                        TradeRankMVC.Controller.remove();
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
                        field: 'userId',
                        title: '用户ID',
                        width: 100
                    }, {
                        field: 'username',
                        title: '用户名',
                        width: 100
                    },
                    {
                        field: 'amount',
                        title: '折合USDT交易额',
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
                    return TradeRankMVC.Controller.modify();
                },
                onLoadSuccess: function () { //加载完成后,设置选中第一项

                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });
        },
        bindEvent: function () {
            $('#trade-rank-btn-export').on('click', function () {
                var queryParam = TradeRankMVC.Util.getQueryParams();
                window.location.href = TradeRankMVC.URLs.export.url + "?username=" + queryParam.username;
            });
            $('#trade-rank-btn-search').bind('click', function () {
                var queryParam = TradeRankMVC.Util.getQueryParams();
                $('#trade-rank-datagrid').datagrid('load', queryParam);
            });
        }
    },
    Controller: {
        add: function () {
            $('#action').val('save');
            var options = TradeRankMVC.Util.getOptions();
            options.title = '新增排行榜纪录';
            EasyUIUtils.openAddDlg(options);
        },
        save: function () {
            var options = {
                gridId: null,
                gridUrl: TradeRankMVC.URLs.list.url,
                dlgId: "#trade-rank-dlg",
                formId: "#trade-rank-form",
                method: 'POST',
                url: null,
                callback: function () {
                }
            };

            var action = $('#action').val();
            if (action === 'save') {
                options.url = TradeRankMVC.URLs.add.url;
            } else if (action === 'update') {
                options.url = TradeRankMVC.URLs.edit.url;
            }
            options.gridId = '#trade-rank-datagrid';
            return EasyUIUtils.save(options);
        },
        modify: function () {
            var row = $('#trade-rank-datagrid').datagrid('getSelected');
            if (row) {
                $('#action').val('update');
                var options = TradeRankMVC.Util.getOptions();
                options.iconCls = 'icon-edit';
                options.data = row;
                options.title = '修改[' + options.data.userId + ']';
                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#trade-rank-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: TradeRankMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#trade-rank-datagrid',
                    gridUrl: TradeRankMVC.URLs.list.url,
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
                dlgId: '#trade-rank-dlg',
                formId: '#trade-rank-form',
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
            var username = $("#trade-rank-username").textbox("getValue");

            var params = {
                username: username
            };
            return params;
        }
    }
};