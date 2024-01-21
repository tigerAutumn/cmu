$(function () {
    VersionInfo.init();
});

var VersionInfo = {
    init: function () {
        VersionInfoMVC.View.initControl();
        VersionInfoMVC.View.bindEvent();
        VersionInfoMVC.View.bindValidate();
    }
};

var VersionInfoCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/extra/cms/version-info/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/'
};

var VersionInfoMVC = {
    URLs: {
        add: {
            url: VersionInfoCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: VersionInfoCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: VersionInfoCommon.baseUrl + 'list',
            method: 'GET'
        },
        getBrokerList: {
            url: VersionInfoCommon.brokerBaseUrl + 'list',
            method: 'GET'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {

            $("#brokerId").combobox({
                url: VersionInfoMVC.URLs.getBrokerList.url,
                method: 'get',
                valueField: "id",
                textField: "brokerName",
                editable: true,
                value: '',
                prompt: '选择券商',
                panelHeight: 300
            });

            $('#versionInfo-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: VersionInfoMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        VersionInfoMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        VersionInfoMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#versionInfo-datagrid');
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
                    field: 'platform',
                    title: '客户端平台',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value == 1) {
                            return "iOS";
                        }
                        if (value == 2) {
                            return "Android";
                        }
                        if (value == 11) {
                            return "iOS APP下载";
                        }
                        if (value == 22) {
                            return "Android APP下载";
                        }

                        return "";
                    }
                }, {
                    field: 'newVersion',
                    title: '最新版本号',
                    width: 150,
                    sortable: true,
                }, {
                    field: 'forceUpdate',
                    title: '强制升级',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value == 0) {
                            return "不提醒";
                        }
                        if (value == 1) {
                            return "仅提醒升级";
                        }
                        if (value == 2) {
                            return "强制升级";
                        }
                        return "";
                    }
                }, {
                    field: 'downloadUrl',
                    title: '下载链接',
                    width: 150,
                    sortable: true,
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
                        }];
                        var buttons = [];
                        for (var i = 0; i < icons.length; i++) {
                            var tmpl = '<a href="#" title ="${title}" '
                                + 'onclick="VersionInfoMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: VersionInfoCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return VersionInfoMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // dialogs
            $('#versionInfo-dlg').dialog({
                closed: true,
                modal: false,
                width: 860,
                height: 500,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#versionInfo-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: VersionInfoMVC.Controller.save
                }]
            });


        },
        bindEvent: function () {
            $('#btn-search').bind('click', VersionInfoMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#versionInfo-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return VersionInfoMVC.Controller.edit();
            }
        },
        add: function () {
            var options = VersionInfoMVC.Util.getOptions();
            options.title = '新增版本信息';
            EasyUIUtils.openAddDlg(options);
            $('#platform').combobox('setValue', "-1");
            $('#forceUpdate').combobox('setValue', "0");
            $("#brokerId").combobox('readonly', false);
        },
        edit: function () {
            var row = $('#versionInfo-datagrid').datagrid('getSelected');
            if (row) {
                var options = VersionInfoMVC.Util.getOptions();

                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改版本信息';

                //console.log(options);
                EasyUIUtils.openEditDlg(options);

                $("#brokerId").combobox('readonly', true);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        find: function () {
            var url = VersionInfoMVC.Util.getSearchUrl();
            $("#versionInfo-datagrid").datagrid({
                pageNumber: 1,
                pageSize: 50,
                url: url
            });
        },
        save: function () {
            var action = $('#modal-action').val();

            var options = {
                gridId: null,
                gridUrl: VersionInfoMVC.URLs.list.url,
                dlgId: "#versionInfo-dlg",
                formId: "#versionInfo-form",
                url: null,
                callback: function () {
                }
            };

            if (action === "edit") {
                var row = $('#versionInfo-datagrid').datagrid('getSelected');
                //取得ID传给后台
                options.url = VersionInfoMVC.URLs.edit.url + '?id=' + row.id;
            } else {
                //添加操作
                options.url = VersionInfoMVC.URLs.add.url;
                options.gridId = '#versionInfo-datagrid';
            }
            options.callback = function (result) {
                if (!result.code) {
                    EasyUIUtils.reloadDatagrid('#versionInfo-datagrid');
                }
            }
            //修改后刷新列表
            EasyUIUtils.save(options);

            return;
        }

    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#versionInfo-dlg',
                formId: '#versionInfo-form',
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
        getSearchUrl: function () {
            var versionInfoPlatform = $("#versionInfo-platform").combobox('getValue');
            var url = VersionInfoMVC.URLs.list.url + '?platform=' + versionInfoPlatform;

            return url;
        }
    }
};