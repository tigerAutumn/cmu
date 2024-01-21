$(function () {
    FuturePortfolio.init();
});

var FuturePortfolio = {
    init: function () {
        FuturePortfolioMVC.View.initControl();
        FuturePortfolioMVC.View.bindEvent();
        FuturePortfolioMVC.View.bindValidate();
    }
};

var FuturePortfolioCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/portfolio/future/',
    imageUrl: BossGlobal.ctxPath + '/v1/boss/upload/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/'
};

var FuturePortfolioMVC = {
    URLs: {
        add: {
            url: FuturePortfolioCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: FuturePortfolioCommon.baseUrl + 'edit',
            method: 'POST'
        },
        remove: {
            url: FuturePortfolioCommon.baseUrl + 'remove',
            method: 'POST'
        },
        list: {
            url: FuturePortfolioCommon.baseUrl + 'list',
            method: 'GET'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {

            $('#transferSpot-switch').switchbutton({
                onText: '开',
                offText: '关',
                checked: true,
                onChange: function (checked) {
                    if (checked) {
                        $('#transferSpot').val(1);
                    } else {
                        $('#transferSpot').val(0);
                    }
                }
            });

            $('#purchStart').datetimebox({
                required: true,
                showSeconds: false
            });

            $('#purchEnd').datetimebox({
                required: true,
                showSeconds: false
            });

            $('#deliveryTime').datetimebox({
                showSeconds: false
            });

            $('#portfolioType').combobox({
                valueField: 'id',
                textField: 'value',
                readonly: true,
                data: [
                    {
                        id: '1',
                        value: '指数组合'
                    }, {
                        id: '2',
                        value: '未来权益组合'
                    }, {
                        id: '3',
                        value: '主动管理组合'
                    }, {
                        id: '4',
                        value: '杠杆组合'
                    }
                ]
            });

            $('#envStatus').combobox({
                valueField: 'id',
                textField: 'value',
                data: [
                    {
                        id: '0',
                        value: '下线'
                    }, {
                        id: '1',
                        value: '预发'
                    }, {
                        id: '2',
                        value: '上线'
                    }
                ]
            });

            $('#issuerLogo').filebox({
                buttonText: '选择',
                buttonAlign: 'right'
            });

            $('#futurePortfolio-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: FuturePortfolioMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        FuturePortfolioMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        FuturePortfolioMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#futurePortfolio-datagrid').datagrid('load', {});
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
                    field: 'name',
                    title: '分类名称',
                    width: 100,
                    sortable: true
                }, {
                    field: 'type',
                    title: '类别',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value == 1) {
                            return '币种标签';
                        } else if (value == 2) {
                            return '币对标签';
                        }

                        return '';
                    }
                }, {
                    field: 'choose',
                    title: '选择类型',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value == 1) {
                            return '多选标签';
                        } else if (value == 2) {
                            return '单选标签';
                        }

                        return '';
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
                                + 'onclick="FuturePortfolioMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: FuturePortfolioCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return FuturePortfolioMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // dialogs
            $('#futurePortfolio-dlg').dialog({
                closed: true,
                modal: false,
                width: 1100,
                height: window.innerHeight - 150 || document.documentElement.clientHeight - 150 || document.body.clientHeight - 150,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#futurePortfolio-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: FuturePortfolioMVC.Controller.save
                }]
            });

        },
        bindEvent: function () {
            $('#btn-search').bind('click', FuturePortfolioMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#futurePortfolio-datagrid').datagrid('selectRow', index);

            if (name === "edit") {
                return FuturePortfolioMVC.Controller.edit();
            }

            if (name === "remove") {
                return FuturePortfolioMVC.Controller.remove();
            }
        },
        add: function () {
            var options = FuturePortfolioMVC.Util.getOptions();
            options.title = '新增未来权益组合';
            EasyUIUtils.openAddDlg(options);

            $('#portfolioType').combobox('setValue', '2');
            $('#envStatus').combobox('setValue', '0');
        },
        edit: function () {
            var row = $('#futurePortfolio-datagrid').datagrid('getSelected');
            if (row) {
                var options = FuturePortfolioMVC.Util.getOptions();

                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改未来权益组合[' + options.data.name + ']';

                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#futurePortfolio-datagrid').datagrid('getSelected');
            if (row) {
                var options = FuturePortfolioMVC.Util.getOptions();

                options.url = FuturePortfolioMVC.URLs.remove.url;
                options.rows = [row];
                options.data = {id: row.id};

                EasyUIUtils.remove(options);
            }
        },
        find: function () {
            var params = FuturePortfolioMVC.Util.getQueryParams();

            $('#futurePortfolio-datagrid').datagrid('load', params);
        },
        save: function () {
            var action = $('#modal-action').val();

            var options = FuturePortfolioMVC.Util.getOptions();

            if (action === "edit") {
                var row = $('#futurePortfolio-datagrid').datagrid('getSelected');
                //取得ID传给后台
                options.url = FuturePortfolioMVC.URLs.edit.url + '?id=' + row.id;
            }
            else {
                //添加操作
                options.url = FuturePortfolioMVC.URLs.add.url;
            }

            //修改后刷新列表
            EasyUIUtils.save(options);

            return;
        }

    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#futurePortfolio-dlg',
                formId: '#futurePortfolio-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                url: null,
                gridId: '#futurePortfolio-datagrid',
                gridUrl: FuturePortfolioMVC.URLs.list.url
            };
        },
        getQueryParams: function () {
            var name = $('#futurePortfolio-name').textbox('getValue');
            var type = $('#futurePortfolio-type').combobox('getValue');
            var choose = $('#futurePortfolio-choose').combobox('getValue');

            var params = {
                name: name,
                type: type,
                choose: choose
            };

            return params;
        }
    }
};