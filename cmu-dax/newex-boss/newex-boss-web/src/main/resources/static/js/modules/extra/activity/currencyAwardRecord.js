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
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    kycUrl: BossGlobal.ctxPath + '/v1/boss/users/kyc/'
};

var CurrencyAwardMVC = {
        URLs: {
            add: {
                url: CurrencyAwardCommon.baseUrl + 'record/add',
                method: 'POST'
            },
            list: {
                url: CurrencyAwardCommon.baseUrl + 'getByPager',
                method: 'POST'
            },
            detail: {
                url: CurrencyAwardCommon.baseUrl + 'record/getByPager',
                method: 'POST'
            },
            all: {
                url: CurrencyAwardCommon.baseUrl + 'all',
                method: 'GET'
            },
            currency: {
                url: CurrencyAwardCommon.commonUrl + 'spot/currency'
            },
            kyc: {
                url: CurrencyAwardCommon.kycUrl + 'list',
                method: 'GET'
            }
        },
        Model: {
            roles: {},
            allCurrency: {},
            kyc: {}
        },
        View: {
            initControl: function () {

                $.get(CurrencyAwardMVC.URLs.all.url, "", function (_datax) {

                    $("#code").combobox({
                        data: _datax.data,
                        valueField: "id",
                        textField: "code",
                    });

                    var _datax2 = [{'value': '', 'text': '全部'}];
                    for (var i = 0; i < _datax.data.length; i++) {
                        _datax2.push({
                            'value': _datax.data[i].id,
                            'text': _datax.data[i].code
                        });
                    }
                    $("#activity-id").combobox({
                        data: _datax2,
                        valueField: "value",
                        textField: "text",
                        required: true,
                    });
                });

                $.get(CurrencyAwardMVC.URLs.currency.url, "", function (_datax) {
                    CurrencyAwardMVC.Model.allCurrency = _datax;
                    var _datax2 = [{'value': '', 'text': '全部'}];
                    for (var i = 0; i < _datax.length; i++) {
                        _datax2.push({
                            'value': _datax[i].id,
                            'text': _datax[i].symbol
                        });
                    }

                    $("#currency-code").combobox({
                        data: _datax2,
                        valueField: "value",
                        textField: "text",
                        required: true,
                    });
                });

                $('#currencyAward-datagrid').datagrid({
                    method: 'post',
                    fit: true,
                    fitColumns: true,
                    singleSelect: true,
                    pagination: true,
                    rownumbers: true,
                    pageSize: 50,
                    url: CurrencyAwardMVC.URLs.detail.url,
                    toolbar: [{
                        iconCls: 'icon-add',
                        handler: function () {
                            CurrencyAwardMVC.Controller.add();
                        }
                    }, '-', {
                        text: '全部数据',
                        iconCls: 'icon-reload',
                        handler: function () {
                            $("#currencyAward-datagrid").datagrid("load", {});
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
                        field: 'userId',
                        title: '用户编号',
                        width: 150,
                        sortable: true,
                    }, {
                        field: 'currencyId',
                        title: '币种',
                        width: 150,
                        sortable: true,
                        formatter: function (val) {
                            var currencies = CurrencyAwardMVC.Model.allCurrency;
                            for (var i = 0; i < currencies.length; i++) {
                                if (currencies[i].id === val) {
                                    return currencies[i].symbol;
                                }
                            }
                        }
                    }, {
                        field: 'amount',
                        title: '发币数量',
                        width: 150,
                        sortable: true,
                    }]],
                    onLoadError: function (data) {
                        return $.messager.alert('失败', data.responseJSON.msg, 'error');
                    },
                    onLoadSuccess: function () {
                        $('.pagination-page-list').hide();
                    }
                });


                $('#currencyAward-dlg').dialog({
                    closed: true,
                    modal: false,
                    width: 800,
                    height: 400,
                    iconCls: 'icon-add',
                    buttons: [{
                        text: '预览',
                        iconCls: 'icon-preview',
                        handler: CurrencyAwardMVC.Controller.preview
                    }, {
                        text: '关闭',
                        iconCls: 'icon-no',
                        handler: function () {
                            $("#currencyAward-dlg").dialog('close');
                        }
                    }, {
                        text: '确认',
                        iconCls: 'icon-save',
                        id: 'okBtn',
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
                $('#currencyAward-datagrid').datagrid('selectRow', index);
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
                $('#code').combobox('setValue', '请选择活动');
            },
            find: function () {
                var params = CurrencyAwardMVC.Util.getQueryParams();
                $('#currencyAward-datagrid').datagrid('load', params);
            },
            preview: function () {

                $.get(CurrencyAwardMVC.Util.getActivityDetail(), "", function (_datax) {
                    CurrencyAwardMVC.Model.activities = _datax.data;

                });
                var textArea = $('#area').val();
                var array = [];
                var temp = textArea.split(/[\s\n]/);
                var total = 0;
                for (var i = 0; i < temp.length; i++) {
                    total++;
                    var arr = temp[i].split(",");
                    if (arr.length != 3) {
                        $.messager.alert('警告', '数据格式输入错误');
                        return;
                    }
                    for (var j = 0; j < arr.length; j++) {
                        var singleArray = arr[j].split(",");
                        array.push(singleArray);
                    }
                }
                $('#detail-number').text(total);


                var recordJson = new Object();
                var jsonArray = [];
                var rows = [];
                for (var i = 0; i < array.length; i += 3) {
                    var row = new Object();
                    row.userId = array[i];
                    row.currencyId = array[i + 1];
                    row.amount = array[i + 2];
                    rows.push(row)
                    jsonArray.push(rows);
                }
                recordJson.total = total;
                recordJson.rows = rows;

                $('#preview-dlg').dialog({
                    title: '预览详情',
                    width: 800,
                    height: 400,
                    closed: false,
                    cache: false
                });

                $('#detail-datagrid').datagrid({
                        data: recordJson,
                        fit: true,
                        fitColumns: true,
                        singleSelect: true,
                        pagination: false,
                        rownumbers: true,
                        pageSize: 10,
                        columns: [[
                            {
                                field: 'userId',
                                title: '用户名',
                                width: 100,
                                formatter: function (value) {
                                    $.get(CurrencyAwardMVC.Util.getKycDetail(value), "", function (_datax) {
                                        var kycData = _datax.data.rows[0];
                                        if (kycData) {
                                            CurrencyAwardMVC.Model.kyc = kycData.country;
                                        }
                                    });
                                    return value;
                                }
                            },
                            {
                                field: 'locale',
                                title: '地区',
                                width: 100,
                                formatter: function (value, rows, index) {
                                    var country = CurrencyAwardMVC.Model.kyc;
                                    return CurrencyAwardMVC.Util.getCountryStr(country);
                                }
                            },
                            {
                                field: 'currencyId',
                                title: '币种',
                                width: 100,
                            },
                            {
                                field: 'amount',
                                title: '数量',
                                width: 100,
                                align: 'right'
                            }
                        ]]
                    }
                )
                ;


                $.get(CurrencyAwardMVC.Util.getActivityDetail(), "", function (_datax) {
                    var previewData = _datax.data;
                    $('#activity-code').textbox('setValue', previewData.rows[0].code);
                    $('#activity-sms-code').textbox('setValue', previewData.rows[0].smsCode);
                    $('#activity-mail-code').textbox('setValue', previewData.rows[0].mailCode);
                });

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
                        $("#okBtn").linkbutton('disable');
                        var validator = $('#currencyAward-form').form('validate');
                        if (validator) {
                            $.messager.progress();
                        }
                        options.url = CurrencyAwardMVC.URLs.add.url;
                    } else {
                        $.messager.alert('警告', '请使用英文标点符号');
                    }
                }
                //修改后刷新列表
                $.messager.progress('close');
                EasyUIUtils.save(options);

                return;
            }
        },
        Util: {
            getActivityDetail: function () {
                var code = $("#code").textbox('getText');
                return CurrencyAwardMVC.URLs.list.url + '?code=' + code;

            },
            getKycDetail: function (userId) {
                return CurrencyAwardMVC.URLs.kyc.url + '?userProperty=1' + '&userValue=' + userId;
            },
            getCountryStr: function (country) {
                var countryStr = '';

                if (country == 156) {
                    countryStr = '国内';
                } else {
                    countryStr = '国际';
                }
                return countryStr;
            },
            getOptions: function () {
                return {
                    dlgId: '#currencyAward-dlg',
                    formId: '#currencyAward-form',
                    actId: '#modal-action',
                    rowId: '#roleId',
                    title: '',
                    iconCls: 'icon-add',
                    data: {},
                    callback: function (arg) {
                    },
                    url: null,
                    gridId: '#currencyAward-datagrid',
                    gridUrl: CurrencyAwardMVC.URLs.detail.url
                };
            }
            ,
            getQueryParams: function () {
                var actId = $("#activity-id").textbox('getValue');
                var userId = $("#user-id").textbox('getValue');
                var currencyId = $("#currency-code").combobox('getValue');
                var params = {
                    actId: actId,
                    userId: userId,
                    currencyId: currencyId
                };

                return params;
            }
        }

    }
;