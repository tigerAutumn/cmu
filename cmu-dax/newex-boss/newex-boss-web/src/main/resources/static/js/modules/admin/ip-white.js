$(function () {
    IPWhiteList.init();
});

var IPWhiteList = {
    init: function () {
        IPWhiteListMVC.View.initControl();
        IPWhiteListMVC.View.bindEvent();
        IPWhiteListMVC.View.bindValidate();
        IPWhiteListMVC.View.initData();
    }
};

var IPWhiteListCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/admin/ip-white/',
    baseUserUrl: BossGlobal.ctxPath + '/v1/boss/admin/users/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/'
};

var IPWhiteListMVC = {
    URLs: {
        add: {
            url: IPWhiteListCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: IPWhiteListCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: IPWhiteListCommon.baseUrl + 'listByPage',
            method: 'GET'
        },
        remove: {
            url: IPWhiteListCommon.baseUrl + 'remove',
            method: 'POST'
        },
        getAllUsers: {
            url: IPWhiteListCommon.baseUserUrl + 'getAll',
            method: 'GET'
        }
    },
    Model: {
        users: {}
    },
    View: {
        initControl: function () {
            $('#ipwhite-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: IPWhiteListMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        IPWhiteListMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        IPWhiteListMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-remove1',
                    handler: function () {
                        IPWhiteListMVC.Controller.remove();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        $("#ipwhite-datagrid").datagrid({
                            pageNumber: 1,
                            pageSize: 50,
                            url: IPWhiteListMVC.URLs.list.url
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
                    title: 'ID',
                    width: 50,
                    sortable: true
                }, {
                    field: 'adminUserId',
                    title: '管理员ID',
                    width: 100,
                    sortable: true
                }, {
                    field: 'ipAddress',
                    title: 'IP',
                    width: 100,
                    sortable: true,
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
                                + 'onclick="IPWhiteListMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: IPWhiteListCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return IPWhiteListMVC.Controller.edit();
                }
            });

            var pager = $("#ipwhite-datagrid").datagrid("getPager");
            if (pager) {
                $(pager).pagination({
                    onSelectPage: function (pageNumber, pageSize) {
                        var url = IPWhiteListMVC.Controller.getSearchUrl();
                        $("#ipwhite-datagrid").datagrid({
                            pageNumber: pageNumber,
                            pageSize: pageSize,
                            url: url
                        });

                    }
                });
            }



            // dialogs
            $('#ipwhite-dlg').dialog({
                closed: true,
                modal: false,
                width: 560,
                height: 200,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#ipwhite-dlg").dialog('close');
                    }
                }, {
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: IPWhiteListMVC.Controller.save
                }]
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', IPWhiteListMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {
            IPWhiteListMVC.Util.loadUsersList();
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#ipwhite-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return IPWhiteListMVC.Controller.edit();
            }
            if (name === "remove") {
                return IPWhiteListMVC.Controller.remove();
            }
        },
        add: function () {
            var options = IPWhiteListMVC.Util.getOptions();
            options.title = '新增IP白名单';
            EasyUIUtils.openAddDlg(options);
            IPWhiteListMVC.Util.fillUsersCombox("add", "0");
        },
        edit: function () {
            var row = $('#ipwhite-datagrid').datagrid('getSelected');
            if (row) {
                var options = IPWhiteListMVC.Util.getOptions();
                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改[' + options.data.id + ']IP白名单';
                EasyUIUtils.openEditDlg(options);
                IPWhiteListMVC.Util.fillUsersCombox("edit", row.adminUserId);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        getSearchUrl: function () {
            var fieldName = $("#field-name").combobox('getValue');
            var keyword = $("#keyword").val();
            return url = IPWhiteListMVC.URLs.list.url + '?fieldName=' + fieldName + '&keyword=' + keyword;

        },
        find: function () {
            var url = IPWhiteListMVC.Controller.getSearchUrl();
            $("#ipwhite-datagrid").datagrid({
                pageNumber: 1,
                pageSize: 50,
                url: url
            });
        },
        remove: function () {
            var row = $('#ipwhite-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: IPWhiteListMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#ipwhite-datagrid',
                    gridUrl: IPWhiteListMVC.URLs.list.url,
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
                gridUrl: IPWhiteListMVC.URLs.list.url,
                dlgId: "#ipwhite-dlg",
                formId: "#ipwhite-form",
                url: null,
                callback: function () {
                }
            };

            $('#adminUserId').val($('#combox-users').combobox('getValue'));
            options.url = (action === "edit" ? IPWhiteListMVC.URLs.edit.url : IPWhiteListMVC.URLs.add.url);
            options.gridId = '#ipwhite-datagrid';
            return EasyUIUtils.save(options);
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#ipwhite-dlg',
                formId: '#ipwhite-form',
                actId: '#modal-action',
                rowId: '#ipwhiteId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                gridId: null,
            };
        },
        fillUsersCombox: function (act, value) {
            var id = '#combox-users';
            $(id).combobox('clear');
            var data = [];
            var items = IPWhiteListMVC.Model.users;
            for (var i = 0; i < items.length; i++) {
                var item = items[i];
                data.push({
                    "value": item.id,
                    "name": item.name + '(' + item.id + ')',
                    "selected": i === 0
                });
            }
            $(id).combobox('loadData', data);
            if (act === "edit") {
                $(id).combobox('setValue', value);
            }
        },
        loadUsersList: function () {
            $.getJSON(IPWhiteListMVC.URLs.getAllUsers.url, function (src) {
                IPWhiteListMVC.Model.users = src.data;
            });
        }
    }
};