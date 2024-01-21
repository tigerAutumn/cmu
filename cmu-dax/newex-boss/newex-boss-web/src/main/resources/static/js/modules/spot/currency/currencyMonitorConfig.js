$(function () {
    CurrencyMonitorConfig.init();
});

var CurrencyMonitorConfig = {
    init: function () {
        CurrencyMonitorConfigMVC.View.initControl();
        CurrencyMonitorConfigMVC.View.bindEvent();
        CurrencyMonitorConfigMVC.View.bindValidate();
    }
};

var CurrencyMonitorConfigCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/spot/currency/monitor-config/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    basecomboxUrl: BossGlobal.ctxPath + '/v1/boss/common/'
};

var CurrencyMonitorConfigMVC = {
    URLs: {
        add: {
            url: CurrencyMonitorConfigCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: CurrencyMonitorConfigCommon.baseUrl + 'edit',
            method: 'POST'
        },
        remove: {
            url: CurrencyMonitorConfigCommon.baseUrl + 'remove',
            method: 'POST'
        },
        list: {
            url: CurrencyMonitorConfigCommon.baseUrl + 'list',
            method: 'GET'
        },
        currency: {
            url: CurrencyMonitorConfigCommon.basecomboxUrl + 'spot/currency',
            method: 'GET'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {

            $("#currencyId").combobox({
                url: CurrencyMonitorConfigMVC.URLs.currency.url,
                method: 'get',
                valueField: "id",
                textField: "symbol",
                panelHeight: 300,
                editable: true
            });

            $("#currencyMonitorConfig-currencyId").combobox({
                url: CurrencyMonitorConfigMVC.URLs.currency.url,
                method: 'get',
                valueField: "id",
                textField: "symbol",
                panelHeight: 300,
                editable: true
            });

            $('#status').combobox({
                valueField: 'id',
                textField: 'value',
                data: [
                    {
                        id: 1,
                        value: '有效'
                    }, {
                        id: 0,
                        value: '无效'
                    }
                ]
            });

            $('#currencyMonitorConfig-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: CurrencyMonitorConfigMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        CurrencyMonitorConfigMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        CurrencyMonitorConfigMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#currencyMonitorConfig-datagrid').datagrid('load', {});
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
                    field: 'currencyId',
                    title: '币种ID',
                    width: 100,
                    sortable: true
                }, {
                    field: 'threshold',
                    title: '报警阈值',
                    width: 100,
                    sortable: true
                }, {
                    field: 'status',
                    title: '状态',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value === 0) {
                            return "无效";
                        } else if (value === 1) {
                            return "有效";
                        } else {
                            return "";
                        }
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
                                + 'onclick="CurrencyMonitorConfigMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: CurrencyMonitorConfigCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return CurrencyMonitorConfigMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // dialogs
            $('#currencyMonitorConfig-dlg').dialog({
                closed: true,
                modal: false,
                width: 760,
                height: 500,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#currencyMonitorConfig-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: CurrencyMonitorConfigMVC.Controller.save
                }]
            });


        },
        bindEvent: function () {
            $('#btn-search').bind('click', CurrencyMonitorConfigMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#currencyMonitorConfig-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return CurrencyMonitorConfigMVC.Controller.edit();
            }

            if (name === "remove") {
                return CurrencyMonitorConfigMVC.Controller.remove();
            }
        },
        add: function () {
            var options = CurrencyMonitorConfigMVC.Util.getOptions();
            options.title = '新增 币种监测阈值配置';
            EasyUIUtils.openAddDlg(options);

            $('#status').combobox('setValue', '1');
        },
        edit: function () {
            var row = $('#currencyMonitorConfig-datagrid').datagrid('getSelected');
            if (row) {
                var options = CurrencyMonitorConfigMVC.Util.getOptions();

                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改 币种监测阈值配置';

                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#currencyMonitorConfig-datagrid').datagrid('getSelected');
            if (row) {
                var options = CurrencyMonitorConfigMVC.Util.getOptions();

                options.url = CurrencyMonitorConfigMVC.URLs.remove.url;
                options.rows = [row];
                options.data = {id: row.id, code: row.code};

                EasyUIUtils.remove(options);
            }
        },
        find: function () {
            var params = CurrencyMonitorConfigMVC.Util.getQueryParams();
            $('#currencyMonitorConfig-datagrid').datagrid('load', params);
        },
        save: function () {
            var action = $('#modal-action').val();

            var options = CurrencyMonitorConfigMVC.Util.getOptions();

            if (action === "edit") {
                var row = $('#currencyMonitorConfig-datagrid').datagrid('getSelected');
                //取得ID传给后台
                options.url = CurrencyMonitorConfigMVC.URLs.edit.url + '?id=' + row.id;
            }
            else {
                //添加操作
                options.url = CurrencyMonitorConfigMVC.URLs.add.url;
            }

            //修改后刷新列表
            EasyUIUtils.save(options);

            return;
        }

    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#currencyMonitorConfig-dlg',
                formId: '#currencyMonitorConfig-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                url: null,
                gridId: '#currencyMonitorConfig-datagrid',
                gridUrl: CurrencyMonitorConfigMVC.URLs.list.url
            };
        },
        getQueryParams: function () {
            var currencyId = $("#currencyMonitorConfig-currencyId").val();
            var params = {
                currencyId: currencyId
            };

            return params;
        }
    }
};