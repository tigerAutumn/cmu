$(function () {
    Lockup.init();
});

var Lockup = {
    init: function () {
        LockupMVC.View.initControl();
        LockupMVC.View.bindEvent();
    }
};

var LockUpCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/extra/lockup/winlockup/',
    currencyBaseUrl: BossGlobal.ctxPath + '/v1/boss/asset/lockup/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/',
    uploadUrl: BossGlobal.ctxPath + '/v1/boss/upload/',

};

var LockupMVC = {
    URLs: {
        Search: {
            url: LockUpCommon.baseUrl + 'list',
            method: 'GET'
        },
        Save: {
            url: LockUpCommon.baseUrl + 'add',
            method: 'POST'
        },
        Edit: {
            url: LockUpCommon.baseUrl + 'edit',
            method: 'POST'
        },
        Code: {
            url: LockUpCommon.baseUrl + 'code',
            method: 'GET'
        },
        Remove: {
            url: LockUpCommon.baseUrl + 'delete',
            method: 'POST'
        },
        getBrokerList: {
            url: LockUpCommon.brokerBaseUrl + 'list',
            method: 'GET'
        },
        getAllCurrency: {
            url: LockUpCommon.currencyBaseUrl + 'listAllCurrency',
            method: 'GET'
        },
        img: {
            url: LockUpCommon.uploadUrl + 'public/upload',
            method: 'GET'
        },
    },
    Model: {},
    View: {
        initControl: function () {

            // 初始化券商数据
            $("#brokerId").combobox({
                url: LockupMVC.URLs.getBrokerList.url,
                method: 'get',
                valueField: "id",
                textField: "brokerName",
                editable: true,
                value: '',
                prompt: '选择券商',
                panelHeight: 300
            });

            $.get(LockupMVC.URLs.getAllCurrency.url, "", function (_datax) {

                $("#lockCurrency").combobox({
                    data: _datax.data,
                    valueField: "id",
                    textField: "symbol",
                    onLoadSuccess: function () { //加载完成后,设置选中第一项
                        //$('#lockCurrency').combobox('setValue', "-1");
                    }
                });

            });
            $('#bannerUrl-file').filebox({
                buttonText: '选择',
                buttonAlign: 'right',
                prompt: '选择图片',
                onChange: function (newVal, oldVal) {
                    if (newVal) {
                        LockupMVC.Util.uploadImg();
                    }
                }
            });

            var optionsDatagrid = $('#lockup-options-datagrid');
            optionsDatagrid.datagrid({
                scrollbarSize: 0,
                height: 300,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        optionsDatagrid.datagrid('appendRow', {
                            name: 'new name',
                            value: 'value',
                            rule: 'new rule'
                        });
                    }
                }, '-', {
                    iconCls: 'icon-remove1',
                    handler: function () {
                        var row = optionsDatagrid.datagrid('cell');
                        if (row) {
                            optionsDatagrid.datagrid('deleteRow', row.index);
                        }
                    }
                }],
                columns: [[
                    {field: 'name', title: '类型名称', width: 100, sortable: false, editor: 'text'},
                    {field: 'value', title: '收益说明', width: 100, resizable: false, editor: 'text'},
                    {field: 'rule', title: '奖励规则', width: 100, resizable: false, editor: 'text'}
                ]]
            });
            optionsDatagrid.datagrid('enableCellEditing');

            $('#lockup-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: false,
                sortOrder: 'desc',
                pageSize: 50,
                pageNumber: 1,
                url: LockupMVC.URLs.Search.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#lockup-datagrid')
                    }
                }, '-', {
                    iconCls: 'icon-add',
                    handler: function () {
                        LockupMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit',
                    handler: function () {
                        LockupMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-remove',
                    handler: function () {
                        LockupMVC.Controller.remove();
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
                        field: 'biaoshi',
                        title: '活动标识',
                        width: 100,
                        sortable: true,
                    },
                    {
                        field: 'huodongTitle',
                        title: '活动名称',
                        width: 100,
                        sortable: true,
                    },
                    {
                        field: 'startTime',
                        title: '活动时间',
                        width: 100,
                        sortable: true,
                        formatter: function (val) {
                            return moment(new Date(val)).format('YYYY-MM-DD HH:mm:ss');
                        }
                    }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return LockupMVC.Controller.edit();
                },
                onLoadSuccess: function () { //加载完成后,设置选中第一项
                    $('.pagination-page-list').hide();

                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });
            $('#lockup-dlg').dialog({
                closed: true,
                modal: false,
                width: 800,
                height: 600,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#lockup-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: LockupMVC.Controller.save
                }]
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', LockupMVC.Controller.find);
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#channel-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return LockupMVC.Controller.edit();
            }
        },
        find: function () {
            var biaoshi = $('#search-biaoshi').textbox('getValue');
            var title = $('#search-huodongTitle').textbox('getValue');

            var url = LockupMVC.URLs.Search.url + '?biaoshi=' + biaoshi + '&title=' + title;
            $("#lockup-datagrid").datagrid({
                pageNumber: 1,
                pageSize: 50,
                url: url
            });

        },
        add: function () {
            var options = LockupMVC.Util.getOptions();
            options.title = '新增活动';
            $("#modal-action").val("add");
            EasyUIUtils.openAddDlg(options);
            //获取channel code
            //var url = LockupMVC.URLs.Code.url;
            // $.getJSON(url, function (data) {
            //     console.log(data);
            //     if (!data.code) {
            //         $('#channeLink').textbox('setValue', data.data.channeLink);
            //         $('#channelCode').textbox('setValue', data.data.channelCode);
            //     } else {
            //         $.messager.alert('失败', "渠道码错误", 'error');
            //     }
            // })

        },
        save: function () {
            var rows = $('#lockup-options-datagrid').datagrid('getRows');
            $('#options').val(JSON.stringify(rows));
            var action = $('#modal-action').val();
            var options = {
                gridId: null,
                gridUrl: LockupMVC.URLs.Search.url,
                dlgId: "#lockup-dlg",
                formId: "#lockup-form",
                url: null,
                callback: function () {
                }
            };
            options.url = (action === "edit" ? LockupMVC.URLs.Edit.url : LockupMVC.URLs.Save.url);
            options.gridId = '#lockup-datagrid';
            return EasyUIUtils.save(options);
        },
        edit: function () {
            var row = $('#lockup-datagrid').datagrid('getSelected');
            row.endTime =  moment(new Date(row.endTime)).format('YYYY-MM-DD HH:mm:ss');
            row.startTime =  moment(new Date(row.startTime)).format('YYYY-MM-DD HH:mm:ss');
            console.log(row);
            $("#biaoshi").textbox({readonly: false});      //设置下拉款为禁用
            $("#modal-action").val("edit");
            if (row) {
                var options = LockupMVC.Util.getOptions();
                options.iconCls = 'icon-edit';
                options.data = row;
                options.title = '修改[' + options.data.biaoshi + ']';
                EasyUIUtils.openEditDlg(options);
                $('#bannerUrl-file').filebox('setText', row.bannerUrl);
                $('#lockup-options-datagrid').datagrid('loadData', $.toJSON(row.options));

            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#channel-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: LockupMVC.URLs.Remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#channel-datagrid',
                    gridUrl: LockupMVC.URLs.Search.url,
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
        uploadImg: function () {
            var files = $("#bannerUrl-file").filebox('files');

            var formData = new FormData();
            formData.append("file", files[0]);

            //调用apicontroller后台action方法，将form数据传递给后台处理。contentType必须设置为false,否则chrome和firefox不兼容
            $.ajax({
                url: LockupMVC.URLs.img.url,
                type: 'POST',
                data: formData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (data) {
                    if (data.code == 0) {
                        $('#bannerUrl').val(data.data.fileName);
                    } else {
                        $.messager.alert('提示', data.responseJSON.msg, 'info');
                    }
                },
                error: function (data) {
                    if (data.responseJSON.code == "403") {
                        $.messager.alert('提示', data.responseJSON.msg, 'info');

                    } else {
                        $.messager.alert('提示', 'error', 'info');
                    }
                }
            });
        },
        getOptions: function () {
            return {
                dlgId: '#lockup-dlg',
                formId: '#lockup-form',
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