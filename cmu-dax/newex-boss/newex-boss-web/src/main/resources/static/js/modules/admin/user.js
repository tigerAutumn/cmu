$(function () {
    MembershipUser.init();
});

var MembershipUser = {
    init: function () {
        UserMVC.View.initControl();
        UserMVC.View.bindEvent();
        UserMVC.View.bindValidate();
        UserMVC.View.initData();
    }
};

var UserCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/admin/users/',
    baseRoleUrl: BossGlobal.ctxPath + '/v1/boss/admin/roles/',
    groupsBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/groups/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/'
};

var UserMVC = {
    URLs: {
        add: {
            url: UserCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: UserCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: UserCommon.baseUrl + 'listByPage',
            method: 'GET'
        },
        remove: {
            url: UserCommon.baseUrl + 'remove',
            method: 'POST'
        },
        getRoleList: {
            url: UserCommon.baseRoleUrl + 'getRoleList',
            method: 'GET'
        },
        editPassword: {
            url: UserCommon.baseUrl + 'editPassword',
            method: 'POST'
        },
        generateGoogleKey: {
            url: UserCommon.baseUrl + 'generateGoogleKey',
            method: 'POST'
        },
        getAdminGroupTree: {
            url: UserCommon.groupsBaseUrl + 'getGroupTree',
            method: 'GET'
        },
        getBrokerList: {
            url: UserCommon.brokerBaseUrl + 'list'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {

            $.get(UserMVC.URLs.getAdminGroupTree.url, "", function (_data) {
                $("#groupId").combotree({
                    data: _data.data,
                    valueField: "id",
                    required: true,
                    textField: "text",
                    panelHeight: 300,
                    editable: true
                });
            });

            $("#brokerId").combobox({
                url: UserMVC.URLs.getBrokerList.url,
                method: 'get',
                valueField: "id",
                textField: "brokerName",
                editable: true,
                value: '',
                prompt: '选择券商',
                panelHeight: 300
            });

            $('#user-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: UserMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        UserMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        UserMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-pwd',
                    text: '设置密码',
                    handler: function () {
                        UserMVC.Controller.resetPwd();
                    }
                }, '-', {
                    iconCls: 'icon-remove1',
                    handler: function () {
                        UserMVC.Controller.remove();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        $("#user-datagrid").datagrid({
                            pageNumber: 1,
                            pageSize: 50,
                            url: UserMVC.URLs.list.url
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
                    title: '用户ID',
                    width: 50,
                    sortable: true
                }, {
                    field: 'account',
                    title: '账号',
                    width: 100,
                    sortable: true
                }, {
                    field: 'brokerId',
                    title: '券商Id',
                    width: 100,
                    sortable: true
                }, {
                    field: 'name',
                    title: '姓名',
                    width: 80,
                    sortable: true,
                }, {
                    field: 'telephone',
                    title: '电话',
                    width: 50,
                    sortable: true
                }, {
                    field: 'email',
                    title: '邮箱',
                    width: 80,
                    sortable: true
                }, {
                    field: 'totpKey',
                    title: 'GoogleKey',
                    width: 80,
                    sortable: true
                }, {
                    field: 'groupId',
                    title: '组织机构ID',
                    width: 80,
                    sortable: true,
                    hidden: true
                }, {
                    field: 'groupName',
                    title: '组织机构',
                    width: 80,
                    sortable: true
                }, {
                    field: 'orderNum',
                    title: '工单数量',
                    width: 80,
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
                    title: '用户类型',
                    width: 50,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return value === 2 ? "组长" : "客服人员";
                    }
                }, {
                    field: 'createdDate',
                    title: '创建时间',
                    width: 80,
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
                            "name": "pwd",
                            "title": "修改密码"
                        }, {
                            "name": "remove",
                            "title": "删除"
                        }];
                        var buttons = [];
                        for (var i = 0; i < icons.length; i++) {
                            var tmpl = '<a href="#" title ="${title}" '
                                + 'onclick="UserMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: UserCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return UserMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });


            var pager = $("#user-datagrid").datagrid("getPager");
            if (pager) {
                $(pager).pagination({
                    onSelectPage: function (pageNumber, pageSize) {
                        var url = UserMVC.Controller.getSearchUrl();
                        $("#user-datagrid").datagrid({
                            pageNumber: pageNumber,
                            pageSize: pageSize,
                            url: url
                        });

                    }
                });
            }

            // dialogs
            $('#user-dlg').dialog({
                closed: true,
                modal: false,
                width: 560,
                height: 470,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#user-dlg").dialog('close');
                    }
                }, {
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: UserMVC.Controller.save
                }]
            });

            $('#reset-pwd-dlg').dialog({
                closed: true,
                modal: false,
                width: 560,
                height: 250,
                iconCls: 'icon-pwd',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#reset-pwd-dlg").dialog('close');
                    }
                }, {
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: UserMVC.Controller.save
                }]
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', UserMVC.Controller.find);
            $('#btn-generateKey').bind('click', UserMVC.Controller.generateGoogleKey);
        },
        bindValidate: function () {
        },
        initData: function () {
            UserMVC.Util.loadRoleList();
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#user-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return UserMVC.Controller.edit();
            }
            if (name === "remove") {
                return UserMVC.Controller.remove();
            }
            if (name === "pwd") {
                return UserMVC.Controller.resetPwd();
            }
        },
        add: function () {
            var options = UserMVC.Util.getOptions();
            options.title = '新增用户';
            EasyUIUtils.openAddDlg(options);
            UserMVC.Util.fillRoleCombox("add", []);
            $('#status').combobox('setValue', "1");
            $('#type').combobox('setValue', "1");
            $('#account').textbox('readonly', false);
        },
        edit: function () {
            var row = $('#user-datagrid').datagrid('getSelected');
            if (row) {
                var options = UserMVC.Util.getOptions();
                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改[' + options.data.name + ']用户';
                EasyUIUtils.openEditDlg(options);
                $('#account').textbox('readonly', true);
                var roleIds = row.roles || "";
                UserMVC.Util.fillRoleCombox("edit", roleIds.split(','));
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        resetPwd: function () {
            var row = $('#user-datagrid').datagrid('getSelected');
            if (row) {
                $('#reset-pwd-dlg').dialog('open').dialog('center');
                $("#modal-action").val("resetPwd");
                $("#reset-pwd-form").form('clear');
                $("#reset-userId").val(row.id);
                $("#reset-account").text(row.account);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        getSearchUrl: function () {
            var fieldName = $("#field-name").combobox('getValue');
            var keyword = $("#keyword").val();
            return url = UserMVC.URLs.list.url + '?fieldName=' + fieldName + '&keyword=' + keyword;
        },
        find: function () {
            var url = UserMVC.Controller.getSearchUrl();
            $("#user-datagrid").datagrid({
                pageNumber: 1,
                pageSize: 50,
                url: url
            });
        },
        remove: function () {
            var row = $('#user-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: UserMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#user-datagrid',
                    gridUrl: UserMVC.URLs.list.url,
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
                gridUrl: UserMVC.URLs.list.url,
                dlgId: "#user-dlg",
                formId: "#user-form",
                url: null,
                callback: function () {
                }
            };

            if (action === "resetPwd") {
                options.dlgId = '#reset-pwd-dlg';
                options.formId = '#reset-pwd-form';
                options.url = UserMVC.URLs.editPassword.url;
            } else {
                $('#roles').val($('#combox-roles').combobox('getValues'));
                options.url = (action === "edit" ? UserMVC.URLs.edit.url : UserMVC.URLs.add.url);
                options.gridId = '#user-datagrid';
            }
            return EasyUIUtils.save(options);
        },
        generateGoogleKey: function () {
            $.getJSON(UserMVC.URLs.generateGoogleKey.url, function (result) {
                if (result && result.data) {
                    $('#totpKey').textbox('setValue', result.data.key);
                    $('#google').attr("src", result.data.qRCode);
                }
            }).error(function (result) {
                if (result.responseJSON.code === 403) {
                    $.messager.show({
                        title: '错误',
                        msg: data.responseJSON.msg
                    });

                } else {
                    $.messager.alert('提示', 'error', 'info');
                }
            });
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#user-dlg',
                formId: '#user-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                gridId: null,
            };
        },
        fillRoleCombox: function (act, values) {
            var id = '#combox-roles';
            $(id).combobox('clear');
            var data = [];
            var items = UserMVC.Model.roles;
            for (var i = 0; i < items.length; i++) {
                var item = items[i];
                data.push({
                    "value": item.id,
                    "name": item.name,
                    "selected": i === 0
                });
            }
            $(id).combobox('loadData', data);
            if (act === "edit") {
                $(id).combobox('setValues', values);
            }
        },
        loadRoleList: function () {
            $.getJSON(UserMVC.URLs.getRoleList.url, function (src) {
                UserMVC.Model.roles = src.data;
            });

        }
    }
};