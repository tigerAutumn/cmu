$(function () {
    RedPacketSendRecord.init();
});

var RedPacketSendRecord = {
    init: function () {
        RedPacketSendRecordMVC.View.initControl();
        RedPacketSendRecordMVC.View.bindEvent();
    }
};

var RedPacketSendRecordCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/activity/red_packet/',
    commonUrl: BossGlobal.ctxPath + '/v1/boss/common/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/',
    uploadUrl: BossGlobal.ctxPath + '/v1/boss/upload/',
    spotCurrencyUrl: BossGlobal.ctxPath + '/v1/boss/common/'
};

var RedPacketSendRecordMVC = {
    URLs: {
        list: {
            url: RedPacketSendRecordCommon.baseUrl + 'send_record/list',
            method: 'GET'
        },
        add: {
            url: RedPacketSendRecordCommon.baseUrl + 'send_record/save',
            method: 'POST'
        },
        edit: {
            url: RedPacketSendRecordCommon.baseUrl + 'send_record/edit',
            method: 'POST'
        },
        remove: {
            url: RedPacketSendRecordCommon.baseUrl + 'send_record/remove',
            method: 'POST'
        },
        getBrokerList: {
            url: RedPacketSendRecordCommon.brokerBaseUrl + 'list',
            method: 'GET'
        },
        img: {
            url: RedPacketSendRecordCommon.uploadUrl + 'activity/upload',
            method: 'GET'
        },
        spotCurrencyList: {
            url: RedPacketSendRecordCommon.spotCurrencyUrl + 'spot/currency',
            method: 'GET'
        }
    },
    Model: {
        allCurrency: {}
    },
    View: {
        initControl: function () {

            $.get(RedPacketSendRecordMVC.URLs.getBrokerList.url, "", function (_data) {
                $("#brokerId").combobox({
                    data: _data,
                    valueField: "id",
                    textField: "brokerName",
                    panelHeight: 300,
                    editable: true,
                    required: true,
                    prompt: '选择券商'
                });
            });

            $.get(RedPacketSendRecordMVC.URLs.spotCurrencyList.url, "", function (_data) {
                RedPacketSendRecordMVC.Model.allCurrency = _data;
                $("#currencyId").combobox({
                    data: _data,
                    valueField: "id",
                    textField: "symbol",
                    panelHeight: 300,
                    editable: true,
                    required: true,
                    onSelect: function (record) {
                        $('#currencyCode').textbox('setValue', record.symbol);
                    }
                });
            });


            $('#red-packet-send-record-dlg').dialog({
                closed: true,
                modal: false,
                width: 700,
                height: 400,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#red-packet-send-record-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: function () {
                        RedPacketSendRecordMVC.Controller.save();
                    }
                }]
            });

            $('#red-packet-send-record-datagrid').datagrid({
                method: 'GET',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: RedPacketSendRecordMVC.URLs.list.url,
                toolbar: [{
                    text: '新增',
                    iconCls: 'icon-add',
                    handler: function () {
                        RedPacketSendRecordMVC.Controller.add();
                    }
                }, '-', {
                    text: '修改',
                    iconCls: 'icon-edit1',
                    handler: function () {
                        RedPacketSendRecordMVC.Controller.modify();
                    }
                }, '-', {
                    text: '删除',
                    iconCls: 'icon-remove',
                    handler: function () {
                        RedPacketSendRecordMVC.Controller.remove();
                    }
                }, '-', {
                    text: '全部数据',
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#red-packet-send-record-datagrid').datagrid('load', {});
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
                        field: 'sendUserId',
                        title: '发红包的人',
                        width: 100
                    },
                    {
                        field: 'redUid',
                        title: '红包唯一标识',
                        width: 100
                    },
                    {
                        field: 'redType',
                        title: '红包类型',
                        width: 100,
                        formatter: function (value) {
                            if (value === 1) {
                                return '普通红包';
                            } else if (value === 2) {
                                return '口令红包';
                            }
                        }
                    },
                    {
                        field: 'currencyId',
                        title: '币种ID',
                        width: 100
                    },
                    {
                        field: 'currencyCode',
                        title: '币种名称',
                        width: 100
                    },
                    {
                        field: 'amountType',
                        title: '红包额度类型',
                        width: 100,
                        formatter: function (value) {
                            if (value === 1) {
                                return '固定数量';
                            } else if (value === 2) {
                                return '随机数量';
                            }
                        }
                    },
                    {
                        field: 'amount',
                        title: '红包中币种的总数量',
                        width: 100
                    },
                    {
                        field: 'brokerId',
                        title: '券商Id',
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
                    }
                ]],
                onDblClickRow: function (rowIndex, rowData) {
                    return RedPacketSendRecordMVC.Controller.modify();
                },
                onLoadSuccess: function () {
                }
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', RedPacketSendRecordMVC.Controller.query);
        }
    },
    Controller: {
        add: function () {
            $('#sendUserId').textbox({readonly: false});
            $('#currencyCode').textbox({readonly: false});
            $('#amount').textbox({readonly: false});

            $('#brokerId').combobox({readonly: false});
            $('#currencyId').combobox({readonly: false});
            $('#redType').combobox({readonly: false});
            $('#amountType').combobox({readonly: false});

            $('#action').val("save");
            var options = RedPacketSendRecordMVC.Util.getOptions();
            options.title = '新增红包发送纪录';
            EasyUIUtils.openAddDlg(options);
        },
        save: function () {
            var options = {
                gridId: null,
                gridUrl: RedPacketSendRecordMVC.URLs.list.url,
                dlgId: "#red-packet-send-record-dlg",
                formId: "#red-packet-send-record-form",
                method: 'POST',
                url: null,
                callback: function () {
                }
            };

            var action = $('#action').val();
            if (action === "save") {
                options.url = RedPacketSendRecordMVC.URLs.add.url;
            } else if (action === "update") {
                options.url = RedPacketSendRecordMVC.URLs.edit.url;
            }
            options.gridId = '#red-packet-send-record-datagrid';
            return EasyUIUtils.save(options);
        },
        modify: function () {
            var row = $('#red-packet-send-record-datagrid').datagrid('getSelected');
            if (row) {
                $('#sendUserId').textbox({readonly: true});
                $('#currencyCode').textbox({readonly: true});
                $('#amount').textbox({readonly: true});

                $('#brokerId').combobox({readonly: true});
                $('#currencyId').combobox({readonly: true});
                $('#redType').combobox({readonly: true});
                $('#amountType').combobox({readonly: true});


                $('#action').val("update");
                var options = RedPacketSendRecordMVC.Util.getOptions();
                options.iconCls = 'icon-edit';
                options.data = row;
                options.data.expiredDate = moment(new Date(row.expiredDate)).format("YYYY-MM-DD HH:mm:ss")
                options.title = '修改[' + options.data.nickName + ']的红包发送纪录';
                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#red-packet-send-record-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: RedPacketSendRecordMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#red-packet-send-record-datagrid',
                    gridUrl: RedPacketSendRecordMVC.URLs.list.url,
                    callback: function (rows) {
                    }
                };
                EasyUIUtils.remove(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        query: function () {
            var params = RedPacketSendRecordMVC.Util.getQueryParam();
            $('#red-packet-send-record-datagrid').datagrid('load', params);
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#red-packet-send-record-dlg',
                formId: '#red-packet-send-record-form',
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
            var userId = $("#query-red-packet-send-record-userId").textbox('getValue');
            var type = $("#query-red-packet-send-record-type").combobox('getValue');
            var params = {
                userId: userId,
                type: type
            };

            return params;
        }
    }
};
