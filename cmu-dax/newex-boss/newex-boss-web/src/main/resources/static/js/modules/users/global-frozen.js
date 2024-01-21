$(function () {
    GlobalFrozen.init();
});

var GlobalFrozen = {
    init: function () {
        GlobalFrozenMVC.View.initControl();
        GlobalFrozenMVC.View.bindEvent();
        GlobalFrozenMVC.View.bindValidate();
        GlobalFrozenMVC.View.initData();
    }
};

var GlobalFrozenCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/users/frozen/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/'
};

var GlobalFrozenMVC = {
    URLs: {
        add: {
            url: GlobalFrozenCommon.baseUrl + 'global/add',
            method: 'POST'
        },
        edit: {
            url: GlobalFrozenCommon.baseUrl + 'global/edit',
            method: 'POST'
        },
        list: {
            url: GlobalFrozenCommon.baseUrl + 'global/list',
            method: 'get'
        },
        remove: {
            url: GlobalFrozenCommon.baseUrl + 'global/remove',
            method: 'POST'
        },
        freeze: {
            url: GlobalFrozenCommon.baseUrl + 'global/freeze',
            method: 'POST'
        },
        unfreeze: {
            url: GlobalFrozenCommon.baseUrl + 'global/unfreeze',
            method: 'POST'
        },
        getBrokerList: {
            url: GlobalFrozenCommon.brokerBaseUrl + 'list',
            method: 'GET'
        }
    },
    Model: {},
    View: {
        initControl: function () {

            $("#brokerId").combobox({
                url: GlobalFrozenMVC.URLs.getBrokerList.url,
                method: 'get',
                valueField: "id",
                textField: "brokerName",
                editable: true,
                value: '',
                prompt: '选择券商',
                panelHeight: 300
            });

            $('#frozen-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: GlobalFrozenMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        GlobalFrozenMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        GlobalFrozenMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-remove1',
                    handler: function () {
                        GlobalFrozenMVC.Controller.remove();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#frozen-datagrid');
                    }
                }, '-', {
                    iconCls: 'icon-lock',
                    handler: function () {
                        GlobalFrozenMVC.Controller.lock();
                    }
                }, '-', {
                    iconCls: 'icon-unlock',
                    handler: function () {
                        GlobalFrozenMVC.Controller.unlock();
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
                    title: '标识',
                    width: 50,
                    sortable: true
                }, {
                    field: 'code',
                    title: '代号',
                    width: 150,
                    sortable: true
                }, {
                    field: 'brokerId',
                    title: '券商',
                    width: 150,
                    hidden: true
                }, {
                    field: 'status',
                    title: '状态',
                    width: 80,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value == '1') {
                            return "已冻结";
                        }
                        if (value == '0') {
                            return "未冻结";
                        }
                        return "其他";
                    }
                }, {
                    field: 'cacheStatus',
                    title: '缓存状态',
                    width: 80,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value == '1') {
                            return "已冻结";
                        }
                        if (value == '0') {
                            return "未冻结";
                        }
                        return "其他";
                    }
                }, {
                    field: 'memo',
                    title: '说明',
                    width: 150,
                    sortable: true
                }, {
                    field: 'createdDate',
                    title: '创建时间',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                    }
                }, {
                    field: 'updatedDate',
                    title: '更新时间',
                    width: 100,
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
                        }, {
                            "name": "lock",
                            "title": "冻结"
                        }, {
                            "name": "unlock",
                            "title": "解冻"
                        }];
                        var buttons = [];
                        for (var i = 0; i < icons.length; i++) {
                            var tmpl = '<a href="#" title ="${title}" '
                                + 'onclick="GlobalFrozenMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: GlobalFrozenCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return GlobalFrozenMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // dialogs
            $('#frozen-dlg').dialog({
                closed: true,
                modal: false,
                width: 650,
                height: 300,
                iconCls: 'icon-add',
                buttons: [
                    {
                        text: '关闭',
                        iconCls: 'icon-no',
                        handler: function () {
                            $("#frozen-dlg").dialog('close');
                        }
                    }, {
                        text: '保存',
                        iconCls: 'icon-save',
                        handler: GlobalFrozenMVC.Controller.save
                    }]
            });
        },
        bindValidate: function () {
        },
        initData: function () {
        },
        bindEvent: function () {
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#frozen-datagrid').datagrid('selectRow', index);
            if (name === "add") {
                return GlobalFrozenMVC.Controller.add();
            }
            if (name === "remove") {
                return GlobalFrozenMVC.Controller.remove();
            }
            if (name === "lock") {
                return GlobalFrozenMVC.Controller.lock();
            }
            if (name === "unlock") {
                return GlobalFrozenMVC.Controller.unlock();
            }
        },
        add: function () {
            var options = GlobalFrozenMVC.Util.getOptions();
            options.title = '新增全局冻结状态';
            EasyUIUtils.openAddDlg(options);
        },
        edit: function () {
            var row = $('#frozen-datagrid').datagrid('getSelected');
            if (row) {
                var options = GlobalFrozenMVC.Util.getOptions();
                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改全局冻结状态';
                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        find: function () {
        },
        remove: function () {
            var row = $('#frozen-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: GlobalFrozenMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#frozen-datagrid',
                    gridUrl: GlobalFrozenMVC.URLs.list.url,
                    callback: function (rows) {
                    }
                };
                EasyUIUtils.remove(options);
            }
        },
        lock: function () {
            var row = $('#frozen-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: GlobalFrozenMVC.URLs.freeze.url,
                    data: {
                        name: row.code
                    },
                    gridId: '#frozen-datagrid',
                    gridUrl: GlobalFrozenMVC.URLs.list.url,
                    callback: function (rows) {
                    },
                    msg: '危险！您确定要冻结该业务么？冻结后所有用户将不能交易！'
                };
                return EasyUIUtils.confirmAction(options);
            }
        },
        unlock: function () {
            var row = $('#frozen-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: GlobalFrozenMVC.URLs.unfreeze.url,
                    data: {
                        name: row.code
                    },
                    gridId: '#frozen-datagrid',
                    gridUrl: GlobalFrozenMVC.URLs.list.url,
                    callback: function (rows) {
                    },
                    msg: '您确定要解冻该业务么？'
                };
                return EasyUIUtils.confirmAction(options);
            }
        },
        save: function () {
            var action = $('#modal-action').val();
            var options = {
                gridId: null,
                gridUrl: GlobalFrozenMVC.URLs.list.url,
                dlgId: "#frozen-dlg",
                formId: "#frozen-form",
                url: null,
                callback: function () {
                }
            };
            options.url = (action === "edit" ? GlobalFrozenMVC.URLs.edit.url : GlobalFrozenMVC.URLs.add.url);
            options.gridId = '#frozen-datagrid';
            return EasyUIUtils.save(options);
        }
    },
    Util: {
        isRowSelected: function (func) {
            var row = $('#frozen-datagrid').datagrid('getSelected');
            if (row) {
                func(row);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        getOptions: function () {
            return {
                dlgId: '#frozen-dlg',
                formId: '#frozen-form',
                actId: '#modal-action',
                rowId: '#id',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                gridId: '#frozen-datagrid',
            };
        }
    }
};
