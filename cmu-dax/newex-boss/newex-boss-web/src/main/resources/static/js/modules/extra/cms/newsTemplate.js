$(function () {
    NewsTemplate.init();
});

var NewsTemplate = {
    init: function () {
        NewsTemplateMVC.View.initControl();
        NewsTemplateMVC.View.bindEvent();
        NewsTemplateMVC.View.bindValidate();
    }
};

var NewsTemplateCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/extra/cms/news/templates/',
    imageUrl: BossGlobal.ctxPath + '/v1/boss/upload/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/'
};

var NewsTemplateMVC = {
    URLs: {
        add: {
            url: NewsTemplateCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: NewsTemplateCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: NewsTemplateCommon.baseUrl + 'list',
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
                url: NewsTemplateMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        NewsTemplateMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        NewsTemplateMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#user-datagrid').datagrid('load', {});
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
                    title: '模板名称',
                    width: 150,
                    sortable: true
                }, {
                    field: 'memo',
                    title: '模板备注',
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
                                + 'onclick="NewsTemplateMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: NewsTemplateCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return NewsTemplateMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // dialogs
            $('#user-dlg').dialog({
                closed: true,
                modal: false,
                width: 860,
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
                    handler: NewsTemplateMVC.Controller.save
                }]
            });

        },
        bindEvent: function () {
            $('#btn-search').bind('click', NewsTemplateMVC.Controller.find);
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
                return NewsTemplateMVC.Controller.edit();
            }
        },
        add: function () {
            var options = NewsTemplateMVC.Util.getOptions();
            options.title = '新增网页模板';
            EasyUIUtils.openAddDlg(options);
        },
        edit: function () {
            var row = $('#user-datagrid').datagrid('getSelected');
            if (row) {
                var options = NewsTemplateMVC.Util.getOptions();

                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改网页模板[' + options.data.name + ']';

                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        find: function () {
            var params = NewsTemplateMVC.Util.getQueryParams();

            $('#user-datagrid').datagrid('load', params);
        },
        save: function () {
            var action = $('#modal-action').val();

            var options = NewsTemplateMVC.Util.getOptions();

            if (action === "edit") {
                var row = $('#user-datagrid').datagrid('getSelected');
                //取得ID传给后台
                options.url = NewsTemplateMVC.URLs.edit.url + '?id=' + row.id;
            } else {
                //添加操作
                options.url = NewsTemplateMVC.URLs.add.url;
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
                url: null,
                gridId: '#user-datagrid',
                gridUrl: NewsTemplateMVC.URLs.list.url
            };
        },
        getQueryParams: function () {
            var newsTemplateName = $("#newsTemplate-name").textbox("getValue");

            var params = {
                name: newsTemplateName
            };

            return params;
        }
    }
};