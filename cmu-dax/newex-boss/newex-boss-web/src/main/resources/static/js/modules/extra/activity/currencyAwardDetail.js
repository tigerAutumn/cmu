$(function () {
    CurrencyAwardDetail.init();
})

var CurrencyAwardDetail = {
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
            url: CurrencyAwardCommon.baseUrl + 'record/add',
            method: 'POST'
        },
        list: {
            url: CurrencyAwardCommon.baseUrl + 'getByPager',
            method: 'GET'
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
                $("#code").combobox({
                    data: _datax.data,
                    valueField: "id",
                    textField: "code",
                    required: true,
                });
            });

            $('#activity-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
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
                    field: 'actId',
                    title: '活动编号',
                    width: 100,
                    sortable: true

                }, {
                    field: 'code',
                    title: '活动名称',
                    width: 150,
                    sortable: true,
                }, {
                    field: 'amount',
                    title: '发放人数',
                    width: 150,
                    sortable: true,
                }, {
                    field: 'options',
                    title: '操作',
                    width: 100,
                    formatter: function (value, row, index) {
                        var icons = [{
                            "name": "edit",
                            "title": "编辑"
                        }, {
                            "name": "details_open",
                            "title": "查看详情"
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
            // $('#btn-search').bind('click', CurrencyAwardMVC.Controller.find);
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
            if (name === "details_open") {
                return CurrencyAwardMVC.Controller.details_open();
            }
            if (name === "back") {
                return CurrencyAwardMVC.Controller.back();
            }
        },
        add: function () {
            var options = CurrencyAwardMVC.Util.getOptions();
            options.title = '新增发奖';
            EasyUIUtils.openAddDlg(options);

            $('#code').combobox('setValue', '选择活动');

        },
        edit: function () {
            var row = $('#activity-datagrid').datagrid('getSelected');
            if (row) {
                var options = CurrencyAwardMVC.Util.getOptions();

                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改banner[' + options.data.title + ']';
                EasyUIUtils.openEditDlg(options);

            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        details_open: function () {

            // $("#activity-dtl").combogrid({
            //     panelWidth: 450,
            //     value: '006',
            //     idField: 'code',
            //     textField: 'name',
            //     method: 'get',
            //     url: CurrencyAwardMVC.URLs.list.url,
            //     columns: [[
            //         {field: 'actId', title: 'Code', width: 60},
            //         {field: 'code', title: 'Name', width: 100},
            //         {field: 'amount', title: 'Address', width: 120}
            //     ]]
            // })

            $('#detail-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
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
                    field: 'actId',
                    title: '活动编号',
                    width: 100,
                    sortable: true

                }, {
                    field: 'code',
                    title: '活动名称',
                    width: 150,
                    sortable: true,
                }, {
                    field: 'amount',
                    title: '发放人数',
                    width: 150,
                    sortable: true,
                }, {
                    field: 'options',
                    title: '操作',
                    width: 100,
                    formatter: function (value, row, index) {
                        var icons = [{
                            "name": "back",
                            "title": "返回"
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
        },
        back: function () {
            $('#activity-dtl').datagrid().hide();
        },
        save: function () {

            var options = CurrencyAwardMVC.Util.getOptions();

            var action = $('#modal-action').val();


            if (action === "edit") {
                var row = $('#user-datagrid').datagrid('getSelected');
                //取得ID传给后台
                options.url = CurrencyAwardMVC.URLs.edit.url + '?id=' + row.id;
            }
            else {
                //添加操作
                var textarea = $("#area").val();
                if (!textarea.match(/[，]/g)) {
                    options.url = CurrencyAwardMVC.URLs.add.url;
                } else {
                    ß
                    $.messager.alert('警告', '请使用英文标点符号');
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