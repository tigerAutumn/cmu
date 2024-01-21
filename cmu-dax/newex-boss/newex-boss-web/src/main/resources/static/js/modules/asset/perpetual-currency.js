$(function () {
    PerpetualCurrency.init();
});

var PerpetualCurrency = {
    init: function () {
        PerpetualCurrencyMVC.View.initControl();
        PerpetualCurrencyMVC.View.bindEvent();
        PerpetualCurrencyMVC.View.bindValidate();
        PerpetualCurrencyMVC.View.initData();
    }
};

var PerpetualCurrencyCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/asset/currency/perpetual/',
    baseimgUrl: BossGlobal.ctxPath + '/v1/boss/upload/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/'
};

var PerpetualCurrencyMVC = {
    URLs: {
        add: {
            url: PerpetualCurrencyCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: PerpetualCurrencyCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: PerpetualCurrencyCommon.baseUrl + 'list',
            method: 'GET'
        },
        img: {
            url: PerpetualCurrencyCommon.baseimgUrl + 'oss-upload',
            method: 'GET'
        },
        OneStep: {
            url: PerpetualCurrencyCommon.baseUrl + 'one-step',
            method: 'POST'
        },
        getBrokerList: {
            url: PerpetualCurrencyCommon.brokerBaseUrl + 'list',
            method: 'GET'
        }
    },
    Model: {},
    View: {
        initControl: function () {

            $("#imgfile").filebox({
                onChange: function () {
                    PerpetualCurrencyMVC.Util.imgupdate();
                },
                prompt: '选择图片',
                buttonText: '&nbsp;选&nbsp;择&nbsp;',
            });

            $("#brokerId").combobox({
                url: PerpetualCurrencyMVC.URLs.getBrokerList.url,
                method: 'get',
                valueField: "id",
                textField: "brokerName",
                editable: true,
                value: '',
                prompt: '选择券商',
                panelHeight: 300
            });

            $('#withdrawable').combobox({
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

            $('#currency-withdrawable').combobox({
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
                    $('#currency-withdrawable').combobox('setValue', '-1');
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

            $('#exchange').combobox({
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

            $('#rechargeable').combobox({
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

            $('#currency-rechargeable').combobox({
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
                    $('#currency-rechargeable').combobox('setValue', '-1');
                }
            });

            $('#expireDate').datebox({}).datebox('calendar').calendar({
                validator: function (date) {
                    var now = new Date();
                    var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate());

                    return date >= d1;
                }
            });

            $('#futureDown').combobox({
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

            $('#receive').combobox({
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

            $('#needTag').combobox({
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

            $('#needAlert').combobox({
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

            $('#biz').combobox({
                valueField: 'id',
                textField: 'value',
                data: [
                    {
                        id: 'Perpetual',
                        value: '币币'
                    }, {
                        id: 'contract',
                        value: '合约'
                    }, {
                        id: 'portfolio',
                        value: '组合'
                    }
                ]
            });

            $('#currency-biz').combobox({
                valueField: 'id',
                textField: 'value',
                data: [
                    {
                        id: 'Perpetual',
                        value: '币币'
                    }, {
                        id: 'contract',
                        value: '合约'
                    }, {
                        id: 'portfolio',
                        value: '组合'
                    }
                ],
                onLoadSuccess: function () {
                    $('#currency-biz').combobox('setValue', 'Perpetual');
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

            $('#currency-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: PerpetualCurrencyMVC.URLs.list.url,
                toolbar: [{
                    text: '新增',
                    iconCls: 'icon-add',
                    handler: function () {
                        PerpetualCurrencyMVC.Controller.add();
                    }
                }, '-', {
                    text: '编辑',
                    iconCls: 'icon-edit',
                    handler: function () {
                        PerpetualCurrencyMVC.Controller.edit();
                    }
                }, '-', {
                    text: '冻结',
                    iconCls: 'icon-lock',
                    handler: function () {
                        PerpetualCurrencyMVC.Controller.freeze();
                    }
                }, '-', {
                    text: '解冻',
                    iconCls: 'icon-unlock',
                    handler: function () {
                        PerpetualCurrencyMVC.Controller.unfreeze();
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
                    title: '币种ID',
                    width: 150,
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
                    field: 'currencyPictureUrl',
                    title: '币种图片',
                    width: 150,
                    sortable: true
                }, {
                    field: 'biz',
                    title: '产品线',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value == "Perpetual") {
                            return "币币";
                        } else if (value == "c2c") {
                            return "法币";
                        } else if (value == "contract") {
                            return "合约";
                        } else if (value == "portfolio") {
                            return "组合";
                        } else {
                            return "";
                        }
                    }
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
                    field: 'withdrawable',
                    title: '可否提现',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return value === 1 ? "是" : "否";
                    }
                }, {
                    field: 'rechargeable',
                    title: '可否充值',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return value === 1 ? "是" : "否";
                    }
                }, {
                    field: 'exchange',
                    title: '可否兑换',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return value === 1 ? "是" : "否";
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
                    field: 'receive',
                    title: '能否领取',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return value === 1 ? "是" : "否";
                    }
                }, {
                    field: 'sort',
                    title: '排序',
                    width: 150,
                    sortable: true
                }, {
                    field: 'depositConfirm',
                    title: '充值确认数',
                    width: 150,
                    sortable: true
                }, {
                    field: 'minDepositAmount',
                    title: '最小充值数量',
                    width: 150,
                    sortable: true
                }, {
                    field: 'withdrawFee',
                    title: '提现手续费',
                    width: 150,
                    sortable: true
                }, {
                    field: 'minWithdrawAmount',
                    title: '最小提现数',
                    width: 150,
                    sortable: true
                }, {
                    field: 'withdrawConfirm',
                    title: '提现确认数',
                    width: 150,
                    sortable: true
                }, {
                    field: 'maxWithdrawAmount',
                    title: '最大提现数量',
                    width: 150,
                    sortable: true
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
                    field: 'txExplorerUrl',
                    title: '交易ID浏览器地址',
                    width: 150,
                    sortable: true
                }, {
                    field: 'needTag',
                    title: '是否需要tag',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return value === 1 ? "是" : "否";
                    }
                }, {
                    field: 'tagField',
                    title: 'tagField',
                    width: 150,
                    sortable: true
                }, {
                    field: 'cnWikiUrl',
                    title: '币种链接(中文)',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return "<a href='" + value + "' target='_blank'>" + value + "</a>";
                    }
                }, {
                    field: 'usWikiUrl',
                    title: '币种链接(英文)',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return "<a href='" + value + "' target='_blank'>" + value + "</a>";
                    }
                }, {
                    field: 'options',
                    title: '操作',
                    width: 150,
                    formatter: function (value, row, index) {
                        var icons = [{
                            "name": "edit",
                            "title": "编辑"
                        }];
                        var buttons = [];
                        for (var i = 0; i < icons.length; i++) {
                            var tmpl = '<a href="#" title ="${title}" '
                                + 'onclick="PerpetualCurrencyMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: PerpetualCurrencyCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return PerpetualCurrencyMVC.Controller.edit();
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
                    handler: PerpetualCurrencyMVC.Controller.save
                }]
            });

        },
        bindEvent: function () {
            $('#btn-search').bind('click', PerpetualCurrencyMVC.Controller.find);
            $('#one-step-frozen').bind('click', PerpetualCurrencyMVC.Controller.oneStep);
            $('#one-step-unfrozen').bind('click', PerpetualCurrencyMVC.Controller.oneStep);
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
                return PerpetualCurrencyMVC.Controller.edit();
            }
        },
        add: function () {
            var options = PerpetualCurrencyMVC.Util.getOptions();
            options.title = '新增币币币种';
            EasyUIUtils.openAddDlg(options);

            $("#symbol").textbox('readonly', false);
            $("#biz").combobox('readonly', false);

            $('#biz').combobox('select', 'Perpetual');
            $('#online').combobox('select', '1');
            $('#zone').combobox('select', '1');
            $('#futureDown').combobox('select', '0');
            $('#withdrawable').combobox('select', '1');
            $('#rechargeable').combobox('select', '1');
            $('#exchange').combobox('select', '0');
            $('#transfer').combobox('select', '1');
            $('#receive').combobox('select', '0');
            $('#needAlert').combobox('select', '0');
            $('#needTag').combobox('select', '0');
            $("#brokerId").combobox('readonly', false);
        },
        edit: function () {
            var row = $('#currency-datagrid').datagrid('getSelected');

            if (row) {
                var options = PerpetualCurrencyMVC.Util.getOptions();
                console.log(options);
                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改币币币种[' + row.symbol + ']';

                $("#symbol").textbox('readonly', true);
                $("#biz").combobox('readonly', true);
                $("#brokerId").combobox('readonly', true);

                var expireDate = row.expireDate;
                if (expireDate) {
                    row.futureDown = 1;
                    row.expireDate = moment(new Date(expireDate)).format("YYYY-MM-DD");
                } else {
                    row.futureDown = 0;
                }

                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        find: function () {
            var params = PerpetualCurrencyMVC.Util.getQueryParams();

            $('#currency-datagrid').datagrid('load', params);
        },
        save: function () {
            var action = $('#modal-action').val();

            var options = PerpetualCurrencyMVC.Util.getOptions();

            if (action === "edit") {
                var row = $('#currency-datagrid').datagrid('getSelected');
                //取得ID传给后台
                options.url = PerpetualCurrencyMVC.URLs.edit.url + '?id=' + row.id;
            } else {
                options.url = PerpetualCurrencyMVC.URLs.add.url;
            }

            //修改后刷新列表
            EasyUIUtils.save(options);

            return;
        },
        freeze: function () {
            PerpetualCurrencyMVC.Controller.oneStep('冻结', 1);
        },
        unfreeze: function () {
            PerpetualCurrencyMVC.Controller.oneStep('解冻', 2);
        },
        oneStep: function (tips, type) {
            $.messager.confirm('确认', '确定需要执行一键[' + tips + ']操作?', function (r) {
                if (r) {
                    var url = PerpetualCurrencyMVC.URLs.OneStep.url;
                    $.post(url, {type: type}, function (d) {
                        if (!d.code) {
                            $.messager.alert('成功', tips + "成功", 'success');
                        } else {
                            $.messager.alert('失败', tips + "失败", 'error');
                        }
                    }, 'json');
                }
            });

            $('#currency-datagrid').datagrid('load', {});
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
                gridUrl: PerpetualCurrencyMVC.URLs.list.url
            };
        },
        getQueryParams: function () {
            var symbol = $('#currency-symbol').textbox('getValue');
            var withdrawable = $('#currency-withdrawable').combobox('getValue');
            var rechargeable = $('#currency-rechargeable').combobox('getValue');
            var online = $('#currency-online').combobox('getValue');
            var biz = $('#currency-biz').combobox('getValue');

            var params = {
                symbol: symbol,
                withdrawable: withdrawable,
                rechargeable: rechargeable,
                online: online,
                biz: biz
            };

            return params;
        },
        imgupdate: function () {
            var file = $("#imgfile").filebox('getValue');
            var formData = new FormData($("#currency-form")[0]);
            //调用apicontroller后台action方法，将form数据传递给后台处理。contentType必须设置为false,否则chrome和firefox不兼容
            $.ajax({
                url: PerpetualCurrencyMVC.URLs.img.url,
                type: 'POST',
                data: formData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (data) {
                    $('#currencyPictureUrl').textbox('setValue', data.data.s3path);
                    //上传成功后将控件内容清空，并显示上传成功信息
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
    }
};