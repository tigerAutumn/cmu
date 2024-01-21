$(function () {
    CurrencyPermission.init();
});

var CurrencyPermission = {
    init: function () {
        CurrencyPermissionMVC.View.initControl();
        CurrencyPermissionMVC.View.bindEvent();
        CurrencyPermissionMVC.View.bindValidate();
    }
};

var CurrencyPermissionCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/extra/currency/permission/',
    currencyUrl: BossGlobal.ctxPath + '/v1/boss/extra/currency/base/',
    imageUrl: BossGlobal.ctxPath + '/v1/boss/upload/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/'
};

var CurrencyPermissionMVC = {
    URLs: {
        add: {
            url: CurrencyPermissionCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: CurrencyPermissionCommon.baseUrl + 'edit',
            method: 'POST'
        },
        remove: {
            url: CurrencyPermissionCommon.baseUrl + 'remove',
            method: 'POST'
        },
        list: {
            url: CurrencyPermissionCommon.baseUrl + 'list',
            method: 'GET'
        },
        allCurrency: {
            url: CurrencyPermissionCommon.currencyUrl + 'listAll',
            method: 'GET'
        }
    },
    Model: {},
    View: {
        initControl: function () {

            $.get(CurrencyPermissionMVC.URLs.allCurrency.url, "", function (_data) {
                var allCurrency = _data.data || [];

                var currencyWithCode = [];
                var allCurrencyWithCode = [];

                allCurrencyWithCode.push({
                    "value": "",
                    "text": "全部"
                });

                for (var i = 0; i < allCurrency.length; i++) {
                    var item = allCurrency[i];

                    currencyWithCode.push({
                        "value": item.code,
                        "text": item.code.toUpperCase()
                    });

                    allCurrencyWithCode.push({
                        "value": item.code,
                        "text": item.code.toUpperCase()
                    });
                }

                $('#currencyPermission-code').combobox({
                    valueField: 'value',
                    textField: 'text',
                    data: allCurrencyWithCode
                });

                $('#code').combobox({
                    required: true,
                    valueField: 'value',
                    textField: 'text',
                    data: currencyWithCode
                });
            });

            $('#status').combobox({
                required: true,
                valueField: 'id',
                textField: 'value',
                data: [
                    {
                        id: '0',
                        value: '禁用'
                    }, {
                        id: '1',
                        value: '启用'
                    }
                ]
            });

            $('#currencyPermission-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: CurrencyPermissionMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        CurrencyPermissionMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        CurrencyPermissionMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#currencyPermission-datagrid').datagrid('load', {});
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
                    field: 'code',
                    title: '币种简称',
                    width: 100,
                    sortable: true
                }, {
                    field: 'userId',
                    title: '用户ID',
                    width: 100,
                    sortable: true
                }, {
                    field: 'organization',
                    title: '组织机构',
                    width: 200,
                    sortable: true
                }, {
                    field: 'mobile',
                    title: '手机',
                    width: 100,
                    sortable: true
                }, {
                    field: 'status',
                    title: '状态',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value == 0) {
                            return '禁用';
                        }

                        if (value == 1) {
                            return '启用';
                        }

                        return '';
                    }
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
                }, {
                    field: 'options',
                    title: '操作',
                    width: 100,
                    formatter: function (value, row, index) {
                        var icons = [{
                            "name": "edit",
                            "title": "编辑"
                        }, {
                            "name": "remove",
                            "title": "删除"
                        }];

                        var buttons = [];
                        for (var i = 0; i < icons.length; i++) {
                            var tmpl = '<a href="#" title ="${title}" '
                                + 'onclick="CurrencyPermissionMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: CurrencyPermissionCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return CurrencyPermissionMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // dialogs
            $('#currencyPermission-dlg').dialog({
                closed: true,
                modal: false,
                width: 800,
                height: 500,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#currencyPermission-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: CurrencyPermissionMVC.Controller.save
                }]
            });


        },
        bindEvent: function () {
            $('#btn-search').bind('click', CurrencyPermissionMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#currencyPermission-datagrid').datagrid('selectRow', index);

            if (name === "edit") {
                return CurrencyPermissionMVC.Controller.edit();
            }

            if (name === "remove") {
                return CurrencyPermissionMVC.Controller.remove();
            }
        },
        add: function () {
            var options = CurrencyPermissionMVC.Util.getOptions();
            options.title = '新增币种权限';
            EasyUIUtils.openAddDlg(options);

            $('#status').combobox('select', '1');

            var allData = $('#code').combobox('getData');
            if (allData) {
                $('#code').combobox('select', allData[0]['value']);
            }
        },
        edit: function () {
            var row = $('#currencyPermission-datagrid').datagrid('getSelected');
            if (row) {
                var options = CurrencyPermissionMVC.Util.getOptions();

                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改币种权限[' + row.code + ']';

                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#currencyPermission-datagrid').datagrid('getSelected');
            if (row) {
                var options = CurrencyPermissionMVC.Util.getOptions();

                options.url = CurrencyPermissionMVC.URLs.remove.url;
                options.rows = [row];
                options.data = {id: row.id};

                EasyUIUtils.remove(options);
            }
        },
        find: function () {
            var params = CurrencyPermissionMVC.Util.getQueryParams();

            $('#currencyPermission-datagrid').datagrid('load', params);
        },
        save: function () {
            var action = $('#modal-action').val();

            var options = CurrencyPermissionMVC.Util.getOptions();

            if (action === "edit") {
                var row = $('#currencyPermission-datagrid').datagrid('getSelected');
                //取得ID传给后台
                options.url = CurrencyPermissionMVC.URLs.edit.url + '?id=' + row.id;
            }
            else {
                //添加操作
                options.url = CurrencyPermissionMVC.URLs.add.url;
            }

            //修改后刷新列表
            EasyUIUtils.save(options);

            return;
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#currencyPermission-dlg',
                formId: '#currencyPermission-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                url: null,
                gridId: '#currencyPermission-datagrid',
                gridUrl: CurrencyPermissionMVC.URLs.list.url
            };
        },
        getQueryParams: function () {
            var code = $('#currencyPermission-code').textbox('getValue');
            var userId = $('#currencyPermission-userId').textbox('getValue');

            var params = {
                code: code,
                userId: userId
            };

            return params;
        }
    }
};