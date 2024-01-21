$(function () {
    PerpetualCurrencyPair.init();
});

var PerpetualCurrencyPair = {
    init: function () {
        PerpetualCurrencyPairMVC.View.initControl();
        PerpetualCurrencyPairMVC.View.bindEvent();
    }
};

var PerpetualCurrencyPairCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/perpetual/currency-pair',
    commonUrl: BossGlobal.ctxPath + '/v1/boss/common/'
};

var PerpetualCurrencyPairMVC = {
    URLs: {
        list: {
            url: PerpetualCurrencyPairCommon.baseUrl + '/list',
            method: 'GET'
        },
        add: {
            url: PerpetualCurrencyPairCommon.baseUrl + '/add',
            method: 'POST'
        },
        edit: {
            url: PerpetualCurrencyPairCommon.baseUrl + '/edit',
            method: 'POST'
        },
        remove: {
            url: PerpetualCurrencyPairCommon.baseUrl + '/remove',
            method: 'POST'
        },
        currency: {
            url: PerpetualCurrencyPairCommon.commonUrl + 'perpetual/currency',
            method: 'GET'
        }
    },
    Model: {
        allCurrency: {}
    },
    View: {
        initControl: function () {

            $('#biz').combobox({
                onChange: function (newVal, oldVal) {
                    PerpetualCurrencyPairMVC.Controller.changePairCode();
                }
            });

            $('#quote').combobox({
                valueField: 'value',
                textField: 'value',
                editable: true,
                required: true,
                data: [
                    {
                        id: '1',
                        value: 'BTC'
                    }, {
                        id: '2',
                        value: 'ETH'
                    }, {
                        id: '6',
                        value: 'USDT'
                    }
                ],
                onChange: function (newVal, oldVal) {
                    PerpetualCurrencyPairMVC.Controller.changePairCode();
                }
            });

            $.getJSON(PerpetualCurrencyPairMVC.URLs.currency.url, "", function (_data) {
                PerpetualCurrencyPairMVC.Model.allCurrency = _data;

                var queryBase = [];
                var queryQuote = [];
                var quote = [];

                queryBase.push({
                    "value": "",
                    "text": "全部"
                });
                queryQuote.push({
                    "value": "",
                    "text": "全部"
                });
                for (var i = 0; i < _data.length; i++) {
                    var item = _data[i];
                    queryBase.push({
                        "value": item.symbol,
                        "text": item.symbol
                    });
                    queryQuote.push({
                        "value": item.symbol,
                        "text": item.symbol
                    });
                    quote.push({
                        "value": item.symbol,
                        "text": item.symbol
                    })
                }

                $("#indexBase").combobox({
                    data: _data,
                    valueField: "symbol",
                    textField: "symbol",
                    panelHeight: 300,
                    editable: true,
                    required: true,
                    onChange: function (newVal, oldVal) {
                        PerpetualCurrencyPairMVC.Controller.changePairCode();
                    }
                });
                $("#base").combobox({
                    data: _data,
                    valueField: "symbol",
                    textField: "symbol",
                    panelHeight: 300,
                    editable: true,
                    required: true
                });

                $("#quote").combobox({
                    data: quote,
                    valueField: "value",
                    textField: "text",
                    panelHeight: 300,
                    editable: true,
                    required: true,
                    onChange: function (newVal, oldVal) {
                        PerpetualCurrencyPairMVC.Controller.changePairCode();
                    }
                });

                $("#query-base").combobox({
                    data: queryBase,
                    valueField: "value",
                    textField: "text",
                    panelHeight: 300,
                    editable: true,
                    required: true
                });


                $("#query-quote").combobox({
                    data: queryQuote,
                    valueField: "value",
                    textField: "text",
                    panelHeight: 300,
                    editable: true,
                    required: true
                });
            });

            $('#perpetual-currency-pair-dlg').dialog({
                closed: true,
                modal: false,
                width: 800,
                height: 500,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#perpetual-currency-pair-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: function () {
                        PerpetualCurrencyPairMVC.Controller.save();
                    }
                }]
            });

            $('#perpetual-currency-pair-datagrid').datagrid({
                method: 'GET',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: PerpetualCurrencyPairMVC.URLs.list.url,
                toolbar: [{
                    text: '新增',
                    iconCls: 'icon-add',
                    handler: function () {
                        PerpetualCurrencyPairMVC.Controller.add();
                    }
                }, '-', {
                    text: '修改',
                    iconCls: 'icon-edit1',
                    handler: function () {
                        PerpetualCurrencyPairMVC.Controller.modify();
                    }
                }, '-', {
                    text: '全部数据',
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#perpetual-currency-pair-datagrid').datagrid('load', {});
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
                        field: 'biz',
                        title: '业务类型',
                        width: 100
                    },
                    {
                        field: 'base',
                        title: '交易货币',
                        width: 100
                    },
                    {
                        field: 'indexBase',
                        title: '指数基础货币',
                        width: 100
                    },
                    {
                        field: 'quote',
                        title: '计价货币',
                        width: 100
                    },
                    {
                        field: 'pairCode',
                        title: '交易/计价货币组合',
                        width: 150
                    },
                    {
                        field: 'insuranceAccount',
                        title: '保险金账号',
                        width: 100
                    },
                    {
                        field: 'unitAmount',
                        title: 'Quote面值',
                        width: 100
                    },
                    {
                        field: 'minOrderAmount',
                        title: '每笔最小挂单数',
                        width: 150
                    },
                    {
                        field: 'maxOrderAmount',
                        title: '每笔最大挂单数',
                        width: 150
                    },
                    {
                        field: 'maxOrders',
                        title: '单人最大挂单数',
                        width: 150
                    },
                    {
                        field: 'minTradeDigit',
                        title: '基础货币最小交易',
                        width: 150
                    },
                    {
                        field: 'minQuoteDigit',
                        title: '计价货币最小交易',
                        width: 150
                    },
                    {
                        field: 'maxLevel',
                        title: '最大杠杆',
                        width: 100
                    },
                    {
                        field: 'direction',
                        title: '方向',
                        width: 100,
                        formatter: function (value) {
                            if (value === 0) {
                                return '正向';
                            } else if (value === 1) {
                                return '反向';
                            }
                        }
                    },
                    {
                        field: 'type',
                        title: '合约类型',
                        width: 100,
                        formatter: function (value) {
                            if (value === 'week') {
                                return '周';
                            } else if (value === 'nextweek') {
                                return '次周';
                            } else if (value === 'month') {
                                return '月';
                            } else if (value === 'quarter') {
                                return '季度';
                            } else if (value === 'perpetual') {
                                return '永续';
                            }
                        }
                    },
                    {
                        field: 'preLiqudatePriceThreshold',
                        title: '预强平价格阈值',
                        width: 150
                    },
                    {
                        field: 'premiumMinRange',
                        title: '溢价指数阈值下限',
                        width: 150
                    },
                    {
                        field: 'premiumMaxRange',
                        title: '溢价指数阈值上限',
                        width: 150
                    },
                    {
                        field: 'premiumDepth',
                        title: '溢价指数加权买卖价的取值深度',
                        width: 150
                    },
                    {
                        field: 'fundingCeiling',
                        title: '绝对的资金费率上限',
                        width: 150
                    },
                    {
                        field: 'minGear',
                        title: '最低档位',
                        width: 100
                    },
                    {
                        field: 'diffGear',
                        title: '每档的差值',
                        width: 100
                    },
                    {
                        field: 'maxGear',
                        title: '最高档位',
                        width: 100
                    },
                    {
                        field: 'interestRate',
                        title: '利率',
                        width: 100
                    },
                    {
                        field: 'maintainRate',
                        title: '维持保证金费率',
                        width: 150
                    },
                    {
                        field: 'liquidationHour',
                        title: '清算周期',
                        width: 100
                    },
                    {
                        field: 'marketPriceDigit',
                        title: '标记价格最小单位',
                        width: 100
                    },
                    {
                        field: 'dkFee',
                        title: '点卡抵扣手续费',
                        width: 100,
                        formatter: function (value) {
                            if (value === 0) {
                                return '不适用';
                            } else if (value === 1) {
                                return '使用';
                            }
                        }
                    },
                    {
                        field: 'env',
                        title: '是否测试盘',
                        width: 100,
                        formatter: function (value) {
                            if (value === 0) {
                                return '线上盘';
                            } else if (value === 1) {
                                return '测试盘';
                            }
                        }
                    },
                    {
                        field: 'online',
                        title: '状态',
                        width: 100,
                        formatter: function (value) {
                            if (value === 0) {
                                return '下线';
                            } else if (value === 1) {
                                return '预发';
                            } else if (value === 2) {
                                return '上线';
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
                        field: 'modifyDate',
                        title: '更新时间',
                        width: 100,
                        formatter: function (value) {
                            return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                        }
                    }
                ]],
                onDblClickRow: function (rowIndex, rowData) {
                    return PerpetualCurrencyPairMVC.Controller.modify();
                },
                onLoadSuccess: function () {
                }
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', PerpetualCurrencyPairMVC.Controller.query);
        }
    },
    Controller: {
        add: function () {
            $('#action').val("save");
            var options = PerpetualCurrencyPairMVC.Util.getOptions();
            options.title = '新增合约币对纪录';
            EasyUIUtils.openAddDlg(options);
            var data = {
                unitAmount: 1,
                minOrderAmount: 1,
                maxOrderAmount: 10000000,
                maxOrders: 100,
                minTradeDigit: 8,
                minQuoteDigit: 8,
                preLiqudatePriceThreshold: 0.5,
                premiumMinRange: -0.0005,
                premiumMaxRange: 0.0005,
                fundingCeiling: 0.75,
                minGear: 100.0,
                diffGear: 100.0,
                maxGear: 1000.0,
                liquidationHour: 8,
                maintainRate: 0.005,
                direction: 1,
                dkFee: 0,
                online: 0,
                premiumDepth: 10,
                type: 'perpetual',
                interestRate: 0,
                marketPriceDigit: 2
            };
            $('#perpetual-currency-pair-form').form('load', data);
            $('#pairCode').textbox('readonly', true);
        },
        save: function () {
            var options = {
                gridId: null,
                gridUrl: PerpetualCurrencyPairMVC.URLs.list.url,
                dlgId: "#perpetual-currency-pair-dlg",
                formId: "#perpetual-currency-pair-form",
                method: 'POST',
                url: null,
                callback: function () {
                }
            };

            var action = $('#action').val();
            if (action === "save") {
                options.url = PerpetualCurrencyPairMVC.URLs.add.url;
            } else if (action === "update") {
                options.url = PerpetualCurrencyPairMVC.URLs.edit.url;
            }
            options.gridId = '#perpetual-currency-pair-datagrid';
            return EasyUIUtils.save(options);
        },
        modify: function () {
            var row = $('#perpetual-currency-pair-datagrid').datagrid('getSelected');
            if (row) {
                $('#action').val("update");
                var options = PerpetualCurrencyPairMVC.Util.getOptions();
                options.iconCls = 'icon-edit';
                options.data = row;
                options.title = '修改[' + options.data.pairCode + ']';
                EasyUIUtils.openEditDlg(options);
                $('#pairCode').textbox('readonly', true);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#perpetual-currency-pair-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: PerpetualCurrencyPairMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#perpetual-currency-pair-datagrid',
                    gridUrl: PerpetualCurrencyPairMVC.URLs.list.url,
                    callback: function (rows) {
                    }
                };
                EasyUIUtils.remove(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        changePairCode: function () {
            var quote = $('#quote').combobox('getValue').toLowerCase();
            var base = $('#base').combobox('getValue').toLowerCase();
            var biz = $('#biz').combobox('getValue').toLowerCase();
            var prefix = biz.slice(0, 1);

            var direction = $('#direction').combobox('getValue');

            var pairCodeText = prefix + "" + base + "" + quote;
            if (direction == 1) {
                pairCodeText = pairCodeText + "(" + base + ")";
            } else {
                pairCodeText = pairCodeText + "(" + quote + ")";
            }

            $('#pairCode').textbox('setValue', pairCodeText);
        },
        query: function () {
            var params = PerpetualCurrencyPairMVC.Util.getQueryParam();
            $('#perpetual-currency-pair-datagrid').datagrid('load', params);
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#perpetual-currency-pair-dlg',
                formId: '#perpetual-currency-pair-form',
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
            var biz = $("#query-biz").combobox('getValue');
            var base = $("#query-base").combobox('getValue');
            var quote = $("#query-quote").combobox('getValue');
            var online = $("#query-online").combobox('getValue');

            var params = {
                biz: biz,
                base: base,
                online: online,
                quote: quote
            };

            return params;
        }
    }
};
