$(function () {
    C2CCurrency.init();
});

var C2CCurrency = {
    init: function () {
        C2CCurrencyMVC.View.initControl();
        C2CCurrencyMVC.View.bindEvent();
        C2CCurrencyMVC.View.bindValidate();
    }
};

var C2CCurrencyCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/asset/currency/c2c/',
    imageUrl: BossGlobal.ctxPath + '/v1/boss/upload/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/'
};

var C2CCurrencyMVC = {
    URLs: {
        add: {
            url: C2CCurrencyCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: C2CCurrencyCommon.baseUrl + 'edit',
            method: 'POST'
        },
        remove: {
            url: C2CCurrencyCommon.baseUrl + 'remove',
            method: 'POST'
        },
        list: {
            url: C2CCurrencyCommon.baseUrl + 'list',
            method: 'GET'
        },
        getBrokerList: {
            url: C2CCurrencyCommon.brokerBaseUrl + 'list',
            method: 'GET'
        }
    },
    Model: {},
    View: {
        initControl: function () {

            $('#biz').combobox({
                readonly: true,
                valueField: 'id',
                textField: 'value',
                data: [
                    {
                        id: 'spot',
                        value: '币币'
                    }, {
                        id: 'c2c',
                        value: '法币'
                    }, {
                        id: 'contract',
                        value: '合约'
                    }, {
                        id: 'portfolio',
                        value: '组合'
                    }
                ]
            });

            $("#brokerId").combobox({
                url: C2CCurrencyMVC.URLs.getBrokerList.url,
                method: 'get',
                valueField: "id",
                textField: "brokerName",
                editable: true,
                value: '',
                prompt: '选择券商',
                panelHeight: 300
            });

            $('#currency-online').combobox({
                valueField: 'id',
                textField: 'value',
                data: [
                    {
                        id: '-1',
                        value: '全部'
                    }, {
                        id: '0',
                        value: '下线'
                    }, {
                        id: '1',
                        value: '上线'
                    }, {
                        id: '2',
                        value: '预发'
                    }
                ],
                onLoadSuccess: function () {
                    $('#currency-online').combobox('setValue', '-1');
                }
            });

            $('#online').combobox({
                valueField: 'id',
                textField: 'value',
                data: [
                    {
                        id: '0',
                        value: '下线'
                    }, {
                        id: '1',
                        value: '上线'
                    }, {
                        id: '2',
                        value: '预发'
                    }
                ]
            });

            $('#currency-transfer').combobox({
                valueField: 'id',
                textField: 'value',
                data: [
                    {
                        id: '-1',
                        value: '全部'
                    }, {
                        id: '1',
                        value: '是'
                    }, {
                        id: '0',
                        value: '否'
                    }
                ],
                onLoadSuccess: function () {
                    $('#currency-transfer').combobox('setValue', '-1');
                }
            });

            $('#transfer').combobox({
                valueField: 'id',
                textField: 'value',
                data: [
                    {
                        id: '1',
                        value: '是'
                    }, {
                        id: '0',
                        value: '否'
                    }
                ]
            });

            $('#isAssetVisible').combobox({
                valueField: 'id',
                textField: 'value',
                data: [
                    {
                        id: '1',
                        value: '是'
                    }, {
                        id: '0',
                        value: '否'
                    }
                ]
            });

            $('#type').combobox({
                valueField: 'id',
                textField: 'value',
                data: [
                    {
                        id: '0',
                        value: '数字货币'
                    }, {
                        id: '1',
                        value: '法币'
                    }
                ]
            });

            $('#zone').combobox({
                valueField: 'id',
                textField: 'value',
                data: [
                    {
                        id: '1',
                        value: '主板块'
                    }, {
                        id: '2',
                        value: '创新板'
                    }, {
                        id: '3',
                        value: '币创板'
                    }
                ]
            });

            $('#currency-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: C2CCurrencyMVC.URLs.list.url,
                toolbar: [{
                    text: '新增',
                    iconCls: 'icon-add',
                    handler: function () {
                        C2CCurrencyMVC.Controller.add();
                    }
                }, '-', {
                    text: '编辑',
                    iconCls: 'icon-edit',
                    handler: function () {
                        C2CCurrencyMVC.Controller.edit();
                    }
                }, '-', {
                    text: '所有数据',
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#currency-datagrid').datagrid('load', {});
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
                    title: 'ID',
                    width: 50,
                    sortable: true
                }, {
                    field: 'brokerId',
                    title: '券商',
                    width: 150,
                    sortable: true
                }, {
                    field: 'symbol',
                    title: '币种简称',
                    width: 150,
                    sortable: true
                }, {
                    field: 'fullName',
                    title: '币种全称',
                    width: 150,
                    sortable: true
                }, {
                    field: 'sign',
                    title: '币种符号',
                    width: 150,
                    sortable: true
                }, {
                    field: 'issuePrice',
                    title: '发行价格',
                    width: 150,
                    sortable: true
                }, {
                    field: 'online',
                    title: '是否上线',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value === 1) {
                            return "上线";
                        }
                        else if (value === 2) {
                            return "预发";
                        }
                        else if (value === 0) {
                            return "下线";
                        }
                        else {
                            return value;
                        }
                    }
                }, {
                    field: 'zone',
                    title: '上线板块',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value == 1) {
                            return "主板块";
                        } else if (value == 2) {
                            return "创新板";
                        } else if (value == 3) {
                            return "币创板";
                        } else {
                            return "";
                        }
                    }
                }, {
                    field: 'transfer',
                    title: '可否划转',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return value === 1 ? "是" : "否";
                    }
                }, {
                    field: 'type',
                    title: '币种类型',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return value === 1 ? "法币" : "数字货币";
                    }
                }, {
                    field: 'sort',
                    title: '排序',
                    width: 150,
                    sortable: true
                }, {
                    field: 'createdDate',
                    title: '创建时间',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value) {
                            var ds = moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss");
                            row.createdDate = ds;

                            return ds;
                        }

                        return '';
                    }
                }, {
                    field: 'options',
                    title: '操作',
                    width: 100,
                    formatter: function (value, row, index) {
                        var icons = [{
                            "name": "edit",
                            "title": "编辑"
                        }];

                        var buttons = [];
                        for (var i = 0; i < icons.length; i++) {
                            var tmpl = '<a href="#" title ="${title}" '
                                + 'onclick="C2CCurrencyMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: C2CCurrencyCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return C2CCurrencyMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // dialogs
            $('#currency-dlg').dialog({
                closed: true,
                modal: false,
                width: 1100,
                height: window.innerHeight - 100 || document.documentElement.clientHeight - 100 || document.body.clientHeight - 100,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#currency-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: C2CCurrencyMVC.Controller.save
                }]
            });

        },
        bindEvent: function () {
            $('#btn-search').bind('click', C2CCurrencyMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#currency-datagrid').datagrid('selectRow', index);

            if (name === "edit") {
                return C2CCurrencyMVC.Controller.edit();
            }
        },
        add: function () {
            var options = C2CCurrencyMVC.Util.getOptions();
            options.title = '新增法币币种';
            EasyUIUtils.openAddDlg(options);

            $('#biz').combobox('select', 'c2c');
            $('#online').combobox('select', '1');
            $('#transfer').combobox('select', '1');
            $('#isAssetVisible').combobox('select', '1');
            $('#type').combobox('select', '0');
            $('#zone').combobox('select', '1');
            $("#brokerId").combobox('readonly', false);
        },
        edit: function () {
            var row = $('#currency-datagrid').datagrid('getSelected');
            if (row) {
                var options = C2CCurrencyMVC.Util.getOptions();

                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改法币币种[' + options.data.name + ']';

                $("#brokerId").combobox('readonly', true);

                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        find: function () {
            var params = C2CCurrencyMVC.Util.getQueryParams();

            $('#currency-datagrid').datagrid('load', params);
        },
        save: function () {
            var action = $('#modal-action').val();

            var options = C2CCurrencyMVC.Util.getOptions();

            if (action === "edit") {
                var row = $('#currency-datagrid').datagrid('getSelected');
                //取得ID传给后台
                options.url = C2CCurrencyMVC.URLs.edit.url + '?id=' + row.id;
            }
            else {
                //添加操作
                options.url = C2CCurrencyMVC.URLs.add.url;
            }

            //修改后刷新列表
            EasyUIUtils.save(options);

            return;
        }

    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#currency-dlg',
                formId: '#currency-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                url: null,
                gridId: '#currency-datagrid',
                gridUrl: C2CCurrencyMVC.URLs.list.url
            };
        },
        getQueryParams: function () {
            var symbol = $('#currency-symbol').textbox('getValue');
            var transfer = $('#currency-transfer').combobox('getValue');
            var online = $('#currency-online').combobox('getValue');

            var params = {
                symbol: symbol,
                transfer: transfer,
                online: online
            };

            return params;
        }
    }
};