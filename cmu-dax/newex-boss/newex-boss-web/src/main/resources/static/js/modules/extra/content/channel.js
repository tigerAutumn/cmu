$(function () {
    Channel.init();
});

var Channel = {
    init: function () {
        ChannelMVC.View.initControl();
        ChannelMVC.View.bindEvent();
    }
};

var ChannelCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/extra/content/channel/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',

};

var ChannelMVC = {
    URLs: {
        Search: {
            url: ChannelCommon.baseUrl + 'list',
            method: 'GET'
        },
        Save: {
            url: ChannelCommon.baseUrl + 'add',
            method: 'POST'
        },
        Edit: {
            url: ChannelCommon.baseUrl + 'edit',
            method: 'POST'
        },
        Code: {
            url: ChannelCommon.baseUrl + 'code',
            method: 'GET'
        },
        Remove: {
            url: ChannelCommon.baseUrl + 'delete',
            method: 'POST'
        }
    },
    Model: {},
    View: {
        initControl: function () {
            $('#channel-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: false,
                sortOrder: 'desc',
                pageSize: 50,
                pageNumber: 1,
                url: ChannelMVC.URLs.Search.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#channel-datagrid')
                    }
                }, '-', {
                    iconCls: 'icon-add',
                    handler: function () {
                        ChannelMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit',
                    handler: function () {
                        ChannelMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-remove',
                    handler: function () {
                        ChannelMVC.Controller.remove();
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
                        field: 'channelName',
                        title: '渠道简称',
                        width: 100,
                        sortable: true,
                    },
                    {
                        field: 'channelFullName',
                        title: '渠道全称',
                        width: 100,
                        sortable: true,
                    },
                    {
                        field: 'channelCode',
                        title: '渠道码',
                        width: 100,
                        sortable: true,
                    },
                    {
                        field: 'channeStatus',
                        title: '渠道状态',
                        width: 100,
                        sortable: true,
                    },
                    {
                        field: 'channeLink',
                        title: '渠道链接',
                        width: 100,
                        sortable: true,
                    }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return ChannelMVC.Controller.edit();
                },
                onLoadSuccess: function () { //加载完成后,设置选中第一项
                    $('.pagination-page-list').hide();

                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });
            $('#channel-dlg').dialog({
                closed: true,
                modal: false,
                width: 600,
                height: 400,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#channel-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: ChannelMVC.Controller.save
                }]
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', ChannelMVC.Controller.find);
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#channel-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return ChannelMVC.Controller.edit();
            }
        },
        find: function () {
            var name = $('#search-channelName').textbox('getValue');
            var fullName = $('#search-channelFullName').textbox('getValue');
            var code = $('#search-channelCode').textbox('getValue');

            var url = ChannelMVC.URLs.Search.url + '?channelCode=' + code + '&channelName=' + name + '&channelFullName=' + fullName;
            $("#channel-datagrid").datagrid({
                pageNumber: 1,
                pageSize: 50,
                url: url
            });

        },
        add: function () {
            var options = ChannelMVC.Util.getOptions();
            options.title = '新增渠道';
            $("#modal-action").val("add");
            EasyUIUtils.openAddDlg(options);
            //获取channel code
            var url = ChannelMVC.URLs.Code.url;
            $.getJSON(url, function (data) {
                console.log(data);
                if (!data.code) {
                    $('#channeLink').textbox('setValue', data.data.channeLink);
                    $('#channelCode').textbox('setValue', data.data.channelCode);
                } else {
                    $.messager.alert('失败', "渠道码错误", 'error');
                }
            })

        },
        save: function () {
            var action = $('#modal-action').val();
            var options = {
                gridId: null,
                gridUrl: ChannelMVC.URLs.Search.url,
                dlgId: "#channel-dlg",
                formId: "#channel-form",
                url: null,
                callback: function () {
                }
            };
            options.url = (action === "edit" ? ChannelMVC.URLs.Edit.url : ChannelMVC.URLs.Save.url);
            options.gridId = '#channel-datagrid';
            return EasyUIUtils.save(options);
        },
        edit: function () {
            var row = $('#channel-datagrid').datagrid('getSelected');
            $("#channelId").textbox({readonly: false});      //设置下拉款为禁用
            $("#modal-action").val("edit");
            if (row) {
                var options = ChannelMVC.Util.getOptions();
                options.iconCls = 'icon-edit';
                options.data = row;
                options.title = '修改[' + options.data.channelName + ']';
                EasyUIUtils.openEditDlg(options);

            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#channel-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: ChannelMVC.URLs.Remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#channel-datagrid',
                    gridUrl: ChannelMVC.URLs.Search.url,
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
                dlgId: '#channel-dlg',
                formId: '#channel-form',
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