$(function () {
    Banner.init();
});

var Banner = {
    init: function () {
        BannerMVC.View.initControl();
        BannerMVC.View.bindEvent();
        BannerMVC.View.bindValidate();
    }
};

var BannerCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/extra/cms/banner/',
    imageUrl: BossGlobal.ctxPath + '/v1/boss/upload/',
    commonUrl: BossGlobal.ctxPath + '/v1/boss/common/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/'
};

var BannerMVC = {
    URLs: {
        add: {
            url: BannerCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: BannerCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: BannerCommon.baseUrl + 'list',
            method: 'GET'
        },
        img: {
            url: BannerCommon.imageUrl + 'oss-upload',
            method: 'POST'
        },
        Language: {
            url: BannerCommon.commonUrl + 'lang'
        },
        getBrokerList: {
            url: BannerCommon.brokerBaseUrl + 'list',
            method: 'GET'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {

            $.get(BannerMVC.URLs.Language.url, "", function (_datax) {
                $("#locale").combobox({
                    data: _datax,
                    valueField: "code",
                    textField: "code",
                    required: true
                });
                var _datax2 = [{'value': '', 'text': '全部'}];
                for (var i = 0; i < _datax.length; i++) {
                    _datax2.push({
                        'value': _datax[i].code,
                        'text': _datax[i].code
                    });
                }
                $("#banner-locale").combobox({
                    data: _datax2,
                    valueField: 'value',
                    textField: 'text',
                    required: true,
                    onLoadSuccess: function () {
                        $("#banner-locale").combobox('setValue', '');//设置默认值
                    }
                });
            });

            $("#brokerId").combobox({
                url: BannerMVC.URLs.getBrokerList.url,
                method: 'get',
                valueField: "id",
                textField: "brokerName",
                editable: true,
                value: '',
                prompt: '选择券商',
                panelHeight: 300
            });


            $('#imgfile-filebox').filebox({
                buttonText: '选择',
                buttonAlign: 'right',
                prompt: '选择图片',
                onChange: function (newVal, oldVal) {
                    if (newVal) {
                        BannerMVC.Controller.updateImage1();
                    }
                }
            });

            $('#imgfile1-filebox').filebox({
                buttonText: '选择',
                buttonAlign: 'right',
                prompt: '选择图片',
                onChange: function (newVal, oldVal) {
                    if (newVal) {
                        BannerMVC.Controller.updateImage2();
                    }
                }
            });

            $('#imgfile2-filebox').filebox({
                buttonText: '选择',
                buttonAlign: 'right',
                prompt: '选择图片',
                onChange: function (newVal, oldVal) {
                    if (newVal) {
                        BannerMVC.Controller.updateImage3();
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
                url: BannerMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        BannerMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        BannerMVC.Controller.edit();
                    }
                }, '-', {
                    text: '全部数据',
                    iconCls: 'icon-reload',
                    handler: function () {
                        $("#user-datagrid").datagrid("load", {});
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
                    field: 'locale',
                    title: '语言',
                    width: 100,
                    sortable: true

                }, {
                    field: 'type',
                    title: '类型',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value == 1) {
                            return "banner";
                        }
                        if (value == 2) {
                            return "公告";
                        }
                        if (value == 3) {
                            return "轮播图";
                        }
                        if (value == 4) {
                            return "APP启动页";
                        }
                        return value;
                    }
                }, {
                    field: 'title',
                    title: '标题',
                    width: 150,
                    sortable: true,
                }, {
                    field: 'text',
                    title: '文本',
                    width: 150,
                    sortable: true,

                }, {
                    field: 'imageUrl',
                    title: '图片',
                    width: 100,
                    sortable: true,

                }, {
                    field: 'originalImageUrl',
                    title: '原始图片',
                    width: 100,
                    sortable: true,

                }, {
                    field: 'link',
                    title: '链接',
                    width: 100,
                    sortable: true,

                }, {
                    field: 'platform',
                    title: '平台',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value == 1) {
                            return "PC";
                        }
                        if (value == 2) {
                            return "IOS";
                        }
                        if (value == 3) {
                            return "Android";
                        }
                        return value;
                    }

                }, {
                    field: 'startTime',
                    title: '发布时间',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                    }

                }, {
                    field: 'endTime',
                    title: '下架时间',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                    }
                }, {
                    field: 'status',
                    title: '状态',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value == 0) {
                            return "已保存";
                        }
                        if (value == 1) {
                            return "已发布";
                        }
                        if (value == 2) {
                            return "已下架";
                        }
                        if (value == -1) {
                            return "已删除";
                        }
                        return value;
                    }

                }, {
                    field: 'sort',
                    title: '排序',
                    width: 100,
                    sortable: true,

                }, {
                    field: 'publishUser',
                    title: '发布人',
                    width: 100,
                    sortable: true,

                }, {
                    field: 'shareTitle',
                    title: '分享标题',
                    width: 100,
                    sortable: true,

                }, {
                    field: 'shareText',
                    title: '分享文本',
                    width: 100,
                    sortable: true,

                }, {
                    field: 'shareImageUrl',
                    title: '分享图片地址',
                    width: 100,
                    sortable: true,

                }, {
                    field: 'shareLink',
                    title: '分享地址',
                    width: 100,
                    sortable: true,

                }, {
                    field: 'rndNum',
                    title: '市场序列号',
                    width: 100,
                    sortable: true,

                }, {
                    field: 'createdDate',
                    title: '创建时间',
                    width: 150,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss")
                    }

                }, {
                    field: 'updateDate',
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
                                + 'onclick="BannerMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: BannerCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return BannerMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                },
                onLoadSuccess: function () {
                    $('.pagination-page-list').hide();
                }
            });

            // dialogs
            $('#user-dlg').dialog({
                closed: true,
                modal: false,
                width: 1060,
                height: 500,
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
                    handler: BannerMVC.Controller.save
                }]
            });


        },
        bindEvent: function () {
            $('#btn-search').bind('click', BannerMVC.Controller.find);
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
                return BannerMVC.Controller.edit();
            }
        },
        add: function () {
            var options = BannerMVC.Util.getOptions();
            options.title = '新增banner';
            EasyUIUtils.openAddDlg(options);

            $('#type').combobox('setValue', '1');
            $('#platform').combobox('setValue', '1');
            $('#status').combobox('setValue', '0');
            $('#locale').combobox('setValue', 'zh-cn');
            $("#brokerId").combobox('readonly', false);
        },
        edit: function () {
            var row = $('#user-datagrid').datagrid('getSelected');
            if (row) {

                var options = BannerMVC.Util.getOptions();

                var newStartTime = moment(new Date(row.startTime)).format("YYYY-MM-DD HH:mm:ss");
                var newEndTime = moment(new Date(row.endTime)).format("YYYY-MM-DD HH:mm:ss");

                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改banner[' + options.data.title + ']';
                options.data.startTime = newStartTime;
                options.data.endTime = newEndTime;

                //console.log(options);
                EasyUIUtils.openEditDlg(options);
                $("#brokerId").combobox('readonly', true);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        find: function () {
            var params = BannerMVC.Util.getQueryParams();

            $('#user-datagrid').datagrid('load', params);
        },
        save: function () {
            var action = $('#modal-action').val();

            var options = BannerMVC.Util.getOptions();

            if (action === "edit") {
                var row = $('#user-datagrid').datagrid('getSelected');
                //取得ID传给后台
                options.url = BannerMVC.URLs.edit.url + '?id=' + row.id;
            }
            else {
                //添加操作
                options.url = BannerMVC.URLs.add.url;
            }

            //修改后刷新列表
            EasyUIUtils.save(options);

            return;
        },
        updateImage1: function () {
            BannerMVC.Util.uploadImg("#imgfile-filebox", '#imageUrl');
        },
        updateImage2: function () {
            BannerMVC.Util.uploadImg("#imgfile1-filebox", '#originalImageUrl');
        },
        updateImage3: function () {
            BannerMVC.Util.uploadImg("#imgfile2-filebox", '#shareImageUrl');
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
                gridUrl: BannerMVC.URLs.list.url
            };
        },
        getQueryParams: function () {
            var bannerType = $("#banner-type").combobox('getValue');
            var bannerPlatform = $("#banner-platform").combobox('getValue');
            var bannerStatus = $("#banner-status").combobox('getValue');
            var bannerLocale = $("#banner-locale").combobox('getValue');


            var params = {
                bannerType: bannerType,
                bannerPlatform: bannerPlatform,
                bannerStatus: bannerStatus,
                bannerLocale: bannerLocale
            };

            return params;
        },
        uploadImg: function (fileBoxId, fileInputHiddenId) {
            var files = $(fileBoxId).filebox('files');

            var formData = new FormData();
            formData.append("imgfile", files[0]);

            //调用apicontroller后台action方法，将form数据传递给后台处理。contentType必须设置为false,否则chrome和firefox不兼容
            $.ajax({
                url: BannerMVC.URLs.img.url,
                type: 'POST',
                data: formData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (data) {
                    if (data.code == 0) {
                        $(fileInputHiddenId).val(data.data.s3path);
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

    }
};