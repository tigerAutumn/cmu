$(function () {
    BreakerCurrency.init();
});

var BreakerCurrency = {
    init: function () {
        BreakerCurrencyMVC.View.initControl();
        BreakerCurrencyMVC.View.bindEvent();
        BreakerCurrencyMVC.View.bindValidate();
    }
};

var BreakerCurrencyCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/spot/breaker/',
    commonUrl: BossGlobal.ctxPath + '/v1/boss/common/',
    imageUrl: BossGlobal.ctxPath + '/v1/boss/upload/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/'
};

var BreakerCurrencyMVC = {
    URLs: {
        list: {
            url: BreakerCurrencyCommon.baseUrl + 'listCurrency',
            method: 'GET'
        },
        recordList: {
            url: BreakerCurrencyCommon.baseUrl + 'listRecord',
            method: 'GET'
        },
        historyList: {
            url: BreakerCurrencyCommon.baseUrl + 'listHistory',
            method: 'GET'
        },
        productList: {
            url: BreakerCurrencyCommon.commonUrl + 'product',
            method: 'GET'
        },
        strategyList: {
            url: BreakerCurrencyCommon.baseUrl + 'listAllStrategy',
            method: 'GET'
        },
        breaker: {
            url: BreakerCurrencyCommon.baseUrl + 'breaker',
            method: 'POST'
        },
        recovery: {
            url: BreakerCurrencyCommon.baseUrl + 'recovery',
            method: 'POST'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {

            $.get(BreakerCurrencyMVC.URLs.productList.url, "", function (_data) {
                var allCurrency = _data || [];

                var allCurrencyWithId = [];

                allCurrencyWithId.push({
                    "value": "",
                    "text": "全部"
                });

                for (var i = 0; i < allCurrency.length; i++) {
                    var item = allCurrency[i];

                    allCurrencyWithId.push({
                        "value": item.id,
                        "text": item.code
                    });
                }

                $('#breakerCurrency-productId').combobox({
                    valueField: 'value',
                    textField: 'text',
                    data: allCurrencyWithId
                });

            });

            $('#breakerCurrency-status').combobox({
                valueField: 'id',
                textField: 'value',
                data: [
                    {
                        id: '',
                        value: '全部'
                    }, {
                        id: '0',
                        value: '熔断'
                    }, {
                        id: '1',
                        value: '正常'
                    }
                ]
            });

            $('#breakerCurrency-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: BreakerCurrencyMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-info',
                    handler: function () {
                        BreakerCurrencyMVC.Controller.detail();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#breakerCurrency-datagrid').datagrid('load', {});
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
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return row.baseName.toUpperCase() + '_' + row.quoteName.toUpperCase();
                    }
                }, {
                    field: 'strategyCnt',
                    title: '策略数量',
                    width: 100,
                    sortable: true
                }, {
                    field: 'breakerCnt',
                    title: '熔断次数',
                    width: 100,
                    sortable: true
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
                    field: 'recoveryDate',
                    title: '恢复时间',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value) {
                            return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss");
                        }

                        return "";
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    BreakerCurrencyMVC.Controller.detail();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            $('#breaker-record-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: false,
                rownumbers: true,
                pageSize: 50,
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
                    field: 'options',
                    title: '操作',
                    width: 100,
                    formatter: function (value, row, index) {
                        var icons = [];

                        if (row.breakerStatusEnum == 'BREAKER') {
                            icons.push({
                                "name": "recovery",
                                "title": "恢复"
                            });
                        } else if (row.breakerStatusEnum == 'RECOVERY') {
                            icons.push({
                                "name": "breaker",
                                "title": "熔断"
                            });
                        }

                        var buttons = [];
                        for (var i = 0; i < icons.length; i++) {
                            var tmpl = '<a href="#" title ="${title}" '
                                + 'onclick="BreakerCurrencyMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '${title}</a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index
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

            $('#breaker-history-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: false,
                rownumbers: true,
                pageSize: 50,
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
                    field: 'recordId',
                    title: '熔断任务ID',
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
                    field: 'breakerTypeEnum',
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
                    field: 'recoveryDate',
                    title: '恢复时间',
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
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // dialogs
            $('#breakerCurrency-dlg').dialog({
                closed: true,
                modal: false,
                width: 900,
                height: 500,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#breakerCurrency-dlg").dialog('close');
                        BreakerCurrencyMVC.Controller.find();
                    }
                }],
                onClose: function () {
                    BreakerCurrencyMVC.Controller.find();
                }
            });

            $('#breakerCurrency-breaker-dlg').dialog({
                closed: true,
                modal: false,
                width: 400,
                height: 200,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#breakerCurrency-breaker-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: BreakerCurrencyMVC.Controller.saveBreaker
                }]
            });

            $('#breakerCurrency-recovery-dlg').dialog({
                closed: true,
                modal: false,
                width: 400,
                height: 200,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#breakerCurrency-recovery-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: BreakerCurrencyMVC.Controller.saveRecovery
                }]
            });

            $('#breakerCurrency-tabs').tabs({
                border: false,
                onSelect: function (title) {
                    var tab = $('#breakerCurrency-tabs').tabs('getSelected');
                    var index = $('#breakerCurrency-tabs').tabs('getTabIndex', tab);

                    if (index === 0) {
                        BreakerCurrencyMVC.Controller.showRecord();
                    }

                    if (index === 1) {
                        BreakerCurrencyMVC.Controller.showHistory();
                    }
                }
            });

        },
        bindEvent: function () {
            $('#btn-search').bind('click', BreakerCurrencyMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#breaker-record-datagrid').datagrid('selectRow', index);

            if (name === "breaker") {
                return BreakerCurrencyMVC.Controller.breaker();
            }

            if (name === "recovery") {
                return BreakerCurrencyMVC.Controller.recovery();
            }
        },
        breaker: function () {
            var row = $('#breaker-record-datagrid').datagrid('getSelected');

            if (row) {
                $('#breaker-Id').val(row.id);
                $('#breakerCurrency-breaker-dlg').dialog({iconCls: 'icon-edit1'})
                    .dialog('open')
                    .dialog('center')
                    .dialog('setTitle', '熔断');
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        saveBreaker: function () {
            var options = BreakerCurrencyMVC.Util.breakerOptions();

            EasyUIUtils.save(options);

            return;
        },
        recovery: function () {
            var row = $('#breaker-record-datagrid').datagrid('getSelected');

            if (row) {
                $('#recovery-Id').val(row.id);
                $('#breakerCurrency-recovery-dlg').dialog({iconCls: 'icon-edit1'})
                    .dialog('open')
                    .dialog('center')
                    .dialog('setTitle', '恢复');
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        saveRecovery: function () {
            var options = BreakerCurrencyMVC.Util.recoveryOptions();

            EasyUIUtils.save(options);

            return;
        },
        detail: function () {
            var row = $('#breakerCurrency-datagrid').datagrid('getSelected');
            if (row) {
                $('#breakerCurrency-dlg').dialog({iconCls: 'icon-info'})
                    .dialog('open')
                    .dialog('center')
                    .dialog('setTitle', '监控熔断详情 - ' + row.baseName + '/' + row.quoteName);

                BreakerCurrencyMVC.Controller.showRecord();
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        showRecord: function () {
            var row = $('#breakerCurrency-datagrid').datagrid('getSelected');

            $('#breaker-record-datagrid').datagrid({
                url: BreakerCurrencyMVC.URLs.recordList.url,
                queryParams: {
                    productId: row.productId
                }
            });
        },
        showHistory: function () {
            var row = $('#breakerCurrency-datagrid').datagrid('getSelected');

            $('#breaker-history-datagrid').datagrid({
                url: BreakerCurrencyMVC.URLs.historyList.url,
                queryParams: {
                    productId: row.productId
                }
            });
        },
        find: function () {
            var params = BreakerCurrencyMVC.Util.getQueryParams();

            $('#breakerCurrency-datagrid').datagrid('load', params);
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#breakerCurrency-dlg',
                formId: '#breakerCurrency-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                url: null,
                gridId: '#breakerCurrency-datagrid',
                gridUrl: BreakerCurrencyMVC.URLs.list.url
            };
        },
        breakerOptions: function () {
            return {
                dlgId: '#breakerCurrency-breaker-dlg',
                formId: '#breaker-form',
                actId: '',
                rowId: '#rowId',
                title: '熔断',
                iconCls: 'icon-edit1',
                data: {},
                gridId: null,
                gridUrl: null,
                url: BreakerCurrencyMVC.URLs.breaker.url,
                callback: function () {
                    BreakerCurrencyMVC.Controller.showRecord();
                }
            };
        },
        recoveryOptions: function () {
            return {
                dlgId: '#breakerCurrency-recovery-dlg',
                formId: '#recovery-form',
                actId: '',
                rowId: '#rowId',
                title: '熔断',
                iconCls: 'icon-edit1',
                data: {},
                gridId: null,
                gridUrl: null,
                url: BreakerCurrencyMVC.URLs.recovery.url,
                callback: function () {
                    BreakerCurrencyMVC.Controller.showRecord();
                }
            };
        },
        getQueryParams: function () {
            var status = $('#breakerCurrency-status').combobox('getValue');
            var productId = $('#breakerCurrency-productId').combobox('getValue');

            var params = {
                status: status,
                productId: productId
            };

            return params;
        }
    }
};