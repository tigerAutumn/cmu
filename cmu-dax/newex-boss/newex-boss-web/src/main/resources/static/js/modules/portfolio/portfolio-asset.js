$(function () {
    PortfolioAsset.init();
});

var PortfolioAsset = {
    init: function () {
        PortfolioAssetMVC.View.initControl();
        PortfolioAssetMVC.View.bindEvent();
        PortfolioAssetMVC.View.bindValidate();
    }
};

var PortfolioAssetCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/portfolio/asset/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/'
};

var PortfolioAssetMVC = {
    URLs: {
        list: {
            url: PortfolioAssetCommon.baseUrl + 'list',
            method: 'GET'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {

            $('#portfolioAsset-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: PortfolioAssetMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#portfolioAsset-datagrid').datagrid('load', {});
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
                    title: '币种',
                    width: 100,
                    sortable: true
                }, {
                    field: 'name',
                    title: '余额',
                    width: 100,
                    sortable: true
                }, {
                    field: 'name',
                    title: '可用',
                    width: 100,
                    sortable: true
                }, {
                    field: 'name',
                    title: '冻结',
                    width: 100,
                    sortable: true
                }, {
                    field: 'name',
                    title: 'BTC估值',
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