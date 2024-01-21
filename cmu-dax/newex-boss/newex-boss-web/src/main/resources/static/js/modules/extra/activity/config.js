$(function () {
    ActivityConfig.init();
});

var ActivityConfig = {
    init: function () {
        ActivityConfigMVC.View.initControl();
        ActivityConfigMVC.View.bindEvent();

    }
};

var ConfigCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/activity/mining/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
};

var ActivityConfigMVC = {
    URLs: {
        Search: {
            url: ConfigCommon.baseUrl+'config',
            method: 'GET'
        },
        Save:{
            url:ConfigCommon.baseUrl+'add',
            method:'POST'
        },
        Edit:{
            url: ConfigCommon.baseUrl + 'editConfig',
            method:'POST'
        },

    },
    Model: {},
    View: {
        initControl: function () {
           $('#config-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                rownumbers: true,
                sortOrder: 'desc',
                pageSize: 50,
                pageNumber: 1,
               url: ActivityConfigMVC.URLs.Search.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#config-datagrid');
                    }
                }, '-', {
                    iconCls: 'icon-add',
                    handler: function () {
                        ActivityConfigMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit',
                    handler: function () {
                        ActivityConfigMVC.Controller.edit();
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
                        field: 'actId',
                        title: 'ID',
                        width: 100,
                        sortable: true,


                    }, {
                        field: 'actKey',
                        title: '活动key',
                        width: 100,
                    }, {
                        field: 'actName',
                        title: '活动名称',
                        width: 100,
                        sortale: true,
                    },{
                        field: 'status',
                        title: '状态',
                        width: 100,
                        formatter:function(val){
                            if(val===0){
                                return "未开始"
                            }
                            if(val===1){
                                return "正进行"
                            }
                            if(val===2){
                                return "已结束"
                            }
                        }
                    },
                    {
                        field: 'startDate',
                        title: '开始时间',
                        width: 100,
                        formatter: function (val) {
                            return moment(new Date(val)).format('YYYY-MM-DD HH:mm:ss')
                        }
                    }, {
                        field: 'endDate',
                        title: '结束时间',
                        width: 100,
                        formatter: function (val) {
                            return moment(new Date(val)).format('YYYY-MM-DD HH:mm:ss')
                        }
                    }
                    , {
                        field: 'whileList',
                        title: '白名单',
                        width: 100,
                        sortable: true
                    }
                    , {
                        field: 'actSort',
                        title: '排序',
                        width: 100,
                        sortable: true
                    }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return ActivityConfigMVC.Controller.edit();
                },
                onLoadSuccess: function () { //加载完成后,设置选中第一项
                    $('.pagination-page-list').hide();

                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });
            $('#config-dlg').dialog({
                closed: true,
                modal: false,
                width: 600,
                height: 400,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#config-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: ActivityConfigMVC.Controller.save
                }]
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', ActivityConfigMVC.Controller.find);
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#config-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return ActivityConfigMVC.Controller.edit();
            }
        },
        add: function () {
            var options = ActivityConfigMVC.Util.getOptions();
            options.title = '新增活动配置';
            EasyUIUtils.openAddDlg(options);
            $('#propId').textbox('readonly', true);
        },
        save: function () {
            var options = {
                gridId: null,
                gridUrl: ActivityConfigMVC.URLs.Search.url,
                dlgId: "#config-dlg",
                formId: "#config-form",
                method: 'POST',
                url: null,
                callback: function () {
                }
            };

            options.url = ActivityConfigMVC.URLs.Edit.url;
            options.gridId = '#config-datagrid';
            return EasyUIUtils.save(options);
        },
        edit:function () {
            var row = $('#config-datagrid').datagrid('getSelected');
            $('#propId').textbox('readonly', true);
            if (row) {
                var options = ActivityConfigMVC.Util.getOptions();
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
                dlgId: '#config-dlg',
                formId: '#config-form',
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