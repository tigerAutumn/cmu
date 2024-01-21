$(function () {
    Tissue.init();
});

var Tissue = {
    init: function () {
        TissueMVC.View.initControl();
        TissueMVC.View.bindEvent();
    }
};

var TissueCommon = {
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    baseCommonUrl: BossGlobal.ctxPath + '/v1/boss/common/',
    tissueUrl: BossGlobal.ctxPath + '/v1/boss/users/tissue/'
};

var TissueMVC = {
    URLs: {
        getTissueListUrl: {
            url: TissueCommon.tissueUrl + 'list',
            method: 'GET'
        },
        getTissueChildAccountList: {
            url: TissueCommon.tissueUrl + 'childAccount',
            method: 'GET'
        },
        add: {
            url: TissueCommon.tissueUrl + 'save',
            method: 'POST'
        },
        edit: {
            url: TissueCommon.tissueUrl + 'edit',
            method: 'POST'
        },
        remove: {
            url: TissueCommon.tissueUrl + 'remove',
            method: 'POST'
        }
    },
    Model: {},
    View: {
        initControl: function () {

            $('#tissue-child-account-dlg').dialog({
                closed: true,
                modal: false,
                iconCls: 'icon-edit',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#tissue-child-account-dlg").dialog('close');
                    }
                }]
            });

            $('#tissue-dlg').dialog({
                closed: true,
                modal: false,
                width: 600,
                height: 400,
                iconCls: 'icon-edit1',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#tissue-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: function () {
                        TissueMVC.Controller.saveOrUpdate();
                    }
                }]
            });

            $('#tissue-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                sortOrder: 'desc',
                pageSize: 50,
                pageNumber: 1,
                url: TissueMVC.URLs.getTissueListUrl.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    text: '刷新',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#tissue-datagrid')
                    }
                }, '-', {
                    iconCls: 'icon-add',
                    text: '新增',
                    handler: function () {
                        TissueMVC.Controller.save()
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    text: '修改',
                    handler: function () {
                        TissueMVC.Controller.edit()
                    }
                }],
                loadFilter: function (src) {
                    if (!src.code) {
                        return src.data;
                    }
                    return $.messager.alert('失败', src.msg, 'error');
                },
                columns: [[
                    {
                        field: 'id',
                        title: 'ID',
                        width: 100
                    },
                    {
                        field: 'userId',
                        title: '用户ID',
                        width: 100
                    },
                    {
                        field: 'orgName',
                        title: '机构名称',
                        width: 100
                    },
                    {
                        field: 'orgType',
                        title: '机构类型',
                        width: 100,
                        formatter: function (value, row, index) {
                            if (value === 11) {
                                return '团队';
                            } else if (value === 12) {
                                return '企业';
                            } else if (value === 19) {
                                return '其他组织';
                            }
                        }
                    },
                    {
                        field: 'contactName',
                        title: '联系人',
                        width: 100
                    },
                    {
                        field: 'contactInfo',
                        title: '联系人电话',
                        width: 100
                    },
                    /*{
                        field: 'options',
                        title: '操作',
                        width: 100,
                        formatter: function (value, row, index) {
                            var icons = [{
                                "name": "lockChildAccount",
                                "title": "查看子账号"
                            }];
                            var buttons = [];
                            for (var i = 0; i < icons.length; i++) {
                                var tmpl = '<a href="#" title ="${title}" '
                                    + 'onclick="TissueMVC.Controller.lookChildAccount(\'${index}\')">'
                                    + '${title}</a>';
                                var data = {
                                    title: icons[i].title,
                                    name: icons[i].name,
                                    index: index
                                };
                                buttons.push(juicer(tmpl, data));
                            }
                            return buttons.join(' ');
                        }

                    },*/
                    {
                        field: 'createdDate',
                        title: '创建时间',
                        width: 100,
                        formatter: function (val) {
                            return moment(new Date(val)).format('YYYY-MM-DD HH:mm:ss');
                        }
                    },
                    {
                        field: 'updatedDate',
                        title: '更新时间',
                        width: 100,
                        formatter: function (val) {
                            return moment(new Date(val)).format('YYYY-MM-DD HH:mm:ss');
                        }
                    }]],
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                },
                onDblClickRow: function () {
                    return TissueMVC.Controller.edit();
                }
            });
        },
        bindEvent: function () {
            $('#tissue-btn-search').on('click', function () {
                $('#tissue-datagrid').datagrid('load', TissueMVC.Util.getSearchParam());
            });
        }
    },
    Controller: {
        lookChildAccount: function (index) {
            $('#tissue-datagrid').datagrid('selectRow', index);
            var row = $('#tissue-datagrid').datagrid('getSelected');
            if (row) {
                TissueMVC.Controller.loadChildAccountDataGrid(row.userId);
            }
        },
        save: function () {
            $('#action').val('save');
            var options = TissueMVC.Util.getOptions();
            options.title = '新增组织信息';
            EasyUIUtils.openAddDlg(options);
            $("#userId").textbox('readonly', false);
        },
        edit: function () {
            var row = $('#tissue-datagrid').datagrid('getSelected');
            if (row) {
                $('#action').val('update');
                var options = TissueMVC.Util.getOptions();
                options.iconCls = 'icon-edit';
                options.data = row;
                options.title = '修改[' + options.data.userId + ']';
                EasyUIUtils.openEditDlg(options);
                $("#userId").textbox('readonly', true);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        saveOrUpdate: function () {
            var options = TissueMVC.Util.getOptions();
            var action = $('#action').val();
            if (action === 'save') {
                options.url = TissueMVC.URLs.add.url;
            } else if (action === 'update') {
                options.url = TissueMVC.URLs.edit.url;
            }
            options.method = "POST";
            options.gridUrl = TissueMVC.URLs.getTissueListUrl.url;
            options.gridId = '#tissue-datagrid';

            return EasyUIUtils.save(options);
        },
        remove: function () {
            var row = $('#tissue-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: TissueMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#tissue-datagrid',
                    gridUrl: TissueMVC.URLs.getTissueListUrl.url,
                    callback: function (rows) {
                    }
                };
                EasyUIUtils.remove(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        loadChildAccountDataGrid: function (userId) {

            $('#tissue-child-account-datagrid').datagrid({
                method: 'GET',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                pageNumber: 1,
                url: TissueMVC.URLs.getTissueChildAccountList.url,
                loadFilter: function (src) {
                    if (!src.code) {
                        return src.data;
                    }
                    return $.messager.alert('失败', src.msg, 'error');
                },
                columns: [[
                    {
                        field: 'childAccount',
                        title: '子账号',
                        width: 100
                    },
                    {
                        field: 'mark',
                        title: '备注',
                        width: 100
                    },
                    {
                        field: 'valuation',
                        title: '资产',
                        width: 100
                    }]],
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            $('#tissue-child-account-dlg').dialog('open').dialog('setTitle', '子账号列表');
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#tissue-dlg',
                formId: '#tissue-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                gridId: null,
                gridUrl: null,
                method: null
            };
        },
        getSearchParam: function () {
            var userId = $("#tissue-userId").textbox("getValue");
            var orgName = $("#tissue-orgName").textbox("getValue");
            var contactName = $("#tissue-contactName").textbox("getValue");
            var orgType = $("#tissue-orgType").combobox("getValue");

            return {
                userId: userId,
                orgName: orgName,
                contactName: contactName,
                orgType: orgType
            };
        }
    }
};