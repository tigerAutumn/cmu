$(function () {
    Mining.init();
});

var Mining = {
    init: function () {
        MiningMVC.View.initControl();
        MiningMVC.View.bindEvent();
    }
};

var MiningCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/activity/mining/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    commonUrl: BossGlobal.ctxPath + '/v1/boss/common/'

};

var MiningMVC = {
    URLs: {
        Search: {
            url: MiningCommon.baseUrl+'list',
            method: 'GET'
        },
        Remove: {
            url: MiningCommon.baseUrl + 'delete',
            method:'POST'
        },
        Edit:{
            url:MiningCommon.baseUrl+'edit',
            method:'POST'
        },
        SearchSub: {
            url: MiningCommon.baseUrl + 'listSub',
            method: 'GET'
        },
        Product: {
            url: MiningCommon.commonUrl + 'product',
            method: 'GET'
        },
        Activity: {
            url: MiningCommon.baseUrl + 'getActivity',
            method: 'GET'
        }
    },
    Model: {},
    View: {
        initControl: function () {
            $('#activityIdd').combobox({
                url: MiningMVC.URLs.Activity.url,
                method: 'get',
                valueField: "activityId",
                textField: "name",
                editable: false
            });
            $('#mining-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: false,
                rownumbers: false,
                sortOrder: 'desc',
                pageSize: 50,
                pageNumber: 1,
                url: MiningMVC.URLs.Search.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                       EasyUIUtils.reloadDatagrid('#mining-datagrid')
                    }
                }, '-', {
                    iconCls: 'icon-add',
                    handler: function () {
                        MiningMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit',
                    handler: function () {
                        MiningMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-remove',
                    handler: function () {
                        MiningMVC.Controller.remove();
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
                        field: 'actId',
                        title: 'ID',
                        width: 100,
                        sortable: true,
                    },
                    {
                        field: 'activityIdd',
                        title: '活动Id',
                        width: 100,
                        sortable: true,
                    },
                    {
                        field: 'finishFlag',
                        title: '状态',
                        width: 100,
                        formatter:function(val){
                            if(val===0){
                                return "未开始"
                            }
                            if(val===1){
                                return "已结束"
                            }
                        }
                    },
                    {
                        field: 'titleCN',
                        title: '标题',
                        width: 100,
                        sortable: true,
                    },
                    {
                        field: 'startDate',
                        title: '开始时间',
                        width: 100,
                        formatter: function (val) {
                            return moment(new Date(val)).format('YYYY-MM-DD HH:mm:ss')
                        }
                    },
                    {
                        field: 'endDate',
                        title: '结束时间',
                        width: 100,
                        formatter: function (val) {
                            return moment(new Date(val)).format('YYYY-MM-DD HH:mm:ss')
                        }
                    },
                    {
                        field: 'currencyCode',
                        title: '挖币币种',
                        width: 100
                    },
                    {
                        field: 'totalLimit',
                        title: '矿池总量',
                        width: 100
                    },
                    {
                        field: 'productIds',
                        title: '挖矿币对',
                        width: 100,
                        sortable: true
                    },
                    {
                        field: 'reward',
                        title: '挖矿奖励比例',
                        width: 100,
                        sortable: true
                    },
                    {
                        field: 'dailyLimit',
                        title: '挖矿每日上限',
                        width: 100,
                        sortable: true
                    },
                    {
                        field: 'personDailyLimit',
                        title: '单人单日挖矿上限',
                        width: 100,
                        sortable: true
                    },
                    {
                        field: 'feeHoldReturn',
                        title: '手续费返回持仓比例',
                        width: 100,
                        sortable: true
                    },
                    {
                        field: 'personMining',
                        title: '参与用户数',
                        width: 100
                    },
                    {
                        field: 'projectAccount',
                        title: '项目方返还账号',
                        width: 100
                    },
                    {
                        field: 'personHolds',
                        title: '持有用户数',
                        width: 100,
                        sortable: true
                    },
                    {
                        field: 'miningReward',
                        title: '送矿币折合BTC',
                        width: 100,
                        sortable: true
                    },
                    {
                        field: 'feeReward',
                        title: '返还手续费折合BTC',
                        width: 100,
                        sortable: true
                    },
                    {
                        field: 'totalReward',
                        title: '全部返还折合BTC',
                        width: 100,
                        sortable: true
                    },
                    {
                        field: 'platformMinings',
                        title: '平台矿池总量',
                        width: 100
                    },
                    {
                        field: 'totalMinings',
                        title: '挖矿产出总量',
                        width: 100
                    },
                    {
                        field: 'online',
                        title: '是否上线',
                        width: 100,
                        formatter: function (val) {
                            if (val === 0) {
                                return "未上线"
                            }
                            if (val === 1) {
                                return "上线"
                            }
                            if (val === 2) {
                                return "预发上线"
                            }
                        }
                    },
                    {
                        field: 'effectiveHoldMinings',
                        title: '有效持仓总量',
                        width: 100
                    },
                    {
                        field: 'projectReturnFlag',
                        title: '项目方是否返还/操作',
                        width: 100,
                        formatter: function (val) {
                            if (val === 0) {
                                return '未返回'
                            }
                            if (val === 1) {
                                return '已返回'
                            }
                        }
                    },
                    {
                        field: 'explainLink',
                        title: '解释链接',
                        width: 100
                    }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return MiningMVC.Controller.edit();
                },
                onLoadSuccess: function () { //加载完成后,设置选中第一项
                    $('.pagination-page-list').hide();

                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });
            $('#mining-dlg').dialog({
                closed: true,
                modal: false,
                width: 800,
                height: 500,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#mining-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: MiningMVC.Controller.save
                }]
            });

            //sub
            $('#sub-mining-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: false,
                rownumbers: false,
                sortOrder: 'desc',
                pageSize: 50,
                pageNumber: 1,
                url: MiningMVC.URLs.SearchSub.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#sub-mining-datagrid')
                    }
                }
                // , '-', {
                //     iconCls: 'icon-add',
                //     handler: function () {
                //         MiningMVC.Controller.addSub();
                //     }
                // }
                , '-', {
                    iconCls: 'icon-info',
                    handler: function () {
                        MiningMVC.Controller.editSub();
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
                        sortable: true,
                    },
                    {
                        field: 'miningId',
                        title: '挖矿活动Id',
                        width: 100,
                        sortable: true,
                    }, {
                        field: 'lastUserBillId',
                        title: '账单Id',
                        width: 100,
                        sortable: true,
                    }
                    , {
                        field: 'startDate',
                        title: '开始时间',
                        width: 100,
                        formatter: function (val) {
                            return moment(new Date(val)).format('YYYY-MM-DD HH:mm:ss')
                        }
                    },
                    {
                        field: 'endDate',
                        title: '结束时间',
                        width: 100,
                        formatter: function (val) {
                            return moment(new Date(val)).format('YYYY-MM-DD HH:mm:ss')
                        }
                    }, {
                        field: 'currencyCode',
                        title: '挖币币种',
                        width: 100,
                        formatter: function (val) {
                            return val.toUpperCase();
                        }
                    }, {
                        field: 'miningReward',
                        title: '挖矿奖励',
                        width: 100,
                        sortable: true
                    }, {
                        field: 'productIds',
                        title: '挖矿币对',
                        width: 100,
                        sortable: true
                    },
                    {
                        field: 'dailyLimit',
                        title: '每天限额',
                        width: 100,
                        sortable: true
                    }, {
                        field: 'miningRewardNum',
                        title: '挖矿奖励数量',
                        width: 100,
                        sortable: true
                    }, {
                        field: 'feeReward',
                        title: '服务费奖励',
                        width: 100,
                        sortable: true
                    }, {
                        field: 'totalReward',
                        title: '总奖励',
                        width: 100,
                        sortable: true
                    }
                    , {
                        field: 'finishFlag',
                        title: '结束标记',
                        width: 100,
                        sortable: true
                    }
                    , {
                        field: 'effectiveHoldMinings',
                        title: '有效持仓',
                        width: 100,
                        sortable: true
                    }]],
                // onDblClickRow: function (rowIndex, rowData) {
                //     return MiningMVC.Controller.editSub();
                // },
                onLoadSuccess: function () { //加载完成后,设置选中第一项
                    $('.pagination-page-list').hide();

                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });
            $('#sub-mining-dlg').dialog({
                closed: true,
                modal: false,
                width: 800,
                height: 500,
                iconCls: 'icon-add',
                buttons: []
            });
            $("#productIds").tagbox({
                url: MiningMVC.URLs.Product.url,
                method: 'get',
                valueField: 'id',
                textField: 'code',
                limitToList: true,
                hasDownArrow: true,
                prompt: '选择币对',
                tagStyler: function (value) {
                }
            })
        },
        bindEvent: function () {
            $('#btn-search').bind('click', MiningMVC.Controller.find);
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#mining-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return MiningMVC.Controller.edit();
            }
        },
        find: function () {
            var url = MiningMVC.Util.getSearchUrl();
            $("#mining-datagrid").datagrid({
                pageNumber: 1,
                pageSize: 50,
                url: url
            });

        },
        add: function () {
            var options = MiningMVC.Util.getOptions();
            options.title = '新增挖矿活动';
            EasyUIUtils.openAddDlg(options);
            $("#platformMinings").textbox("setValue", 0);
            $("#totalMinings").textbox("setValue", 0);
            $("#personMining").textbox("setValue", 0);
            $("#miningReward").textbox("setValue", 0);
            $("#feeReward").textbox("setValue", 0);
            $("#totalReward").textbox("setValue", 0);
            $("#effectiveHoldMinings").textbox("setValue", 0);
        },
        addSub: function () {
        },
        save: function () {
            var options = {
                gridId: null,
                gridUrl: MiningMVC.URLs.Search.url,
                dlgId: "#mining-dlg",
                formId: "#mining-form",
                url: null,
                callback: function () {
                }
            };

            options.url = MiningMVC.URLs.Edit.url;
            options.gridId = '#mining-datagrid';
            return EasyUIUtils.save(options);
        },
        edit:function () {
            var row = $('#mining-datagrid').datagrid('getSelected');
            $("#miningId").textbox({readonly: false});      //设置下拉款为禁用
            $("#activityId").combobox({readonly: false});
            if (row) {
                var options = MiningMVC.Util.getOptions();
                options.iconCls = 'icon-edit';
                options.data = row;
                options.title = '修改[' + options.data.titleCN + ']';
                EasyUIUtils.openEditDlg(options);

            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#mining-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: MiningMVC.URLs.Remove.url,
                    data: {
                        id: row.actId
                    },
                    gridId: '#mining-datagrid',
                    gridUrl: MiningMVC.URLs.Search.url,
                    callback: function (rows) {
                    }
                };
                EasyUIUtils.remove(options);
            }
        },
        editSub: function () {
            var row = $('#sub-mining-datagrid').datagrid('getSelected');
            if (row) {
                var options = MiningMVC.Util.getSubOptions();
                options.iconCls = 'icon-info';
                options.data = row;
                options.title = '详情[' + options.data.id + ']';
                EasyUIUtils.openEditDlg(options);

            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        }

    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#mining-dlg',
                formId: '#mining-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                gridId: null,
            };
        },
        getSubOptions: function () {
            return {
                dlgId: '#sub-mining-dlg',
                formId: '#sub-mining-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                gridId: null,
            };
        },
        getSearchUrl: function () {
            var status = $("#status").combobox('getValue');
            var language = $("#language").combobox('getValue');
            return url = MiningMVC.URLs.Search.url + '?status=' + status + '&language=' + language;
        }
    }
};