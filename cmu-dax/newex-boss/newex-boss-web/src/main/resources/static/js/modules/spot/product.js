$(function () {
    MembershipUser.init();
});

var MembershipUser = {
    init: function () {
        ProductMVC.View.initControl();
        ProductMVC.View.bindEvent();
        ProductMVC.View.bindValidate();
    }
};

var ProductCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/spot/product/',
    baseRoleUrl: BossGlobal.ctxPath + '/v1/boss/spot/product/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    basecomboxUrl: BossGlobal.ctxPath + '/v1/boss/common/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/'
};

var ProductMVC = {
    URLs: {
        add: {
            url: ProductCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: ProductCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: ProductCommon.baseUrl + 'searchlist',
            method: 'GET'
        },
        currency: {
            url: ProductCommon.basecomboxUrl + 'spot/currency',
            method: 'GET'
        },
        editTag: {
            url: ProductCommon.baseUrl + 'editProductTag',
            method: 'POST'
        },
        getTagTree: {
            url: ProductCommon.baseUrl + 'productTagTree',
            method: 'GET'
        },
        getBrokerIds: {
            url: ProductCommon.brokerBaseUrl + 'list'
        },
        batchCancel: {
            url: ProductCommon.baseUrl + 'batchCancel',
            method: 'POST'
        }
    },
    Model: {
        allCurrency: {},
        roles: {}
    },
    View: {
        initControl: function () {

            $("#brokerIds").tagbox({
                url: ProductMVC.URLs.getBrokerIds.url,
                method: 'get',
                valueField: 'id',
                textField: 'brokerName',
                limitToList: true,
                hasDownArrow: true,
                prompt: '选择券商'
            });

            $("#brokerId").combobox({
                url: ProductMVC.URLs.getBrokerIds.url,
                method: 'get',
                valueField: 'id',
                textField: 'brokerName',
                prompt: '选择券商'
            });

            $("#currency-name").combobox({
                url: ProductMVC.URLs.currency.url,
                method: 'get',
                valueField: "id",
                textField: "symbol",
                panelHeight: 300,
                editable: true
            });

            $.getJSON(ProductMVC.URLs.currency.url, "", function (_data) {
                ProductMVC.Model.allCurrency = _data;
                $("#baseCurrency").combobox({
                    data: _data,
                    valueField: "id",
                    textField: "symbol",
                    panelHeight: 300,
                    editable: true,
                    required: true,
                    onSelect: function (record) {
                        if (record.zone > 0) {
                            $('#zone').combobox('select', record.zone);
                        } else {
                            $('#zone').combobox('clear');
                        }
                        var currencies = ProductMVC.Model.allCurrency;
                        for (var i = 0; i < currencies.length; i++) {
                            if (currencies[i].id === record.id) {
                                $('#baseCurrencyCode').val(currencies[i].symbol);
                            }
                        }
                    }
                });
            });

            // data: [{label: '1',value: 'BTC'},{label: '2',value: 'ETH'},{label: '6',value: 'USDT'}]
            $('#quoteCurrency').combobox({
                valueField: 'id',
                textField: 'value',
                editable: true,
                required: true,
                data: [
                    {
                        id: '1',
                        value: 'BTC'
                    }, {
                        id: '2',
                        value: 'ETH'
                    }, {
                        id: '6',
                        value: 'USDT'
                    }, {
                        id: '35',
                        value: 'NEO'
                    }
                ],
                onChange: function (newVal, oldVal) {
                    var action = $('#modal-action').val();

                    if (action === "add") {
                        var value = 0;
                        if (newVal === '6') {
                            value = 4;
                            $('#mergeType').textbox('setValue', '0.1,0.01,0.001,0.0001');
                            $('#quoteIncrement').textbox('setValue', '0.0001');
                            $('#baseIncrement').textbox('setValue', '0.0001');
                        } else {
                            value = 8;
                            $('#mergeType').textbox('setValue', '0.01,0.0001,0.000001,0.00000001');
                            $('#quoteIncrement').textbox('setValue', '0.00000001');
                            $('#baseIncrement').textbox('setValue', '0.00000001');
                        }
                        $('#maxPriceDigit').textbox('setValue', value);
                        $('#maxSizeDigit').textbox('setValue', value);
                        $('#quotePrecision').textbox('setValue', value);
                    }

                },
                onSelect: function (record) {
                    $('#quoteCurrencyCode').val(record.value);
                }
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

            $('#status').combobox({
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
                ],
                onSelect: function (record) {
                    if (record.id == 1) {
                        $("#expireDate").datebox("readonly", false);
                    } else {
                        $("#expireDate").datebox("clear");
                        $("#expireDate").datebox("readonly", true);
                    }
                }
            });

            $('#expireDate').datebox({}).datebox('calendar').calendar({
                validator: function (date) {
                    var now = new Date();
                    var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate());

                    return date >= d1;
                }
            });

            $('#user-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: ProductMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        ProductMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        ProductMVC.Controller.edit();
                    }
                }, '-', {
                    text: '全部数据',
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#user-datagrid').datagrid('load', {});
                    }
                }, '-', {
                    text: '编辑标签',
                    iconCls: 'icon-edit1',
                    handler: function () {
                        ProductMVC.Controller.tag();
                    }
                }, '-', {
                    text: '批量撤单',
                    iconCls: 'icon-edit1',
                    handler: function () {
                        ProductMVC.Controller.batchCancel();
                    }
                }],
                loadFilter: function (src) {
                    if (!src.code) {
                        return src.data;
                    }
                    return $.messager.alert('失败', src.msg, 'error');
                },
                columns: [[
                    {
                        field: 'id',
                        title: '币对ID',
                        width: 100,
                        sortable: true
                    }, {
                        field: 'baseCurrency',
                        title: '交易货币',
                        width: 100,
                        sortable: true,
                        formatter: function (value, row, index) {
                            //调用后台下来，便利后进行显示
                            var currencies = ProductMVC.Model.allCurrency;
                            for (var i = 0; i < currencies.length; i++) {
                                if (currencies[i].id === value) {
                                    return currencies[i].symbol;
                                }
                            }
                        }
                    }, {
                        field: 'quoteCurrency',
                        title: '计价货币',
                        width: 100,
                        sortable: true,
                        formatter: function (value, row, index) {
                            var subOptions = [];
                            // 1:BTC,2:ETH,6:UDST
                            if (value === 1) {
                                return "BTC";
                            } else if (value === 2) {
                                return "ETH";
                            } else if (value === 6) {
                                return "USDT";
                            } else if (value === 35) {
                                return "NEO";
                            } else {
                                return value;
                            }
                        }
                    }, {
                        field: 'code',
                        title: '币对缩写',
                        width: 100,
                        sortable: true
                    }, {
                        field: 'minTradeSize',
                        title: '最小委托量',
                        width: 100,
                        sortable: true
                    }, {
                        field: 'maxPriceDigit',
                        title: '交易价格小数位数',
                        width: 150,
                        sortable: true
                    }, {
                        field: 'maxSizeDigit',
                        title: '交易数量小数位数:',
                        width: 150,
                        sortable: true
                    }, {
                        field: 'quotePrecision',
                        title: '计价货币数量单位精读',
                        width: 150,
                        sortable: true
                    }, {
                        field: 'quoteIncrement',
                        title: '交易价格最小变动单位',
                        width: 150,
                        sortable: true
                    }, {
                        field: 'baseIncrement',
                        title: '交易数量最小变动单位',
                        width: 150,
                        sortable: true
                    }, {
                        field: 'marketFrom',
                        title: '市场序号',
                        width: 100,
                        sortable: true
                    }, {
                        field: 'marginOpen',
                        title: '是否开启杠杆',
                        width: 150,
                        sortable: true,
                        formatter: function (value, row, index) {
                            if (value) {
                                return "开启";
                            } else {
                                return "关闭";
                            }
                        }
                    }, {
                        field: 'maxMarginLeverage',
                        title: '最大杠杆倍数',
                        width: 150,
                        sortable: true
                    }, {
                        field: 'marginRiskPreRatio',
                        title: '杠杆预爆仓风险系数',
                        width: 150,
                        sortable: true
                    }, {
                        field: 'marginRiskRatio',
                        title: '杠杆爆仓风险系数',
                        width: 150,
                        sortable: true
                    }, {
                        field: 'sort',
                        title: '排序',
                        width: 100,
                        sortable: true
                    }, {
                        field: 'brokerIds',
                        title: '券商',
                        hidden: true
                    }, {
                        field: 'online',
                        title: '是否已上线',
                        width: 100,
                        sortable: true,
                        formatter: function (value, row, index) {
                            if (value === 0) {
                                return "下线";
                            }
                            if (value === 1) {
                                return "上线";
                            }
                            if (value === 2) {
                                return "预发";
                            }
                        }
                    }, {
                        field: 'status',
                        title: '是否开启观察期',
                        width: 100,
                        sortable: true,
                        formatter: function (value, row, index) {
                            if (value === 0) {
                                return "否";
                            }
                            if (value === 1) {
                                return "是";
                            }
                            return "";
                        }
                    }, {
                        field: 'expireDate',
                        title: '观察期时间',
                        width: 100,
                        sortable: true,
                        formatter: function (value, row, index) {
                            if (value) {
                                row.expireDate = moment(new Date(value)).format("YYYY-MM-DD");

                                return row.expireDate;
                            } else {
                                return "";
                            }
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
                                    + 'onclick="ProductMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                    + '<img src="${imgSrc}" alt="${title}"/"></a>';
                                var data = {
                                    title: icons[i].title,
                                    name: icons[i].name,
                                    index: index,
                                    imgSrc: ProductCommon.baseIconUrl + icons[i].name + ".png"
                                };
                                buttons.push(juicer(tmpl, data));
                            }
                            return buttons.join(' ');
                        }
                    }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return ProductMVC.Controller.edit();
                },
                onLoadSuccess: function () {
                }
            });

            $('#user-dlg').dialog({
                closed: true,
                modal: false,
                width: 1100,
                height: window.innerHeight - 100 || document.documentElement.clientHeight - 100 || document.body.clientHeight - 100,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#user-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: ProductMVC.Controller.save
                }]
            });

            $('#batch-cancel-dlg').dialog({
                closed: true,
                modal: false,
                width: 500,
                height: 300,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#batch-cancel-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: ProductMVC.Controller.saveCancel
                }]
            });

            $('#currencyPair-tag-dlg').dialog({
                closed: true,
                modal: false,
                iconCls: 'icon-add',
                buttons: [
                    {
                        text: '修改',
                        iconCls: 'icon-edit1',
                        handler: function () {
                            var nodes = $('#tag-tree').tree('getChecked');
                            var tags = '';
                            for (var i = 0; i < nodes.length; i++) {
                                if (tags !== '') {
                                    tags += ',';
                                }
                                var node = nodes[i];

                                if (!$('#tag-tree').tree('isLeaf', node.target)) {
                                    continue;
                                }
                                var nodeParent = $('#tag-tree').tree('getParent', node.target);

                                var categoryId = nodeParent.id;
                                var tagId = node.id;
                                tags += categoryId + "/" + tagId;
                            }
                            ProductMVC.Controller.modifyTag(tags);
                        }
                    },
                    {
                        text: '返回',
                        iconCls: 'icon-back',
                        handler: function () {
                            $('#currencyPair-tag-dlg').dialog('close');
                            $('#user-datagrid').datagrid('load', {});
                        }
                    }
                ],
                onClose: function () {
                    $('#user-datagrid').datagrid('load', {});
                }
            });

        },
        bindEvent: function () {
            $('#btn-search').bind('click', ProductMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {
            ProductMVC.Util.loadRoleList();
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#user-datagrid').datagrid('selectRow', index);

            if (name === "edit") {
                return ProductMVC.Controller.edit();
            }
        },
        add: function () {
            var options = ProductMVC.Util.getOptions();
            options.title = '新增币对';
            EasyUIUtils.openAddDlg(options);

            $('#baseCurrency').combobox('readonly', false);
            $('#quoteCurrency').combobox('readonly', false);

            $('#lastPrice').textbox('readonly', false);
            $('#online').combobox('select', "1");
            $('#status').combobox('select', '0');
            ProductMVC.Util.fillText();
        },
        edit: function () {
            var row = $('#user-datagrid').datagrid('getSelected');

            $('#baseCurrency').combobox('readonly', true);
            $('#quoteCurrency').combobox('readonly', true);

            $('#lastPrice').textbox('readonly', true);  //设置输入框为禁用

            if (row) {
                var options = ProductMVC.Util.getOptions();
                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改币对[' + options.data.code + ']';
                EasyUIUtils.openEditDlg(options);
                ProductMVC.Util.fillRoleCombox1("edit", row);
                ProductMVC.Util.fillRoleCombox2("edit", row.mergeType);
            } else {
                $.messager.alert('警告', '请选中一个币对!', 'info');
            }
        },
        find: function () {
            var params = ProductMVC.Util.getQueryParams();

            $('#user-datagrid').datagrid('load', params);
        },
        save: function () {
            var action = $('#modal-action').val();

            var options = ProductMVC.Util.getOptions();

            if (action === "edit") {
                var row = $('#user-datagrid').datagrid('getSelected');
                //取得ID传给后台
                options.url = ProductMVC.URLs.edit.url + '?id=' + row.id;
            } else {
                options.url = ProductMVC.URLs.add.url;
            }

            //修改后刷新列表
            EasyUIUtils.save(options);
        },
        tag: function () {
            var row = $('#user-datagrid').datagrid('getSelected');
            if (row) {
                var title = row.code;
                $('#currencyPair-tag-dlg').dialog({iconCls: 'icon-edit1'})
                    .dialog('open')
                    .dialog('center')
                    .dialog('setTitle', title);

                $('#currencyPair-tag-tabs').tabs('select', 0);

                var code = row.code;
                var currencyId = row.baseCurrency;
                $("#currencyPair-code").val(code);
                $("#currencyPair-id").val(row.id);

                var currencies = ProductMVC.Model.allCurrency;
                for (var i = 0; i < currencies.length; i++) {
                    if (currencies[i].id === currencyId) {
                        var finalCurrencyId = currencies[i].symbol;
                        $("#currency-code").val(finalCurrencyId);
                    }
                }

                //获取当前币种的标签信息
                var url = ProductMVC.URLs.getTagTree.url + "?code=" + code;
                ProductMVC.Controller.showTreeTag(url);
            } else {
                $.messager.alert('警告', '请选中一个币对!', 'info');
            }
        },
        modifyTag: function (data) {
            var currencyPairCode = $("#currencyPair-code").val();
            var currencyPairId = $("#currencyPair-id").val();
            var currencyCode = $("#currency-code").val();

            //构建json数据
            var jsonData = {
                code: currencyPairCode,
                id: currencyPairId,
                tags: data,
                currencyCode: currencyCode
            };
            $.ajax({
                url: ProductMVC.URLs.editTag.url,
                type: 'POST',
                data: JSON.stringify(jsonData),
                async: false,
                cache: false,
                contentType: 'application/json',
                success: function (result) {
                    if (!result.code) {
                        $('#currencyPair-tag-dlg').dialog('close');
                    }
                }
            });
        },
        showTreeTag: function (url) {
            $.ajax({
                url: url,
                type: 'GET',
                data: '',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (data) {
                    if (!data.code) {
                        if (data.data && data.data.length > 0) {

                            $('#tag-tree').tree({
                                checkbox: true,
                                animate: true,
                                data: data.data
                            });
                        }

                    }
                }
            });
        },
        batchCancel: function () {
            var row = $('#user-datagrid').datagrid('getSelected');

            if (row) {
                var options = ProductMVC.Util.getCancelOptions();
                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '批量撤单[' + options.data.code + ']';

                EasyUIUtils.openEditDlg(options);

                $('#cancelCode').val(options.data.code);
            } else {
                $.messager.alert('警告', '请选中一个币对!', 'info');
            }
        },
        saveCancel: function () {
            var options = ProductMVC.Util.getCancelOptions();

            EasyUIUtils.save(options);
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
                url: null,
                gridId: '#user-datagrid',
                gridUrl: ProductMVC.URLs.list.url
            };
        },
        getCancelOptions: function () {
            return {
                dlgId: '#batch-cancel-dlg',
                formId: '#batch-cancel-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                url: ProductMVC.URLs.batchCancel.url,
                gridId: '#user-datagrid',
                gridUrl: ProductMVC.URLs.list.url
            };
        },
        getQueryParams: function () {
            var baseCurrency = $("#currency-name").combobox('getValue');
            var quoteCurrency = $("#cash-name").combobox('getValue');
            var status = $("#golive-name").combobox('getValue');
            var marginOpen = $("#recharge-name").combobox('getValue');

            var params = {
                baseCurrency: baseCurrency,
                quoteCurrency: quoteCurrency,
                status: status,
                marginOpen: marginOpen
            };

            return params;
        },
        fillRoleCombox1: function (act, values) {
            if (values.marginOpen) {
                $('#marginOpen').combobox('setValue', 'true');
                $('#marginOpen').combobox('setText', '开启');
            } else {
                $('#marginOpen').combobox('setValue', 'false');
                $('#marginOpen').combobox('setText', '关闭');
            }
        },
        fillRoleCombox2: function (act, values) {

            if (act === "edit") {
                $('#mergeType').textbox('setValue', values);

            }
        },
        fillText: function () {
            $('#marginOpen').combobox('setValue', '0');
            $('#marginOpen').combobox({
                onChange: function (newValue) {
                    if (newValue === '0') {
                        $('#maxMarginLeverage').textbox('setValue', 0);
                        $('#marginRiskPreRatio').textbox('setValue', 0);
                        $('#marginRiskRatio').textbox('setValue', 0);
                        $('#quoteLimit').textbox('setValue', 0);
                        $('#quoteUserLimit').textbox('setValue', 0);
                        $('#baseLimit').textbox('setValue', 0);
                        $('#baseUserLimit').textbox('setValue', 0);
                        $('#baseInterestRate').textbox('setValue', 0);
                        $('#quoteInterestRate').textbox('setValue', 0);
                    }
                }
            });
            $('#makerRate').textbox('setValue', -0.001);
            $('#tickerRate').textbox('setValue', -0.001);
            $('#name').textbox('setValue', 'Coinmex');
        }

    }
};