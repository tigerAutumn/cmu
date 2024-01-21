$(function () {
    WarningUserInfo.init();
});

var WarningUserInfo = {
    init: function () {
        WarningUserInfoMVC.View.initControl();
        WarningUserInfoMVC.View.bindEvent();
        WarningUserInfoMVC.View.bindValidate();
    }
};

var WarningUserInfoCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/spot/warning/user-info/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/'
};

var WarningUserInfoMVC = {
    URLs: {
        add: {
            url: WarningUserInfoCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: WarningUserInfoCommon.baseUrl + 'edit',
            method: 'POST'
        },
        remove: {
            url: WarningUserInfoCommon.baseUrl + 'remove',
            method: 'POST'
        },
        list: {
            url: WarningUserInfoCommon.baseUrl + 'list',
            method: 'GET'
        },
        getBrokerList: {
            url: WarningUserInfoCommon.brokerBaseUrl + 'list'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {

            $('#type').combobox({
                valueField: 'id',
                textField: 'value',
                data: [
                    {
                        id: 1,
                        value: '短信'
                    }, {
                        id: 2,
                        value: '邮件'
                    }, {
                        id: 0,
                        value: '两者皆可'
                    }
                ]
            });

            $('#warningUserInfo-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: WarningUserInfoMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        WarningUserInfoMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        WarningUserInfoMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#warningUserInfo-datagrid').datagrid('load', {});
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
                    field: 'email',
                    title: 'Email',
                    width: 100,
                    sortable: true
                }, {
                    field: 'mobile',
                    title: '手机号',
                    width: 100,
                    sortable: true
                }, {
                    field: 'username',
                    title: '用户名称',
                    width: 100,
                    sortable: true
                }, {
                    field: 'type',
                    title: '通知方式',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value === 0) {
                            return "两者皆可";
                        } else if (value === 1) {
                            return "短信";
                        } else if (value === 2) {
                            return "邮件";
                        } else {
                            return "";
                        }
                    }
                }, {
                    field: 'createdDate',
                    title: '创建时间',
                    width: 150,
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
                                + 'onclick="WarningUserInfoMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: WarningUserInfoCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return WarningUserInfoMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // dialogs
            $('#warningUserInfo-dlg').dialog({
                closed: true,
                modal: false,
                width: 760,
                height: 500,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#warningUserInfo-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: WarningUserInfoMVC.Controller.save
                }]
            });


        },
        bindEvent: function () {
            $('#btn-search').bind('click', WarningUserInfoMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#warningUserInfo-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return WarningUserInfoMVC.Controller.edit();
            }

            if (name === "remove") {
                return WarningUserInfoMVC.Controller.remove();
            }
        },
        add: function () {
            var options = WarningUserInfoMVC.Util.getOptions();
            options.title = '新增 告警用户配置';
            EasyUIUtils.openAddDlg(options);

            $('#type').combobox('setValue', '1');
        },
        edit: function () {
            var row = $('#warningUserInfo-datagrid').datagrid('getSelected');
            if (row) {
                var options = WarningUserInfoMVC.Util.getOptions();

                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改 告警用户配置';

                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#warningUserInfo-datagrid').datagrid('getSelected');
            if (row) {
                var options = WarningUserInfoMVC.Util.getOptions();

                options.url = WarningUserInfoMVC.URLs.remove.url;
                options.rows = [row];
                options.data = {id: row.id, code: row.code};

                EasyUIUtils.remove(options);
            }
        },
        find: function () {
            var params = WarningUserInfoMVC.Util.getQueryParams();
            $('#warningUserInfo-datagrid').datagrid('load', params);
        },
        save: function () {
            var action = $('#modal-action').val();

            var options = WarningUserInfoMVC.Util.getOptions();

            if (action === "edit") {
                var row = $('#warningUserInfo-datagrid').datagrid('getSelected');
                //取得ID传给后台
                options.url = WarningUserInfoMVC.URLs.edit.url + '?id=' + row.id;
            }
            else {
                //添加操作
                options.url = WarningUserInfoMVC.URLs.add.url;
            }

            //修改后刷新列表
            EasyUIUtils.save(options);

            return;
        }

    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#warningUserInfo-dlg',
                formId: '#warningUserInfo-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                url: null,
                gridId: '#warningUserInfo-datagrid',
                gridUrl: WarningUserInfoMVC.URLs.list.url
            };
        },
        getQueryParams: function () {
            var email = $("#warningUserInfo-email").val();
            var mobile = $("#warningUserInfo-mobile").val();

            var params = {
                email: email,
                mobile: mobile
            };

            return params;
        }
    }
};