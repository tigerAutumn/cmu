$(function () {
    News.init();
});

var News = {
    init: function () {
        NewsMVC.View.initControl();
        NewsMVC.View.bindEvent();
        NewsMVC.View.bindValidate();
    }
};

var NewsCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/extra/cms/news/',
    imageUrl: BossGlobal.ctxPath + '/v1/boss/upload/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    categoryUrl: BossGlobal.ctxPath + '/v1/boss/extra/cms/news/categories/',
    templateUrl: BossGlobal.ctxPath + '/v1/boss/extra/cms/news/templates/',
    commonUrl: BossGlobal.ctxPath + '/v1/boss/common/'
};

var NewsMVC = {
    URLs: {
        add: {
            url: NewsCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: NewsCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: NewsCommon.baseUrl + 'list',
            method: 'GET'
        },
        categoryGroup: {
            url: NewsCommon.categoryUrl + 'list/all/group',
            method: 'GET'
        },
        categoryTree: {
            url: NewsCommon.categoryUrl + 'list/all/tree',
            method: 'GET'
        },
        template: {
            url: NewsCommon.templateUrl + 'list/all',
            method: 'GET'
        },
        lang:{
            url: NewsCommon.commonUrl + 'lang',
            method:'GET'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {

            $.get(NewsMVC.URLs.lang.url, "", function (_datax) {
                console.log(_datax);
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
                $("#news-locale").combobox({
                    data: _datax2,
                    valueField: 'value',
                    textField: 'text',
                    required: true,
                    onLoadSuccess: function () {
                        $("#news-locale").combobox('setValue','');//设置默认值
                    }
                });

            });

            $.get(NewsMVC.URLs.categoryTree.url, "", function (_datax) {
                _datax.unshift({
                    'id': '-1',
                    'text': '全部'
                });//向json数组开头添加自定义数据
                $("#news-category").combotree({
                    data: _datax,
                    onLoadSuccess: function () { //加载完成后,设置选中第一项
                        $('#news-category').combotree('setValue', "-1");
                    }
                });
            });

            $.get(NewsMVC.URLs.categoryTree.url, "", function (_datax) {
                _datax.unshift({
                    'id': '0',
                    'text': '--请选择--'
                });//向json数组开头添加自定义数据
                $("#categoryId").combotree({
                    data: _datax,
                    required: true
                });
            });

            $.get(NewsMVC.URLs.template.url, "", function (_datax) {

                $("#templateId").combobox({
                    data: _datax,
                    valueField: "id",
                    textField: "name",
                    required: true
                });

            });

            KindEditor.ready(function (K) {
                window.editor = K.create('textarea[name="content-editor"]', {
                    resizeType: 1,
                    allowPreviewEmoticons: false,
                    allowImageUpload: false,
                    items: [
                        'source', '|', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
                        'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
                        'insertunorderedlist', '|', 'emoticons', 'image', 'link']
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
                url: NewsMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        NewsMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        NewsMVC.Controller.edit();
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
                    //return $.messager.alert('失败', src.msg, 'error');
                    return {total: 0, rows: []};
                },
                columns: [[{
                    field: 'id',
                    title: 'ID',
                    width: 50,
                    sortable: true
                }, {
                    field: 'categoryId',
                    title: '新闻类别id',
                    width: 100,
                    sortable: true
                }, {
                    field: 'templateId',
                    title: '网页模板id',
                    width: 100,
                    sortable: true
                }, {
                    field: 'publisherId',
                    title: '发布用户id',
                    width: 100,
                    sortable: true
                }, {
                    field: 'uid',
                    title: '新闻编号',
                    width: 200,
                    sortable: true,
                }, {
                    field: 'locale',
                    title: '语言',
                    width: 100,
                    sortable: true,

                }, {
                    field: 'title',
                    title: '新闻标题',
                    width: 250,
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
                                + 'onclick="NewsMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: NewsCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return NewsMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    //return $.messager.alert('失败', data.responseJSON.msg, 'error');
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
                    handler: NewsMVC.Controller.save
                }]
            });

        },
        bindEvent: function () {
            $('#btn-search').bind('click', NewsMVC.Controller.find);
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
                return NewsMVC.Controller.edit();
            }
        },
        add: function () {
            window.editor.html('');

            var options = NewsMVC.Util.getOptions();
            options.title = '新增新闻';
            EasyUIUtils.openAddDlg(options);

            var allTemplate = $('#templateId').combobox('getData');
            if (allTemplate) {
                $('#templateId').combobox('select', allTemplate[0]['id']);
            }

            $('#categoryId').combotree('setValue', '0');

            $('#locale').combobox('setValue', 'zh-cn');
        },
        edit: function () {
            var row = $('#user-datagrid').datagrid('getSelected');
            if (row) {
                var options = NewsMVC.Util.getOptions();

                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改新闻[' + options.data.title + ']';

                EasyUIUtils.openEditDlg(options);

                window.editor.html(options.data.content);

            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        find: function () {
            var params = NewsMVC.Util.getQueryParams();

            $('#user-datagrid').datagrid('load', params);
        },
        save: function () {
            var action = $('#modal-action').val();

            var options = NewsMVC.Util.getOptions();

            var contentEditorHtml = window.editor.html();
            $('#content').val(contentEditorHtml);

            if (action === "edit") {
                var row = $('#user-datagrid').datagrid('getSelected');
                //取得ID传给后台
                options.url = NewsMVC.URLs.edit.url + '?id=' + row.id;
            } else {
                //添加操作
                options.url = NewsMVC.URLs.add.url;
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
                gridUrl: NewsMVC.URLs.list.url
            };
        },
        getQueryParams: function () {
            var newsCategory = $("#news-category").combobox("getValue");
            var newsLocale = $("#news-locale").val();
            var newsTitle = $("#news-title").textbox("getValue");

            var params = {
                categoryId: newsCategory,
                title: newsTitle,
                locale: newsLocale=='全部'?'':newsLocale
            };

            return params;
        }
    }
};