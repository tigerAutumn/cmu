$(function () {
    I18nLanguage.init();
});

var I18nLanguage = {
    init: function () {
        I18nLanguageMVC.View.initControl();
        I18nLanguageMVC.View.bindEvent();
        I18nLanguageMVC.View.bindValidate();
    }
};

var I18nLanguageCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/extra/cms/i18n/languages/',
    imageUrl: BossGlobal.ctxPath + '/v1/boss/upload/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/'
};

var I18nLanguageMVC = {
    URLs: {
        add: {
            url: I18nLanguageCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: I18nLanguageCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: I18nLanguageCommon.baseUrl + 'list',
            method: 'GET'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {

            $('#user-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: I18nLanguageMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        I18nLanguageMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        I18nLanguageMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#user-datagrid');
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
                    field: 'name',
                    title: '语言名称',
                    width: 150,
                    sortable: true
                }, {
                    field: 'code',
                    title: '语言代号',
                    width: 150,
                    sortable: true
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
                                + 'onclick="I18nLanguageMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: I18nLanguageCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return I18nLanguageMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // dialogs
            $('#user-dlg').dialog({
                closed: true,
                modal: false,
                width: 660,
                height: 500,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#user-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: I18nLanguageMVC.Controller.save
                }]
            });


        },
        bindEvent: function () {
            $('#btn-search').bind('click', I18nLanguageMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#user-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return I18nLanguageMVC.Controller.edit();
            }
        },
        add: function () {
            var options = I18nLanguageMVC.Util.getOptions();
            options.title = '新增本地化语言';
            EasyUIUtils.openAddDlg(options);
        },
        edit: function () {
            var row = $('#user-datagrid').datagrid('getSelected');
            if (row) {
                var options = I18nLanguageMVC.Util.getOptions();

                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改本地化语言[' + options.data.name + ']';

                //console.log(options);
                EasyUIUtils.openEditDlg(options);

            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        find: function () {
            var i18nLanguageName = $("#i18nLanguage-name").val();
            var url = I18nLanguageMVC.URLs.list.url + '?name=' + i18nLanguageName;
            EasyUIUtils.loadToDatagrid('#user-datagrid', url)
        },
        save: function () {
            var action = $('#modal-action').val();

            var options = {
                gridId: null,
                gridUrl: I18nLanguageMVC.URLs.list.url,
                dlgId: "#user-dlg",
                formId: "#user-form",
                url: null,
                callback: function () {
                }
            };

            if (action === "edit") {
                var row = $('#user-datagrid').datagrid('getSelected');
                //取得ID传给后台
                options.url = I18nLanguageMVC.URLs.edit.url + '?id=' + row.id;
            } else {
                //添加操作
                options.url = I18nLanguageMVC.URLs.add.url;
                options.gridId = '#user-datagrid';
            }
            options.callback = function (result) {
                if (!result.code) {
                    EasyUIUtils.reloadDatagrid('#user-datagrid');
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
        }
    }
};