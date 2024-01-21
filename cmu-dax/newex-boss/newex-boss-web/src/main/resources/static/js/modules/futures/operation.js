$(function () {
    Operation.init();
});

var Operation = {
    init: function () {
        OperationMVC.View.initControl();
        OperationMVC.View.bindEvent();
        OperationMVC.View.bindValidate();

    }
};

var OperationCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/Operation/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
};

var OperationMVC = {
    URLs: {
        Search: {
            url: OperationCommon.baseUrl + 'searchList',
            method: 'GET'
        },
    },
    Model: {},
    View: {
        initControl: function () {
            $('#futures-datagrid').datagrid({
                method: 'get',
                fit:true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                sortOrder: 'desc',
                pageSize: 50,
                pageNumber: 1,
                url: OperationMVC.URLs.Search.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        $("#futures-datagrid").datagrid({
                            pageNumber: 1,
                            pageSize: 50,
                            url: OperationMVC.URLs.Search.url
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
                        field: '',
                        title: '未交割期货合约',
                        width: 100,
                        sortable: true,


                    }, {
                        field: 'a',
                        title: 'ID',
                        width: 100,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return value.toUpperCase();
                        }

                    }, {
                        field: 'a',
                        title: '指数价格',
                        width: 100,
                        sortable: true
                    }, {
                        field: 'a',
                        title: '币币价格',
                        width: 100,
                        sortable: true,
                        formatter: function (val, row, index) {
                            if (val > 0) {
                                return '<span style="color:red">' + val + '</span>'
                            } else {
                                return '<span style="color:green">' + val + '</span>'
                            }
                        }

                    }
                    , {
                        field: 'a',
                        title: '最新成交价',
                        width: 100,
                        sortable: true
                    }
                    , {
                        field: 'a',
                        title: '交割/结算价',
                        width: 100,
                        sortable: true
                    }
                    , {
                        field: 'a',
                        title: '操作',
                        width: 100,
                        sortable: true
                    }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return OperationMVC.Controller.edit();
                },
                onLoadSuccess: function () { //加载完成后,设置选中第一项
                    $('.pagination-page-list').hide();

                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });
            var pager = $("#futures-datagrid").datagrid("getPager");
            if (pager) {
                $(pager).pagination({
                    onSelectPage: function (pageNumber, pageSize) {
                        var url = OperationMVC.Util.getSearchUrl();
                        $("#futures-datagrid").datagrid({
                            pageNumber: pageNumber,
                            pageSize: pageSize,
                            url: url
                        });

                    }
                });
            }
        },
        bindEvent: function () {
            $('#btn-search').bind('click', OperationMVC.Controller.find);
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#futures-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return OperationMVC.Controller.edit();
            }
        },
        find: function () {
            var url = OperationMVC.Util.getSearchUrl();
            $("#futures-datagrid").datagrid({
                pageNumber: 1,
                pageSize: 50,
                url: url
            });

        }
    },
    Util: {
        getSearchUrl: function () {
            var name = $("#Operation-name").textbox('getValue');
            return url = OperationMVC.URLs.Search.url;
        }
    }
};