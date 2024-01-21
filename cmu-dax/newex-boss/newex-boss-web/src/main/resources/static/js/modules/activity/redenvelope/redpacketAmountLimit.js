$(function () {
    RedPacketAmountLimit.init();
});

var RedPacketAmountLimit = {
    init: function () {
        RedPacketAmountLimitMVC.View.initControl();
        RedPacketAmountLimitMVC.View.bindEvent();
    }
};

var RedPacketAmountLimitCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/activity/red_packet/',
    commonUrl: BossGlobal.ctxPath + '/v1/boss/common/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/',
    uploadUrl: BossGlobal.ctxPath + '/v1/boss/upload/',
    spotCurrencyUrl: BossGlobal.ctxPath + '/v1/boss/common/'
};

var RedPacketAmountLimitMVC = {
    URLs: {
        list: {
            url: RedPacketAmountLimitCommon.baseUrl + 'amount_limit/list',
            method: 'GET'
        },
        add: {
            url: RedPacketAmountLimitCommon.baseUrl + 'amount_limit/save',
            method: 'POST'
        },
        edit: {
            url: RedPacketAmountLimitCommon.baseUrl + 'amount_limit/edit',
            method: 'POST'
        },
        remove: {
            url: RedPacketAmountLimitCommon.baseUrl + 'amount_limit/remove',
            method: 'POST'
        },
        getBrokerList: {
            url: RedPacketAmountLimitCommon.brokerBaseUrl + 'list',
            method: 'GET'
        },
        spotCurrencyList: {
            url: RedPacketAmountLimitCommon.spotCurrencyUrl + 'spot/currency',
            method: 'GET'
        }
    },
    Model: {
        allCurrency: {}
    },
    View: {
        initControl: function () {

            $.get(RedPacketAmountLimitMVC.URLs.getBrokerList.url, "", function (_data) {
                $("#brokerId").combobox({
                    data: _data,
                    valueField: "id",
                    textField: "brokerName",
                    panelHeight: 300,
                    editable: true,
                    required: true,
                    prompt: '选择券商'
                });
            });

            $.get(RedPacketAmountLimitMVC.URLs.spotCurrencyList.url, "", function (_data) {
                RedPacketAmountLimitMVC.Model.allCurrency = _data;
                $("#currencyId").combobox({
                    data: _data,
                    valueField: "id",
                    textField: "symbol",
                    panelHeight: 300,
                    editable: true,
                    required: true,
                    onSelect: function (record) {
                        $('#currencyCode').textbox('setValue', record.symbol);
                    }
                });
            });

            $('#red-packet-amount-limit-dlg').dialog({
                closed: true,
                modal: false,
                width: 700,
                height: 400,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#red-packet-amount-limit-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: function () {
                        RedPacketAmountLimitMVC.Controller.save();
                    }
                }]
            });

            $('#red-packet-amount-limit-datagrid').datagrid({
                method: 'GET',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: RedPacketAmountLimitMVC.URLs.list.url,
                toolbar: [{
                    text: '新增',
                    iconCls: 'icon-add',
                    handler: function () {
                        RedPacketAmountLimitMVC.Controller.add();
                    }
                }, '-', {
                    text: '修改',
                    iconCls: 'icon-edit1',
                    handler: function () {
                        RedPacketAmountLimitMVC.Controller.modify();
                    }
                }, '-', {
                    text: '删除',
                    iconCls: 'icon-remove',
                    handler: function () {
                        RedPacketAmountLimitMVC.Controller.remove();
                    }
                }, '-', {
                    text: '全部数据',
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#red-packet-amount-limit-datagrid').datagrid('load', {});
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
                    },
                    {
                        field: 'currencyId',
                        title: '币种ID',
                        width: 100
                    },
                    {
                        field: 'currencyCode',
                        title: '币种名称',
                        width: 100
                    },
                    {
                        field: 'minAmount',
                        title: '币种最小限额',
                        width: 100
                    },
                    {
                        field: 'maxAmount',
                        title: '币种最大限额',
                        width: 100
                    },
                    {
                        field: 'maxNumber',
                        title: '红包个数限制',
                        width: 100
                    },
                    {
                        field: 'maxDigit',
                        title: '币种数量小数位数限制',
                        width: 150
                    },
                    {
                        field: 'brokerId',
                        title: '券商ID',
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
                    }
                ]],
                onDblClickRow: function (rowIndex, rowData) {
                    return RedPacketAmountLimitMVC.Controller.modify();
                },
                onLoadSuccess: function () {
                }
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', RedPacketAmountLimitMVC.Controller.query);
        }
    },
    Controller: {
        add: function () {
            $('#action').val("save");
            var options = RedPacketAmountLimitMVC.Util.getOptions();
            options.title = '新增红包币种限额纪录';
            EasyUIUtils.openAddDlg(options);
        },
        save: function () {
            var options = {
                gridId: null,
                gridUrl: RedPacketAmountLimitMVC.URLs.list.url,
                dlgId: "#red-packet-amount-limit-dlg",
                formId: "#red-packet-amount-limit-form",
                method: 'POST',
                url: null,
                callback: function () {
                }
            };

            var action = $('#action').val();
            if (action === "save") {
                options.url = RedPacketAmountLimitMVC.URLs.add.url;
            } else if (action === "update") {
                options.url = RedPacketAmountLimitMVC.URLs.edit.url;
            }
            options.gridId = '#red-packet-amount-limit-datagrid';
            return EasyUIUtils.save(options);
        },
        modify: function () {
            var row = $('#red-packet-amount-limit-datagrid').datagrid('getSelected');
            if (row) {
                $('#action').val("update");
                var options = RedPacketAmountLimitMVC.Util.getOptions();
                options.iconCls = 'icon-edit';
                options.data = row;
                options.data.expiredDate = moment(new Date(row.expiredDate)).format("YYYY-MM-DD HH:mm:ss")
                options.title = '修改[' + options.data.currencyCode + ']的红包币种限额纪录';
                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#red-packet-amount-limit-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: RedPacketAmountLimitMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#red-packet-amount-limit-datagrid',
                    gridUrl: RedPacketAmountLimitMVC.URLs.list.url,
                    callback: function (rows) {
                    }
                };
                EasyUIUtils.remove(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        query: function () {
            var params = RedPacketAmountLimitMVC.Util.getQueryParam();
            $('#red-packet-amount-limit-datagrid').datagrid('load', params);
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#red-packet-amount-limit-dlg',
                formId: '#red-packet-amount-limit-form',
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
        getQueryParam: function () {

            var currencyId = $("#query-red-packet-amount-limit-currencyId").textbox('getValue');

            var params = {
                currencyId: currencyId
            };

            return params;
        }
    }
};
