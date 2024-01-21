$(function () {
    TagCategory.init();
});

var TagCategory = {
    init: function () {
        TagCategoryMVC.View.initControl();
        TagCategoryMVC.View.bindEvent();
        TagCategoryMVC.View.bindValidate();
    }
};

var TagCategoryCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/extra/currency/tag-category/',
    imageUrl: BossGlobal.ctxPath + '/v1/boss/upload/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    commonUrl: BossGlobal.ctxPath + '/v1/boss/common/'
};

var TagCategoryMVC = {
    URLs: {
        add: {
            url: TagCategoryCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: TagCategoryCommon.baseUrl + 'edit',
            method: 'POST'
        },
        remove: {
            url: TagCategoryCommon.baseUrl + 'remove',
            method: 'POST'
        },
        list: {
            url: TagCategoryCommon.baseUrl + 'list',
            method: 'GET'
        },
        lang:{
            url: TagCategoryCommon.commonUrl + 'lang',
            method:'GET'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {

            $.get(TagCategoryMVC.URLs.lang.url, "", function (_datax) {
                $("#locale").combobox({
                    data: _datax,
                    valueField: "code",
                    textField: "code",
                    required: true
                });
                var _datax2 = [{'value':'','text':'全部'}];
                for(var i=0;i<_datax.length;i++){
                    _datax2.push({
                        'value':_datax[i].code,
                        'text':_datax[i].code
                    });
                }
                $("#tagCategory-locale").combobox({
                    data: _datax2,
                    valueField: 'value',
                    textField: 'text',
                    required: true,
                    onLoadSuccess: function () {
                        $("#tagCategory-locale").combobox('setValue','');//设置默认值
                    }
                });

            });

            $('#tagCategory-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: TagCategoryMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        TagCategoryMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        TagCategoryMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#tagCategory-datagrid').datagrid('load', {});
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
                    title: '分类名称',
                    width: 100,
                    sortable: true
                },{
                    field: 'locale',
                    title: '分类语言',
                    width: 100,
                    sortable: true
                },{
                    field: 'code',
                    title: '分类编码',
                    width: 100,
                    sortable: true
                }, {
                    field: 'type',
                    title: '类别',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value == 1) {
                            return '币种标签';
                        } else if (value == 2) {
                            return '币对标签';
                        }

                        return '';
                    }
                },{
                    field: 'sort',
                    title: '分类排序',
                    width: 100,
                    sortable: true
                },  {
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
                                + 'onclick="TagCategoryMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: TagCategoryCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return TagCategoryMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // dialogs
            $('#tagCategory-dlg').dialog({
                closed: true,
                modal: false,
                width: 700,
                height: 350,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#tagCategory-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: TagCategoryMVC.Controller.save
                }]
            });


        },
        bindEvent: function () {
            $('#btn-search').bind('click', TagCategoryMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#tagCategory-datagrid').datagrid('selectRow', index);

            if (name === "edit") {
                return TagCategoryMVC.Controller.edit();
            }

            if (name === "remove") {
                return TagCategoryMVC.Controller.remove();
            }
        },
        add: function () {
            var options = TagCategoryMVC.Util.getOptions();
            options.title = '新增标签分类';
            EasyUIUtils.openAddDlg(options);

            $('#type').combobox('setValue', "1");
            $('#choose').combobox('setValue', "1");
        },
        edit: function () {
            var row = $('#tagCategory-datagrid').datagrid('getSelected');
            if (row) {
                var options = TagCategoryMVC.Util.getOptions();

                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改标签分类[' + options.data.name + ']';

                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#tagCategory-datagrid').datagrid('getSelected');
            if (row) {
                var options = TagCategoryMVC.Util.getOptions();

                options.url = TagCategoryMVC.URLs.remove.url;
                options.rows = [row];
                options.data = {id: row.id,code: row.code};

                EasyUIUtils.remove(options);
            }
        },
        find: function () {
            var params = TagCategoryMVC.Util.getQueryParams();

            $('#tagCategory-datagrid').datagrid('load', params);
        },
        save: function () {
            var action = $('#modal-action').val();

            var options = TagCategoryMVC.Util.getOptions();

            if (action === "edit") {
                var row = $('#tagCategory-datagrid').datagrid('getSelected');
                //取得ID传给后台
                options.url = TagCategoryMVC.URLs.edit.url + '?id=' + row.id;
            }
            else {
                //添加操作
                options.url = TagCategoryMVC.URLs.add.url;
            }

            //修改后刷新列表
            EasyUIUtils.save(options);

            return;
        }

    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#tagCategory-dlg',
                formId: '#tagCategory-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                url: null,
                gridId: '#tagCategory-datagrid',
                gridUrl: TagCategoryMVC.URLs.list.url
            };
        },
        getQueryParams: function () {
            var name = $('#tagCategory-name').textbox('getValue');
            var type = $('#tagCategory-type').combobox('getValue');
            var locale = $('#tagCategory-locale').val();

            var params = {
                name: name,
                type: type,
                locale: locale
            };

            return params;
        }
    }
};