$(function () {
    AdminGroup.init();
});

var AdminGroup = {
    init: function () {
        AdminGroupMVC.View.initControl();
        AdminGroupMVC.View.bindEvent();
        AdminGroupMVC.View.bindValidate();
        AdminGroupMVC.Controller.loadAdminGroupData();
    }
};

var AdminGroupCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/admin/groups/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/'
};

var AdminGroupMVC = {
    URLs: {
        add: {
            url: AdminGroupCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: AdminGroupCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: AdminGroupCommon.baseUrl + 'listByPage',
            method: 'GET'
        },
        remove: {
            url: AdminGroupCommon.baseUrl + 'remove',
            method: 'POST'
        },
        getAdminGroupTree: {
            url: AdminGroupCommon.baseUrl + 'getGroupTree',
            method: 'GET'
        },
        getBrokerList: {
            url: AdminGroupCommon.brokerBaseUrl + 'list'
        }
    },
    View: {
        initControl: function () {
            // 左边字典树
            $('#west').panel({
                tools: [{
                    iconCls: 'icon-add',
                    handler: AdminGroupMVC.Controller.addRoot
                }, {
                    iconCls: 'icon-reload',
                    handler: function () {
                        AdminGroupMVC.Controller.reloadTree();
                        EasyUIUtils.loadDataWithUrl('#group-datagrid', AdminGroupMVC.URLs.list.url + '?id=0');
                    }
                }]
            });

            $("#brokerId").combobox({
                url: AdminGroupMVC.URLs.getBrokerList.url,
                method: 'get',
                valueField: "id",
                textField: "brokerName",
                editable: true,
                value: '',
                prompt: '选择券商',
                panelHeight: 300
            });

            $('#group-tree').tree({
                checkbox: false,
                method: 'get',
                animate: true,
                dnd: true,
                onClick: function (node) {
                    $('#group-tree').tree('expand', node.target);
                    EasyUIUtils.loadDataWithUrl('#group-datagrid', AdminGroupMVC.URLs.list.url + '?id=' + node.id);
                },
                onDrop: function (target, source, point) {
                    var targetNode = $('#group-tree').tree('getNode', target);
                    if (targetNode) {
                        $.post(AdminGroupMVC.URLs.move.url, {
                            sourceId: source.id,
                            targetId: targetNode.id,
                            sourcePid: source.attributes.parentId,
                            sourcePath: source.attributes.path
                        }, function (data) {
                            if (data.code) {
                                $.messager.alert('失败', data.msg, 'error');
                            }
                        }, 'json');
                    }
                },
                onContextMenu: function (e, node) {
                    e.preventDefault();
                    $('#group-tree').tree('select', node.target);
                    $('#tree_ctx_menu').menu('show', {
                        left: e.pageX,
                        top: e.pageY
                    });
                }
            });

            $('#tree_ctx_menu').menu({
                onClick: function (item) {
                    if (item.name === "add") {
                        return AdminGroupMVC.Controller.add();
                    }
                    if (item.name === "edit") {
                        return AdminGroupMVC.Controller.editNode();
                    }
                    if (item.name === "remove") {
                        return AdminGroupMVC.Controller.remove();
                    }
                }
            });

            $('#group-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: AdminGroupMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        AdminGroupMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        AdminGroupMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-remove1',
                    handler: function () {
                        AdminGroupMVC.Controller.remove();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        var node = $('#group-tree').tree('getSelected');
                        if (node) {
                            EasyUIUtils.loadDataWithUrl('#group-datagrid', AdminGroupMVC.URLs.list.url + '?id=' + node.id);
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
                    width: 50,
                    sortable: true
                }, {
                    field: 'adminUserId',
                    title: '创建用户ID',
                    width: 50,
                    sortable: true
                }, {
                    field: 'parentId',
                    title: '父ID',
                    width: 50,
                    sortable: true
                }, {
                    field: 'name',
                    title: '名称',
                    width: 50,
                    sortable: true,
                }, {
                    field: 'path',
                    title: '路径',
                    width: 100,
                    sortable: true
                }, {
                    field: 'status',
                    title: '状态',
                    width: 50,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return value === 1 ? "启用" : "禁用";
                    }
                }, {
                    field: 'type',
                    title: '类型',
                    width: 50,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value === 1) {
                            return "总部";
                        }
                        if (value === 2) {
                            return "分部";
                        }
                        if (value === 3) {
                            return "海外";
                        }
                        if (value === 4) {
                            return "工单";
                        }
                        return "其他";
                    }
                }, {
                    field: 'sequence',
                    title: '顺序',
                    width: 50,
                    sortable: true
                }, {
                    field: 'createdDate',
                    title: '创建时间',
                    width: 80,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                    }
                }, {
                    field: 'updatedDate',
                    title: '更新时间',
                    width: 80,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return AdminGroupMVC.Controller.edit(rowIndex, rowData);
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // dialogs
            $('#group-dlg').dialog({
                closed: true,
                modal: false,
                width: 560,
                height: 320,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#group-dlg").dialog('close');
                    }
                }, {
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: AdminGroupMVC.Controller.save
                }]
            });
        },
        bindEvent: function () {
        },
        bindValidate: function () {
        }
    },
    Controller: {
        addRoot: function () {
            var name = "根组织机构";
            var id = "0";
            AdminGroupMVC.Util.initAdd(id, name);
        },
        add: function () {
            var node = $('#group-tree').tree('getSelected');
            if (node) {
                var name = node.attributes.name;
                var id = node.id;
                AdminGroupMVC.Util.initAdd(id, name);
            } else {
                $.messager.alert('警告', '请选中一个父组织机构!', 'info');
            }
        },
        editNode: function () {
            var node = $('#group-tree').tree('getSelected');
            if (node) {
                AdminGroupMVC.Util.edit(node.attributes);
            }
        },
        edit: function () {
            var row = $('#group-datagrid').datagrid('getSelected');
            AdminGroupMVC.Util.edit(row);
        },
        remove: function () {
            var row = $('#group-datagrid').datagrid('getSelected');
            var node = $('#group-tree').tree('getSelected');
            node = node ? node.attributes : null;
            row = row || node;

            var data = {
                id: row.id,
                pid: row.parentId
            };
            var options = {
                rows: [row],
                url: AdminGroupMVC.URLs.remove.url,
                data: data,
                callback: function (rows) {
                    var row = rows[0];
                    AdminGroupMVC.Controller.refreshNode(row.parentId);
                    EasyUIUtils.loadDataWithUrl('#group-datagrid', AdminGroupMVC.URLs.list.url + '?id=' + row.parentId);
                }
            };
            EasyUIUtils.remove(options);
        },
        save: function () {
            var action = $('#modal-action').val();
            var parentId = $("#parentId").val();
            var gridUrl = AdminGroupMVC.URLs.list.url + '?id=' + parentId;
            var actUrl = action === "edit" ? AdminGroupMVC.URLs.edit.url : AdminGroupMVC.URLs.add.url;
            var options = {
                dlgId: "#group-dlg",
                formId: "#group-form",
                url: actUrl,
                callback: function () {
                    AdminGroupMVC.Controller.refreshNode(parentId);
                    EasyUIUtils.loadDataWithUrl('#group-datagrid', gridUrl);
                }
            };
            EasyUIUtils.save(options);
        },
        refreshNode: function (pid) {
            if (pid === "0") {
                this.reloadTree();
            } else {
                var node = $('#group-tree').tree('find', pid);
                if (node) {
                    $('#group-tree').tree('select', node.target);
                    $('#group-tree').tree('reload', node.target);
                }
            }
        },
        reloadTree: function () {
            $('#group-tree').tree('reload');
            AdminGroupMVC.Controller.loadAdminGroupData();
        },
        loadAdminGroupData: function () {
            $.getJSON(AdminGroupMVC.URLs.getAdminGroupTree.url, function (src) {
                if (!src.code) {
                    $('#group-tree').tree('loadData', src.data);
                }
            });
        }
    },
    Util: {
        initAdd: function (id, name) {
            var options = AdminGroupMVC.Util.getOptions();
            options.title = '新增[' + name + ']的子组织机构';
            EasyUIUtils.openAddDlg(options);

            $('#tr-parent-group-name').show();
            $("#parentId").val(id);
            $("#parent-group-name").text(name);
            $("#sequence").textbox('setValue', 10);
            $('#status').combobox('setValue', '1');
            $('#type').combobox('setValue', '1');
        },
        edit: function (data) {
            $('#tr-parent-group-name').hide();
            var options = AdminGroupMVC.Util.getOptions();
            options.iconCls = 'icon-edit1';
            options.data = data;
            options.title = '修改[' + options.data.name + ']组织机构';
            EasyUIUtils.openEditDlg(options);
        },
        getOptions: function () {
            return {
                dlgId: '#group-dlg',
                formId: '#group-form',
                actId: '#modal-action',
                rowId: '#groupId',
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
