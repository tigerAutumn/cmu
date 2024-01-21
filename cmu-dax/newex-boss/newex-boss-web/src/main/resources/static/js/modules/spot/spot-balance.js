$(function () {
    BalanceDs.init();
});

var BalanceDs = {
    init: function () {
        BalanceDsMVC.View.initControl();
        BalanceDsMVC.View.bindEvent();

    }
};

var BalanceDsCommon = {
    coinsbaseUrl: BossGlobal.ctxPath + '/v1/boss/spot/balancecoins/',
    fiatbaseUrl: BossGlobal.ctxPath + '/v1/boss/c2c/balancefiat/',
    indexbaseUrl: BossGlobal.ctxPath + '/v1/boss/portfolio/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    basecomboxUrl: BossGlobal.ctxPath + '/v1/boss/asset/currency/currencies',
    TradeTypeUrl: BossGlobal.ctxPath + '/v1/boss/c2c/asset-transfer/type',
    baseCommonUrl: BossGlobal.ctxPath + '/v1/boss/common/',
    c2cCurrencyListUrl: BossGlobal.ctxPath + '/v1/boss/c2c/order/currencies',

};

var BalanceDsMVC = {
    URLs: {
        SearchCoins: {
            url: BalanceDsCommon.coinsbaseUrl + 'coinslist',
            method: 'GET'
        },
        SearchFiat: {
            url: BalanceDsCommon.fiatbaseUrl + 'fiatlist',
            method: 'GET'
        },
        SearchIndex: {
            url: BalanceDsCommon.indexbaseUrl + 'bill',
            method: 'GET'
        },
        getcurrenname: {
            url: BalanceDsCommon.basecomboxUrl + '?findInstitution',
            method: 'post'
        },
        getSpotCurrencyList: {
            url: BalanceDsCommon.baseCommonUrl + 'spot/currency',
            method: 'GET'
        },
        getC2cCurrencyList: {
            url: BalanceDsCommon.baseCommonUrl + 'c2c/currency',
            method: 'GET'
        },
        getPortfolioCurrencyList: {
            url: BalanceDsCommon.baseCommonUrl + 'portfolio/currency',
            method: 'GET'
        }


    },
    Model: {},
    View: {
        initControl: function () {

            $.get(BalanceDsMVC.URLs.getSpotCurrencyList.url, "", function (_data) {
                $("#conins-currency-name ").combobox({
                    data: _data,
                    valueField: "symbol",
                    textField: "symbol",
                    panelHeight: 300,
                    editable: true,
                    onSelect: function (record) {
                        $.tabIndex = $('.conins-currency-name ').index(this);
                        $(".conins-currency-name ").eq($.tabIndex).textbox("setValue", record.id)
                    },
                    onLoadSuccess: function () { //加载完成后,设置选中第一项
                        var val = $(this).combobox("getData");
                        for (var item in val[0]) {
                            if (item == "symbol") {
                                $(this).combobox("select", val[0][item]);
                            }
                        }
                    }
                });
            });

            $.get(BalanceDsMVC.URLs.getC2cCurrencyList.url, "", function (_data) {
                $("#fiat-currency-name").combobox({
                    data: _data,
                    valueField: "symbol",
                    textField: "symbol",
                    panelHeight: 300,
                    editable: true,
                    onSelect: function (record) {
                        $.tabIndex = $('.fiat-currency-name').index(this);
                        $(".fiat-currency-name").eq($.tabIndex).textbox("setValue", record.id)
                    },
                    onLoadSuccess: function () { //加载完成后,设置选中第一项
                        var val = $(this).combobox("getData");
                        for (var item in val[0]) {
                            if (item == "symbol") {
                                $(this).combobox("select", val[0][item]);
                            }
                        }
                    }

                });
            });

            $.get(BalanceDsMVC.URLs.getPortfolioCurrencyList.url, "", function (_data) {
                $("#index-currency").combobox({
                    data: _data,
                    valueField: "symbol",
                    textField: "symbol",
                    panelHeight: 300,
                    editable: true,
                    onSelect: function (record) {
                        $.tabIndex = $('.index-currency').index(this);
                        $(".index-currency").eq($.tabIndex).textbox("setValue", record.id)
                    },
                    onLoadSuccess: function () { //加载完成后,设置选中第一项
                        var val = $(this).combobox("getData");
                        for (var item in val[0]) {
                            if (item == "symbol") {
                                $(this).combobox("select", val[0][item]);
                            }
                        }
                    }

                });
            });

            $('#conins-order').datagrid({
                method: 'get',
                fit: true,
                autoRowWidth: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                sortOrder: 'desc',
                pageSize: 50,
                pageNumber: 1,
                url: BalanceDsMVC.URLs.SearchCoins.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        $("#conins-order").datagrid({
                            pageNumber: 1,
                            pageSize: 50,
                            url: BalanceDsMVC.URLs.SearchCoins.url
                        });
                        //EasyUIUtils.reloadDatagrid('#conins-order');
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
                        field: 'createdDate',
                        title: '时间',
                        width: 150,
                        sortable: true,
                        formatter: function (val, row, index) {
                            return moment(new Date(val)).format("YYYY-MM-DD HH:mm:ss")
                        }

                    }, {
                        field: 'currencyCode',
                        title: '币种',
                        width: 100,
                        sortable: true
                    },
                    {
                        field: 'userSize',
                        title: '用户账单余额总和',
                        width: 200,
                        sortable: true
                    },
                    {
                        field: 'userBalance',
                        title: '用户资产余额总和',
                        width: 200,
                        sortable: true
                    },
                    {
                        field: 'userFee',
                        title: '手续费',
                        width: 200,
                        sortable: true

                    },
                    {
                        field: 'B',
                        title: '充值未确认',
                        width: 100,
                        sortable: true
                    },
                    {
                        field: 'A',
                        title: '提现未确认',
                        width: 100,
                        sortable: true
                    },

                    {
                        field: 'A',
                        title: '结算中',
                        width: 100,
                        sortable: true

                    }, {
                        field: 'diff',
                        title: '差额',
                        width: 200,
                        sortable: true
                    },
                    {
                        field: 'normal',
                        title: '是否正常',
                        width: 100,
                        formatter: function (value, row, index) {
                            if (value) {
                                return "是";
                            } else {
                                return "否";
                            }
                        }
                    }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return BalanceDsMVC.Controller.edit();
                },
                onLoadSuccess: function () {
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });
            $('#index-bill-datagrid').datagrid({
                method: 'get',
                fit: true,
                autoRowWidth: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                sortOrder: 'desc',
                pageSize: 50,
                pageNumber: 1,
                url: BalanceDsMVC.URLs.SearchIndex.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#index-bill-datagrid');
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
                        field: 'createdDate',
                        title: '时间',
                        width: 150,
                        sortable: true,
                        formatter: function (val, row, index) {
                            return moment(new Date(val)).format("YYYY-MM-DD HH:mm:ss")
                        }

                    }, {
                        field: 'currencyCode',
                        title: '币种',
                        width: 100,
                        sortable: true
                    },
                    {
                        field: 'userSize',
                        title: '组合计算资产',
                        width: 200,
                        sortable: true
                    },
                    {
                        field: 'userBalance',
                        title: '实际冻结资产',
                        width: 200,
                        sortable: true
                    },
                    {
                        field: 'userFee',
                        title: '手续费',
                        width: 200,
                        sortable: true

                    },
                    {
                        field: 'A',
                        title: '阀值',
                        width: 100,
                        sortable: true

                    }, {
                        field: 'diff',
                        title: '差额',
                        width: 200,
                        sortable: true
                    },
                    {
                        field: 'normal',
                        title: '是否正常',
                        width: 100,
                        formatter: function (value, row, index) {
                            if (value) {
                                return "是";
                            } else {
                                return "否";
                            }
                        }
                    }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return BalanceDsMVC.Controller.edit();
                },
                onLoadSuccess: function () {
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            var pager = $("#conins-order").datagrid("getPager");
            if (pager) {
                $(pager).pagination({
                    onSelectPage: function (pageNumber, pageSize) {

                        var url = BalanceDsMVC.Util.getSearchUrl();
                        $("#conins-order").datagrid({
                            pageNumber: pageNumber,
                            pageSize: pageSize,
                            url: url
                        });

                    }
                });
            }
            $('#fiat-post').datagrid({
                method: 'get',
                fit: true,
                autoRowWidth: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                sortOrder: 'desc',
                pageSize: 50,
                pageNumber: 1,
                url: BalanceDsMVC.URLs.SearchFiat.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        $("#fiat-post").datagrid({
                            pageNumber: 1,
                            pageSize: 50,
                            url: BalanceDsMVC.URLs.SearchFiat.url
                        });
                        //EasyUIUtils.reloadDatagrid('#fiat-post');
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
                        field: 'createdDate',
                        title: '时间',
                        width: 150,
                        formatter: function (val, row, index) {
                            return moment(new Date(val)).format("YYYY-MM-DD HH:mm:ss")
                        }
                    }, {
                        field: 'currencyCode',
                        title: '币种',
                        width: 100,
                        sortable: true
                    }, {
                        field: 'userSize',
                        title: '用户账单余额总和',
                        width: 150,
                        sortable: true
                    }, {
                        field: 'userBalance',
                        title: '用户资产余额总和',
                        width: 150,
                        sortable: true
                    }, {
                        field: 'userFee',
                        title: '手续费',
                        width: 200,
                        sortable: true
                    }, {
                        field: 'A',
                        title: '充值未确认',
                        width: 100,
                        sortable: true

                    }, {
                        field: 'B',
                        title: '提现未确认',
                        width: 100,
                        sortable: true

                    }, {
                        field: 'A',
                        title: '结算中',
                        width: 100,
                        sortable: true

                    }, {
                        field: 'diff',
                        title: '差额',
                        width: 200,
                        sortable: true
                    }, {
                        field: 'normal',
                        title: '是否正常',
                        width: 100,
                        formatter: function (value, row, index) {
                            if (value) {
                                return "是";
                            } else {
                                return "否";
                            }
                        }

                    }
                ]],
                onDblClickRow: function (rowIndex, rowData) {
                },
                onLoadSuccess: function () {
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            var pager = $("#fiat-post").datagrid("getPager");
            if (pager) {
                $(pager).pagination({
                    onSelectPage: function (pageNumber, pageSize) {

                        var url = BalanceDsMVC.Util.getfiatSearchUrl();
                        $("#fiat-post").datagrid({
                            pageNumber: pageNumber,
                            pageSize: pageSize,
                            url: url
                        });

                    }
                });
            }
        },
        bindEvent: function () {
            $('#conins-search').bind('click', BalanceDsMVC.Controller.findconins);
            $('#fiat-search').bind('click', BalanceDsMVC.Controller.findfiat);
            $('#fiat-reset').bind('click', BalanceDsMVC.Controller.fiatreset);
            $('#conins-reset').bind('click', BalanceDsMVC.Controller.coninsreset);

        }
    },
    Controller: {

        findconins: function () {
            var url = BalanceDsMVC.Util.getSearchUrl();
            $("#conins-order").datagrid({
                pageNumber: 1,
                pageSize: 50,
                url: url
            });
            // var currencyname = $("#conins-currency-name").combobox('getValue');
            // var startDateMillis = $("#conins-beginDate").textbox('getValue');
            // var endDateMillis = $("#conins-endDate").textbox('getValue');
            // var url = BalanceDsMVC.URLs.SearchCoins.url + '?startDateMillis=' + startDateMillis + '&endDateMillis=' + endDateMillis + '&currencyCode=' + currencyname;
            // EasyUIUtils.loadToDatagrid('#conins-order', url)
        },
        findfiat: function () {
            var url = BalanceDsMVC.Util.getfiatSearchUrl();
            $("#fiat-post").datagrid({
                pageNumber: 1,
                pageSize: 50,
                url: url
            });

            // var currencyname = $("#fiat-currency-name").combobox('getValue');
            // var startDate = $("#fiat-beginDate").textbox('getValue');
            // var endDate = $("#fiat-endDate").textbox('getValue');
            // var url = BalanceDsMVC.URLs.SearchFiat.url + '?startDateMillis=' + startDate + '&endDateMillis=' + endDate + '&currencyCode=' + currencyname;
            // EasyUIUtils.loadToDatagrid('#fiat-post', url)
        },
        fiatreset: function () {
            $('#fiat-currency-name').combobox('clear')
            $('#fiat-beginDate').combo('clear');
            $('#fiat-endDate').combo('clear');
        },
        coninsreset: function () {
            $('#conins-currency-name').combobox('clear')
            $('#conins-beginDate').combo('clear');
            $('#conins-endDate').combo('clear');
        }
    },
    Util: {

        getSearchUrl: function () {
            var currencyname = $("#conins-currency-name").combobox('getValue');
            var startDateMillis = $("#conins-beginDate").textbox('getValue');
            var endDateMillis = $("#conins-endDate").textbox('getValue');
            return BalanceDsMVC.URLs.SearchCoins.url + '?startDateMillis=' + startDateMillis + '&endDateMillis=' + endDateMillis + '&currencyCode=' + currencyname;

        },
        getfiatSearchUrl: function () {
            var currencyname = $("#fiat-currency-name").combobox('getValue');
            var startDate = $("#fiat-beginDate").textbox('getValue');
            var endDate = $("#fiat-endDate").textbox('getValue');
            return BalanceDsMVC.URLs.SearchFiat.url + '?startDateMillis=' + startDate + '&endDateMillis=' + endDate + '&currencyCode=' + currencyname;
        },

        isRowSelected: function (func) {
            var row = $('#c2c-bill').datagrid('getSelected');
            if (row) {
                func(row);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        }
    }
};