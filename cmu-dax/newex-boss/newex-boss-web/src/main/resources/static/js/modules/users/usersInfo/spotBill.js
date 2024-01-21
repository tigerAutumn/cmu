var SpotBillUtils = {
    loadBillType: function () {
        $.get(UsersInfoMVC.URLs.billcombox.url, "", function (_data) {
            _data.data.unshift({
                "code": "",
                "name": "全部"
            });
            
            $("#bill-type").combobox({
                data: _data.data,
                valueField: "code",
                textField: "name",
                panelHeight: 300,
                editable: true,
                onLoadSuccess: function () { //加载完成后,设置选中第一项
                    var allData = $(this).combobox("getData");

                    if (allData) {
                        $(this).combobox("select", allData[0]["code"]);
                    }

                }
            });
        });
    },
    loadAllCurrencyForBill: function (currencies) {
        $("#bill-currency-name").combobox({
            data: currencies,
            valueField: "value",
            textField: "text",
            editable: true,
            onLoadSuccess: function () { //加载完成后,设置选中第一项
                var allData = $(this).combobox("getData");
                if (allData) {
                    $(this).combobox("select", allData[0]["value"]);
                }
            }
        });
    },
    loadCurrencyPairForOrder: function (currencyPairs) {
        $("#order-currencyCode").combobox({
            data: currencyPairs,
            valueField: "value",
            textField: "text",
            editable: true,
            onLoadSuccess: function () { //加载完成后,设置选中第一项
                var allData = $(this).combobox("getData");
                if (allData) {
                    $(this).combobox("select", allData[0]["value"]);
                }
            }
        });
    },

    loadCurrencyPairForHistory: function (currencyPairs) {
        $("#his-currencyCode").combobox({
            data: currencyPairs,
            valueField: "value",
            textField: "text",
            editable: true,
            onLoadSuccess: function () { //加载完成后,设置选中第一项
                var allData = $(this).combobox("getData");
                if (allData) {
                    $(this).combobox("select", allData[0]["value"]);
                }
            }
        });
    },
    loadBill: function (gridUrl, userId) {
        var url = gridUrl + '?userId=' + userId;
        $('#bill-datagrid').datagrid({
            method: 'get',
            fit: true,
            fitColumns: false,
            singleSelect: true,
            pagination: true,
            rownumbers: true,
            pageSize: 50,
            url: url,
            queryParams: {
                isHiisHistorystory: false
            },
            toolbar: [{
                text: '全部',
                iconCls: 'icon-reload',
                handler: function () {
                    $('#bill-datagrid').datagrid('load', {isHiisHistorystory: false});
                }
            }, '-', {
                text: '详情',
                iconCls: 'icon-info',
                handler: function () {
                    SpotBillUtils.billshowDetail();
                }
            }],
            loadFilter: function (src) {
                if (!src.code) {
                    return src.data;
                }
                return $.messager.alert('失败', src.msg, 'error');
            },

            columns: [[{
                field: 'userId',
                title: '用户ID',
                width: 100,
                sortable: true
            },
                {
                    field: 'createTime',
                    title: '时间',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                    }
                },
                {
                    field: 'currencyName',
                    title: '币种',
                    width: 80,
                    sortable: true
                },
                {
                    field: 'pairName',
                    title: '币对',
                    width: 80
                }, {
                    field: 'type',
                    title: '类型',
                    width: 80,
                    sortable: true,
                    formatter: function (value, row, index) {

                        if (value == '1') {
                            return "充值";
                        } else if (value == '2') {
                            return "提现";
                        } else if (value == '7') {
                            return "买入";
                        } else if (value == '8') {
                            return "卖出";
                        } else if (value == '14') {
                            return "活动派发";
                        }
                    }
                },
                {
                    field: 'size',
                    title: '成交量',
                    width: 150,
                    sortable: true,
                },
                {
                    field: 'afterBalance',
                    title: '余额',
                    width: 150,
                    sortable: true
                },
                {
                    field: 'price',
                    title: '实际成交价',
                    width: 150,
                    sortable: true
                },
                {
                    field: 'referId',
                    title: '关联订单ID',
                    width: 150,
                    sortable: true
                },
                {
                    field: 'fee',
                    title: '手续费',
                    width: 150,
                    sortable: true
                },
            ]],
            onDblClickRow: function (rowIndex, rowData) {
                return SpotBillUtils.billshowDetail();
            },
            onLoadSuccess: function () {
            }
        });
    },
    billshowDetail: function () {
        SpotBillUtils.billisHisRowSelected(function (row) {
            $('#bill-dlg').dialog('open').dialog('center');

            SpotBillUtils.billhisfillDetailLabels(row);
        });
    },
    billisHisRowSelected: function (func) {
        var row = $('#bill-datagrid').datagrid('getSelected');
        if (row) {
            func(row);
        } else {
            $.messager.alert('警告', '请选中一条记录!', 'info');
        }
    },
    billhisfillDetailLabels: function (data) {
        $("#spot_bill_userid").text(data.userId);
        $("#spot_bill_createTime").text(moment(new Date(data.createTime)).format("YYYY-MM-DD HH:mm:ss"));
        $("#spot_bill_code").text(data.code);
        if (data.type == "1") {
            $("#spot_bill_billType").text("充值");
        } else if (data.type == "1") {
            $("#spot_bill_billType").text("提现");
        } else if (data.type == "2") {
            $("#spot_bill_billType").text("市价单");
        } else if (data.type == "7") {
            $("#spot_bill_billType").text("买入");
        } else if (data.type == "8") {
            $("#spot_bill_billType").text("卖出");
        } else if (data.type == "14") {
            $("#spot_bill_billType").text("活动派发");
        }
        $("#spot_bill_currency").text(data.currencyName);
        $("#spot_bill_size").text(data.size);
        $("#spot_bill_fee").text(data.fee);
        $("#spot_bill_afterBalance").text(data.afterBalance);

        if (data.tradeNo == "") {
            //$.messager.alert('提示', '没有找到TXID', 'info');
            return;
        }
        var url = UsersInfoMVC.URLs.billgetadress.url + "?traderNo=" + data.tradeNo;
        $.ajax({
            url: url,
            type: 'GET',
            data: '',
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                $("#spot_bill_adress").text(data.data.to);
                $("#spot_bill_txid").text(data.data.traderNo);
            }
        });
    },
    billfind: function () {
        var currencyCode = $("#bill-currency-name").textbox('getValue');
        var startDateMillis = $("#bill-startDateMillis").textbox('getValue');
        var endDateMillis = $("#bill-endDateMillis").textbox('getValue');
        var billType = $("#bill-type").combobox('getValue');
        var isHiisHistorystory = $("#bill-history").combobox('getValue');

        var params = {
            currencyCode: currencyCode,
            startDateMillis: startDateMillis,
            endDateMillis: endDateMillis,
            billType: billType,
            isHiisHistorystory: isHiisHistorystory
        };

        $('#bill-datagrid').datagrid('load', params);
    },
    loadOrder: function (gridUrl, userId) {
        var url = gridUrl + '?userId=' + userId;

        $('#order-datagrid').datagrid({
            method: 'get',
            fit: true,
            fitColumns: false,
            singleSelect: true,
            pagination: true,
            rownumbers: true,
            pageSize: 50,
            url: url,
            toolbar: [{
                text: '全部',
                iconCls: 'icon-reload',
                handler: function () {
                    $('#order-datagrid').datagrid('load', {});
                }
            }, '-', {
                text: '详情',
                iconCls: 'icon-info',
                handler: function () {
                    SpotBillUtils.ordermanagementshowDetail();
                }
            }],
            loadFilter: function (src) {
                if (!src.code) {
                    return src.data;
                }
                return $.messager.alert('失败', src.msg, 'error');
            },
            columns: [[{
                field: 'userId',
                title: '用户ID',
                width: 80,
                sortable: true
            }, {
                field: 'createdDate',
                title: '时间',
                width: 100,
                sortable: true,
                formatter: function (value, row, index) {
                    return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                }
            }, {
                field: 'code',
                title: '币对',
                width: 80,
                sortable: true
            }, {
                field: 'orderId',
                title: '订单Id',
                width: 100
            }, {
                field: 'orderType',
                title: '类型',
                width: 80,
                sortable: true,
                formatter: function (value, row, index) {
                    if (value == 'market') {
                        return "市价单";
                    } else {
                        return "限价单";
                    }
                }
            }, {
                field: 'side',
                title: '交易方向',
                width: 100,
                sortable: true,
                formatter: function (value, row, index) {
                    if (value == '1') {
                        return "买入";
                    } else if (value == '2') {
                        return "卖出";
                    } else {
                        return "";
                    }
                }
            }, {
                field: 'price',
                title: '委托价',
                width: 80,
                sortable: true
            }, {
                field: 'averagePrice',
                title: '平均成交价',
                width: 100
            }, {
                field: 'volume',
                title: '委托量',
                width: 100,
                sortable: true
            }, {
                field: 'filledVolume',
                title: '基准币已成交的数量',
                width: 100,
                sortable: true
            }, {
                field: 'executedVolume',
                title: '计价币已成交的数量',
                width: 100
            }, {
                field: 'source',
                title: '订单来源',
                width: 100,
                sortable: true
            }, {
                field: 'status',
                title: '订单状态',
                width: 100,
                formatter: function (value, row, index) {
                    if (value == "open") {
                        return "未成交";
                    } else if (value == "partially-filled") {
                        return "部分成交";
                    } else if (value == "filled") {
                        return "完全成交";
                    } else if (value == "cancel") {
                        return "撤单中";
                    } else if (value == "canceled") {
                        return "已撤单";
                    }
                }
            }]],
            onDblClickRow: function (rowIndex, rowData) {
                return SpotBillUtils.ordermanagementshowDetail();
            },
            onLoadSuccess: function () {
            }
        });
    },
    ordermanagementshowDetail: function () {
        SpotBillUtils.ordermanagementisRowSelected(function (row) {
            $('#order-dlg').dialog('open').dialog('center');

            SpotBillUtils.ordermanagementfillDetailLabels(row);
        });
    },
    ordermanagementisRowSelected: function (func) {
        var row = $('#order-datagrid').datagrid('getSelected');
        if (row) {
            func(row);
        } else {
            $.messager.alert('警告', '请选中一条记录!', 'info');
        }
    },
    ordermanagementfillDetailLabels: function (data) {
        $("#spot_order_userid").text(data.userId);
        $("#spot_order_createdata").text(moment(new Date(data.createdDate)).format("YYYY-MM-DD HH:mm:ss"));
        if (data.orderType == "limit") {
            $("#spot_order_orderType").text("限价单");
        } else {
            $("#spot_order_orderType").text("市价单");
        }
        $("#spot_order_price").text(data.price);
        $("#spot_order_code").text(data.code);
        $("#spot_order_volume").text(data.volume);
        $("#spot_order_source").text(data.source);
        $("#spot_order_filledVolume").text(data.filledVolume);
        $("#spot_order_averagePrice").text(data.averagePrice);
        if (data.status == "open") {
            $("#spot_order_status").text("未成交");
        } else if (data.status == "partially-filled") {
            $("#spot_order_status").text("部分成交");
        } else if (data.status == "filled") {
            $("#spot_order_status").text("完全成交");
        } else if (data.status == "cancel") {
            $("#spot_order_status").text("撤单中");
        } else if (data.status == "canceled") {
            $("#spot_order_status").text("已撤单");
        }
    },
    orderManagementFind: function () {
        var code = $("#order-currencyCode").combobox('getValue');
        var startDateMillis = $("#order-startDateMillis").textbox('getValue');
        var endDateMillis = $("#order-endDateMillis").textbox('getValue');
        var price = $("#order-price").textbox('getValue');
        var type = $("#order-type").combobox('getValue');
        var source = $("#order-source").combobox('getValue');

        var params = {
            code: code,
            startDateMillis: startDateMillis,
            endDateMillis: endDateMillis,
            price: price,
            type: type,
            source: source
        };

        $('#order-datagrid').datagrid('load', params);
    },
    loadHistory: function (gridUrl, userId) {
        var currencyCode = UsersInfoMVC.Model.currencyPairWithCode[0].value;
        console.log(currencyCode);
        var url = gridUrl + '?userId=' + userId;
        $('#his-datagrid').datagrid({
            method: 'get',
            fit: true,
            fitColumns: false,
            singleSelect: true,
            pagination: true,
            rownumbers: true,
            pageSize: 50,
            url: url,
            queryParams: {
                code: currencyCode,
                isHistory: false
            },
            toolbar: [{
                text: '全部',
                iconCls: 'icon-reload',
                handler: function () {
                    $('#his-datagrid').datagrid('load', {code: currencyCode, isHistory: false});
                }
            }, '-', {
                text: '详情',
                iconCls: 'icon-info',
                handler: function () {
                    SpotBillUtils.hisshowDetail();
                }
            }],
            loadFilter: function (src) {
                if (!src.code) {
                    return src.data;
                }
                return $.messager.alert('失败', src.msg, 'error');
            },
            columns: [[{
                field: 'userId',
                title: '用户ID',
                width: 80,
                sortable: true
            }, {
                field: 'createdDate',
                title: '时间',
                width: 100,
                sortable: true,
                formatter: function (value, row, index) {
                    return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                }
            }, {
                field: 'code',
                title: '币对',
                width: 80,
                sortable: true
            }, {
                field: 'orderId',
                title: '订单Id',
                width: 100
            }, {
                field: 'orderType',
                title: '类型',
                width: 80,
                sortable: true,
                formatter: function (value, row, index) {
                    if (value == 'market') {
                        return "市价单";
                    }

                    if (value == 'limit') {
                        return "限价单";
                    }

                    return '';
                }
            }, {
                field: 'side',
                title: '交易方向',
                width: 100,
                sortable: true,
                formatter: function (value, row, index) {
                    if (value == 'buy') {
                        return "买入";
                    }

                    if (value == 'sell') {
                        return "卖出";
                    }

                    return '';
                }
            }, {
                field: 'price',
                title: '委托价',
                width: 80,
                sortable: true,
            }, {
                field: 'averagePrice',
                title: '平均成交价',
                width: 100
            }, {
                field: 'volume',
                title: '委托量',
                width: 100,
                sortable: true
            }, {
                field: 'filledVolume',
                title: '基准币已成交的数量',
                width: 100,
                sortable: true
            }, {
                field: 'executedVolume',
                title: '计价币已成交的数量',
                width: 100
            }, {
                field: 'source',
                title: '订单来源',
                width: 100,
                sortable: true
            }, {
                field: 'status',
                title: '状态',
                width: 80,
                sortable: true,
                formatter: function (value, row, index) {
                    if (value == "open") {
                        return "未成交";
                    } else if (value == "partially-filled") {
                        return "部分成交";
                    } else if (value == "filled") {
                        return "完全成交";
                    } else if (value == "cancel") {
                        return "撤单中";
                    } else if (value == "canceled") {
                        return "已撤单";
                    }
                }
            }]],
            onDblClickRow: function (rowIndex, rowData) {
                return SpotBillUtils.hisshowDetail();
            },
            onLoadSuccess: function () {
            }
        });
    },
    hisshowDetail: function () {
        SpotBillUtils.isHisRowSelected(function (row) {
            $('#history-dlg').dialog('open').dialog('center');
            SpotBillUtils.HisfillDetailLabels(row);
        });
    },
    isHisRowSelected: function (func) {
        var row = $('#his-datagrid').datagrid('getSelected');
        if (row) {
            func(row);
        } else {
            $.messager.alert('警告', '请选中一条记录!', 'info');
        }
    },
    HisfillDetailLabels: function (data) {
        $("#spot_his_userid").text(data.userId);
        $("#spot_his_createdata").text(moment(new Date(data.createdDate)).format("YYYY-MM-DD HH:mm:ss"));

        $("#spot_his_code").text(data.code);
        if (data.orderType == "limit") {
            $("#spot_his_orderType").text("限价单");
        } else {
            $("#spot_his_orderType").text("市价单");
        }
        $("#spot_his_price").text(data.price);
        $("#spot_his_volume").text(data.volume);
        $("#spot_his_source").text(data.source);
        $("#spot_his_averagePrice").text(data.averagePrice);
        if (data.status == "open") {
            $("#spot_his_status").text("未成交");
        } else if (data.status == "partially-filled") {
            $("#spot_his_status").text("部分成交");
        } else if (data.status == "filled") {
            $("#spot_his_status").text("完全成交");
        } else if (data.status == "cancel") {
            $("#spot_his_status").text("撤单中");
        } else if (data.status == "canceled") {
            $("#spot_his_status").text("已撤单");
        }
        $("#spot_his_filledVolume").text(data.filledVolume);
    },
    historyfind: function () {
        var code = $("#his-currencyCode").textbox('getValue');
        var startDateMillis = $("#his_startDateMillis").textbox('getValue');
        var endDateMillis = $("#his_endDateMillis").textbox('getValue');
        var type = $("#his_type").combobox('getValue');
        var source = $("#his_source").combobox('getValue');
        var price = $("#his-price").textbox('getValue');
        var isHistory = $("#order-history").combobox('getValue');

        var params = {
            code: code,
            startDateMillis: startDateMillis,
            endDateMillis: endDateMillis,
            type: type,
            source: source,
            price: price,
            isHistory: isHistory
        };

        $('#his-datagrid').datagrid('load', params);
    }
};