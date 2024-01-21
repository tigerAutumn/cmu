$(function () {
    CurrencyMonitorUser.init();
});

var CurrencyMonitorUser = {
    init: function () {
        CurrencyMonitorUserMVC.View.initControl();
        CurrencyMonitorUserMVC.View.bindEvent();
        CurrencyMonitorUserMVC.View.bindValidate();
    }
};

var CurrencyMonitorUserCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/spot/currency/monitor-user/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/'
};

var CurrencyMonitorUserMVC = {
    URLs: {
        add: {
            url: CurrencyMonitorUserCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: CurrencyMonitorUserCommon.baseUrl + 'edit',
            method: 'POST'
        },
        remove: {
            url: CurrencyMonitorUserCommon.baseUrl + 'remove',
            method: 'POST'
        },
        list: {
            url: CurrencyMonitorUserCommon.baseUrl + 'list',
            method: 'GET'
        },
        getBrokerList: {
            url: CurrencyMonitorUserCommon.brokerBaseUrl + 'list'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {

            $("#brokerId").combobox({
                url: CurrencyMonitorUserMVC.URLs.getBrokerList.url,
                method: 'get',
                valueField: "id",
                textField: "brokerName",
                editable: true,
                value: '',
                prompt: '选择券商',
                panelHeight: 300
            });

            $("#currencyMonitorUser-brokerId").combobox({
                url: CurrencyMonitorUserMVC.URLs.getBrokerList.url,
                method: 'get',
                valueField: "id",
                textField: "brokerName",
                editable: true,
                value: '',
                prompt: '选择券商',
                panelHeight: 300
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

            $('#currencyMonitorUser-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: CurrencyMonitorUserMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        CurrencyMonitorUserMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        CurrencyMonitorUserMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#currencyMonitorUser-datagrid').datagrid('load', {});
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
                    field: 'userId',
                    title: '用户ID',
                    width: 100,
                    sortable: true
                }, {
                    field: 'remark',
                    title: '账号说明',
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
                    field: 'brokerId',
                    title: '券商',
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
                                + 'onclick="CurrencyMonitorUserMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: CurrencyMonitorUserCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return CurrencyMonitorUserMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // dialogs
            $('#currencyMonitorUser-dlg').dialog({
                closed: true,
                modal: false,
                width: 760,
                height: 500,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#currencyMonitorUser-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: CurrencyMonitorUserMVC.Controller.save
                }]
            });


        },
        bindEvent: function () {
            $('#btn-search').bind('click', CurrencyMonitorUserMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#currencyMonitorUser-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return CurrencyMonitorUserMVC.Controller.edit();
            }

            if (name === "remove") {
                return CurrencyMonitorUserMVC.Controller.remove();
            }
        },
        add: function () {
            var options = CurrencyMonitorUserMVC.Util.getOptions();
            options.title = '新增 币种监测账号配置';
            EasyUIUtils.openAddDlg(options);

            $('#status').combobox('setValue', '1');
        },
        edit: function () {
            var row = $('#currencyMonitorUser-datagrid').datagrid('getSelected');
            if (row) {
                var options = CurrencyMonitorUserMVC.Util.getOptions();

                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改 币种监测账号配置';

                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#currencyMonitorUser-datagrid').datagrid('getSelected');
            if (row) {
                var options = CurrencyMonitorUserMVC.Util.getOptions();

                options.url = CurrencyMonitorUserMVC.URLs.remove.url;
                options.rows = [row];
                options.data = {id: row.id, code: row.code};

                EasyUIUtils.remove(options);
            }
        },
        find: function () {
            var params = CurrencyMonitorUserMVC.Util.getQueryParams();
            $('#currencyMonitorUser-datagrid').datagrid('load', params);
        },
        save: function () {
            var action = $('#modal-action').val();

            var options = CurrencyMonitorUserMVC.Util.getOptions();

            if (action === "edit") {
                var row = $('#currencyMonitorUser-datagrid').datagrid('getSelected');
                //取得ID传给后台
                options.url = CurrencyMonitorUserMVC.URLs.edit.url + '?id=' + row.id;
            }
            else {
                //添加操作
                options.url = CurrencyMonitorUserMVC.URLs.add.url;
            }

            //修改后刷新列表
            EasyUIUtils.save(options);

            return;
        }

    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#currencyMonitorUser-dlg',
                formId: '#currencyMonitorUser-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                url: null,
                gridId: '#currencyMonitorUser-datagrid',
                gridUrl: CurrencyMonitorUserMVC.URLs.list.url
            };
        },
        getQueryParams: function () {
            var brokerId = $("#currencyMonitorUser-brokerId").val();
            var params = {
                brokerId: brokerId
            };

            return params;
        }
    }
};