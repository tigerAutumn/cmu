$(function () {
    MessageBlacklist.init();
});

var MessageBlacklist = {
    init: function () {
        MessageBlacklistMVC.View.initControl();
        MessageBlacklistMVC.View.bindEvent();
        MessageBlacklistMVC.View.bindValidate();
        MessageBlacklistMVC.View.initData();
    }
};

var MessageBlacklistCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/integration/message-blacklist/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/'
};

var MessageBlacklistMVC = {
    URLs: {
        add: {
            url: MessageBlacklistCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: MessageBlacklistCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: MessageBlacklistCommon.baseUrl + 'list',
            method: 'GET'
        },
        remove: {
            url: MessageBlacklistCommon.baseUrl + 'remove',
            method: 'POST'
        },
        refresh: {
            url: MessageBlacklistCommon.baseUrl + 'refresh',
            method: 'GET'
        },
        getBrokerList: {
            url: MessageBlacklistCommon.brokerBaseUrl + 'list'
        }
    },
    Model: {},
    View: {
        initControl: function () {
            $("#brokerId").combobox({
                url: MessageBlacklistMVC.URLs.getBrokerList.url,
                method: 'get',
                valueField: "id",
                textField: "brokerName",
                editable: true,
                value: '',
                prompt: '选择券商',
                panelHeight: 300
            });
            $('#messageBlacklist-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: MessageBlacklistMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        MessageBlacklistMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        MessageBlacklistMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-remove1',
                    handler: function () {
                        MessageBlacklistMVC.Controller.remove();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#messageBlacklist-datagrid');
                    }
                }, '-', {
                    text: '刷新缓存',
                    iconCls: 'icon-refresh',
                    handler: function () {
                        MessageBlacklistMVC.Controller.refresh();
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
                    field: 'brokerId',
                    title: '券商',
                    width: 50,
                    hidden: true
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
                                + 'onclick="MessageBlacklistMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: MessageBlacklistCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return MessageBlacklistMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // dialogs
            $('#messageBlacklist-dlg').dialog({
                closed: true,
                modal: false,
                width: 660,
                height: 380,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#messageBlacklist-dlg").dialog('close');
                    }
                }, {
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: MessageBlacklistMVC.Controller.save
                }]
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', MessageBlacklistMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#messageBlacklist-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return MessageBlacklistMVC.Controller.edit();
            }
            if (name === "remove") {
                return MessageBlacklistMVC.Controller.remove();
            }
        },
        add: function () {
            var options = MessageBlacklistMVC.Util.getOptions();
            options.title = '新增黑名单配置';
            EasyUIUtils.openAddDlg(options);
            $('#type').combobox('setValue', "sms");
            $('#brokerId').combobox({readonly: false})
        },
        edit: function () {
            var row = $('#messageBlacklist-datagrid').datagrid('getSelected');
            $('#brokerId').combobox({readonly: true});
            if (row) {
                var options = MessageBlacklistMVC.Util.getOptions();
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
            var url = MessageBlacklistMVC.Util.getSearchUrl();
            EasyUIUtils.loadToDatagrid('#messageBlacklist-datagrid', url)
        },
        remove: function () {
            var row = $('#messageBlacklist-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: MessageBlacklistMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#messageBlacklist-datagrid',
                    gridUrl: MessageBlacklistMVC.URLs.list.url,
                    callback: function (rows) {
                    }
                };
                EasyUIUtils.remove(options);
            }
        },
        refresh: function () {
            $.ajax({
                url: MessageBlacklistMVC.URLs.refresh.url,
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
                gridUrl: MessageBlacklistMVC.URLs.list.url,
                dlgId: "#messageBlacklist-dlg",
                formId: "#messageBlacklist-form",
                url: null,
                callback: function () {
                }
            };

            options.url = (action === "edit" ? MessageBlacklistMVC.URLs.edit.url : MessageBlacklistMVC.URLs.add.url);
            options.gridId = '#messageBlacklist-datagrid';
            return EasyUIUtils.save(options);
        }
    },
    Util: {
        getSearchUrl: function () {
            var fieldName = $("#field-name").combobox('getValue');
            var keyword = $("#keyword").val();
            return MessageBlacklistMVC.URLs.list.url + '?fieldName=' + fieldName + '&keyword=' + keyword;

        },
        getOptions: function () {
            return {
                dlgId: '#messageBlacklist-dlg',
                formId: '#messageBlacklist-form',
                actId: '#modal-action',
                rowId: '#messageBlacklistId',
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