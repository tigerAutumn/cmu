$(function () {
    I18nMessageCode.init();
});

var I18nMessageCode = {
    init: function () {
        I18nMessageCodeMVC.View.initControl();
        I18nMessageCodeMVC.View.bindEvent();
        I18nMessageCodeMVC.View.bindValidate();
    }
};

var I18nMessageCodeCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/extra/cms/i18n/message-codes/',
    imageUrl: BossGlobal.ctxPath + '/v1/boss/upload/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/'
};

var I18nMessageCodeMVC = {
    URLs: {
        add: {
            url: I18nMessageCodeCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: I18nMessageCodeCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: I18nMessageCodeCommon.baseUrl + 'list',
            method: 'GET'
        },
        treeList: {
            url: I18nMessageCodeCommon.baseUrl + 'list/all/tree',
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
                url: I18nMessageCodeMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        I18nMessageCodeMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        I18nMessageCodeMVC.Controller.edit();
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
                    field: 'parentId',
                    title: '父节点ID',
                    width: 150,
                    sortable: true
                }, {
                    field: 'name',
                    title: '名称',
                    width: 150,
                    sortable: true
                }, {
                    field: 'code',
                    title: '编号',
                    width: 150,
                    sortable: true
                }, {
                    field: 'type',
                    title: '节点类型',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value == "cate") {
                            return "类别节点";
                        }
                        if (value == "leaf") {
                            return "叶子节点";
                        }

                        return "未知";
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
                        }];
                        var buttons = [];
                        for (var i = 0; i < icons.length; i++) {
                            var tmpl = '<a href="#" title ="${title}" '
                                + 'onclick="I18nMessageCodeMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: I18nMessageCodeCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return I18nMessageCodeMVC.Controller.edit();
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
                    handler: I18nMessageCodeMVC.Controller.save
                }]
            });


        },
        bindEvent: function () {
            $('#btn-search').bind('click', I18nMessageCodeMVC.Controller.find);
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
                return I18nMessageCodeMVC.Controller.edit();
            }
        },
        add: function () {
            $.get(I18nMessageCodeMVC.URLs.treeList.url, "", function (_data) {
                _data.unshift({
                    'id': '0',
                    'text': '--请选择--'
                });//向json数组开头添加自定义数据
                $("#parentId").combotree({
                    data: _data,
                    onLoadSuccess: function () { //加载完成后,设置选中第一项
                        $('#parentId').combotree('setValue', "0");
                    }
                });
            });

            var options = I18nMessageCodeMVC.Util.getOptions();
            options.title = '新增本地化编码';
            EasyUIUtils.openAddDlg(options);

            $('#type').combobox('setValue', "-1");
        },
        edit: function () {
            var row = $('#user-datagrid').datagrid('getSelected');
            if (row) {
                var options = I18nMessageCodeMVC.Util.getOptions();

                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改本地化编码[' + options.data.name + ']';

                var parentId = options.data.parentId;
                $.get(I18nMessageCodeMVC.URLs.treeList.url, "", function (_data) {
                    _data.unshift({
                        'id': '0',
                        'text': '--请选择--'
                    });//向json数组开头添加自定义数据
                    $("#parentId").combotree({
                        data: _data,
                        onLoadSuccess: function () {
                            $('#parentId').combotree('setValue', parentId);
                        }
                    });
                });

                //console.log(options);
                EasyUIUtils.openEditDlg(options);

            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        find: function () {
            var i18nMessageCodeName = $("#i18nMessageCode-name").val();
            var url = I18nMessageCodeMVC.URLs.list.url + '?name=' + i18nMessageCodeName;
            EasyUIUtils.loadToDatagrid('#user-datagrid', url)
        },
        save: function () {
            var action = $('#modal-action').val();

            var options = {
                gridId: null,
                gridUrl: I18nMessageCodeMVC.URLs.list.url,
                dlgId: "#user-dlg",
                formId: "#user-form",
                url: null,
                callback: function () {
                }
            };

            if (action === "edit") {
                var row = $('#user-datagrid').datagrid('getSelected');
                //取得ID传给后台
                options.url = I18nMessageCodeMVC.URLs.edit.url + '?id=' + row.id;
            } else {
                //添加操作
                options.url = I18nMessageCodeMVC.URLs.add.url;
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