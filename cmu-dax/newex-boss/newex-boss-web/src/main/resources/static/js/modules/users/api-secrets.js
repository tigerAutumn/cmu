$(function () {
    ApiSecrets.init();
});

var ApiSecrets = {
    init: function () {
        ApiSecretsMVC.View.initControl();
        ApiSecretsMVC.View.bindEvent();
        ApiSecretsMVC.View.bindValidate();
        ApiSecretsMVC.View.initData();
    }
};

var ApiSecretsCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/users/api-secrets/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/'
};

var ApiSecretsMVC = {
    URLs: {
        edit: {
            url: ApiSecretsCommon.baseUrl + '${id}',
            method: 'POST'
        },
        list: {
            url: ApiSecretsCommon.baseUrl + '${userId}',
            method: 'GET'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {
            $('#api-secret-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: juicer(ApiSecretsMVC.URLs.list.url, {userId: 10000080}),
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#api-secret-datagrid');
                    }
                }, '-', {
                    iconCls: 'icon-edit',
                    handler: function () {
                        ApiSecretsMVC.Controller.info();
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
                }, {
                    field: 'options',
                    title: '操作',
                    width: 100,
                    formatter: function (value, row, index) {
                        var icons = [{
                            "name": "edit",
                            "title": "编辑"
                        }];
                        var buttons = [];
                        for (var i = 0; i < icons.length; i++) {
                            var tmpl = '<a href="#" title ="${title}" '
                                + 'onclick="ApiSecretsMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: ApiSecretsCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }
                ]],
                onDblClickRow: function () {
                    return ApiSecretsMVC.Controller.info();
                }
            });

            $('#ratelimit-dlg').dialog({
                closed: true,
                modal: false,
                width: 560,
                height: 400,
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#ratelimit-dlg").dialog('close');
                    }
                }, {
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: ApiSecretsMVC.Controller.save
                }]
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', ApiSecretsMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#api-secret-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return ApiSecretsMVC.Controller.info();
            }
        },
        info: function () {
            var row = $('#api-secret-datagrid').datagrid('getSelected');
            if (row) {
                $('#ratelimit-dlg').dialog('open').dialog('center');
                $("#modal-action").val("info");
                $("#ratelimit-form").form('clear');
                $("#ratelimit").textbox('setValue', row.rateLimit);
                $("#ipWhiteLists").textbox('setValue', row.ipWhiteLists.split(',').join(',\n'));
                $("#authorities").textbox('setValue', row.authorities);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        save: function () {
            var ratelimit = $("#ratelimit").textbox('getValue');
            if (ratelimit == '') {
                $.messager.alert('提示', '请填写新的限流次数，格式为:次数/秒', 'info');
                return;
            }
            var authorities = $("#authorities").textbox('getValue');
            var ipWhiteLists = $("#ipWhiteLists").textbox('getValue');
            var row = $('#api-secret-datagrid').datagrid('getSelected');
            var url = juicer(ApiSecretsMVC.URLs.edit.url, {id: row.id});
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
                    EasyUIUtils.reloadDatagrid('#api-secret-datagrid');
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
        find: function () {
            var userId = $("#userId").textbox('getValue');
            var url = juicer(ApiSecretsMVC.URLs.list.url, {userId: userId});
            EasyUIUtils.loadToDatagrid('#api-secret-datagrid', url)
        }
    }
};