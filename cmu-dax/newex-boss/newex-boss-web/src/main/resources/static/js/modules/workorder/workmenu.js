$(function () {
    WorkMenu.init();
});

var WorkMenu = {
    init: function () {
        WorkMenuMVC.View.initControl();
        WorkMenuMVC.View.bindEvent();
        WorkMenuMVC.View.bindValidate();
        WorkMenuMVC.View.initData();

    }
};

var WorkMenuCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/workorder/workMenu/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    groupsBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/groups/',
};

var WorkMenuMVC = {
    URLs: {
        add: {
            url: WorkMenuCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: WorkMenuCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: WorkMenuCommon.baseUrl + 'list',
            method: 'GET'
        },
        remove: {
            url: WorkMenuCommon.baseUrl + 'remove',
            method: 'GET'
        },
        getAdminGroupTree: {
            url: WorkMenuCommon.groupsBaseUrl + 'getGroupTree',
            method: 'GET'
        }
        ,
        getWorkTree: {
            url: WorkMenuCommon.baseUrl + 'getWorkMenuTree',
            method: 'GET'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {

            $.get(WorkMenuMVC.URLs.getWorkTree.url, "", function (_data) {
                $("#parentId").combotree({
                    data: _data.data,
                    valueField: "id",
                    required: true,
                    textField: "text",
                    panelHeight: 300,
                    editable: true
                });
            });

            $.get(WorkMenuMVC.URLs.getAdminGroupTree.url, "", function (_data) {
                $("#groupId").combotree({
                    data: _data.data,
                    valueField: "id",
                    required: true,
                    textField: "text",
                    panelHeight: 300,
                    multiple: true,
                    editable: true,
                    onChange: function () {
                        var symbol = $('#groupId').combobox('getValues');
                        $("#groupList").val(symbol);
                    }
                });
            });

            $('#WorkMenu-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: false,
                pageSize: 50,
                url: WorkMenuMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        WorkMenuMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        WorkMenuMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-remove1',
                    handler: function () {
                        WorkMenuMVC.Controller.remove();
                    }
                }, '-', {
                    text: '全部数据',
                    iconCls: 'icon-reload',
                    handler: function () {
                        $("#WorkMenu-datagrid").datagrid({
                            pageNumber: 1,
                            pageSize: 50,
                            url: WorkMenuMVC.URLs.list.url
                        });
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
                    title: '菜单ID',
                    width: 150,
                    sortable: true
                }, {
                    field: 'parentId',
                    title: '菜单父级ID',
                    width: 150,
                    sortable: true
                }, {
                    field: 'locale',
                    title: '本地化语言',
                    width: 150,
                    sortable: true,
                }, {
                    field: 'name',
                    title: '菜单名称',
                    width: 150,
                    sortable: true,
                }, {
                    field: 'status',
                    title: '状态',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return value === 0 ? "启用" : "停用";
                    }
                }, {
                    field: 'groupId',
                    title: ' 组织机构',
                    width: 150,
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
                    title: '修改时间',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                    }
                }, {
                    field: 'options',
                    title: '操作',
                    width: 150,
                    formatter: function (value, row, index) {
                        var icons = [{
                            "name": "edit",
                            "title": "编辑"
                        }];
                        var buttons = [];
                        for (var i = 0; i < icons.length; i++) {
                            var tmpl = '<a href="#" title ="${title}" '
                                + 'onclick="WorkMenuMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: WorkMenuCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return WorkMenuMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                },
                onLoadSuccess: function () {
                    $('.pagination-page-list').hide();
                }
            });

            var pager = $("#WorkMenu-datagrid").datagrid("getPager");
            if (pager) {
                $(pager).pagination({
                    onSelectPage: function (pageNumber, pageSize) {
                        var url = WorkMenuMVC.Util.getSearchUrl();
                        $("#WorkMenu-datagrid").datagrid({
                            pageNumber: pageNumber,
                            pageSize: pageSize,
                            url: url
                        });

                    }
                });
            }

            $('#WorkMenu-dlg').dialog({
                closed: true,
                modal: false,
                width: 630,
                height: 500,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#WorkMenu-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: WorkMenuMVC.Controller.save
                }]
            });


        },
        bindEvent: function () {
            $('#btn-search').bind('click', WorkMenuMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#WorkMenu-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return WorkMenuMVC.Controller.edit();
            }
        },
        add: function () {
            var options = WorkMenuMVC.Util.getOptions();
            options.title = '新增工单菜单';
            EasyUIUtils.openAddDlg(options);
            $('#status').combobox('setValue', "0");
            $('#locale').combobox('setValue', "zh-cn");
        },
        edit: function () {
            var row = $('#WorkMenu-datagrid').datagrid('getSelected');
            if (row) {
                var options = WorkMenuMVC.Util.getOptions();
                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改[' + options.data.name + ']工单菜单';
                EasyUIUtils.openEditDlg(options);
                $('#status').combobox('setValue', row.status);
                $('#name').textbox('setValue', row.name);
                $('#locale').combobox('setValue', row.locale);
                $('#groupId').tree('setValue', row.groupId);
                $('#parentId').tree('setValue', row.parentId);


            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#WorkMenu-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: WorkMenuMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#WorkMenu-datagrid',
                    gridUrl: WorkMenuMVC.URLs.list.url,
                    callback: function (rows) {
                        EasyUIUtils.reloadDatagrid('#WorkMenu-datagrid');
                    }
                };
                EasyUIUtils.remove(options);
            }
        },
        find: function () {
            var url = WorkMenuMVC.Util.getSearchUrl();
            $("#WorkMenu-datagrid").datagrid({
                pageNumber: 1,
                pageSize: 50,
                url: url
            });
        },
        save: function () {
            var action = $('#modal-action').val();
            var options = {
                gridId: null,
                gridUrl: WorkMenuMVC.URLs.list.url,
                dlgId: "#WorkMenu-dlg",
                formId: "#WorkMenu-form",
                url: null,
                callback: function () {
                }
            };
            if (action === "edit") {
                var row = $('#WorkMenu-datagrid').datagrid('getSelected');
                //取得ID传给后台
                options.url = WorkMenuMVC.URLs.edit.url + '?id=' + row.id;
            }
            else {
                options.url = WorkMenuMVC.URLs.add.url;
                options.gridId = '#WorkMenu-datagrid';
            }
            options.callback = function (result) {
                if (!result.code) {
                    EasyUIUtils.reloadDatagrid('#WorkMenu-datagrid');
                }
            }
            //修改后刷新列表
            return EasyUIUtils.save(options);
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#WorkMenu-dlg',
                formId: '#WorkMenu-form',
                actId: '#modal-action',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                gridId: null,
            };
        },
        getSearchUrl: function () {
            var name = $("#workmenu-name").textbox('getValue');
            var status = $("#workmenu-status").combobox('getValue');
            return url = WorkMenuMVC.URLs.list.url + '?name=' + name + '&status=' + status;
        }
    }
};