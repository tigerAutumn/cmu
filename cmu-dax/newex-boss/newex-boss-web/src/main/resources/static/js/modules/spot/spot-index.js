$(function () {
    SpotIndex.init();
});

var SpotIndex = {
    init: function () {
        SpotIndexMVC.View.initControl();
        SpotIndexMVC.View.bindEvent();
        SpotIndexMVC.View.bindValidate();
    }
};

var SpotIndexCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/spot/index/',
    imageUrl: BossGlobal.ctxPath + '/v1/boss/upload/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/'
};

var SpotIndexMVC = {
    URLs: {
        edit: {
            url: SpotIndexCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: SpotIndexCommon.baseUrl + 'list',
            method: 'GET'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {

            $('#status').combobox({
                required: true,
                valueField: 'id',
                textField: 'value',
                data: [
                    {
                        id: '0',
                        value: '上线'
                    }, {
                        id: '1',
                        value: '预发'
                    }, {
                        id: '2',
                        value: '下线'
                    }
                ]
            });

            $('#spotIndex-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: false,
                rownumbers: true,
                pageSize: 50,
                url: SpotIndexMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-edit1',
                    handler: function () {
                        SpotIndexMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#spotIndex-datagrid').datagrid('load', {});
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
                    field: 'symbol',
                    title: '币种ID',
                    width: 100,
                    sortable: true
                }, {
                    field: 'symbolName',
                    title: '币种简称',
                    width: 100,
                    sortable: true
                }, {
                    field: 'status',
                    title: '状态',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value == 0) {
                            return '上线';
                        }

                        if (value == 1) {
                            return '预发';
                        }

                        if (value == 2) {
                            return '下线';
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
                                + 'onclick="SpotIndexMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: SpotIndexCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return SpotIndexMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // dialogs
            $('#spotIndex-dlg').dialog({
                closed: true,
                modal: false,
                width: 700,
                height: 350,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#spotIndex-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: SpotIndexMVC.Controller.save
                }]
            });

        },
        bindEvent: function () {
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#spotIndex-datagrid').datagrid('selectRow', index);

            if (name === "edit") {
                return SpotIndexMVC.Controller.edit();
            }
        },
        edit: function () {
            var row = $('#spotIndex-datagrid').datagrid('getSelected');
            if (row) {
                var options = SpotIndexMVC.Util.getOptions();

                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改指数[' + row.symbolName + ']';

                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        save: function () {
            var options = SpotIndexMVC.Util.getOptions();

            var row = $('#spotIndex-datagrid').datagrid('getSelected');

            //取得ID传给后台
            options.url = SpotIndexMVC.URLs.edit.url + '?id=' + row.id;

            //修改后刷新列表
            EasyUIUtils.save(options);

            return;
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#spotIndex-dlg',
                formId: '#spotIndex-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                url: null,
                gridId: '#spotIndex-datagrid',
                gridUrl: SpotIndexMVC.URLs.list.url
            };
        }
    }
};