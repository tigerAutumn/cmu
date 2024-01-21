$(function () {
    RushProgress.init();
});

var RushProgress = {
    init: function () {
        RushProgressMVC.View.initControl();
        RushProgressMVC.View.bindEvent();
    }
};

var RushProgressCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/activity/hundred3/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
};

var RushProgressMVC = {
    URLs: {
        list: {
            url: RushProgressCommon.baseUrl + 'rushProgress/list',
            method: 'GET'
        },
        add: {
            url: RushProgressCommon.baseUrl + 'rushProgress/add',
            method: 'POST'
        },
        edit: {
            url: RushProgressCommon.baseUrl + 'rushProgress/edit',
            method: 'POST'
        },
        remove: {
            url: RushProgressCommon.baseUrl + 'rushProgress/remove',
            method: 'POST'
        },
        export: {
            url: RushProgressCommon.baseUrl + 'rushProgress/export',
            method: 'GET'
        }
    },
    Model: {},
    View: {
        initControl: function () {

            $('#rush-progress-dlg').dialog({
                closed: true,
                modal: false,
                width: 600,
                height: 400,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#rush-progress-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: function () {
                        RushProgressMVC.Controller.save();
                    }
                }]
            });

            $('#rush-progress-datagrid').datagrid({
                method: 'GET',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                sortOrder: 'desc',
                pageSize: 50,
                pageNumber: 1,
                url: RushProgressMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    text: '刷新',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#rush-progress-datagrid')
                    }
                }, '-', {
                    iconCls: 'icon-add',
                    text: '新增',
                    handler: function () {
                        RushProgressMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit',
                    text: '修改',
                    handler: function () {
                        RushProgressMVC.Controller.modify();
                    }
                }, '-', {
                    iconCls: 'icon-remove',
                    text: '删除',
                    handler: function () {
                        RushProgressMVC.Controller.remove();
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
                        sortable: true
                    }, {
                        field: 'periodNo',
                        title: '期数',
                        width: 100
                    }, {
                        field: 'startDate',
                        title: '开始时间',
                        width: 100,
                        formatter: function (value) {
                            return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                        }
                    },
                    {
                        field: 'rushTimes',
                        title: '用户抢购次数',
                        width: 100
                    },
                    {
                        field: 'status',
                        title: '状态',
                        width: 100,
                        formatter: function (value) {
                            if (value === 1) {
                                return "待开始";
                            } else if (value === 2) {
                                return "进行中";
                            } else if (value === 3) {
                                return "已结束";
                            }
                        }
                    },
                    {
                        field: 'createdDate',
                        title: '创建时间',
                        width: 100,
                        formatter: function (value) {
                            return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                        }
                    },
                    {
                        field: 'updatedDate',
                        title: '更新时间',
                        width: 100,
                        formatter: function (value) {
                            return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                        }
                    }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return RushProgressMVC.Controller.modify();
                },
                onLoadSuccess: function () { //加载完成后,设置选中第一项

                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });
        },
        bindEvent: function () {
            // $('#rush-progress-btn-export').on('click', function () {
            //     var queryParam = RushProgressMVC.Util.getQueryParams();
            //     window.location.href = RushProgressMVC.URLs.export.url + "?username=" + queryParam.username;
            // });
            // $('#rush-progress-btn-search').bind('click', function () {
            //     var queryParam = RushProgressMVC.Util.getQueryParams();
            //     $('#rush-progress-datagrid').datagrid('load', queryParam);
            // });
        }
    },
    Controller: {
        add: function () {
            $('#action').val('save');
            var options = RushProgressMVC.Util.getOptions();
            options.title = '新增抢购活动进程信息';
            EasyUIUtils.openAddDlg(options);
        },
        save: function () {
            var options = {
                gridId: null,
                gridUrl: RushProgressMVC.URLs.list.url,
                dlgId: "#rush-progress-dlg",
                formId: "#rush-progress-form",
                method: 'POST',
                url: null,
                callback: function () {
                }
            };

            var action = $('#action').val();
            if (action === 'save') {
                options.url = RushProgressMVC.URLs.add.url;
            } else if (action === 'update') {
                options.url = RushProgressMVC.URLs.edit.url;
            }
            options.gridId = '#rush-progress-datagrid';
            return EasyUIUtils.save(options);
        },
        modify: function () {
            var row = $('#rush-progress-datagrid').datagrid('getSelected');
            if (row) {
                $('#action').val('update');
                var options = RushProgressMVC.Util.getOptions();
                options.iconCls = 'icon-edit';
                var data = {
                    startDate: moment(new Date(row.startDate)).format("YYYY-MM-DD HH:mm:ss"),
                    periodNo: row.periodNo,
                    status: row.status,
                    rushTimes: row.rushTimes,
                    id: row.id
                };
                options.data = data;
                options.title = '修改[' + options.data.id + ']';
                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#rush-progress-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: RushProgressMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#rush-progress-datagrid',
                    gridUrl: RushProgressMVC.URLs.list.url,
                    callback: function (rows) {
                    }
                };
                EasyUIUtils.remove(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#rush-progress-dlg',
                formId: '#rush-progress-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                gridId: null
            };
        },
        // getQueryParams: function () {
        //     var username = $("#rush-progress-username").textbox("getValue");
        //
        //     var params = {
        //         username: username
        //     };
        //     return params;
        // }
    }
};