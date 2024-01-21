$(function () {
    MessTempaDs.init();
});

var MessTempaDs = {
    init: function () {
        MessTempMVC.View.initControl();
        MessTempMVC.View.bindEvent();
        MessTempMVC.View.bindValidate();
    }
};

var DsCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/integration/message-templates/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    commonUrl: BossGlobal.ctxPath + '/v1/boss/common/'
};

var MessTempMVC = {
    URLs: {
        add: {
            url: DsCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: DsCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: DsCommon.baseUrl + 'list',
            method: 'GET'
        },
        remove: {
            url: DsCommon.baseUrl + 'remove',
            method: 'POST'
        },
        OrderDetail: {
            url: DsCommon.baseUrl + 'detail',
            method: 'POST'
        },
        refresh: {
            url: DsCommon.baseUrl + 'refresh',
            method: 'GET'
        },
        Language: {
            url: DsCommon.commonUrl + 'lang'
        }
    },
    Model: {
        dbTypes: {},
        dbPoolTypes: {}
    },
    View: {
        initControl: function () {

            $('#locale,#temp_locale').combobox({
                url: MessTempMVC.URLs.Language.url,
                method: 'get',
                valueField: "code",
                textField: "code",
                editable: true,
                value: '',
                prompt: '选择语言',
                panelHeight: 300
            });

            $('#ds-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: MessTempMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        MessTempMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        MessTempMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-remove1',
                    handler: function () {
                        MessTempMVC.Controller.remove();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#ds-datagrid');
                    }
                }, '-', {
                    text: '刷新缓存',
                    iconCls: 'icon-refresh',
                    handler: function () {
                        MessTempMVC.Controller.refresh();
                    }
                }],
                loadFilter: function (src) {
                    if (!src.code) {
                        return src.data;
                    }
                    return $.messager.alert('失败', src.msg, 'error');
                },
                columns: [[{
                    field: 'code',
                    title: '模板编码',
                    width: 230,
                    sortable: true
                }, {
                    field: 'subject',
                    title: '邮件主题',
                    width: 100,
                    sortable: true
                }, {
                    field: 'sign',
                    title: '模板签名',
                    width: 100,
                    sortable: true
                }, {
                    field: 'locale',
                    title: '模板语言',
                    width: 50,
                    sortable: true
                }, {
                    field: 'type',
                    title: '模版类型',
                    width: 50,
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
                        }];
                        var buttons = [];
                        for (var i = 0; i < icons.length; i++) {
                            var tmpl = '<a href="#" title ="${title}" '
                                + 'onclick="MessTempMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: DsCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return MessTempMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            var pager = $("#ds-datagrid").datagrid("getPager");
            if (pager) {
                $(pager).pagination({
                    onSelectPage: function (pageNumber, pageSize) {

                        var url = MessTempMVC.Util.getSearchUrl();
                        $("#ds-datagrid").datagrid({
                            pageNumber: pageNumber,
                            pageSize: pageSize,
                            url: url
                        });

                    }
                });
            }

            $('#ds-dlg-detail').dialog({
                closed: true,
                modal: true,
                striped: true,
                collapsible: true,
                resizable: true,
                width: 910,
                height: window.screen.height - 350,
                maximizable: true,
                iconCls: 'icon-info',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-back',
                    handler: function () {
                        $("#ds-dlg-detail").dialog('close');
                    }
                }]
            });

            // dialogs
            $('#ds-dlg').dialog({
                closed: true,
                modal: false,
                width: 700,
                height: 480,
                iconCls: 'icon-add',
                buttons: [
                    {
                        text: '关闭',
                        iconCls: 'icon-no',
                        handler: function () {
                            $("#ds-dlg").dialog('close');
                        }
                    }, {
                        text: '保存',
                        iconCls: 'icon-save',
                        handler: MessTempMVC.Controller.save
                    }]
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', MessTempMVC.Controller.find);
        },
        bindValidate: function () {
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#ds-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return MessTempMVC.Controller.edit();
            }
            if (name === "remove") {
                return MessTempMVC.Controller.remove();
            }
        },
        add: function () {
            var options = MessTempMVC.Util.getOptions();
            options.title = '新增消息模版';
            EasyUIUtils.openAddDlg(options);
            MessTempMVC.Util.fillCombox("#dbType", "add", MessTempMVC.Model.dbTypes, "driverClass", "");
            MessTempMVC.Util.fillCombox("#dbPoolType", "add", MessTempMVC.Model.dbPoolTypes, "poolClass", "");
        },
        edit: function () {
            var row = $('#ds-datagrid').datagrid('getSelected');
            if (row) {
                var options = MessTempMVC.Util.getOptions();
                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改[' + options.data.code + ']模版';
                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        find: function () {
            var url = MessTempMVC.Util.getSearchUrl();
            $("#ds-datagrid").datagrid({
                pageNumber: 1,
                pageSize: 50,
                url: url
            });
        },
        remove: function () {
            var row = $('#ds-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: MessTempMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#ds-datagrid',
                    gridUrl: MessTempMVC.URLs.list.url,
                    callback: function (rows) {
                    }
                };
                EasyUIUtils.remove(options);
            }
        },
        refresh: function () {
            $.ajax({
                url: MessTempMVC.URLs.refresh.url,
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
                gridUrl: MessTempMVC.URLs.list.url,
                dlgId: "#ds-dlg",
                formId: "#ds-form",
                url: null,
                callback: function () {
                }
            };
            options.url = (action === "edit" ? MessTempMVC.URLs.edit.url : MessTempMVC.URLs.add.url);
            options.gridId = '#ds-datagrid';
            EasyUIUtils.save(options);
        }
    },
    Util: {
        getSearchUrl: function () {
            var subject = $("#subject").textbox('getValue');
            var locale = $("#locale").combobox('getValue');
            var type = $("#type").combobox('getValue');
            var code = $("#code").textbox('getValue');
            return MessTempMVC.URLs.list.url + '?subject=' + subject + '&locale=' + locale + '&type=' + type + '&code=' + code;

        },

        isRowSelected: function (func) {
            var row = $('#ds-datagrid').datagrid('getSelected');
            if (row) {
                func(row);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        getOptions: function () {
            return {
                dlgId: '#ds-dlg',
                formId: '#ds-form',
                actId: '#modal-action',
                rowId: '#dsId',
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
