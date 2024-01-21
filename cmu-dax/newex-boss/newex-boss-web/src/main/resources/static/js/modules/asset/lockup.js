$(function () {
    Lockup.init();
});

var Lockup = {
    init: function () {
        LockupMVC.View.initControl();
        LockupMVC.View.bindEvent();
        LockupMVC.View.bindValidate();
    }
};

var LockupCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/asset/lockup/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/'
};

var LockupMVC = {
    URLs: {
        add: {
            url: LockupCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: LockupCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: LockupCommon.baseUrl + 'list',
            method: 'GET'
        },
        unlock: {
            url: LockupCommon.baseUrl + 'unlock',
            method: 'POST'
        },
        listAllCurrency: {
            url: LockupCommon.baseUrl + 'listAllCurrency',
            method: 'GET'
        },
        lockRecordList: {
            url: LockupCommon.baseUrl + 'getLockRecodeList',
            method: 'GET'
        },
        getBrokerList: {
            url: LockupCommon.brokerBaseUrl + 'list',
            method: 'GET'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {

            $.get(LockupMVC.URLs.listAllCurrency.url, "", function (_datax) {

                _datax.data.unshift({
                    'id': '-1',
                    'symbol': '全部'
                });

                $("#lockup-currency").combobox({
                    data: _datax.data,
                    valueField: "id",
                    textField: "symbol",
                    onLoadSuccess: function () { //加载完成后,设置选中第一项
                        $('#lockup-currency').combobox('setValue', "-1");
                    }
                });

                $("#lock_currency").combobox({
                    data: _datax.data,
                    valueField: "id",
                    textField: "symbol",
                    onLoadSuccess: function () { //加载完成后,设置选中第一项
                        $('#lock_currency').combobox('setValue', "-1");
                    }
                });


            });

            $("#brokerId").combobox({
                url: LockupMVC.URLs.getBrokerList.url,
                method: 'get',
                valueField: "id",
                textField: "brokerName",
                editable: true,
                value: '',
                prompt: '选择券商',
                panelHeight: 300
            });

            $('#unlockTime').datebox({}).datetimebox('calendar').calendar({
                validator: function (date) {
                    var now = new Date();
                    var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate());

                    return date >= d1;
                }
            });

            $('#unlockCycle').combobox({
                valueField: 'id',
                textField: 'value',
                data: [
                    {
                        id: 2,
                        value: '每月'
                    }, {
                        id: 5,
                        value: '每天'
                    }
                ],
                onLoadSuccess: function () { //加载完成后,设置选中第一项
                    $('#unlockCycle').combobox('setValue', "2");
                }
            });


            $('#lockup-tabs').tabs({
                onSelect: function (title, index) {
                    if (index == 0) {// 锁仓列表
                        EasyUIUtils.reloadDatagrid('#lockup-datagrid');
                    } else if (index == 1) {// 锁仓记录
                        EasyUIUtils.reloadDatagrid('#lock-record-datagrid');
                    }
                }
            });

            $('#lockup-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: LockupMVC.URLs.list.url,
                toolbar: [{
                    text: '添加锁仓',
                    iconCls: 'icon-add',
                    handler: function () {
                        LockupMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#lockup-datagrid').datagrid('load', {});
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
                    width: 50
                }, {
                    field: 'userId',
                    title: '用户ID',
                    width: 100,
                    sortable: true
                }, {
                    field: 'brokerId',
                    title: '券商',
                    width: 150,
                    sortable: true
                }, {
                    field: 'createDate',
                    title: '锁仓创建时间',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD")
                    }
                }, {
                    field: 'lockPositionName',
                    title: '锁仓名称',
                    width: 150,
                    sortable: true
                }, {
                    field: 'currencyName',
                    title: '锁仓币种',
                    width: 100,
                    sortable: true
                }, {
                    field: 'amount',
                    title: '锁仓数量',
                    width: 150,
                    sortable: true
                }, {
                    field: 'releaseDate',
                    title: '首次释放时间',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD")
                    }
                }, {
                    field: 'releaseRule',
                    title: '释放规则',
                    width: 150,
                    sortable: true
                }, {
                    field: 'lockAmount',
                    title: '未释放数量',
                    width: 150,
                    sortable: true
                }, {
                    field: 'unlockAmount',
                    title: '已释放数量',
                    width: 150,
                    sortable: true,
                }, {
                    field: 'dividend',
                    title: '参与分红',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value === 1) {
                            return "是";
                        }
                        else {
                            return "否";
                        }
                    }
                }, {
                    field: 'options',
                    title: '操作',
                    width: 100,
                    formatter: function (value, row, index) {
                        var icons = [{
                            "name": "edit",
                            "title": "解锁"
                        }];
                        var buttons = [];
                        for (var i = 0; i < icons.length; i++) {
                            var tmpl = '<a href="#" title ="${title}" '
                                + 'onclick="LockupMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '${title}</a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: LockupCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    // return LockupMVC.Controller.edit();
                }

            });

            $('#lock-record-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: LockupMVC.URLs.lockRecordList.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#lock-record-datagrid').datagrid('load', {});
                    }
                }],
                loadFilter: function (src) {
                    if (!src.code) {
                        return src.data;
                    }
                    return $.messager.alert('失败', src.msg, 'error');
                },
                columns: [[{
                    field: 'createTime',
                    title: '时间',
                    width: 200,
                    formatter: function (val) {
                        return moment(new Date(val)).format('YYYY-MM-DD')
                    }
                }, {
                    field: 'userId',
                    title: '用户ID',
                    width: 150,
                    sortable: true
                }, {
                    field: 'currency',
                    title: '币种',
                    width: 150
                }, {
                    field: 'amount',
                    title: '数量',
                    width: 150,
                    sortable: true
                }, {
                    field: 'lockPosition',
                    title: '锁仓类型',
                    width: 250,
                    sortable: true,
                }, {
                    field: 'transferType',
                    title: '行为',
                    width: 150,
                    formatter: function (val) {
                        if (val === 6) {
                            return "加锁";
                        }
                        if (val === 7) {
                            return "解锁";
                        }
                    }
                }, {
                    field: 'operator',
                    title: '操作人',
                    width: 150,
                    sortable: true
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    //return LockupMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            $('#lockup-dlg').dialog({
                closed: true,
                modal: false,
                width: 850,
                height: window.screen.height - 400,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#lockup-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: LockupMVC.Controller.save
                }]
            });

            $('#unlock-dlg').dialog({
                closed: true,
                modal: false,
                width: 650,
                height: 300,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#unlock-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: LockupMVC.Controller.saveUnlock
                }]
            });

        },
        bindEvent: function () {
            $('#lockup-btn-search').bind('click', LockupMVC.Controller.find);
            $('#lock-record-search').bind('click', LockupMVC.Controller.findLockRecord);
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#lockup-datagrid').datagrid('selectRow', index);

            LockupMVC.Controller.unlock();
        },
        add: function () {
            var options = LockupMVC.Util.lockupOptions();
            EasyUIUtils.openAddDlg(options);

            $.get(LockupMVC.URLs.listAllCurrency.url, "", function (_datax) {

                $("#currency").combobox({
                    data: _datax.data,
                    valueField: "id",
                    textField: "symbol",
                    onLoadSuccess: function () { //加载完成后,设置选中第一项
                        var allData = $(this).combobox('getData');
                        if (allData) {
                            $('#currency').combobox('setValue', allData[0]['id']);
                        }
                    }
                });

            });

            $("#dividend").combobox("setValue", "0");
            $("#unlockCycle").combobox("setValue", "2");
        },
        edit: function () {
            var row = $('#lockup-datagrid').datagrid('getSelected');
            if (row) {
                var options = LockupMVC.Util.lockupOptions();

                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改锁仓[' + options.data.lockPositionName + ']';
                options.url = LockupMVC.URLs.edit.url + '?id=' + row.id;

                //console.log(options);
                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        unlock: function () {
            var row = $('#lockup-datagrid').datagrid('getSelected');
            if (row) {
                var options = LockupMVC.Util.unlockOptions();
                EasyUIUtils.openAddDlg(options);

                $("#unlock-Id").val(row.id);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }

        },
        find: function () {
            var params = LockupMVC.Util.getLockupQueryParams();

            $('#lockup-datagrid').datagrid('load', params);
        },
        findLockRecord: function () {
            var params = LockupMVC.Util.getRecordQueryParams();

            $('#lock-record-datagrid').datagrid('load', params);
        },
        save: function () {
            var options = LockupMVC.Util.lockupOptions();

            EasyUIUtils.save(options);

            return;
        },
        saveUnlock: function () {
            var options = LockupMVC.Util.unlockOptions();

            EasyUIUtils.save(options);

            return;
        }

    },
    Util: {
        unlockOptions: function () {
            return {
                dlgId: '#unlock-dlg',
                formId: '#unlock-form',
                actId: '#unlock-modal-action',
                rowId: '#rowId',
                title: '解锁',
                iconCls: 'icon-add',
                data: {},
                gridId: '#lockup-datagrid',
                gridUrl: LockupMVC.URLs.list.url,
                url: LockupMVC.URLs.unlock.url,
                callback: function () {
                    LockupMVC.Controller.find();
                }
            };
        },
        lockupOptions: function () {
            return {
                dlgId: '#lockup-dlg',
                formId: '#lockup-form',
                actId: '#lockup-modal-action',
                rowId: '#rowId',
                title: '添加锁仓',
                iconCls: 'icon-add',
                data: {},
                gridId: '#lockup-datagrid',
                gridUrl: LockupMVC.URLs.list.url,
                url: LockupMVC.URLs.add.url
            };
        },
        getLockupQueryParams: function () {
            var userProperty = $("#lockup-userProperty").combobox("getValue");
            var userValue = $("#lockup-userValue").textbox("getValue");
            var currencyId = $("#lockup-currency").combobox("getValue");
            var lockPositionName = $("#lockup-name").textbox("getValue");
            var startTime = $("#lockup-startTime").datetimebox("getValue");
            var endTime = $("#lockup-endTime").datetimebox("getValue");

            var params = {
                userProperty: userProperty,
                userValue: userValue,
                currencyId: currencyId,
                lockPositionName: lockPositionName,
                startTime: startTime,
                endTime: endTime
            };

            return params;
        },
        getRecordQueryParams: function () {
            var lock_field = $('#lock_field').combobox('getValue');
            var lock_keyword = $('#lock_keyword').textbox('getValue');
            var lock_action = $('#lock_action').combobox('getValue');
            var lock_begin_time = $('#lock_begin_time').datetimebox('getValue');
            var lock_end_time = $('#lock_end_time').datetimebox('getValue');
            var currency = $('#lock_currency').combobox('getValue');

            var params = {
                field: lock_field,
                keyword: lock_keyword,
                currency: currency,
                action: lock_action,
                beginTime: lock_begin_time,
                endTime: lock_end_time
            };

            return params;
        }
    }
};