$(function () {
    ContractTeam.init();
});

var ContractTeam = {
    init: function () {
        ContractTeamMVC.View.initControl();
        ContractTeamMVC.View.bindEvent();
    }
};

var ContractTeamCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/activity/team/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    uploadUrl: BossGlobal.ctxPath + '/v1/boss/upload/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/'
};

var ContractTeamMVC = {
    URLs: {
        contractTeamList: {
            url: ContractTeamCommon.baseUrl + 'contract_team/list',
            method: 'GET'
        },
        contractTeamAdd: {
            url: ContractTeamCommon.baseUrl + 'contract_team/add',
            method: 'POST'
        },
        contractTeamEdit: {
            url: ContractTeamCommon.baseUrl + 'contract_team/edit',
            method: 'POST'
        },
        contractTeamRemove: {
            url: ContractTeamCommon.baseUrl + 'contract_team/remove',
            method: 'POST'
        },
        img: {
            url: ContractTeamCommon.uploadUrl + 'activity/upload',
            method: 'GET'
        },
        getBrokerList: {
            url: ContractTeamCommon.brokerBaseUrl + 'list',
            method: 'GET'
        }
    },
    Model: {},
    View: {
        initControl: function () {

            $('#avatar-file').filebox({
                buttonText: '选择',
                buttonAlign: 'right',
                prompt: '选择图片',
                onChange: function (newVal, oldVal) {
                    if (newVal) {
                        ContractTeamMVC.Util.uploadImg();
                    }
                }
            });

            $("#brokerId").combobox({
                url: ContractTeamMVC.URLs.getBrokerList.url,
                method: 'get',
                valueField: "id",
                textField: "brokerName",
                editable: true,
                value: '',
                prompt: '选择券商',
                panelHeight: 300
            });

            $('#contractTeam-dlg').dialog({
                closed: true,
                modal: false,
                width: 700,
                height: 450,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#contractTeam-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: function () {
                        ContractTeamMVC.Controller.save();
                    }
                }]
            });

            $('#contractTeam-datagrid').datagrid({
                method: 'GET',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                sortOrder: 'desc',
                pageSize: 50,
                pageNumber: 1,
                url: ContractTeamMVC.URLs.contractTeamList.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    text: '刷新',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#contractTeam-datagrid')
                    }
                }, '-', {
                    iconCls: 'icon-add',
                    text: '新增',
                    handler: function () {
                        ContractTeamMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit',
                    text: '修改',
                    handler: function () {
                        ContractTeamMVC.Controller.modify();
                    }
                }, '-', {
                    iconCls: 'icon-remove',
                    text: '删除',
                    handler: function () {
                        ContractTeamMVC.Controller.remove();
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
                        field: 'uid',
                        title: '团队唯一标识',
                        width: 100
                    },
                    {
                        field: 'teamName',
                        title: '团队名称zh_cn',
                        width: 100
                    },
                    {
                        field: 'teamNameEnUs',
                        title: '团队名称en_us',
                        width: 100
                    },
                    {
                        field: 'teamNumber',
                        title: '团队人数',
                        width: 100
                    },
                    {
                        field: 'avatar',
                        title: '团队头像',
                        width: 100
                    },
                    {
                        field: 'slogan',
                        title: '宣传语zh_cn',
                        width: 100
                    },
                    {
                        field: 'sloganEnUs',
                        title: '宣传语en_us',
                        width: 100
                    },
                    {
                        field: 'leaderId',
                        title: '团长ID',
                        width: 100
                    },
                    {
                        field: 'leaderName',
                        title: '团长名称zh_cn',
                        width: 100
                    },
                    {
                        field: 'leaderNameEnUs',
                        title: '团长名称en_us',
                        width: 100
                    },
                    {
                        field: 'leaderAccount',
                        title: '团长账号',
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
                        field: 'pnl',
                        title: '当前盈亏',
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
                    return ContractTeamMVC.Controller.modify();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });
        },
        bindEvent: function () {
            $('#contractTeam-btn-search').bind('click', function () {
                var queryParam = ContractTeamMVC.Util.getQueryParams();
                $('#contractTeam-datagrid').datagrid('load', queryParam);
            });
        }
    },
    Controller: {
        add: function () {
            $('#action').val('save');
            var options = ContractTeamMVC.Util.getOptions();
            options.title = '新增合约战队纪录';
            EasyUIUtils.openAddDlg(options);
            $('#balance').textbox('readonly', false);
            $('#pnl').textbox('readonly', false);
            $('#liveness').textbox('readonly', false);
        },
        save: function () {
            var options = {
                gridId: null,
                gridUrl: ContractTeamMVC.URLs.contractTeamList.url,
                dlgId: "#contractTeam-dlg",
                formId: "#contractTeam-form",
                method: 'POST',
                url: null,
                callback: function () {
                }
            };

            var action = $('#action').val();
            if (action === 'save') {
                options.url = ContractTeamMVC.URLs.contractTeamAdd.url;
            } else if (action === 'update') {
                options.url = ContractTeamMVC.URLs.contractTeamEdit.url;
            }
            options.gridId = '#contractTeam-datagrid';
            return EasyUIUtils.save(options);
        },
        modify: function () {
            var row = $('#contractTeam-datagrid').datagrid('getSelected');
            if (row) {
                $('#action').val('update');
                var options = ContractTeamMVC.Util.getOptions();
                options.iconCls = 'icon-edit';
                options.data = row;
                options.title = '修改[' + options.data.userId + ']';
                EasyUIUtils.openEditDlg(options);
                $('#balance').textbox('readonly', true);
                $('#pnl').textbox('readonly', true);
                $('#liveness').textbox('readonly', true);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#contractTeam-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: ContractTeamMVC.URLs.contractTeamRemove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#contractTeam-datagrid',
                    gridUrl: ContractTeamMVC.URLs.contractTeamList.url,
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
                dlgId: '#contractTeam-dlg',
                formId: '#contractTeam-form',
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
            var id = $("#contractTeam-uid").textbox("getValue");
            var teamName = $("#contractTeam-teamName").textbox("getValue");
            var teamNameEnUs = $("#contractTeam-teamNameEnUs").textbox("getValue");
            var teamNumber = $("#contractTeam-teamNumber").textbox("getValue");
            var balance = $("#contractTeam-balance").textbox("getValue");

            var params = {
                teamName: teamName,
                teamNameEnUs: teamNameEnUs,
                id: id,
                balance: balance,
                teamNumber: teamNumber
            };
            return params;
        },
        uploadImg: function () {
            var files = $("#avatar-file").filebox('files');

            var formData = new FormData();
            formData.append("file", files[0]);

            //调用apicontroller后台action方法，将form数据传递给后台处理。contentType必须设置为false,否则chrome和firefox不兼容
            $.ajax({
                url: ContractTeamMVC.URLs.img.url,
                type: 'POST',
                data: formData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (data) {
                    if (data.code == 0) {
                        $('#avatar').val(data.data.fileName);
                    } else {
                        $.messager.alert('提示', data.responseJSON.msg, 'info');
                    }
                },
                error: function (data) {
                    if (data.responseJSON.code == "403") {
                        $.messager.alert('提示', data.responseJSON.msg, 'info');

                    } else {
                        $.messager.alert('提示', 'error', 'info');
                    }
                }
            });
        }
    }
};