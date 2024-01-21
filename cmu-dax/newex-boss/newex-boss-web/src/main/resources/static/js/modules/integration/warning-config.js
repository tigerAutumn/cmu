$(function () {
    WarningConfig.init();
});

var WarningConfig = {
    init: function () {
        WarningConfigMVC.View.initControl();
        WarningConfigMVC.View.bindEvent();
        WarningConfigMVC.View.bindValidate();
        WarningConfigMVC.View.initData();
    }
};

var WarningConfigCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/integration/warning-config/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/'
};

var WarningConfigMVC = {
    URLs: {
        add: {
            url: WarningConfigCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: WarningConfigCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: WarningConfigCommon.baseUrl + 'list',
            method: 'GET'
        },
        remove: {
            url: WarningConfigCommon.baseUrl + 'remove',
            method: 'POST'
        }
    },
    Model: {},
    View: {
        initControl: function () {
            $('#warning-config-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: WarningConfigMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        WarningConfigMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit',
                    handler: function () {
                        WarningConfigMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-remove',
                    handler: function () {
                        WarningConfigMVC.Controller.remove();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        $("#warning-config-datagrid").datagrid({
                            pageNumber: 1,
                            pageSize: 50,
                            url: WarningConfigMVC.URLs.list.url
                        });
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
                    title: '配置Code',
                    width: 100,
                    sortable: true
                }, {
                    field: 'bizType',
                    title: '业务类型',
                    width: 50,
                    sortable: true,
                }, {
                    field: 'content',
                    title: '详情',
                    width: 100,
                }, {
                    field: 'memo',
                    title: '备注',
                    width: 50,
                    sortable: true,
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return WarningConfigMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            var pager = $("#warning-config-datagrid").datagrid("getPager");
            if (pager) {
                $(pager).pagination({
                    onSelectPage: function (pageNumber, pageSize) {

                        var url = WarningConfigMVC.Util.getSearchUrl();
                        $("#warning-config-datagrid").datagrid({
                            pageNumber: pageNumber,
                            pageSize: pageSize,
                            url: url
                        });

                    }
                });
            }
            // dialogs
            $('#warning-config-dlg').dialog({
                closed: true,
                modal: false,
                width: 660,
                height: 480,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#warning-config-dlg").dialog('close');
                    }
                }, {
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: WarningConfigMVC.Controller.save
                }]
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', WarningConfigMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#warning-config-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return WarningConfigMVC.Controller.edit();
            }
            if (name === "remove") {
                return WarningConfigMVC.Controller.remove();
            }
        },
        add: function () {
            var options = WarningConfigMVC.Util.getOptions();
            options.title = '新增预警配置';
            EasyUIUtils.openAddDlg(options);
        },
        edit: function () {
            var row = $('#warning-config-datagrid').datagrid('getSelected');
            if (row) {
                var options = WarningConfigMVC.Util.getOptions();
                options.iconCls = 'icon-edit';
                options.data = row;
                options.title = '修改[' + options.data.code + ']报警配置';
                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        find: function () {

            var url = WarningConfigMVC.Util.getSearchUrl();
            $("#warning-config-datagrid").datagrid({
                pageNumber: 1,
                pageSize: 50,
                url: url
            });
        },
        remove: function () {
            var row = $('#warning-config-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: WarningConfigMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#warning-config-datagrid',
                    gridUrl: WarningConfigMVC.URLs.list.url,
                    callback: function (rows) {
                    }
                };
                EasyUIUtils.remove(options);
            }
        },
        save: function () {
            var action = $('#modal-action').val();
            var options = {
                gridId: null,
                gridUrl: WarningConfigMVC.URLs.list.url,
                dlgId: "#warning-config-dlg",
                formId: "#warning-config-form",
                url: null,
                callback: function () {
                }
            };

            options.url = (action === "edit" ? WarningConfigMVC.URLs.edit.url : WarningConfigMVC.URLs.add.url);
            options.gridId = '#warning-config-datagrid';
            return EasyUIUtils.save(options);
        }
    },
    Util: {
        getSearchUrl: function () {

            var code = $("#search-code").textbox('getValue');
            var content = $("#search-content").textbox('getValue');
            return WarningConfigMVC.URLs.list.url + '?code=' + code + '&content=' + content;

        },
        getOptions: function () {
            return {
                dlgId: '#warning-config-dlg',
                formId: '#warning-config-form',
                actId: '#modal-action',
                rowId: '#warning-configId',
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