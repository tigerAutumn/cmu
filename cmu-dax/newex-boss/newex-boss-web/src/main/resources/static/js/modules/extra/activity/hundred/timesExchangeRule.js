$(function () {
    TimesExchangeRule.init();
});

var TimesExchangeRule = {
    init: function () {
        TimesExchangeRuleMVC.View.initControl();
        TimesExchangeRuleMVC.View.bindEvent();
        TimesExchangeRuleMVC.View.bindValidate();
    }
};

var TimesExchangeRuleCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/activity/wheel/times/exchange-rule/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    basecomboxUrl: BossGlobal.ctxPath + '/v1/boss/common/'
};

var TimesExchangeRuleMVC = {
    URLs: {
        add: {
            url: TimesExchangeRuleCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: TimesExchangeRuleCommon.baseUrl + 'edit',
            method: 'POST'
        },
        remove: {
            url: TimesExchangeRuleCommon.baseUrl + 'remove',
            method: 'POST'
        },
        list: {
            url: TimesExchangeRuleCommon.baseUrl + 'list',
            method: 'GET'
        },
        currency: {
            url: TimesExchangeRuleCommon.basecomboxUrl + 'spot/currency',
            method: 'GET'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {

            $("#currencyId").combobox({
                url: TimesExchangeRuleMVC.URLs.currency.url,
                method: 'get',
                valueField: "id",
                textField: "symbol",
                panelHeight: 300,
                editable: true
            });

            $('#timesExchangeRule-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: TimesExchangeRuleMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        TimesExchangeRuleMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        TimesExchangeRuleMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#timesExchangeRule-datagrid').datagrid('load', {});
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
                    field: 'totalTimes',
                    title: '抽奖次数',
                    width: 100,
                    sortable: true
                }, {
                    field: 'currencyId',
                    title: '币种ID',
                    width: 100,
                    sortable: true
                }, {
                    field: 'amount',
                    title: '币种数量',
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
                                + 'onclick="TimesExchangeRuleMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: TimesExchangeRuleCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return TimesExchangeRuleMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // dialogs
            $('#timesExchangeRule-dlg').dialog({
                closed: true,
                modal: false,
                width: 760,
                height: 500,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#timesExchangeRule-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: TimesExchangeRuleMVC.Controller.save
                }]
            });


        },
        bindEvent: function () {
            $('#btn-search').bind('click', TimesExchangeRuleMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#timesExchangeRule-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return TimesExchangeRuleMVC.Controller.edit();
            }

            if (name === "remove") {
                return TimesExchangeRuleMVC.Controller.remove();
            }
        },
        add: function () {
            var options = TimesExchangeRuleMVC.Util.getOptions();
            options.title = '新增 抽奖机会兑换规则';
            EasyUIUtils.openAddDlg(options);
        },
        edit: function () {
            var row = $('#timesExchangeRule-datagrid').datagrid('getSelected');
            if (row) {
                var options = TimesExchangeRuleMVC.Util.getOptions();

                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改 抽奖机会兑换规则';

                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#timesExchangeRule-datagrid').datagrid('getSelected');
            if (row) {
                var options = TimesExchangeRuleMVC.Util.getOptions();

                options.url = TimesExchangeRuleMVC.URLs.remove.url;
                options.rows = [row];
                options.data = {id: row.id, code: row.code};

                EasyUIUtils.remove(options);
            }
        },
        find: function () {
            var params = TimesExchangeRuleMVC.Util.getQueryParams();
            $('#timesExchangeRule-datagrid').datagrid('load', params);
        },
        save: function () {
            var action = $('#modal-action').val();

            var options = TimesExchangeRuleMVC.Util.getOptions();

            if (action === "edit") {
                var row = $('#timesExchangeRule-datagrid').datagrid('getSelected');
                //取得ID传给后台
                options.url = TimesExchangeRuleMVC.URLs.edit.url + '?id=' + row.id;
            }
            else {
                //添加操作
                options.url = TimesExchangeRuleMVC.URLs.add.url;
            }

            //修改后刷新列表
            EasyUIUtils.save(options);

            return;
        }

    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#timesExchangeRule-dlg',
                formId: '#timesExchangeRule-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                url: null,
                gridId: '#timesExchangeRule-datagrid',
                gridUrl: TimesExchangeRuleMVC.URLs.list.url
            };
        },
        getQueryParams: function () {
            var params = {};

            return params;
        }
    }
};