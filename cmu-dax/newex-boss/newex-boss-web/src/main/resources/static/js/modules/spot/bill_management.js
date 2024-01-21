$(function () {
    BillDataDs.init();
});

var BillDataDs = {
    init: function () {
        BillMVC.View.initControl();
        BillMVC.View.bindEvent();
        BillMVC.View.bindValidate();
        BillMVC.View.initData();
        var type
    }
};

var DsCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/spot/billmanage/',
    basecomboxUrl: BossGlobal.ctxPath + '/v1/boss/asset/currency/currencies',
    baseCommonUrl: BossGlobal.ctxPath + '/v1/boss/common/',
    keys: {
        dbType: 'dbType',
        dbPoolType: 'dbPoolType'
    }
};

var BillMVC = {
    URLs: {

        list: {
            url: DsCommon.baseUrl + 'bill-all',
            method: 'get'
        },
        combox: {
            url: DsCommon.baseUrl + 'getType',
            method: 'GET'
        },
        getcurrenname: {
            url: DsCommon.basecomboxUrl + '?findInstitution',
            method: 'post'
        },
        getadress: {
            url: DsCommon.baseUrl + 'getadress',
            method: 'get'
        },
        getSpotCurrencyList: {
            url: DsCommon.baseCommonUrl + 'spot/currency',
            method: 'GET'
        }
    },
    Model: {
        dbTypes: {},
        dbPoolTypes: {}
    },
    View: {
        initControl: function () {

            $.get(BillMVC.URLs.getSpotCurrencyList.url, "", function (_data) {
                $("#bill-currency-name").combobox({
                    data: _data,
                    valueField: "symbol",
                    textField: "symbol",
                    panelHeight: 300,
                    editable: true,
                    onSelect: function (record) {
                        $.tabIndex = $('.bill-currency-name').index(this);
                        $(".bill-currency-name").eq($.tabIndex).textbox("setValue", record.id)
                    },
                    onLoadSuccess: function () { //加载完成后,设置选中第一项
                        var val = $(this).combobox("getData");
                        for (var item in val[0]) {
                            if (item == "symbol") {
                                $(this).combobox("select", val[0][item]);
                            }
                        }
                    }

                });
            });


            $.get(BillMVC.URLs.combox.url, "", function (_data) {
                $("#bill-type").combobox({
                    data: _data.data,
                    valueField: "name",
                    textField: "name",
                    panelHeight: 300,
                    editable: true,
                    onSelect: function (record) {
                        $.tabIndex = $('.bill-type').index(this);
                        $(".bill-type").eq($.tabIndex).textbox("setValue", record.code)

                          type = record.code;
                    },
                    onLoadSuccess: function () { //加载完成后,设置选中第一项
                        var val = $(this).combobox("getData");
                        for (var item in val[0]) {
                            if (item == "name") {
                                $(this).combobox("select", val[0][item]);
                            }
                        }
                    }
                });
            });

            $('#bill-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: BillMVC.URLs.list.url+ '?userId=' +10000075 + '&isHiisHistorystory=' +"true",

                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#bill-datagrid');
                    }
                }],
                loadFilter: function (src) {
                    if (!src.code) {
                        return src.data;
                    }
                    return $.messager.alert('失败', src.msg, 'error');
                },

                columns: [[{
                    field: 'userId',
                    title: '用户ID',
                    width: 200,
                    sortable: true
                },{
                    field: 'createTime',
                    title: '时间',
                    width: 200,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                    }
                },{
                    field: 'currencyName',
                    title: '币种',
                    width: 200,
                    sortable: true
                },{
                    field: 'type',
                    title: '类型',
                    width: 80,
                    sortable: true,
                    formatter: function (value, row, index) {

                        if(value=='1'){
                            return "充值";
                        }else if(value=='2'){
                            return "提现";
                        }else if(value=='7'){
                            return "买入";
                        }else if(value=='8'){
                            return "卖出";
                        }else if(value=='14'){
                            return "活动派发";
                        }
                    }
                },{
                    field: 'size',
                    title: '金额',
                    width: 150,
                    sortable: true,
                },{
                    field: 'afterBalance',
                    title: '余额',
                    width: 150,
                    sortable: true
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return BillMVC.Controller.hisshowDetail();
                }
            });

            // dialogs
            $('#bill-dlg').dialog({
                closed: true,
                modal: false,
                width: 650,
                title:"账单详情",
                height: 450,
                iconCls: 'icon-info',
                buttons: []
            });

        },
        bindEvent: function () {
            $('#bill-search').bind('click', BillMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {
            BillMVC.Util.loadConfigItems();
        }
    },
    Controller: {
        hisshowDetail: function () {
            BillMVC.Util.isHisRowSelected(function (row) {
                $('#bill-dlg').dialog('open').dialog('center');
                BillMVC.Util.hisfillDetailLabels(row);
            });
        },

        find: function () {

            var userid = 10000075;
            var currencyCode = $("#bill-currency-name").textbox('getValue');
            var startDateMillis = $("#bill-startDateMillis").textbox('getValue');
            var endDateMillis = $("#bill-endDateMillis").textbox('getValue');
            var isHiisHistorystory = $("#bill-history").combobox('getValue');
            var url = BillMVC.URLs.list.url + '?startDateMillis=' + startDateMillis + '&endDateMillis=' + endDateMillis + '&type=' + type + '&currencyCode=' + currencyCode+ '&userId=' + userid+ '&isHiisHistorystory=' + isHiisHistorystory;
            EasyUIUtils.loadToDatagrid('#bill-datagrid', url)

        }
    },
    Util: {
        hisfillDetailLabels: function (data) {

            $("#spot_bill_userid").text(data.userId);
            $("#spot_bill_createTime").text(moment(new Date(data.createTime)).format("YYYY-MM-DD HH:mm:ss"));
            $("#spot_bill_code").text(data.code);
            if(data.type=="1"){
                $("#spot_bill_billType").text("充值");
            }else if(data.type=="1"){
                $("#spot_bill_billType").text("提现");
            }else if(data.type=="2"){
                $("#spot_bill_billType").text("市价单");
            }else if(data.type=="7"){
                $("#spot_bill_billType").text("买入");
            }else if(data.type=="8"){
                $("#spot_bill_billType").text("卖出");
            }else if(data.type=="14"){
                $("#spot_bill_billType").text("活动派发");
            }
            $("#spot_bill_currency").text( data.currencyName);
            $("#spot_bill_size").text( data.size);
            $("#spot_bill_fee").text( data.fee);
            $("#spot_bill_afterBalance").text( data.afterBalance);

            if (data.tradeNo == "") {
                $.messager.alert('提示', '没有找到TXID', 'info');
                return;
            }
            var url = BillMVC.URLs.getadress.url + "?traderNo=" + data.tradeNo;
            $.ajax({
                url: url,
                type: 'GET',
                data: '',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (data) {
                    $("#spot_bill_adress").text( data.data.to);
                    $("#spot_bill_txid").text(data.data.traderNo);
                }
            });
        },

        isHisRowSelected: function (func) {
            var row = $('#bill-datagrid').datagrid('getSelected');
            if (row) {
                func(row);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },

        fillCombox: function (id, act, map, fieldName, value) {
            $(id).combobox('clear');
            var data = [];
            var i = 0;
            for (var key in map) {
                var item = map[key];
                data.push({
                    "value": item.key,
                    "name": item.name,
                    "selected": i === 0
                });
                i++;
            }
            $(id).combobox('loadData', data);
            if (act === "edit") {
                var key = BillMVC.Util.findKey(map, fieldName, value);
                $(id).combobox('setValues', key);
            }
        },
        loadConfigItems: function () {
            $.getJSON(BillMVC.URLs.getConfItems.url + "?key=" + DsCommon.keys.dbType, function (result) {
                BillMVC.Util.toMap(BillMVC.Model.dbTypes, result.data);
            });
            $.getJSON(BillMVC.URLs.getConfItems.url + "?key=" + DsCommon.keys.dbPoolType, function (result) {
                BillMVC.Util.toMap(BillMVC.Model.dbPoolTypes, result.data);
            });
        },
        toMap: function (srcMap, data) {
            if (!data || data.length === 0) return {};
            for (var i = 0; i < data.length; i++) {
                var item = data[i];
                item.value = $.toJSON(item.value);
                srcMap[item.key] = item;
            }
            return srcMap;
        },
        findKey: function (map, fieldName, value) {
            for (var key in map) {
                if (value === map[key].value[fieldName]) {
                    return key;
                }
            }
            return "";
        }
    }
};
