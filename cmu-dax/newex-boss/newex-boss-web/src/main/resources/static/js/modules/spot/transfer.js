$(function () {
    Transfer.init();
});

var Transfer = {
    init: function () {
        TransferMVC.View.initControl();
        TransferMVC.View.bindEvent();
        TransferMVC.View.bindValidate();
    }
};

var TransferCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/spot/transfer/',
    commonUrl: BossGlobal.ctxPath + '/v1/boss/common/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    brokerIdsUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/'
};

var TransferMVC = {
    URLs: {
        add: {
            url: TransferCommon.baseUrl + 'add',
            method: 'POST'
        },
        batchAdd: {
            url: TransferCommon.baseUrl + 'batch-add',
            method: 'POST'
        },
        list: {
            url: TransferCommon.baseUrl + 'list',
            method: 'GET'
        },
        spotCurrency: {
            url: TransferCommon.commonUrl + 'spot/currency',
            method: 'GET'
        },
        getBrokerList: {
            url: TransferCommon.brokerIdsUrl + 'list'
        }
    },
    Model: {},
    View: {
        initControl: function () {
            $.get(TransferMVC.URLs.spotCurrency.url, "", function (_data) {
                var allCurrency = _data || [];

                var allCurrencyWithId = [];

                for (var i = 0; i < allCurrency.length; i++) {
                    var item = allCurrency[i];

                    allCurrencyWithId.push({
                        "value": item.id,
                        "text": item.symbol.toUpperCase()
                    });

                }

                $('#currencyId').combobox({
                    required: true,
                    valueField: 'value',
                    textField: 'text',
                    data: allCurrencyWithId
                });
            });

            $.get(TransferMVC.URLs.getBrokerList.url, "", function (_data) {
                var allBrokerIds = _data || [];

                var allBrokerId = [];

                for (var i = 0; i < allBrokerIds.length; i++) {
                    var item = allBrokerIds[i];
                    allBrokerId.push({
                        "brokerId": item.id,
                        "brokerName": item.brokerName
                    });
                }

                $('#brokerId').combobox({
                    required: true,
                    valueField: 'brokerId',
                    textField: 'brokerName',
                    data: allBrokerId
                });

                $('#batchBrokerId').combobox({
                    required: true,
                    valueField: 'brokerId',
                    textField: 'brokerName',
                    data: allBrokerId
                });
            });

            $('#transfer-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: TransferMVC.URLs.list.url,
                toolbar: [{
                    text: '单个划转',
                    iconCls: 'icon-add',
                    handler: function () {
                        TransferMVC.Controller.add();
                    }
                }, '-', {
                    text: '批量划转',
                    iconCls: 'icon-add',
                    handler: function () {
                        TransferMVC.Controller.batchAdd();
                    }
                }, '-', {
                    text: '全部数据',
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#transfer-datagrid').datagrid('load', {});
                    }
                }],
                loadFilter: function (src) {
                    if (!src.code) {
                        return src.data;
                    }
                    return $.messager.alert('失败', src.msg, 'error');
                },
                columns: [[{
                    field: 'id',
                    title: 'ID',
                    width: 50,
                    sortable: true
                }, {
                    field: 'userId',
                    title: '用户ID',
                    width: 100,
                    sortable: true
                }, {
                    field: 'brokerId',
                    title: '券商',
                    width: 100,
                    sortable: true
                }, {
                    field: 'type',
                    title: '账单类型',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value == 1) {
                            return '冻结';
                        }

                        if (value == 2) {
                            return '解冻';
                        }

                        if (value == 3) {
                            return '转出';
                        }

                        if (value == 4) {
                            return '转入';
                        }

                        return '';
                    }
                }, {
                    field: 'currencyCode',
                    title: '币种',
                    width: 100,
                    sortable: true
                }, {
                    field: 'amount',
                    title: '金额',
                    width: 100,
                    sortable: true
                }, {
                    field: 'status',
                    title: '状态',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value == 1) {
                            return '未处理';
                        }

                        if (value == 2) {
                            return '处理成功';
                        }

                        if (value == 3) {
                            return '处理失败';
                        }

                        return '';
                    }
                }, {
                    field: 'remark',
                    title: '备注',
                    width: 100,
                    sortable: true
                }, {
                    field: 'createTime',
                    title: '创建时间',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                    }
                }, {
                    field: 'modifyTime',
                    title: '修改时间',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return TransferMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // dialogs
            $('#transfer-dlg').dialog({
                closed: true,
                modal: false,
                width: 800,
                height: 400,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#transfer-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: TransferMVC.Controller.save
                }]
            });

            $('#batch-transfer-dlg').dialog({
                closed: true,
                modal: false,
                width: 800,
                height: 600,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#batch-transfer-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: TransferMVC.Controller.batchSave
                }]
            });

        },
        bindEvent: function () {
            $('#btn-search').bind('click', TransferMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        doOption: function (index, name) {

        },
        add: function () {
            var options = TransferMVC.Util.getOptions();
            options.title = '币币资金划转';
            EasyUIUtils.openAddDlg(options);

            var allData = $('#currencyId').combobox('getData');
            if (allData) {
                $('#currencyId').combobox('select', allData[0]['value']);
            }

        },
        batchAdd: function () {
            var options = TransferMVC.Util.getBatchOptions();
            options.title = '币币资金划转';
            EasyUIUtils.openAddDlg(options);
        },
        find: function () {
            var params = TransferMVC.Util.getQueryParams();

            $('#transfer-datagrid').datagrid('load', params);
        },
        save: function () {
            var options = TransferMVC.Util.getOptions();

            //修改后刷新列表
            EasyUIUtils.save(options);

            return;
        },
        batchSave: function () {
            var options = TransferMVC.Util.getBatchOptions();

            //修改后刷新列表
            EasyUIUtils.save(options);

            return;
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#transfer-dlg',
                formId: '#transfer-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                url: TransferMVC.URLs.add.url,
                gridId: '#transfer-datagrid',
                gridUrl: TransferMVC.URLs.list.url
            };
        },
        getBatchOptions: function () {
            return {
                dlgId: '#batch-transfer-dlg',
                formId: '#batch-transfer-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                url: TransferMVC.URLs.batchAdd.url,
                gridId: '#transfer-datagrid',
                gridUrl: TransferMVC.URLs.list.url
            };
        },
        getQueryParams: function () {
            var beginTime = $('#transfer-beginTime').datetimebox('getValue');
            var endTime = $('#transfer-endTime').datetimebox('getValue');
            var userId = $('#transfer-userId').textbox('getValue');

            var params = {
                beginTime: beginTime,
                endTime: endTime,
                userId: userId
            };

            return params;
        }
    }
};