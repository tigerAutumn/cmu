$(function () {
    MessSendDs.init();
});

var MessSendDs = {
    init: function () {
        MessSendMVC.View.initControl();
        MessSendMVC.View.bindEvent();
        MessSendMVC.View.bindValidate();
        MessSendMVC.View.initData();
    }
};

var DsCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/integration/message-send/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    keys: {
        dbType: 'dbType',
        dbPoolType: 'dbPoolType'
    }
};

var MessSendMVC = {
    URLs: {

        list: {
            url: DsCommon.baseUrl + 'list',
            method: 'GET'
        },

        OrderDetail: {
            url: DsCommon.baseUrl + 'detail',
            method: 'GET'
        }
    },
    Model: {
        dbTypes: {},
        dbPoolTypes: {}
    },
    View: {
        initControl: function () {
            $('#ds-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: MessSendMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        $("#ds-datagrid").datagrid({
                            pageNumber: 1,
                            pageSize: 50,
                            url: MessSendMVC.URLs.list.url
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
                    field: 'msgId',
                    title: '信息id',
                    width: 100,
                    sortable: true
                }, {
                    field: 'channel',
                    title: '发送渠道代号',
                    width: 50,
                    sortable: true
                }, {
                    field: 'type',
                    title: '信息类型',
                    width: 100,
                    sortable: true
                }, {
                    field: 'region',
                    title: '服务地区',
                    width: 100,
                    sortable: true
                }, {
                    field: 'status',
                    title: '是否成功',
                    width: 50,
                    sortable: true,
                    formatter: function (value, row, index) {

                        if (value == 0) {
                            return "失败";
                        } else {
                            return "成功";
                        }
                    }
                }, {
                    field: 'createdTime',
                    title: '创建时间',
                    width: 50,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return MessSendMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            var pager = $("#ds-datagrid").datagrid("getPager");
            if (pager) {
                $(pager).pagination({
                    onSelectPage: function (pageNumber, pageSize) {

                        var url = MessSendMVC.Util.getSearchUrl();
                        $("#ds-datagrid").datagrid({
                            pageNumber: pageNumber,
                            pageSize: pageSize,
                            url: url
                        });

                    }
                });
            }

            $('#dbType').combobox({
                onChange: function (newValue, oldValue) {
                    var item = MessSendMVC.Model.dbTypes[newValue].value;
                    $('#jdbcUrl').textbox('setValue', item.jdbcUrl);
                    $('#driverClass').val(item.driverClass);
                    $('#queryerClass').val(item.queryerClass);
                }
            });

            $('#dbPoolType').combobox({
                onChange: function (newValue, oldValue) {
                    var item = MessSendMVC.Model.dbPoolTypes[newValue].value;
                    $('#poolClass').val(item.poolClass);
                    var data = EasyUIUtils.toPropertygridRows(item.options);
                    $('#ds-options-pg').propertygrid('loadData', data);
                }
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', MessSendMVC.Controller.find);
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
                return MessSendMVC.Controller.edit();
            }
            if (name === "remove") {
                return MessSendMVC.Controller.remove();
            }
        },
        find: function () {

            var url = MessSendMVC.Util.getSearchUrl();
            $("#ds-datagrid").datagrid({
                pageNumber: 1,
                pageSize: 50,
                url: url
            });
        }
    },
    Util: {
        getSearchUrl: function () {

            var channel = $("#channel").textbox('getValue');
            var status = $("#status").combobox('getValue');
            var type = $("#type").combobox('getValue');
            return MessSendMVC.URLs.list.url + '?channel=' + channel + '&status=' + status + '&type=' + type;

        },

        isRowSelected: function (func) {
            var row = $('#ds-datagrid').datagrid('getSelected');
            if (row) {
                func(row);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
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
