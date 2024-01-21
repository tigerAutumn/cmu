$(function () {
    RedPacketTheme.init();
});

var RedPacketTheme = {
    init: function () {
        RedPacketThemeMVC.View.initControl();
        RedPacketThemeMVC.View.bindEvent();
    }
};

var RedPacketThemeCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/activity/red_packet/',
    commonUrl: BossGlobal.ctxPath + '/v1/boss/common/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/',
    uploadUrl: BossGlobal.ctxPath + '/v1/boss/upload/'
};

var RedPacketThemeMVC = {
    URLs: {
        list: {
            url: RedPacketThemeCommon.baseUrl + 'theme/list',
            method: 'GET'
        },
        add: {
            url: RedPacketThemeCommon.baseUrl + 'theme/save',
            method: 'POST'
        },
        edit: {
            url: RedPacketThemeCommon.baseUrl + 'theme/edit',
            method: 'POST'
        },
        remove: {
            url: RedPacketThemeCommon.baseUrl + 'theme/remove',
            method: 'POST'
        },
        getBrokerList: {
            url: RedPacketThemeCommon.brokerBaseUrl + 'list',
            method: 'GET'
        },
        img: {
            url: RedPacketThemeCommon.uploadUrl + 'activity/upload',
            method: 'GET'
        }
    },
    Model: {
        allCurrency: {}
    },
    View: {
        initControl: function () {

            $.get(RedPacketThemeMVC.URLs.getBrokerList.url, "", function (_data) {
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

            $('#imageUrl-file').filebox({
                buttonText: '选择',
                buttonAlign: 'right',
                prompt: '选择图片',
                onChange: function (newVal, oldVal) {
                    if (newVal) {
                        RedPacketThemeMVC.Util.uploadImg();
                    }
                }
            });

            $('#red-packet-theme-dlg').dialog({
                closed: true,
                modal: false,
                width: 700,
                height: 400,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#red-packet-theme-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: function () {
                        RedPacketThemeMVC.Controller.save();
                    }
                }]
            });

            $('#red-packet-theme-datagrid').datagrid({
                method: 'GET',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: RedPacketThemeMVC.URLs.list.url,
                toolbar: [{
                    text: '新增',
                    iconCls: 'icon-add',
                    handler: function () {
                        RedPacketThemeMVC.Controller.add();
                    }
                }, '-', {
                    text: '修改',
                    iconCls: 'icon-edit1',
                    handler: function () {
                        RedPacketThemeMVC.Controller.modify();
                    }
                }, '-', {
                    text: '删除',
                    iconCls: 'icon-remove',
                    handler: function () {
                        RedPacketThemeMVC.Controller.remove();
                    }
                }, '-', {
                    text: '全部数据',
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#red-packet-theme-datagrid').datagrid('load', {});
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
                        field: 'themeName',
                        title: '主题名称',
                        width: 100
                    },
                    {
                        field: 'imageUrl',
                        title: '主题图片',
                        width: 100
                    },
                    {
                        field: 'greeting',
                        title: '祝福语',
                        width: 100
                    },
                    {
                        field: 'status',
                        title: '红包状态',
                        width: 100,
                        formatter: function (value) {
                            if (value === 1) {
                                return '已保存';
                            } else if (value === 2) {
                                return '已发布';
                            } else if (value === 3) {
                                return '已下架';
                            } else if (value === 4) {
                                return '默认主题';
                            }
                        }
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
                    return RedPacketThemeMVC.Controller.modify();
                },
                onLoadSuccess: function () {
                }
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', RedPacketThemeMVC.Controller.query);
        }
    },
    Controller: {
        add: function () {
            $('#action').val("save");
            var options = RedPacketThemeMVC.Util.getOptions();
            options.title = '新增红包主题纪录';
            EasyUIUtils.openAddDlg(options);
            $('#red-packet-theme-form').form('load', data);
        },
        save: function () {
            var options = {
                gridId: null,
                gridUrl: RedPacketThemeMVC.URLs.list.url,
                dlgId: "#red-packet-theme-dlg",
                formId: "#red-packet-theme-form",
                method: 'POST',
                url: null,
                callback: function () {
                }
            };

            var action = $('#action').val();
            if (action === "save") {
                options.url = RedPacketThemeMVC.URLs.add.url;
            } else if (action === "update") {
                options.url = RedPacketThemeMVC.URLs.edit.url;
            }
            options.gridId = '#red-packet-theme-datagrid';
            return EasyUIUtils.save(options);
        },
        modify: function () {
            var row = $('#red-packet-theme-datagrid').datagrid('getSelected');
            if (row) {
                $('#action').val("update");
                var options = RedPacketThemeMVC.Util.getOptions();
                options.iconCls = 'icon-edit';
                options.data = row;
                options.data.expiredDate = moment(new Date(row.expiredDate)).format("YYYY-MM-DD HH:mm:ss")
                options.title = '修改[' + options.data.themeName + ']的红包主题纪录';
                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#red-packet-theme-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: RedPacketThemeMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#red-packet-theme-datagrid',
                    gridUrl: RedPacketThemeMVC.URLs.list.url,
                    callback: function (rows) {
                    }
                };
                EasyUIUtils.remove(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        query: function () {
            var params = RedPacketThemeMVC.Util.getQueryParam();
            $('#red-packet-theme-datagrid').datagrid('load', params);
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#red-packet-theme-dlg',
                formId: '#red-packet-theme-form',
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
            var files = $("#imageUrl-file").filebox('files');

            var formData = new FormData();
            formData.append("file", files[0]);

            $.ajax({
                url: RedPacketThemeMVC.URLs.img.url,
                type: 'POST',
                data: formData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (data) {
                    if (data.code == 0) {
                        $('#imageUrl').val(data.data.fileName);
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
            var themeName = $("#query-red-packet-theme-themeName").textbox('getValue');
            var status = $("#query-red-packet-theme-status").combobox('getValue');

            var params = {
                themeName: themeName,
                status: status
            };

            return params;
        }
    }
};
