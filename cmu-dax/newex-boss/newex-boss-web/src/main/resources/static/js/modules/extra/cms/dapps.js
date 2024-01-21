$(function () {
    DApps.init();
});

var DApps = {
    init: function () {
        DAppsMVC.View.initControl();
        DAppsMVC.View.bindEvent();
    }
};

var DAppsCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/extra/cms/dApps/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    commonUrl: BossGlobal.ctxPath + '/v1/boss/common/',
    file: BossGlobal.ctxPath + '/v1/boss/upload/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/'

};

var DAppsMVC = {
    URLs: {
        Search: {
            url: DAppsCommon.baseUrl + 'info',
            method: 'GET'
        },
        Save: {
            url: DAppsCommon.baseUrl + 'add',
            method: 'POST'
        },
        Edit: {
            url: DAppsCommon.baseUrl + 'edit',
            method: 'POST'
        },
        Currency: {
            url: DAppsCommon.commonUrl + 'c2c/currency',
            method: 'GET'
        },
        Product: {
            url: DAppsCommon.commonUrl + 'product',
            method: 'GET'
        },
        Upload: {
            url: DAppsCommon.file + 'public/upload',
            method: 'POST'
        },
        Language: {
            url: DAppsCommon.commonUrl + 'lang'
        },
        getBrokerList: {
            url: DAppsCommon.brokerBaseUrl + 'list',
            method: 'GET'
        }
    },
    Model: {},
    View: {
        initControl: function () {
            $('#file').filebox({
                accept: 'image/*',
                onChange: function () {
                    DAppsMVC.Util.upload();
                }, prompt: '选择APP封面图片', buttonText: '选择封面图片',

            });

            $("#brokerId").combobox({
                url: DAppsMVC.URLs.getBrokerList.url,
                method: 'get',
                valueField: "id",
                textField: "brokerName",
                editable: true,
                value: '',
                prompt: '选择券商',
                panelHeight: 300
            });
            $('#currency').combobox({
                url: DAppsMVC.URLs.Currency.url,
                method: 'get',
                valueField: "symbol",
                textField: "symbol",
                editable: true,
                value: '',
                prompt: '选择币种',
                panelHeight: 300
            });
            $('#locale').combobox({
                url: DAppsMVC.URLs.Language.url,
                method: 'get',
                valueField: "code",
                textField: "code",
                editable: true,
                value: '',
                prompt: '选择语言',
                panelHeight: 300
            });

            $('#dapps-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: false,
                rownumbers: false,
                sortOrder: 'desc',
                pageSize: 50,
                pageNumber: 1,
                url: DAppsMVC.URLs.Search.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#dapps-datagrid')
                    }
                }, '-', {
                    iconCls: 'icon-add',
                    handler: function () {
                        DAppsMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit',
                    handler: function () {
                        DAppsMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-remove',
                    handler: function () {
                        DAppsMVC.Controller.remove();
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
                        title: 'ID',
                        width: 100,
                        sortable: true,
                    },
                    {
                        field: 'locale',
                        title: '语言',
                        width: 100,
                        sortable: true,
                    },
                    {
                        field: 'status',
                        title: '状态',
                        width: 100,
                        formatter: function (val) {
                            if (val === 0) {
                                return "未上线"
                            }
                            if (val === 1) {
                                return "已上线"
                            }
                            if (val === 2) {
                                return "预发"
                            }
                        }
                    },
                    {
                        field: 'title',
                        title: 'DAPPS名称',
                        width: 100,
                        sortable: true,
                    },
                    {
                        field: 'imageUrl',
                        title: '封面图',
                        width: 100
                    },
                    {
                        field: 'pcLink',
                        title: '产品链接(PC)',
                        width: 100
                    },
                    {
                        field: 'appLink',
                        title: '产品链接(APP)',
                        width: 100
                    },
                    {
                        field: 'product',
                        title: '交易币对',
                        width: 100,
                        sortable: true
                    }, {
                        field: 'currency',
                        title: '法币币种',
                        width: 100,
                        sortable: true
                    },
                    {
                        field: 'sort',
                        title: '排序',
                        width: 100,
                        sortable: true
                    },
                    {
                        field: 'text',
                        title: '解释文案',
                        width: 100,
                        sortable: true
                    }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return DAppsMVC.Controller.edit();
                },
                onLoadSuccess: function () { //加载完成后,设置选中第一项
                    $('.pagination-page-list').hide();

                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });
            $('#dapps-dlg').dialog({
                closed: true,
                modal: false,
                width: 800,
                height: 500,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#dapps-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: DAppsMVC.Controller.save
                }]
            });

            $("#product").combobox({
                url: DAppsMVC.URLs.Product.url,
                method: 'get',
                valueField: 'code',
                textField: 'code',
                limitToList: true,
                hasDownArrow: true,
                prompt: '选择币对',
                panelHeight: 300,

                tagStyler: function (value) {
                }
            })
        },
        bindEvent: function () {
            $('#btn-search').bind('click', DAppsMVC.Controller.find);
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#dapps-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return DAppsMVC.Controller.edit();
            }
        },
        find: function () {
            var url = DAppsMVC.Util.getSearchUrl();
            $("#dapps-datagrid").datagrid({
                pageNumber: 1,
                pageSize: 50,
                url: url
            });

        },
        add: function () {
            var options = DAppsMVC.Util.getOptions();
            options.title = '新增DApp';
            $("#modal-action").val("add");
            EasyUIUtils.openAddDlg(options);
            $("#brokerId").combobox('readonly', false);
        },
        save: function () {
            var action = $('#modal-action').val();
            var options = {
                gridId: null,
                gridUrl: DAppsMVC.URLs.Search.url,
                dlgId: "#dapps-dlg",
                formId: "#dapps-form",
                url: null,
                callback: function () {
                }
            };
            options.url = (action === "edit" ? DAppsMVC.URLs.Edit.url : DAppsMVC.URLs.Save.url);
            options.gridId = '#dapps-datagrid';
            return EasyUIUtils.save(options);
        },
        edit: function () {
            var row = $('#dapps-datagrid').datagrid('getSelected');
            $("#DAppsId").textbox({readonly: false});      //设置下拉款为禁用
            $("#modal-action").val("edit");
            if (row) {
                var options = DAppsMVC.Util.getOptions();
                options.iconCls = 'icon-edit';
                options.data = row;
                options.title = '修改[' + options.data.title + ']';
                EasyUIUtils.openEditDlg(options);

                $("#brokerId").combobox('readonly', true);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#dapps-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: DAppsMVC.URLs.Remove.url,
                    data: {
                        id: row.actId
                    },
                    gridId: '#dapps-datagrid',
                    gridUrl: DAppsMVC.URLs.Search.url,
                    callback: function (rows) {
                    }
                };
                EasyUIUtils.remove(options);
            }
        }

    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#dapps-dlg',
                formId: '#dapps-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                gridId: null,
            };
        },
        upload: function () {
            var formData = new FormData($("#dapps-form")[0]);
            $.ajax({
                url: DAppsMVC.URLs.Upload.url,
                type: 'post',
                data: formData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                beforeSend: function () {
                    $.messager.progress({
                        title: '请稍等...',
                        text: '正在上传图片...',
                    });
                },
                success: function (d) {
                    $.messager.progress('close');
                    $('#imageUrl').textbox('setValue', d.data.fileName);
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