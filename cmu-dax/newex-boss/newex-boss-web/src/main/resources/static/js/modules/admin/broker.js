$(function () {
    Membershipbroker.init();
});

var Membershipbroker = {
    init: function () {
        BrokerMVC.View.initControl();
        BrokerMVC.View.bindEvent();
        BrokerMVC.View.bindValidate();
    }
};

var BrokerCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/'
};

var BrokerMVC = {
    URLs: {
        add: {
            url: BrokerCommon.baseUrl + 'add' ,
            method: 'POST'
        },
        edit: {
            url: BrokerCommon.baseUrl + 'edit' ,
            method: 'POST'
        },
        list: {
            url: BrokerCommon.baseUrl + 'listByPage',
            method: 'GET'
        },
        remove: {
            url: BrokerCommon.baseUrl + 'remove' ,
            method: 'POST'
        },
        isSuperAdmin: {
            url: BrokerCommon.baseUrl + 'isSuperAdmin',
            method: 'GET'
        }
    },
    View: {
        initControl: function () {
            $('#broker-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: BrokerMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        BrokerMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        BrokerMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-remove1',
                    handler: function () {
                        BrokerMVC.Controller.remove();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        $("#broker-datagrid").datagrid({
                            pageNumber: 1,
                            pageSize: 50,
                            url: BrokerMVC.URLs.list.url
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
                    title: '券商ID',
                    width: 50,
                    sortable: true
                }, {
                    field: 'brokerHosts',
                    title: '券商域名',
                    width: 100,
                    sortable: true
                }, {
                    field: 'brokerName',
                    title: '券商名称',
                    width: 80,
                    sortable: true
                }, {
                    field: 'status',
                    title: '状态',
                    width: 100,
                    formatter: function (val) {
                        if (val == 0) {
                            return "启用"
                        } else {
                            return "禁用"
                        }
                    }
                }, {
                    field: 'sign',
                    title: '签名',
                    width: 80
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return BrokerMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });


            var pager = $("#broker-datagrid").datagrid("getPager");
            if (pager) {
                $(pager).pagination({
                    onSelectPage: function (pageNumber, pageSize) {
                        var url = BrokerMVC.Controller.getSearchUrl();
                        $("#broker-datagrid").datagrid({
                            pageNumber: pageNumber,
                            pageSize: pageSize,
                            url: url
                        });

                    }
                });
            }



            // dialogs
            $('#broker-dlg').dialog({
                closed: true,
                modal: false,
                width: 600,
                height: 380,
                maximizable: true,
                striped: true,
                collapsible: true,
                resizable: true,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#broker-dlg").dialog('close');
                    }
                }, {
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: BrokerMVC.Controller.save
                }]
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', BrokerMVC.Controller.find);
        },
        bindValidate: function () {
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#broker-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return BrokerMVC.Controller.edit();
            }
            if (name === "remove") {
                return BrokerMVC.Controller.remove();
            }
        },
        add: function () {
            var options = BrokerMVC.Util.getOptions();
            options.title = '新增券商';
            EasyUIUtils.openAddDlg(options);
        },
        edit: function () {
            $('#modal-action').val('edit');
            var row = $('#broker-datagrid').datagrid('getSelected');
            if (row) {
                var options = BrokerMVC.Util.getOptions();
                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改[' + options.data.brokerName + ']';
                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        getSearchUrl: function () {
            var fieldName = 'name';
            var keyword = $("#keyword").val();
            return url = BrokerMVC.URLs.list.url + '?fieldName=' + fieldName + '&keyword=' + keyword;

        },
        find: function () {
            var url = BrokerMVC.Controller.getSearchUrl();
            $("#broker-datagrid").datagrid({
                pageNumber: 1,
                pageSize: 50,
                url: url
            });
        },
        remove: function () {
            var row = $('#broker-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: BrokerMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#broker-datagrid',
                    gridUrl: BrokerMVC.URLs.list.url,
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
                gridUrl: BrokerMVC.URLs.list.url,
                dlgId: "#broker-dlg",
                formId: "#broker-form",
                url: null,
                callback: function () {
                }
            };
            options.url = (action === "edit" ? BrokerMVC.URLs.edit.url : BrokerMVC.URLs.add.url);
            options.gridId = '#broker-datagrid';

            return EasyUIUtils.save(options);
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#broker-dlg',
                formId: '#broker-form',
                actId: '#modal-action',
                rowId: '#brokerId',
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