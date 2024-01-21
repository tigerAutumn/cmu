$(function () {
    MineMenu.init();
});

var MineMenu = {
    init: function () {
        MineMVC.View.initControl();
        MineMVC.View.bindEvent();
        MineMVC.View.bindValidate();
        MineMVC.View.initData();

    }
};

var MineCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/workorder/mine/',
    workMenubaseUrl: BossGlobal.ctxPath + '/v1/boss/workorder/workMenu/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/'
};

var MineMVC = {
    URLs: {
        list: {
            url: MineCommon.baseUrl + 'list',
            method: 'GET'
        },
        statusCombox: {
            url: MineCommon.baseUrl + 'status',
            method:
                'GET'
        },
        getWorkTree: {
            url: MineCommon.workMenubaseUrl + 'getWorkMenuTree',
            method: 'GET'
        }
    },
    Model: {
        marginOpen: {}
    },
    View: {
        initControl: function () {

            $.get(MineMVC.URLs.getWorkTree.url, "", function (_data) {
                $("#mine-classify").combotree({
                    data: _data.data,
                    valueField: "id",
                    textField: "text",
                    panelHeight: 300,
                    editable: true
                });
            });

            $.get(MineMVC.URLs.statusCombox.url, "", function (_data) {
                $("#mine-status").combobox({
                    data: _data.data,
                    valueField: "code",
                    textField: "name",
                    panelHeight: 300,
                    editable: true,
                    onSelect: function (record) {
                        $.tabIndex = $('.mine-status').index(this);
                        $(".mine-status").eq($.tabIndex).textbox("setValue", record.id)
                    },
                    onLoadSuccess: function () { //加载完成后,设置选中第一项
                        var val = $(this).combobox("getData");

                        MineMVC.Model.marginOpen = val || [];
                    }

                });
            });

            $('#mine-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                pageSize: 50,
                url: MineMVC.URLs.list.url,
                toolbar: [{
                    text: '全部',
                    iconCls: 'icon-reload',
                    handler: function () {
                        $("#mine-datagrid").datagrid({
                            pageNumber: 1,
                            pageSize: 50,
                            url: MineMVC.URLs.list.url
                        });
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
                    title: '工单ID',
                    width: 150,
                    sortable: true
                }, {
                    field: 'menuId',
                    title: '菜单ID',
                    width: 150,
                    sortable: true
                },{
                    field: 'source',
                    title: '派发类型',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {

                        if (value===0) {
                            return "派发";
                        } else  {
                            return "转发";
                        }
                    }
                }, {
                    field: 'content',
                    title: '内容',
                    width: 150,
                    sortable: true,
                }, {
                    field: 'userName',
                    title: '用户姓名',
                    width: 150,
                    sortable: true
                }, {
                    field: 'userId',
                    title: ' 用户ID',
                    width: 150,
                    sortable: true
                }, {
                    field: 'userPhone',
                    title: '手机号',
                    width: 100,
                    sortable: true
                }, {
                    field: 'fromType',
                    title: '是否为客服创建',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return value === 0 ? "启用" : "运营";
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
                    field: 'responseTime',
                    title: '响应时间',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                    }
                }, {
                    field: 'updatedDate',
                    title: '更新时间',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                    }
                }, {
                    field: 'acceptTime',
                    title: '处理时长',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                    }
                }, {
                    field: 'status',
                    title: '状态',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        //调用后台下来，便利后进行显示
                        var currencies = MineMVC.Model.marginOpen;
                        for (var i = 0; i < currencies.length; i++) {
                            if (currencies[i].code === value) {
                                return currencies[i].name;
                            }
                        }
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                },
                onLoadSuccess: function () {
                    $('.pagination-page-list').hide();
                }
            });

            var pager = $("#mine-datagrid").datagrid("getPager");
            if (pager) {
                $(pager).pagination({
                    onSelectPage: function (pageNumber, pageSize) {
                        var url = MineMVC.Util.getSearchUrl();
                        $("#mine-datagrid").datagrid({
                            pageNumber: pageNumber,
                            pageSize: pageSize,
                            url: url
                        });

                    }
                });
            }

        },
        bindEvent: function () {
            $('#btn-search').bind('click', MineMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#WorkMenu-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return MineMVC.Controller.edit();
            }
        }
        ,
        showDetail: function () {
            //    MineMVC.Util.isRowSelected(function (row) {
            $('#reset-mine-dlg').dialog('open').dialog('center');
            setInterval(function () {
                $('#mine-tab1').datagrid('reload');
            }, 10000);

            //  });
        },
        find: function () {
            var url = MineMVC.Util.getSearchUrl();
            $("#mine-datagrid").datagrid({
                pageNumber: 1,
                pageSize: 50,
                url: url
            });
        }
    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#MineMVC-dlg',
                formId: '#MineMVC-form',
                actId: '#modal-action',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                gridId: null,
            };
        },
        isRowSelected: function (func) {
            var row = $('#mine-datagrid').datagrid('getSelected');
            if (row) {
                func(row);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        }
        ,
        getSearchUrl: function () {
            var type = $("#mine-type").combobox('getValue');
            var classify = $("#mine-classify").combobox('getValue');
            var status = $("#mine-status").combobox('getValue');
            var workId = $("#mine-id").textbox('getValue');
            var userId = $("#mine-userid").textbox('getValue');

            return url = MineMVC.URLs.list.url + '?type=' + type + '&workId=' + workId + '&userId=' + userId + '&status=' + status + '&classify=' + classify;

        }
    }
};