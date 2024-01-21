$(function () {
    AssetTransfer.init();
});

var AssetTransfer = {
    init: function () {
        AssetTransferMVC.View.initControl();
        AssetTransferMVC.View.bindEvent();
        AssetTransferMVC.View.bindValidate();
    }
};

var AssetTransferCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/c2c/asset-transfer/',
    commonUrl: BossGlobal.ctxPath + '/v1/boss/common/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/'
};

var AssetTransferMVC = {
    URLs: {
        Search: {
            url: AssetTransferCommon.baseUrl + 'searchList',
            method: 'GET'
        },
        add: {
            url: AssetTransferCommon.baseUrl + 'add',
            method: 'POST'
        },
        c2cCurrency: {
            url: AssetTransferCommon.commonUrl + 'c2c/currency',
            method: 'GET'
        },
        optionType: {
            url: AssetTransferCommon.baseUrl + "type",
            method: 'GET'
        }
    },
    Model: {},
    View: {
        initControl: function () {

            $('#fromBiz').combobox({
                required: true,
                valueField: 'id',
                textField: 'value',
                data: [
                    {
                        id: 'spot',
                        value: '币币'
                    }, {
                        id: 'c2c',
                        value: '法币'
                    }
                ],
                onSelect: function (rec) {
                    if (rec.id == 'spot') {
                        $('#toBiz').combobox('select', 'c2c');
                    } else if (rec.id == 'c2c') {
                        $('#toBiz').combobox('select', 'spot');
                    }
                }
            });

            $('#toBiz').combobox({
                readonly: true,
                required: true,
                valueField: 'id',
                textField: 'value',
                data: [
                    {
                        id: 'spot',
                        value: '币币'
                    }, {
                        id: 'c2c',
                        value: '法币'
                    }
                ]
            });

            $.get(AssetTransferMVC.URLs.c2cCurrency.url, "", function (_data) {
                var allCurrency = _data || [];

                var allCurrencyWithId = [];
                var allCurrencyWithCode = [];

                allCurrencyWithId.push({
                    "value": "-1",
                    "text": "全部"
                });

                for (var i = 0; i < allCurrency.length; i++) {
                    var item = allCurrency[i];

                    allCurrencyWithId.push({
                        "value": item.id,
                        "text": item.symbol.toUpperCase()
                    });

                    allCurrencyWithCode.push({
                        "value": item.symbol,
                        "text": item.symbol.toUpperCase()
                    });

                }

                $('#assetTransfer-currency').combobox({
                    valueField: 'value',
                    textField: 'text',
                    data: allCurrencyWithId,
                    onLoadSuccess: function () { //加载完成后,设置选中第一项
                        $('#assetTransfer-currency').combobox('select', '-1');
                    }
                });

                $('#currency').combobox({
                    valueField: 'value',
                    textField: 'text',
                    data: allCurrencyWithCode
                });
            });

            $.get(AssetTransferMVC.URLs.optionType.url, "", function (_datax) {
                _datax.unshift({
                    'code': '-1',
                    'description': '全部'
                });//向json数组开头添加自定义数据
                $("#assetTransfer-legalTender").combobox({
                    data: _datax,
                    valueField: "code",
                    textField: "description",
                    onLoadSuccess: function () { //加载完成后,设置选中第一项
                        $('#assetTransfer-legalTender').combobox('select', '-1');
                    }
                });
            });

            $('#assetTransfer-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                sortOrder: 'desc',
                pageSize: 50,
                pageNumber: 1,
                url: AssetTransferMVC.URLs.Search.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        AssetTransferMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#assetTransfer-datagrid').datagrid('load', {});
                    }
                }],
                loadFilter: function (src) {
                    if (!src.code) {
                        return src.data;
                    }
                    return $.messager.alert('失败', src.msg, 'error');
                },
                columns: [[
                    {
                        field: 'userId',
                        title: '用户ID',
                        width: 100,
                        sortable: true
                    }, {
                        field: 'currencySymbol',
                        title: '币种',
                        width: 100,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return value.toUpperCase();
                        }
                    }, {
                        field: 'typeDescription',
                        title: '类型',
                        width: 100,
                        sortable: true
                    }, {
                        field: 'amountStr',
                        title: '交易数量',
                        width: 100,
                        sortable: true,
                        formatter: function (val, row, index) {
                            if (val > 0) {
                                return '<span style="color:red">' + val + '</span>'
                            } else {
                                return '<span style="color:green">' + val + '</span>'
                            }
                        }

                    }, {
                        field: 'createDate',
                        title: '时间',
                        width: 100,
                        sortable: true
                    }]],
                onDblClickRow: function (rowIndex, rowData) {
                },
                onLoadSuccess: function () {
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // dialogs
            $('#assetTransfer-dlg').dialog({
                closed: true,
                modal: false,
                width: 800,
                height: 400,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#assetTransfer-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: AssetTransferMVC.Controller.save
                }]
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', AssetTransferMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {
        }
    },
    Controller: {
        doOption: function (index, name) {

        },
        find: function () {
            var params = AssetTransferMVC.Util.getQueryParams();

            $('#assetTransfer-datagrid').datagrid('load', params);
        },
        add: function () {
            var options = AssetTransferMVC.Util.getOptions();
            options.title = '法币资金划转';
            EasyUIUtils.openAddDlg(options);

            var allData = $('#currency').combobox('getData');
            if (allData) {
                $('#currency').combobox('select', allData[0]['value']);
            }

        },
        save: function () {
            var options = AssetTransferMVC.Util.getOptions();

            //修改后刷新列表
            EasyUIUtils.save(options);

            return;
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#assetTransfer-dlg',
                formId: '#assetTransfer-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                url: AssetTransferMVC.URLs.add.url,
                gridId: '#assetTransfer-datagrid',
                gridUrl: AssetTransferMVC.URLs.Search.url
            };
        },
        getQueryParams: function () {
            var userId = $("#assetTransfer-userId").textbox('getValue');
            var currency = $("#assetTransfer-currency").combobox('getValue');
            var legalTender = $("#assetTransfer-legalTender").combobox('getValue');

            var params = {
                userId: userId,
                currency: currency,
                legalTender: legalTender
            };

            return params;
        }
    }
};