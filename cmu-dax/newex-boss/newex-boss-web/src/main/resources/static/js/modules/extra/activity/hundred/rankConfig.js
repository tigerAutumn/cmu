$(function () {
    RankConfig.init();
});

var RankConfig = {
    init: function () {
        RankConfigMVC.View.initControl();
        RankConfigMVC.View.bindEvent();
        RankConfigMVC.View.bindValidate();
    }
};

var RankConfigCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/activity/rank/config/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/'
};

var RankConfigMVC = {
    URLs: {
        add: {
            url: RankConfigCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: RankConfigCommon.baseUrl + 'edit',
            method: 'POST'
        },
        remove: {
            url: RankConfigCommon.baseUrl + 'remove',
            method: 'POST'
        },
        list: {
            url: RankConfigCommon.baseUrl + 'list',
            method: 'GET'
        },
        getBrokerList: {
            url: RankConfigCommon.brokerBaseUrl + 'list'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {

            $("#brokerId").combobox({
                url: RankConfigMVC.URLs.getBrokerList.url,
                method: 'get',
                valueField: "id",
                textField: "brokerName",
                editable: true,
                value: '',
                prompt: '选择券商',
                panelHeight: 300
            });

            $("#rankConfig-brokerId").combobox({
                url: RankConfigMVC.URLs.getBrokerList.url,
                method: 'get',
                valueField: "id",
                textField: "brokerName",
                editable: true,
                value: '',
                prompt: '选择券商',
                panelHeight: 300
            });

            $('#status').combobox({
                valueField: 'id',
                textField: 'value',
                data: [
                    {
                        id: 1,
                        value: '未开始'
                    }, {
                        id: 2,
                        value: '火热进行中'
                    }, {
                        id: 3,
                        value: '已结束'
                    }
                ],
                onLoadSuccess: function () { //加载完成后,设置选中第一项
                    $('#status').combobox('setValue', "1");
                }
            });

            $('#rankConfig-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: RankConfigMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        RankConfigMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        RankConfigMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#rankConfig-datagrid').datagrid('load', {});
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
                    field: 'term',
                    title: '期数',
                    width: 100,
                    sortable: true
                }, {
                    field: 'brokerId',
                    title: '券商',
                    width: 100,
                    sortable: true
                }, {
                    field: 'startDate',
                    title: '开始时间',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                    }
                }, {
                    field: 'endDate',
                    title: '结束时间',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                    }
                }, {
                    field: 'status',
                    title: '状态',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value === 1) {
                            return "未开始";
                        } else if (value === 2) {
                            return "火热进行中";
                        } else if (value === 3) {
                            return "已结束";
                        } else {
                            return "";
                        }
                    }
                }, {
                    field: 'createdDate',
                    title: '创建时间',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                    }
                }, {
                    field: 'updatedDate',
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
                                + 'onclick="RankConfigMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: RankConfigCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return RankConfigMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // dialogs
            $('#rankConfig-dlg').dialog({
                closed: true,
                modal: false,
                width: 760,
                height: 500,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#rankConfig-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: RankConfigMVC.Controller.save
                }]
            });


        },
        bindEvent: function () {
            $('#btn-search').bind('click', RankConfigMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#rankConfig-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return RankConfigMVC.Controller.edit();
            }

            if (name === "remove") {
                return RankConfigMVC.Controller.remove();
            }
        },
        add: function () {
            var options = RankConfigMVC.Util.getOptions();
            options.title = '新增 CT-USDT交易排行榜活动时间';
            EasyUIUtils.openAddDlg(options);
        },
        edit: function () {
            var row = $('#rankConfig-datagrid').datagrid('getSelected');
            if (row) {
                var options = RankConfigMVC.Util.getOptions();

                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改 CT-USDT交易排行榜活动时间';

                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#rankConfig-datagrid').datagrid('getSelected');
            if (row) {
                var options = RankConfigMVC.Util.getOptions();

                options.url = RankConfigMVC.URLs.remove.url;
                options.rows = [row];
                options.data = {id: row.id, code: row.code};

                EasyUIUtils.remove(options);
            }
        },
        find: function () {
            var params = RankConfigMVC.Util.getQueryParams();
            $('#rankConfig-datagrid').datagrid('load', params);
        },
        save: function () {
            var action = $('#modal-action').val();

            var options = RankConfigMVC.Util.getOptions();

            if (action === "edit") {
                var row = $('#rankConfig-datagrid').datagrid('getSelected');
                //取得ID传给后台
                options.url = RankConfigMVC.URLs.edit.url + '?id=' + row.id;
            }
            else {
                //添加操作
                options.url = RankConfigMVC.URLs.add.url;
            }

            //修改后刷新列表
            EasyUIUtils.save(options);

            return;
        }

    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#rankConfig-dlg',
                formId: '#rankConfig-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                url: null,
                gridId: '#rankConfig-datagrid',
                gridUrl: RankConfigMVC.URLs.list.url
            };
        },
        getQueryParams: function () {
            var brokerId = $("#rankConfig-brokerId").val();
            var params = {
                brokerId: brokerId
            };

            return params;
        }
    }
};