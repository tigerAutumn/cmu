$(function () {
    VRFutures.init();
});

var VRFutures = {
    init: function () {
        VRFuturesMVC.View.initControl();
        VRFuturesMVC.View.bindEvent();
        VRFuturesMVC.View.bindValidate();
    }
};

var VRFuturesCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/vr/futures/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/'
};

var VRFuturesMVC = {
    URLs: {
        add: {
            url: VRFuturesCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: VRFuturesCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: VRFuturesCommon.baseUrl + 'list',
            method: 'GET'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {

            $('#vrfutures-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: VRFuturesMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        VRFuturesMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        VRFuturesMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#vrfutures-datagrid');
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
                    title: '合约ID',
                    width: 50,
                    sortable: true
                }, {
                    field: 'categoryId',
                    title: '合约名称',
                    width: 100,
                    sortable: true
                }, {
                    field: 'templateId',
                    title: '指数价格',
                    width: 100,
                    sortable: true
                }, {
                    field: 'publisherId',
                    title: '币币价格',
                    width: 100,
                    sortable: true
                }, {
                    field: 'uid',
                    title: '最新成交价',
                    width: 200,
                    sortable: true,
                }, {
                    field: 'locale',
                    title: '交割结算价',
                    width: 100,
                    sortable: true,
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
                        }];
                        var buttons = [];
                        for (var i = 0; i < icons.length; i++) {
                            var tmpl = '<a href="#" title ="${title}" '
                                + 'onclick="VRFuturesMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: VRFuturesCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return VRFuturesMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // dialogs
            $('#vrfutures-dlg').dialog({
                closed: true,
                modal: false,
                width: 860,
                height: 500,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#vrfutures-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: VRFuturesMVC.Controller.save
                }]
            });

        },
        bindEvent: function () {
            $('#btn-search').bind('click', VRFuturesMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#vrfutures-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return VRFuturesMVC.Controller.edit();
            }
        },
        add: function () {
            var options = VRFuturesMVC.Util.getOptions();
            options.title = '新增合约';
            EasyUIUtils.openAddDlg(options);
            $('#categoryId').combotree('setValue', "0");
            $('#templateId').combobox('setValue', "-1");
            $('#locale').combobox('setValue', "-1");
        },
        edit: function () {
            var row = $('#vrfutures-datagrid').datagrid('getSelected');
            if (row) {
                var options = VRFuturesMVC.Util.getOptions();

                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改合约[' + options.data.title + ']';

                window.editor.html(options.data.content);

                //console.log(options);
                EasyUIUtils.openEditDlg(options);

            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        find: function () {
            var newsCategory = $("#news-category").combobox('getValue');
            var newsLocale = $("#news-locale").combobox('getValue');
            var newsTitle = $("#news-title").val();
            var url = VRFuturesMVC.URLs.list.url + '?categoryId=' + newsCategory + '&locale=' + newsLocale + '&title=' + newsTitle;
            EasyUIUtils.loadToDatagrid('#vrfutures-datagrid', url)
        },
        save: function () {
            var action = $('#modal-action').val();

            var options = {
                gridId: null,
                gridUrl: VRFuturesMVC.URLs.list.url,
                dlgId: "#vrfutures-dlg",
                formId: "#vrfutures-form",
                url: null,
                callback: function () {
                }
            };

            if (action === "edit") {
                var row = $('#vrfutures-datagrid').datagrid('getSelected');
                //取得ID传给后台
                options.url = VRFuturesMVC.URLs.edit.url + '?id=' + row.id;
            } else {
                //添加操作
                options.url = VRFuturesMVC.URLs.add.url;
                options.gridId = '#vrfutures-datagrid';
            }
            options.callback = function (result) {
                if (!result.code) {
                    EasyUIUtils.reloadDatagrid('#vrfutures-datagrid');
                }
            }
            //修改后刷新列表
            EasyUIUtils.save(options);

            return;
        }

    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#vrfutures-dlg',
                formId: '#vrfutures-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                gridId: null,
            };
        }
    }
};