$(function () {
    MixIndex.init();
});

var MixIndex = {
    init: function () {
        MixIndexMVC.View.initControl();
        MixIndexMVC.View.bindEvent();
    }
};

var MixIndexCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/portfolio/mix/index/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',

};

var MixIndexMVC = {
    URLs: {
        Search: {
            url: MixIndexCommon.baseUrl + 'list',
            method: 'GET'
        },
        Save: {
            url: MixIndexCommon.baseUrl + 'add',
            method: 'POST'
        },
        Edit: {
            url: MixIndexCommon.baseUrl + 'edit',
            method: 'POST'
        },
        Web: {
            url: MixIndexCommon.baseUrl + 'web',
            method: 'GET'
        },
        Currency: {
            url: MixIndexCommon.baseUrl + 'currency',
            method: 'GET'
        },
        Remove: {
            url: MixIndexCommon.baseUrl + 'delete',
            method: 'POST'
        }
    },
    Model: {},
    View: {
        initControl: function () {
            $("#currency").tagbox({
                url: MixIndexMVC.URLs.Currency.url,
                method: 'get',
                valueField: 'code',
                textField: 'currency',
                limitToList: true,
                hasDownArrow: true,
                prompt: '选择币种',
                onClick: function (value) {
                    MixIndexMVC.Util.addCurencyRow(value);
                },
                onRemoveTag: function (value) {
                    MixIndexMVC.Util.deleteCurencyRow(value);
                }
            });
            $('#mix-index-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: false,
                sortOrder: 'desc',
                pageSize: 50,
                pageNumber: 1,
                url: MixIndexMVC.URLs.Search.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#mix-index-datagrid')
                    }
                }, '-', {
                    iconCls: 'icon-add',
                    handler: function () {
                        MixIndexMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit',
                    handler: function () {
                        MixIndexMVC.Controller.edit();
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
                        hidden: true
                    },
                    {
                        field: 'symbol',
                        title: '指数Id',
                        width: 100
                    },
                    {
                        field: 'symbolName',
                        title: '指数名称',
                        width: 100
                    },
                    {
                        field: 'initialDate',
                        title: '初始时间',
                        width: 100
                    },
                    {
                        field: 'status',
                        title: '状态',
                        width: 100,
                        sortable: true,
                        formatter: function (val) {
                            if (val === 0) {
                                return "上线"
                            }
                            if (val === 1) {
                                return "预发"
                            }
                            if (val === 2) {
                                return "下线"
                            }
                        }
                    },
                    {
                        field: 'currency',
                        title: '币种',
                        width: 100,
                        hidden: true
                    },
                    {
                        field: 'configs',
                        title: '币种组合',
                        width: 100,
                        formatter: function (val) {
                            var result = [];
                            var data = $.toJSON(val);
                            for (var d in data) {
                                result.push(data[d].symbolName);
                            }
                            return result;
                        }
                    }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return MixIndexMVC.Controller.edit();
                },
                onLoadSuccess: function () { //加载完成后,设置选中第一项
                    $('.pagination-page-list').hide();

                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });
            var optionsDatagrid = $('#mix-index-options-datagrid');
            optionsDatagrid.datagrid({
                scrollbarSize: 0,
                singleSelect: true,
                height: 200,
                columns: [[
                    {field: 'symbol', title: 'symbol', width: 150, align: 'center', sortable: true},
                    {field: 'symbolName', title: 'symbolName', width: 150, align: 'center', sortable: true},
                    {field: 'ratio', title: '比例(各项之和为1)', width: 150, align: 'center', resizable: false, editor: 'text'}
                ]]
            });
            optionsDatagrid.datagrid('enableCellEditing');

            $('#mix-index-dlg').dialog({
                closed: true,
                modal: false,
                width: 700,
                height: 400,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#mix-index-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: MixIndexMVC.Controller.save
                }]
            });
        },
        bindEvent: function () {
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#mix-index-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return MixIndexMVC.Controller.edit();
            }
        },
        add: function () {
            var options = MixIndexMVC.Util.getOptions();
            options.title = '新增混合指数';
            $("#modal-action").val("add");
            EasyUIUtils.openAddDlg(options);
            $('#symbol').textbox({readonly: false});
            $('#symbolName').textbox({readonly: false});
            $("#currency").tagbox({disabled: false});
            EasyUIUtils.clearDatagrid('#mix-index-options-datagrid');

        },
        save: function () {
            var rows = $('#mix-index-options-datagrid').datagrid('getData').rows;
            $('#configs').val(JSON.stringify(rows));
            var action = $('#modal-action').val();
            var options = {
                gridId: null,
                gridUrl: MixIndexMVC.URLs.Search.url,
                dlgId: "#mix-index-dlg",
                formId: "#mix-index-form",
                url: null,
                callback: function () {
                }
            };
            options.url = (action === "edit" ? MixIndexMVC.URLs.Edit.url : MixIndexMVC.URLs.Save.url);
            options.gridId = '#mix-index-datagrid';
            return EasyUIUtils.save(options);
        },
        edit: function () {
            var row = $('#mix-index-datagrid').datagrid('getSelected');
            $("#symbol").textbox({readonly: true});
            $("#symbolName").textbox({readonly: true});
            $("#currency").tagbox({disabled: true});
            $("#modal-action").val("edit");
            if (row) {
                var options = MixIndexMVC.Util.getOptions();
                options.iconCls = 'icon-edit';
                options.data = row;
                options.title = '修改[' + options.data.symbolName + ']';
                EasyUIUtils.openEditDlg(options);
                EasyUIUtils.clearDatagrid('#mix-index-options-datagrid');
                $('#mix-index-options-datagrid').datagrid('loadData', $.toJSON(row.configs));

            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#mix-index-dlg',
                formId: '#mix-index-form',
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
        addCurencyRow: function (d) {
            $('#mix-index-options-datagrid').datagrid('appendRow', {
                symbol: d.code,
                symbolName: d.currency,
                ratio: '0.00'
            });
        },
        deleteCurencyRow: function (d) {
            var optionsDatagrid = $('#mix-index-options-datagrid');
            var rows = optionsDatagrid.datagrid('getData').rows;
            for (var i = 0; i < rows.length; i++) {
                if (rows[i].symbol == d) {
                    var rowIndex = optionsDatagrid.datagrid('getRowIndex', rows[i]);
                    optionsDatagrid.datagrid('deleteRow', rowIndex);
                }
            }
        }
    }
};