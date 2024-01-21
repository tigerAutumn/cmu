$(function () {
    CashRisk.init();
});

var CashRisk = {
    init: function () {
        RiskMVC.View.initControl();
        RiskMVC.View.bindEvent();
        RiskMVC.View.bindValidate();
    }
};

var RiskCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/risk/cash/'
};

var RiskMVC = {
    URLs: {
        edit: {
            url: RiskCommon.baseUrl + 'edit',
            method: 'POST'
        },
        get: {
            url: RiskCommon.baseUrl + 'get',
            method: 'GET'
        },
        editCheckAmount: {
            url: RiskCommon.baseUrl + 'editCheckAmount',
            method: 'POST'
        },
        getCheckAmount: {
            url: RiskCommon.baseUrl + 'getCheckAmount',
            method: 'GET'
        },
        getCurrentUser: {
            url: RiskCommon.baseUrl + 'getCurrentUser',
            method: 'GET'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {

            $.get(RiskMVC.URLs.getCurrentUser.url, "", function (_datax) {
                if (!_datax.code) {
                    var username = _datax.data.name;

                    $("#user-name-label").text(username);
                }

            });

            $.get(RiskMVC.URLs.get.url, "", function (_datax) {
                var maxAmount = 0;

                if (!_datax.code) {
                    maxAmount = _datax.data.currency;
                }

                $("#max-amount-val").text(maxAmount);
            });

            $.get(RiskMVC.URLs.getCheckAmount.url, "", function (_datax) {
                var checkAmount = 0;

                if (!_datax.code) {
                    checkAmount = _datax.data.currency;
                }

                $("#check-amount-val").text(checkAmount);
            });

            $('#risk-dlg').dialog({
                closed: true,
                modal: false,
                width: 600,
                height: 350,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#risk-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: RiskMVC.Controller.save
                }]
            });

            $('#withdraw-checker-dlg').dialog({
                closed: true,
                modal: false,
                width: 600,
                height: 350,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#withdraw-checker-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: RiskMVC.Controller.saveCheckAmount
                }]
            });


        },
        bindEvent: function () {
            $('#max-amount-edit-button').bind('click', RiskMVC.Controller.edit);
            $('#check-amount-edit-button').bind('click', RiskMVC.Controller.editCheckAmount);
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        edit: function () {
            var options = RiskMVC.Util.getOptions();
            EasyUIUtils.openAddDlg(options);

            var maxAmount = $("#max-amount-val").text();
            $("#maxAmount").textbox("setValue", maxAmount);
        },
        save: function () {
            var options = RiskMVC.Util.getOptions();

            EasyUIUtils.save(options);

            return;
        },
        editCheckAmount: function () {
            var options = RiskMVC.Util.getCheckOptions();
            EasyUIUtils.openAddDlg(options);

            var amount = $("#check-amount-val").text();
            $("#checkAmount").textbox("setValue", amount);
        },
        saveCheckAmount: function () {
            var options = RiskMVC.Util.getCheckOptions();

            EasyUIUtils.save(options);

            return;
        }

    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#risk-dlg',
                formId: '#risk-form',
                actId: null,
                rowId: null,
                title: '',
                iconCls: 'icon-edit1',
                data: {},
                callback: function () {
                    $.get(RiskMVC.URLs.get.url, "", function (_datax) {
                        if (!_datax.code) {
                            var amount = _datax.data.currency;

                            $("#max-amount-val").text(amount);
                        }

                    });
                },
                gridId: null,
                url: RiskMVC.URLs.edit.url
            };
        },
        getCheckOptions: function () {
            return {
                dlgId: '#withdraw-checker-dlg',
                formId: '#withdraw-checker-form',
                actId: null,
                rowId: null,
                title: '',
                iconCls: 'icon-edit1',
                data: {},
                callback: function () {
                    $.get(RiskMVC.URLs.getCheckAmount.url, "", function (_datax) {
                        if (!_datax.code) {
                            var amount = _datax.data.currency;

                            $("#check-amount-val").text(amount);
                        }

                    });
                },
                gridId: null,
                url: RiskMVC.URLs.editCheckAmount.url
            };
        },
    }
};