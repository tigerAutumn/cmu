$(function () {
    Business.init();
});

var Business = {
    init: function () {
        BusMVC.View.initControl();
        BusMVC.View.bindEvent();
        BusMVC.View.bindValidate();
    }
};

var BusinessCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/c2c/vendors/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
};

var BusMVC = {
    URLs: {
        edit: {
            url: BusinessCommon.baseUrl + 'updateUser',
            method: 'POST'
        },
        list: {
            url: BusinessCommon.baseUrl + 'list',
            method: 'GET'
        },
        freeze: {
            url: BusinessCommon.baseUrl + 'disable',
            method: 'POST'
        }
    },
    Model: {},
    View: {
        initControl: function () {
            $('#user-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: BusMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    text: '升级商家',
                    handler: function () {
                        BusMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        BusMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        $("#user-datagrid").datagrid({
                            pageNumber: 1,
                            pageSize: 50,
                            url: BusMVC.URLs.list.url
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
                    field: 'userId',
                    title: '用户id',
                    width: 100,
                    sortable: true
                }, {
                    field: 'isCertifiedUser',
                    title: '资质',
                    width: 100,
                    formatter: function (value) {
                        return value === false ? "普通用户" : "商家";
                    }
                }, {
                    field: 'createdDate',
                    title: '创建时间',
                    width: 100,
                    sortable: true,
                    formatter: function (value) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss");

                    }
                },
                    {
                        field: 'disabled',
                        title: '状态',
                        width: 100,
                        formatter: function (value) {
                            return value === false ? "正常" : "已冻结";
                        }
                    }, {
                        field: 'tags',
                        title: '标签',
                        width: 100
                    }, {
                        field: 'remark',
                        title: '备注',
                        width: 100
                    }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return BusMVC.Controller.edit();
                }
            });

            // dialogs
            $('#user-dlg').dialog({
                closed: true,
                modal: false,
                width: 450,
                height: 400,
                iconCls: 'icon-add',
                buttons: [{
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: BusMVC.Controller.save
                }, {
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#user-dlg").dialog('close');
                    }
                }]
            });
            var pager = $("#user-datagrid").datagrid("getPager");
            if (pager) {
                $(pager).pagination({
                    onSelectPage: function (pageNumber, pageSize) {
                        var url = BusMVC.Util.getSearchUrl();
                        $("#user-datagrid").datagrid({
                            pageNumber: pageNumber,
                            pageSize: pageSize,
                            url: url
                        });

                    }
                });
            }
        },
        bindEvent: function () {
            $('#btn-search').bind('click', BusMVC.Controller.find);
        },
        bindValidate: function () {
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#user-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return BusMVC.Controller.edit();
            }
            if (name === "disable") {
                return BusMVC.Controller.disable();
            }
        },
        add: function () {
            var options = BusMVC.Util.getOptions();
            options.title = '普通用户升级商家';
            EasyUIUtils.openAddDlg(options);
            $('#userId').textbox('readonly', false);
            $('#upgrade').textbox('setValue', true);
        },
        edit: function () {
            var row = $('#user-datagrid').datagrid('getSelected');
            if (row) {
                var options = BusMVC.Util.getOptions();
                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改[' + options.data.userId + ']';
                EasyUIUtils.openEditDlg(options);
                $('#userId').textbox('readonly', true);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        disable: function () {
            var row = $("#user-datagrid").datagrid('getSelected');
            if (row) {
                var options = BusMVC.Util.getOptions();
                options.data = row;
                var userId = options.data.userId;
                $.messager.confirm('确认', '是否确定操作【' + userId + '】?', function (r) {
                    if (r) {
                        var data = {
                            userId: userId,
                            status: options.data.disabled
                        };
                        $.post(BusMVC.URLs.freeze.url, data, function callback(data) {
                            if (!data.code) {
                                $.messager.alert('success', "success", 'success');
                            }
                        });
                    }
                });
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        find: function () {
            var url = BusMVC.Util.getSearchUrl();
            $("#user-datagrid").datagrid({
                pageNumber: 1,
                pageSize: 50,
                url: url
            });
            var userId = $("#search-user").textbox('getValue');
            var isCertifiedUser = $("#isCertifiedUser").combobox('getValue');
            var url = BusMVC.URLs.list.url + '?userId=' + userId + '&isCertifiedUser=' + isCertifiedUser;
            EasyUIUtils.loadToDatagrid('#user-datagrid', url)
        },

        save: function () {
            var action = $('#modal-action').val();
            var options = {
                gridId: null,
                gridUrl: BusMVC.URLs.list.url,
                dlgId: "#user-dlg",
                formId: "#user-form",
                url: null,
                callback: function () {
                }
            };
            options.url = BusMVC.URLs.edit.url;
            options.gridId = '#user-datagrid';
            $("#user-dlg").dialog('close');
            return EasyUIUtils.save(options);
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#user-dlg',
                formId: '#user-form',
                actId: '#modal-action',
                rowId: '#userId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                gridId: null
            };
        },
        getSearchUrl: function () {
            var userId = $("#search-user").textbox('getValue');
            var isCertifiedUser = $("#isCertifiedUser").combobox('getValue');
            return url = BusMVC.URLs.list.url + '?userId=' + userId + '&isCertifiedUser=' + isCertifiedUser;

        }
    }
};
