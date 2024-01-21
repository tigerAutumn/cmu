$(function () {
    NewsCategory.init();
});

var NewsCategory = {
    init: function () {
        NewsCategoryMVC.View.initControl();
        NewsCategoryMVC.View.bindEvent();
        NewsCategoryMVC.View.bindValidate();
    }
};

var NewsCategoryCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/extra/cms/news/categories/',
    imageUrl: BossGlobal.ctxPath + '/v1/boss/upload/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    commonUrl: BossGlobal.ctxPath + '/v1/boss/common/'
};

var NewsCategoryMVC = {
    URLs: {
        add: {
            url: NewsCategoryCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: NewsCategoryCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: NewsCategoryCommon.baseUrl + 'list',
            method: 'GET'
        },
        treeList: {
            url: NewsCategoryCommon.baseUrl + 'list/all/tree',
            method: 'GET'
        },
        lang:{
            url: NewsCategoryCommon.commonUrl + 'lang',
            method:'GET'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {

            $.get(NewsCategoryMVC.URLs.lang.url, "", function (_datax) {
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
                $("#newsCategory-locale").combobox({
                    data: _datax2,
                    valueField: 'value',
                    textField: 'text',
                    required: true,
                    onLoadSuccess: function () {
                        $("#newsCategory-locale").combobox('setValue','');//设置默认值
                    }
                });

            });

            $.get(NewsCategoryMVC.URLs.treeList.url, "", function (_data) {
                _data.unshift({
                    'id': '0',
                    'text': '--请选择--'
                });//向json数组开头添加自定义数据
                $("#parentId").combotree({
                    data: _data
                });
            });

            $('#user-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: NewsCategoryMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        NewsCategoryMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        NewsCategoryMVC.Controller.edit();
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
                    field: 'parentId',
                    title: '父节点id',
                    width: 100,
                    sortable: true
                }, {
                    field: 'locale',
                    title: '语言',
                    width: 100,
                    sortable: true,

                }, {
                    field: 'name',
                    title: '名称',
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
                                + 'onclick="NewsCategoryMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: NewsCategoryCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return NewsCategoryMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // dialogs
            $('#user-dlg').dialog({
                closed: true,
                modal: false,
                width: 760,
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
                    handler: NewsCategoryMVC.Controller.save
                }]
            });


        },
        bindEvent: function () {
            $('#btn-search').bind('click', NewsCategoryMVC.Controller.find);
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
                return NewsCategoryMVC.Controller.edit();
            }
        },
        add: function () {
            var options = NewsCategoryMVC.Util.getOptions();
            options.title = '新增新闻类别';
            EasyUIUtils.openAddDlg(options);

            $('#parentId').combotree('setValue', "0");

            $('#locale').combobox('select', 'zh-cn');
        },
        edit: function () {
            var row = $('#user-datagrid').datagrid('getSelected');
            if (row) {
                var options = NewsCategoryMVC.Util.getOptions();

                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改新闻类别[' + options.data.name + ']';

                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        find: function () {
            var params = NewsCategoryMVC.Util.getQueryParams();
            $('#user-datagrid').datagrid('load', params);
        },
        save: function () {
            var action = $('#modal-action').val();

            var options = NewsCategoryMVC.Util.getOptions();

            if (action === "edit") {
                var row = $('#user-datagrid').datagrid('getSelected');
                //取得ID传给后台
                options.url = NewsCategoryMVC.URLs.edit.url + '?id=' + row.id;
            }
            else {
                //添加操作
                options.url = NewsCategoryMVC.URLs.add.url;
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
                gridUrl: NewsCategoryMVC.URLs.list.url
            };
        },
        getQueryParams: function () {
            var newsCategoryLocale = $("#newsCategory-locale").val();
            var newsCategoryName = $("#newsCategory-name").val();
            var params = {
                locale: newsCategoryLocale,
                name: newsCategoryName
            };

            return params;
        }
    }
};