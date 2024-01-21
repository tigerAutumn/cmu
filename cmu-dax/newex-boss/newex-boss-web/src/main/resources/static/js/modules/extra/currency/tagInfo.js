$(function () {
    TagInfo.init();
});

var TagInfo = {
    init: function () {
        TagInfoMVC.View.initControl();
        TagInfoMVC.View.bindEvent();
        TagInfoMVC.View.bindValidate();
    }
};

var TagInfoCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/extra/currency/tag-info/',
    categoryBaseUrl: BossGlobal.ctxPath + '/v1/boss/extra/currency/tag-category/',
    imageUrl: BossGlobal.ctxPath + '/v1/boss/upload/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    commonUrl: BossGlobal.ctxPath + '/v1/boss/common/'
};

var TagInfoMVC = {
    URLs: {
        add: {
            url: TagInfoCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: TagInfoCommon.baseUrl + 'edit',
            method: 'POST'
        },
        remove: {
            url: TagInfoCommon.baseUrl + 'remove',
            method: 'POST'
        },
        list: {
            url: TagInfoCommon.baseUrl + 'list',
            method: 'GET'
        },
        category: {
            url: TagInfoCommon.categoryBaseUrl + 'listAll',
            method: 'GET'
        },
        lang:{
            url: TagInfoCommon.commonUrl + 'lang',
            method:'GET'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {

            $.get(TagInfoMVC.URLs.lang.url, "", function (_datax) {
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
                $("#tagInfo-locale").combobox({
                    data: _datax2,
                    valueField: 'value',
                    textField: 'text',
                    required: true,
                    onLoadSuccess: function () {
                        $("#tagInfo-locale").combobox('setValue','');//设置默认值
                    }
                });

            });

            $.get(TagInfoMVC.URLs.category.url, "", function (_data) {
                var _datax = _data.data
                var _datax1 = [];
                var _datax2 = [{"value":"","text":"全部"}];
                for(var i=0;i<_datax.length;i++){
                    _datax2.push({"value": _datax[i].code,"text":_datax[i].name + "("+ _datax[i].code+")"});
                    _datax1.push({"value": _datax[i].code,"text":_datax[i].name + "("+ _datax[i].code+")"});
                }
                $("#tagCategoryCode").combobox({
                    data: _datax1,
                    valueField: "value",
                    textField: "text",
                    required: true
                });
                $("#tagInfo-tagCategoryCode").combobox({
                    data: _datax2,
                    valueField: "value",
                    textField: "text",
                    onLoadSuccess: function () {
                        $("#tagInfo-tagCategoryCode").combobox("setValue", '');
                    }
                });
            });

            $('#tagInfo-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: TagInfoMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        TagInfoMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        TagInfoMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#tagInfo-datagrid').datagrid('load', {});
                    }
                }, '-', {
                        text: '刷新缓存',
                        iconCls: 'icon-refresh',
                        handler: function () {
                            TagInfoMVC.Controller.refresh();
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
                    title: '标签名称',
                    width: 100,
                    sortable: true
                }, {
                    field: 'locale',
                    title: '标签语言',
                    width: 100,
                    sortable: true
                }, {
                    field: 'code',
                    title: '标签编码',
                    width: 100,
                    sortable: true
                }, {
                    field: 'tagCategoryCode',
                    title: '标签分类编码',
                    width: 100,
                    sortable: true
                },{
                    field: 'sort',
                    title: '标签排序',
                    width: 100,
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
                        }, {
                            "name": "remove",
                            "title": "删除"
                        }];

                        var buttons = [];
                        for (var i = 0; i < icons.length; i++) {
                            var tmpl = '<a href="#" title ="${title}" '
                                + 'onclick="TagInfoMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: TagInfoCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return TagInfoMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // dialogs
            $('#tagInfo-dlg').dialog({
                closed: true,
                modal: false,
                width: 700,
                height: 350,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#tagInfo-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: TagInfoMVC.Controller.save
                }]
            });


        },
        bindEvent: function () {
            $('#btn-search').bind('click', TagInfoMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#tagInfo-datagrid').datagrid('selectRow', index);

            if (name === "edit") {
                return TagInfoMVC.Controller.edit();
            }

            if (name === "remove") {
                return TagInfoMVC.Controller.remove();
            }
        },
        add: function () {
            var options = TagInfoMVC.Util.getOptions();
            options.title = '新增标签';
            EasyUIUtils.openAddDlg(options);

            $.get(TagInfoMVC.URLs.category.url, "", function (_datax) {

                $("#tagCategoryId").combobox({
                    data: _datax.data,
                    valueField: "id",
                    textField: "name",
                    onLoadSuccess: function () {
                        var allData = $("#tagCategoryId").combobox("getData");
                        if (allData) {
                            $("#tagCategoryId").combobox("setValue", allData[0]["id"]);
                        }
                    }
                });
            });
        },
        edit: function () {
            var row = $('#tagInfo-datagrid').datagrid('getSelected');
            if (row) {
                $.get(TagInfoMVC.URLs.category.url, "", function (_datax) {

                    $("#tagCategoryId").combobox({
                        data: _datax.data,
                        valueField: "id",
                        textField: "name"
                    });
                });

                var options = TagInfoMVC.Util.getOptions();

                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改标签[' + options.data.name + ']';

                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#tagInfo-datagrid').datagrid('getSelected');
            if (row) {
                var options = TagInfoMVC.Util.getOptions();

                options.url = TagInfoMVC.URLs.remove.url;
                options.rows = [row];
                options.data = {id: row.id,code: row.code};

                EasyUIUtils.remove(options);
            }
        },
        find: function () {
            var params = TagInfoMVC.Util.getQueryParams();

            $('#tagInfo-datagrid').datagrid('load', params);
        },
        save: function () {
            var action = $('#modal-action').val();

            var options = TagInfoMVC.Util.getOptions();

            if (action === "edit") {
                var row = $('#tagInfo-datagrid').datagrid('getSelected');
                //取得ID传给后台
                options.url = TagInfoMVC.URLs.edit.url + '?id=' + row.id;
            }
            else {
                //添加操作
                options.url = TagInfoMVC.URLs.add.url;
            }

            //修改后刷新列表
            EasyUIUtils.save(options);

            return;
        },
        refresh: function () {
            $.ajax({
                url: TagInfoMVC.URLs.category.url,
                type: 'GET',
                success: function (_data) {
                    var _datax = _data.data
                    var _datax1 = [];
                    var _datax2 = [{"value":"","text":"全部"}];
                    for(var i=0;i<_datax.length;i++){
                        _datax2.push({"value": _datax[i].code,"text":_datax[i].name + "("+ _datax[i].code+")"});
                        _datax1.push({"value": _datax[i].code,"text":_datax[i].name + "("+ _datax[i].code+")"});
                    }
                    $("#tagCategoryCode").combobox({
                        data: _datax1,
                        valueField: "value",
                        textField: "text",
                        required: true
                    });
                    $("#tagInfo-tagCategoryCode").combobox({
                        data: _datax2,
                        valueField: "value",
                        textField: "text",
                        onLoadSuccess: function () {
                            $("#tagInfo-tagCategoryCode").combobox("setValue", '');
                        }
                    });
                    return $.messager.alert('提示', '刷新成功', 'info');
                },
                error: function (result) {
                    if (result.responseJSON.code == "403") {
                        $.messager.alert('提示', '没有权限执行该操作', 'error');
                    } else {
                        $.messager.alert('提示', result.responseJSON.msg, 'error');
                    }
                }
            });
        },

    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#tagInfo-dlg',
                formId: '#tagInfo-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                url: null,
                gridId: '#tagInfo-datagrid',
                gridUrl: TagInfoMVC.URLs.list.url
            };
        },
        getQueryParams: function () {
            var name = $('#tagInfo-name').textbox('getValue');
            var tagCategoryCode = $('#tagInfo-tagCategoryCode').combobox('getValue');
            var locale = $('#tagInfo-locale').val();


            var params = {
                name: name,
                tagCategoryCode: tagCategoryCode,
                locale: locale
            };

            return params;
        }
    }
};