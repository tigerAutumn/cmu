$(function () {
    CurrencyBaseInfo.init();
});

var CurrencyBaseInfo = {
    init: function () {
        CurrencyBaseInfoMVC.View.initControl();
        CurrencyBaseInfoMVC.View.bindEvent();
        CurrencyBaseInfoMVC.View.bindValidate();
    }
};

var CurrencyBaseInfoCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/extra/currency/base/',
    detailBaseUrl: BossGlobal.ctxPath + '/v1/boss/extra/currency/detail/',
    uploadUrl: BossGlobal.ctxPath + '/v1/boss/upload/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    tagUrl: BossGlobal.ctxPath + '/v1/boss/extra/currency/tag/'
};

var CurrencyBaseInfoMVC = {
    URLs: {
        add: {
            url: CurrencyBaseInfoCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: CurrencyBaseInfoCommon.baseUrl + 'edit',
            method: 'POST'
        },
        remove: {
            url: CurrencyBaseInfoCommon.baseUrl + 'remove',
            method: 'POST'
        },
        list: {
            url: CurrencyBaseInfoCommon.baseUrl + 'list',
            method: 'GET'
        },
        editDetailInfo: {
            url: CurrencyBaseInfoCommon.detailBaseUrl + 'edit',
            method: 'POST'
        },
        getBaseInfoByCode: {
            url: CurrencyBaseInfoCommon.baseUrl + 'getByCode',
            method: 'GET'
        },
        getDetailInfoByCode: {
            url: CurrencyBaseInfoCommon.detailBaseUrl + 'getByCode',
            method: 'GET'
        },
        img: {
            url: CurrencyBaseInfoCommon.uploadUrl + 'public/upload',
            method: 'GET'
        },
        editTag: {
            url: CurrencyBaseInfoCommon.tagUrl + 'editCurrencyTag',
            method: 'POST'
        },
        getAllTags: {
            url: CurrencyBaseInfoCommon.tagUrl + 'currencyTagList',
            method: 'GET'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {

            $('#status').combobox({
                valueField: 'id',
                textField: 'value',
                data: [
                    {
                        id: '1',
                        value: '待审核'
                    }, {
                        id: '2',
                        value: '已发布'
                    }, {
                        id: '3',
                        value: '已下架'
                    }
                ]
            });

            $('#currencyInfo-detail-locale').combobox({
                valueField: 'id',
                textField: 'value',
                data: [
                    {
                        id: 'zh-cn',
                        value: 'zh-cn'
                    }, {
                        id: 'en-us',
                        value: 'en-us'
                    }
                ],
                onLoadSuccess: function () { //加载完成后,设置选中第一项
                    $('#currencyInfo-detail-locale').combobox('select', 'zh-cn');
                },
                onSelect: function (record) {
                    var code = $('#currencyInfo-detail-code').textbox('getValue');
                    if (code) {
                        CurrencyBaseInfoMVC.Controller.fillDetailInfo(code, record.id);
                    }

                }
            });

            $('#imgUrl-file').filebox({
                buttonText: '选择',
                buttonAlign: 'right',
                prompt: '选择图片',
                onChange: function (newVal, oldVal) {
                    if (newVal) {
                        CurrencyBaseInfoMVC.Util.uploadImg();
                    }
                }
            });

            $('#currencyInfo-base-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: CurrencyBaseInfoMVC.URLs.list.url,
                toolbar: [{
                    text: '新增',
                    iconCls: 'icon-add',
                    handler: function () {
                        CurrencyBaseInfoMVC.Controller.add();
                    }
                }, '-', {
                    text: '编辑',
                    iconCls: 'icon-edit',
                    handler: function () {
                        CurrencyBaseInfoMVC.Controller.edit();
                    }
                }, '-', {
                    text: '删除',
                    iconCls: 'icon-remove',
                    handler: function () {
                        CurrencyBaseInfoMVC.Controller.remove();
                    }
                }, '-', {
                    text: '所有数据',
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#currencyInfo-base-datagrid').datagrid('load', {});
                    }
                }, '-', {
                    text: '编辑标签',
                    iconCls: 'icon-edit1',
                    handler: function () {
                        CurrencyBaseInfoMVC.Controller.tag();
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
                    field: 'code',
                    title: '简称',
                    width: 150,
                    sortable: true
                }, {
                    field: 'name',
                    title: '全称',
                    width: 150,
                    sortable: true
                }, {
                    field: 'symbol',
                    title: '符号',
                    width: 150,
                    sortable: true
                }, {
                    field: 'issuePrice',
                    title: '发行价格',
                    width: 150,
                    sortable: true
                }, {
                    field: 'issueDate',
                    title: '发行日期',
                    width: 150,
                    sortable: true
                }, {
                    field: 'maxSupply',
                    title: '发行总量',
                    width: 150,
                    sortable: true
                }, {
                    field: 'circulatingSupply',
                    title: '当前流通数量',
                    width: 150,
                    sortable: true
                }, {
                    field: 'officalWebsite',
                    title: '官网',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value) {
                            return '<a href="' + value + '" target="_blank">' + value + '</a>';
                        }

                        return '';
                    }
                }, {
                    field: 'explorer',
                    title: '区块浏览器地址',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value) {
                            return '<a href="' + value + '" target="_blank">浏览器</a>';
                        }

                        return '';
                    }
                }, {
                    field: 'status',
                    title: '状态',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value == 1) {
                            return '待审核';
                        }

                        if (value == 2) {
                            return '已发布';
                        }

                        if (value == 3) {
                            return '已下架';
                        }

                        return '';
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
                        }, {
                            "name": "remove",
                            "title": "删除"
                        }];

                        var buttons = [];
                        for (var i = 0; i < icons.length; i++) {
                            var tmpl = '<a href="#" title ="${title}" '
                                + 'onclick="CurrencyBaseInfoMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: CurrencyBaseInfoCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return CurrencyBaseInfoMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // dialogs
            $('#currencyInfo-base-dlg').dialog({
                closed: true,
                modal: false,
                width: 900,
                height: window.innerHeight - 100 || document.documentElement.clientHeight - 100 || document.body.clientHeight - 100,
                iconCls: 'icon-add',
                buttons: [{
                    text: '返回',
                    iconCls: 'icon-back',
                    handler: function () {
                        $('#currencyInfo-base-dlg').dialog('close');
                        $('#currencyInfo-base-datagrid').datagrid('load', {});
                    }
                }],
                onClose: function () {
                    $('#currencyInfo-base-datagrid').datagrid('load', {});
                }
            });

            $('#currencyInfo-tag-dlg').dialog({
                closed: true,
                modal: false,
                iconCls: 'icon-add',
                buttons: [
                    {
                        text: '修改',
                        iconCls: 'icon-edit1',
                        handler: function () {
                            var nodes = $('#tag-tree').tree('getChecked');
                            var tags = '';
                            for (var i = 0; i < nodes.length; i++) {
                                if (tags !== '') {
                                    tags += ',';
                                }
                                var node = nodes[i];
                                if (!$('#tag-tree').tree('isLeaf', node.target)) {
                                    continue;
                                }
                                var nodeParent = $('#tag-tree').tree('getParent', node.target);
                                var categoryId = nodeParent.id;
                                var tagId = node.id;
                                tags += categoryId + "/" + tagId;
                            }
                            CurrencyBaseInfoMVC.Controller.modifyTag(tags);
                        }
                    },
                    {
                        text: '返回',
                        iconCls: 'icon-back',
                        handler: function () {
                            $('#currencyInfo-tag-dlg').dialog('close');
                            $('#currencyInfo-base-datagrid').datagrid('load', {});
                        }
                    }
                ],
                onClose: function () {
                    $('#currencyInfo-base-datagrid').datagrid('load', {});
                }
            });

        },
        bindEvent: function () {
            $('#currency-btn-search').bind('click', CurrencyBaseInfoMVC.Controller.find);
            $('#base-btn-save').bind('click', CurrencyBaseInfoMVC.Controller.saveBaseInfo);
            $('#detail-btn-save').bind('click', CurrencyBaseInfoMVC.Controller.saveDetailInfo);
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#currencyInfo-base-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return CurrencyBaseInfoMVC.Controller.edit();
            }

            if (name === "remove") {
                return CurrencyBaseInfoMVC.Controller.remove();
            }
        },
        add: function () {
            $('#currencyInfo-base-form').form('clear');
            $('#currencyInfo-detail-form').form('clear');

            var title = '新增币种';

            $('#currencyInfo-base-dlg').dialog({iconCls: 'icon-add'})
                .dialog('open')
                .dialog('center')
                .dialog('setTitle', title);

            $('#currencyInfo-tabs').tabs('select', 0);

            $('#status').combobox('select', "1");
        },
        edit: function () {
            var row = $('#currencyInfo-base-datagrid').datagrid('getSelected');
            if (row) {
                var title = "修改币种 - " + row.code + "(" + row.name + ")";
                $('#currencyInfo-base-dlg').dialog({iconCls: 'icon-edit1'})
                    .dialog('open')
                    .dialog('center')
                    .dialog('setTitle', title);

                $('#currencyInfo-tabs').tabs('select', 0);

                var code = row.code;
                var locale = 'zh-cn';

                CurrencyBaseInfoMVC.Controller.fillBaseInfo(code);
                CurrencyBaseInfoMVC.Controller.fillDetailInfo(code, locale);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#currencyInfo-base-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    data: {code: row.code},
                    url: CurrencyBaseInfoMVC.URLs.remove.url,
                    gridId: '#currencyInfo-base-datagrid',
                    gridUrl: CurrencyBaseInfoMVC.URLs.list.url
                };

                EasyUIUtils.remove(options);
            }
        },
        fillBaseInfo: function (code) {
            $('#currencyInfo-base-form').form('clear');

            var url = CurrencyBaseInfoMVC.URLs.getBaseInfoByCode.url + "?code=" + code;

            $.ajax({
                url: url,
                type: 'GET',
                data: '',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (data) {
                    if (!data.code) {
                        if (data.data && data.data.length > 0) {
                            $('#currencyInfo-detail-code').textbox('setValue', code);
                            $('#currencyInfo-detail-locale').textbox('setValue', 'zh-cn');

                            $('#currencyInfo-base-form').form('load', data.data[0]);
                            $('#imgUrl-file').filebox('setText', data.data[0].imgUrl);
                        }

                    }

                }
            });
        },
        fillDetailInfo: function (code, locale) {
            $('#currencyInfo-detail-form').form('clear');

            $("#currencyInfo-detail-code").textbox("setValue", code);
            $('#currencyInfo-detail-locale').textbox('setValue', locale);

            var url = CurrencyBaseInfoMVC.URLs.getDetailInfoByCode.url + "?code=" + code + "&locale=" + locale;

            $.ajax({
                url: url,
                type: 'GET',
                data: '',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (data) {
                    if (!data.code) {
                        if (data.data && data.data.length > 0) {
                            $("#currencyInfo-detail-id").val(data.data[0].id);
                            $("#currencyInfo-detail-concept").textbox("setValue", data.data[0].concept);
                            $("#currencyInfo-detail-whitePaper").textbox("setValue", data.data[0].whitePaper);
                            $("#currencyInfo-detail-telegram").textbox("setValue", data.data[0].telegram);
                            $("#currencyInfo-detail-facebook").textbox("setValue", data.data[0].facebook);
                            $("#currencyInfo-detail-twitter").textbox("setValue", data.data[0].twitter);
                            $("#currencyInfo-detail-weibo").textbox("setValue", data.data[0].weibo);
                            $("#currencyInfo-detail-introduction").textbox("setValue", data.data[0].introduction);
                            $("#currencyInfo-detail-coinMarketCapUrl").textbox("setValue", data.data[0].coinMarketCapUrl);

                        }
                    }
                }
            });
        },
        saveBaseInfo: function () {
            $("#currencyInfo-base-form").form('submit', {
                url: CurrencyBaseInfoMVC.URLs.edit.url,
                onSubmit: function () {
                    return $(this).form('validate');
                },
                success: function (data) {
                    var result = $.toJSON(data);
                    if (result.code) {
                        $.messager.show({
                            title: '错误',
                            msg: result.msg
                        });
                    } else {
                        var code = $('#code').textbox('getValue');
                        CurrencyBaseInfoMVC.Controller.fillBaseInfo(code);

                        EasyUIUtils.showMsg(result.msg || "操作成功");
                    }
                },
                error: function (data) {
                    if (data.responseJSON.code == "403") {
                        $.messager.show({
                            title: '错误',
                            msg: data.responseJSON.msg
                        });
                    } else {
                        $.messager.alert('提示', 'error', 'info');
                    }
                }
            });
        },
        saveDetailInfo: function () {
            var code = $('#currencyInfo-detail-code').textbox('getValue');
            var locale = $('#currencyInfo-detail-locale').combobox('getValue');

            $("#currencyInfo-detail-form").form('submit', {
                url: CurrencyBaseInfoMVC.URLs.editDetailInfo.url,
                onSubmit: function () {
                    return $(this).form('validate');
                },
                success: function (data) {
                    var result = $.toJSON(data);
                    if (result.code) {
                        $.messager.show({
                            title: '错误',
                            msg: result.msg
                        });
                    } else {
                        CurrencyBaseInfoMVC.Controller.fillDetailInfo(code, locale);

                        EasyUIUtils.showMsg(result.msg || "操作成功");
                    }
                },
                error: function (data) {
                    if (data.responseJSON.code == "403") {
                        $.messager.show({
                            title: '错误',
                            msg: data.responseJSON.msg
                        });
                    } else {
                        $.messager.alert('提示', 'error', 'info');
                    }
                }
            });
        },
        find: function () {
            var params = CurrencyBaseInfoMVC.Util.getQueryParams();

            $("#currencyInfo-base-datagrid").datagrid("load", params);
        },
        //
        tag: function () {
            var row = $('#currencyInfo-base-datagrid').datagrid('getSelected');

            if (row) {
                var title = row.code;
                $('#currencyInfo-tag-dlg').dialog({iconCls: 'icon-edit1'})
                    .dialog('open')
                    .dialog('center')
                    .dialog('setTitle', title);

                $('#currencyInfo-tag-tabs').tabs('select', 0);
                var code = row.code;
                $("#currency-code").val(code);
                $("#currency-id").val(row.id);
                //获取当前币种的标签信息
                var url = CurrencyBaseInfoMVC.URLs.getAllTags.url + "?code=" + code;

                CurrencyBaseInfoMVC.Controller.showTreeTag(url);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        //编辑标签
        modifyTag: function (data) {
            var currencyCode = $("#currency-code").val();
            var currencyId = $("#currency-id").val();

            var jsonData = {
                code: currencyCode,
                id: currencyId,
                tags: data
            };

            $.ajax({
                url: CurrencyBaseInfoMVC.URLs.editTag.url,
                type: 'POST',
                data: JSON.stringify(jsonData),
                async: false,
                cache: false,
                contentType: 'application/json',
                success: function (result) {
                    if (!result.code) {
                        $('#currencyInfo-tag-dlg').dialog('close');
                    }
                }
            });
        },
        //展示标签树
        showTreeTag: function (url) {
            $.ajax({
                url: url,
                type: 'GET',
                data: '',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (data) {
                    if (!data.code) {
                        if (data.data && data.data.length > 0) {

                            $('#tag-tree').tree({
                                checkbox: true,
                                animate: true,
                                data: data.data
                            });
                        }

                    }

                }
            });
        }
    },
    Util: {
        getQueryParams: function () {
            var code = $("#currencyInfo-code").textbox('getValue');
            var name = $("#currencyInfo-name").textbox('getValue');

            var params = {
                code: code,
                name: name
            };

            return params;
        },
        uploadImg: function () {
            var files = $("#imgUrl-file").filebox('files');

            var formData = new FormData();
            formData.append("file", files[0]);

            //调用apicontroller后台action方法，将form数据传递给后台处理。contentType必须设置为false,否则chrome和firefox不兼容
            $.ajax({
                url: CurrencyBaseInfoMVC.URLs.img.url,
                type: 'POST',
                data: formData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (data) {
                    if (data.code == 0) {
                        $('#imgUrl').val(data.data.fileName);
                    } else {
                        $.messager.alert('提示', data.responseJSON.msg, 'info');
                    }
                },
                error: function (data) {
                    if (data.responseJSON.code == "403") {
                        $.messager.alert('提示', data.responseJSON.msg, 'info');

                    } else {
                        $.messager.alert('提示', 'error', 'info');
                    }
                }
            });
        }
    }
};