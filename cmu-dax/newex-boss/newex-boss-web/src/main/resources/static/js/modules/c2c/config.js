$(function () {
    Config.init();
});

var Config = {
    init: function () {
        ConfigMVC.View.initControl();
        ConfigMVC.View.bindEvent();
        ConfigMVC.View.bindValidate();

    }
};

var ConfigManageCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/c2c/config/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/'
};

var ConfigMVC = {
    URLs: {
        top: {
            url: ConfigManageCommon.baseUrl + 'top'
        },
        limit: {
            url: ConfigManageCommon.baseUrl + 'limit',
            method: 'GET'
        },
        over: {
            url: ConfigManageCommon.baseUrl + 'over',
            method: 'GET'
        },
        edit_top: {
            url: ConfigManageCommon.baseUrl + 'updateTopInfo',
            method: 'POST'
        },
        edit: {
            url: ConfigManageCommon.baseUrl + 'update',
            method: 'POST'
        },
        disable: {
            url: ConfigManageCommon.baseUrl + 'disable',
            method: 'POST'
        }


    },
    Model: {
        info: []
    },
    View: {
        initControl: function () {

            $('#limit-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: false,
                rownumbers: true,
                sortOrder: 'desc',
                pageSize: 20,
                url: ConfigMVC.URLs.limit.url + "?type=0",
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#limit-datagrid');
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
                        title: '币种Id',
                        hidden: true,
                        width: 0
                    },
                    {
                        field: 'currencySymbol',
                        title: '法币币种',
                        width: 100,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return value.toUpperCase();
                        }


                    }, {
                        field: 'minOrderTotalPerOrder',
                        title: '单笔最小限额',
                        width: 100,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return value;
                        }

                    }, {
                        field: 'maxOrderTotalPerOrder',
                        title: '单笔最大限额',
                        width: 100,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return value;
                        }
                    }, {
                        field: 'maxIncompleteOrderTotalPerUser',
                        title: '总限额',
                        width: 100,
                        sortable: true,

                    }, {
                        field: 'isTradeAllowed',
                        title: '状态',
                        width: 100,
                        formatter: function (value, row, index) {
                            return value === 0 ? "已禁用" : "已启用";
                        }
                    }, {
                        field: 'options',
                        title: '操作',
                        width: 100,
                        formatter: function (value, row, index) {
                            var icons = [{
                                "name": "edit",
                                "title": "修改"
                            }, {
                                "name": "disable",
                                "title": "禁用"
                            }];
                            var buttons = [];
                            for (var i = 0; i < icons.length; i++) {
                                var tmpl = '<a href="#" title ="${title}" '
                                    + 'onclick="ConfigMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                    + '<img src="${imgSrc}" alt="${title}"/"></a>';
                                var data = {
                                    title: icons[i].title,
                                    name: icons[i].name,
                                    index: index,
                                    imgSrc: ConfigManageCommon.baseIconUrl + icons[i].name + ".png"
                                };
                                buttons.push(juicer(tmpl, data));
                            }
                            return buttons.join(' ');
                        }
                    }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return ConfigMVC.Controller.edit();
                },
                onLoadSuccess: function () { //加载完成后,设置选中第一项
                    $('.pagination-page-list').hide();
                }
            });

            $('#spilLover-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: false,
                rownumbers: true,
                sortOrder: 'desc',
                pageSize: 20,
                url: ConfigMVC.URLs.limit.url + "?type=1",
                toolbar: [],
                loadFilter: function (src) {
                    if (!src.code) {
                        return src.data;
                    }
                    return $.messager.alert('失败', src.msg, 'error');
                },
                columns: [[
                    {
                        field: 'id',
                        title: '币种Id',
                        hidden: true,
                        width: 0
                    },
                    {
                        field: 'currencySymbol',
                        title: '数字货币币种',
                        width: 100,
                        formatter: function (value) {
                            return value.toUpperCase();
                        }
                    },
                    {
                        field: 'baseCurrency',
                        title: '买入溢价',
                        width: 100,
                        sortable: true,
                        formatter: function (value, row, index) {

                        }

                    }, {
                        field: 'quoteCurrency',
                        title: '卖出溢价',
                        width: 100,
                        sortable: true,
                        formatter: function (value, row, index) {

                        }
                    }, {
                        field: 'code',
                        title: '返佣比例',
                        width: 100,
                        sortable: true,

                    }, {
                        field: 'isTradeAllowed',
                        title: '状态',
                        width: 100,
                        formatter: function (value, row, index) {
                            return value === 0 ? "已禁用" : "已启用";
                        }
                    }, {
                        field: 'options',
                        title: '操作',
                        width: 100,
                        formatter: function (value, row, index) {
                            var icons = [{
                                "name": "edit",
                                "title": "修改"
                            }, {
                                "name": "disable",
                                "title": "禁用"
                            }];
                            var buttons = [];
                            for (var i = 0; i < icons.length; i++) {
                                var tmpl = '<a href="#" title ="${title}" '
                                    + 'onclick="ConfigMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                    + '<img src="${imgSrc}" alt="${title}"/"></a>';
                                var data = {
                                    title: icons[i].title,
                                    name: icons[i].name,
                                    index: index,
                                    imgSrc: ConfigManageCommon.baseIconUrl + icons[i].name + ".png"
                                };
                                buttons.push(juicer(tmpl, data));
                            }
                            return buttons.join('  ');
                        }
                    }]],
                onDblClickRow: function (rowIndex, rowData) {
                    // return ConfigMVC.Controller.edit();
                },
                onLoadSuccess: function () { //加载完成后,设置选中第一项
                    // $('.pagination-page-list').hide();

                }
            });

            $('#limit-dlg').dialog({
                closed: true,
                modal: false,
                width: 450,
                height: 400,
                iconCls: 'icon-add',
                buttons: [{
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: ConfigMVC.Controller.save
                }, {
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#limit-dlg").dialog('close');
                    }
                }]
            });

            ConfigMVC.Controller.loadinfo();


        },
        bindEvent: function () {
            $('#infoedita').bind('click', ConfigMVC.Controller.edit_top);
            $('#infoeditb').bind('click', ConfigMVC.Controller.edit_top);
            $('#infoeditc').bind('click', ConfigMVC.Controller.edit_top);
        },
        bindValidate: function () {
        },
        initData: function () {
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#limit-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return ConfigMVC.Controller.edit();
            }
            if (name === "disable") {
                return ConfigMVC.Controller.disable()
            }
        },
        edit: function () {
            var row = $('#limit-datagrid').datagrid('getSelected');
            if (row) {
                var options = ConfigMVC.Util.getOptions();
                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改[' + options.data.id + ']';
                EasyUIUtils.openEditDlg(options);
                $('#options').val(row.options || "{}");
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        edit_top: function () {
            var idname = $(this).attr('id');
            var param = {
                key: '',
                desc: '',
                defaultValue: ''
            };
            if (idname == 'infoedita') {
                param.key = 'max_incomplete_order_quantity_per_user';
                param.desc = '用户最大未处理订单数';
                param.defaultValue = $('#max_incomplete_order_quantity_per_user').text()
            }
            if (idname == 'infoeditb') {
                param.key = 'max_incomplete_order_quantity_per_supplier';
                param.desc = '商家最大处理订单数';
                param.defaultValue = $('#max_incomplete_order_quantity_per_supplier').text()
            }
            if (idname == 'infoeditc') {
                param.key = 'transfer_refund_fee';
                param.desc = '客诉订单追回手续费';
                param.defaultValue = $('#transfer_refund_fee').text()
            }
            $.messager.prompt({
                title: '修改[' + param.defaultValue + ']',
                msg: "输入" + param.desc,
                fn: function (r) {
                    if (r) {
                        if (param.key === 'transfer_refund_fee') {
                            r = r / 100;
                        }
                        var data = {
                            key: param.key,
                            value: r
                        };

                        $.post(ConfigMVC.URLs.edit_top.url, data, function callback(data) {
                            if (!data.code) {
                                $.messager.alert('success', 'success', 'success')
                            } else {
                                $.messager.alert('fail', "fail", 'error');
                            }
                        }, 'json').error(function (data) {
                            if (data.responseJSON.code == "403") {
                                $.messager.show({
                                    title: '错误',
                                    msg: data.responseJSON.msg
                                });
                            } else {
                                $.messager.alert('提示', 'error', 'info');
                            }
                        });

                    }
                }
            });
            $(".messager-input").val(param.defaultValue);

        },
        save: function () {
            var action = $('#modal-action').val();
            var options = {
                gridId: null,
                gridUrl: ConfigMVC.URLs.limit.url + "?type=0",
                dlgId: "#limit-dlg",
                formId: "#limit-form",
                url: null,
                callback: function () {
                }
            };
            options.url = (action === "edit" ? ConfigMVC.URLs.edit.url : ConfigMVC.URLs.edit.url);
            options.gridId = '#limit-datagrid';
            return EasyUIUtils.save(options);
        },
        disable: function () {
            var row = $("#limit-datagrid").datagrid('getSelected');
            var options = ConfigMVC.Util.getOptions();
            options.data = row;
            var Id = options.data.id;
            if (options.data.isTradeAllowed === 0 || options.data.isTradeAllowed === 1) {
                $.messager.confirm('确认', '是否操作【' + Id + '】', function (r) {
                    if (r) {
                        var mydata = {
                            cid: 1,
                            isTradeAllowed: options.data.isTradeAllowed
                        };
                        $.post(ConfigMVC.URLs.disable.url, mydata, function (result) {
                            if (!data.code) {
                                $.messager.alert('成功', "成功", 'success');
                            } else {
                                $.messager.alert('失败', "失败", 'error');
                            }
                        }, 'json').error(function (data) {
                            if (data.responseJSON.code == "403") {
                                $.messager.show({
                                    title: '错误',
                                    msg: data.responseJSON.msg
                                });

                            } else {
                                $.messager.alert('提示', 'error', 'info');
                            }
                        });
                    }
                });
            } else {
                $.messager.alert('警告', "不支持操作", 'error');
            }
        },
        loadinfo: function () {
            $.getJSON(ConfigMVC.URLs.top.url, function (result) {
                var tmp = result.data;
                $("#max_incomplete_order_quantity_per_user").html(tmp.max_incomplete_order_quantity_per_user);
                $("#max_incomplete_order_quantity_per_supplier").html(tmp.max_incomplete_order_quantity_per_supplier);
                $("#transfer_refund_fee").html(tmp.transfer_refund_fee * 100);
            });
        }

    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#limit-dlg',
                formId: '#limit-form',
                actId: '#modal-action',
                rowId: '#id',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                gridId: null,
            };
        },
        isRowSelected: function (func) {
            var row = $('#limit-datagrid').datagrid('getSelected');
            if (row) {
                func(row);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        }
    }
};