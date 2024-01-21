$(function () {
    MessaRatioDs.init();
});

var MessaRatioDs = {
    init: function () {
        MessRatioMVC.View.initControl();
        MessRatioMVC.View.bindEvent();
        MessRatioMVC.View.bindValidate();
        MessRatioMVC.View.initData();
    }
};

var DsCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/integration/message-success-ratios/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    keys: {
        dbType: 'dbType',
        dbPoolType: 'dbPoolType'
    }
};

var MessRatioMVC = {
    URLs: {

        list: {
            url: DsCommon.baseUrl + 'list',
            method: 'get'
        }
    },
    Model: {
        dbTypes: {},
        dbPoolTypes: {}
    },
    View: {
        initControl: function () {
            $('#ds-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: MessRatioMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        $("#ds-datagrid").datagrid({
                            pageNumber: 1,
                            pageSize: 50,
                            url: MessRatioMVC.URLs.list.url
                        });
                        //EasyUIUtils.reloadDatagrid('#ds-datagrid');
                    }
                }],
                loadFilter: function (src) {
                    if (!src.code) {
                        return src.data;
                    }
                    return $.messager.alert('失败', src.msg, 'error');
                },
                columns: [[{
                    field: 'channel',
                    title: '渠道代号',
                    width: 200,
                    sortable: true
                }, {
                    field: 'type',
                    title: '信息类型',
                    width: 100,
                    sortable: true
                }, {
                    field: 'region',
                    title: '服务地区',
                    width: 100,
                    sortable: true
                }, {
                    field: 'ratio',
                    title: '成功率',
                    width: 100,
                    sortable: true
                }, {
                    field: 'createdTime',
                    title: '创建时间',
                    width: 50,
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
                                + 'onclick="MessRatioMVC.Controller.doOption(\'${index}\',\'${name}\')">'
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
                    return MessRatioMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            var pager = $("#ds-datagrid").datagrid("getPager");
            if (pager) {
                $(pager).pagination({
                    onSelectPage: function (pageNumber, pageSize) {

                        var url = MessRatioMVC.Util.getSearchUrl();
                        $("#ds-datagrid").datagrid({
                            pageNumber: pageNumber,
                            pageSize: pageSize,
                            url: url
                        });

                    }
                });
            }

            // dialogs
            $('#ds-dlg').dialog({
                closed: true,
                modal: false,
                width: 650,
                height: 450,
                iconCls: 'icon-add',
                buttons: [
                    {
                        text: '关闭',
                        iconCls: 'icon-no',
                        handler: function () {
                            $("#ds-dlg").dialog('close');
                        }
                    }, {
                        text: '保存',
                        iconCls: 'icon-save',
                        handler: MessRatioMVC.Controller.save
                    }]
            });

            $('#dbType').combobox({
                onChange: function (newValue, oldValue) {
                    var item = MessRatioMVC.Model.dbTypes[newValue].value;
                    $('#jdbcUrl').textbox('setValue', item.jdbcUrl);
                    $('#driverClass').val(item.driverClass);
                    $('#queryerClass').val(item.queryerClass);
                }
            });

            $('#dbPoolType').combobox({
                onChange: function (newValue, oldValue) {
                    var item = MessRatioMVC.Model.dbPoolTypes[newValue].value;
                    $('#poolClass').val(item.poolClass);
                    var data = EasyUIUtils.toPropertygridRows(item.options);
                    $('#ds-options-pg').propertygrid('loadData', data);
                }
            });

            $('#ds-options-pg').propertygrid({
                scrollbarSize: 0,
                height: 200,
                columns: [[
                    {field: 'name', title: '配置项', width: 200, sortable: true},
                    {field: 'value', title: '配置值', width: 100, resizable: false}
                ]]
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', MessRatioMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {
            MessRatioMVC.Util.loadConfigItems();
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#ds-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return MessRatioMVC.Controller.edit();
            }
            if (name === "remove") {
                return MessRatioMVC.Controller.remove();
            }
            if (name === "connect") {
                return MessRatioMVC.Controller.testConnectionById(index);
            }
        },
        add: function () {
            var options = MessRatioMVC.Util.getOptions();
            options.title = '新增消息模版';
            EasyUIUtils.openAddDlg(options);
            MessRatioMVC.Util.fillCombox("#dbType", "add", MessRatioMVC.Model.dbTypes, "driverClass", "");
            MessRatioMVC.Util.fillCombox("#dbPoolType", "add", MessRatioMVC.Model.dbPoolTypes, "poolClass", "");
        },
        edit: function () {
            var row = $('#ds-datagrid').datagrid('getSelected');
            if (row) {
                var options = MessRatioMVC.Util.getOptions();
                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改[' + options.data.code + ']模版';
                EasyUIUtils.openEditDlg(options);
                MessRatioMVC.Util.fillCombox("#dbType", "edit", MessRatioMVC.Model.dbTypes, "driverClass", row.driverClass);
                MessRatioMVC.Util.fillCombox("#dbPoolType", "edit", MessRatioMVC.Model.dbPoolTypes, "poolClass", row.poolClass);
                $('#jdbcUrl').textbox('setValue', row.jdbcUrl);
                $('#options').val(row.options || "{}");
                BossGlobal.utils.debug(row.options);
                $('#ds-options-pg').propertygrid('loadData', EasyUIUtils.toPropertygridRows($.toJSON(row.options)));
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        showDetail: function () {

            MessRatioMVC.Util.isRowSelected(function (row) {
                $('#ds-dlg-detail').dialog('open').dialog('center');

                MessRatioMVC.Util.fillDetailLabels(row);
            });
        },
        find: function () {
            var url = MessRatioMVC.Util.getSearchUrl();
            $("#ds-datagrid").datagrid({
                pageNumber: 1,
                pageSize: 50,
                url: url
            });
        },
        remove: function () {
            var row = $('#ds-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: MessRatioMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#ds-datagrid',
                    gridUrl: MessRatioMVC.URLs.list.url,
                    callback: function (rows) {
                    }
                };
                EasyUIUtils.remove(options);
            }
        },
        testConnectionById: function (index) {
            $('#ds-datagrid').datagrid('selectRow', index);
            var row = $('#ds-datagrid').datagrid('getSelected');
            $.post(MessRatioMVC.URLs.testConnectionById.url, {
                id: row.id
            }, function callback(data) {
                if (!data.code) {
                    $.messager.alert('成功', "测试成功", 'success');
                } else {
                    $.messager.alert('失败', "测试失败", 'error');
                }
            }, 'json');
        },
        testConnection: function () {
            var key = $("#dbType").combobox('getValue');
            var item = MessRatioMVC.Model.dbTypes[key].value;
            var data = {
                driverClass: item.driverClass,
                url: $("#jdbcUrl").val(),
                pass: $("#password").val(),
                user: $("#user").val()
            };
            BossGlobal.utils.debug(data);

            $.post(MessRatioMVC.URLs.testConnection.url, data, function callback(data) {
                if (!data.code) {
                    $.messager.alert('成功', "测试成功", 'success');
                } else {
                    $.messager.alert('失败', "测试失败", 'error');
                }
            }, 'json');
        },
        save: function () {

            //BossGlobal.utils.debug($('#options').val());

            var action = $('#modal-action').val();

            var options = {
                gridId: null,
                gridUrl: MessRatioMVC.URLs.list.url,
                dlgId: "#ds-dlg",
                formId: "#ds-form",
                url: null,
                callback: function () {
                }
            };

            options.url = (action === "edit" ? MessRatioMVC.URLs.edit.url : MessRatioMVC.URLs.add.url);
            options.gridId = '#ds-datagrid';
            return EasyUIUtils.save(options);
        }
    },
    Util: {

        getSearchUrl: function () {

            var type = $("#type").combobox('getValue');
            return MessRatioMVC.URLs.list.url + '?type=' + type;

        },

        isRowSelected: function (func) {
            var row = $('#ds-datagrid').datagrid('getSelected');
            if (row) {
                func(row);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        getOptions: function () {
            return {
                dlgId: '#ds-dlg',
                formId: '#ds-form',
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
                var key = MessRatioMVC.Util.findKey(map, fieldName, value);
                $(id).combobox('setValues', key);
            }
        },
        loadConfigItems: function () {
            $.getJSON(MessRatioMVC.URLs.getConfItems.url + "?key=" + DsCommon.keys.dbType, function (result) {
                MessRatioMVC.Util.toMap(MessRatioMVC.Model.dbTypes, result.data);
            });
            $.getJSON(MessRatioMVC.URLs.getConfItems.url + "?key=" + DsCommon.keys.dbPoolType, function (result) {
                MessRatioMVC.Util.toMap(MessRatioMVC.Model.dbPoolTypes, result.data);
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
        },
        fillDetailLabels: function (data) {

        }
    }
};
