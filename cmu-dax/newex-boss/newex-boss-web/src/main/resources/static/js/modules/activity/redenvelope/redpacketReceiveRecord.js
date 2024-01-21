$(function () {
    RedPacketReceiveRecord.init();
});

var RedPacketReceiveRecord = {
    init: function () {
        RedPacketReceiveRecordMVC.View.initControl();
        RedPacketReceiveRecordMVC.View.bindEvent();
    }
};

var RedPacketReceiveRecordCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/activity/red_packet/',
    commonUrl: BossGlobal.ctxPath + '/v1/boss/common/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/',
    uploadUrl: BossGlobal.ctxPath + '/v1/boss/upload/',
    spotCurrencyUrl: BossGlobal.ctxPath + '/v1/boss/common/'
};

var RedPacketReceiveRecordMVC = {
    URLs: {
        list: {
            url: RedPacketReceiveRecordCommon.baseUrl + 'receive_record/list',
            method: 'GET'
        },
        add: {
            url: RedPacketReceiveRecordCommon.baseUrl + 'receive_record/save',
            method: 'POST'
        },
        edit: {
            url: RedPacketReceiveRecordCommon.baseUrl + 'receive_record/edit',
            method: 'POST'
        },
        remove: {
            url: RedPacketReceiveRecordCommon.baseUrl + 'receive_record/remove',
            method: 'POST'
        },
        getBrokerList: {
            url: RedPacketReceiveRecordCommon.brokerBaseUrl + 'list',
            method: 'GET'
        },
        img: {
            url: RedPacketReceiveRecordCommon.uploadUrl + 'activity/upload',
            method: 'GET'
        },
        spotCurrencyList: {
            url: RedPacketReceiveRecordCommon.spotCurrencyUrl + 'spot/currency',
            method: 'GET'
        }
    },
    Model: {
        allCurrency: {}
    },
    View: {
        initControl: function () {

            $.get(RedPacketReceiveRecordMVC.URLs.getBrokerList.url, "", function (_data) {
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

            $.get(RedPacketReceiveRecordMVC.URLs.spotCurrencyList.url, "", function (_data) {
                RedPacketReceiveRecordMVC.Model.allCurrency = _data;
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

            $('#red-packet-receive-record-dlg').dialog({
                closed: true,
                modal: false,
                width: 700,
                height: 400,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#red-packet-receive-record-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: function () {
                        RedPacketReceiveRecordMVC.Controller.save();
                    }
                }]
            });

            $('#red-packet-receive-record-datagrid').datagrid({
                method: 'GET',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: RedPacketReceiveRecordMVC.URLs.list.url,
                toolbar: [{
                    text: '新增',
                    iconCls: 'icon-add',
                    handler: function () {
                        RedPacketReceiveRecordMVC.Controller.add();
                    }
                }, '-', {
                    text: '修改',
                    iconCls: 'icon-edit1',
                    handler: function () {
                        RedPacketReceiveRecordMVC.Controller.modify();
                    }
                }, '-', {
                    text: '删除',
                    iconCls: 'icon-remove',
                    handler: function () {
                        RedPacketReceiveRecordMVC.Controller.remove();
                    }
                }, '-', {
                    text: '全部数据',
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#red-packet-receive-record-datagrid').datagrid('load', {});
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
                        field: 'receiveUserId',
                        title: '领红包的人',
                        width: 100
                    },
                    {
                        field: 'sendUserId',
                        title: '发红包的人',
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
                        field: 'sendNickName',
                        title: '发红包人昵称',
                        width: 100
                    },
                    {
                        field: 'receiveNickName',
                        title: '领红包人昵称',
                        width: 100
                    },
                    {
                        field: 'amount',
                        title: '领到的币种数量',
                        width: 100
                    },
                    {
                        field: 'redUid',
                        title: '红包唯一标识',
                        width: 150
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
                    return RedPacketReceiveRecordMVC.Controller.modify();
                },
                onLoadSuccess: function () {
                }
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', RedPacketReceiveRecordMVC.Controller.query);
        }
    },
    Controller: {
        add: function () {
            $('#sendUserId').textbox({readonly: false});
            $('#receiveUserId').textbox({readonly: false});
            $('#currencyCode').textbox({readonly: false});
            $('#amount').textbox({readonly: false});
            $('#sendNickName').textbox({readonly: false});
            $('#receiveNickName').textbox({readonly: false});

            $('#currencyId').combobox({readonly: false});
            $('#redType').combobox({readonly: false});
            $('#amountType').combobox({readonly: false});
            $('#brokerId').combobox({readonly: false});


            $('#action').val("save");
            var options = RedPacketReceiveRecordMVC.Util.getOptions();
            options.title = '新增红包领取纪录';
            EasyUIUtils.openAddDlg(options);
        },
        save: function () {
            var options = {
                gridId: null,
                gridUrl: RedPacketReceiveRecordMVC.URLs.list.url,
                dlgId: "#red-packet-receive-record-dlg",
                formId: "#red-packet-receive-record-form",
                method: 'POST',
                url: null,
                callback: function () {
                }
            };

            var action = $('#action').val();
            if (action === "save") {
                options.url = RedPacketReceiveRecordMVC.URLs.add.url;
            } else if (action === "update") {
                options.url = RedPacketReceiveRecordMVC.URLs.edit.url;
            }
            options.gridId = '#red-packet-receive-record-datagrid';
            return EasyUIUtils.save(options);
        },
        modify: function () {
            var row = $('#red-packet-receive-record-datagrid').datagrid('getSelected');
            if (row) {
                $('#sendUserId').textbox({readonly: true});
                $('#receiveUserId').textbox({readonly: true});
                $('#currencyCode').textbox({readonly: true});
                $('#amount').textbox({readonly: true});
                $('#sendNickName').textbox({readonly: true});
                $('#receiveNickName').textbox({readonly: true});

                $('#currencyId').combobox({readonly: true});
                $('#redType').combobox({readonly: true});
                $('#amountType').combobox({readonly: true});
                $('#brokerId').combobox({readonly: true});

                $('#action').val("update");
                var options = RedPacketReceiveRecordMVC.Util.getOptions();
                options.iconCls = 'icon-edit';
                options.data = row;
                options.data.expiredDate = moment(new Date(row.expiredDate)).format("YYYY-MM-DD HH:mm:ss")
                options.title = '修改[' + options.data.redUid + ']的红包领取纪录';
                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#red-packet-receive-record-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: RedPacketReceiveRecordMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#red-packet-receive-record-datagrid',
                    gridUrl: RedPacketReceiveRecordMVC.URLs.list.url,
                    callback: function (rows) {
                    }
                };
                EasyUIUtils.remove(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        query: function () {
            var params = RedPacketReceiveRecordMVC.Util.getQueryParam();
            $('#red-packet-receive-record-datagrid').datagrid('load', params);
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#red-packet-receive-record-dlg',
                formId: '#red-packet-receive-record-form',
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
        uploadImg: function () {
            var files = $("#avatarUrl-file").filebox('files');

            var formData = new FormData();
            formData.append("file", files[0]);

            $.ajax({
                url: RedPacketReceiveRecordMVC.URLs.img.url,
                type: 'POST',
                data: formData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (data) {
                    if (data.code == 0) {
                        $('#avatarUrl').val(data.data.fileName);
                    } else {
                        $.messager.alert('提示', data.msg, 'info');
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
        },
        getQueryParam: function () {
            var type = $("#query-red-packet-receive-record-type").combobox('getValue');
            var redUid = $("#query-red-packet-receive-record-redUid").textbox('getValue');
            var params = {
                type: type,
                redUid: redUid
            };
            return params;
        }
    }
};
