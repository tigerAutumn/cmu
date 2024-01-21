$(function () {
    CurrencyAward.init();
})

var CurrencyAward = {
    init: function () {
        CurrencyAwardMVC.View.initControl();
        CurrencyAwardMVC.View.bindEvent();
        CurrencyAwardMVC.View.bindValidate();
    }
};

var CurrencyAwardCommon = {

    baseUrl: BossGlobal.ctxPath + '/v1/boss/extra/activity/currencyAward/',
    imageUrl: BossGlobal.ctxPath + '/v1/boss/upload/',
    commonUrl: BossGlobal.ctxPath + '/v1/boss/common/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/'
};

var CurrencyAwardMVC = {
    URLs: {
        add: {
            url: CurrencyAwardCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: CurrencyAwardCommon.baseUrl + 'update',
            method: 'POST'
        },
        list: {
            url: CurrencyAwardCommon.baseUrl + 'getByPager',
            method: 'GET'
        },
        record: {
            url: CurrencyAwardCommon.baseUrl + 'record/getByPager',
            method: 'GET'
        },
        delete: {
            url: CurrencyAwardCommon.baseUrl + 'delete'
        },
        all: {
            url: CurrencyAwardCommon.baseUrl + 'all',
            method: 'GET'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {

            $.get(CurrencyAwardMVC.URLs.all.url, "", function (_datax) {

                var _datax2 = [{'value': '', 'text': '全部'}];
                for (var i = 0; i < _datax.data.length; i++) {
                    _datax2.push({
                        'value': _datax.data[i].code,
                        'text': _datax.data[i].code
                    });
                }

                $("#activity-code").combobox({
                    data: _datax2,
                    valueField: 'value',
                    textField: 'text',
                    required: true,
                    onLoadSuccess: function () {
                        $("#activity-code").combobox('setValue', '');//设置默认值
                    }
                });
            });

            $('#activity-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: CurrencyAwardMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        CurrencyAwardMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        CurrencyAwardMVC.Controller.edit();
                    }
                }, '-', {
                    text: '全部数据',
                    iconCls: 'icon-reload',
                    handler: function () {
                        $("#activity-datagrid").datagrid("load", {});
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
                    title: '活动编号',
                    width: 50,
                    sortable: true
                }, {
                    field: 'localeContent',
                    title: '语言',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return value[0].locale;
                    }
                }, {
                    field: 'code',
                    title: '活动名称',
                    width: 150,
                    sortable: true
                }, {
                    field: 'mailCode',
                    title: '邮件名称',
                    width: 300,
                    sortable: true
                }, {
                    field: 'smsCode',
                    title: '信息名称',
                    width: 300,
                    sortable: true
                }, {
                    field: 'number',
                    title: '发放人数',
                    width: 100,
                    sortable: true
                }, {
                    field: 'adminUser',
                    title: '发放奖励管理员',
                    width: 100,
                    sortable: true,
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
                                + 'onclick="CurrencyAwardMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: CurrencyAwardCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return CurrencyAwardMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                },
                onLoadSuccess: function () {
                    $('.pagination-page-list').hide();
                }
            });

            $('#activity-dlg').dialog({
                closed: true,
                modal: false,
                width: 800,
                height: 400,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#activity-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: CurrencyAwardMVC.Controller.save
                }]
            });


        },

        bindEvent: function () {
            $('#btn-search').bind('click', CurrencyAwardMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#activity-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return CurrencyAwardMVC.Controller.edit();
            }
        },
        add: function () {
            var options = CurrencyAwardMVC.Util.getOptions();
            options.title = '新增活动';
            EasyUIUtils.openAddDlg(options);
        },
        edit: function () {
            var row = $('#activity-datagrid').datagrid('getSelected');
            if (row) {
                var options = CurrencyAwardMVC.Util.getOptions();

                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改活动[' + options.data.code + ']';
                EasyUIUtils.openEditDlg(options);
                var localeContent = options.data.localeContent;
                if (localeContent[0].locale == 'zh-cn') {
                    $('#cn-title').textbox('setValue', localeContent[0].title);
                }
                if (localeContent[1].locale == 'en-us') {
                    $('#en-title').textbox('setValue', localeContent[1].title);
                }
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        find: function () {
            var params = CurrencyAwardMVC.Util.getQueryParams();
            $('#activity-datagrid').datagrid('load', params);
        },
        save: function () {
            var options = CurrencyAwardMVC.Util.getOptions();

            var action = $('#modal-action').val();


            if (action === "edit") {
                var row = $('#activity-datagrid').datagrid('getSelected');
                //取得ID传给后台
                options.url = CurrencyAwardMVC.URLs.edit.url + '?id=' + row.id;
            }
            else {
                //添加操作
                options.url = CurrencyAwardMVC.URLs.add.url;
            }

            //修改后刷新列表
            EasyUIUtils.save(options);

            return;
        }
    },
    Util: {
        getSearchUrl: function () {
            var actId = $("#activity-id").textbox('getValue');
            var code = $("#activity-code").textbox('getValue');
            return CurrencyAwardMVC.URLs.list.url + '?actId=' + actId + '&code=' + code;

        },
        getQueryParams: function () {
            var code = $("#activity-code").combobox('getValue');
            var params = {
                code: code,
            };

            return params;
        },
        getOptions: function () {
            return {
                dlgId: '#activity-dlg',
                formId: '#activity-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                url: null,
                gridId: '#activity-datagrid',
                gridUrl: CurrencyAwardMVC.URLs.list.url
            };
        }
    }

};