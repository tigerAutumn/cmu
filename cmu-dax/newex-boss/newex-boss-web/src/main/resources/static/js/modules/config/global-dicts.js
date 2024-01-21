$(function () {
    GlobalDict.init();
});

var GlobalDict = {
    init: function () {
        GlobalDictMVC.View.initControl();
        GlobalDictMVC.View.bindEvent();
        GlobalDictMVC.View.bindValidate();
    }
};

var GlobalDictCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/config/global-dicts/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/'
};

var GlobalDictMVC = {
    URLs: {
        add: {
            url: GlobalDictCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: GlobalDictCommon.baseUrl + 'edit',
            method: 'PUT'
        },
        list: {
            url: GlobalDictCommon.baseUrl + 'listByPage',
            method: 'GET'
        },
        remove: {
            url: GlobalDictCommon.baseUrl + 'remove',
            method: 'POST'
        },
        listChildren: {
            url: GlobalDictCommon.baseUrl + 'listChildren',
            method: 'GET'
        }
    },
    View: {
        initControl: function () {
            // 左边字典树
            $('#west').panel({
                tools: [{
                    iconCls: 'icon-search',
                    handler: GlobalDictMVC.Controller.openSearchDlg
                }, {
                    iconCls: 'icon-add',
                    handler: GlobalDictMVC.Controller.addRoot
                }, {
                    iconCls: 'icon-reload',
                    handler: function () {
                        GlobalDictMVC.Controller.reloadTree();
                        EasyUIUtils.loadDataWithUrl('#dict-datagrid', GlobalDictMVC.URLs.list.url + '?id=0');
                    }
                }]
            });

            $('#dict-tree').tree({
                checkbox: false,
                method: 'get',
                animate: true,
                dnd: true,
                url: GlobalDictMVC.URLs.listChildren.url,
                onClick: function (node) {
                    $('#dict-tree').tree('expand', node.target);
                    $('#dict-tree').tree('options').url = GlobalDictMVC.URLs.listChildren.url;
                    EasyUIUtils.loadDataWithUrl('#dict-datagrid', GlobalDictMVC.URLs.list.url + '?id=' + node.id);
                },
                onContextMenu: function (e, node) {
                    e.preventDefault();
                    $('#dict-tree').tree('select', node.target);
                    $('#tree_ctx_menu').menu('show', {
                        left: e.pageX,
                        top: e.pageY
                    });
                },
                loadFilter: function (src, parent) {
                    if (!src.code) {
                        return src.data;
                    }
                    return $.messager.alert('失败', src.msg, 'error');
                }
            });

            $('#tree_ctx_menu').menu({
                onClick: function (item) {
                    if (item.name === "add") {
                        return GlobalDictMVC.Controller.add();
                    }
                    if (item.name === "edit") {
                        return GlobalDictMVC.Controller.editNode();
                    }
                    if (item.name === "remove") {
                        return GlobalDictMVC.Controller.remove();
                    }
                    if (item.name === "find") {
                        return GlobalDictMVC.Controller.openSearchDlg();
                    }
                }
            });

            $('#dict-datagrid').datagrid({
                url: GlobalDictMVC.URLs.list.url,
                method: 'get',
                idField: 'id',
                pageSize: 50,
                fit: true,
                pagination: true,
                rownumbers: true,
                fitColumns: true,
                singleSelect: true,
                toolbar: [{
                    text: '增加',
                    iconCls: 'icon-add',
                    handler: GlobalDictMVC.Controller.add
                }, '-', {
                    text: '修改',
                    iconCls: 'icon-edit1',
                    handler: GlobalDictMVC.Controller.edit
                }, '-', {
                    text: '复制',
                    iconCls: 'icon-copy',
                    handler: GlobalDictMVC.Controller.copy
                }, '-', {
                    text: '删除',
                    iconCls: 'icon-remove',
                    handler: GlobalDictMVC.Controller.remove
                }],
                loadFilter: function (src) {
                    if (!src.code) {
                        return src.data;
                    }
                    return $.messager.alert('失败', src.msg, 'error');
                },
                columns: [[{
                    field: 'id',
                    title: '标识',
                    width: 50,
                    sortable: true
                }, {
                    field: 'name',
                    title: '名称',
                    width: 100,
                    sortable: true
                }, {
                    field: 'code',
                    title: '键',
                    width: 100,
                    sortable: true
                }, {
                    field: 'value',
                    title: '值',
                    width: 100
                }, {
                    field: 'sequence',
                    title: '顺序',
                    width: 50,
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
                }]],
                onDblClickRow: function (index, row) {
                    GlobalDictMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            $('#search-node-result').datagrid({
                method: 'get',
                fit: true,
                pagination: true,
                rownumbers: true,
                fitColumns: true,
                singleSelect: true,
                pageSize: 10,
                loadFilter: function (src) {
                    if (!src.code) {
                        return src.data;
                    }
                    return {
                        total: 0,
                        rows: []
                    };
                },
                columns: [[{
                    field: 'id',
                    title: '标识',
                    width: 50
                }, {
                    field: 'parentId',
                    title: '父标识',
                    hidden: true
                }, {
                    field: 'name',
                    title: '名称',
                    width: 150
                }, {
                    field: 'key',
                    title: '对应键',
                    width: 100
                }, {
                    field: 'value',
                    title: '对应值',
                    width: 100
                }]],
                onDblClickRow: function (index, row) {
                }
            });

            $('#dict-dlg').dialog({
                closed: true,
                modal: true,
                width: 600,
                height: 450,
                iconCls: 'icon-save',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#dict-dlg").dialog('close');
                    }
                }, {
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: GlobalDictMVC.Controller.save
                }]
            });

            $('#search-node-dlg').dialog({
                closed: true,
                modal: true,
                width: 750,
                height: 550,
                maximizable: true,
                iconCls: 'icon-search',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#search-node-dlg").dialog('close');
                    }
                }]
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', GlobalDictMVC.Controller.find);
        },
        bindValidate: function () {
        }
    },
    Controller: {
        addRoot: function () {
            var name = "根配置项";
            var id = "0";
            GlobalDictMVC.Util.initAdd(id, name);
        },
        add: function () {
            var node = $('#dict-tree').tree('getSelected');
            if (node) {
                var name = node.attributes.name;
                var id = node.id;
                GlobalDictMVC.Util.initAdd(id, name);
            } else {
                $.messager.alert('警告', '请选中一个父配置项!', 'info');
            }
        },
        editNode: function () {
            var node = $('#dict-tree').tree('getSelected');
            if (node) {
                GlobalDictMVC.Util.edit(node.attributes);
            }
        },
        edit: function () {
            var row = $('#dict-datagrid').datagrid('getSelected');
            GlobalDictMVC.Util.edit(row);
        },
        copy: function () {
            $("#dictParentNameDiv").hide();
            var row = $('#dict-datagrid').datagrid('getSelected');
            if (row) {
                var options = GlobalDictMVC.Util.getOptions();
                options.iconCls = 'icon-copy';
                options.data = row;
                options.title = '复制[' + options.data.name + ']配置项';
                EasyUIUtils.openEditDlg(options);
                $('#modal-action').val("add");
            }
        },
        remove: function () {
            var row = $('#dict-datagrid').datagrid('getSelected');
            var node = $('#dict-tree').tree('getSelected');
            node = node ? node.attributes : null;
            row = row || node;

            var options = {
                rows: [row],
                url: GlobalDictMVC.URLs.remove.url,
                data: {
                    id: row.id
                },
                callback: function (rows) {
                    var row = rows[0];
                    GlobalDictMVC.Controller.reloadTree();
                    EasyUIUtils.loadDataWithUrl('#dict-datagrid', GlobalDictMVC.URLs.list.url + '?id=' + row.parentId);
                }
            };
            EasyUIUtils.remove(options);
        },
        save: function () {
            var action = $('#modal-action').val();
            var gridUrl = GlobalDictMVC.URLs.list.url + '?id=' + $("#dictPid").val();
            var actUrl = action === "edit" ? GlobalDictMVC.URLs.edit.url : GlobalDictMVC.URLs.add.url;

            var options = {
                dlgId: "#dict-dlg",
                formId: "#dict-form",
                url: actUrl,
                callback: function () {
                    GlobalDictMVC.Controller.reloadTree();
                    EasyUIUtils.loadDataWithUrl('#dict-datagrid', gridUrl);
                }
            };
            EasyUIUtils.save(options);
        },
        refreshNode: function (pid) {
            if (pid === "0") {
                this.reloadTree();
            } else {
                var node = $('#dict-tree').tree('find', pid);
                if (node) {
                    $('#dict-tree').tree('select', node.target);
                    $('#dict-tree').tree('reload', node.target);
                }
            }
        },
        reloadTree: function () {
            $('#dict-tree').tree('reload');
        },
        openSearchDlg: function () {
            $('#search-node-dlg').dialog('open').dialog('center');
            EasyUIUtils.clearDatagrid('#search-node-result');
        },
        find: function () {
            var fieldName = $('#field-name').combobox('getValue');
            var keyword = $('#keyword').val();
            var url = GlobalDictMVC.URLs.list.url + '?fieldName=' + fieldName + '&keyword=' + keyword;
            return EasyUIUtils.loadDataWithUrl('#search-node-result', url);
        }
    },
    Util: {
        initAdd: function (id, name) {
            var options = GlobalDictMVC.Util.getOptions();
            options.title = '新增[' + name + ']的子配置项';
            EasyUIUtils.openAddDlg(options);

            $("#dictPid").val(id);
            $("#dictParentNameDiv").show();
            $("#dictParentName").html(name);
            $("#sequence").textbox('setValue', 10);
        },
        edit: function (data) {
            $("#dictParentNameDiv").hide();
            var options = GlobalDictMVC.Util.getOptions();
            options.iconCls = 'icon-edit1';
            options.data = data;
            options.title = '修改[' + options.data.name + ']配置项';
            EasyUIUtils.openEditDlg(options);
        },
        getOptions: function () {
            return {
                dlgId: '#dict-dlg',
                formId: '#dict-form',
                actId: '#modal-action',
                rowId: '#dictId',
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