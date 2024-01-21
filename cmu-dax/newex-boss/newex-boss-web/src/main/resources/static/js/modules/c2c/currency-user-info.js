$(function () {
    CurrencyUserInfo.init();
});

var CurrencyUserInfo = {
    init: function () {
        CurrencyUserInfoMVC.View.initControl();
        CurrencyUserInfoMVC.View.bindEvent();
        CurrencyUserInfoMVC.View.bindValidate();

    }
};

var CurrencyUserInfoCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/c2c/user-currency/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    basecomboxUrl: BossGlobal.ctxPath + '/v1/boss/c2c/order/currencies',
    TradeTypeUrl: BossGlobal.ctxPath + '/v1/boss/c2c/asset-transfer/type'
};

var CurrencyUserInfoMVC = {
    URLs: {
        Search: {
            url: CurrencyUserInfoCommon.baseUrl,
            method: 'GET'
        },
        BitType: {
            url: CurrencyUserInfoCommon.basecomboxUrl,
            method: 'GET'
        },
        optionType: {
            url: CurrencyUserInfoCommon.TradeTypeUrl,
            method: 'GET'
        }
    },
    Model: {
        userId: 10000041
    },
    View: {
        initControl: function () {
            $("#currency").combobox({
                url: CurrencyUserInfoMVC.URLs.BitType.url + "?type=0",
                method: 'get',
                valueField: "id",
                textField: "currencySymbol",
                panelHeight: 300,
                editable: false,
                formatter: function (row) {
                    var opts = $(this).combobox('options');
                    return row[opts.textField].toUpperCase();
                },
                onSelect: function () {
                }
            });
            $("#currency2").combobox({
                url: CurrencyUserInfoMVC.URLs.BitType.url + "?type=0",
                method: 'get',
                valueField: "id",
                textField: "currencySymbol",
                panelHeight: 300,
                editable: false,
                formatter: function (row) {
                    var opts = $(this).combobox('options');
                    return row[opts.textField].toUpperCase();
                },
                onSelect: function () {
                }
            });
            $("#legalTender").combobox({
                url: CurrencyUserInfoMVC.URLs.optionType.url,
                method: 'get',
                valueField: "code",
                textField: "description",
                panelHeight: 300,
                editable: false,
                onSelect: function () {
                }
            });
            $('#c2c-info').datagrid({
                method: 'get',
                fitColumns: true,
                singleSelect: true,
                rownumbers: true,
                sortOrder: 'desc',
                pageSize: 50,
                pageNumber: 1,
                url: CurrencyUserInfoMVC.URLs.Search.url + CurrencyUserInfoMVC.Model.userId + "/info",
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#c2c-info');
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
                        field: 'currencySymbol',
                        title: '币种',
                        width: 100,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return value.toUpperCase();
                        }

                    },
                    {
                        field: 'availableBalance',
                        title: '可用余额',
                        width: 100,
                        sortable: true
                    },
                    {
                        field: 'frozenBalance',
                        title: '冻结金额',
                        width: 100,
                        sortable: true

                    },
                    {
                        field: 'withdrawLimit',
                        title: '可提现余额',
                        width: 100,
                        sortable: true
                    }
                ]],
                onDblClickRow: function (rowIndex, rowData) {
                    return CurrencyUserInfoMVC.Controller.edit();
                },
                onLoadSuccess: function () { //加载完成后,设置选中第一项
                    // $('.pagination-page-list').hide();

                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });
            $('#c2c-bill').datagrid({
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
                url: CurrencyUserInfoMVC.URLs.Search.url + CurrencyUserInfoMVC.Model.userId + "/userBill",
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#c2c-bill');
                    }
                }, '-', {
                    id: "info",
                    name: 'info',
                    text: '详情',
                    iconCls: 'icon-info',
                    handler: function () {
                        CurrencyUserInfoMVC.Controller.billInfo();
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
                        field: 'userId',
                        title: '用户ID',
                        hidden: true
                    },
                    {
                        field: 'tradeNo',
                        title: '交易号',
                        width: 100,
                        hidden: true

                    },
                    {
                        field: 'id',
                        title: '账单Id',
                        width: 100,

                    }, {
                        field: 'currencySymbol',
                        title: '币种',
                        width: 100,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return value.toUpperCase();
                        }

                    }, {
                        field: 'createDate',
                        title: '时间',
                        width: 100
                    }, {
                        field: 'typeDescription',
                        title: '类型',
                        width: 100,
                        sortable: true

                    }, {
                        field: 'amountStr',
                        title: '金额',
                        width: 100,
                        sortable: true
                    }, {
                        field: 'afterBalance',
                        title: '余额',
                        width: 100,
                        formatter: function (value) {
                            return value;
                        }
                    }
                ]],
                onDblClickRow: function () {
                    return CurrencyUserInfoMVC.Controller.billInfo();
                },
                onLoadSuccess: function () {

                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });
            $('#bill-detail-dlg').dialog({
                closed: true,
                modal: true,
                width: 550,
                height: 300,
                maximizable: true,
                iconCls: 'icon-info',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#bill-detail-dlg").dialog('close');
                    }
                }]
            });
            $('#c2c-order').datagrid({
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
                url: CurrencyUserInfoMVC.URLs.Search.url + CurrencyUserInfoMVC.Model.userId + "/order",
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#c2c-order');
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
                        field: 'userId',
                        title: '用户ID',
                        width: 100,
                        hidden: true
                    }, {
                        field: 'publicOrderId',
                        title: '订单号',
                        width: 100,
                        sortable: true

                    }, {
                        field: 'tradingOrderUserId',
                        title: '委托人Id',
                        width: 100,
                        hidden: true
                    },
                    {
                        field: 'buyerId',
                        title: '买家',
                        width: 100,
                        hidden: true
                    },
                    {
                        field: 'sellerId',
                        title: '卖家',
                        width: 100,
                        hidden: true
                    },
                    {
                        field: 'orderType',
                        title: '类型',
                        width: 100,
                        formatter: function (value, row, index) {
                            if (row.buyerId === row.tradingOrderUserId) {
                                return "买";
                            } else {
                                return "卖";
                            }
                        }
                    },
                    {
                        field: 'legalCurrencySymbol',
                        title: '法币币种',
                        width: 100,
                        formatter: function (value) {
                            return value.toUpperCase();
                        }
                    },
                    {
                        field: 'digitalCurrencySymbol',
                        title: '数字币种',
                        width: 100,
                        formatter: function (value) {
                            return value.toUpperCase();
                        }
                    },

                    {
                        field: 'exchangeRate',
                        title: '价格',
                        width: 100,

                    }, {
                        field: 'buyerInAmount',
                        title: '数量',
                        width: 100,
                        sortable: true
                    },
                    {
                        field: 'orderTotal',
                        title: '金额',
                        width: 100,
                        sortable: true
                    },
                    {
                        field: 'createdDate',
                        title: '下单时间',
                        width: 100,
                    },
                    {
                        field: 'orderStatus',
                        title: '订单状态',
                        width: 100,
                        formatter: function (val) {
                            switch (val) {
                                case 0:
                                    return "垃圾数据";
                                case 1:
                                    return "新订单";
                                case 2:
                                    return "已取消";
                                case 3:
                                    return "已完成";
                            }
                        }
                    },
                    {
                        field: 'receiptAccountType',
                        title: '支付方式',
                        width: 100,
                        formatter: function (val) {
                            switch (val) {
                                case 0:
                                    return "银行";
                                case 1:
                                    return "支付宝";
                                case 2:
                                    return "微信";
                            }
                        }

                    }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return CurrencyUserInfoMVC.Controller.edit();
                },
                onLoadSuccess: function () {
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });
            $('#c2c-post').datagrid({
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
                url: CurrencyUserInfoMVC.URLs.Search.url + CurrencyUserInfoMVC.Model.userId + "/trade",
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#c2c-post');
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
                        field: 'userId',
                        title: '用户ID',
                        width: 100,
                        hidden: true
                    },
                    {
                        field: 'publicTradingOrderId',
                        title: '委托单号',
                        width: 120,


                    }, {
                        field: 'isBuy',
                        title: '类型',
                        width: 100,
                        formatter: function (val) {
                            switch (val) {
                                case false:
                                    return "卖";
                                case true:
                                    return "买";
                            }
                        }
                    }, {
                        field: 'availableAmount',
                        title: '数量',
                        width: 100,
                        sortable: true
                    },

                    {
                        field: 'frozenAmount',
                        title: '已冻结数量',
                        width: 100,
                        sortable: true
                    },
                    {
                        field: 'depositAmount',
                        title: '保证金',
                        width: 100,
                        sortable: true
                    },
                    {
                        field: 'completedOrderQuantity',
                        title: '已完成单数',
                        width: 100,
                        sortable: true

                    },
                    {
                        field: 'completedOrderTotal',
                        title: '已完成订单金额',
                        width: 100,
                        sortable: true

                    }, {
                        field: 'completedOrderRate',
                        title: '完成率',
                        width: 100,
                        sortable: true

                    },

                    {
                        field: 'status',
                        title: '委托单状态',
                        width: 100,
                        formatter: function (val) {
                            switch (val) {
                                case 0:
                                    return "垃圾数据";
                                case 1:
                                    return "新订单";
                                case 2:
                                    return "已取消";
                                case 3:
                                    return "已完成";
                            }
                        }
                    }, {
                        field: 'createdDate',
                        title: '创建时间',
                        width: 100,
                        formatter: function (val, row, index) {
                            return moment(new Date(val)).format("YYYY-MM-DD HH:mm:ss")
                        }
                    }, {
                        field: 'paymentElapsedSecondsAvg',
                        title: '平均付款时间',
                        width: 100,
                        sortable: true

                    }, {
                        field: 'completeElapsedSecondsAvg',
                        title: '平均放币时间',
                        width: 100,
                        sortable: true

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

        },
        bindEvent: function () {
            $('#post-search').bind('click', CurrencyUserInfoMVC.Controller.findPost);
            $('#order-search').bind('click', CurrencyUserInfoMVC.Controller.findOrder);
            $('#account-search').bind('click', CurrencyUserInfoMVC.Controller.findBill);
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#c2c-bill').datagrid('selectRow', index);
            if (name === "info") {
                return CurrencyUserInfoMVC.Controller.billInfo();
            }
        },
        billInfo: function () {
            CurrencyUserInfoMVC.Util.isRowSelected(function (row) {
                $('#bill-detail-dlg').dialog('open').dialog('center');
                var tradeNo = row.tradeNo;
                //var tradeNo = '63f9fe72-4d5e-4230-a3b9-68a2c3a023e21526627957268';
                var url;
                if (tradeNo !== '') {
                    url = CurrencyUserInfoMVC.URLs.Search.url + tradeNo + "/bill";
                }
                CurrencyUserInfoMVC.Util.fillDetailLabels(url, row);

            });
        },
        findPost: function () {
            var postType = $("#postType").combobox('getValue');
            var postId = $("#postId").textbox('getValue');
            var status = $("#postStatus").combobox('getValue');
            var url = CurrencyUserInfoMVC.URLs.Search.url + CurrencyUserInfoMVC.Model.userId + '/trade' + '?postType=' + postType + '&postId=' + postId + '&status=' + status;
            EasyUIUtils.loadToDatagrid('#c2c-post', url)
        },
        findOrder: function () {
            var orderId = $("#orderId").textbox('getValue');
            var currency = $("#currency2").combobox('getText');
            var payType = $("#payType2").combobox('getValue');
            var url = CurrencyUserInfoMVC.URLs.Search.url + CurrencyUserInfoMVC.Model.userId + '/order' + '?orderId=' + orderId + '&payType=' + payType + '&currency=' + currency;
            EasyUIUtils.loadToDatagrid('#c2c-order', url)
        },
        findBill: function () {
            var currency = $("#currency").combobox('getValue');
            var legalTender = $("#legalTender").combobox('getValue');
            var orderId = '';
            var url = CurrencyUserInfoMVC.URLs.Search.url + CurrencyUserInfoMVC.Model.userId + "/userBill" + '?currency=' + currency + '&legalTender=' + legalTender + '&orderId=' + orderId;
            EasyUIUtils.loadToDatagrid('#c2c-bill', url)
        }
    },
    Util: {
        isRowSelected: function (func) {
            var row = $('#c2c-bill').datagrid('getSelected');
            if (row) {
                func(row);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        fillDetailLabels: function (url, data) {
            $('#bill-detail-dlg label').each(function (i) {
                $(this).text("");
            });
            $("#bill-userId").text(data.userId);
            $("#bill-id").text(data.id);
            $("#bill-currencySymbol").text(data.currencySymbol);
            $("#bill-createdDate").text(data.createDate);
            $("#bill-typeDescription").text(data.typeDescription);
            $("#bill-amountStr").text(data.amountStr);
            $("#bill-afterBalance").text(data.afterBalance);
            $.getJSON(url, function (r) {
                var dataT = r.data;
                $("#bill-tradeNo").text(dataT.traderNo);
                $("#bill-to").text(dataT.to);
                $("#bill-fee").text(dataT.fee);
                $("#bill-real").text(moment(new Date(dataT.updateDate)).format('YYYY-MM-DD HH:mm:ss'));
            });



        },
    }
};