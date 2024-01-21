$(function () {
    WithdrawChecker.init();
});

var WithdrawChecker = {
    init: function () {
        WithdrawCheckerMVC.View.initControl();
        WithdrawCheckerMVC.View.bindEvent();
        WithdrawCheckerMVC.View.bindValidate();
    }
};

var WithdrawCheckerCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/asset/withdraw/checker/',
    imageUrl: BossGlobal.ctxPath + '/v1/boss/upload/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    commonUrl: BossGlobal.ctxPath + '/v1/boss/common/'
};

var WithdrawCheckerMVC = {
    URLs: {
        edit: {
            url: WithdrawCheckerCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: WithdrawCheckerCommon.baseUrl + 'list',
            method: 'GET'
        },
        currency: {
            url: WithdrawCheckerCommon.commonUrl + 'spot/currency',
            method: 'GET'
        }
    },
    Model: {
        allCurrency: {},
        roles: {}
    },
    View: {
        initControl: function () {

            $.getJSON(WithdrawCheckerMVC.URLs.currency.url, "", function (_data) {
                WithdrawCheckerMVC.Model.allCurrency = _data;
                $("#currency").combobox({
                    data: _data,
                    valueField: "id",
                    textField: "symbol",
                    panelHeight: 300,
                    editable: true
                });
            });

            $('#withdraw-checker-status').combobox({
                valueField: 'id',
                textField: 'value',
                data: [
                    {
                        id: '0',
                        value: '待审核'
                    }, {
                        id: '1',
                        value: '审核通过'
                    }, {
                        id: '2',
                        value: '审核失败'
                    }, {
                        id: '3',
                        value: '审核已发送'
                    }
                ]
            });

            $('#status').combobox({
                valueField: 'id',
                textField: 'value',
                data: [
                    {
                        id: '0',
                        value: '待审核'
                    }, {
                        id: '1',
                        value: '审核通过'
                    }, {
                        id: '2',
                        value: '审核失败'
                    }, {
                        id: '3',
                        value: '审核已发送'
                    }
                ]
            });

            $('#withdraw-checker-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: WithdrawCheckerMVC.URLs.list.url,
                toolbar: [{
                    text: '审核',
                    iconCls: 'icon-edit1',
                    handler: function () {
                        WithdrawCheckerMVC.Controller.edit();
                    }
                }, '-', {
                    text: '全部数据',
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#withdraw-checker-datagrid').datagrid('load', {});
                    }
                }],
                loadFilter: function (src) {
                    if (!src.code) {
                        return src.data;
                    }
                    return $.messager.alert('失败', src.msg, 'error');
                },
                columns: [[{
                    field: 'traderNo',
                    title: 'traderNo',
                    width: 350,
                    sortable: true
                }, {
                    field: 'userId',
                    title: '用户ID',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return row.withdrawRecordDto.userId
                    }
                }, {
                    field: 'currency',
                    title: '币种',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        var currencies = WithdrawCheckerMVC.Model.allCurrency;
                        for (var i = 0; i < currencies.length; i++) {
                            if (currencies[i].id === row.withdrawRecordDto.currency) {
                                return currencies[i].symbol;
                            }
                        }
                    }
                }, {
                    field: 'amount',
                    title: '数量',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return row.withdrawRecordDto.amount
                    }
                }, {
                    field: 'fee',
                    title: '手续费',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return row.withdrawRecordDto.fee
                    }
                }, {
                    field: 'status',
                    title: '状态',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value === 0) {
                            return "待审核";
                        }
                        if (value === 1) {
                            return "审核通过";
                        }
                        if (value === 2) {
                            return "审核失败";
                        }
                        if (value === 3) {
                            return "审核已发送";
                        }
                    }
                }, {
                    field: 'updateDate',
                    title: '时间',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                    }

                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return WithdrawCheckerMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // dialogs
            $('#withdraw-checker-dlg').dialog({
                closed: true,
                modal: false,
                width: 700,
                height: 500,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#withdraw-checker-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: WithdrawCheckerMVC.Controller.save
                }]
            });


        },
        bindEvent: function () {
            $('#btn-search').bind('click', WithdrawCheckerMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#withdraw-checker-datagrid').datagrid('selectRow', index);

            if (name === "edit") {
                return WithdrawCheckerMVC.Controller.edit();
            }

        },
        edit: function () {
            var row = $('#withdraw-checker-datagrid').datagrid('getSelected');
            if (row) {
                var options = WithdrawCheckerMVC.Util.getOptions();

                options.iconCls = 'icon-edit1';
                options.title = '提现审核';
                options.data = row;
                options.data.userId = row.withdrawRecordDto.userId;
                options.data.currency = row.withdrawRecordDto.currency;
                options.data.amount = row.withdrawRecordDto.amount;
                options.data.fee = row.withdrawRecordDto.fee;
                options.data.updateDate = moment(new Date(row.updateDate)).format("YYYY-MM-DD HH:mm:ss");

                $('#currency').combobox('readonly', true);

                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        find: function () {
            var params = WithdrawCheckerMVC.Util.getQueryParams();

            $('#withdraw-checker-datagrid').datagrid('load', params);
        },
        save: function () {
            var action = $('#modal-action').val();

            var options = WithdrawCheckerMVC.Util.getOptions();

            if (action === "edit") {
                var row = $('#withdraw-checker-datagrid').datagrid('getSelected');
                //取得ID传给后台
                options.url = WithdrawCheckerMVC.URLs.edit.url;
            }

            //修改后刷新列表
            EasyUIUtils.save(options);

            return;
        }

    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#withdraw-checker-dlg',
                formId: '#withdraw-checker-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                url: null,
                gridId: '#withdraw-checker-datagrid',
                gridUrl: WithdrawCheckerMVC.URLs.list.url
            };
        },
        getQueryParams: function () {
            var traderNo = $('#withdraw-checker-traderNo').textbox('getValue');
            var status = $('#withdraw-checker-status').combobox('getValue');

            var params = {
                traderNo: traderNo,
                status: status
            };

            return params;
        }
    }
};