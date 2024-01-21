$(function () {
    WorkOrderTempaDs.init();
});

var WorkOrderTempaDs = {
    init: function () {
        WorkOrderTempaMVC.View.initControl();
        WorkOrderTempaMVC.View.bindEvent();
        WorkOrderTempaMVC.View.bindValidate();
        WorkOrderTempaMVC.View.initData();
    }
};

var DsCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/workorder/template/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    basecomboxUrl: BossGlobal.ctxPath + '/v1/boss/workorder/workMenu/',
    keys: {
        dbType: 'dbType',
        dbPoolType: 'dbPoolType'
    }
};

var WorkOrderTempaMVC = {
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
        getMenuTree: {
            url: DsCommon.basecomboxUrl + 'getWorkMenuTree',
            method: 'get'
        }
    },
    Model: {
        dbTypes: {},
        dbPoolTypes: {}
    },
    View: {
        initControl: function () {

            $.get(WorkOrderTempaMVC.URLs.getMenuTree.url, "", function (_data) {
                $("#menuId").combotree({
                    data: _data.data,
                    valueField: "id",
                    required: true,
                    textField: "text",
                    panelHeight: 300,
                    editable: true
                });
            });

            $('#ds-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: WorkOrderTempaMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        WorkOrderTempaMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        WorkOrderTempaMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-remove1',
                    handler: function () {
                        WorkOrderTempaMVC.Controller.remove();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        $("#ds-datagrid").datagrid({
                            pageNumber: 1,
                            pageSize: 50,
                            url: WorkOrderTempaMVC.URLs.list.url
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

                    field: 'id',
                    title: '模版ID',
                    width: 100,
                    sortable: true
                }, {
                    field: 'menuId',
                    title: '菜单ID',
                    width: 100,
                    sortable: true
                }, {
                    field: 'template',
                    title: '问题模版',
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
                                + 'onclick="WorkOrderTempaMVC.Controller.doOption(\'${index}\',\'${name}\')">'
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
                    return WorkOrderTempaMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            var pager = $("#ds-datagrid").datagrid("getPager");
            if (pager) {
                $(pager).pagination({
                    onSelectPage: function (pageNumber, pageSize) {

                        var url = WorkOrderTempaMVC.Util.getSearchUrl();
                        $("#ds-datagrid").datagrid({
                            pageNumber: pageNumber,
                            pageSize: pageSize,
                            url: url
                        });

                    }
                });
            }

            $('#ds-dlg-detail').dialog({
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
                        $("#ds-dlg-detail").dialog('close');
                    }
                }]
            });

            // dialogs
            $('#ds-dlg').dialog({
                closed: true,
                modal: false,
                width: 650,
                height: 200,
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
                        handler: WorkOrderTempaMVC.Controller.save
                    }]
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
            $('#btn-search').bind('click', WorkOrderTempaMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {
            WorkOrderTempaMVC.Util.loadConfigItems();
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#ds-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return WorkOrderTempaMVC.Controller.edit();
            }
            if (name === "remove") {
                return WorkOrderTempaMVC.Controller.remove();
            }
        },
        add: function () {
            var options = WorkOrderTempaMVC.Util.getOptions();
            options.title = '新增工单模版';
            EasyUIUtils.openAddDlg(options);
        },
        edit: function () {
            var row = $('#ds-datagrid').datagrid('getSelected');
            if (row) {
                var options = WorkOrderTempaMVC.Util.getOptions();
                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改工单模版';
                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        find: function () {
            var url = WorkOrderTempaMVC.Util.getSearchUrl();
            $("#ds-datagrid").datagrid({
                pageNumber: 1,
                pageSize: 50,
                url: url
            });

            // var menuId = $("#menuId").combotree('getValue');
            // var template = $("#template").textbox('getValue');
            // var url = WorkOrderTempaMVC.URLs.list.url + '?menuId=' + menuId + '&template=' + template;
            // EasyUIUtils.loadToDatagrid('#ds-datagrid', url)
        },
        remove: function () {
            var row = $('#ds-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: WorkOrderTempaMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#ds-datagrid',
                    gridUrl: WorkOrderTempaMVC.URLs.list.url,
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
                gridUrl: WorkOrderTempaMVC.URLs.list.url,
                dlgId: "#ds-dlg",
                formId: "#ds-form",
                url: null,
                callback: function () {
                }
            };
            options.url = (action === "edit" ? WorkOrderTempaMVC.URLs.edit.url : WorkOrderTempaMVC.URLs.add.url);
            options.gridId = '#ds-datagrid';
            return EasyUIUtils.save(options);
        }
    },
    Util: {
        getSearchUrl: function () {
            var menuId = $("#menuId").combotree('getValue');
            var template = $("#template").textbox('getValue');
            return WorkOrderTempaMVC.URLs.list.url + '?menuId=' + menuId + '&template=' + template;

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
                var key = WorkOrderTempaMVC.Util.findKey(map, fieldName, value);
                $(id).combobox('setValues', key);
            }
        },
        loadConfigItems: function () {
            $.getJSON(WorkOrderTempaMVC.URLs.getConfItems.url + "?key=" + DsCommon.keys.dbType, function (result) {
                WorkOrderTempaMVC.Util.toMap(WorkOrderTempaMVC.Model.dbTypes, result.data);
            });
            $.getJSON(WorkOrderTempaMVC.URLs.getConfItems.url + "?key=" + DsCommon.keys.dbPoolType, function (result) {
                WorkOrderTempaMVC.Util.toMap(WorkOrderTempaMVC.Model.dbPoolTypes, result.data);
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
