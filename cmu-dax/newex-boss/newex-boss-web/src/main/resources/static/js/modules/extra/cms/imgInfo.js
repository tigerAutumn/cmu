$(function () {
    ImgInfo.init();
});

var ImgInfo = {
    init: function () {
        ImgInfoMVC.View.initControl();
        ImgInfoMVC.View.bindEvent();
        ImgInfoMVC.View.bindValidate();
    }
};

var ImgInfoCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/extra/cms/news/img-info/',
    uploadUrl: BossGlobal.ctxPath + '/v1/boss/upload/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/'
};

var ImgInfoMVC = {
    URLs: {
        add: {
            url: ImgInfoCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: ImgInfoCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: ImgInfoCommon.baseUrl + 'list',
            method: 'GET'
        },
        img: {
            url: ImgInfoCommon.uploadUrl + 'public/upload',
            method: 'GET'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {

            $('#imgUrl-file').filebox({
                buttonText: '选择',
                buttonAlign: 'right',
                prompt: '选择图片',
                onChange: function (newVal, oldVal) {
                    if (newVal) {
                        ImgInfoMVC.Util.uploadImg('#imgUrl-file', '#imgUrl');
                    }
                }
            });

            $('#user-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: ImgInfoMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        ImgInfoMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        ImgInfoMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#user-datagrid').datagrid('load', {});
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
                    title: 'ID',
                    width: 50,
                    sortable: true
                }, {
                    field: 'name',
                    title: '名称',
                    width: 150,
                    sortable: true
                }, {
                    field: 'imgUrl',
                    title: '图片',
                    width: 150,
                    sortable: true
                }, {
                    field: 'createdDate',
                    title: '创建时间',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                    }
                }, {
                    field: 'updatedDate',
                    title: '修改时间',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                    }
                }, {
                    field: 'options',
                    title: '操作',
                    width: 100,
                    formatter: function (value, row, index) {
                        var icons = [{
                            "name": "edit",
                            "title": "编辑"
                        }];
                        var buttons = [];
                        for (var i = 0; i < icons.length; i++) {
                            var tmpl = '<a href="#" title ="${title}" '
                                + 'onclick="ImgInfoMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: ImgInfoCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return ImgInfoMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // dialogs
            $('#user-dlg').dialog({
                closed: true,
                modal: false,
                width: 800,
                height: 300,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#user-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: ImgInfoMVC.Controller.save
                }]
            });

        },
        bindEvent: function () {
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#user-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return ImgInfoMVC.Controller.edit();
            }
        },
        add: function () {
            var options = ImgInfoMVC.Util.getOptions();
            options.title = '新增图片';
            EasyUIUtils.openAddDlg(options);
        },
        edit: function () {
            var row = $('#user-datagrid').datagrid('getSelected');
            if (row) {
                var options = ImgInfoMVC.Util.getOptions();

                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改图片[' + options.data.name + ']';

                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        save: function () {
            var action = $('#modal-action').val();

            var options = ImgInfoMVC.Util.getOptions();

            if (action === "edit") {
                var row = $('#user-datagrid').datagrid('getSelected');
                //取得ID传给后台
                options.url = ImgInfoMVC.URLs.edit.url + '?id=' + row.id;
            } else {
                //添加操作
                options.url = ImgInfoMVC.URLs.add.url;
            }

            //修改后刷新列表
            EasyUIUtils.save(options);

            return;
        }

    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#user-dlg',
                formId: '#user-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                url: null,
                gridId: '#user-datagrid',
                gridUrl: ImgInfoMVC.URLs.list.url
            };
        },
        uploadImg: function (fileBoxId, fileImgId) {
            var files = $(fileBoxId).filebox('files');

            var formData = new FormData();
            formData.append("file", files[0]);

            //调用apicontroller后台action方法，将form数据传递给后台处理。contentType必须设置为false,否则chrome和firefox不兼容
            $.ajax({
                url: ImgInfoMVC.URLs.img.url,
                type: 'POST',
                data: formData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (data) {
                    if (data.code == 0) {
                        $(fileImgId).val(data.data.fileName);
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
        }
    }
};