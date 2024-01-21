$(function () {
    Specification.init();
});

var Specification = {
    init: function () {
        SpecificationMVC.View.initControl();
        SpecificationMVC.View.bindEvent();
        SpecificationMVC.View.bindValidate();

    }
};

var SpecificationCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/Specification/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
};

var SpecificationMVC = {
    URLs: {
        Search: {
            url: SpecificationCommon.baseUrl + 'searchList',
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
                url: SpecificationMVC.URLs.Search.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        $("#futures-datagrid").datagrid({
                            pageNumber: 1,
                            pageSize: 50,
                            url: SpecificationMVC.URLs.Search.url
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
                        title: '序号',
                        width: 100,
                        sortable: true,


                    }, {
                        field: 'a',
                        title: '交易货币',
                        width: 100,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return value.toUpperCase();
                        }

                    }, {
                        field: 'a',
                        title: '计价货币',
                        width: 100,
                        sortable: true
                    }, {
                        field: 'a',
                        title: '最小委托量',
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
                        title: '交易单位',
                        width: 100,
                        sortable: true
                    }
                    , {
                        field: 'a',
                        title: '最小交易单位',
                        width: 100,
                        sortable: true
                    }
                    , {
                        field: 'a',
                        title: '交易量小数位数',
                        width: 100,
                        sortable: true
                    }
                    , {
                        field: 'a',
                        title: '合并深度档位',
                        width: 100,
                        sortable: true
                    }
                    , {
                        field: 'a',
                        title: '交易手续费',
                        width: 100,
                        sortable: true
                    }
                    , {
                        field: 'a',
                        title: '强平手续费',
                        width: 100,
                        sortable: true
                    }
                    , {
                        field: 'a',
                        title: '指数限价',
                        width: 100,
                        sortable: true
                    }, {
                        field: 'a',
                        title: '盘口限价',
                        width: 100,
                        sortable: true
                    }, {
                        field: 'a',
                        title: '创建时间',
                        width: 100,
                        sortable: true
                    }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return SpecificationMVC.Controller.edit();
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
                        var url = SpecificationMVC.Util.getSearchUrl();
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
            $('#btn-search').bind('click', SpecificationMVC.Controller.find);
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#futures-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return SpecificationMVC.Controller.edit();
            }
        },
        find: function () {
            var url = SpecificationMVC.Util.getSearchUrl();
            $("#futures-datagrid").datagrid({
                pageNumber: 1,
                pageSize: 50,
                url: url
            });

        }
    },
    Util: {
        getSearchUrl: function () {
            var name = $("#Specification-name").textbox('getValue');
            return url = SpecificationMVC.URLs.Search.url;
        }
    }
};