$(function () {
    Order.init();
});

var Order = {
    init: function () {
        OrderMVC.View.initControl();
        OrderMVC.View.bindEvent();
        OrderMVC.View.bindValidate();
        OrderMVC.View.initData();
    }
};

var OrderCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/c2c/order/',
    baseRoleUrl: BossGlobal.ctxPath + '/v1/boss/c2c/order/',
    basecomboxUrl: BossGlobal.ctxPath + '/v1/boss/c2c/order/currencies',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/'
};

var OrderMVC = {
    URLs: {
        list: {
            url: OrderCommon.baseUrl + 'List',
            method: 'GET'
        },
        OrderDetail: {
            url: OrderCommon.baseUrl + 'getOrderDetail',
            method: 'GET'
        },
        remark: {
            url: OrderCommon.baseUrl + 'remark',
            method: 'POST'
        },
        freeze: {
            url: OrderCommon.baseUrl + 'freeze',
            method: 'POST'
        },
        unfreeze: {
            url: OrderCommon.baseUrl + 'unfreeze',
            method: 'POST'
        },
        cancel: {
            url: OrderCommon.baseUrl + 'cancel',
            method: 'POST'
        },
        finish: {
            url: OrderCommon.baseUrl + 'finish',
            method: 'POST'
        },
        combox: {
            url: OrderCommon.basecomboxUrl,
            method: 'GET'
        },
        getSellInfo: {
            url: OrderCommon.baseUrl + 'getSellInfo',
            method: 'GET'
        },
        getUserInfo: {
            url: OrderCommon.baseUrl + 'getUserInfo',
            method: 'GET'
        },
        getOrderStatistics: {
            url: OrderCommon.baseUrl + 'getOrderStatistics',
            method: 'GET'
        }
    },
    Model: {
        marginOpen: {},
        roles: {}

    },
    View: {
        initControl: function () {

            $("#order-digitalCurrencySymbol").combobox({
                url: OrderMVC.URLs.combox.url + "?type=0",
                valueField: "currencySymbol",
                textField: "currencySymbol",
                panelHeight: 300,
                editable: true,
                onSelect: function (record) {
                    $.tabIndex = $('.order-digitalCurrencySymbol').index(this);
                    $(".order-digitalCurrencySymbol").eq($.tabIndex).textbox("setValue", record.id)
                },
                onLoadSuccess: function () {
                    var val = $(this).combobox("getData");
                    var v = "";
                    for (var ii = 0; ii < val.length; ii++) {
                        v = v + "<div style=\"float:left;padding-left:20px;font-size: 16px;\">" + val[ii].currencySymbol.toLocaleUpperCase() + "服务费:" + val[ii].platformCommissionRate + "</div>     ";
                    }
                    v = v + "<div class=\"clear\"></div>";
                    $("#currencyList").html(v);
                }

            });

            $("#order-legalCurrencySymbol").combobox({
                url: OrderMVC.URLs.combox.url + "?type=1",
                valueField: "currencySymbol",
                textField: "currencySymbol",
                panelHeight: 300,
                editable: true,
                onSelect: function (record) {
                    $.tabIndex = $('.order-legalCurrencySymbol').index(this);
                    $(".order-legalCurrencySymbol").eq($.tabIndex).textbox("setValue", record.id)
                },
                onLoadSuccess: function () {
                }

            });

            $('#report-detail-dlg').dialog({
                closed: true,
                modal: true,
                striped: true,
                collapsible: true,
                resizable: true,
                width: 910,
                height: window.screen.height - 350,
                maximizable: true,
                iconCls: 'icon-info',
                buttons: [{
                    text: '添加备注',
                    iconCls: 'icon-add',
                    handler: OrderMVC.Controller.remark
                }, {
                    id: "FrozenBttOne",
                    text: '冻结',
                    iconCls: 'icon-no',
                    handler: OrderMVC.Controller.Frozen
                }, {
                    id: "ThawBttOne",
                    text: '解冻',
                    iconCls: 'icon-ok',
                    handler: OrderMVC.Controller.Thaw
                }, {
                    id: "cancelBttOne",
                    text: '取消订单',
                    iconCls: 'icon-no',
                    handler: OrderMVC.Controller.cancel
                }, {
                    id: "completeBttOne",
                    text: '完成',
                    iconCls: 'icon-ok',
                    handler: OrderMVC.Controller.complete
                }, {
                    text: '返回',
                    iconCls: 'icon-back',
                    handler: function () {
                        $("#report-detail-dlg").dialog('close');
                    }
                }]
            });
            $('#user-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                sortOrder: 'desc',
                pageSize: 50,
                url: OrderMVC.URLs.list.url,
                toolbar: [{
                    text: '全部数据',
                    iconCls: 'icon-reload',
                    handler: function () {
                        $("#user-datagrid").datagrid({
                            pageNumber: 1,
                            pageSize: 50,
                            url: OrderMVC.URLs.list.url
                        });
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
                        field: 'publicOrderId',
                        title: '订单ID',
                        width: 100,
                        sortable: true,

                    }
                    , {
                        field: 'digitalCurrencySymbol',
                        title: '币种类型',
                        width: 100,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return value.toUpperCase();
                        }

                    }, {
                        field: 'legalCurrencySymbol',
                        title: '法币类型',
                        width: 100,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return value.toUpperCase();
                        }
                    }, {
                        field: 'buyerId',
                        title: '买家ID',
                        width: 100,
                        sortable: true,

                    }
                    , {
                        field: 'sellerId',
                        title: '卖家ID',
                        width: 100,
                        sortable: true,

                    }, {
                        field: 'acceptorId',
                        title: '吃单类型',
                        width: 100,
                        sortable: true,
                        formatter: function (value, row, index) {
                            if (row.creatorId == row.sellerId) {
                                return "卖";
                            } else {
                                return "买";
                            }
                        }
                    }, {
                        field: 'exchangeRate',
                        title: '交易价格',
                        width: 100,
                        sortable: true,
                    }, {
                        field: 'buyerInAmount',
                        title: '交易数量',
                        width: 100,
                        sortable: true,
                    }, {
                        field: 'platformInAmount',
                        title: '平台服务费',
                        width: 100,
                        sortable: true,
                    }, {
                        field: 'receiptAccountType',
                        title: '转账类型',
                        width: 100,
                        sortable: true,
                        formatter: function (value, row, index) {
                            if (value == 1) {
                                return "银行转账";
                            }
                            else if (value == 2) {
                                return "支付宝转账";
                            }
                            else if (value == 3) {
                                return "微信转账";
                            }
                            else {
                                return "";
                            }
                        }
                    }, {
                        field: 'createdDate',
                        title: '订单创建时间',
                        width: 150,
                        sortable: true,
                    }
                    , {
                        field: 'paymentStatus',
                        title: '支付状态',
                        width: 100,
                        sortable: true,
                        formatter: function (value, row, index) {
                            if (value === 1) {
                                return "未付款";
                            }
                            else if (value === 2) {
                                return "已付款";
                            }
                            else if (value === 3) {
                                return "已确认";
                            }
                            else if (value === 4) {
                                return "已经驳回";
                            }
                            else {
                                return value;
                            }
                        }
                    }, {
                        field: 'isFrozen',
                        title: '冻结状态',
                        width: 100,
                        sortable: true,
                        formatter: function (value, row, index) {
                            if (value) {
                                return "冻结";
                            }
                            else {
                                return "未冻结";
                            }
                        }
                    }, {
                        field: 'orderStatus',
                        title: '订单状态',
                        width: 100,
                        sortable: true,
                        formatter: function (value, row, index) {
                            if (value === 1) {
                                return "新订单";
                            }
                            else if (value === 2) {
                                return "已取消";
                            }
                            else if (value === 3) {
                                return "已完成";
                            }
                            else {
                                return value;
                            }
                        }
                    }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return OrderMVC.Controller.showDetail();
                },
                onLoadSuccess: function () { //加载完成后,设置选中第一项
                    $('.pagination-page-list').hide();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });


            var pager = $("#user-datagrid").datagrid("getPager");
            if (pager) {
                $(pager).pagination({
                    onSelectPage: function (pageNumber, pageSize) {
                        var url = OrderMVC.Util.getSearchUrl();
                        $("#user-datagrid").datagrid({
                            pageNumber: pageNumber,
                            pageSize: pageSize,
                            url: url
                        });

                    }
                });
            }
        },
        bindEvent: function () {
            $('#btn-search').bind('click', OrderMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {
            OrderMVC.Util.getOrderCount();
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#user-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return OrderMVC.Controller.edit();
            }
        },
        showDetail: function () {
            OrderMVC.Util.isRowSelected(function (row) {
                $('#report-detail-dlg').dialog('open').dialog('center');
                //根据条件调用接口返回的数据
                // alert(row.id);
                OrderMVC.Util.fillDetailLabels(row);
            });
        },
        edit: function () {
            var row = $('#user-datagrid').datagrid('getSelected');
            if (row) {
                //id传给后台返回操作记录显示在前台
                var options = OrderMVC.Util.getOptions();
                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '审核[' + options.data.code + ']';
                EasyUIUtils.openEditDlg(options);

            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remark: function () {
            //添加备注
            var remarks = $("#remarks").textbox('getValue');
            if (remarks == "") {
                $.messager.alert('提示', '备注不能为空', 'info');
                return;
            } else {
                var url = OrderMVC.URLs.remark.url;
                var orderId = $("#orderId").val();
                var remarks = $("#remarks").textbox('getValue');
                if (orderId == "") {
                    $.messager.alert('提示', '没有找到订单ID', 'info');
                    return;
                }
                $.ajax({
                    url: url,
                    type: 'POST',
                    data: {orderId: orderId, remarks: remarks},
                    success: function (data) {
                        //那道操作记录展示在前台
                        if (data.msg == "success") {
                            $("#report-detail-dlg").dialog('close');
                            EasyUIUtils.reloadDatagrid('#user-datagrid');
                        } else {
                            $.messager.alert('提示', data.msg, 'info');
                        }

                    },
                    error: function (data) {
                        if (data.responseJSON.code == "403") {
                            $.messager.alert('提示', data.responseJSON.msg, 'info');

                        } else {
                            $.messager.alert('提示', 'error', 'info');
                        }
                    }
                });
            }
        },
        Thaw: function () {
            //解冻订单
            $.messager.confirm('请再次确认信息', '是否解冻当前订单？', function (r) {
                if (r) {
                    var url = OrderMVC.URLs.unfreeze.url;
                    var remarks = $("#remarks").textbox('getValue');
                    var orderId = $("#orderId").val();
                    if (orderId == "") {
                        $.messager.alert('提示', '没有找到订单ID', 'info');
                        return;
                    }
                    $.ajax({
                        url: url,
                        type: 'POST',
                        data: {type: 1, orderId: orderId, remarks: remarks},
                        success: function (data) {
                            //那道操作记录展示在前台
                            if (data.msg == "success") {
                                $("#report-detail-dlg").dialog('close');
                                EasyUIUtils.reloadDatagrid('#user-datagrid');
                            } else {
                                $.messager.alert('提示', data.msg, 'info');
                            }

                        },
                        error: function (data) {
                            if (data.responseJSON.code == "403") {
                                $.messager.alert('提示', data.responseJSON.msg, 'info');

                            } else {
                                $.messager.alert('提示', 'error', 'info');
                            }
                        }
                    });

                }
            });
        },
        Frozen: function () {
            //冻结订单
            $.messager.confirm('请再次确认信息', '是否冻结当前订单？', function (r) {
                if (r) {
                    var url = OrderMVC.URLs.freeze.url;
                    var remarks = $("#remarks").textbox('getValue');
                    var orderId = $("#orderId").val();
                    if (orderId == "") {
                        $.messager.alert('提示', '没有找到订单ID', 'info');
                        return;
                    }
                    $.ajax({
                        url: url,
                        type: 'POST',
                        data: {type: 2, orderId: orderId, remarks: remarks},
                        success: function (data) {
                            //那道操作记录展示在前台
                            if (data.msg == "success") {
                                $("#report-detail-dlg").dialog('close');
                                EasyUIUtils.reloadDatagrid('#user-datagrid');
                            } else {
                                $.messager.alert('提示', data.msg, 'info');
                            }
                        },
                        error: function (data) {
                            if (data.responseJSON.code == "403") {
                                $.messager.alert('提示', data.responseJSON.msg, 'info');

                            } else {
                                $.messager.alert('提示', 'error', 'info');
                            }
                        }
                    });

                }
            });
        },
        cancel: function () {
            //取消订单
            $.messager.confirm('请再次确认信息', '是否取消当前订单？', function (r) {
                if (r) {
                    var url = OrderMVC.URLs.cancel.url;
                    var remarks = $("#remarks").textbox('getValue');
                    var orderId = $("#orderId").val();
                    if (orderId == "") {
                        $.messager.alert('提示', '没有找到订单ID', 'info');
                        return;
                    }
                    $.ajax({
                        url: url,
                        type: 'POST',
                        data: {type: 3, orderId: orderId, remarks: remarks},
                        success: function (data) {
                            //那道操作记录展示在前台
                            if (data.msg == "success") {
                                $("#report-detail-dlg").dialog('close');
                                EasyUIUtils.reloadDatagrid('#user-datagrid');
                            } else {
                                $.messager.alert('提示', data.msg, 'info');
                            }

                        },
                        error: function (data) {
                            if (data.responseJSON.code == "403") {
                                $.messager.alert('提示', data.responseJSON.msg, 'info');

                            } else {
                                $.messager.alert('提示', 'error', 'info');
                            }
                        }
                    });

                }
            });
        },
        complete: function () {
            $.messager.confirm('请再次确认信息', '是否完成当前订单？', function (r) {
                if (r) {
                    //完成订单
                    var url = OrderMVC.URLs.finish.url;
                    var remarks = $("#remarks").textbox('getValue');
                    var orderId = $("#orderId").val();
                    if (orderId == "") {
                        $.messager.alert('提示', '没有找到订单ID', 'info');
                        return;
                    }
                    $.ajax({
                        url: url,
                        type: 'POST',
                        data: {type: 4, orderId: orderId, remarks: remarks},
                        success: function (data) {
                            //那道操作记录展示在前台
                            if (data.msg == "success") {
                                $("#report-detail-dlg").dialog('close');
                                EasyUIUtils.reloadDatagrid('#user-datagrid');
                            } else {
                                $.messager.alert('提示', data.msg, 'info');
                            }

                        },
                        error: function (data) {
                            if (data.responseJSON.code == "403") {
                                $.messager.alert('提示', data.responseJSON.msg, 'info');

                            } else {
                                $.messager.alert('提示', 'error', 'info');
                            }
                        }
                    });

                }
            });

        },
        find: function () {
            var url = OrderMVC.Util.getSearchUrl();
            $("#user-datagrid").datagrid({
                pageNumber: 1,
                pageSize: 50,
                url: url
            });

        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#user-dlg',
                formId: '#user-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                gridId: null,
            };
        },
        getSearchUrl: function () {
            var orderId = $("#order-publicOrderId").textbox('getValue');
            var buy = $("#order-buy").textbox('getValue');
            var sell = $("#order-sell").textbox('getValue');
            var digitalCurrencySymbol = $("#order-digitalCurrencySymbol").combobox('getValue');
            var legalCurrencySymbol = $("#order-legalCurrencySymbol").combobox('getValue');
            var paymentType = $("#order-paymentType").combobox('getValue');
            var orderStatus = $("#order-orderStatus").combobox('getValue');
            var paymentStatus = $("#order-paymentStatus").combobox('getValue');
            var freezeType = $("#order-freezeType").combobox('getValue');
            var beginTime = $("#beginTime").datebox('getValue');
            var endTime = $("#endTime").datebox('getValue');

            return url = OrderMVC.URLs.list.url + '?orderId=' + orderId +
                '&buy=' + buy + '&sell=' + sell + '&digitalCurrencySymbol=' + digitalCurrencySymbol + '&legalCurrencySymbol=' + legalCurrencySymbol +
                '&paymentType=' + paymentType + '&orderStatus=' + orderStatus +
                '&paymentStatus=' + paymentStatus + '&freezeType=' + freezeType + '&beginTime=' + beginTime + '&endTime=' + endTime;
        },
        getOrderCount: function () {
            $.ajax({
                url: OrderMVC.URLs.getOrderStatistics.url,
                type: 'GET',
                data: '',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (data) {
                    // console.log(data);
                    $("#report-detail-allOrderCount").text(data.data.allOrderCount);
                    $("#report-detail-cancelledOrderCount").text(data.data.cancelledOrderCount);
                    $("#report-detail-completedOrderCount").text(data.data.completedOrderCount);
                    $("#report-detail-frozenCount").text(data.data.frozenCount);

                },
                error: function (data) {
                    if (data.responseJSON.code == "403") {
                        $.messager.alert('提示', data.responseJSON.msg, 'info');

                    } else {
                        $.messager.alert('提示', 'error', 'info');
                    }
                }
            });
        },
        isRowSelected: function (func) {
            var row = $('#user-datagrid').datagrid('getSelected');
            if (row) {
                func(row);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        fillDetailLabels: function (data) {


            $("#orderId").text("");
            $("#remarks").textbox('setValue', "");
            $("#remarkslist").text("");
            $("#report-detail-tradingOrderId").text("");
            $("#report-detail-type").text("");
            $("#report-detail-paymentStatus").text("");
            $("#report-detail-receiptAccountType").text("");
            $("#report-detail-orderTotal").text("");
            $("#report-detail-exchangeRate").text("");
            $("#report-detail-buyerInAmount").text("");
            $("#report-detail-platformInAmount").text("");
            $("#report-detail-paidDate").text("");
            $("#report-detail-isFrozen").text("");
            $("#report-detail-modifyDate").text("");
            $("#report-detail-dispatch").text("");
            $("#report-detail-completeDate").text("");
            $("#report-detail-bankCard").text("");
            $("#report-detail-accountOpeningBank").text("");
            $("#report-detail-bankCardNumber").text("");
            $("#report-detail-buyName").text("");
            $("#report-detail-buyMobile").text("");
            $("#report-detail-sellName").text("");
            $("#report-detail-sellMobile").text("");
            $("#imglist").attr("style", "display:none;");
            $("#imgrow").html("");

            $("#FrozenBttOne").hide();
            $("#ThawBttOne").hide();
            $("#cancelBttOne").hide();
            $("#completeBttOne").hide();


            var orderId = data.id;
            var url = OrderMVC.URLs.OrderDetail.url + "?orderId=" + orderId;
            $.ajax({
                url: url,
                type: 'GET',
                data: '',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (data) {
                    //那道操作记录展示在前台
                    //console.log(data);

                    if (data.data.order.orderStatus == 1) {
                        if (data.data.order.paymentStatus == 2) {
                            $("#cancelBttOne").show();
                            $("#completeBttOne").show();
                            if (data.data.order.isFrozen) {
                                $("#ThawBttOne").show();
                            } else {
                                $("#FrozenBttOne").show();
                            }
                        }
                        if (data.data.order.paymentStatus == 1) {
                            $("#cancelBttOne").show();
                            if (data.data.order.isFrozen) {
                                $("#ThawBttOne").show();
                            } else {
                                $("#FrozenBttOne").show();
                            }
                        }
                        if (data.data.order.paymentStatus == 3) {
                            $("#cancelBttOne").show();
                            $("#completeBttOne").show();

                        }
                        if (data.data.order.paymentStatus == 4) {
                            $("#cancelBttOne").show();
                            $("#completeBttOne").show();

                        }
                    }


                    $("#orderId").val(data.data.order.id);

                    if (data.data.order.paymentStatus == 1) {
                        $("#report-detail-paymentStatus").text("未付款");
                    } else if (data.data.order.paymentStatus == 2) {
                        $("#report-detail-paymentStatus").text("已付款");
                    } else if (data.data.order.paymentStatus == 3) {
                        $("#report-detail-paymentStatus").text("已确认");
                    } else if (data.data.order.paymentStatus == 4) {
                        $("#report-detail-paymentStatus").text("已经驳回");
                    }

                    if (data.data.order.orderStatus == 1) {
                        $("#report-detail-type").text("新订单");
                    } else if (data.data.order.orderStatus == 2) {
                        $("#report-detail-type").text("已取消");
                    } else if (data.data.order.orderStatus == 3) {
                        $("#report-detail-type").text("已完成");
                    }

                    $("#report-detail-tradingOrderId").text(data.data.order.publicOrderId);

                    if (data.data.order.receiptAccountType == 1) {
                        $("#report-detail-receiptAccountType").text("银行转账");
                        //根据买家sellid 查询 银行卡信息
                        var sellInfo = OrderMVC.URLs.getSellInfo.url + "?sellerId=" + data.data.order.sellerId;

                        $.ajax({
                            url: sellInfo,
                            type: 'GET',
                            data: '',
                            async: false,
                            cache: false,
                            contentType: false,
                            processData: false,
                            success: function (data) {
                                // console.log(data);
                                for (var i = 0; i < data.length; i++) {
                                    if (data[i].payType == 1) {
                                        $("#report-detail-bankCard").text(data[i].userName);
                                        $("#report-detail-accountOpeningBank").text(data[i].bankName);
                                        $("#report-detail-bankCardNumber").text(data[i].bankCard);
                                    }
                                }

                            },
                            error: function (data) {
                                if (data.responseJSON.code == "403") {
                                    $.messager.alert('提示', data.responseJSON.msg, 'info');

                                } else {
                                    $.messager.alert('提示', 'error', 'info');
                                }
                            }
                        });
                    }
                    if (data.data.order.receiptAccountType == 2) {
                        $("#report-detail-receiptAccountType").text("支付宝转账");
                    }
                    if (data.data.order.receiptAccountType == 3) {
                        $("#report-detail-receiptAccountType").text("微信转账");
                    }


                    $("#report-detail-orderTotal").text(data.data.order.orderTotal);
                    $("#report-detail-exchangeRate").text(data.data.order.exchangeRate);
                    $("#report-detail-buyerInAmount").text(data.data.order.buyerInAmount);
                    $("#report-detail-platformInAmount").text(data.data.order.platformInAmount);
                    $("#report-detail-paidDate").text(data.data.order.paidDate);


                    if (data.data.order.isFrozen) {
                        $("#report-detail-isFrozen").text("已冻结");
                    } else {
                        $("#report-detail-isFrozen").text("未冻结");
                    }

                    //判断订单已完成后现实找个字段，否则空,否则为放币
                    if (data.data.order.orderStatus == 3) {
                        $("#report-detail-modifyDate").text(data.data.order.modifyDate);
                        $("#report-detail-dispatch").text("已放币");
                        $("#report-detail-completeDate").text(data.data.order.completeDate);
                    } else {
                        $("#report-detail-modifyDate").text("");
                        $("#report-detail-dispatch").text("未放币");
                        $("#report-detail-completeDate").text("");
                    }


                    if (data.data.order.buyerId != "") {
                        var userInfo = OrderMVC.URLs.getUserInfo.url + "?UserId=" + data.data.order.buyerId;
                        $.ajax({
                            url: userInfo,
                            type: 'GET',
                            data: '',
                            async: false,
                            cache: false,
                            contentType: false,
                            processData: false,
                            success: function (data) {
                                $("#report-detail-buyName").text(data.data.realName);
                                if (data.data.email != "") {
                                    $("#report-detail-buyMobile").text(data.data.email);
                                } else {
                                    $("#report-detail-buyMobile").text(data.data.mobile);
                                }

                            }
                        });
                    }

                    if (data.data.order.sellerId != "") {
                        var userInfo = OrderMVC.URLs.getUserInfo.url + "?UserId=" + data.data.order.sellerId;
                        $.ajax({
                            url: userInfo,
                            type: 'GET',
                            data: '',
                            async: false,
                            cache: false,
                            contentType: false,
                            processData: false,
                            success: function (data) {
                                $("#report-detail-sellName").text(data.data.realName);
                                if (data.data.email != "") {
                                    $("#report-detail-sellMobile").text(data.data.email);
                                } else {
                                    $("#report-detail-sellMobile").text(data.data.mobile);
                                }

                            }
                        });
                    }


                    //循环展示备注详情
                    var remark = "";
                    for (var i = 0; i < data.data.noteList.length; i++) {
                        if (data.data.noteList[i].type == 1 || data.data.noteList[i].type == 6 || data.data.noteList[i].type == 7) {
                            var text = "";
                            if (data.data.noteList[i].type == 1) {
                                var notedata = JSON.parse(data.data.noteList[i].note);
                                var isfreeze = "无";
                                text = "操作人：" + notedata.userName + "    状态：" + isfreeze + "    备注：" + notedata.note + "　　时间：" + data.data.noteList[i].createdDate;
                            }
                            if (data.data.noteList[i].type == 6) {
                                var notedata = JSON.parse(data.data.noteList[i].note);
                                var isfreeze;
                                if (notedata.isFreeze) {
                                    isfreeze = "冻结"
                                } else {
                                    isfreeze = "解冻"
                                }

                                text = "操作人：" + notedata.userName + "    状态：" + isfreeze + "    备注：" + notedata.note + "　　时间：" + data.data.noteList[i].createdDate;
                            }
                            if (data.data.noteList[i].type == 7) {
                                var notedata = JSON.parse(data.data.noteList[i].note);
                                var isfreeze;
                                if (notedata.type == 2) {
                                    isfreeze = "完成"
                                } else {
                                    isfreeze = "取消"
                                }

                                text = "操作人：" + notedata.userName + "    状态：" + isfreeze + "    备注：" + notedata.note + "　　时间：" + data.data.noteList[i].createdDate;
                            }
                            remark = remark + text + "<br/>";
                            $("#remarkslist").html(remark);
                        }
                        if (data.data.noteList[i].type == 5) {
                            //循环增加审核图片
                            $("#imglist").attr("style", "display:block;");
                            var imgdata = JSON.parse(data.data.noteList[i].note);
                            var v = "";
                            for (var ii = 0; ii < imgdata.length; ii++) {
                                v = v + "<img id=\"img" + ii + "\" src=\"" + imgdata[ii] + "\" onclick=\"imgOpen(this);\" width=\"200\" height=\"200\">";
                            }
                            $("#imgrow").html(v);
                        }


                    }


                },
                error: function (data) {
                    if (data.responseJSON.code == "403") {
                        $.messager.alert('提示', data.responseJSON.msg, 'info');

                    } else {
                        $.messager.alert('提示', 'error', 'info');
                    }
                }
            });

        }
    }
};