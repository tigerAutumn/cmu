var C2cBillUtils = {
    loadOptionType: function () {
        $.get(UsersInfoMVC.URLs.optionType.url, "", function (_datax) {
            _datax.unshift({
                'code': '',
                'description': '全部'
            });//向json数组开头添加自定义数据

            $("#legalTender").combobox({
                data: _datax,
                valueField: "code",
                textField: "description",
                onLoadSuccess: function () { //加载完成后,设置选中第一项
                    var allData = $(this).combobox("getData");
                    if (allData) {
                        $(this).combobox("select", allData[0]["code"]);
                    }
                }
            });

        });
    },
    loadAllCurrency: function () {
        $("#currency").combobox({
            data: UsersInfoMVC.Model.allC2CCurrencyWithId,
            valueField: "value",
            textField: "text",
            onLoadSuccess: function () { //加载完成后,设置选中第一项
                var allData = $(this).combobox("getData");
                if (allData) {
                    $(this).combobox("select", allData[0]["value"]);
                }
            }
        });

        $("#currency2").combobox({
            data: UsersInfoMVC.Model.allC2CCurrencyWithCode,
            valueField: "value",
            textField: "text",
            onLoadSuccess: function () { //加载完成后,设置选中第一项
                var allData = $(this).combobox("getData");
                if (allData) {
                    $(this).combobox("select", allData[0]["value"]);
                }
            }
        });
    },
    loadBill: function (gridUrl, userId) {
        var url = gridUrl + userId + "/userBill";

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
            url: url,
            toolbar: [{
                text: '全部',
                iconCls: 'icon-reload',
                handler: function () {
                    $('#c2c-bill').datagrid('load', {});
                }
            }, '-', {
                text: '详情',
                iconCls: 'icon-info',
                handler: function () {
                    C2cBillUtils.billInfo();
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
                return C2cBillUtils.billInfo();
            },
            onLoadSuccess: function () {
            },
            onLoadError: function (data) {
                return $.messager.alert('失败', data.responseJSON.msg, 'error');
            }
        });
    },
    billInfo: function () {
        C2cBillUtils.C2CisRowSelected(function (row) {
            $('#bill-detail-dlg').dialog('open').dialog('center');

            var tradeNo = row.tradeNo;
            var url = '';
            if (tradeNo) {
                url = UsersInfoMVC.URLs.C2cSearch.url + tradeNo + "/bill";
            }

            C2cBillUtils.C2CfillDetailLabels(url, row);
        });
    },
    C2CfillDetailLabels: function (url, data) {
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

        if (url) {
            $.getJSON(url, function (r) {
                var dataT = r.data;
                $("#bill-tradeNo").text(dataT.traderNo);
                $("#bill-to").text(dataT.to);
                $("#bill-fee").text(dataT.fee);
                $("#bill-real").text(moment(new Date(dataT.updateDate)).format('YYYY-MM-DD HH:mm:ss'));
            });
        }
    },
    C2CisRowSelected: function (func) {
        var row = $('#c2c-bill').datagrid('getSelected');
        if (row) {
            func(row);
        } else {
            $.messager.alert('警告', '请选中一条记录!', 'info');
        }
    },
    findBill: function () {
        var currency = $("#currency").combobox('getValue');
        var legalTender = $("#legalTender").combobox('getValue');

        var params = {
            currency: currency,
            legalTender: legalTender
        };

        $('#c2c-bill').datagrid('load', params);
    },
    loadPost: function (gridUrl, userId) {
        var url = gridUrl + '?userId=' + userId;

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
            url: url,
            toolbar: [{
                text: '全部',
                iconCls: 'icon-reload',
                handler: function () {
                    $('#c2c-post').datagrid('load', {});
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
                    width: 120
                }, {
                    field: 'digitalCurrencySymbol',
                    title: '交易币种',
                    width: 100
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
    findPost: function () {
        var postType = $("#postType").combobox('getValue');
        var postId = $("#postId").textbox('getValue');
        var status = $("#postStatus").combobox('getValue');

        var params = {
            postType: postType,
            postId: postId,
            status: status
        };

        $('#c2c-post').datagrid('load', params);
    },
    loadOrder: function (gridUrl, userId) {
        var url = gridUrl + userId + "/order";

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
            url: url,
            toolbar: [{
                text: '全部',
                iconCls: 'icon-reload',
                handler: function () {
                    $('#c2c-order').datagrid('load', {});
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
            },
            onLoadSuccess: function () {
            },
            onLoadError: function (data) {
                return $.messager.alert('失败', data.responseJSON.msg, 'error');
            }
        });
    },
    findOrder: function () {
        var orderId = $("#orderId").textbox('getValue');
        var currency = $("#currency2").combobox('getValue');
        var payType = $("#payType2").combobox('getValue');

        var params = {
            orderId: orderId,
            currency: currency,
            payType: payType
        };

        $('#c2c-order').datagrid('load', params);
    }
};