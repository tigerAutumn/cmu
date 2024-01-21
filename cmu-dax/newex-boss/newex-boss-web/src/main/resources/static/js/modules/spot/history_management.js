$(function () {
    historyMaDataDs.init();
});

var historyMaDataDs = {
    init: function () {
        HistoryMaMVC.View.initControl();
        HistoryMaMVC.View.bindEvent();
        HistoryMaMVC.View.bindValidate();
        HistoryMaMVC.View.initData();
    }
};

var DsCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/spot/ordermanage/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    basecomboxUrl: BossGlobal.ctxPath + '/v1/boss/spot/billmanage/',
    keys: {
        dbType: 'dbType',
        dbPoolType: 'dbPoolType'
    }
};

var HistoryMaMVC = {
    URLs: {

        list: {
            url: DsCommon.baseUrl + 'finish',
            method: 'get'
        },
        comboxcode: {
            url: DsCommon.basecomboxUrl + 'getCurrencyCode',
            method: 'GET'
        }
    },
    Model: {
        dbTypes: {},
        dbPoolTypes: {}
    },
    View: {
        initControl: function () {

            $.get(HistoryMaMVC.URLs.comboxcode.url, "", function (_data) {
                $("#his-currencyCode").combobox({
                    data: _data.data,
                    valueField: "code",
                    textField: "code",
                    panelHeight: 300,
                    editable: true,
                    onSelect: function (record) {
                        $.tabIndex = $('.his-currencyCode').index(this);
                        $(".his-currencyCode").eq($.tabIndex).textbox("setValue", record.code)
                    },
                    onLoadSuccess: function () { //加载完成后,设置选中第一项
                        var val = $(this).combobox("getData");
                        for (var item in val[0]) {
                            if (item == "code") {
                                $(this).combobox("select", val[0][item]);
                            }
                        }
                    }
                });
            });

            $('#his-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: HistoryMaMVC.URLs.list.url + '?code=' + "btc_usdt" + '&isHistory=' + "true" + '&userId=' + "10120655",

                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#his-datagrid');
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
                    field: 'createdDate',
                    title: '时间',
                    width: 200,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                    }
                },{
                    field: 'code',
                    title: '币对',
                    width: 200,
                    sortable: true
                },{
                    field: 'orderType',
                    title: '类型',
                    width: 80,
                    sortable: true,
                    formatter: function (value, row, index) {

                        if(value=='market'){
                            return "市价单";
                        }else{
                            return "限价单";
                        }
                    }
                },{
                    field: 'price',
                    title: '委托价',
                    width: 80,
                    sortable: true,
                },{
                    field: 'volume',
                    title: '委托量',
                    width: 100,
                    sortable: true
                },{
                    field: 'source',
                    title: '订单来源',
                    width: 100,
                    sortable: true
                },{
                    field: 'status',
                    title: '状态',
                    width: 80,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if(value=="open"){
                            return "未成交";
                        }else if(value=="partially-filled"){
                            return "部分成交";
                        }else if(value=="filled"){
                            return "完全成交";
                        }else if(value=="cancel"){
                            return "撤单中";
                        }else if(value=="canceled"){
                            return "已撤单";
                        }
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return HistoryMaMVC.Controller.hisshowDetail();
                }
            });

            // dialogs
            $('#history-dlg').dialog({
                closed: true,
                modal: false,
                width: 650,
                title:"历史委托订单详情",
                height: 450,
                iconCls: 'icon-info',
                buttons: []
            });


        },
        bindEvent: function () {
            $('#btn-search').bind('click', HistoryMaMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {
            HistoryMaMVC.Util.loadConfigItems();
        }
    },
    Controller: {
        hisshowDetail: function () {
            HistoryMaMVC.Util.isHisRowSelected(function (row) {
                $('#history-dlg').dialog('open').dialog('center');
                HistoryMaMVC.Util.HisfillDetailLabels(row);
            });
        },
        find: function () {

            var code = $("#his-currencyCode").textbox('getValue');
            var price = $("#his-price").textbox('getValue');
            var userid = 1099;
            var startDateMillis = $("#his_startDateMillis").textbox('getValue');
            var endDateMillis = $("#his_endDateMillis").textbox('getValue');
            var type = $("#his_type").combobox('getValue');
            var isHistory = $("#order-history").combobox('getValue');
            var source = $("#his_source").combobox('getValue');
            var price = $("#his-price").textbox('getValue');
            var url = HistoryMaMVC.URLs.list.url + '?code=' + code + '&startDateMillis=' + startDateMillis + '&endDateMillis=' + endDateMillis + '&type=' + type+ '&source=' + source+ '&userId=' + userid+ '&price=' + price+ '&isHistory=' + isHistory;
            EasyUIUtils.loadToDatagrid('#his-datagrid', url)

        }
    },
    Util: {

        HisfillDetailLabels: function (data) {


            $("#spot_his_userid").text(data.userId);
            $("#spot_his_createdata").text(moment(new Date(data.createdDate)).format("YYYY-MM-DD HH:mm:ss"));

            $("#spot_his_code").text( data.code);
            if(data.orderType=="limit"){
                $("#spot_his_orderType").text("限价单");
            }else{
                $("#spot_his_orderType").text("市价单");
            }
            $("#spot_his_price").text( data.price);
            $("#spot_his_volume").text(data.volume);
            $("#spot_his_source").text( data.source);
            $("#spot_his_averagePrice").text(data.averagePrice);
            if(data.status=="open"){
                $("#spot_his_status").text("未成交");
            }else if(data.status=="partially-filled"){
                $("#spot_his_status").text( "部分成交");
            }else if(data.status=="filled"){
                $("#spot_his_status").text("完全成交");
            }else if(data.status=="cancel"){
                $("#spot_his_status").text( "撤单中");
            }else if(data.status=="canceled"){
                $("#spot_his_status").text( "已撤单");
            }
            $("#spot_his_filledVolume").text(data.filledVolume);



        },

        isHisRowSelected: function (func) {
            var row = $('#his-datagrid').datagrid('getSelected');
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
                var key = HistoryMaMVC.Util.findKey(map, fieldName, value);
                $(id).combobox('setValues', key);
            }
        },
        loadConfigItems: function () {
            $.getJSON(HistoryMaMVC.URLs.getConfItems.url + "?key=" + DsCommon.keys.dbType, function (result) {
                HistoryMaMVC.Util.toMap(HistoryMaMVC.Model.dbTypes, result.data);
            });
            $.getJSON(HistoryMaMVC.URLs.getConfItems.url + "?key=" + DsCommon.keys.dbPoolType, function (result) {
                HistoryMaMVC.Util.toMap(HistoryMaMVC.Model.dbPoolTypes, result.data);
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
