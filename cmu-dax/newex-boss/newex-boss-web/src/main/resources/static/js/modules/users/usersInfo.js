$(function () {
    UsersInfo.init();
});

var UsersInfo = {
    init: function () {
        UsersInfoMVC.View.initControl();
        UsersInfoMVC.View.bindEvent();
        UsersInfoMVC.View.bindValidate();
        UsersInfoMVC.View.initData();
    }
};

var UsersInfoCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/users/usersInfo/',
    secretsBaseUrl: BossGlobal.ctxPath + '/v1/boss/users/api-secrets/',
    kycbaseUrl: BossGlobal.ctxPath + '/v1/boss/users/kyc/',
    frozenUrl: BossGlobal.ctxPath + '/v1/boss/users/frozen/',

    assetBaseUrl: BossGlobal.ctxPath + '/v1/boss/asset/currency/',
    assetCurrenciesUrl: BossGlobal.ctxPath + '/v1/boss/asset/currency/currencies',

    orderBaseRoleUrl: BossGlobal.ctxPath + '/v1/boss/c2c/order/',
    c2cBaseComBoxUrl: BossGlobal.ctxPath + '/v1/boss/c2c/order/currencies',
    c2cBaseUrl: BossGlobal.ctxPath + '/v1/boss/c2c/user-currency/',
    c2cTradingOrderUrl: BossGlobal.ctxPath + '/v1/boss/c2c/trading-order/',
    TradeTypeUrl: BossGlobal.ctxPath + '/v1/boss/c2c/asset-transfer/type',

    assetcbaseUrl: BossGlobal.ctxPath + '/v1/boss/spot/assetcmanage/',
    orderManagementbaseUrl: BossGlobal.ctxPath + '/v1/boss/spot/ordermanage/',
    billbaseUrl: BossGlobal.ctxPath + '/v1/boss/spot/billmanage/',

    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',

    baseOpLogUrl: BossGlobal.ctxPath + '/v1/boss/users/oplog/',
    baseCommonUrl: BossGlobal.ctxPath + '/v1/boss/common/',
    contractAssertUrl: BossGlobal.ctxPath + '/v1/boss/perpetual/assert'
};

var UsersInfoMVC = {
        URLs: {
            settings: {
                url: UsersInfoCommon.baseUrl + '${userId}/settings',
                method: 'GET'
            },
            detail: {
                url: UsersInfoCommon.baseUrl + '${userId}/detail',
                method: 'GET'
            },
            list: {
                url: UsersInfoCommon.baseUrl + 'list',
                method: 'GET'
            },
            google: {
                url: UsersInfoCommon.baseUrl + '${userId}/google-code/unbind',
                method: 'GET'
            },
            mobile: {
                url: UsersInfoCommon.baseUrl + '${userId}/mobile/unbind',
                method: 'GET'
            },
            editEmail: {
                url: UsersInfoCommon.baseUrl + '${userId}/email',
                method: 'POST'
            },

            assetCurrencies: {
                url: UsersInfoCommon.assetBaseUrl + 'currencies?findInstitution',
                method: 'POST'
            },
            assetRecord: {
                url: UsersInfoCommon.assetBaseUrl + "getUserRecord",
                method: 'GET'
            },
            useraddress: {
                url: UsersInfoCommon.assetBaseUrl + 'userAddress',
                method: 'GET'
            },
            assetclist: {
                url: UsersInfoCommon.assetcbaseUrl + 'list',
                method: 'GET'
            },
            contractAssertUrl: {
                url: UsersInfoCommon.contractAssertUrl,
                method: 'GET'
            },
            optionType: {
                url: UsersInfoCommon.TradeTypeUrl,
                method: 'GET'
            },
            BitType: {
                url: UsersInfoCommon.c2cBaseComBoxUrl,
                method: 'GET'
            },
            C2cSearch: {
                url: UsersInfoCommon.c2cBaseUrl,
                method: 'GET'
            },
            c2cTradingOrderList: {
                url: UsersInfoCommon.c2cTradingOrderUrl + 'list',
                method: 'GET'
            },

            billlist: {
                url: UsersInfoCommon.billbaseUrl + 'bill-all',
                method: 'GET'
            },
            billcombox: {
                url: UsersInfoCommon.billbaseUrl + 'getType',
                method: 'GET'
            },
            billgetadress: {
                url: UsersInfoCommon.billbaseUrl + 'getadress',
                method: 'GET'
            },
            getcode: {
                url: UsersInfoCommon.billbaseUrl + 'getCurrencyCode',
                method: 'GET'
            },

            kyc: {
                url: UsersInfoCommon.kycbaseUrl + 'getKycUser',
                method: 'GET'
            },
            secretsedit: {
                url: UsersInfoCommon.secretsBaseUrl + '${id}',
                method: 'POST'
            },
            secretslist: {
                url: UsersInfoCommon.secretsBaseUrl + '${userId}',
                method: 'GET'
            },

            getUserInfo: {
                url: UsersInfoCommon.orderBaseRoleUrl + 'getC2cUserInfo',
                method: 'GET'
            },
            c2cUserFreeze: {
                url: UsersInfoCommon.orderBaseRoleUrl + 'c2cUserFreeze',
                method: 'GET'
            },
            c2cUserUpFreeze: {
                url: UsersInfoCommon.orderBaseRoleUrl + 'c2cUserUpFreeze',
                method: 'GET'
            },
            c2cBusinessFreeze: {
                url: UsersInfoCommon.orderBaseRoleUrl + 'c2cBusinessFreeze',
                method: 'GET'
            },
            c2cBusinessUpFreeze: {
                url: UsersInfoCommon.orderBaseRoleUrl + 'c2cBusinessUpFreeze',
                method: 'GET'
            },

            orderlist: {
                url: UsersInfoCommon.orderManagementbaseUrl + 'order-all',
                method: 'GET'
            },
            historylist: {
                url: UsersInfoCommon.orderManagementbaseUrl + 'finish',
                method: 'GET'
            },

            freezeEmail24Hours: {
                url: UsersInfoCommon.frozenUrl + '${userId}/freeze-email-24hours',
                method: 'POST'
            },
            freezeBiz: {
                url: UsersInfoCommon.frozenUrl + '${userId}/freeze',
                method: 'POST'
            },
            unfreezeBiz: {
                url: UsersInfoCommon.frozenUrl + '${userId}/unfreeze',
                method: 'POST'
            },
            opLog: {
                custom: {
                    url: UsersInfoCommon.baseOpLogUrl + 'custom'
                },
                user: {
                    url: UsersInfoCommon.baseOpLogUrl + 'user'
                },
                memo: {
                    url: UsersInfoCommon.baseOpLogUrl + 'memo'
                }
            },
            getSpotCurrencyList: {
                url: UsersInfoCommon.baseCommonUrl + 'spot/currency',
                method: 'GET'
            },
            getC2cCurrencyList: {
                url: UsersInfoCommon.baseCommonUrl + 'c2c/currency',
                method: 'GET'
            },
            replacePhone: {
                url: UsersInfoCommon.baseUrl + 'replacePhone',
                method: 'POST'
            }

        },
        Model: {
            marginOpen: [],
            allCurrencyWithId: [],
            allCurrencyWithCode: [],
            currencyPairWithCode: [],
            allC2CCurrencyWithId: [],
            allC2CCurrencyWithCode: [],
            allCurrencyPairWithCode: [],
            roles: {},
            frozen: 0,
            spotFrozenFlag: 0,
            c2cFrozenFlag: 0,
            contractsFrozenFlag: 0,
            assetFrozenFlag: 0
        },
        View: {
            initControl: function () {
                // 加载spot的币种
                $.get(UsersInfoMVC.URLs.getSpotCurrencyList.url, "", function (_data) {
                    var allCurrency = _data || [];

                    UsersInfoMVC.Model.marginOpen = allCurrency;

                    var allCurrencyWithId = [];
                    var allCurrencyWithCode = [];

                    allCurrencyWithId.push({
                        "value": -1,
                        "text": "全部"
                    });

                    allCurrencyWithCode.push({
                        "value": "",
                        "text": "全部"
                    });

                    for (var i = 0; i < allCurrency.length; i++) {
                        var item = allCurrency[i];

                        allCurrencyWithId.push({
                            "value": item.id,
                            "text": item.symbol
                        });

                        allCurrencyWithCode.push({
                            "value": item.symbol,
                            "text": item.symbol
                        });
                    }

                    UsersInfoMVC.Model.allCurrencyWithId = allCurrencyWithId;
                    UsersInfoMVC.Model.allCurrencyWithCode = allCurrencyWithCode;
                });

                // 加载c2c的币种
                $.get(UsersInfoMVC.URLs.getC2cCurrencyList.url, "", function (_data) {
                    var allC2CCurrency = _data || [];

                    var allC2CCurrencyWithId = [];
                    var allC2CCurrencyWithCode = [];

                    allC2CCurrencyWithId.push({
                        "value": -1,
                        "text": "全部"
                    });

                    allC2CCurrencyWithCode.push({
                        "value": "",
                        "text": "全部"
                    });

                    for (var i = 0; i < allC2CCurrency.length; i++) {
                        var item = allC2CCurrency[i];

                        allC2CCurrencyWithId.push({
                            "value": item.id,
                            "text": item.symbol
                        });

                        allC2CCurrencyWithCode.push({
                            "value": item.symbol,
                            "text": item.symbol
                        });
                    }

                    UsersInfoMVC.Model.allC2CCurrencyWithId = allC2CCurrencyWithId;
                    UsersInfoMVC.Model.allC2CCurrencyWithCode = allC2CCurrencyWithCode;
                });

                // 加载币对
                $.get(UsersInfoMVC.URLs.getcode.url, "", function (_data) {
                    var allCurrencyPair = _data.data || [];

                    var currencyPairWithCode = [];
                    var allCurrencyPairWithCode = [];

                    allCurrencyPairWithCode.push({
                        "value": "",
                        "text": "全部"
                    });

                    for (var i = 0; i < allCurrencyPair.length; i++) {
                        var item = allCurrencyPair[i];

                        currencyPairWithCode.push({
                            "value": item.code,
                            "text": item.code
                        });

                        allCurrencyPairWithCode.push({
                            "value": item.code,
                            "text": item.code
                        });
                    }

                    UsersInfoMVC.Model.currencyPairWithCode = currencyPairWithCode;
                    UsersInfoMVC.Model.allCurrencyPairWithCode = allCurrencyPairWithCode;
                });

                $('#user-datagrid').datagrid({
                    method: 'get',
                    fit: true,
                    fitColumns: false,
                    singleSelect: true,
                    pagination: true,
                    rownumbers: true,
                    pageSize: 50,
                    url: UsersInfoMVC.URLs.list.url,
                    toolbar: [{
                        text: '修改用户邮箱',
                        iconCls: 'icon-edit',
                        handler: function () {
                            UsersInfoMVC.Controller.resetEmail();
                        }
                    }, '-', {
                        text: '预览',
                        iconCls: 'icon-info',
                        handler: function () {
                            UsersInfoMVC.Controller.showDetail();
                        }
                    }, '-', {
                        text: '全部',
                        iconCls: 'icon-reload',
                        handler: function () {
                            $('#user-datagrid').datagrid('load', {});
                        }
                    }, '-', {
                        text: '换绑手机号',
                        iconCls: 'icon-edit1',
                        handler: function () {
                            UsersInfoMVC.Controller.replacePhoneDlg();
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
                        title: '用户ID',
                        width: 150,
                        sortable: true
                    }, {
                        field: 'email',
                        title: '用户Email',
                        width: 150,
                        sortable: true
                    }, {
                        field: 'mobile',
                        title: '手机号',
                        width: 150,
                        sortable: true
                    }, {
                        field: 'authorities',
                        title: '用户角色',
                        width: 150,
                        sortable: true
                    }, {
                        field: 'nickname',
                        title: '用户昵称',
                        width: 150,
                        sortable: true
                    }, {
                        field: 'realName',
                        title: '用户名',
                        width: 150,
                        sortable: true
                    }, {
                        field: 'createdDate',
                        title: '注册时间',
                        width: 150,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss");
                        }
                    }, {
                        field: 'antiPhishingCode',
                        title: '防钓鱼码',
                        width: 150,
                        sortable: true
                    }, {
                        field: 'uid',
                        title: '被邀请码',
                        width: 150,
                        sortable: true
                    }, {
                        field: 'channel',
                        title: '渠道码',
                        width: 150,
                        sortable: true
                    }, {
                        field: 'frozen',
                        title: '账号冻结状态',
                        width: 150,
                        sortable: true,
                        formatter: function (value, row, index) {
                            if (value == 0) {
                                return "未冻结";
                            }
                            if (value == 1) {
                                return "已冻结";
                            }
                            return value;
                        }
                    }, {
                        field: 'type',
                        title: '用户类型',
                        width: 150,
                        sortable: true,
                        formatter: function (value, row, index) {
                            if (value == 0) {
                                return "普通用户";
                            }
                            if (value == 1) {
                                return "公司内部用户";
                            }
                            return value;
                        }
                    }, {
                        field: 'areaCode',
                        title: '手机编号',
                        width: 150,
                        sortable: true
                    }, {
                        field: 'status',
                        title: '用户状态',
                        width: 150,
                        sortable: true,
                        formatter: function (value, row, index) {
                            if (value == 0) {
                                return "开启";
                            }
                            if (value == 1) {
                                return "禁用";
                            }
                            return value;
                        }
                    }, {
                        field: 'regIp',
                        title: '注册IP',
                        width: 150,
                        sortable: true
                    }, {
                        field: 'memo',
                        title: '说明备注',
                        width: 150,
                        sortable: true
                    }, {
                        field: 'googleAuthFlag',
                        title: 'Google验证码绑定状态',
                        width: 150,
                        sortable: true,
                        formatter: function (value, row, index) {
                            if (value == 0) {
                                return "未绑定";
                            }
                            if (value == 1) {
                                return "绑定";
                            }
                            return value;
                        }
                    }, {
                        field: 'emailAuthFlag',
                        title: '邮箱验证状态',
                        width: 150,
                        sortable: true,
                        formatter: function (value, row, index) {
                            if (value == 0) {
                                return "未开启";
                            }
                            if (value == 1) {
                                return "开启";
                            }
                            return value;
                        }
                    }, {
                        field: 'mobileAuthFlag',
                        title: '手机验证状态',
                        width: 150,
                        sortable: true,
                        formatter: function (value, row, index) {
                            if (value == 0) {
                                return "未开启";
                            }
                            if (value == 1) {
                                return "开启";
                            }
                            return value;
                        }
                    }]],
                    onDblClickRow: function (rowIndex, rowData) {
                        return UsersInfoMVC.Controller.showDetail();
                    },
                    onLoadError: function (data) {
                        return $.messager.alert('失败', data.responseJSON.msg, 'error');
                    },
                    onLoadSuccess: function () {
                    }
                });

                // dialogs
                $('#reset-email-dlg').dialog({
                    closed: true,
                    modal: false,
                    width: 560,
                    height: 250,
                    iconCls: 'icon-email',
                    buttons: [{
                        text: '关闭',
                        iconCls: 'icon-no',
                        handler: function () {
                            $("#reset-email-dlg").dialog('close');
                        }
                    }, {
                        text: '保存',
                        iconCls: 'icon-save',
                        handler: UsersInfoMVC.Controller.saveEmail
                    }]
                });

                // 换绑手机号
                $('#replace-phone-dlg').dialog({
                    closed: true,
                    modal: false,
                    width: 300,
                    height: 200,
                    iconCls: 'icon-phone',
                    buttons: [{
                        text: '关闭',
                        iconCls: 'icon-no',
                        handler: function () {
                            $("#reset-email-dlg").dialog('close');
                        }
                    }, {
                        text: '保存',
                        iconCls: 'icon-save',
                        handler: UsersInfoMVC.Controller.replacePhone
                    }]
                });

                $('#report-detail-dlg').dialog({
                    closed: true,
                    modal: true,
                    striped: true,
                    collapsible: true,
                    resizable: true,
                    width: 1200,
                    height: window.innerHeight - 100 || document.documentElement.clientHeight - 100 || document.body.clientHeight - 100,
                    maximizable: true,
                    iconCls: 'icon-info',
                    buttons: [{
                        text: '返回',
                        iconCls: 'icon-back',
                        handler: function () {
                            $("#report-detail-dlg").dialog('close');
                            $("#report-detail-dlg").dialog('refresh');
                        }
                    }]
                });

                $('#history-dlg').dialog({
                    closed: true,
                    modal: false,
                    width: 650,
                    title: "历史委托订单详情",
                    height: 450,
                    iconCls: 'icon-info',
                    buttons: []
                });

                $('#bill-detail-dlg').dialog({
                    closed: true,
                    modal: true,
                    width: 550,
                    height: 350,
                    maximizable: true,
                    iconCls: 'icon-info',
                    buttons: [{
                        text: '关闭',
                        iconCls: 'icon-no',
                        handler: function () {
                            $("#bill-detail-dlg").dialog('close');
                        }
                    }]
                });

                $('#order-dlg').dialog({
                    closed: true,
                    modal: false,
                    width: 650,
                    title: "订单详情",
                    height: 470,
                    iconCls: 'icon-info',
                    buttons: []
                });

                $('#ratelimit-dlg').dialog({
                    closed: true,
                    modal: false,
                    width: 560,
                    height: 400,
                    buttons: [{
                        text: '关闭',
                        iconCls: 'icon-no',
                        handler: function () {
                            $("#ratelimit-dlg").dialog('close');
                        }
                    }, {
                        text: '保存',
                        iconCls: 'icon-save',
                        handler: ApiKeyUtils.secretsSave
                    }]
                });

                $('#bill-dlg').dialog({
                    closed: true,
                    modal: false,
                    width: 650,
                    title: "账单详情",
                    height: 450,
                    iconCls: 'icon-info',
                    buttons: []
                });

                $('#frozenSwitch').switchbutton({
                    onChange: function (checked) {
                        // UsersInfoMVC.Model.switchButtonStatus = 'all';
                        PersonalSettingsUtils.frozen('all', checked)
                    }
                });

                $('#spotFrozenSwitch').switchbutton({
                    onChange: function (checked) {
                        // UsersInfoMVC.Model.switchButtonStatus = 'spot';
                        PersonalSettingsUtils.frozen('spot', checked)
                    }
                });

                $('#c2cFrozenSwitch').switchbutton({
                    onChange: function (checked) {
                        // UsersInfoMVC.Model.switchButtonStatus = 'c2c';
                        PersonalSettingsUtils.frozen('c2c', checked)
                    }
                });

                $('#contractsFrozenSwitch').switchbutton({
                    onChange: function (checked) {
                        // UsersInfoMVC.Model.switchButtonStatus = 'contracts';
                        PersonalSettingsUtils.frozen('contracts', checked)
                    }
                });

                $('#assetFrozenSwitch').switchbutton({
                    onChange: function (checked) {
                        // UsersInfoMVC.Model.switchButtonStatus = 'asset';
                        PersonalSettingsUtils.frozen('asset', checked)
                    }
                });
            },
            bindEvent: function () {
                $('#btn-search').bind('click', UsersInfoMVC.Controller.find);

                $('#RedrawMoney-search').bind('click', RechargeUtils.RedrawMoney);
                $('#extract-search').bind('click', ExtractUtils.extract);

                $('#RedrawMoney-record-search').bind('click', RechargeUtils.RedrawMoneyRecord);
                $('#extract-record-search').bind('click', ExtractUtils.extractRecord);

                $('#account-search').bind('click', C2cBillUtils.findBill);
                $('#post-search').bind('click', C2cBillUtils.findPost);
                $('#c2corder-search').bind('click', C2cBillUtils.findOrder);

                $('#bill-search').bind('click', SpotBillUtils.billfind);
                $('#order-search').bind('click', SpotBillUtils.orderManagementFind);
                $('#history-btn-search').bind('click', SpotBillUtils.historyfind);
            },
            bindValidate: function () {
            },
            initData: function () {
            }
        },
        Controller: {
            doOption: function (index, name) {

            },
            resetEmail: function () {
                var row = $('#user-datagrid').datagrid('getSelected');
                if (row) {
                    $('#reset-email-dlg').dialog('open').dialog('center');
                    $("#reset-email-form").form('clear');
                    $("#reset-userId").val(row.id);
                    $("#reset-email").text(row.email);
                } else {
                    $.messager.alert('警告', '请选中一条记录!', 'info');
                }
            },
            saveEmail: function () {
                var userId = $("#reset-userId").val();

                var newEmail = $("#reset-newEmail").textbox('getValue');
                if (newEmail == "") {
                    $.messager.alert('提示', '请填写Email！！', 'info');
                    return;
                } else {
                    var tmpl = UsersInfoMVC.URLs.editEmail.url;
                    var data = {
                        userId: userId,
                    };
                    var url = juicer(tmpl, data);
                    $.ajax({
                        url: url,
                        type: 'put',
                        data: {
                            newEmail: newEmail
                        },
                        success: function (data) {
                            $("#reset-email-dlg").dialog('close');

                            $('#user-datagrid').datagrid('load', {});
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
            },
            showDetail: function () {
                UsersInfoMVC.Util.isRowSelected(function (row) {
                    $('#report-detail-dlg').dialog('open').dialog('center');

                    UsersInfoMVC.Util.fillDetailLabels(row);
                });
            },
            find: function () {
                var params = UsersInfoMVC.Util.getUserQueryParams();

                $('#user-datagrid').datagrid('load', params);
            },
            replacePhone: function () {
                var options = UsersInfoMVC.Util.getOptions();
                options.gridId = '#user-datagrid';
                options.dlgId = '#replace-phone-dlg';
                options.gridUrl = UsersInfoMVC.URLs.list.url;
                options.formId = '#replace-phone-form';
                options.url = UsersInfoMVC.URLs.replacePhone.url;

                return EasyUIUtils.save(options);
            },
            replacePhoneDlg: function () {
                var row = $('#user-datagrid').datagrid('getSelected');
                if (row) {
                    var options = UsersInfoMVC.Util.getOptions();
                    options.iconCls = 'icon-edit';
                    options.data = row;
                    options.dlgId = '#replace-phone-dlg';
                    options.formId = '#replace-phone-form';
                    options.title = '换绑[' + options.data.id + ']的手机号';

                    EasyUIUtils.openEditDlg(options);
                } else {
                    $.messager.alert('警告', '请选中一条记录!', 'info');
                }
            }
        },
        Util: {
            getOptions: function () {
                return {
                    dlgId: '#user-dlg',
                    formId: '#user-form',
                    actId: '#modal-action',
                    rowId: '#roleId',
                    title: '',
                    iconCls: 'icon-add',
                    data: {},
                    callback: function (arg) {
                    },
                    gridId: null,
                    gridUrl: null
                };
            },
            getStatusStr: function (status) {
                var statusStr = '初始值';

                if (status == 0) {
                    statusStr = '初始值';
                }

                if (status == 1) {
                    statusStr = '审核通过';
                }

                if (status == 2) {
                    statusStr = '驳回';
                }

                if (status == 3) {
                    statusStr = '其他异常';
                }

                if (status == 4) {
                    statusStr = '待审核';
                }

                return statusStr;
            },
            getUserQueryParams: function () {
                var id = $("#usersInfo-id").textbox('getValue');
                var email = $("#usersInfo-email").textbox('getValue');
                var mobile = $("#usersInfo-mobile").textbox('getValue');
                var realName = $("#usersInfo-realName").textbox('getValue');
                var beginTime = $("#begin-Time").textbox('getValue');
                var endTime = $("#end-Time").textbox('getValue');
                var uid = $('#uid').textbox('getValue');
                var channel = $('#channel').textbox('getValue');

                var params = {
                    id: id,
                    email: email,
                    mobile: mobile,
                    realName: realName,
                    beginTime: beginTime,
                    endTime: endTime,
                    uid: uid,
                    channel: channel
                };

                return params;
            },
            isRowSelected: function (func) {
                var row = $('#user-datagrid').datagrid('getSelected');
                if (row) {
                    func(row);
                } else {
                    $.messager.alert('警告', '请选中一条记录!', 'info');
                }
            },
            updateAuthFlag: function (userId) {
                var url = juicer(UsersInfoMVC.URLs.detail.url, {userId: userId});

                $.ajax({
                    url: url,
                    type: 'get',
                    success: function (result) {
                        if (result.code) {
                            return $.messager.show({
                                title: '错误',
                                msg: result.msg
                            });
                        } else {
                            var detail = result.data;

                            var emailAuthFlag = detail.emailAuthFlag;
                            var mobileAuthFlag = detail.mobileAuthFlag;
                            var googleAuthFlag = detail.googleAuthFlag;
                            var uid = detail.uid;
                            $("#report-detail-uid").text(uid);

                            var emailAuthFlagStr = "未开启";
                            if (emailAuthFlag == 1) {
                                emailAuthFlagStr = "开启";
                            }
                            $("#report-detail-emailAuthFlag").text(emailAuthFlagStr);

                            var mobileAuthFlagStr = "未开启";
                            if (mobileAuthFlag == 1) {
                                mobileAuthFlagStr = "开启";
                            }
                            $("#report-detail-mobileAuthFlag").text(mobileAuthFlagStr);

                            var googleAuthFlagStr = "未绑定";
                            if (googleAuthFlag == 1) {
                                googleAuthFlagStr = "绑定";
                            }
                            $("#report-detail-googleAuthFlag").text(googleAuthFlagStr);
                        }
                    },
                    error: function (data) {
                        if (data.responseJSON.code == "403") {
                            $.messager.alert('提示', data.responseJSON.msg, 'error');
                        } else {
                            $.messager.alert('提示', data.responseJSON.msg, 'error');
                        }
                    }
                });
            },
            updateKycStatus: function (userId, level, labelId) {
                // kyc认证状态
                $.ajax({
                    url: UsersInfoMVC.URLs.kyc.url,
                    type: 'POST',
                    data: {
                        userId: userId,
                        level: level
                    },
                    success: function (_datax) {
                        var status = 0;
                        if (_datax.data) {
                            status = _datax.data.status;
                        }

                        var statusStr = UsersInfoMVC.Util.getStatusStr(status);

                        $(labelId).text(statusStr);
                    }
                });
            },
            changeUserTabs: function (userId) {
                $('#tabl1').tabs('select', 0);

                $('#tabl1').tabs({
                    border: false,
                    onSelect: function (title) {
                        var tab = $('#tabl1').tabs('getSelected');
                        var index = $('#tabl1').tabs('getTabIndex', tab);

                        if (index == 1) {
                            // 个人设置
                            PersonalSettingsUtils.updateC2CUserType(userId);

                            PersonalSettingsUtils.loadFrozenSwitch(userId);
                        }

                        if (index == 2) {
                            // 充值 - 充值地址
                            RechargeUtils.loadAddress(UsersInfoMVC.URLs.useraddress.url, userId, UsersInfoMVC.Model.marginOpen);
                            RechargeUtils.loadAllCurrencyForAddress(UsersInfoMVC.Model.allCurrencyWithId);

                            // 充值 - 充值记录
                            RechargeUtils.loadRecords(UsersInfoMVC.URLs.assetRecord.url, userId, UsersInfoMVC.Model.marginOpen);
                            RechargeUtils.loadAllCurrencyForRecord(UsersInfoMVC.Model.allCurrencyWithCode);
                        }

                        if (index == 3) {
                            // 提币 - 提币地址
                            ExtractUtils.loadAddress(UsersInfoMVC.URLs.useraddress.url, userId, UsersInfoMVC.Model.marginOpen);
                            ExtractUtils.loadAllCurrencyForAddress(UsersInfoMVC.Model.allCurrencyWithId);

                            // 提币 - 提币记录
                            ExtractUtils.loadRecords(UsersInfoMVC.URLs.assetRecord.url, userId, UsersInfoMVC.Model.marginOpen);
                            ExtractUtils.loadAllCurrencyForRecord(UsersInfoMVC.Model.allCurrencyWithCode);
                        }

                        if (index == 4) {
                            // Api Key
                            ApiKeyUtils.loadApiKey(UsersInfoMVC.URLs.secretslist.url, userId);
                        }

                        if (index == 5) {
                            var host = window.location.host;
                            console.log(host);
                            if (host.indexOf('boss.mexdm.com') !== -1) {
                                $('#tabl7').tabs('getTab', '合约资产').panel('options').tab.show();
                                $('#tabl7').tabs('getTab', '法币资产').panel('options').tab.hide();
                                $('#tabl7').tabs('getTab', '币币资产').panel('options').tab.hide();
                                $('#tabl7').tabs('getTab', '组合资产').panel('options').tab.hide();
                                AssetUtils.loadContractAssert(UsersInfoMVC.URLs.contractAssertUrl.url, userId);
                            } else {
                                $('#tabl7').tabs('getTab', '合约资产').panel('options').tab.hide();
                                $('#tabl7').tabs('getTab', '法币资产').panel('options').tab.show();
                                $('#tabl7').tabs('getTab', '币币资产').panel('options').tab.show();
                                $('#tabl7').tabs('getTab', '组合资产').panel('options').tab.show();
                                // 资产信息 - 币币资产
                                AssetUtils.loadSpotAsset(UsersInfoMVC.URLs.assetclist.url, userId);
                                // 资产信息 - 法币资产
                                AssetUtils.loadC2cAsset(UsersInfoMVC.URLs.C2cSearch.url, userId);
                            }
                        }

                        if (index == 6) {
                            C2cBillUtils.loadOptionType();
                            C2cBillUtils.loadAllCurrency();

                            // 法币账单 - 账单记录
                            C2cBillUtils.loadBill(UsersInfoMVC.URLs.C2cSearch.url, userId);

                            // 法币账单 - 广告单
                            C2cBillUtils.loadPost(UsersInfoMVC.URLs.c2cTradingOrderList.url, userId);

                            // 法币账单 - 订单记录
                            C2cBillUtils.loadOrder(UsersInfoMVC.URLs.C2cSearch.url, userId);
                        }

                        if (index == 7) {
                            // 币币账单 - 账单记录
                            SpotBillUtils.loadBill(UsersInfoMVC.URLs.billlist.url, userId);
                            SpotBillUtils.loadAllCurrencyForBill(UsersInfoMVC.Model.allCurrencyWithCode);
                            SpotBillUtils.loadBillType();

                            // 币币账单 - 当前委托订单
                            SpotBillUtils.loadOrder(UsersInfoMVC.URLs.orderlist.url, userId);
                            SpotBillUtils.loadCurrencyPairForOrder(UsersInfoMVC.Model.allCurrencyPairWithCode);

                            // 币币账单 - 历史委托订单
                            SpotBillUtils.loadHistory(UsersInfoMVC.URLs.historylist.url, userId);
                            SpotBillUtils.loadCurrencyPairForHistory(UsersInfoMVC.Model.currencyPairWithCode);
                        }
                        if (index == 8) {
                            //客服操作日志
                            LogUtils.customOpLog(UsersInfoMVC.URLs.opLog.custom.url, userId);
                            //用户操作日志
                            LogUtils.userOpLog(UsersInfoMVC.URLs.opLog.user.url, userId);
                        }

                    }
                });
            },
            fillDetailLabels: function (data) {
                $('#report-detail-dlg label').each(function (i) {
                    $(this).text("");
                });

                $("#remarks").textbox("setValue", "");

                var userid = data["id"];
                var email = data["email"];
                var mobile = data["mobile"];
                var authorities = data["authorities"];
                var nickname = data["nickname"];
                var realName = data["realName"];
                var antiPhishingCode = data["antiPhishingCode"];
                var remarks = data["memo"];
                var frozen = data["frozen"];
                var type = data["type"];
                var areaCode = data["areaCode"];
                var status = data["status"];
                var regIp = data["regIp"];

                var createdDate = moment(new Date(data["createdDate"])).format("YYYY-MM-DD HH:mm:ss");
                var updatedDate = moment(new Date(data["updatedDate"])).format("YYYY-MM-DD HH:mm:ss");

                $("#userId").val(userid);
                $("#report-detail-id").text(userid);

                $("#report-detail-email").text(email);
                $("#report-detail-mobile").text(mobile);
                $("#report-detail-authorities").text(authorities);
                $("#report-detail-nickname").text(nickname);
                $("#report-detail-realName").text(realName);
                $("#report-detail-antiPhishingCode").text(antiPhishingCode);

                $("#remarks").textbox("setValue", remarks);

                var frozenStr = "";
                if (frozen == 1) {
                    // 已冻结
                    frozenStr = "已冻结";

                    $("#freeze").hide();
                    $("#upfreeze").show();
                } else {
                    frozenStr = "未冻结";

                    $("#freeze").show();
                    $("#upfreeze").hide();
                }

                $("#report-detail-frozen").text(frozenStr);

                var typeStr = "";
                if (type == 1) {
                    typeStr = "公司内部用户";
                } else {
                    typeStr = "普通用户";
                }
                $("#report-detail-type").text(typeStr);

                $("#report-detail-areaCode").text(areaCode);

                var statusStr = "";
                if (status == 1) {
                    statusStr = "禁用";
                } else {
                    statusStr = "开启";
                }
                $("#report-detail-status").text(statusStr);

                $("#report-detail-regIp").text(regIp);

                $("#report-detail-createdDate").text(createdDate);
                $("#report-detail-updatedDate").text(updatedDate);

                if (userid) {
                    UsersInfoMVC.Util.changeUserTabs(userid);

                    // 邮箱、手机、Google验证码状态
                    UsersInfoMVC.Util.updateAuthFlag(userid);

                    // kyc认证状态
                    UsersInfoMVC.Util.updateKycStatus(userid, 1, "#report-detail-kyc1");
                    UsersInfoMVC.Util.updateKycStatus(userid, 2, "#report-detail-kyc2");
                }
            }
        }
    }
;