$(function () {
    ContractBalance.init();
});

var ContractBalance = {
    init: function () {
        ContractBalanceMVC.View.initControl();
        ContractBalanceMVC.View.bindEvent();
    }
};

var ContractBalanceCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/activity/team/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    uploadUrl: BossGlobal.ctxPath + '/v1/boss/upload/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/'
};

var ContractBalanceMVC = {
    URLs: {
        contractBalanceList: {
            url: ContractBalanceCommon.baseUrl + 'contract_balance/list',
            method: 'GET'
        },
        contractBalanceAdd: {
            url: ContractBalanceCommon.baseUrl + 'contract_balance/add',
            method: 'POST'
        },
        contractBalanceEdit: {
            url: ContractBalanceCommon.baseUrl + 'contract_balance/edit',
            method: 'POST'
        },
        contractBalanceRemove: {
            url: ContractBalanceCommon.baseUrl + 'contract_balance/remove',
            method: 'POST'
        },
        getBrokerList: {
            url: ContractBalanceCommon.brokerBaseUrl + 'list',
            method: 'GET'
        }
    },
    Model: {},
    View: {
        initControl: function () {

            $("#brokerId").combobox({
                url: ContractBalanceMVC.URLs.getBrokerList.url,
                method: 'get',
                valueField: "id",
                textField: "brokerName",
                editable: true,
                value: '',
                prompt: '选择券商',
                panelHeight: 300
            });

            $('#contractBalance-dlg').dialog({
                closed: true,
                modal: false,
                width: 600,
                height: 450,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#contractBalance-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: function () {
                        ContractBalanceMVC.Controller.save();
                    }
                }]
            });

            $('#contractBalance-datagrid').datagrid({
                method: 'GET',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                sortOrder: 'desc',
                pageSize: 50,
                pageNumber: 1,
                url: ContractBalanceMVC.URLs.contractBalanceList.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    text: '刷新',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#contractBalance-datagrid')
                    }
                }, '-', {
                    iconCls: 'icon-add',
                    text: '新增',
                    handler: function () {
                        ContractBalanceMVC.Controller.add();
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
                        field: 'userId',
                        title: '成员ID',
                        width: 100
                    },
                    {
                        field: 'currencyCode',
                        title: '币种',
                        width: 100
                    },
                    {
                        field: 'amount',
                        title: '模拟金',
                        width: 100
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
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });
        },
        bindEvent: function () {
            $('#contractBalance-btn-search').bind('click', function () {
                var queryParam = ContractBalanceMVC.Util.getQueryParams();
                $('#contractBalance-datagrid').datagrid('load', queryParam);
            });
        }
    },
    Controller: {
        add: function () {
            $('#action').val('save');
            var options = ContractBalanceMVC.Util.getOptions();
            options.title = '新增合约模拟金纪录';
            EasyUIUtils.openAddDlg(options);
        },
        save: function () {
            var options = {
                gridId: null,
                gridUrl: ContractBalanceMVC.URLs.contractBalanceList.url,
                dlgId: "#contractBalance-dlg",
                formId: "#contractBalance-form",
                method: 'POST',
                url: null,
                callback: function () {
                }
            };

            var action = $('#action').val();
            if (action === 'save') {
                options.url = ContractBalanceMVC.URLs.contractBalanceAdd.url;
            } else if (action === 'update') {
                options.url = ContractBalanceMVC.URLs.contractBalanceEdit.url;
            }
            options.gridId = '#contractBalance-datagrid';
            return EasyUIUtils.save(options);
        },
        modify: function () {
            var row = $('#contractBalance-datagrid').datagrid('getSelected');
            if (row) {
                $('#action').val('update');
                var options = ContractBalanceMVC.Util.getOptions();
                options.iconCls = 'icon-edit';
                options.data = row;
                options.title = '修改[' + options.data.userId + ']';
                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#contractBalance-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: ContractBalanceMVC.URLs.contractBalanceRemove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#contractBalance-datagrid',
                    gridUrl: ContractBalanceMVC.URLs.contractBalanceList.url,
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
                dlgId: '#contractBalance-dlg',
                formId: '#contractBalance-form',
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
            var userId = $("#contractBalance-userId").textbox("getValue");
            var currencyCode = $("#contractBalance-currencyCode").textbox("getValue");

            var params = {
                userId: userId,
                currencyCode: currencyCode
            };
            return params;
        }
    }
};