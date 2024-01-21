$(function () {
    CurrencyTrendInfo.init();
});

var CurrencyTrendInfo = {
    init: function () {
        CurrencyTrendInfoMVC.View.initControl();
        CurrencyTrendInfoMVC.View.bindEvent();
        CurrencyTrendInfoMVC.View.bindValidate();
    }
};

var CurrencyTrendInfoCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/extra/currency/trend-info/',
    currencyUrl: BossGlobal.ctxPath + '/v1/boss/extra/currency/base/',
    imageUrl: BossGlobal.ctxPath + '/v1/boss/upload/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    commonUrl: BossGlobal.ctxPath + '/v1/boss/common/'
};

var CurrencyTrendInfoMVC = {
    URLs: {
        add: {
            url: CurrencyTrendInfoCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: CurrencyTrendInfoCommon.baseUrl + 'edit',
            method: 'POST'
        },
        remove: {
            url: CurrencyTrendInfoCommon.baseUrl + 'remove',
            method: 'POST'
        },
        list: {
            url: CurrencyTrendInfoCommon.baseUrl + 'list',
            method: 'GET'
        },
        allCurrency: {
            url: CurrencyTrendInfoCommon.currencyUrl + 'listAll',
            method: 'GET'
        },
        lang:{
            url: CurrencyTrendInfoCommon.commonUrl + 'lang',
            method:'GET'
        }
    },
    Model: {},
    View: {
        initControl: function () {

            $.get(CurrencyTrendInfoMVC.URLs.lang.url, "", function (_datax) {
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
                $("#currencyTrendInfo-locale").combobox({
                    data: _datax2,
                    valueField: 'value',
                    textField: 'text',
                    required: true,
                    onLoadSuccess: function () {
                        $("#currencyTrendInfo-locale").combobox('setValue','');//设置默认值
                    }
                });


            });


            $('#status').combobox({
                required: true,
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

            $('#currencyTrendInfo-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: CurrencyTrendInfoMVC.URLs.list.url,
                toolbar: [{
                    text: '新增',
                    iconCls: 'icon-add',
                    handler: function () {
                        CurrencyTrendInfoMVC.Controller.add();
                    }
                }, '-', {
                    text: '编辑',
                    iconCls: 'icon-edit',
                    handler: function () {
                        CurrencyTrendInfoMVC.Controller.edit();
                    }
                }, '-', {
                    text: '删除',
                    iconCls: 'icon-remove',
                    handler: function () {
                        CurrencyTrendInfoMVC.Controller.remove();
                    }
                }, '-', {
                    text: '所有数据',
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#currencyTrendInfo-datagrid').datagrid('load', {});
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
                    title: '币种简称',
                    width: 100,
                    sortable: true
                }, {
                    field: 'locale',
                    title: '语言',
                    width: 100,
                    sortable: true
                }, {
                    field: 'link',
                    title: '链接',
                    width: 200,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value) {
                            return '<a href="' + value + '" target="_blank">' + row.title + '</a>';
                        }

                        return row.title;
                    }
                }, {
                    field: 'publishDate',
                    title: '发布日期',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        var pds = moment(new Date(value)).format("YYYY-MM-DD");
                        row.publishDate = pds;

                        return pds;
                    }
                }, {
                    field: 'sort',
                    title: '排序',
                    width: 100,
                    sortable: true
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
                                + 'onclick="CurrencyTrendInfoMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: CurrencyTrendInfoCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return CurrencyTrendInfoMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // dialogs
            $('#currencyTrendInfo-dlg').dialog({
                closed: true,
                modal: false,
                width: 800,
                height: 500,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#currencyTrendInfo-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: CurrencyTrendInfoMVC.Controller.save
                }]
            });


        },
        bindEvent: function () {
            $('#btn-search').bind('click', CurrencyTrendInfoMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#currencyTrendInfo-datagrid').datagrid('selectRow', index);

            if (name === "edit") {
                return CurrencyTrendInfoMVC.Controller.edit();
            }

            if (name === "remove") {
                return CurrencyTrendInfoMVC.Controller.remove();
            }
        },
        add: function () {
            $.get(CurrencyTrendInfoMVC.URLs.allCurrency.url, "", function (_data) {
                var allCurrency = _data.data || [];

                var allCurrencyWithCode = [];

                for (var i = 0; i < allCurrency.length; i++) {
                    var item = allCurrency[i];

                    allCurrencyWithCode.push({
                        "value": item.code,
                        "text": item.code.toUpperCase()
                    });

                }

                $('#code').combobox({
                    required: true,
                    valueField: 'value',
                    textField: 'text',
                    data: allCurrencyWithCode,
                    onLoadSuccess: function () {
                        $('#code').combobox('select', '');
                    }
                });
            });

            var options = CurrencyTrendInfoMVC.Util.getOptions();
            options.title = '新增项目动态';
            EasyUIUtils.openAddDlg(options);

            $('#locale').combobox('select', 'zh-cn');
            $('#status').combobox('select', '2');
        },
        edit: function () {
            var row = $('#currencyTrendInfo-datagrid').datagrid('getSelected');
            if (row) {
                $.get(CurrencyTrendInfoMVC.URLs.allCurrency.url, "", function (_data) {
                    var allCurrency = _data.data || [];

                    var allCurrencyWithCode = [];

                    for (var i = 0; i < allCurrency.length; i++) {
                        var item = allCurrency[i];

                        allCurrencyWithCode.push({
                            "value": item.code,
                            "text": item.code.toUpperCase()
                        });

                    }

                    $('#code').combobox({
                        required: true,
                        valueField: 'value',
                        textField: 'text',
                        data: allCurrencyWithCode,
                        onLoadSuccess: function () {
                            $('#code').combobox('select', row.code);
                        }
                    });
                });

                var options = CurrencyTrendInfoMVC.Util.getOptions();

                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改项目动态[' + row.code + ']';

                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#currencyTrendInfo-datagrid').datagrid('getSelected');
            if (row) {
                var options = CurrencyTrendInfoMVC.Util.getOptions();

                options.url = CurrencyTrendInfoMVC.URLs.remove.url;
                options.rows = [row];
                options.data = {id: row.id};

                EasyUIUtils.remove(options);
            }
        },
        find: function () {
            var params = CurrencyTrendInfoMVC.Util.getQueryParams();

            $('#currencyTrendInfo-datagrid').datagrid('load', params);
        },
        save: function () {
            var action = $('#modal-action').val();

            var options = CurrencyTrendInfoMVC.Util.getOptions();

            if (action === "edit") {
                var row = $('#currencyTrendInfo-datagrid').datagrid('getSelected');
                //取得ID传给后台
                options.url = CurrencyTrendInfoMVC.URLs.edit.url + '?id=' + row.id;
            }
            else {
                //添加操作
                options.url = CurrencyTrendInfoMVC.URLs.add.url;
            }

            //修改后刷新列表
            EasyUIUtils.save(options);

            return;
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#currencyTrendInfo-dlg',
                formId: '#currencyTrendInfo-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                url: null,
                gridId: '#currencyTrendInfo-datagrid',
                gridUrl: CurrencyTrendInfoMVC.URLs.list.url
            };
        },
        getQueryParams: function () {
            var code = $('#currencyTrendInfo-code').textbox('getValue');
            var locale = $('#currencyTrendInfo-locale').combobox('getValue');

            var params = {
                code: code,
                locale: locale
            };

            return params;
        }
    }
};