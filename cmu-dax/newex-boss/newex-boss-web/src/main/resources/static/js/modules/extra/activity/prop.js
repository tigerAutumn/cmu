$(function () {
    ActivityProp.init();
});

var ActivityProp = {
    init: function () {
        PropMVC.View.initControl();
        PropMVC.View.bindEvent();

    }
};

var PropCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/activity/mining/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
};

var PropMVC = {
    URLs: {
        Search: {
            url: PropCommon.baseUrl+'getProp',
            method: 'GET'
        },
        Save:{
            url:PropCommon.baseUrl+'add',
            method:'POST'
        },
        Edit:{
            url:PropCommon.baseUrl+'editProp',
            method:'POST'
        },

    },
    Model: {},
    View: {
        initControl: function () {
            $('#prop-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: false,
                rownumbers: true,
                sortOrder: 'desc',
                pageSize: 50,
                pageNumber: 1,
                url: PropMVC.URLs.Search.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#prop-datagrid')
                    }
                }, '-', {
                    iconCls: 'icon-add',
                    handler: function () {
                        PropMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit',
                    handler: function () {
                        PropMVC.Controller.edit();
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
                        field: 'propId',
                        title: 'ID',
                        width: 100,
                        sortable: true
                    }, {
                        field: 'actKey',
                        title: '活动key',
                        width: 100
                    }, {
                        field: 'activityPropKey',
                        title: '活动参数Key',
                        width: 100
                    }, {
                        field: 'activityPropValue',
                        title: '活动参数Value',
                        width: 100
                    }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return PropMVC.Controller.edit();
                },
                onLoadSuccess: function () { //加载完成后,设置选中第一项
                    $('.pagination-page-list').hide();

                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });
            $('#prop-dlg').dialog({
                closed: true,
                modal: false,
                width: 600,
                height: 300,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#prop-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: PropMVC.Controller.save
                }]
            });
        },
        bindEvent: function () {
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#prop-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return PropMVC.Controller.edit();
            }
        },
        add: function () {
            var options = PropMVC.Util.getOptions();
            options.title = '新增活动';
            EasyUIUtils.openAddDlg(options);
        },
        save: function () {
            var options = {
                gridId: null,
                gridUrl: PropMVC.URLs.Search.url,
                dlgId: "#prop-dlg",
                formId: "#prop-form",
                url: null,
                callback: function () {
                }
            };
            options.url = PropMVC.URLs.Edit.url;
            options.callback = function (result) {
                if (!result.code) {
                    EasyUIUtils.reloadDatagrid('#prop-datagrid');
                }
            };
            return EasyUIUtils.save(options);
        },
        edit:function () {
            var row = $('#prop-datagrid').datagrid('getSelected');
            if (row) {
                var options = PropMVC.Util.getOptions();
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
                dlgId: '#prop-dlg',
                formId: '#prop-form',
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