$(function () {
    CurrencyPairBrokerRelation.init();
});

var CurrencyPairBrokerRelation = {
    init: function () {
        CurrencyPairBrokerRelationMVC.View.initControl();
        CurrencyPairBrokerRelationMVC.View.bindEvent();
    }
};

var CurrencyPairBrokerRelationCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/perpetual/currencyPairBrokerRelation',
    commonUrl: BossGlobal.ctxPath + '/v1/boss/common/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/'
};

var CurrencyPairBrokerRelationMVC = {
    URLs: {
        list: {
            url: CurrencyPairBrokerRelationCommon.baseUrl + '/list',
            method: 'GET'
        },
        add: {
            url: CurrencyPairBrokerRelationCommon.baseUrl + '/add',
            method: 'POST'
        },
        edit: {
            url: CurrencyPairBrokerRelationCommon.baseUrl + '/edit',
            method: 'POST'
        },
        remove: {
            url: CurrencyPairBrokerRelationCommon.baseUrl + '/remove',
            method: 'POST'
        },
        currency: {
            url: CurrencyPairBrokerRelationCommon.commonUrl + 'spot/currency',
            method: 'GET'
        },
        getBrokerList: {
            url: CurrencyPairBrokerRelationCommon.brokerBaseUrl + 'list',
            method: 'GET'
        },
        getPerpetualPairCodeList: {
            url: CurrencyPairBrokerRelationCommon.commonUrl + 'perpetual/currencyPair/pairCodes',
            method: 'GET'
        }
    },
    Model: {
        allCurrency: {},
        allBroker: {}
    },
    View: {
        initControl: function () {

            $.get(CurrencyPairBrokerRelationMVC.URLs.getPerpetualPairCodeList.url, "", function (_data) {

                var pairCodes = [];
                pairCodes.push({
                    "value": "",
                    "text": "全部"
                });
                for (var i = 0; i < _data.length; i++) {
                    var item = _data[i];
                    pairCodes.push({
                        "value": item.pairCode,
                        "text": item.pairCode
                    });
                }
                $('#pairCode').combobox({
                    data: _data,
                    valueField: "pairCode",
                    textField: "pairCode",
                    panelHeight: 300,
                    editable: true,
                    required: true
                });

                $('#query-pairCode').combobox({
                    data: pairCodes,
                    valueField: "value",
                    textField: "text",
                    panelHeight: 300,
                    editable: true,
                    required: true
                });
            });

            $.get(CurrencyPairBrokerRelationMVC.URLs.getBrokerList.url, "", function (_data) {
                CurrencyPairBrokerRelationMVC.Model.allBroker = _data;
                var queryBrokerID = [];
                queryBrokerID.push({
                    "value": "",
                    "text": "全部"
                });
                for (var i = 0; i < _data.length; i++) {
                    var item = _data[i];
                    queryBrokerID.push({
                        "value": item.id,
                        "text": item.brokerName
                    });
                }

                $("#brokerId").combobox({
                    data: _data,
                    valueField: "id",
                    textField: "brokerName",
                    panelHeight: 300,
                    editable: true,
                    required: true,
                    prompt: '选择券商'
                });

                $("#query-brokerId").combobox({
                    data: queryBrokerID,
                    valueField: "value",
                    textField: "text",
                    panelHeight: 300,
                    editable: true,
                    required: true
                });
            });

            $('#currencyPairBrokerRelation-dlg').dialog({
                closed: true,
                modal: false,
                width: 500,
                height: 300,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#currencyPairBrokerRelation-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: function () {
                        CurrencyPairBrokerRelationMVC.Controller.save();
                    }
                }]
            });

            $('#currencyPairBrokerRelation-datagrid').datagrid({
                method: 'GET',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: CurrencyPairBrokerRelationMVC.URLs.list.url,
                toolbar: [{
                    text: '新增',
                    iconCls: 'icon-add',
                    handler: function () {
                        CurrencyPairBrokerRelationMVC.Controller.add();
                    }
                }, '-', {
                    text: '修改',
                    iconCls: 'icon-edit1',
                    handler: function () {
                        CurrencyPairBrokerRelationMVC.Controller.modify();
                    }
                }, '-', {
                    text: '全部数据',
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#currencyPairBrokerRelation-datagrid').datagrid('load', {});
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
                        field: 'pairCode',
                        title: '币对Code',
                        width: 100
                    },
                    {
                        field: 'brokerId',
                        title: '券商ID',
                        width: 150,
                        formatter: function (value) {
                            var brokerList = CurrencyPairBrokerRelationMVC.Model.allBroker;
                            for (var i = 0; i < brokerList.length; i++) {
                                if (value === brokerList[i].id) {
                                    return brokerList[i].brokerName;
                                }
                            }
                            return value;
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
                        field: 'modifyDate',
                        title: '更新时间',
                        width: 100,
                        formatter: function (value) {
                            return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                        }
                    }
                ]],
                onDblClickRow: function (rowIndex, rowData) {
                    return CurrencyPairBrokerRelationMVC.Controller.modify();
                },
                onLoadSuccess: function () {
                }
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', CurrencyPairBrokerRelationMVC.Controller.query);
        }
    },
    Controller: {
        add: function () {
            $('#action').val("save");
            var options = CurrencyPairBrokerRelationMVC.Util.getOptions();
            options.title = '新增合约币对券商关联纪录';
            EasyUIUtils.openAddDlg(options);
        },
        save: function () {
            var options = {
                gridId: null,
                gridUrl: CurrencyPairBrokerRelationMVC.URLs.list.url,
                dlgId: "#currencyPairBrokerRelation-dlg",
                formId: "#currencyPairBrokerRelation-form",
                method: 'POST',
                url: null,
                callback: function () {
                }
            };

            var action = $('#action').val();
            if (action === "save") {
                options.url = CurrencyPairBrokerRelationMVC.URLs.add.url;
            } else if (action === "update") {
                options.url = CurrencyPairBrokerRelationMVC.URLs.edit.url;
            }
            options.gridId = '#currencyPairBrokerRelation-datagrid';
            return EasyUIUtils.save(options);
        },
        modify: function () {
            var row = $('#currencyPairBrokerRelation-datagrid').datagrid('getSelected');
            if (row) {
                $('#action').val("update");
                var options = CurrencyPairBrokerRelationMVC.Util.getOptions();
                options.iconCls = 'icon-edit';
                options.data = row;
                options.title = '修改[' + options.data.pairCode + ']';
                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#currencyPairBrokerRelation-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: CurrencyPairBrokerRelationMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#currencyPairBrokerRelation-datagrid',
                    gridUrl: CurrencyPairBrokerRelationMVC.URLs.list.url,
                    callback: function (rows) {
                    }
                };
                EasyUIUtils.remove(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        changePairCode: function () {
            var quote = $('#quote').combobox('getValue').toUpperCase();
            var base = $("#base").combobox('getValue').toUpperCase();
            var biz = $('#biz').combobox('getValue').toUpperCase();
            var prefix = biz.slice(0, 1);
            $('#pairCode').textbox('setValue', prefix + "_" + base + "_" + quote);
        },
        query: function () {
            var params = CurrencyPairBrokerRelationMVC.Util.getQueryParam();
            $('#currencyPairBrokerRelation-datagrid').datagrid('load', params);
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#currencyPairBrokerRelation-dlg',
                formId: '#currencyPairBrokerRelation-form',
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
            var pairCode = $("#query-pairCode").combobox('getValue');
            var brokerId = $("#query-brokerId").combobox('getValue');

            var params = {
                pairCode: pairCode,
                brokerId: brokerId
            };

            return params;
        }
    }
};
