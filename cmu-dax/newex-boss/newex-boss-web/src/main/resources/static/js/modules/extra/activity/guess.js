$(function () {
    ActivityGuess.init();
});

var ActivityGuess = {
    init: function () {
        GuessMVC.View.initControl();
        GuessMVC.View.bindEvent();
    }
};

var GuessCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/activity/guess/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
};

var GuessMVC = {
    URLs: {
        Search: {
            url: GuessCommon.baseUrl + 'list',
            method: 'GET'
        },
        Save: {
            url: GuessCommon.baseUrl + 'add',
            method: 'POST'
        },
        Edit: {
            url: GuessCommon.baseUrl + 'editProp',
            method: 'POST'
        },

    },
    Model: {},
    View: {
        initControl: function () {
            $('#guess-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: false,
                rownumbers: true,
                sortOrder: 'desc',
                pageSize: 50,
                pageNumber: 1,
                url: GuessMVC.URLs.Search.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#guess-datagrid')
                    }
                }, '-', {
                    iconCls: 'icon-add',
                    handler: function () {
                        GuessMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit',
                    handler: function () {
                        GuessMVC.Controller.edit();
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
                        field: 'minPrice',
                        title: '最低价',
                        width: 100
                    }, {
                        field: 'maxPrice',
                        title: '最高价',
                        width: 100
                    }, {
                        field: 'count',
                        title: '数量',
                        width: 100
                    }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return GuessMVC.Controller.edit();
                },
                onLoadSuccess: function () { //加载完成后,设置选中第一项
                    $('.pagination-page-list').hide();

                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });
            $('#guess-dlg').dialog({
                closed: true,
                modal: false,
                width: 600,
                height: 300,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#guess-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: GuessMVC.Controller.save
                }]
            });
        },
        bindEvent: function () {
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#guess-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return GuessMVC.Controller.edit();
            }
        },
        add: function () {
            var options = GuessMVC.Util.getOptions();
            options.title = '新增活动';
            EasyUIUtils.openAddDlg(options);
        },
        save: function () {
            var options = {
                gridId: null,
                gridUrl: GuessMVC.URLs.Search.url,
                dlgId: "#guess-dlg",
                formId: "#guess-form",
                url: null,
                callback: function () {
                }
            };
            options.url = GuessMVC.URLs.Save.url;
            return EasyUIUtils.save(options);
        },
        edit: function () {
            //$('#modal-action').textbox('SetValue','edit');
            var row = $('#guess-datagrid').datagrid('getSelected');
            $("#propId").textbox('readonly', true);
            if (row) {
                var options = GuessMVC.Util.getOptions();
                options.iconCls = 'icon-edit';
                options.data = row;
                options.title = '修改[' + options.data.actKey + ']';
                EasyUIUtils.openEditDlg(options);

            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#guess-dlg',
                formId: '#guess-form',
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