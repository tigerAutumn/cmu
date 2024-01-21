$(function () {
    TradingOrder.init();
});

var TradingOrder = {
    init: function () {
        TradingOrderMVC.View.initControl();
        TradingOrderMVC.View.bindEvent();
        TradingOrderMVC.View.bindValidate();
    }
};

var TradingOrderCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/c2c/trading-order/',
    imageUrl: BossGlobal.ctxPath + '/v1/boss/upload/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/'
};

var TradingOrderMVC = {
    URLs: {
        list: {
            url: TradingOrderCommon.baseUrl + 'list',
            method: 'GET'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {

            $('#trading-order-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: TradingOrderMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#trading-order-datagrid').datagrid('load', {});
                    }
                }],
                loadFilter: function (src) {
                    if (!src.code) {
                        return src.data;
                    }
                    //return $.messager.alert('失败', src.msg, 'error');
                    return {total: 0, rows: []};
                },
                columns: [[
                    {
                        field: 'userId',
                        title: '用户ID',
                        width: 100
                    }, {
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
                    }, {
                        field: 'frozenAmount',
                        title: '已冻结数量',
                        width: 100,
                        sortable: true
                    }, {
                        field: 'depositAmount',
                        title: '保证金',
                        width: 100,
                        sortable: true
                    }, {
                        field: 'completedOrderQuantity',
                        title: '已完成单数',
                        width: 100,
                        sortable: true
                    }, {
                        field: 'completedOrderTotal',
                        title: '已完成订单金额',
                        width: 100,
                        sortable: true
                    }, {
                        field: 'completedOrderRate',
                        title: '完成率',
                        width: 100,
                        sortable: true
                    }, {
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
                        field: 'paymentElapsedSecondsAvg',
                        title: '平均付款时间',
                        width: 100,
                        sortable: true

                    }, {
                        field: 'completeElapsedSecondsAvg',
                        title: '平均放币时间',
                        width: 100,
                        sortable: true
                    }, {
                        field: 'createdDate',
                        title: '创建时间',
                        width: 100,
                        formatter: function (val, row, index) {
                            return moment(new Date(val)).format("YYYY-MM-DD HH:mm:ss")
                        }
                    }
                ]],
                onDblClickRow: function (rowIndex, rowData) {
                },
                onLoadError: function (data) {
                    //return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

        },
        bindEvent: function () {
            $('#btn-search').bind('click', TradingOrderMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#trading-order-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return TradingOrderMVC.Controller.edit();
            }
        },
        find: function () {
            var params = TradingOrderMVC.Util.getQueryParams();

            $('#trading-order-datagrid').datagrid('load', params);
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#trading-order-dlg',
                formId: '#trading-order-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                url: null,
                gridId: '#trading-order-datagrid',
                gridUrl: TradingOrderMVC.URLs.list.url
            };
        },
        getQueryParams: function () {
            var userId = $("#query-userId").textbox("getValue");
            var postId = $("#query-postId").textbox("getValue");
            var postType = $("#query-postType").combobox("getValue");
            var status = $("#query-status").combobox("getValue");

            var params = {
                userId: userId,
                postId: postId,
                postType: postType,
                status: status
            };

            return params;
        }
    }
};