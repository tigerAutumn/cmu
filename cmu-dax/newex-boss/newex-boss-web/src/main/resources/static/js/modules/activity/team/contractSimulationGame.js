$(function () {
    ContractSimulationGame.init();
});

var ContractSimulationGame = {
    init: function () {
        ContractSimulationGameMVC.View.initControl();
        ContractSimulationGameMVC.View.bindEvent();
    }
};

var ContractSimulationGameCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/activity/team/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    uploadUrl: BossGlobal.ctxPath + '/v1/boss/upload/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/'
};

var ContractSimulationGameMVC = {
    URLs: {
        contractSimulationGameList: {
            url: ContractSimulationGameCommon.baseUrl + 'contract_simulation_game/list',
            method: 'GET'
        },
        contractSimulationGameAdd: {
            url: ContractSimulationGameCommon.baseUrl + 'contract_simulation_game/add',
            method: 'POST'
        },
        contractSimulationGameEdit: {
            url: ContractSimulationGameCommon.baseUrl + 'contract_simulation_game/edit',
            method: 'POST'
        },
        contractSimulationGameRemove: {
            url: ContractSimulationGameCommon.baseUrl + 'contract_simulation_game/remove',
            method: 'POST'
        },
        img: {
            url: ContractSimulationGameCommon.uploadUrl + 'public/upload',
            method: 'GET'
        },
        getBrokerList: {
            url: ContractSimulationGameCommon.brokerBaseUrl + 'list',
            method: 'GET'
        }
    },
    Model: {},
    View: {
        initControl: function () {

            $('.avatar').filebox({
                buttonText: '选择',
                buttonAlign: 'right',
                prompt: '选择图片',
                onChange: function (newVal, oldVal) {
                    if (newVal) {
                        ContractSimulationGameMVC.Util.uploadImg();
                    }
                }
            });

            $("#brokerId").combobox({
                url: ContractSimulationGameMVC.URLs.getBrokerList.url,
                method: 'get',
                valueField: "id",
                textField: "brokerName",
                editable: true,
                value: '',
                prompt: '选择券商',
                panelHeight: 300
            });

            $('#contractSimulationGame-dlg').dialog({
                closed: true,
                modal: false,
                width: 600,
                height: 450,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#contractSimulationGame-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: function () {
                        ContractSimulationGameMVC.Controller.save();
                    }
                }]
            });

            $('#contractSimulationGame-datagrid').datagrid({
                method: 'GET',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                sortOrder: 'desc',
                pageSize: 50,
                pageNumber: 1,
                url: ContractSimulationGameMVC.URLs.contractSimulationGameList.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    text: '刷新',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#contractSimulationGame-datagrid')
                    }
                }, '-', {
                    iconCls: 'icon-add',
                    text: '新增',
                    handler: function () {
                        ContractSimulationGameMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit',
                    text: '修改',
                    handler: function () {
                        ContractSimulationGameMVC.Controller.modify();
                    }
                }, '-', {
                    iconCls: 'icon-remove',
                    text: '删除',
                    handler: function () {
                        ContractSimulationGameMVC.Controller.remove();
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
                    },{
                        field: 'name',
                        title: '模拟赛名称',
                        width: 100,
                        sortable: true
                    },
                    {
                        field: 'contractCode',
                        title: '合约比对',
                        width: 100
                    },
                    {
                        field: 'startDate',
                        title: '开始时间',
                        width: 100,
                        formatter: function (value) {
                            return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                        }
                    },
                    {
                        field: 'endDate',
                        title: '结束时间',
                        width: 100,
                        formatter: function (value) {
                            return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                        }
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
                            if (value === 1) {
                                return '上线';
                            } else if (value === 2) {
                                return '下线';
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
                    return ContractSimulationGameMVC.Controller.modify();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });
        },
        bindEvent: function () {
            $('#contractSimulationGame-btn-search').bind('click', function () {
                var queryParam = ContractSimulationGameMVC.Util.getQueryParams();
                $('#contractSimulationGame-datagrid').datagrid('load', queryParam);
            });
        }
    },
    Controller: {
        add: function () {
            $('#action').val('save');
            var options = ContractSimulationGameMVC.Util.getOptions();
            options.title = '新增合约模拟赛纪录';
            EasyUIUtils.openAddDlg(options);
        },
        save: function () {
            var options = {
                gridId: null,
                gridUrl: ContractSimulationGameMVC.URLs.contractSimulationGameList.url,
                dlgId: "#contractSimulationGame-dlg",
                formId: "#contractSimulationGame-form",
                method: 'POST',
                url: null,
                callback: function () {
                }
            };

            var action = $('#action').val();
            if (action === 'save') {
                options.url = ContractSimulationGameMVC.URLs.contractSimulationGameAdd.url;
            } else if (action === 'update') {
                options.url = ContractSimulationGameMVC.URLs.contractSimulationGameEdit.url;
            }
            options.gridId = '#contractSimulationGame-datagrid';
            return EasyUIUtils.save(options);
        },
        modify: function () {
            var row = $('#contractSimulationGame-datagrid').datagrid('getSelected');
            if (row) {
                $('#action').val('update');
                var options = ContractSimulationGameMVC.Util.getOptions();
                options.iconCls = 'icon-edit';
                options.data = {
                    startDate: moment(new Date(row.startDate)).format("YYYY-MM-DD HH:mm:ss"),
                    endDate: moment(new Date(row.endDate)).format("YYYY-MM-DD HH:mm:ss"),
                    brokerId: row.brokerId,
                    contractCode: row.contractCode,
                    status: row.status,
                    id: row.id,
                    term: row.term,
                    name: row.name,
                    currencyCode: row.currencyCode
                };
                options.title = '修改[' + options.data.id + ']';
                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#contractSimulationGame-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: ContractSimulationGameMVC.URLs.contractSimulationGameRemove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#contractSimulationGame-datagrid',
                    gridUrl: ContractSimulationGameMVC.URLs.contractSimulationGameList.url,
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
                dlgId: '#contractSimulationGame-dlg',
                formId: '#contractSimulationGame-form',
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
            var startDate = $("#contractSimulationGame-startDate").datetimebox("getValue");
            var endDate = $("#contractSimulationGame-endDate").datetimebox("getValue");

            var params = {
                startDate: startDate,
                endDate: endDate
            };
            return params;
        }
    }
};