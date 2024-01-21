$(function () {
    BreakerStrategy.init();
});

var BreakerStrategy = {
    init: function () {
        BreakerStrategyMVC.View.initControl();
        BreakerStrategyMVC.View.bindEvent();
        BreakerStrategyMVC.View.bindValidate();
    }
};

var BreakerStrategyCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/spot/breaker/',
    imageUrl: BossGlobal.ctxPath + '/v1/boss/upload/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/'
};

var BreakerStrategyMVC = {
    URLs: {
        add: {
            url: BreakerStrategyCommon.baseUrl + 'addStrategy',
            method: 'POST'
        },
        edit: {
            url: BreakerStrategyCommon.baseUrl + 'editStrategy',
            method: 'POST'
        },
        remove: {
            url: BreakerStrategyCommon.baseUrl + 'removeStrategy',
            method: 'POST'
        },
        list: {
            url: BreakerStrategyCommon.baseUrl + 'listStrategy',
            method: 'GET'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {

            $('#breakerStrategy-preset').combobox({
                valueField: 'id',
                textField: 'value',
                data: [
                    {
                        id: '',
                        value: '全部'
                    }, {
                        id: '0',
                        value: '否'
                    }, {
                        id: '1',
                        value: '是'
                    }
                ]
            });

            $('#breakerPreset').combobox({
                required: true,
                valueField: 'id',
                textField: 'value',
                data: [
                    {
                        id: '0',
                        value: '否'
                    }, {
                        id: '1',
                        value: '是'
                    }
                ]
            });

            $('#breakerStrategy-direction').combobox({
                valueField: 'id',
                textField: 'value',
                data: [
                    {
                        id: '',
                        value: '全部'
                    }, {
                        id: '0',
                        value: '上涨'
                    }, {
                        id: '1',
                        value: '下跌'
                    }
                ]
            });

            $('#breakerDirection').combobox({
                required: true,
                valueField: 'id',
                textField: 'value',
                data: [
                    {
                        id: '0',
                        value: '上涨'
                    }, {
                        id: '1',
                        value: '下跌'
                    }
                ]
            });

            $('#breakerStrategy-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: BreakerStrategyMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        BreakerStrategyMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        BreakerStrategyMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#breakerStrategy-datagrid').datagrid('load', {});
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
                    field: 'breakerPresetEnum',
                    title: '预置策略',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        row.breakerPreset = 0;

                        if (value == 'NO') {
                            row.breakerPreset = 0;
                            return '否';
                        }

                        if (value == 'YES') {
                            row.breakerPreset = 1;
                            return '是';
                        }

                        return '';
                    }
                }, {
                    field: 'breakerDirectionEnum',
                    title: '监控方向',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        row.breakerDirection = 0;

                        if (value == 'LONG') {
                            row.breakerDirection = 0;
                            return '上涨';
                        }

                        if (value == 'SHORT') {
                            row.breakerDirection = 1;
                            return '下跌';
                        }

                        return '';
                    }
                }, {
                    field: 'monitorTime',
                    title: '监控周期（秒）',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        var ds = value / 1000;
                        row.monitorTime = ds;

                        return ds;
                    }
                }, {
                    field: 'triggerRatio',
                    title: '触发熔断比例',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        var ds = value;
                        row.triggerRatio = ds;

                        return ds + '%';
                    }
                }, {
                    field: 'pauseTime',
                    title: '暂停时长（秒）',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        var ds = value / 1000;
                        row.pauseTime = ds;

                        return ds;
                    }
                }, {
                    field: 'intervalTime',
                    title: '任务间隔时长（秒）',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        var ds = value / 1000;
                        row.intervalTime = ds;

                        return ds;
                    }
                }, {
                    field: 'remark',
                    title: '备注',
                    width: 100,
                    sortable: true
                }, {
                    field: 'createDate',
                    title: '创建时间',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                    }
                }, {
                    field: 'modifyDate',
                    title: '修改时间',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                    }
                }, {
                    field: 'options',
                    title: '操作',
                    width: 100,
                    formatter: function (value, row, index) {
                        var icons = [{
                            "name": "edit",
                            "title": "编辑"
                        }, {
                            "name": "remove",
                            "title": "删除"
                        }];

                        var buttons = [];
                        for (var i = 0; i < icons.length; i++) {
                            var tmpl = '<a href="#" title ="${title}" '
                                + 'onclick="BreakerStrategyMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: BreakerStrategyCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return BreakerStrategyMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // dialogs
            $('#breakerStrategy-dlg').dialog({
                closed: true,
                modal: false,
                width: 800,
                height: 500,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#breakerStrategy-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: BreakerStrategyMVC.Controller.save
                }]
            });

        },
        bindEvent: function () {
            $('#btn-search').bind('click', BreakerStrategyMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#breakerStrategy-datagrid').datagrid('selectRow', index);

            if (name === "edit") {
                return BreakerStrategyMVC.Controller.edit();
            }

            if (name === "remove") {
                return BreakerStrategyMVC.Controller.remove();
            }
        },
        add: function () {
            var options = BreakerStrategyMVC.Util.getOptions();
            options.title = '新增熔断策略';
            EasyUIUtils.openAddDlg(options);

            $('#breakerPreset').combobox('select', '0');
            $('#breakerDirection').combobox('select', '0');
        },
        edit: function () {
            var row = $('#breakerStrategy-datagrid').datagrid('getSelected');
            if (row) {
                var options = BreakerStrategyMVC.Util.getOptions();

                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改熔断策略';

                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#breakerStrategy-datagrid').datagrid('getSelected');
            if (row) {
                var options = BreakerStrategyMVC.Util.getOptions();

                options.url = BreakerStrategyMVC.URLs.remove.url;
                options.rows = [row];
                options.data = {id: row.id};

                EasyUIUtils.remove(options);
            }
        },
        find: function () {
            var params = BreakerStrategyMVC.Util.getQueryParams();

            $('#breakerStrategy-datagrid').datagrid('load', params);
        },
        save: function () {
            var action = $('#modal-action').val();

            var options = BreakerStrategyMVC.Util.getOptions();

            if (action === "edit") {
                var row = $('#breakerStrategy-datagrid').datagrid('getSelected');
                //取得ID传给后台
                options.url = BreakerStrategyMVC.URLs.edit.url + '?id=' + row.id;
            }
            else {
                //添加操作
                options.url = BreakerStrategyMVC.URLs.add.url;
            }

            //修改后刷新列表
            EasyUIUtils.save(options);

            return;
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#breakerStrategy-dlg',
                formId: '#breakerStrategy-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                url: null,
                gridId: '#breakerStrategy-datagrid',
                gridUrl: BreakerStrategyMVC.URLs.list.url
            };
        },
        getQueryParams: function () {
            var id = $('#breakerStrategy-id').textbox('getValue');
            var preset = $('#breakerStrategy-preset').combobox('getValue');
            var direction = $('#breakerStrategy-direction').combobox('getValue');

            var params = {
                id: id,
                preset: preset,
                direction: direction
            };

            return params;
        }
    }
};