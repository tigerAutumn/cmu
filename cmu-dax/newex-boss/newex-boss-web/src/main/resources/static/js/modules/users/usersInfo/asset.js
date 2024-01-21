var AssetUtils = {
    loadSpotAsset: function (gridUrl, userId) {
        var url = gridUrl + "?userid=" + userId;

        $('#asse-datagrid').datagrid({
            method: 'get',
            fit: true,
            autoRowWidth: true,
            fitColumns: true,
            singleSelect: true,
            pagination: false,
            rownumbers: true,
            sortOrder: 'desc',
            pageSize: 50,
            pageNumber: 1,
            url: url,
            toolbar: [{
                iconCls: 'icon-reload',
                handler: function () {
                    $('#asse-datagrid').datagrid('load', {});
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
                width: 80,
                sortable: true
            }, {
                field: 'sum',
                title: '余额',
                width: 200,
                sortable: true,
                formatter: function (value, row, index) {
                    value = parseFloat(row.available) + parseFloat(row.hold);
                    return value;
                }
            }, {
                field: 'available',
                title: '可用',
                width: 80,
                sortable: true
            }, {
                field: 'hold',
                title: '冻结',
                width: 80,
                sortable: true
            }, {
                field: 'BTC',
                title: 'BTC估值',
                width: 80,
                sortable: true
            }, {
                field: 'lock',
                title: '锁仓',
                width: 80,
                sortable: true
            }]],
            onDblClickRow: function (rowIndex, rowData) {
            }
        });

    },
    loadC2cAsset: function (gridUrl, userId) {
        var url = gridUrl + userId + "/info";

        $('#c2c-info').datagrid({
            method: 'get',
            fit: true,
            autoRowWidth: true,
            fitColumns: true,
            singleSelect: true,
            pagination: false,
            rownumbers: true,
            sortOrder: 'desc',
            pageSize: 50,
            pageNumber: 1,
            url: url,
            toolbar: [{
                iconCls: 'icon-reload',
                handler: function () {
                    $('#c2c-info').datagrid('load', {});
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
                    width: 80,
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
            },
            onLoadSuccess: function () {
            },
            onLoadError: function (data) {
                return $.messager.alert('失败', data.responseJSON.msg, 'error');
            }
        });

    },
    loadC2cAsset: function (gridUrl, userId) {
        var url = gridUrl + userId + "/info";

        $('#c2c-info').datagrid({
            method: 'get',
            fit: true,
            autoRowWidth: true,
            fitColumns: true,
            singleSelect: true,
            pagination: false,
            rownumbers: true,
            sortOrder: 'desc',
            pageSize: 50,
            pageNumber: 1,
            url: url,
            toolbar: [{
                iconCls: 'icon-reload',
                handler: function () {
                    $('#c2c-info').datagrid('load', {});
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
                    width: 80,
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
            },
            onLoadSuccess: function () {
            },
            onLoadError: function (data) {
                return $.messager.alert('失败', data.responseJSON.msg, 'error');
            }
        });

    },

    loadContractAssert: function (gridUrl, userId) {
        var url = gridUrl + "/" + userId;

        $('#contract-info').datagrid({
            method: 'get',
            fit: true,
            autoRowWidth: true,
            fitColumns: true,
            singleSelect: true,
            pagination: false,
            rownumbers: true,
            sortOrder: 'desc',
            pageSize: 50,
            pageNumber: 1,
            url: url,
            toolbar: [{
                iconCls: 'icon-reload',
                handler: function () {
                    $('#contract-info').datagrid('load', {});
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
                    field: 'currencyCode',
                    title: '币种',
                    width: 80,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return value.toUpperCase();
                    }

                },
                {
                    field: 'env',
                    title: '是否测试币',
                    width: 100,
                    sortable: true
                },
                {
                    field: 'totalBalance',
                    title: '总资产',
                    width: 100,
                    sortable: true

                },
                {
                    field: 'realizedSurplus',
                    title: '总已实现盈余',
                    width: 100,
                    sortable: true

                },
                {
                    field: 'unrealizedSurplus',
                    title: '总未实现盈余',
                    width: 100,
                    sortable: true
                },
                {
                    field: 'brokerId',
                    title: '券商ID',
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

    }
};