var LogUtils = {
    customOpLog: function (gridUrl, userId) {
        $('#custom-dlg').dialog({
            closed: true,
            modal: false,
            width: 400,
            height: 300,
            iconCls: 'icon-edit',
            buttons: [{
                text: '关闭',
                iconCls: 'icon-no',
                handler: function () {
                    $("#custom-dlg").dialog('close');
                }
            }, {
                text: '保存',
                iconCls: 'icon-save',
                handler: LogUtils.memo
            }]
        });
        var url = gridUrl + "?userId=" + userId;
        $('#custom-oplog-datagrid').datagrid({
            method: 'get',
            fit: true,
            autoRowWidth: true,
            fitColumns: true,
            singleSelect: true,
            pagination: true,
            rownumbers: true,
            sortOrder: 'desc',
            pageSize: 50,
            pageNumber: 1,
            url: url,
            toolbar: [{
                iconCls: 'icon-reload',
                handler: function () {
                    $('#custom-oplog-datagrid').datagrid('load', {});
                }
            }, '-', {
                iconCls: 'icon-edit',
                text: '添加备注',
                handler: function () {
                    var row = $('#custom-oplog-datagrid').datagrid('getSelected');
                    if (row) {
                        LogUtils.add(row);
                    } else {
                        $.messager.alert('警告', '请选中一条记录!', 'info');
                    }
                }
            }],
            loadFilter: function (src) {
                if (!src.code) {
                    return src.data;
                }
                return $.messager.alert('失败', src.msg, 'error');
            },
            columns: [[{
                field: 'id',
                title: 'ID',
                width: 100
            },
                {
                    field: 'createdDate',
                    title: '操作时间',
                    width: 100,
                    formatter: function (val) {
                        return moment(new Date(val)).format('YYYY-MM-DD HH:mm:ss');
                    }
                },
                {
                    field: 'source',
                    title: '操作名称',
                    width: 100
                },
                {
                    field: 'userId',
                    title: '客服ID',
                    width: 100
                },
                {
                    field: 'account',
                    title: '客服用户名',
                    width: 100
                },
                {
                    field: 'memo',
                    title: '备注',
                    width: 100
                }
            ]],
            onDblClickRow: function (rowIndex, rowData) {
                LogUtils.add(rowData);
            }
        });

    },
    userOpLog: function (gridUrl, userId) {
        var url = gridUrl + "?userId=" + userId;

        $('#user-oplog-datagrid').datagrid({
            method: 'get',
            fit: true,
            autoRowWidth: true,
            fitColumns: true,
            singleSelect: true,
            pagination: true,
            rownumbers: true,
            sortOrder: 'desc',
            pageSize: 50,
            pageNumber: 1,
            url: url,
            toolbar: [{
                iconCls: 'icon-reload',
                handler: function () {
                    $('#user-oplog-datagrid').datagrid('load', {});
                }
            }],
            loadFilter: function (src) {
                if (!src.code) {
                    return src.data;
                }
                return $.messager.alert('失败', src.msg, 'error');
            },
            columns: [[{
                field: 'dateTime',
                title: '操作时间',
                width: 100
            }, {
                field: 'type',
                    title: '操作名称',
                    width: 100
                },
                {
                    field: 'ipAddress',
                    title: 'IP地址',
                    width: 100
                }, {
                    field: 'region',
                    title: '区域',
                    width: 100
                }]],
            onDblClickRow: function (rowIndex, rowData) {
            },
            onLoadSuccess: function () {
            },
            onLoadError: function (data) {
                return $.messager.alert('失败', data.responseJSON.msg, 'error');
            }
        });

    },
    add: function (rowData) {
        $('#custom-memo').textbox('setValue', rowData.memo);
        $('#custom-dlg').dialog('open').dialog('center').dialog('setTitle', '添加备注');
        $('#id').val(rowData.id);
    },
    memo: function () {
        var url = UsersInfoMVC.URLs.opLog.memo.url;
        var id = $('#id').val();
        var memoMsg = $('#custom-memo').textbox('getValue');
        if (id != 0 && memoMsg != '') {
            d = {
                id: id,
                memo: memoMsg
            };
            $.post(url, d, function callback(data) {
                if (!data.code) {
                    $('#custom-oplog-datagrid').datagrid('load', {});
                    $('#custom-dlg').dialog('close');
                } else {
                    $.messager.alert('失败', "添加备注失败", 'error');
                }
            }, 'json');
        } else {
            $.messager.alert('info', "备注不能为空", 'info');
        }
    }
};