$(function () {
    systemBillTotal.init();
});

var systemBillTotal = {
    init: function () {
        systemBillTotalMVC.View.initControl();
        systemBillTotalMVC.View.bindEvent();
    }
};

var systemBillTotalCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/perpetual/systemBillTotal',
    commonUrl: BossGlobal.ctxPath + '/v1/boss/common/'
};

var systemBillTotalMVC = {
    URLs: {
        list: {
            url: systemBillTotalCommon.baseUrl + '/list',
            method: 'GET'
        },
        getPerpetualPairBaseCodeList: {
            url: systemBillTotalCommon.commonUrl + 'perpetual/currencyPair/baseCodes',
            method: 'GET'
        }
    },
    Model: {
        allCurrency: {},
        allBroker: {}
    },
    View: {
        initControl: function () {

            $.get(systemBillTotalMVC.URLs.getPerpetualPairBaseCodeList.url, "", function (_data) {

                var baseCodes = [];
                baseCodes.push({
                    "value": "",
                    "text": "全部"
                });
                for (var i = 0; i < _data.length; i++) {
                    var item = _data[i];
                    baseCodes.push({
                        "value": item.baseCode,
                        "text": item.baseCode
                    });
                }

                $('#query-pairCode').combobox({
                    data: baseCodes,
                    valueField: "value",
                    textField: "text",
                    panelHeight: 300,
                    editable: true,
                    required: true
                });
            });



            $('#systemBillTotal-datagrid').datagrid({
                method: 'GET',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: systemBillTotalMVC.URLs.list.url,
                toolbar: [],
                loadFilter: function (src) {
                    if (!src.code) {
                        return src.data;
                    }
                    return $.messager.alert('失败', src.msg, 'error');
                },
                columns: [[
                    {
                        field: 'id',
                        title: 'ID',
                        width: 100,
                        sortable: true
                    },
                    {
                        field: 'currencyCode',
                        title: '币种',
                        width: 100,
                        sortable: true
                    },
                    {
                        field: 'fee',
                        title: '变动手续费总和',
                        width: 100
                    },
                    {
                        field: 'profit',
                        title: '变动收益总和',
                        width: 100
                    },
                    {
                        field: 'userBalance',
                        title: '用户资产余额总和',
                        width: 100
                    },
                    {
                        field: 'totalFee',
                        title: '用户手续费总和',
                        width: 100
                    },
                    {
                        field: 'positionSize',
                        title: '用户仓位资产总和',
                        width: 100
                    },
                    {
                        field: 'totalProfit',
                        title: '用户账单收益总和',
                        width: 100
                    },
                    {
                        field: 'baseAdjust',
                        title: '人工调整',
                        width: 100
                    },
                    {
                        field: 'createdDate',
                        title: '创建时间',
                        width: 100,
                        formatter: function (value) {
                            return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                        }
                    },
                    {
                        field: 'modifyDate',
                        title: '更新时间',
                        width: 100,
                        formatter: function (value) {
                            return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                        }
                    }
                ]],
                onLoadSuccess: function () {
                }
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', systemBillTotalMVC.Controller.query);
        }
    },
    Controller: {

        query: function () {
            var params = systemBillTotalMVC.Util.getQueryParam();
            $('#systemBillTotal-datagrid').datagrid('load', params);
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#systemBillTotal-dlg',
                formId: '#systemBillTotal-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                gridId: null
            };
        },
        getQueryParam: function () {
            var startTime = $("#query-startTime").datetimebox('getValue');
            var endTime = $("#query-endTime").datetimebox('getValue');
            var pairCode = $("#query-pairCode").combobox('getValue');

            var params = {
                pairCode: pairCode,
                startTime: startTime,
                endTime: endTime
            };

            return params;
        }
    }
};
