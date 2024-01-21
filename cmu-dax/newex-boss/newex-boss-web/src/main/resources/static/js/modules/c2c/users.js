$(function () {
    MembershipUser.init();
});

var MembershipUser = {
    init: function () {
        UserManageMVC.View.initControl();
        UserManageMVC.View.bindEvent();
        UserManageMVC.View.bindValidate();

    }
};

var UserManageCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/c2c/users/',
    baseRoleUrl: BossGlobal.ctxPath + '/v1/boss/c2c/users/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/'
};

var UserManageMVC = {
    URLs: {
        limit: {
            url: UserManageCommon.baseRoleUrl + 'limit',
            method: 'GET'
        },
        spilLover: {
            url: UserManageCommon.baseRoleUrl + 'spilLover',
            method: 'GET'
        }
    },
    Model: {},
    View: {
        initControl: function () {
            $('#limit-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: false,
                rownumbers: false,
                sortOrder: 'desc',
                pageSize: 20,
                url: UserManageMVC.URLs.limit.url,
                toolbar: [],
                loadFilter: function (src) {
                    if (!src.code) {
                        return src.data;
                    }
                    return $.messager.alert('失败', src.msg, 'error');
                },
                columns: [[
                    {
                        field: 'id',
                        title: '法币币种',
                        width: 100,
                        sortable: true,


                    }, {
                        field: 'baseCurrency',
                        title: '单笔最小限额',
                        width: 100,
                        sortable: true,
                        formatter: function (value, row, index) {

                        }

                    }, {
                        field: 'quoteCurrency',
                        title: '单笔最大限额',
                        width: 100,
                        sortable: true,
                        formatter: function (value, row, index) {

                        }
                    }, {
                        field: 'code',
                        title: '总限额',
                        width: 100,
                        sortable: true,

                    }, {
                        field: 'minTradeSize',
                        title: '状态',
                        width: 100,
                        sortable: true,
                    }, {
                        field: 'options',
                        title: '操作',
                        width: 100,
                        formatter: function (value, row, index) {
                            var icons = [{
                                "name": "edit",
                                "title": "修改"
                            }, {
                                "name": "edit",
                                "title": "禁用"
                            }];
                            var buttons = [];
                            for (var i = 0; i < icons.length; i++) {
                                var tmpl = '<a href="#" title ="${title}" '
                                    + 'onclick="UserManageMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                    + '<img src="${imgSrc}" alt="${title}"/"></a>';
                                var data = {
                                    title: icons[i].title,
                                    name: icons[i].name,
                                    index: index,
                                    imgSrc: UserManageCommon.baseIconUrl + icons[i].name + ".png"
                                };
                                buttons.push(juicer(tmpl, data));
                            }
                            return buttons.join(' ');
                        }
                    }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return UserManageMVC.Controller.edit();
                },
                onLoadSuccess: function () { //加载完成后,设置选中第一项
                    // $('.pagination-page-list').hide();

                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            $('#spilLover-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: false,
                rownumbers: false,
                sortOrder: 'desc',
                pageSize: 20,
                url: UserManageMVC.URLs.spilLover.url,
                toolbar: [],
                loadFilter: function (src) {
                    if (!src.code) {
                        return src.data;
                    }
                    return $.messager.alert('失败', src.msg, 'error');
                },
                columns: [[
                    {
                        field: 'id',
                        title: '数字货币币种',
                        width: 100,
                        sortable: true,


                    }, {
                        field: 'baseCurrency',
                        title: '买入溢价',
                        width: 100,
                        sortable: true,
                        formatter: function (value, row, index) {

                        }

                    }, {
                        field: 'quoteCurrency',
                        title: '卖出溢价',
                        width: 100,
                        sortable: true,
                        formatter: function (value, row, index) {

                        }
                    }, {
                        field: 'code',
                        title: '返佣比例',
                        width: 100,
                        sortable: true,

                    }, {
                        field: 'minTradeSize',
                        title: '状态',
                        width: 100,
                        sortable: true,
                    }, {
                        field: 'options',
                        title: '操作',
                        width: 100,
                        formatter: function (value, row, index) {
                            var icons = [{
                                "name": "edit",
                                "title": "修改"
                            }, {
                                "name": "edit",
                                "title": "禁用"
                            }];
                            var buttons = [];
                            for (var i = 0; i < icons.length; i++) {
                                var tmpl = '<a href="#" title ="${title}" '
                                    + 'onclick="UserManageMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                    + '<img src="${imgSrc}" alt="${title}"/"></a>';
                                var data = {
                                    title: icons[i].title,
                                    name: icons[i].name,
                                    index: index,
                                    imgSrc: UserManageCommon.baseIconUrl + icons[i].name + ".png"
                                };
                                buttons.push(juicer(tmpl, data));
                            }
                            return buttons.join(' ');
                        }
                    }]],
                onDblClickRow: function (rowIndex, rowData) {
                    // return UserManageMVC.Controller.edit();
                },
                onLoadSuccess: function () { //加载完成后,设置选中第一项
                    // $('.pagination-page-list').hide();

                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

        },
        bindEvent: function () {
            //$('#btn-search').bind('click', UserManageMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {
            // UserMVC.Util.loadRoleList();
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#user-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return UserManageMVC.Controller.edit();
            }
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
        isRowSelected: function (func) {
            var row = $('#user-datagrid').datagrid('getSelected');
            if (row) {
                func(row);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        }
    }
};