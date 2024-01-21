$(function () {
    WalletDs.init();
});

var WalletDs = {
    init: function () {
        WalletMVC.View.initControl();
        WalletMVC.View.bindEvent();
        WalletMVC.View.bindValidate();
        WalletMVC.View.initData();
    }
};

var DsCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/asset/wallet/',
    basecomboxUrl: BossGlobal.ctxPath + '/v1/boss/asset/currency/currencies',
    baseCommonUrl: BossGlobal.ctxPath + '/v1/boss/common/'

};

var WalletMVC = {
    URLs: {
        list: {
            url: DsCommon.baseUrl + 'list',
            method: 'GET'
        },
        editReshold: {
            url: DsCommon.baseUrl + 'edit',
            method: 'GET'
        },
        getcurrenname: {
            url: DsCommon.basecomboxUrl + '?findInstitution',
            method: 'post'
        },
        getSpotCurrencyList: {
            url: DsCommon.baseCommonUrl + 'spot/currency',
            method: 'GET'
        }
    },
    Model: {
        dbTypes: {},
        marginOpen: {},
        dbPoolTypes: {}
    },
    View: {
        initControl: function () {
            $.get(WalletMVC.URLs.getSpotCurrencyList.url, "", function (_data) {
                $("#wallet-currency-name").combobox({
                    data: _data,
                    valueField: "symbol",
                    textField: "symbol",
                    panelHeight: 300,
                    editable: true,
                    onSelect: function (record) {
                        $.tabIndex = $('.wallet-currency-name').index(this);
                        $(".wallet-currency-name").eq($.tabIndex).textbox("setValue", record.id)
                    },
                    onLoadSuccess: function () { //加载完成后,设置选中第一项
                        var val = $(this).combobox("getData");
                        for (var item in val[0]) {
                            if (item == "symbol") {
                                $(this).combobox("select", val[0][item]);
                            }
                        }
                        WalletMVC.Model.marginOpen = val || [];
                    }

                });
            });

            $('#reset-threshold-dlg').dialog({
                closed: true,
                modal: false,
                width: 560,
                height: 250,
                iconCls: 'icon-email',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#reset-threshold-dlg").dialog('close');
                    }
                }, {
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: WalletMVC.Controller.saveEmail
                }]
            });

            $('#ds-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: WalletMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-edit1',
                    handler: function () {
                        WalletMVC.Controller.edit();
                    }
                }, {
                    iconCls: 'icon-reload',
                    handler: function () {
                        $("#ds-datagrid").datagrid({
                            pageNumber: 1,
                            pageSize: 50,
                            url: WalletMVC.URLs.list.url
                        });
                        //EasyUIUtils.reloadDatagrid('#ds-datagrid');
                    }
                }],
                loadFilter: function (src) {
                    if (!src.code) {
                        return src.data;
                    }
                    return $.messager.alert('失败', src.msg, 'error');
                },
                columns: [[{
                    field: 'currency',
                    title: '币种',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        //调用后台下来，便利后进行显示
                        var currencies = WalletMVC.Model.marginOpen;
                        for (var i = 0; i < currencies.length; i++) {
                            if (currencies[i].id === value) {
                                return currencies[i].symbol;
                            }
                        }
                    }
                }, {
                    field: 'walletBalance',
                    title: '钱包余额',
                    width: 150,
                    sortable: true
                }, {
                    field: 'c2cBalance',
                    title: '法币余额',
                    width: 150,
                    sortable: true
                }, {
                    field: 'spotBalance',
                    title: '币币余额',
                    width: 150,
                    sortable: true,
                }, {
                    field: 'contractBalance',
                    title: '合约余额',
                    width: 100,
                    sortable: true
                }, {
                    field: 'depositUnconfirmed',
                    title: '充值未确认',
                    width: 100,
                    sortable: true
                }, {
                    field: 'withdrawUnconfirmed',
                    title: '提现未确认',
                    width: 100,
                    sortable: true
                }, {
                    field: 'difference',
                    title: '差额',
                    width: 100,
                    sortable: true
                }, {
                    field: 'timeNode',
                    title: '数据获取时间',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                    }
                }, {
                    field: 'threshold',
                    title: '参考值',
                    width: 100,
                    sortable: true
                }
                ]],
                onDblClickRow: function (rowIndex, rowData) {
                    return WalletMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            var pager = $("#ds-datagrid").datagrid("getPager");
            if (pager) {
                $(pager).pagination({
                    onSelectPage: function (pageNumber, pageSize) {

                        var url = WalletMVC.Util.getSearchUrl();
                        $("#ds-datagrid").datagrid({
                            pageNumber: pageNumber,
                            pageSize: pageSize,
                            url: url
                        });

                    }
                });
            }

        },
        bindEvent: function () {
            $('#wallet-search').bind('click', WalletMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#ds-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return WalletMVC.Controller.edit();
            }
            if (name === "remove") {
                return WalletMVC.Controller.remove();
            }
            if (name === "connect") {
                return WalletMVC.Controller.testConnectionById(index);
            }
        },
        edit: function () {
            var row = $('#ds-datagrid').datagrid('getSelected');
            if (row) {
                $('#reset-threshold-dlg').dialog('open').dialog('center');
                $("#reset-threshold-form").form('clear');
                $("#reset-currencyId").val(row.currency);
                $("#reset-threshold").text(row.threshold);
                // $("#reset-currencyId").val(1);
                // $("#reset-threshold").text("123");
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        find: function () {
            var url = WalletMVC.Util.getSearchUrl();
            $("#ds-datagrid").datagrid({
                pageNumber: 1,
                pageSize: 50,
                url: url
            });

            // var currencyname = $("#wallet-currency-name").combobox('getValue');
            // var startDate = $("#startDateMillis").textbox('getValue');
            // var url = WalletMVC.URLs.list.url + '?currencyCode=' + currencyname + '&startDateMillis=' + startDate;
            //
            // EasyUIUtils.loadToDatagrid('#ds-datagrid', url)
        },
        walletreset: function () {
            $('#wallet-currency-name').combobox('clear')
            $('#wallet-beginDate').combo('setText', '');
            $('#wallet-endDate').combo('setText', '');
        },
        saveEmail: function () {
            var currencyId = $("#reset-currencyId").val();
            var newthreshold = $("#reset-newthreshold").textbox('getValue');
            if (newthreshold == "") {
                $.messager.alert('提示', '请填写新参考值！！', 'info');
                return;
            } else {
                var url = WalletMVC.URLs.editReshold.url;
                $.ajax({
                    url: url,
                    type: 'put',
                    data: {
                        currencyCode: currencyId,
                        threshold: newthreshold
                    },
                    success: function (data) {
                        $("#reset-threshold-dlg").dialog('close');
                        EasyUIUtils.reloadDatagrid('#ds-datagrid');
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
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#ds-dlg',
                formId: '#ds-form',
                actId: '#modal-action',
                rowId: '#dsId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                gridId: null,
            };
        },
        getSearchUrl: function () {
            var currencyname = $("#wallet-currency-name").combobox('getValue');
            var startDate = $("#startDateMillis").textbox('getValue');
            return WalletMVC.URLs.list.url + '?currencyCode=' + currencyname + '&startDateMillis=' + startDate;

        },
    }
};
