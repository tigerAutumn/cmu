$(function () {
    AppDownloadPage.init();
});

var AppDownloadPage = {
    init: function () {
        AppDownloadPageMVC.View.initControl();
        AppDownloadPageMVC.View.bindEvent();
        AppDownloadPageMVC.View.bindValidate();
    }
};

var AppDownloadPageCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/extra/cms/news/app-download-page/',
    uploadUrl: BossGlobal.ctxPath + '/v1/boss/upload/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    templateUrl: BossGlobal.ctxPath + '/v1/boss/extra/cms/news/templates/',
    commonUrl: BossGlobal.ctxPath + '/v1/boss/common/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/'
};

var AppDownloadPageMVC = {
    URLs: {
        add: {
            url: AppDownloadPageCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: AppDownloadPageCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: AppDownloadPageCommon.baseUrl + 'list',
            method: 'GET'
        },
        img: {
            url: AppDownloadPageCommon.uploadUrl + 'public/upload',
            method: 'GET'
        },
        template: {
            url: AppDownloadPageCommon.templateUrl + 'list/all',
            method: 'GET'
        },
        lang:{
            url: AppDownloadPageCommon.commonUrl + 'lang',
            method:'GET'
        },
        getBrokerList: {
            url: AppDownloadPageCommon.brokerBaseUrl + 'list',
            method: 'GET'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {

            $.get(AppDownloadPageMVC.URLs.lang.url, "", function (_datax) {
                $("#locale").combobox({
                    data: _datax,
                    valueField: "code",
                    textField: "code",
                    required: true
                });
                var _datax2 = [{'value':'','text':'全部'}];
                for(var i=0;i<_datax.length;i++){
                    _datax2.push({
                        'value':_datax[i].code,
                        'text':_datax[i].code
                    });
                }
                 $("#appDownloadPage-locale").combobox({
                     data: _datax2,
                     valueField: 'value',
                     textField: 'text',
                     required: true,
                     onLoadSuccess: function () {
                         $("#appDownloadPage-locale").combobox('setValue','');//设置默认值
                     }
                 });

            });

            $("#brokerId").combobox({
                url: AppDownloadPageMVC.URLs.getBrokerList.url,
                method: 'get',
                valueField: "id",
                textField: "brokerName",
                editable: true,
                value: '',
                prompt: '选择券商',
                panelHeight: 300
            });

            $('#status').combobox({
                required: true,
                valueField: 'id',
                textField: 'value',
                data: [
                    {
                        id: '0',
                        value: '已保存'
                    }, {
                        id: '1',
                        value: '已发布'
                    }, {
                        id: '2',
                        value: '已下架'
                    }
                ]
            });

            $.get(AppDownloadPageMVC.URLs.template.url, "", function (_datax) {

                $("#templateId").combobox({
                    data: _datax,
                    valueField: "id",
                    textField: "name",
                    required: true
                });

            });

            $('#logoImg-file').filebox({
                buttonText: '选择',
                buttonAlign: 'right',
                prompt: '选择图片',
                onChange: function (newVal, oldVal) {
                    if (newVal) {
                        AppDownloadPageMVC.Util.uploadImg('#logoImg-file', '#logoImg');
                    }
                }
            });

            $('#firstImg-file').filebox({
                buttonText: '选择',
                buttonAlign: 'right',
                prompt: '选择图片',
                onChange: function (newVal, oldVal) {
                    if (newVal) {
                        AppDownloadPageMVC.Util.uploadImg('#firstImg-file', '#firstImg');
                    }
                }
            });

            $('#secondImg-file').filebox({
                buttonText: '选择',
                buttonAlign: 'right',
                prompt: '选择图片',
                onChange: function (newVal, oldVal) {
                    if (newVal) {
                        AppDownloadPageMVC.Util.uploadImg('#secondImg-file', '#secondImg');
                    }
                }
            });

            $('#thirdImg1-file').filebox({
                buttonText: '选择',
                buttonAlign: 'right',
                prompt: '选择图片',
                onChange: function (newVal, oldVal) {
                    if (newVal) {
                        AppDownloadPageMVC.Util.uploadImg('#thirdImg1-file', '#thirdImg1');
                    }
                }
            });

            $('#thirdImg2-file').filebox({
                buttonText: '选择',
                buttonAlign: 'right',
                prompt: '选择图片',
                onChange: function (newVal, oldVal) {
                    if (newVal) {
                        AppDownloadPageMVC.Util.uploadImg('#thirdImg2-file', '#thirdImg2');
                    }
                }
            });

            $('#thirdImg3-file').filebox({
                buttonText: '选择',
                buttonAlign: 'right',
                prompt: '选择图片',
                onChange: function (newVal, oldVal) {
                    if (newVal) {
                        AppDownloadPageMVC.Util.uploadImg('#thirdImg3-file', '#thirdImg3');
                    }
                }
            });

            $('#thirdImg4-file').filebox({
                buttonText: '选择',
                buttonAlign: 'right',
                prompt: '选择图片',
                onChange: function (newVal, oldVal) {
                    if (newVal) {
                        AppDownloadPageMVC.Util.uploadImg('#thirdImg4-file', '#thirdImg4');
                    }
                }
            });

            $('#appDownloadPage-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: AppDownloadPageMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        AppDownloadPageMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        AppDownloadPageMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#appDownloadPage-datagrid').datagrid('load', {});
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
                    field: 'templateId',
                    title: '网页模板id',
                    width: 100,
                    sortable: true
                }, {
                    field: 'name',
                    title: '名称',
                    width: 150,
                    sortable: true
                }, {
                    field: 'locale',
                    title: '语言',
                    width: 150,
                    sortable: true
                }, {
                    field: 'uid',
                    title: '标识码',
                    width: 150,
                    sortable: true
                }, {
                    field: 'link',
                    title: '下载页链接',
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
                                + 'onclick="AppDownloadPageMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: AppDownloadPageCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return AppDownloadPageMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // dialogs
            $('#appDownloadPage-dlg').dialog({
                closed: true,
                modal: false,
                width: 1100,
                height: window.innerHeight - 100 || document.documentElement.clientHeight - 100 || document.body.clientHeight - 100,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#appDownloadPage-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: AppDownloadPageMVC.Controller.save
                }]
            });

        },
        bindEvent: function () {
            $('#btn-search').bind('click', AppDownloadPageMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#appDownloadPage-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return AppDownloadPageMVC.Controller.edit();
            }
        },
        add: function () {
            var options = AppDownloadPageMVC.Util.getOptions();
            options.title = '新增App下载页';
            EasyUIUtils.openAddDlg(options);

            $('#locale').combobox('select', 'zh-cn');
            $('#status').combobox('select', '0');

            var allTemplate = $('#templateId').combobox('getData');
            if (allTemplate) {
                $('#templateId').combobox('select', allTemplate[0]['id']);
            }
            $("#brokerId").combobox('readonly', false);
        },
        edit: function () {
            var row = $('#appDownloadPage-datagrid').datagrid('getSelected');
            if (row) {
                var options = AppDownloadPageMVC.Util.getOptions();

                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改App下载页[' + options.data.name + ']';

                EasyUIUtils.openEditDlg(options);

                $('#logoImg-file').filebox('setText', row.logoImg);
                $('#firstImg-file').filebox('setText', row.firstImg);
                $('#secondImg-file').filebox('setText', row.secondImg);
                $('#thirdImg1-file').filebox('setText', row.thirdImg1);
                $('#thirdImg2-file').filebox('setText', row.thirdImg2);
                $('#thirdImg3-file').filebox('setText', row.thirdImg3);
                $('#thirdImg4-file').filebox('setText', row.thirdImg4);
                $("#brokerId").combobox('readonly', true);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        find: function () {
            var params = AppDownloadPageMVC.Util.getQueryParams();

            $('#appDownloadPage-datagrid').datagrid('load', params);
        },
        save: function () {
            var action = $('#modal-action').val();

            var options = AppDownloadPageMVC.Util.getOptions();

            if (action === "edit") {
                var row = $('#appDownloadPage-datagrid').datagrid('getSelected');
                //取得ID传给后台
                options.url = AppDownloadPageMVC.URLs.edit.url + '?id=' + row.id;
            } else {
                //添加操作
                options.url = AppDownloadPageMVC.URLs.add.url;
            }

            //修改后刷新列表
            EasyUIUtils.save(options);

            return;
        }

    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#appDownloadPage-dlg',
                formId: '#appDownloadPage-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                url: null,
                gridId: '#appDownloadPage-datagrid',
                gridUrl: AppDownloadPageMVC.URLs.list.url
            };
        },
        getQueryParams: function () {
            var appDownloadPageName = $("#appDownloadPage-name").val();
            var appDownloadPageLocale = $("#appDownloadPage-locale").val();
            var appDownloadPageUid = $("#appDownloadPage-uid").val();

            var params = {
                name: appDownloadPageName,
                locale: appDownloadPageLocale,
                uid: appDownloadPageUid

            };

            return params;
        },
        uploadImg: function (fileBoxId, fileImgId) {
            var files = $(fileBoxId).filebox('files');

            var formData = new FormData();
            formData.append("file", files[0]);

            //调用apicontroller后台action方法，将form数据传递给后台处理。contentType必须设置为false,否则chrome和firefox不兼容
            $.ajax({
                url: AppDownloadPageMVC.URLs.img.url,
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