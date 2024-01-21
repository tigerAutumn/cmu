$(function () {
    RedPacket.init();
});

var RedPacket = {
    init: function () {
        RedPacketMVC.View.initControl();
        RedPacketMVC.View.bindEvent();
    }
};

var RedPacketCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/activity/red_packet/',
    commonUrl: BossGlobal.ctxPath + '/v1/boss/common/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/',
    uploadUrl: BossGlobal.ctxPath + '/v1/boss/upload/'
};

var RedPacketMVC = {
    URLs: {
        list: {
            url: RedPacketCommon.baseUrl + 'config/list',
            method: 'GET'
        },
        add: {
            url: RedPacketCommon.baseUrl + 'config/save',
            method: 'POST'
        },
        edit: {
            url: RedPacketCommon.baseUrl + 'config/edit',
            method: 'POST'
        },
        remove: {
            url: RedPacketCommon.baseUrl + 'config/remove',
            method: 'POST'
        },
        themeList: {
            url: RedPacketCommon.baseUrl + 'config/theme/list',
            method: 'POST'
        },
        currencyList: {
            url: RedPacketCommon.baseUrl + 'config/amount_list/currency',
            method: 'POST'
        },
        getBrokerList: {
            url: RedPacketCommon.brokerBaseUrl + 'list',
            method: 'GET'
        },
        img: {
            url: RedPacketCommon.uploadUrl + 'activity/upload',
            method: 'GET'
        }
    },
    Model: {
        allCurrency: {}
    },
    View: {
        initControl: function () {

            $.get(RedPacketMVC.URLs.getBrokerList.url, "", function (_data) {
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

            $.get(RedPacketMVC.URLs.currencyList.url, "", function (_data) {
                $("#currencyId").combobox({
                    data: _data.data,
                    valueField: "currencyId",
                    textField: "currencyCode",
                    panelHeight: 300,
                    editable: true,
                    required: true
                });
            });

            $.get(RedPacketMVC.URLs.themeList.url, "", function (_data) {
                $("#themeId").combobox({
                    data: _data.data,
                    valueField: "id",
                    textField: "themeName",
                    panelHeight: 300,
                    editable: true,
                    required: true,
                    onSelect: function (record) {
                        $('#greeting').textbox('setValue', record.greeting);
                    }
                });
            });

            $('#avatarUrl-file').filebox({
                buttonText: '选择',
                buttonAlign: 'right',
                prompt: '选择图片',
                onChange: function (newVal, oldVal) {
                    if (newVal) {
                        RedPacketMVC.Util.uploadImg();
                    }
                }
            });

            $('#red-packet-dlg').dialog({
                closed: true,
                modal: false,
                width: 700,
                height: 500,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#red-packet-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: function () {
                        RedPacketMVC.Controller.save();
                    }
                }]
            });

            $('#red-packet-datagrid').datagrid({
                method: 'GET',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: RedPacketMVC.URLs.list.url,
                toolbar: [{
                    text: '新增',
                    iconCls: 'icon-add',
                    handler: function () {
                        RedPacketMVC.Controller.add();
                    }
                }, '-', {
                    text: '修改',
                    iconCls: 'icon-edit1',
                    handler: function () {
                        RedPacketMVC.Controller.modify();
                    }
                }, '-', {
                    text: '删除',
                    iconCls: 'icon-remove',
                    handler: function () {
                        RedPacketMVC.Controller.remove();
                    }
                }, '-', {
                    text: '全部数据',
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#red-packet-datagrid').datagrid('load', {});
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
                        title: '红包唯一标识',
                        width: 100
                    },
                    {
                        field: 'userId',
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
                        field: 'code',
                        title: '红包口令',
                        width: 100
                    },
                    {
                        field: 'codeMd5',
                        title: '口令md5值',
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
                        field: 'singleAmount',
                        title: '单个数量',
                        width: 100
                    },
                    {
                        field: 'randomAmount',
                        title: '随机数量',
                        width: 100
                    },
                    {
                        field: 'amount',
                        title: '红包总数量',
                        width: 100
                    },
                    {
                        field: 'number',
                        title: '红包个数',
                        width: 100
                    },
                    {
                        field: 'themeId',
                        title: '红包主题',
                        width: 100
                    },
                    {
                        field: 'greeting',
                        title: '祝福语',
                        width: 100
                    },
                    {
                        field: 'validityPeriod',
                        title: '红包有效期',
                        width: 100,
                        formatter: function (value) {
                            if (value === 1) {
                                return '24小时';
                            } else if (value === 2) {
                                return '3天';
                            } else if (value === 3) {
                                return '7天';
                            }
                        }
                    },
                    {
                        field: 'status',
                        title: '红包状态',
                        width: 100,
                        formatter: function (value) {
                            if (value === 1) {
                                return '初始化';
                            } else if (value === 2) {
                                return '领取中';
                            } else if (value === 3) {
                                return '领完';
                            } else if (value === 4) {
                                return '过期';
                            } else if (value === 5) {
                                return '已退回';
                            }
                        }
                    },
                    {
                        field: 'nickName',
                        title: '昵称',
                        width: 100
                    },
                    {
                        field: 'avatarUrl',
                        title: '头像',
                        width: 100
                    },
                    {
                        field: 'intro',
                        title: '介绍',
                        width: 100
                    },
                    {
                        field: 'expiredDate',
                        title: '红包过期时间',
                        width: 100,
                        formatter: function (value) {
                            return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                        }
                    },
                    {
                        field: 'redUrl',
                        title: '红包生成url',
                        width: 150
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
                        field: 'modifyDate',
                        title: '更新时间',
                        width: 100,
                        formatter: function (value) {
                            return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                        }
                    }
                ]],
                onDblClickRow: function (rowIndex, rowData) {
                    return RedPacketMVC.Controller.modify();
                },
                onLoadSuccess: function () {
                }
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', RedPacketMVC.Controller.query);
        }
    },
    Controller: {
        add: function () {
            $('#number').textbox({readonly: false});
            $('#amount').textbox({readonly: false});
            $('#currencyId').combobox({readonly: false});
            $('#amountType').combobox({readonly: false});
            $('#redType').combobox({readonly: false});
            $('#randomAmount').textbox({readonly: false});
            $('#singleAmount').textbox({readonly: false});
            $('#code').textbox({readonly: false});
            $('#userId').textbox({readonly: false});


            $('#action').val("save");
            var options = RedPacketMVC.Util.getOptions();
            options.title = '新增红包纪录';
            EasyUIUtils.openAddDlg(options);
        },
        save: function () {
            var options = {
                gridId: null,
                gridUrl: RedPacketMVC.URLs.list.url,
                dlgId: "#red-packet-dlg",
                formId: "#red-packet-form",
                method: 'POST',
                url: null,
                callback: function () {
                }
            };

            var action = $('#action').val();
            if (action === "save") {
                options.url = RedPacketMVC.URLs.add.url;
            } else if (action === "update") {
                options.url = RedPacketMVC.URLs.edit.url;
            }
            options.gridId = '#red-packet-datagrid';
            return EasyUIUtils.save(options);
        },
        modify: function () {
            var row = $('#red-packet-datagrid').datagrid('getSelected');
            if (row) {
                $('#number').textbox({readonly: true});
                $('#amount').textbox({readonly: true});
                $('#currencyId').combobox({readonly: true});
                $('#amountType').combobox({readonly: true});
                $('#redType').combobox({readonly: true});
                $('#randomAmount').textbox({readonly: true});
                $('#singleAmount').textbox({readonly: true});
                $('#code').textbox({readonly: true});
                $('#userId').textbox({readonly: true});

                $('#action').val("update");
                var options = RedPacketMVC.Util.getOptions();
                options.iconCls = 'icon-edit';
                options.data = row;
                options.data.expiredDate = moment(new Date(row.expiredDate)).format("YYYY-MM-DD HH:mm:ss")
                options.title = '修改[' + options.data.nickName + ']的红包纪录';
                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#red-packet-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: RedPacketMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#red-packet-datagrid',
                    gridUrl: RedPacketMVC.URLs.list.url,
                    callback: function (rows) {
                    }
                };
                EasyUIUtils.remove(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        query: function () {
            var params = RedPacketMVC.Util.getQueryParam();
            $('#red-packet-datagrid').datagrid('load', params);
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#red-packet-dlg',
                formId: '#red-packet-form',
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
                url: RedPacketMVC.URLs.img.url,
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
            var userId = $("#query-red-packet-userId").textbox('getValue');
            var type = $("#query-red-packet-type").combobox('getValue');
            var status = $("#query-red-packet-status").combobox('getValue');
            var validTime = $("#query-red-packet-valid-time").combobox('getValue');

            var params = {
                userId: userId,
                type: type,
                validTime: validTime,
                status: status
            };

            return params;
        }
    }
};
