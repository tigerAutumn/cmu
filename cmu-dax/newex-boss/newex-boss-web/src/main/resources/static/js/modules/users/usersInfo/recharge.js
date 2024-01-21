var RechargeUtils = {
    loadAllCurrencyForAddress: function (currencies) {
        $("#symbol-name").combobox({
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
    loadAllCurrencyForRecord: function (currencies) {
        $("#symbol-record-name").combobox({
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
    loadAddress: function (gridUrl, userId, currencies) {
        var url = gridUrl + '?userId=' + userId + '&type=1';

        $('#RedrawMoney-datagrid').datagrid({
            method: 'get',
            fit: true,
            fitColumns: true,
            singleSelect: true,
            pagination: false,
            rownumbers: true,
            pageSize: 50,
            url: url,
            toolbar: [{
                iconCls: 'icon-reload',
                handler: function () {
                    $('#RedrawMoney-datagrid').datagrid('load', {});
                }
            }],
            loadFilter: function (src) {
                if (!src.code) {
                    return src.data;
                }
                return $.messager.alert('失败', src.msg, 'error');
            },
            columns: [[{
                field: 'currency',
                title: '币种',
                width: 150,
                sortable: true,
                formatter: function (value, row, index) {
                    for (var i = 0; i < currencies.length; i++) {
                        if (currencies[i].id === value) {
                            return currencies[i].symbol;
                        }
                    }
                }
            }, {
                field: 'address',
                title: '充值地址',
                width: 150,
                sortable: true
            }]],
            onDblClickRow: function (rowIndex, rowData) {

            },
            onLoadError: function (data) {
                return $.messager.alert('失败', data.responseJSON.msg, 'error');
            }
        });
    },
    RedrawMoney: function () {
        var symbol = $("#symbol-name").combobox('getValue');

        var params = {
            symbol: symbol
        };

        $('#RedrawMoney-datagrid').datagrid('load', params);
    },
    loadRecords: function (gridUrl, userId, currencies) {
        var url = gridUrl + "?userId=" + userId + "&transferType=deposit";

        $('#RedrawMoney-record-datagrid').datagrid({
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
                    $('#RedrawMoney-record-datagrid').datagrid('load', {});
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
                    field: 'address',
                    title: '充值地址',
                    width: 100,
                },
                {
                    field: 'amount',
                    title: '金额',
                    width: 100,
                },
                {
                    field: 'fee',
                    title: '手续费',
                    width: 100,
                },
                {
                    field: 'confirmation',
                    title: '确认数',
                    width: 100,
                },
                {
                    field: 'currency',
                    title: '币种',
                    width: 100,
                    formatter: function (value, row, index) {
                        for (var i = 0; i < currencies.length; i++) {
                            if (currencies[i].id === value) {
                                return currencies[i].symbol;
                            }
                        }
                    }
                },
                {
                    field: 'transferType',
                    title: '交易类型',
                    width: 100,
                    formatter: function (value, row, index) {
                        return "充值";
                    }
                },
                {
                    field: 'status',
                    title: '状态',
                    width: 100,
                    formatter: function (value, row, index) {
                        if (value == '3') {
                            return "已确认";
                        }
                        else {
                            return "未确认";
                        }
                    }
                },
                {
                    field: 'createDate',
                    title: '创建时间',
                    width: 100,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss");
                    }
                }
            ]],
            onDblClickRow: function () {
            },
            onLoadSuccess: function () {
            },
            onLoadError: function (data) {
                return $.messager.alert('失败', data.responseJSON.msg, 'error');
            }
        });

    },
    RedrawMoneyRecord: function () {
        var symbol = $("#symbol-record-name").combobox('getValue');
        var startTime = $("#RedrawMoney_startDateMillis").textbox('getValue');
        var endTime = $("#RedrawMoney_endDateMillis").textbox('getValue');

        var params = {
            symbol: symbol,
            startTime: startTime,
            endTime: endTime
        };

        $('#RedrawMoney-record-datagrid').datagrid('load', params);
    }

};