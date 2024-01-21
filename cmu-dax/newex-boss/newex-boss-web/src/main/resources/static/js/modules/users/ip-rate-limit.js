$(function () {
    UserIpRateLimit.init();
});

var UserIpRateLimit = {
    init: function () {
        UserIpRateLimitMVC.View.initControl();
        UserIpRateLimitMVC.View.bindEvent();
        UserIpRateLimitMVC.View.bindValidate();
        UserIpRateLimitMVC.View.initData();
    }
};

var DsCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/users/ip-rate-limits/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    keys: {
        dbType: 'dbType',
        dbPoolType: 'dbPoolType'
    }
};

var UserIpRateLimitMVC = {
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
        refresh: {
            url: DsCommon.baseUrl + 'refresh',
            method: 'GET'
        }
    },
    Model: {
        dbTypes: {},
        dbPoolTypes: {}
    },
    View: {
        initControl: function () {
            $('#ipRateLimit-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: UserIpRateLimitMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        UserIpRateLimitMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        UserIpRateLimitMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-remove1',
                    handler: function () {
                        UserIpRateLimitMVC.Controller.remove();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        $("#ipRateLimit-datagrid").datagrid({
                            pageNumber: 1,
                            pageSize: 50,
                            url: UserIpRateLimitMVC.URLs.list.url
                        });
                        //EasyUIUtils.reloadDatagrid('#ipRateLimit-datagrid');
                    }
                }, '-', {
                    text: '刷新缓存',
                    iconCls: 'icon-refresh',
                    handler: function () {
                        UserIpRateLimitMVC.Controller.refresh();
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
                                + 'onclick="UserIpRateLimitMVC.Controller.doOption(\'${index}\',\'${name}\')">'
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
                    return UserIpRateLimitMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            var pager = $("#ipRateLimit-datagrid").datagrid("getPager");
            if (pager) {
                $(pager).pagination({
                    onSelectPage: function (pageNumber, pageSize) {

                        var url = UserIpRateLimitMVC.Util.getSearchUrl();
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
                height: window.screen.height - 350,
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
                        handler: UserIpRateLimitMVC.Controller.save
                    }]
            });

            $('#dbType').combobox({
                onChange: function (newValue, oldValue) {
                    var item = UserIpRateLimitMVC.Model.dbTypes[newValue].value;
                    $('#jdbcUrl').textbox('setValue', item.jdbcUrl);
                    $('#driverClass').val(item.driverClass);
                    $('#queryerClass').val(item.queryerClass);
                }
            });

            $('#dbPoolType').combobox({
                onChange: function (newValue, oldValue) {
                    var item = UserIpRateLimitMVC.Model.dbPoolTypes[newValue].value;
                    $('#poolClass').val(item.poolClass);
                    var data = EasyUIUtils.toPropertygridRows(item.options);
                    $('#ipRateLimit-options-pg').propertygrid('loadData', data);
                }
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
            $('#btn-search').bind('click', UserIpRateLimitMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {
            UserIpRateLimitMVC.Util.loadConfigItems();
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#ipRateLimit-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return UserIpRateLimitMVC.Controller.edit();
            }
            if (name === "remove") {
                return UserIpRateLimitMVC.Controller.remove();
            }
        },
        add: function () {
            var options = UserIpRateLimitMVC.Util.getOptions();
            options.title = '新增IP限流';
            EasyUIUtils.openAddDlg(options);
        },
        edit: function () {
            var row = $('#ipRateLimit-datagrid').datagrid('getSelected');
            if (row) {
                var options = UserIpRateLimitMVC.Util.getOptions();
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
            var url = UserIpRateLimitMVC.Util.getSearchUrl();
            $("#ipRateLimit-datagrid").datagrid({
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
                    url: UserIpRateLimitMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#ipRateLimit-datagrid',
                    gridUrl: UserIpRateLimitMVC.URLs.list.url,
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
                gridUrl: UserIpRateLimitMVC.URLs.list.url,
                dlgId: "#ipRateLimit-dlg",
                formId: "#ipRateLimit-form",
                url: null,
                callback: function () {
                }
            };
            options.url = (action === "edit" ? UserIpRateLimitMVC.URLs.edit.url : UserIpRateLimitMVC.URLs.add.url);
            options.gridId = '#ipRateLimit-datagrid';
            return EasyUIUtils.save(options);
        },
        refresh: function () {
            $.ajax({
                url: UserIpRateLimitMVC.URLs.refresh.url,
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

            return UserIpRateLimitMVC.URLs.list.url + '?fieldName=' + fieldName + '&keyword=' + keyword;

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
        },
        fillCombox: function (id, act, map, fieldName, value) {
            $(id).combobox('clear');
            var data = [];
            var i = 0;
            for (var key in map) {
                var item = map[key];
                data.push({
                    "value": item.key,
                    "name": item.name,
                    "selected": i === 0
                });
                i++;
            }
            $(id).combobox('loadData', data);
            if (act === "edit") {
                var key = UserIpRateLimitMVC.Util.findKey(map, fieldName, value);
                $(id).combobox('setValues', key);
            }
        },
        loadConfigItems: function () {
            $.getJSON(UserIpRateLimitMVC.URLs.getConfItems.url + "?key=" + DsCommon.keys.dbType, function (result) {
                UserIpRateLimitMVC.Util.toMap(UserIpRateLimitMVC.Model.dbTypes, result.data);
            });
            $.getJSON(UserIpRateLimitMVC.URLs.getConfItems.url + "?key=" + DsCommon.keys.dbPoolType, function (result) {
                UserIpRateLimitMVC.Util.toMap(UserIpRateLimitMVC.Model.dbPoolTypes, result.data);
            });
        },
        toMap: function (srcMap, data) {
            if (!data || data.length === 0) return {};
            for (var i = 0; i < data.length; i++) {
                var item = data[i];
                item.value = $.toJSON(item.value);
                srcMap[item.key] = item;
            }
            return srcMap;
        },
        findKey: function (map, fieldName, value) {
            for (var key in map) {
                if (value === map[key].value[fieldName]) {
                    return key;
                }
            }
            return "";
        }
    }
};
