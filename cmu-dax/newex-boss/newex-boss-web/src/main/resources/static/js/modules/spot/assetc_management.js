$(function () {
    AssetcDataDs.init();
});

var AssetcDataDs = {
    init: function () {
        AssetcMVC.View.initControl();
        AssetcMVC.View.bindEvent();
        AssetcMVC.View.bindValidate();
        AssetcMVC.View.initData();
    }
};

var DsCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/spot/assetcmanage/',

    keys: {
        dbType: 'dbType',
        dbPoolType: 'dbPoolType'
    }
};

var AssetcMVC = {
    URLs: {

        list: {
            url: DsCommon.baseUrl + 'list',
            method: 'get'
        },
        testConnectionById: {
            url: DsCommon.baseUrl + 'testConnectionById',
            method: 'POST'
        }
    },
    Model: {

    },
    View: {
        initControl: function () {


            $('#asse-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: AssetcMVC.URLs.list.url,

                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#asse-datagrid');
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
                    width: 200,
                    sortable: true
                },{
                    field: 'sum',
                    title: '余额',
                    width: 200,
                    sortable: true,
                    formatter: function (value, row, index) {
                        value = parseFloat(row.available) + parseFloat(row.hold);
                        return value;
                    }
                },{
                    field: 'available',
                    title: '可用',
                    width: 80,
                    sortable: true
                },{
                    field: 'hold',
                    title: '冻结',
                    width: 80,
                    sortable: true
                },{
                    field: 'BTC',
                    title: 'BTC估值',
                    width: 80,
                    sortable: true
                },{
                    field: 'lock',
                    title: '锁仓',
                    width: 80,
                    sortable: true
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return AssetcMVC.Controller.edit();
                }
            });

            $('#dbPoolType').combobox({
                onChange: function (newValue, oldValue) {
                    var item = AssetcMVC.Model.dbPoolTypes[newValue].value;
                    $('#poolClass').val(item.poolClass);
                    var data = EasyUIUtils.toPropertygridRows(item.options);
                    $('#ds-options-pg').propertygrid('loadData', data);
                }
            });

            $('#ds-options-pg').propertygrid({
                scrollbarSize: 0,
                height: 200,
                columns: [[
                    {field: 'name', title: '配置项', width: 200, sortable: true},
                    {field: 'value', title: '配置值', width: 100, resizable: false}
                ]]
            });
        },
        bindEvent: function () {
            $('#asse-search').bind('click', AssetcMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {
            AssetcMVC.Util.loadConfigItems();
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#asse-datagrid').datagrid('selectRow', index);

        },
        find: function () {

            var userid = $("#userid").textbox('getValue');

            var url = AssetcMVC.URLs.list.url + '?userid=' + userid;
            EasyUIUtils.loadToDatagrid('#asse-datagrid', url)

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
                var key = AssetcMVC.Util.findKey(map, fieldName, value);
                $(id).combobox('setValues', key);
            }
        },
        loadConfigItems: function () {
            $.getJSON(AssetcMVC.URLs.getConfItems.url + "?key=" + DsCommon.keys.dbType, function (result) {
                AssetcMVC.Util.toMap(AssetcMVC.Model.dbTypes, result.data);
            });
            $.getJSON(AssetcMVC.URLs.getConfItems.url + "?key=" + DsCommon.keys.dbPoolType, function (result) {
                AssetcMVC.Util.toMap(AssetcMVC.Model.dbPoolTypes, result.data);
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
