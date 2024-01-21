$(function () {
    ProviderConf.init();
});

var ProviderConf = {
    init: function () {
        ProviderConfMVC.View.initControl();
        ProviderConfMVC.View.bindEvent();
        ProviderConfMVC.View.bindValidate();
        ProviderConfMVC.View.initData();
    }
};

var ProviderConfCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/integration/provider-conf/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/'
};

var ProviderConfMVC = {
    URLs: {
        add: {
            url: ProviderConfCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: ProviderConfCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: ProviderConfCommon.baseUrl + 'list',
            method: 'GET'
        },
        remove: {
            url: ProviderConfCommon.baseUrl + 'remove',
            method: 'POST'
        },
        getBrokerList: {
            url: ProviderConfCommon.brokerBaseUrl + 'list'
        }
    },
    Model: {},
    View: {
        initControl: function () {

            $("#brokerId").combobox({
                url: ProviderConfMVC.URLs.getBrokerList.url,
                method: 'get',
                valueField: "id",
                textField: "brokerName",
                editable: true,
                value: '',
                prompt: '选择券商',
                panelHeight: 300
            });

            $('#providerConf-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: ProviderConfMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        ProviderConfMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        ProviderConfMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-remove1',
                    handler: function () {
                        ProviderConfMVC.Controller.remove();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        $("#providerConf-datagrid").datagrid({
                            pageNumber: 1,
                            pageSize: 50,
                            url: ProviderConfMVC.URLs.list.url
                        });
                        //EasyUIUtils.reloadDatagrid('#providerConf-datagrid');
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
                    title: '名称',
                    width: 100,
                    sortable: true
                }, {
                    field: 'type',
                    title: '类型',
                    width: 50,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value === "sms") {
                            return "短信"
                        }
                        if (value === "mail") {
                            return "邮件"
                        }
                        if (value === "ip") {
                            return "IP地址"
                        }
                        if (value === "bank") {
                            return "银行三要素"
                        }
                        return "未知";
                    }
                }, {
                    field: 'region',
                    title: '服务地区',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value === "china") {
                            return "国内"
                        }
                        if (value === "international") {
                            return "国际"
                        }
                        if (value === "all") {
                            return "全部"
                        }
                        return "未知";
                    }
                }, {
                    field: 'status',
                    title: '状态',
                    width: 50,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return value === 1 ? "启用" : "禁用";
                    }
                }, {
                    field: 'brokerId',
                    title: '券商',
                    width: 50
                }, {
                    field: 'weight',
                    title: '权重',
                    width: 50,
                    sortable: true
                }, {
                    field: 'memo',
                    title: '备注',
                    width: 100
                }, {
                    field: 'createdDate',
                    title: '创建时间',
                    width: 80,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                    }
                }, {
                    field: 'updatedDate',
                    title: '更新时间',
                    width: 80,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                    }
                }, {
                    field: 'opt',
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
                                + 'onclick="ProviderConfMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: ProviderConfCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return ProviderConfMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            var pager = $("#providerConf-datagrid").datagrid("getPager");
            if (pager) {
                $(pager).pagination({
                    onSelectPage: function (pageNumber, pageSize) {

                        var url = ProviderConfMVC.Util.getSearchUrl();
                        $("#providerConf-datagrid").datagrid({
                            pageNumber: pageNumber,
                            pageSize: pageSize,
                            url: url
                        });

                    }
                });
            }

            var optionsDatagrid = $('#providerConf-options-datagrid');
            optionsDatagrid.datagrid({
                scrollbarSize: 0,
                height: 300,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        optionsDatagrid.datagrid('appendRow', {
                            name: 'new name',
                            value: 'value',
                        });
                    }
                }, '-', {
                    iconCls: 'icon-remove1',
                    handler: function () {
                        var row = optionsDatagrid.datagrid('cell');
                        if (row) {
                            optionsDatagrid.datagrid('deleteRow', row.index);
                        }
                    }
                }],
                columns: [[
                    {field: 'name', title: '配置项', width: 200, sortable: true, editor: 'text'},
                    {field: 'value', title: '配置值', width: 250, resizable: false, editor: 'text'}
                ]]
            });
            optionsDatagrid.datagrid('enableCellEditing');

            // dialogs
            $('#providerConf-dlg').dialog({
                closed: true,
                modal: false,
                width: 660,
                height: 580,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#providerConf-dlg").dialog('close');
                    }
                }, {
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: ProviderConfMVC.Controller.save
                }]
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', ProviderConfMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#providerConf-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return ProviderConfMVC.Controller.edit();
            }
            if (name === "remove") {
                return ProviderConfMVC.Controller.remove();
            }
        },
        add: function () {
            var options = ProviderConfMVC.Util.getOptions();
            options.title = '新增服务提供者配置';
            EasyUIUtils.openAddDlg(options);
            EasyUIUtils.clearDatagrid('#providerConf-options-datagrid');
            $('#status').combobox('setValue', "1");
            $('#type').combobox('setValue', "sms");
            $('#region').combobox('setValue', "china");
            $('#weight').textbox('setValue', "5");
            $('#brokerId').combobox({readonly: false});
        },
        edit: function () {
            var row = $('#providerConf-datagrid').datagrid('getSelected');
            $('#brokerId').combobox({readonly: true});
            if (row) {
                var options = ProviderConfMVC.Util.getOptions();
                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改[' + options.data.name + ']服务提供者配置';
                EasyUIUtils.openEditDlg(options);
                EasyUIUtils.clearDatagrid('#providerConf-options-datagrid');

                $('#status').combobox('setValue', row.status);
                $('#type').combobox('setValue', row.type);
                $('#region').combobox('setValue', row.region);
                $('#weight').textbox('setValue', row.weight);
                $('#options').val(row.options || "{}");
                $('#providerConf-options-datagrid').datagrid('loadData', EasyUIUtils.toPropertygridRows($.toJSON(row.options)));
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        find: function () {

            var url = ProviderConfMVC.Util.getSearchUrl();
            $("#providerConf-datagrid").datagrid({
                pageNumber: 1,
                pageSize: 50,
                url: url
            });

            // var fieldName = $("#field-name").combobox('getValue');
            // var keyword = $("#keyword").val();
            // var url = ProviderConfMVC.URLs.list.url + '?fieldName=' + fieldName + '&keyword=' + keyword;
            // EasyUIUtils.loadToDatagrid('#providerConf-datagrid', url)
        },
        remove: function () {
            var row = $('#providerConf-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: ProviderConfMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#providerConf-datagrid',
                    gridUrl: ProviderConfMVC.URLs.list.url,
                    callback: function (rows) {
                    }
                };
                EasyUIUtils.remove(options);
            }
        },
        save: function () {
            var rows = $('#providerConf-options-datagrid').datagrid('getRows');
            $('#options').val(JSON.stringify(EasyUIUtils.toPropertygridMap(rows)));

            var action = $('#modal-action').val();
            var options = {
                gridId: null,
                gridUrl: ProviderConfMVC.URLs.list.url,
                dlgId: "#providerConf-dlg",
                formId: "#providerConf-form",
                url: null,
                callback: function () {
                }
            };

            options.url = (action === "edit" ? ProviderConfMVC.URLs.edit.url : ProviderConfMVC.URLs.add.url);
            options.gridId = '#providerConf-datagrid';
            return EasyUIUtils.save(options);
        }
    },
    Util: {
        getSearchUrl: function () {

            var fieldName = $("#field-name").combobox('getValue');
            var keyword = $("#keyword").val();
            return ProviderConfMVC.URLs.list.url + '?fieldName=' + fieldName + '&keyword=' + keyword;

        },
        getOptions: function () {
            return {
                dlgId: '#providerConf-dlg',
                formId: '#providerConf-form',
                actId: '#modal-action',
                rowId: '#providerConfId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                gridId: null,
            };
        }
    }
};