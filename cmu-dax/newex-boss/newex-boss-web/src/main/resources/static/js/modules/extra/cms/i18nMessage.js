$(function () {
    I18nMessage.init();
});

var I18nMessage = {
    init: function () {
        I18nMessageMVC.View.initControl();
        I18nMessageMVC.View.bindEvent();
        I18nMessageMVC.View.bindValidate();
    }
};

var I18nMessageCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/extra/cms/i18n/messages/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    messageCodeUrl: BossGlobal.ctxPath + '/v1/boss/extra/cms/i18n/message-codes/'
};

var I18nMessageMVC = {
    URLs: {
        add: {
            url: I18nMessageCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: I18nMessageCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: I18nMessageCommon.baseUrl + 'message',
            method: 'GET'
        },
        search: {
            url: I18nMessageCommon.messageCodeUrl + 'list',
            method: 'GET'
        },
        codeTree: {
            url: I18nMessageCommon.baseUrl + 'codeTree',
            method: 'GET'
        },
        download: {
            url: I18nMessageCommon.baseUrl + 'download',
            method: 'GET'
        },
        generate: {
            url: I18nMessageCommon.baseUrl + 'generate',
            method: 'GET'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {
            $.get(I18nMessageMVC.URLs.codeTree.url, {"parentId": 0}, function (_data) {
                $("#code-treegrid").treegrid({
                    width: '100%',
                    data: _data,
                    idField: 'id',
                    treeField: 'name',
                    nowrap: false,
                    lines: false,
                    dnd: true,
                    onlyLeafCheck: true,
                    animate: true,
                    collapsible: true,
                    singleSelect: true,
                    columns: [[
                        {field: 'id', title: 'ID', width: 50},
                        {field: 'name', title: '名称', width: 400},
                        {field: 'code', title: '编码', hidden: true},
                        {field: 'type', title: '页节点', hidden: true},
                        {field: 'parentId', title: '父节点', hidden: true}
                    ]],
                    onClickRow: function (row) {
                        if (row.type == 'cate') {
                            $.get(I18nMessageMVC.URLs.codeTree.url, {"parentId": row.id}, function (d) {
                                if (row.children === undefined) {
                                    $("#code-treegrid").treegrid('append', {
                                        parent: row.id,
                                        data: d
                                    });
                                }
                            });
                            var item = $('#user-datagrid').datagrid('getRows');
                            for (var i = item.length - 1; i >= 0; i--) {
                                var index = $('#user-datagrid').datagrid('getRowIndex', item[i]);
                                $('#user-datagrid').datagrid('deleteRow', index);
                            }
                        }
                        else {
                            I18nMessageMVC.Controller.getLocalMessage(row)
                        }

                    }
                });
            });
            $('#user-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                rownumbers: true,
                pageSize: 50,
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#user-datagrid');
                    }
                }, '-', {
                    iconCls: 'icon-edit',
                    text: '生成JS文件',
                    handler: function () {
                        I18nMessageMVC.Controller.generate();
                    }
                }, '-', {
                    iconCls: 'icon-download',
                    text: '下载JS文件到本地',
                    handler: function () {
                        I18nMessageMVC.Controller.download();
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
                    hidden: true
                }, {
                    field: 'msgCodeId',
                    title: '本地化编码ID',
                    width: 150,
                    hidden: true
                }, {
                    field: 'msgCode',
                    title: '本地化编码',
                    width: 300,
                    sortable: true
                }, {
                    field: 'locale',
                    title: '语言',
                    width: 80,
                    sortable: true
                }, {
                    field: 'msgText',
                    title: '文本内容',
                    width: 400
                }, {
                    field: 'options',
                    title: '操作',
                    width: 50,
                    formatter: function (value, row, index) {
                        var icons = [{
                            "name": "edit",
                            "title": "编辑"
                        }];
                        var buttons = [];
                        for (var i = 0; i < icons.length; i++) {
                            var tmpl = '<a href="#" title ="${title}" '
                                + 'onclick="I18nMessageMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: I18nMessageCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return I18nMessageMVC.Controller.edit();
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
                    handler: I18nMessageMVC.Controller.save
                }]
            });


        },
        bindEvent: function () {
            $('#btn-search').bind('click', I18nMessageMVC.Controller.find);
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
                return I18nMessageMVC.Controller.edit();
            }
        },
        getLocalMessage: function (row) {
            $('#code-treegrid').treegrid('selectRow');
            var url = I18nMessageMVC.URLs.list.url + "?code=" + row.code + "&codeId=" + row.id;
            $('#user-datagrid').datagrid({
                url: url
            })

        },
        add: function () {
            var options = I18nMessageMVC.Util.getOptions();
            options.title = '新增本地化文本';
            EasyUIUtils.openAddDlg(options);
        },
        edit: function () {
            var row = $('#user-datagrid').datagrid('getSelected');
            if (row) {
                var options = I18nMessageMVC.Util.getOptions();
                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改[' + options.data.locale + ']-[' + options.data.msgCode + ']';
                EasyUIUtils.openEditDlg(options);

            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        find: function () {
            var name = $("#search-msgText").textbox('getValue');
            if (name === '') {
                return;
            }
            $.get(I18nMessageMVC.URLs.search.url, {"name": name}, function (_data) {
                $("#code-treegrid").treegrid({
                    data: _data.data
                });
            });
        },
        save: function () {
            var codeId = $('#msgCodeId').val();
            var code = $('#msgCode').val();

            var options = {
                gridId: null,
                gridUrl: I18nMessageMVC.URLs.list.url + '?codeId=' + codeId + '&code=' + code,
                dlgId: "#user-dlg",
                formId: "#user-form",
                url: null,
                callback: function () {
                }
            };
            options.url = I18nMessageMVC.URLs.edit.url;
            options.gridId = '#user-datagrid';
            options.callback = function (result) {
                if (!result.code) {
                    EasyUIUtils.reloadDatagrid('#user-datagrid');
                }
            };
            EasyUIUtils.save(options);
            return;
        },
        generate: function () {
            $.getJSON(I18nMessageMVC.URLs.generate.url, function (d) {
                if (!d.code) {
                    $.messager.alert("提示", "已经生成，请点击下载", "")
                } else {
                    $.messager.alert("失败", "生成失败", "")
                }
            })
        },
        download: function () {
            window.open(I18nMessageMVC.URLs.download.url, "_blank", "height=100, width=400, toolbar= no, menubar=no, scrollbars=no, resizable=no, location=no, status=no")
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