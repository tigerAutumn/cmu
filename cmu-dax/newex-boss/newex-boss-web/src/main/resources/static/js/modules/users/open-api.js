$(function () {
    OpenApi.init();
});

var OpenApi = {
    init: function () {
        OpenApiMVC.View.initControl();
        OpenApiMVC.View.bindEvent();
        OpenApiMVC.View.bindValidate();
        OpenApiMVC.View.initData();
    }
};

var DsCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/users/ip-rate-limits/',
    baseSecretUrl: BossGlobal.ctxPath + '/v1/boss/users/api-secrets/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/',
    keys: {
        dbType: 'dbType',
        dbPoolType: 'dbPoolType'
    }
};

var OpenApiMVC = {
    URLs: {
        add: {
            url: DsCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: DsCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: DsCommon.baseUrl + 'list',
            method: 'get'
        },
        remove: {
            url: DsCommon.baseUrl + 'remove',
            method: 'POST'
        },
        OrderDetail: {
            url: DsCommon.baseUrl + 'detail',
            method: 'POST'
        },
        iplist: {
            url: DsCommon.baseSecretUrl + '${userId}',
            method: 'GET'
        },
        ipedit: {
            url: DsCommon.baseSecretUrl + '${id}',
            method: 'POST'
        },
        getlist: {
            url: DsCommon.baseSecretUrl + 'list',
            method: 'GET'
        },
        refresh: {
            url: DsCommon.baseUrl + 'refresh',
            method: 'GET'
        },
        getBrokerList: {
            url: DsCommon.brokerBaseUrl + 'list',
            method: 'GET'
        }
    },
    Model: {
        dbTypes: {},
        dbPoolTypes: {}
    },
    View: {
        initControl: function () {
            $("#brokerId-ip").combobox({
                url: OpenApiMVC.URLs.getBrokerList.url,
                method: 'get',
                valueField: "id",
                textField: "brokerName",
                editable: true,
                value: '',
                prompt: '选择券商',
                panelHeight: 300
            });
            $('#ipRateLimit-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: OpenApiMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        OpenApiMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        OpenApiMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-remove1',
                    handler: function () {
                        OpenApiMVC.Controller.remove();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        $("#ipRateLimit-datagrid").datagrid({
                            pageNumber: 1,
                            pageSize: 50,
                            url: OpenApiMVC.URLs.list.url
                        });
                        //EasyUIUtils.reloadDatagrid('#ipRateLimit-datagrid');
                    }
                }, '-', {
                    text: '刷新缓存',
                    iconCls: 'icon-refresh',
                    handler: function () {
                        OpenApiMVC.Controller.refresh();
                    }
                }],
                loadFilter: function (src) {
                    if (!src.code) {
                        return src.data;
                    }
                    return $.messager.alert('失败', src.msg, 'error');
                },
                columns: [[{

                    field: 'ip',
                    title: 'IP',
                    width: 100,
                    sortable: true
                }, {
                    field: 'rateLimit',
                    title: '限制请求次数/s',
                    width: 100,
                    sortable: true
                }, {
                    field: 'brokerId',
                    title: '券商Id',
                    width: 100,
                    hidden: true
                }, {
                    field: 'memo',
                    title: '备注',
                    width: 100,
                    sortable: true
                }, {
                    field: 'createdDate',
                    title: '创建时间',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                    }
                }, {
                    field: 'updatedDate',
                    title: '更新时间',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                    }
                }, {
                    field: 'options',
                    title: '操作',
                    width: 100,
                    formatter: function (value, row, index) {
                        var icons = [{
                            "name": "edit",
                            "title": "编辑"
                        }, {
                            "name": "remove",
                            "title": "删除"
                        }];
                        var buttons = [];
                        for (var i = 0; i < icons.length; i++) {
                            var tmpl = '<a href="#" title ="${title}" '
                                + 'onclick="OpenApiMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: DsCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return OpenApiMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            var pager = $("#ipRateLimit-datagrid").datagrid("getPager");
            if (pager) {
                $(pager).pagination({
                    onSelectPage: function (pageNumber, pageSize) {

                        var url = OpenApiMVC.Util.getSearchUrl();
                        $("#ipRateLimit-datagrid").datagrid({
                            pageNumber: pageNumber,
                            pageSize: pageSize,
                            url: url
                        });

                    }
                });
            }

            $('#ipRateLimit-dlg-detail').dialog({
                closed: true,
                modal: true,
                striped: true,
                collapsible: true,
                resizable: true,
                width: 910,
                height: 600,
                maximizable: true,
                iconCls: 'icon-info',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-back',
                    handler: function () {
                        $("#ipRateLimit-dlg-detail").dialog('close');
                    }
                }]
            });

            // dialogs
            $('#ipRateLimit-dlg').dialog({
                closed: true,
                modal: false,
                width: 650,
                height: 350,
                iconCls: 'icon-add',
                buttons: [
                    {
                        text: '关闭',
                        iconCls: 'icon-no',
                        handler: function () {
                            $("#ipRateLimit-dlg").dialog('close');
                        }
                    }, {
                        text: '保存',
                        iconCls: 'icon-save',
                        handler: OpenApiMVC.Controller.save
                    }]
            });


            $('#api-secret-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: OpenApiMVC.URLs.getlist.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        $("#api-secret-datagrid").datagrid({
                            pageNumber: 1,
                            pageSize: 50,
                            url: OpenApiMVC.URLs.getlist.url,
                        });
                        //EasyUIUtils.reloadDatagrid('#api-secret-datagrid');
                    }
                }, '-', {
                    iconCls: 'icon-edit',
                    handler: function () {
                        OpenApiMVC.Controller.info();
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
                                + 'onclick="OpenApiMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: DsCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }
                ]],
                onDblClickRow: function () {
                    return OpenApiMVC.Controller.info();
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
                    handler: OpenApiMVC.Controller.savelimit
                }]
            });


            $('#ipRateLimit-options-pg').propertygrid({
                scrollbarSize: 0,
                height: 200,
                columns: [[
                    {field: 'name', title: '配置项', width: 200, sortable: true},
                    {field: 'value', title: '配置值', width: 100, resizable: false}
                ]]
            });
        },
        bindEvent: function () {
            $('#ip-search').bind('click', OpenApiMVC.Controller.findsecre);
            $('#btn-search').bind('click', OpenApiMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {
        }
    },
    Controller: {
        doOptions: function (index, name) {
            $('#api-secret-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return OpenApiMVC.Controller.info();
            }
        },
        doOption: function (index, name) {
            $('#ipRateLimit-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return OpenApiMVC.Controller.edit();
            }
            if (name === "remove") {
                return OpenApiMVC.Controller.remove();
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

        savelimit: function () {
            var ratelimit = $("#ratelimit").textbox('getValue');
            if (ratelimit == '') {
                $.messager.alert('提示', '请填写新的限流次数，格式为:次数/秒', 'info');
                return;
            }
            var ipWhiteLists = $("#ipWhiteLists").textbox('getValue');
            var authorities = $("#authorities").textbox('getValue');
            var row = $('#api-secret-datagrid').datagrid('getSelected');
            var url = juicer(OpenApiMVC.URLs.ipedit.url, {id: row.id});
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

        add: function () {
            var options = OpenApiMVC.Util.getOptions();
            options.title = '新增IP限流';
            EasyUIUtils.openAddDlg(options);
        },
        edit: function () {
            var row = $('#ipRateLimit-datagrid').datagrid('getSelected');
            if (row) {
                var options = OpenApiMVC.Util.getOptions();
                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改';
                EasyUIUtils.openEditDlg(options);
                $('#options').val(row.options || "{}");
                BossGlobal.utils.debug(row.options);
                $('#ipRateLimit-options-pg').propertygrid('loadData', EasyUIUtils.toPropertygridRows($.toJSON(row.options)));
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        find: function () {
            var url = OpenApiMVC.Util.getSearchUrl();
            $("#ipRateLimit-datagrid").datagrid({
                pageNumber: 1,
                pageSize: 50,
                url: url
            });
        },

        findsecre: function () {
            var url = OpenApiMVC.Util.getIpSearchUrl();
            $("#api-secret-datagrid").datagrid({
                pageNumber: 1,
                pageSize: 50,
                url: url
            });
        },
        remove: function () {
            var row = $('#ipRateLimit-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: OpenApiMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#ipRateLimit-datagrid',
                    gridUrl: OpenApiMVC.URLs.list.url,
                    callback: function (rows) {
                    }
                };
                EasyUIUtils.remove(options);
            }
        },
        save: function () {

            var action = $('#modal-action').val();

            var options = {
                gridId: null,
                gridUrl: OpenApiMVC.URLs.list.url,
                dlgId: "#ipRateLimit-dlg",
                formId: "#ipRateLimit-form",
                url: null,
                callback: function () {
                }
            };
            options.url = (action === "edit" ? OpenApiMVC.URLs.edit.url : OpenApiMVC.URLs.add.url);
            options.gridId = '#ipRateLimit-datagrid';
            return EasyUIUtils.save(options);
        },
        refresh: function () {
            $.ajax({
                url: OpenApiMVC.URLs.refresh.url,
                type: 'get',
                success: function (result) {
                    if (result.code) {
                        return $.messager.show({
                            title: '错误',
                            msg: result.msg
                        });
                    }
                    return $.messager.alert('提示', '刷新成功', 'info');
                },
                error: function (result) {
                    if (result.responseJSON.code == "403") {
                        $.messager.alert('提示', '没有权限执行该操作', 'error');
                    } else {
                        $.messager.alert('提示', result.responseJSON.msg, 'error');
                    }
                }
            });
        }
    },
    Util: {
        getSearchUrl: function () {
            var fieldName = "";
            var keyword = "";
            var ip = $("#ip").textbox('getValue');
            var memo = $("#memo").textbox('getValue');
            if (ip !== null && ip !== "") {
                fieldName = "ip";
                keyword = ip;
            } else if (memo !== null && memo !== "") {
                fieldName = "memo";
                keyword = memo;
            }
            return OpenApiMVC.URLs.list.url + '?fieldName=' + fieldName + '&keyword=' + keyword;
        },
        getIpSearchUrl: function () {
            var userid = $("#userid").textbox('getValue');
            var apikey = $("#apikey").textbox('getValue');
            return OpenApiMVC.URLs.getlist.url + '?userId=' + userid + '&apiKey=' + apikey;
        },

        isRowSelected: function (func) {
            var row = $('#ipRateLimit-datagrid').datagrid('getSelected');
            if (row) {
                func(row);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        getOptions: function () {
            return {
                dlgId: '#ipRateLimit-dlg',
                formId: '#ipRateLimit-form',
                actId: '#modal-action',
                rowId: '#dsId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                gridId: null,
            };
        }
    }
};
