$(function () {
    BreakerRecord.init();
});

var BreakerRecord = {
    init: function () {
        BreakerRecordMVC.View.initControl();
        BreakerRecordMVC.View.bindEvent();
        BreakerRecordMVC.View.bindValidate();
    }
};

var BreakerRecordCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/spot/breaker/',
    commonUrl: BossGlobal.ctxPath + '/v1/boss/common/',
    imageUrl: BossGlobal.ctxPath + '/v1/boss/upload/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/'
};

var BreakerRecordMVC = {
    URLs: {
        add: {
            url: BreakerRecordCommon.baseUrl + 'addRecord',
            method: 'POST'
        },
        edit: {
            url: BreakerRecordCommon.baseUrl + 'editRecord',
            method: 'POST'
        },
        remove: {
            url: BreakerRecordCommon.baseUrl + 'removeRecord',
            method: 'POST'
        },
        list: {
            url: BreakerRecordCommon.baseUrl + 'listRecord',
            method: 'GET'
        },
        productList: {
            url: BreakerRecordCommon.commonUrl + 'product',
            method: 'GET'
        },
        strategyList: {
            url: BreakerRecordCommon.baseUrl + 'listAllStrategy',
            method: 'GET'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {

            $.get(BreakerRecordMVC.URLs.productList.url, "", function (_data) {
                var allCurrency = _data || [];

                var currencyWithId = [];
                var allCurrencyWithId = [];

                allCurrencyWithId.push({
                    "value": "",
                    "text": "全部"
                });

                for (var i = 0; i < allCurrency.length; i++) {
                    var item = allCurrency[i];

                    currencyWithId.push({
                        "value": item.id,
                        "text": item.code
                    });

                    allCurrencyWithId.push({
                        "value": item.id,
                        "text": item.code
                    });
                }

                $('#breakerRecord-productId').combobox({
                    valueField: 'value',
                    textField: 'text',
                    data: allCurrencyWithId
                });

                $('#productId').combobox({
                    required: true,
                    valueField: 'value',
                    textField: 'text',
                    data: currencyWithId
                });
            });

            $('#breakerRecord-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: BreakerRecordMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        BreakerRecordMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#breakerRecord-datagrid').datagrid('load', {});
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
                    field: 'productId',
                    title: '币对ID',
                    width: 100,
                    sortable: true
                }, {
                    field: 'baseName',
                    title: '币对名称',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return row.baseName.toUpperCase() + '_' + row.quoteName.toUpperCase();
                    }
                }, {
                    field: 'strategyId',
                    title: '策略ID',
                    width: 100,
                    sortable: true
                }, {
                    field: 'strategyDTO',
                    title: '策略详情',
                    width: 500,
                    sortable: false,
                    formatter: function (value, row, index) {
                        var breakerDirectionEnum = value.breakerDirectionEnum;
                        var monitorTime = value.monitorTime / 1000;
                        var triggerRatio = value.triggerRatio;
                        var pauseTime = value.pauseTime / 1000;
                        var intervalTime = value.intervalTime / 1000;

                        var rstr = '';
                        if (breakerDirectionEnum == 'LONG') {
                            rstr = '拉盘上涨；监控：' + monitorTime + '秒；最新成交价/最低价 > ' + triggerRatio + '%；熔断：' + pauseTime + '秒；再触发：' + intervalTime + '秒；';
                        } else if (breakerDirectionEnum == 'SHORT') {
                            rstr = '砸盘下跌；监控：' + monitorTime + '秒；最高价/最新成交价 > ' + triggerRatio + '%；熔断：' + pauseTime + '秒；再触发：' + intervalTime + '秒；';
                        }


                        return rstr;
                    }
                }, {
                    field: 'startDate',
                    title: '下次开始时间',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value) {
                            return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss");
                        }

                        return "";
                    }
                }, {
                    field: 'breakerDate',
                    title: '熔断时间',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value) {
                            return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss");
                        }

                        return "";
                    }
                }, {
                    field: 'breakerStatusEnum',
                    title: '状态',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value == 'BREAKER') {
                            return '熔断';
                        }

                        if (value == 'RECOVERY') {
                            return '正常';
                        }

                        return '';
                    }
                }, {
                    field: 'createDate',
                    title: '创建时间',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                    }
                }, {
                    field: 'modifyDate',
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
                            "name": "remove",
                            "title": "删除"
                        }];

                        var buttons = [];
                        for (var i = 0; i < icons.length; i++) {
                            var tmpl = '<a href="#" title ="${title}" '
                                + 'onclick="BreakerRecordMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: BreakerRecordCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // dialogs
            $('#breakerRecord-dlg').dialog({
                closed: true,
                modal: false,
                width: 800,
                height: 400,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#breakerRecord-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: BreakerRecordMVC.Controller.save
                }]
            });

        },
        bindEvent: function () {
            $('#btn-search').bind('click', BreakerRecordMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#breakerRecord-datagrid').datagrid('selectRow', index);

            if (name === "remove") {
                return BreakerRecordMVC.Controller.remove();
            }
        },
        add: function () {
            $.get(BreakerRecordMVC.URLs.strategyList.url, "", function (_data) {
                var allStrategy = _data.data || [];

                var strategyWithId = [];

                for (var i = 0; i < allStrategy.length; i++) {
                    var item = allStrategy[i];

                    strategyWithId.push({
                        "value": item.id,
                        "text": item.remark
                    });

                }

                $('#strategyId').combobox({
                    required: true,
                    valueField: 'value',
                    textField: 'text',
                    data: strategyWithId
                });
            });

            var options = BreakerRecordMVC.Util.getOptions();
            options.title = '新增熔断任务';
            EasyUIUtils.openAddDlg(options);

            $('#breakerPreset').combobox('select', '0');
            $('#breakerDirection').combobox('select', '0');
        },
        remove: function () {
            var row = $('#breakerRecord-datagrid').datagrid('getSelected');
            if (row) {
                var options = BreakerRecordMVC.Util.getOptions();

                options.url = BreakerRecordMVC.URLs.remove.url;
                options.rows = [row];
                options.data = {id: row.id};

                EasyUIUtils.remove(options);
            }
        },
        find: function () {
            var params = BreakerRecordMVC.Util.getQueryParams();

            $('#breakerRecord-datagrid').datagrid('load', params);
        },
        save: function () {
            var action = $('#modal-action').val();

            var options = BreakerRecordMVC.Util.getOptions();

            if (action === "edit") {
                var row = $('#breakerRecord-datagrid').datagrid('getSelected');
                //取得ID传给后台
                options.url = BreakerRecordMVC.URLs.edit.url + '?id=' + row.id;
            }
            else {
                //添加操作
                options.url = BreakerRecordMVC.URLs.add.url;
            }

            //修改后刷新列表
            EasyUIUtils.save(options);

            return;
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#breakerRecord-dlg',
                formId: '#breakerRecord-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                url: null,
                gridId: '#breakerRecord-datagrid',
                gridUrl: BreakerRecordMVC.URLs.list.url
            };
        },
        getQueryParams: function () {
            var id = $('#breakerRecord-id').textbox('getValue');
            var strategyId = $('#breakerRecord-strategyId').textbox('getValue');
            var productId = $('#breakerRecord-productId').combobox('getValue');

            var params = {
                id: id,
                strategyId: strategyId,
                productId: productId
            };

            return params;
        }
    }
};