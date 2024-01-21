$(function () {
    MessageWhitelist.init();
});

var MessageWhitelist = {
    init: function () {
        MessageWhitelistMVC.View.initControl();
        MessageWhitelistMVC.View.bindEvent();
        MessageWhitelistMVC.View.bindValidate();
        MessageWhitelistMVC.View.initData();
    }
};

var MessageWhitelistCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/integration/message-whitelist/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/'
};

var MessageWhitelistMVC = {
    URLs: {
        add: {
            url: MessageWhitelistCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: MessageWhitelistCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: MessageWhitelistCommon.baseUrl + 'list',
            method: 'GET'
        },
        remove: {
            url: MessageWhitelistCommon.baseUrl + 'remove',
            method: 'POST'
        },
        refresh: {
            url: MessageWhitelistCommon.baseUrl + 'refresh',
            method: 'GET'
        },
        getBrokerList: {
            url: MessageWhitelistCommon.brokerBaseUrl + 'list'
        }
    },
    Model: {},
    View: {
        initControl: function () {
            $("#brokerId").combobox({
                url: MessageWhitelistMVC.URLs.getBrokerList.url,
                method: 'get',
                valueField: "id",
                textField: "brokerName",
                editable: true,
                value: '',
                prompt: '选择券商',
                panelHeight: 300
            });
            $('#messageWhitelist-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: MessageWhitelistMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        MessageWhitelistMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        MessageWhitelistMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-remove1',
                    handler: function () {
                        MessageWhitelistMVC.Controller.remove();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#messageWhitelist-datagrid');
                    }
                }, '-', {
                    text: '刷新缓存',
                    iconCls: 'icon-refresh',
                    handler: function () {
                        MessageWhitelistMVC.Controller.refresh();
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
                        return "未知";
                    }
                }, {
                    field: 'name',
                    title: '名称',
                    width: 100,
                    sortable: true
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
                                + 'onclick="MessageWhitelistMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: MessageWhitelistCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return MessageWhitelistMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // dialogs
            $('#messageWhitelist-dlg').dialog({
                closed: true,
                modal: false,
                width: 660,
                height: 380,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#messageWhitelist-dlg").dialog('close');
                    }
                }, {
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: MessageWhitelistMVC.Controller.save
                }]
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', MessageWhitelistMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#messageWhitelist-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return MessageWhitelistMVC.Controller.edit();
            }
            if (name === "remove") {
                return MessageWhitelistMVC.Controller.remove();
            }
        },
        add: function () {
            var options = MessageWhitelistMVC.Util.getOptions();
            options.title = '新增黑名单配置';
            EasyUIUtils.openAddDlg(options);
            $('#type').combobox('setValue', "sms");
            $('#maxRequestTimes').textbox('setValue', "60");
            $('#brokerId').combobox({readonly: false});
        },
        edit: function () {
            var row = $('#messageWhitelist-datagrid').datagrid('getSelected');
            $('#brokerId').combobox({readonly: true});
            if (row) {
                var options = MessageWhitelistMVC.Util.getOptions();
                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改[' + options.data.name + ']黑名单配置';
                EasyUIUtils.openEditDlg(options);
                $('#type').combobox('setValue', row.type);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        find: function () {
            var url = MessageWhitelistMVC.Util.getSearchUrl();
            EasyUIUtils.loadToDatagrid('#messageWhitelist-datagrid', url)
        },
        remove: function () {
            var row = $('#messageWhitelist-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: MessageWhitelistMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#messageWhitelist-datagrid',
                    gridUrl: MessageWhitelistMVC.URLs.list.url,
                    callback: function (rows) {
                    }
                };
                EasyUIUtils.remove(options);
            }
        },
        refresh: function () {
            $.ajax({
                url: MessageWhitelistMVC.URLs.refresh.url,
                type: 'get',
                success: function (result) {
                    if (result.code) {
                        return $.messager.show({
                            title: '错误',
                            msg: result.msg
                        });
                    }
                    return $.messager.alert('提示', '刷新成功', 'info');
                },
                error: function (result) {
                    if (result.responseJSON.code == "403") {
                        $.messager.alert('提示', '没有权限执行该操作', 'error');
                    } else {
                        $.messager.alert('提示', result.responseJSON.msg, 'error');
                    }
                }
            });
        },
        save: function () {
            var action = $('#modal-action').val();
            var options = {
                gridId: null,
                gridUrl: MessageWhitelistMVC.URLs.list.url,
                dlgId: "#messageWhitelist-dlg",
                formId: "#messageWhitelist-form",
                url: null,
                callback: function () {
                }
            };

            options.url = (action === "edit" ? MessageWhitelistMVC.URLs.edit.url : MessageWhitelistMVC.URLs.add.url);
            options.gridId = '#messageWhitelist-datagrid';
            return EasyUIUtils.save(options);
        }
    },
    Util: {
        getSearchUrl: function () {
            var fieldName = $("#field-name").combobox('getValue');
            var keyword = $("#keyword").val();
            return MessageWhitelistMVC.URLs.list.url + '?fieldName=' + fieldName + '&keyword=' + keyword;

        },
        getOptions: function () {
            return {
                dlgId: '#messageWhitelist-dlg',
                formId: '#messageWhitelist-form',
                actId: '#modal-action',
                rowId: '#messageWhitelistId',
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