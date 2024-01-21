$(function () {
    PortfolioBill.init();
});

var PortfolioBill = {
    init: function () {
        PortfolioBillMVC.View.initControl();
        PortfolioBillMVC.View.bindEvent();
        PortfolioBillMVC.View.bindValidate();
    }
};

var PortfolioBillCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/portfolio/bill/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/'
};

var PortfolioBillMVC = {
    URLs: {
        purch: {
            url: PortfolioBillCommon.baseUrl + 'purch',
            method: 'GET'
        },
        redeem: {
            url: PortfolioBillCommon.baseUrl + 'redeem',
            method: 'GET'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {

            $('#purch-bill-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: PortfolioBillMVC.URLs.purch.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#purch-bill-datagrid').datagrid('load', {});
                    }
                }],
                loadFilter: function (src) {
                    if (!src.code) {
                        return src.data;
                    }
                    return $.messager.alert('失败', src.msg, 'error');
                },
                columns: [[{
                    field: 'id',
                    title: 'ID',
                    width: 50,
                    sortable: true
                }, {
                    field: 'name',
                    title: '时间',
                    width: 100,
                    sortable: true
                }, {
                    field: 'name',
                    title: '类型',
                    width: 100,
                    sortable: true
                }, {
                    field: 'name',
                    title: '金额(USDT)',
                    width: 100,
                    sortable: true
                }, {
                    field: 'name',
                    title: '份数',
                    width: 100,
                    sortable: true
                }, {
                    field: 'name',
                    title: '手续费(份)',
                    width: 100,
                    sortable: true
                }, {
                    field: 'createdDate',
                    title: '创建时间',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                    }
                }, {
                    field: 'updatedDate',
                    title: '修改时间',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {

                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            $('#redeem-bill-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: PortfolioBillMVC.URLs.redeem.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#redeem-bill-datagrid').datagrid('load', {});
                    }
                }],
                loadFilter: function (src) {
                    if (!src.code) {
                        return src.data;
                    }
                    return $.messager.alert('失败', src.msg, 'error');
                },
                columns: [[{
                    field: 'id',
                    title: 'ID',
                    width: 50,
                    sortable: true
                }, {
                    field: 'name',
                    title: '时间',
                    width: 100,
                    sortable: true
                }, {
                    field: 'name',
                    title: '类型',
                    width: 100,
                    sortable: true
                }, {
                    field: 'name',
                    title: '金额(USDT)',
                    width: 100,
                    sortable: true
                }, {
                    field: 'name',
                    title: '份数',
                    width: 100,
                    sortable: true
                }, {
                    field: 'name',
                    title: '手续费(份)',
                    width: 100,
                    sortable: true
                }, {
                    field: 'createdDate',
                    title: '创建时间',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                    }
                }, {
                    field: 'updatedDate',
                    title: '修改时间',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {

                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

        },
        bindEvent: function () {

        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {},
    Util: {}
};