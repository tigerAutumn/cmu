var ApiKeyUtils = {
    loadApiKey: function (gridUrl, userId) {
        $('#api-secret-datagrid').datagrid({
            method: 'get',
            fit: true,
            fitColumns: true,
            singleSelect: true,
            pagination: true,
            rownumbers: true,
            pageSize: 50,
            url: juicer(gridUrl, {userId: userId}),
            toolbar: [{
                iconCls: 'icon-reload',
                handler: function () {
                    $('#api-secret-datagrid').datagrid('load', {});
                }
            }, '-', {
                iconCls: 'icon-edit',
                handler: function () {
                    ApiKeyUtils.info();
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
                title: 'id',
                width: 150,
                hidden: 'true',
                sortable: true
            }, {
                field: 'userId',
                title: '用户id',
                width: 150,
                sortable: true
            }, {
                field: 'apiKey',
                title: 'apiKey',
                width: 150,
                sortable: true
            }, {
                field: 'authorities',
                title: '权限值',
                width: 150,
                sortable: true
            }, {
                field: 'expiredTime',
                title: '过期时间',
                width: 150,
                sortable: true,
                formatter: function (value, row, index) {
                    return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                }
            }, {
                field: 'rateLimit',
                title: '请求次数限制/s',
                width: 150,
                sortable: true
            }, {
                field: 'ipWhiteLists',
                title: 'ip白名单列表',
                width: 150,
                sortable: true
            }
            ]],
            onDblClickRow: function () {
                return ApiKeyUtils.info();
            }
        });
    },
    info: function () {
        var row = $('#api-secret-datagrid').datagrid('getSelected');

        if (row) {
            $('#ratelimit-dlg').dialog('open').dialog('center');
            $("#ratelimit-form").form('clear');
            $("#ratelimit").textbox('setValue', row.rateLimit);
            $("#ipWhiteLists").textbox('setValue', row.ipWhiteLists.split(',').join(',\n'));
            $("#authorities").textbox('setValue', row.authorities);
        } else {
            $.messager.alert('警告', '请选中一条记录!', 'info');
        }
    },
    secretsSave: function () {
        var ratelimit = $("#ratelimit").textbox('getValue');
        if (ratelimit == '') {
            $.messager.alert('提示', '请填写新的限流次数，格式为:次数/秒', 'info');
            return;
        }
        var authorities = $("#authorities").textbox('getValue');
        var ipWhiteLists = $("#ipWhiteLists").textbox('getValue');
        var row = $('#api-secret-datagrid').datagrid('getSelected');
        var url = juicer(UsersInfoMVC.URLs.secretsedit.url, {id: row.id});

        $.ajax({
            url: url,
            type: 'post',
            data: {
                rateLimit: ratelimit,
                ipWhiteLists: ipWhiteLists,
                authorities: authorities
            },
            success: function () {
                $("#ratelimit-dlg").dialog('close');

                $('#api-secret-datagrid').datagrid('load', {});
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
};