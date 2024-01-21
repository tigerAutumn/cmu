$(function () {
    UserFee.init();
});

var UserFee = {
    init: function () {
        UserFeeMVC.View.initControl();
        UserFeeMVC.View.bindEvent();
    }
};

var UserFeeCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/perpetual/user-fee',
    commonUrl: BossGlobal.ctxPath + '/v1/boss/common/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/'
};

var UserFeeMVC = {
    URLs: {
        list: {
            url: UserFeeCommon.baseUrl + '/list',
            method: 'GET'
        },
        add: {
            url: UserFeeCommon.baseUrl + '/add',
            method: 'POST'
        },
        edit: {
            url: UserFeeCommon.baseUrl + '/edit',
            method: 'POST'
        },
        remove: {
            url: UserFeeCommon.baseUrl + '/remove',
            method: 'POST'
        },
        getBrokerList: {
            url: UserFeeCommon.brokerBaseUrl + 'list',
            method: 'GET'
        },
        getPerpetualPairCodeList: {
            url: UserFeeCommon.commonUrl + 'perpetual/currencyPair/pairCodes',
            method: 'GET'
        }
    },
    Model: {
        allCurrency: {},
        allBroker: {}
    },
    View: {
        initControl: function () {

            $.get(UserFeeMVC.URLs.getPerpetualPairCodeList.url, "", function (_data) {

                var pairCodes = [];
                pairCodes.push({
                    "value": "",
                    "text": "全部"
                });
                for (var i = 0; i < _data.length; i++) {
                    var item = _data[i];
                    pairCodes.push({
                        "value": item.pairCode,
                        "text": item.pairCode
                    });
                }
                $('#pairCode').combobox({
                    data: _data,
                    valueField: "pairCode",
                    textField: "pairCode",
                    panelHeight: 300,
                    editable: true,
                    required: true
                });

                $('#query-pairCode').combobox({
                    data: pairCodes,
                    valueField: "value",
                    textField: "text",
                    panelHeight: 300,
                    editable: true,
                    required: true
                });
            });


            $.get(UserFeeMVC.URLs.getBrokerList.url, "", function (_data) {

                UserFeeMVC.Model.allBroker = _data;

                var queryBrokerID = [];
                queryBrokerID.push({
                    "value": "",
                    "text": "全部"
                });
                for (var i = 0; i < _data.length; i++) {
                    var item = _data[i];
                    queryBrokerID.push({
                        "value": item.id,
                        "text": item.brokerName
                    });
                }

                $("#brokerId").combobox({
                    data: _data,
                    valueField: "id",
                    textField: "brokerName",
                    panelHeight: 300,
                    editable: true,
                    required: true,
                    prompt: '选择券商'
                });

                $("#query-brokerId").combobox({
                    data: queryBrokerID,
                    valueField: "value",
                    textField: "text",
                    panelHeight: 300,
                    editable: true,
                    required: true
                });
            });

            $('#userFee-dlg').dialog({
                closed: true,
                modal: false,
                width: 450,
                height: 450,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#userFee-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: function () {
                        UserFeeMVC.Controller.save();
                    }
                }]
            });

            $('#userFee-datagrid').datagrid({
                method: 'GET',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: UserFeeMVC.URLs.list.url,
                toolbar: [{
                    text: '新增',
                    iconCls: 'icon-add',
                    handler: function () {
                        UserFeeMVC.Controller.add();
                    }
                }, '-', {
                    text: '修改',
                    iconCls: 'icon-edit1',
                    handler: function () {
                        UserFeeMVC.Controller.modify();
                    }
                }, '-', {
                    text: '删除',
                    iconCls: 'icon-remove',
                    handler: function () {
                        UserFeeMVC.Controller.remove();
                    }
                }, '-', {
                    text: '全部数据',
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#userFee-datagrid').datagrid('load', {});
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
                        field: 'pairCode',
                        title: '币对Code',
                        width: 100
                    },
                    {
                        field: 'userId',
                        title: '用户ID',
                        width: 100
                    },
                    {
                        field: 'side',
                        title: '类型',
                        width: 100,
                        formatter: function (value) {
                            if (value === 0) {
                                return 'both';
                            } else if (value === 1) {
                                return 'maker';
                            } else if (value === 2) {
                                return 'taker';
                            }
                        }
                    },
                    {
                        field: 'rate',
                        title: '手续费',
                        width: 100
                    },
                    {
                        field: 'brokerId',
                        title: '券商ID',
                        width: 150,
                        formatter: function (value) {
                            var brokerList = UserFeeMVC.Model.allBroker;
                            for (var i = 0; i < brokerList.length; i++) {
                                if (value === brokerList[i].id) {
                                    return brokerList[i].brokerName;
                                }
                            }
                            return value;
                        }
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
                        field: 'modifyDate',
                        title: '更新时间',
                        width: 100,
                        formatter: function (value) {
                            return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                        }
                    }
                ]],
                onDblClickRow: function (rowIndex, rowData) {
                    return UserFeeMVC.Controller.modify();
                },
                onLoadSuccess: function () {
                }
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', UserFeeMVC.Controller.query);
        }
    },
    Controller: {
        add: function () {
            $('#action').val("save");
            var options = UserFeeMVC.Util.getOptions();
            options.title = '新增合约用户手续费纪录';
            EasyUIUtils.openAddDlg(options);
        },
        save: function () {
            var options = {
                gridId: null,
                gridUrl: UserFeeMVC.URLs.list.url,
                dlgId: "#userFee-dlg",
                formId: "#userFee-form",
                method: 'POST',
                url: null,
                callback: function () {
                }
            };

            var action = $('#action').val();
            if (action === "save") {
                options.url = UserFeeMVC.URLs.add.url;
            } else if (action === "update") {
                options.url = UserFeeMVC.URLs.edit.url;
            }
            options.gridId = '#userFee-datagrid';
            return EasyUIUtils.save(options);
        },
        modify: function () {
            var row = $('#userFee-datagrid').datagrid('getSelected');
            if (row) {
                $('#action').val("update");
                var options = UserFeeMVC.Util.getOptions();
                options.iconCls = 'icon-edit';
                options.data = row;
                options.title = '修改[' + options.data.pairCode + ']';
                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#userFee-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: UserFeeMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#userFee-datagrid',
                    gridUrl: UserFeeMVC.URLs.list.url,
                    callback: function (rows) {
                    }
                };
                EasyUIUtils.remove(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        changePairCode: function () {
            var quote = $('#quote').combobox('getValue').toUpperCase();
            var base = $("#base").combobox('getValue').toUpperCase();
            var biz = $('#biz').combobox('getValue').toUpperCase();
            var prefix = biz.slice(0, 1);
            $('#pairCode').textbox('setValue', prefix + "_" + base + "_" + quote);
        },
        query: function () {
            var params = UserFeeMVC.Util.getQueryParam();
            $('#userFee-datagrid').datagrid('load', params);
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#userFee-dlg',
                formId: '#userFee-form',
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
        getQueryParam: function () {
            var pairCode = $("#query-pairCode").combobox('getValue');
            var side = $("#query-side").combobox('getValue');
            var brokerId = $("#query-brokerId").combobox('getValue');

            var params = {
                side: side,
                pairCode: pairCode,
                brokerId: brokerId
            };

            return params;
        }
    }
};
