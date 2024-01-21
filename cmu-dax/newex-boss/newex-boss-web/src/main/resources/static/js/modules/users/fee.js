$(function () {
    Fee.init();
});

var Fee = {
    init: function () {
        FeeMVC.View.initControl();
        FeeMVC.View.bindEvent();
    }
};

var FeeCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/user/fee/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    commonUrl: BossGlobal.ctxPath + '/v1/boss/common/'
};

var FeeMVC = {
    URLs: {
        SearchUserLevel: {
            url: FeeCommon.baseUrl + 'userLevel',
            method: 'GET'
        },
        SearchProductFee: {
            url: FeeCommon.baseUrl + 'productFee',
            method: 'GET'
        },
        AddUserLevel: {
            url: FeeCommon.baseUrl + 'addUserLevel',
            method: 'POST'
        },
        AddProductFee: {
            url: FeeCommon.baseUrl + 'addProductFee',
            method: 'POST'
        },
        deleteUserLevel: {
            url: FeeCommon.baseUrl + "deleteUserLevel",
            method: 'POST'
        },
        deleteProductFee: {
            url: FeeCommon.baseUrl + "deleteProductFee",
            method: 'POST'
        },
        editProductFee: {
            url: FeeCommon.baseUrl + "editProductFee",
            method: 'POST'
        },
        Product: {
            url: FeeCommon.commonUrl + 'product',
            method: 'GET'
        }
    },
    Model: {},
    View: {
        initControl: function () {
            $('#productId').combobox({
                url: FeeMVC.URLs.Product.url,
                method: 'get',
                valueField: "id",
                textField: "code",
                editable: true,
                panelHeight: 300
            });
            $('#search-product').combobox({
                url: FeeMVC.URLs.Product.url,
                method: 'get',
                valueField: "id",
                textField: "code",
                editable: true,
                panelHeight: 300
            });
            $('#user-tab').tabs({
                border: false,
                onSelect: function () {
                    var tab = $('#user-tab').tabs('getSelected');
                    var index = $('#user-tab').tabs('getTabIndex', tab);
                    if (index === 0) {
                        EasyUIUtils.reloadDatagrid('#user-level-datagrid')
                    }
                    if (index === 1) {
                        EasyUIUtils.reloadDatagrid('#product-fee-datagrid')
                    }
                }
            });
            $('#product-fee-dlg').dialog({
                closed: true,
                modal: true,
                striped: true,
                collapsible: true,
                resizable: true,
                width: 600,
                height: 400,
                maximizable: true,
                iconCls: 'icon-add',
                title: '添加币对手续费',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#product-fee-dlg").dialog('close');
                    }
                }, {
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: FeeMVC.Controller.saveProductFee
                }]
            });
            $('#user-level-dlg').dialog({
                closed: true,
                modal: true,
                striped: true,
                collapsible: true,
                width: 600,
                height: 250,
                iconCls: 'icon-add',
                title: '添加用户等级',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#user-level-dlg").dialog('close');
                    }
                }, {
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: FeeMVC.Controller.saveUserLevel
                }]
            });
            $('#user-level-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                sortOrder: 'desc',
                pageSize: 50,
                pageNumber: 1,
                url: FeeMVC.URLs.SearchUserLevel.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    text: '刷新',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#user-level-datagrid')
                    }
                }, '-', {
                    iconCls: 'icon-add',
                    text: '添加',
                    handler: function () {
                        FeeMVC.Controller.addUserLevel();
                    }
                }, '-', {
                    iconCls: 'icon-remove',
                    text: '删除',
                    handler: function () {
                        FeeMVC.Controller.deleteUserLevel();
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
                        width: 100
                    },
                    {
                        field: 'userId',
                        title: '用户ID',
                        width: 100
                    }, {
                        field: 'userLevel',
                        title: '用户等级',
                        width: 100
                    }
                    , {
                        field: 'createdDate',
                        title: '创建时间',
                        width: 100,
                        formatter: function (val) {
                            return moment(new Date(val)).format('YYYY-MM-DD HH:mm:ss');
                        }
                    }]],
                onDblClickRow: function (rowIndex, rowData) {
                },
                onLoadSuccess: function () {
                    $('.pagination-page-list').hide();

                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });
            $('#product-fee-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                sortOrder: 'desc',
                pageSize: 50,
                pageNumber: 1,
                url: FeeMVC.URLs.SearchProductFee.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    text: '刷新',
                    handler: function () {
                        $("#product-fee-datagrid").datagrid({
                            pageNumber: 1,
                            pageSize: 50,
                            url: FeeMVC.URLs.SearchProductFee.url
                        });
                    }
                }, '-', {
                    iconCls: 'icon-add',
                    text: '添加',
                    handler: function () {
                        FeeMVC.Controller.addProductFee();
                    }
                }, '-', {
                    iconCls: 'icon-edit',
                    text: '编辑',
                    handler: function () {
                        FeeMVC.Controller.EditProductFee();
                    }
                }, '-', {
                    iconCls: 'icon-remove',
                    text: '删除',
                    handler: function () {
                        FeeMVC.Controller.deleteProductFee();
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
                        width: 100
                    },
                    {
                        field: 'userId',
                        title: '用户ID',
                        width: 100
                    }, {
                        field: 'productId',
                        title: '币对',
                        width: 100,
                        hidden: true

                    }, {
                        field: 'productDesc',
                        title: '币对',
                        width: 100

                    }, {
                        field: 'rate',
                        title: '手续费',
                        width: 100
                    }, {
                        field: 'side',
                        title: '类型',
                        width: 100,
                        formatter: function (val) {
                            if (val === 0) {
                                return 'both'
                            }
                            if (val === 1) {
                                return 'maker'
                            }
                            if (val === 2) {
                                return 'taker'
                            }
                        }
                    }
                    , {
                        field: 'createdDate',
                        title: '创建时间',
                        width: 100
                    }
                    , {
                        field: 'expireDate',
                        title: '过期时间',
                        width: 100
                    }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return FeeMVC.Controller.EditProductFee();
                },
                onLoadSuccess: function (data) { //加载完成后,设置选中第一项
                    $('.pagination-page-list').hide();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });
        },
        bindEvent: function () {
            $('#btn-user-search').bind('click', FeeMVC.Controller.findUserLevel);
            $('#btn-prduct-search').bind('click', FeeMVC.Controller.findProductFee);
        }
    },
    Controller: {
        addUserLevel: function () {
            var options = FeeMVC.Util.getUserLevelOptions();
            options.title = '新增用户等级';
            EasyUIUtils.openAddDlg(options);
        },
        addProductFee: function () {
            var options = FeeMVC.Util.getOptions();
            options.title = '新增币对手续费';
            EasyUIUtils.openAddDlg(options);
        },
        findUserLevel: function () {
            var userId = $('#fee-userId').textbox('getValue');
            var url = FeeMVC.URLs.SearchUserLevel.url + '?userId=' + userId;
            $("#user-level-datagrid").datagrid({
                pageNumber: 1,
                pageSize: 50,
                url: url
            });
        },
        findProductFee: function () {
            var userId = $('#search-id').textbox('getValue');
            var productId = $('#search-product').combobox('getValue');
            var url = FeeMVC.URLs.SearchProductFee.url + '?userId=' + userId + '&productId=' + productId;
            $("#product-fee-datagrid").datagrid({
                pageNumber: 1,
                pageSize: 50,
                url: url
            });
        },
        deleteUserLevel: function () {
            var row = $('#user-level-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: FeeMVC.URLs.deleteUserLevel.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#user-level-datagrid',
                    gridUrl: FeeMVC.URLs.SearchUserLevel.url,
                    callback: function (rows) {
                    }
                };
                EasyUIUtils.remove(options);
            }
        },
        deleteProductFee: function () {
            var row = $('#product-fee-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: FeeMVC.URLs.deleteProductFee.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#product-fee-datagrid',
                    gridUrl: FeeMVC.URLs.SearchProductFee.url,
                    callback: function (rows) {
                    }
                };
                EasyUIUtils.remove(options);
            }
        },
        saveUserLevel: function () {
            var options = {
                gridId: null,
                gridUrl: FeeMVC.URLs.SearchUserLevel.url,
                dlgId: "#user-level-dlg",
                formId: "#user-level-form",
                url: null,
                callback: function () {
                    EasyUIUtils.loadDataWithUrl('#user-level-datagrid', options.gridUrl);
                }
            };
            options.url = FeeMVC.URLs.AddUserLevel.url;
            options.gridId = '#user-level-datagrid';
            return EasyUIUtils.save(options);
        },
        saveProductFee: function () {
            var action = $('#modal-action').val();
            var options = {
                gridId: null,
                gridUrl: FeeMVC.URLs.SearchProductFee.url,
                dlgId: "#product-fee-dlg",
                formId: "#product-fee-form",
                url: null,
                callback: function () {
                }
            };

            options.url = (action === "edit" ? FeeMVC.URLs.editProductFee.url : FeeMVC.URLs.AddProductFee.url);
            options.gridId = '#product-fee-datagrid';
            return EasyUIUtils.save(options);
        },
        EditProductFee: function () {
            var row = $('#product-fee-datagrid').datagrid('getSelected');
            $("#modal-action").val("edit");
            if (row) {
                var options = FeeMVC.Util.getOptions();
                options.iconCls = 'icon-edit';
                options.data = row;
                options.title = '修改[' + options.data.userId + ']';
                EasyUIUtils.openEditDlg(options);

            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        }
    },
    Util: {
        getUserLevelOptions: function () {
            return {
                dlgId: '#user-level-dlg',
                formId: '#user-level-form',
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
        getOptions: function () {
            return {
                dlgId: '#product-fee-dlg',
                formId: '#product-fee-form',
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
            var userId = $("#search-id").textbox('getValue');
            var email = $("#search-email").textbox('getValue');
            var mobile = $("#search-mobile").textbox('getValue');
            var title = $("#search-title").combobox('getValue');
            return url = FeeMVC.URLs.Search.url + '?userId=' + userId + '&email=' + email + '&mobile=' + mobile + '&title=' + title;
        },
        getRowSelected: function (func) {
            var row = $('#user-fee-datagrid').datagrid('getSelected');
            if (row) {
                func(row);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
    }
};