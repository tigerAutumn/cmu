$(function () {
    ContractDescriptionConfig.init();
});

var ContractDescriptionConfig = {
    init: function () {
        ContractDescriptionConfigMVC.View.initControl();
        ContractDescriptionConfigMVC.View.bindEvent();
    }
};

var ContractDescriptionConfigCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/activity/team/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    uploadUrl: BossGlobal.ctxPath + '/v1/boss/upload/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/'
};

var ContractDescriptionConfigMVC = {
    URLs: {
        contractBalanceList: {
            url: ContractDescriptionConfigCommon.baseUrl + 'contract_description_config/list',
            method: 'GET'
        },
        contractBalanceAdd: {
            url: ContractDescriptionConfigCommon.baseUrl + 'contract_description_config/add',
            method: 'POST'
        },
        contractBalanceEdit: {
            url: ContractDescriptionConfigCommon.baseUrl + 'contract_description_config/edit',
            method: 'POST'
        },
        contractBalanceRemove: {
            url: ContractDescriptionConfigCommon.baseUrl + 'contract_description_config/remove',
            method: 'POST'
        },
        getBrokerList: {
            url: ContractDescriptionConfigCommon.brokerBaseUrl + 'list',
            method: 'GET'
        }
    },
    Model: {},
    View: {
        initControl: function () {
            

            $('#contractDescriptionConfig-dlg').dialog({
                closed: true,
                modal: false,
                width: 600,
                height: 450,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#contractDescriptionConfig-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: function () {
                        ContractDescriptionConfigMVC.Controller.save();
                    }
                }]
            });

            $('#contractDescriptionConfig-datagrid').datagrid({
                method: 'GET',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                sortOrder: 'desc',
                pageSize: 50,
                pageNumber: 1,
                url: ContractDescriptionConfigMVC.URLs.contractBalanceList.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    text: '刷新',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#contractDescriptionConfig-datagrid')
                    }
                }, '-', {
                    iconCls: 'icon-add',
                    text: '新增',
                    handler: function () {
                        ContractDescriptionConfigMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit',
                    text: '修改',
                    handler: function () {
                        ContractDescriptionConfigMVC.Controller.modify();
                    }
                }, '-', {
                    iconCls: 'icon-remove',
                    text: '删除',
                    handler: function () {
                        ContractDescriptionConfigMVC.Controller.remove();
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
                        field: 'startInterval',
                        title: '开始区间',
                        width: 100
                    },
                    {
                        field: 'endInterval',
                        title: '结束区间',
                        width: 100
                    },
                    {
                        field: 'description',
                        title: '描述',
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
                    return ContractDescriptionConfigMVC.Controller.modify();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });
        },
        bindEvent: function () {
            $('#contractDescriptionConfig-btn-search').bind('click', function () {
                var queryParam = ContractDescriptionConfigMVC.Util.getQueryParams();
                $('#contractDescriptionConfig-datagrid').datagrid('load', queryParam);
            });
        }
    },
    Controller: {
        add: function () {
            $('#action').val('save');
            var options = ContractDescriptionConfigMVC.Util.getOptions();
            options.title = '新增合约区间描述信息配置纪录';
            EasyUIUtils.openAddDlg(options);
        },
        save: function () {
            var options = {
                gridId: null,
                gridUrl: ContractDescriptionConfigMVC.URLs.contractBalanceList.url,
                dlgId: "#contractDescriptionConfig-dlg",
                formId: "#contractDescriptionConfig-form",
                method: 'POST',
                url: null,
                callback: function () {
                }
            };

            var action = $('#action').val();
            if (action === 'save') {
                options.url = ContractDescriptionConfigMVC.URLs.contractBalanceAdd.url;
            } else if (action === 'update') {
                options.url = ContractDescriptionConfigMVC.URLs.contractBalanceEdit.url;
            }
            options.gridId = '#contractDescriptionConfig-datagrid';
            return EasyUIUtils.save(options);
        },
        modify: function () {
            var row = $('#contractDescriptionConfig-datagrid').datagrid('getSelected');
            if (row) {
                $('#action').val('update');
                var options = ContractDescriptionConfigMVC.Util.getOptions();
                options.iconCls = 'icon-edit';
                options.data = row;
                options.title = '修改[' + options.data.id + ']';
                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#contractDescriptionConfig-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: ContractDescriptionConfigMVC.URLs.contractBalanceRemove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#contractDescriptionConfig-datagrid',
                    gridUrl: ContractDescriptionConfigMVC.URLs.contractBalanceList.url,
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
                dlgId: '#contractDescriptionConfig-dlg',
                formId: '#contractDescriptionConfig-form',
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
            var start = $("#contractDescriptionConfig-start").textbox("getValue");
            var end = $("#contractDescriptionConfig-end").textbox("getValue");

            var params = {
                startInterval: start,
                endInterval: end
            };
            return params;
        }
    }
};