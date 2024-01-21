$(function () {
    OrderMaDataDs.init();
});

var OrderMaDataDs = {
    init: function () {
        OrderMaMVC.View.initControl();
        OrderMaMVC.View.bindEvent();
        OrderMaMVC.View.bindValidate();
        OrderMaMVC.View.initData();
    }
};

var DsCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/spot/ordermanage/',
    basecomboxUrl: BossGlobal.ctxPath + '/v1/boss/spot/billmanage/',
    keys: {
        dbType: 'dbType',
        dbPoolType: 'dbPoolType'
    }
};

var OrderMaMVC = {
    URLs: {

        orderlist: {
            url: DsCommon.baseUrl + 'order-all',
            method: 'get'
        },

        getcode: {
            url: DsCommon.basecomboxUrl + 'getCurrencyCode',
            method: 'GET'
        },
        testConnectionById: {
            url: DsCommon.baseUrl + 'testConnectionById',
            method: 'POST'
        }
    },
    Model: {
        dbTypes: {},
        dbPoolTypes: {}
    },
    View: {
        initControl: function () {
            $.get(OrderMaMVC.URLs.getcode.url, "", function (_data) {
                $("#order-currencyCode").combobox({
                    data: _data.data,
                    valueField: "code",
                    textField:"code",
                    panelHeight: 300,
                    editable: true,
                    onSelect: function (record) {
                        $.tabIndex = $('.order-currencyCode').index(this);
                        $(".order-currencyCode").eq($.tabIndex).textbox("setValue", record.code)
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

            $('#order-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: OrderMaMVC.URLs.orderlist.url,

                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#order-datagrid');
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
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return OrderMaMVC.Controller.showDetail();
                }
            });
            // dialogs
            $('#order-dlg').dialog({
                closed: true,
                modal: false,
                width: 650,
                title:"订单详情",
                height: 450,
                iconCls: 'icon-info',
                buttons: []
            });

        },
        bindEvent: function () {
            $('#order-search').bind('click', OrderMaMVC.Controller.find);
        },
        bindValidate: function () {


        },
        initData: function () {
            OrderMaMVC.Util.loadConfigItems();
        }
    },
    Controller: {

        showDetail: function () {
            OrderMaMVC.Util.isRowSelected(function (row) {
                $('#order-dlg').dialog('open').dialog('center');
                //根据条件调用接口返回的数据
                // alert(row.id);
                OrderMaMVC.Util.fillDetailLabels(row);
            });
        },
        find: function () {
            var userid = "10120655";
            var code = $("#order-currencyCode").textbox('getValue');
            var startDateMillis = $("#order-startDateMillis").textbox('getValue');
            var endDateMillis = $("#order-endDateMillis").textbox('getValue');
            var price = $("#order-price").textbox('getValue');
            var type = $("#order-type").combobox('getValue');
            var source = $("#order-source").combobox('getValue');
            var url = OrderMaMVC.URLs.orderlist.url + '?code=' + code + '&startDateMillis=' + startDateMillis + '&endDateMillis=' + endDateMillis + '&type=' + type+ '&source=' + source+ '&userId=' + userid+ '&price=' + price;
            EasyUIUtils.loadToDatagrid('#order-datagrid', url)

        }
    },
    Util: {
        fillDetailLabels: function (data) {
            $("#spot_order_userid").text(data.userId);
            $("#spot_order_createdata").text(moment(new Date(data.createdDate)).format("YYYY-MM-DD HH:mm:ss"));
            if(data.orderType=="limit"){
                $("#spot_order_orderType").text("限价单");
            }else{
                $("#spot_order_orderType").text("市价单");
            }
            $("#spot_order_price").text(data.price);
            $("#spot_order_code").text(data.code);
            $("#spot_order_volume").text(data.volume);
            $("#spot_order_source").text(data.source);
            $("#spot_order_filledVolume").text(data.filledVolume);
            $("#spot_order_averagePrice").text(data.averagePrice);
            if(data.status=="open"){
                $("#spot_order_status").text("未成交");
            }else if(data.status=="partially-filled"){
                $("#spot_order_status").text("部分成交");
            }else if(data.status=="filled"){
                $("#spot_order_status").text("完全成交");
            }else if(data.status=="cancel"){
                $("#spot_order_status").text("撤单中");
            }else if(data.status=="canceled"){
                $("#spot_order_status").text("已撤单");
            }
        },
        isRowSelected: function (func) {
            var row = $('#order-datagrid').datagrid('getSelected');
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
                var key = OrderMaMVC.Util.findKey(map, fieldName, value);
                $(id).combobox('setValues', key);
            }
        },
        loadConfigItems: function () {
            $.getJSON(OrderMaMVC.URLs.getConfItems.url + "?key=" + DsCommon.keys.dbType, function (result) {
                OrderMaMVC.Util.toMap(OrderMaMVC.Model.dbTypes, result.data);
            });
            $.getJSON(OrderMaMVC.URLs.getConfItems.url + "?key=" + DsCommon.keys.dbPoolType, function (result) {
                OrderMaMVC.Util.toMap(OrderMaMVC.Model.dbPoolTypes, result.data);
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

