$(function () {
    ContractMember.init();
});

var ContractMember = {
    init: function () {
        ContractMemberMVC.View.initControl();
        ContractMemberMVC.View.bindEvent();
    }
};

var ContractMemberCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/activity/team/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    uploadUrl: BossGlobal.ctxPath + '/v1/boss/upload/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/'
};

var ContractMemberMVC = {
    URLs: {
        contractMemberList: {
            url: ContractMemberCommon.baseUrl + 'contract_member/list',
            method: 'GET'
        },
        contractMemberAdd: {
            url: ContractMemberCommon.baseUrl + 'contract_member/add',
            method: 'POST'
        },
        contractMemberEdit: {
            url: ContractMemberCommon.baseUrl + 'contract_member/edit',
            method: 'POST'
        },
        contractMemberRemove: {
            url: ContractMemberCommon.baseUrl + 'contract_member/remove',
            method: 'POST'
        },
        getBrokerList: {
            url: ContractMemberCommon.brokerBaseUrl + 'list',
            method: 'GET'
        },
        getTeamIdList: {
            url: ContractMemberCommon.baseUrl + 'contract_team/ids',
            method: 'GET'
        }
    },
    Model: {},
    View: {
        initControl: function () {

            $("#teamId").combobox({
                url: ContractMemberMVC.URLs.getTeamIdList.url,
                method: 'GET',
                valueField: "id",
                textField: "teamName",
                editable: true,
                value: '',
                prompt: '选择团队',
                panelHeight: 300
            });

            $("#brokerId").combobox({
                url: ContractMemberMVC.URLs.getBrokerList.url,
                method: 'get',
                valueField: "id",
                textField: "brokerName",
                editable: true,
                value: '',
                prompt: '选择券商',
                panelHeight: 300
            });

            $('#contractMember-dlg').dialog({
                closed: true,
                modal: false,
                width: 600,
                height: 450,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#contractMember-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: function () {
                        ContractMemberMVC.Controller.save();
                    }
                }]
            });

            $('#contractMember-datagrid').datagrid({
                method: 'GET',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                sortOrder: 'desc',
                pageSize: 50,
                pageNumber: 1,
                url: ContractMemberMVC.URLs.contractMemberList.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    text: '刷新',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#contractMember-datagrid')
                    }
                }, '-', {
                    iconCls: 'icon-add',
                    text: '新增',
                    handler: function () {
                        ContractMemberMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit',
                    text: '修改',
                    handler: function () {
                        ContractMemberMVC.Controller.modify();
                    }
                }, '-', {
                    iconCls: 'icon-remove',
                    text: '删除',
                    handler: function () {
                        ContractMemberMVC.Controller.remove();
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
                        width: 100,
                        sortable: true
                    },
                    {
                        field: 'teamId',
                        title: '团队ID',
                        width: 100
                    },
                    {
                        field: 'userId',
                        title: '成员ID',
                        width: 100
                    },
                    {
                        field: 'username',
                        title: '用户名',
                        width: 100
                    },
                    {
                        field: 'inviteId',
                        title: '邀请人',
                        width: 100
                    },
                    {
                        field: 'todayLiveness',
                        title: '当天活跃度',
                        width: 100
                    },
                    {
                        field: 'currencyCode',
                        title: '币种',
                        width: 100
                    },
                    {
                        field: 'balance',
                        title: '总资产',
                        width: 100
                    },
                    {
                        field: 'virtualBalance',
                        title: '虚拟总资产',
                        width: 100
                    },
                    {
                        field: 'pnl',
                        title: '当前盈亏',
                        width: 100
                    },
                    {
                        field: 'virtualPnl',
                        title: '虚拟盈亏',
                        width: 100
                    },
                    {
                        field: 'liveness',
                        title: '活跃度',
                        width: 100
                    },
                    {
                        field: 'term',
                        title: '期数',
                        width: 100
                    },
                    {
                        field: 'status',
                        title: '状态',
                        width: 100,
                        formatter: function (value) {
                            if (value === 0) {
                                return '正常';
                            } else if (value === 1) {
                                return '禁用';
                            }
                        }
                    },
                    {
                        field: 'brokerId',
                        title: '券商ID',
                        width: 100
                    },
                    {
                        field: 'createdDate',
                        title: '创建时间',
                        width: 100,
                        formatter: function (value) {
                            return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                        }
                    },
                    {
                        field: 'updatedDate',
                        title: '更新时间',
                        width: 100,
                        formatter: function (value) {
                            return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                        }
                    }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return ContractMemberMVC.Controller.modify();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });
        },
        bindEvent: function () {
            $('#contractMember-btn-search').bind('click', function () {
                var queryParam = ContractMemberMVC.Util.getQueryParams();
                $('#contractMember-datagrid').datagrid('load', queryParam);
            });
        }
    },
    Controller: {
        add: function () {
            $('#action').val('save');
            var options = ContractMemberMVC.Util.getOptions();
            options.title = '新增团队成员纪录';
            EasyUIUtils.openAddDlg(options);
            $('#balance').textbox('readonly', false);
            $('#todayLiveness').textbox('readonly', false);
            $('#pnl').textbox('readonly', false);
            $('#liveness').textbox('readonly', false);
        },
        save: function () {
            var options = {
                gridId: null,
                gridUrl: ContractMemberMVC.URLs.contractMemberList.url,
                dlgId: "#contractMember-dlg",
                formId: "#contractMember-form",
                method: 'POST',
                url: null,
                callback: function () {
                }
            };

            var action = $('#action').val();
            if (action === 'save') {
                options.url = ContractMemberMVC.URLs.contractMemberAdd.url;
            } else if (action === 'update') {
                options.url = ContractMemberMVC.URLs.contractMemberEdit.url;
            }
            options.gridId = '#contractMember-datagrid';
            return EasyUIUtils.save(options);
        },
        modify: function () {
            var row = $('#contractMember-datagrid').datagrid('getSelected');
            if (row) {
                $('#action').val('update');
                var options = ContractMemberMVC.Util.getOptions();
                options.iconCls = 'icon-edit';
                options.data = row;
                options.title = '修改[' + options.data.userId + ']';
                EasyUIUtils.openEditDlg(options);
                $('#balance').textbox('readonly', true);
                $('#todayLiveness').textbox('readonly', true);
                $('#pnl').textbox('readonly', true);
                $('#liveness').textbox('readonly', true);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#contractMember-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: ContractMemberMVC.URLs.contractMemberRemove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#contractMember-datagrid',
                    gridUrl: ContractMemberMVC.URLs.contractMemberList.url,
                    callback: function (rows) {
                    }
                };
                EasyUIUtils.remove(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#contractMember-dlg',
                formId: '#contractMember-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                gridId: null
            };
        },
        getQueryParams: function () {
            var teamId = $("#contractMember-teamId").textbox("getValue");
            var currencyCode = $("#contractMember-currencyCode").textbox("getValue");

            var params = {
                teamId: teamId,
                currencyCode: currencyCode
            };
            return params;
        }
    }
};