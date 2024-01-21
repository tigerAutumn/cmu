$(function () {
    KycImg.init();
});

var KycImg = {
    init: function () {
        KycImgMVC.View.initControl();
        KycImgMVC.View.bindEvent();
        KycImgMVC.View.bindValidate();
        KycImgMVC.View.initData();
    }
};

var KycImgCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/users/kyc/img/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/'
};

var KycImgMVC = {
    URLs: {
        edit: {
            url: KycImgCommon.baseUrl + 'edit'
        },
        list: {
            url: KycImgCommon.baseUrl + 'list'
        },
        upload: {
            url: KycImgCommon.baseUrl + 'upload'
        },
        batchUpload: {
            url: KycImgCommon.baseUrl + 'batch-upload'
        },
        add: {
            url: KycImgCommon.baseUrl + 'add'
        },
        delete: {
            url: KycImgCommon.baseUrl + 'delete'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {
            $('#file').filebox({
                // accept: 'image/*,video/*',
                onChange: function () {
                    var userId = $('#userId').textbox('getValue');
                    if (userId == '') {
                        $.messager.alert('提示', 'userId不能为空');
                    } else {
                        KycImgMVC.Util.upload(userId);
                    }
                }, prompt: '选择用户文件'
            });
            $('#img-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: KycImgMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#img-datagrid');
                    }
                }, '-', {
                    iconCls: 'icon-add',
                    handler: function () {
                        KycImgMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit',
                    handler: function () {
                        KycImgMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-remove',
                    text: '删除',
                    handler: function () {
                        KycImgMVC.Controller.delete();
                    }
                }, '-', {
                    iconCls: 'icon-add',
                    text: '批量上传KYC',
                    handler: function () {
                        $('#kyc-dlg').dialog('open');
                        $('#kyc-file').filebox('setValue', '');
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
                    title: 'id',
                    width: 150,
                    sortable: true
                }, {
                    field: 'userId',
                    title: '用户id',
                    width: 150,
                    sortable: true
                }, {
                    field: 'fileName',
                    title: '文件名',
                    width: 150,
                    sortable: true
                }, {
                    field: 'filePath',
                    title: '查看上传文件',
                    width: 150,
                    formatter: function (val) {
                        var imgSrc = KycImgCommon.baseIconUrl + 'download.png';
                        return '<a href="' + val + '" target="_blank"><img src="' + imgSrc + '"/></a>';
                    }
                }, {
                    field: 'note',
                    title: '备注',
                    width: 150
                }, {
                    field: 'fileType',
                    title: '文件类型',
                    width: 150
                }
                ]],
                onDblClickRow: function () {
                    return KycImgMVC.Controller.edit();
                }
            });

            $('#img-dlg').dialog({
                closed: true,
                modal: false,
                width: 700,
                height: 500,
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#img-dlg").dialog('close');
                    }
                }, {
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: KycImgMVC.Controller.save
                }]
            });
            $('#kyc-dlg').dialog({
                closed: true,
                modal: false,
                width: 500,
                height: 300,
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#kyc-dlg").dialog('close');
                    }
                }, {
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: KycImgMVC.Util.batchUpload
                }]
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', KycImgMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#img-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return KycImgMVC.Controller.info();
            }
        },
        add: function () {
            var options = KycImgMVC.Util.getOptions();
            options.title = '上传用户文件';
            EasyUIUtils.openAddDlg(options);
        },
        edit: function () {
            var row = $('#img-datagrid').datagrid('getSelected');
            if (row) {
                var options = KycImgMVC.Util.getOptions();
                options.iconCls = 'icon-edit';
                options.data = row;
                options.title = '修改[' + options.data.fileName + ']';
                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        save: function () {
            var action = $('#modal-action').val();
            var options = {
                gridId: null,
                gridUrl: KycImgMVC.URLs.list.url,
                dlgId: "#img-dlg",
                formId: "#img-form",
                url: null,
                callback: function () {
                }
            };
            options.url = (action === "edit" ? KycImgMVC.URLs.edit.url : KycImgMVC.URLs.add.url);
            options.gridId = '#img-datagrid';
            return EasyUIUtils.save(options);

        },
        find: function () {
            var userId = $("#search_userId").textbox('getValue');
            var url = KycImgMVC.URLs.list.url + '?userId=' + userId;
            EasyUIUtils.loadToDatagrid('#img-datagrid', url)
        },
        delete: function () {
            var row = $('#img-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: KycImgMVC.URLs.delete.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#img-datagrid',
                    gridUrl: KycImgMVC.URLs.list.url,
                    callback: function (rows) {
                    }
                };
                EasyUIUtils.remove(options);
            }
        }
    },
    Util: {
        getSearchUrl: function () {
            var userId = $("#search_userId").textbox('getValue');
            return KycImgMVC.URLs.list.url + '?userId=' + userId;

        },
        getOptions: function () {
            return {
                dlgId: '#img-dlg',
                formId: '#img-form',
                actId: '#modal-action',
                rowId: '#id',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                gridId: null,
            };
        },
        upload: function (userId) {
            console.log(userId);
            var formData = new FormData($("#img-form")[0]);
            $.ajax({
                url: KycImgMVC.URLs.upload.url + '?userId=' + userId,
                type: 'post',
                data: formData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (d) {
                    $.messager.progress('close');
                    console.log(d.data);
                    $('#filePath').textbox('setValue', d.data);
                },
                error: function (data) {
                    if (data.responseJSON.code == "403") {
                        $.messager.alert('提示', data.responseJSON.msg, 'info');

                    } else {
                        $.messager.alert('错误', 'error', 'error');
                    }
                }
            });
        },
        batchUpload: function () {
            var formData = new FormData($("#kyc-form")[0]);
            $.ajax({
                url: KycImgMVC.URLs.batchUpload.url,
                type: 'post',
                data: formData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function () {
                    $('#img-datagrid').datagrid('load', {});
                },
                error: function (data) {
                    if (data.responseJSON.code == "403") {
                        $.messager.alert('提示', data.responseJSON.msg, 'info');

                    } else {
                        $.messager.alert('错误', 'error', 'error');
                    }
                }
            });
        }
    }
};