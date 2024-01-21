$(function () {
    CurrencyDetailInfo.init();
});

var CurrencyDetailInfo = {
    init: function () {
        CurrencyDetailInfoMVC.View.initControl();
        CurrencyDetailInfoMVC.View.bindEvent();
        CurrencyDetailInfoMVC.View.bindValidate();
    }
};

var CurrencyDetailInfoCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/extra/currency/detail/',
    uploadUrl: BossGlobal.ctxPath + '/v1/boss/upload/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/'
};

var CurrencyDetailInfoMVC = {
    URLs: {
        add: {
            url: CurrencyDetailInfoCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: CurrencyDetailInfoCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: CurrencyDetailInfoCommon.baseUrl + 'list',
            method: 'GET'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {

            $('#currencyDetailInfo-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: CurrencyDetailInfoMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        CurrencyDetailInfoMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        CurrencyDetailInfoMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#currencyDetailInfo-datagrid');
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
                                + 'onclick="CurrencyDetailInfoMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: CurrencyDetailInfoCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return CurrencyDetailInfoMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // dialogs
            $('#currencyDetailInfo-dlg').dialog({
                closed: true,
                modal: false,
                width: 860,
                height: 500,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#currencyDetailInfo-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: CurrencyDetailInfoMVC.Controller.save
                }]
            });


        },
        bindEvent: function () {
            $('#btn-search').bind('click', CurrencyDetailInfoMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#currencyDetailInfo-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return CurrencyDetailInfoMVC.Controller.edit();
            }
        },
        add: function () {
            var options = CurrencyDetailInfoMVC.Util.getOptions();
            options.title = '新增版本信息';
            EasyUIUtils.openAddDlg(options);
            $('#platform').combobox('setValue', "-1");
            $('#forceUpdate').combobox('setValue', "0");
        },
        edit: function () {
            var row = $('#currencyDetailInfo-datagrid').datagrid('getSelected');
            if (row) {
                var options = CurrencyDetailInfoMVC.Util.getOptions();

                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改版本信息';

                //console.log(options);
                EasyUIUtils.openEditDlg(options);

            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        find: function () {
            var url = CurrencyDetailInfoMVC.Util.getSearchUrl();
            $("#currencyDetailInfo-datagrid").datagrid({
                pageNumber: 1,
                pageSize: 50,
                url: url
            });
        },
        save: function () {
            var action = $('#modal-action').val();

            var options = {
                gridId: null,
                gridUrl: CurrencyDetailInfoMVC.URLs.list.url,
                dlgId: "#currencyDetailInfo-dlg",
                formId: "#currencyDetailInfo-form",
                url: null,
                callback: function () {
                }
            };

            if (action === "edit") {
                var row = $('#currencyDetailInfo-datagrid').datagrid('getSelected');
                //取得ID传给后台
                options.url = CurrencyDetailInfoMVC.URLs.edit.url + '?id=' + row.id;
            } else {
                //添加操作
                options.url = CurrencyDetailInfoMVC.URLs.add.url;
                options.gridId = '#currencyDetailInfo-datagrid';
            }
            options.callback = function (result) {
                if (!result.code) {
                    EasyUIUtils.reloadDatagrid('#currencyDetailInfo-datagrid');
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
                dlgId: '#currencyDetailInfo-dlg',
                formId: '#currencyDetailInfo-form',
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
            var currencyDetailInfoPlatform = $("#currencyDetailInfo-platform").textbox('getValue');
            var currencyDetailInfoPlatform = $("#currencyDetailInfo-platform").textbox('getValue');

            var url = CurrencyDetailInfoMVC.URLs.list.url + '?platform=' + currencyDetailInfoPlatform;

            return url;
        }
    }
};