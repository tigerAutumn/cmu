$(function () {
    IndexPortfolio.init();
});

var IndexPortfolio = {
    init: function () {
        IndexPortfolioMVC.View.initControl();
        IndexPortfolioMVC.View.bindEvent();
        IndexPortfolioMVC.View.bindValidate();
    }
};

var IndexPortfolioCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/portfolio/index/',
    uploadUrl: BossGlobal.ctxPath + '/v1/boss/upload/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/'
};

var IndexPortfolioMVC = {
    URLs: {
        add: {
            url: IndexPortfolioCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: IndexPortfolioCommon.baseUrl + 'edit',
            method: 'POST'
        },
        remove: {
            url: IndexPortfolioCommon.baseUrl + 'remove',
            method: 'POST'
        },
        list: {
            url: IndexPortfolioCommon.baseUrl + 'list',
            method: 'GET'
        },
        listAllMixedIndex: {
            url: IndexPortfolioCommon.baseUrl + 'listAllMixedIndex',
            method: 'GET'
        },
        img: {
            url: IndexPortfolioCommon.uploadUrl + 'public/upload',
            method: 'GET'
        }
    },
    Model: {
        roles: {}
    },
    View: {
        initControl: function () {

            $('#midwaySwitch-switch').switchbutton({
                onText: '开',
                offText: '关',
                checked: true,
                onChange: function (checked) {
                    if (checked) {
                        $('#midwaySwitch').val(1);
                    } else {
                        $('#midwaySwitch').val(0);
                    }
                }
            });

            $('#redeemComponent-switch').switchbutton({
                onText: '开',
                offText: '关',
                checked: true,
                onChange: function (checked) {
                    if (checked) {
                        $('#redeemComponent').val(1);
                    } else {
                        $('#redeemComponent').val(0);
                    }
                }
            });

            $('#redeemUsdt-switch').switchbutton({
                onText: '开',
                offText: '关',
                checked: true,
                onChange: function (checked) {
                    if (checked) {
                        $('#redeemUsdt').val(1);
                    } else {
                        $('#redeemUsdt').val(0);
                    }
                }
            });

            $('#transferSpot-switch').switchbutton({
                onText: '开',
                offText: '关',
                checked: true,
                onChange: function (checked) {
                    if (checked) {
                        $('#transferSpot').val(1);
                    } else {
                        $('#transferSpot').val(0);
                    }
                }
            });

            $('#prePurchStart').datetimebox({
                required: true,
                showSeconds: false
            }).datetimebox('calendar').calendar({
                validator: function (date) {
                    var now = new Date();
                    var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate());

                    return date >= d1;
                }
            });

            $('#purchStart').datetimebox({
                required: true,
                showSeconds: false
            }).datetimebox('calendar').calendar({
                validator: function (date) {
                    var now = new Date();
                    var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate());

                    return date >= d1;
                }
            });

            $('#purchEnd').datetimebox({
                required: true,
                showSeconds: false
            }).datetimebox('calendar').calendar({
                validator: function (date) {
                    var now = new Date();
                    var d1 = new Date(now.getFullYear(), now.getMonth(), now.getDate());

                    return date >= d1;
                }
            });

            $('#midwayPurchStart').timespinner({
                showSeconds: false
            });

            $('#midwayPurchEnd').timespinner({
                showSeconds: false
            });

            $('#portfolioType').combobox({
                valueField: 'id',
                textField: 'value',
                readonly: true,
                data: [
                    {
                        id: '1',
                        value: '指数组合'
                    }, {
                        id: '2',
                        value: '未来权益组合'
                    }, {
                        id: '3',
                        value: '主动管理组合'
                    }, {
                        id: '4',
                        value: '杠杆组合'
                    }
                ]
            });

            $('#midwayPurchTime').combobox({
                valueField: 'id',
                textField: 'value',
                data: [
                    {
                        id: '1',
                        value: '每天'
                    }
                ]
            });

            $('#envStatus').combobox({
                required: true,
                valueField: 'id',
                textField: 'value',
                data: [
                    {
                        id: '0',
                        value: '下线'
                    }, {
                        id: '1',
                        value: '预发'
                    }, {
                        id: '2',
                        value: '上线'
                    }
                ]
            });

            $('#issuerLogo-file').filebox({
                buttonText: '选择',
                buttonAlign: 'right',
                prompt: '选择图片',
                onChange: function (newVal, oldVal) {
                    if (newVal) {
                        IndexPortfolioMVC.Util.uploadImg();
                    }
                }
            });

            $('#indexPortfolio-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: IndexPortfolioMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        IndexPortfolioMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        IndexPortfolioMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#indexPortfolio-datagrid').datagrid('load', {});
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
                    field: 'portfolioType',
                    title: '产品类型',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value == 1) {
                            return "指数组合";
                        } else if (value == 2) {
                            return "未来权益组合";
                        } else if (value == 3) {
                            return "主动管理组合";
                        } else if (value == 4) {
                            return "杠杆组合";
                        }

                        return "";
                    }
                }, {
                    field: 'name',
                    title: '产品名称',
                    width: 100,
                    sortable: true
                }, {
                    field: 'issuerId',
                    title: '发行方ID',
                    width: 100,
                    sortable: true
                }, {
                    field: 'zhIssuerName',
                    title: '发行方名称(中文)',
                    width: 150,
                    sortable: true
                }, {
                    field: 'enIssuerName',
                    title: '发行方名称(英文)',
                    width: 150,
                    sortable: true
                }, {
                    field: 'mixedIndexName',
                    title: '混合指数',
                    width: 100,
                    sortable: true
                }, {
                    field: 'purchStart',
                    title: '初始申购时间',
                    width: 250,
                    sortable: true,
                    formatter: function (value, row, index) {
                        return row.purchStart + " - " + row.purchEnd;
                    }
                }, {
                    field: 'purchLimit',
                    title: '初始申购总额',
                    width: 150,
                    sortable: true
                }, {
                    field: 'minPurch',
                    title: '最低申购额度',
                    width: 150,
                    sortable: true
                }, {
                    field: 'personLimit',
                    title: '个人最大申请额度',
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
                                + 'onclick="IndexPortfolioMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: IndexPortfolioCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return IndexPortfolioMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // dialogs
            $('#indexPortfolio-dlg').dialog({
                closed: true,
                modal: false,
                width: 1100,
                height: window.innerHeight - 100 || document.documentElement.clientHeight - 100 || document.body.clientHeight - 100,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#indexPortfolio-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: IndexPortfolioMVC.Controller.save
                }]
            });


        },
        bindEvent: function () {
            $('#btn-search').bind('click', IndexPortfolioMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {

        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#indexPortfolio-datagrid').datagrid('selectRow', index);

            if (name === "edit") {
                return IndexPortfolioMVC.Controller.edit();
            }

        },
        add: function () {
            var options = IndexPortfolioMVC.Util.getOptions();
            options.title = '新增指数组合';
            EasyUIUtils.openAddDlg(options);

            $('#name').textbox({readonly: false});

            $.get(IndexPortfolioMVC.URLs.listAllMixedIndex.url, "", function (_datax) {

                $("#mixedIndexName").combobox({
                    required: true,
                    readonly: false,
                    data: _datax.data,
                    valueField: "symbolName",
                    textField: "symbolName",
                    onLoadSuccess: function () {
                        var allMixedIndex = $("#mixedIndexName").combobox("getData");
                        if (allMixedIndex) {
                            $("#mixedIndexName").combobox("setValue", allMixedIndex[0]["symbolName"]);
                        }
                    }
                });

            });

            $('#maxSizeDigit').textbox('setValue', '0');
            $('#portfolioType').combobox('setValue', '1');
            $('#midwayPurchTime').combobox('setValue', '1');
            $('#envStatus').combobox('setValue', '0');

            $('#midwaySwitch').val(0);
            $('#redeemComponent').val(0);
            $('#redeemUsdt').val(0);
            $('#transferSpot').val(0);
        },
        edit: function () {
            var row = $('#indexPortfolio-datagrid').datagrid('getSelected');
            if (row) {
                var options = IndexPortfolioMVC.Util.getOptions();

                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改指数组合[' + row.name + ']';

                EasyUIUtils.openEditDlg(options);

                $.get(IndexPortfolioMVC.URLs.listAllMixedIndex.url, "", function (_datax) {

                    $("#mixedIndexName").combobox({
                        required: true,
                        readonly: true,
                        data: _datax.data,
                        valueField: "symbolName",
                        textField: "symbolName",
                        onLoadSuccess: function () {
                            $("#mixedIndexName").combobox("setValue", row.mixedIndexName);
                        }
                    });

                });

                $('#name').textbox({readonly: true});

                $('#issuerLogo-file').filebox('setText', row.issuerLogo);

                IndexPortfolioMVC.Util.checkSwitch('#midwaySwitch-switch', row.midwaySwitch);
                IndexPortfolioMVC.Util.checkSwitch('#redeemComponent-switch', row.redeemComponent);
                IndexPortfolioMVC.Util.checkSwitch('#redeemUsdt-switch', row.redeemUsdt);
                IndexPortfolioMVC.Util.checkSwitch('#transferSpot-switch', row.transferSpot);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        find: function () {
            var params = IndexPortfolioMVC.Util.getQueryParams();

            $('#indexPortfolio-datagrid').datagrid('load', params);
        },
        save: function () {
            var action = $('#modal-action').val();

            var options = IndexPortfolioMVC.Util.getOptions();

            if (action === "edit") {
                var row = $('#indexPortfolio-datagrid').datagrid('getSelected');
                //取得ID传给后台
                options.url = IndexPortfolioMVC.URLs.edit.url + '?id=' + row.id;
            }
            else {
                //添加操作
                options.url = IndexPortfolioMVC.URLs.add.url;
            }

            //修改后刷新列表
            EasyUIUtils.save(options);

            return;
        }

    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#indexPortfolio-dlg',
                formId: '#indexPortfolio-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                url: null,
                gridId: '#indexPortfolio-datagrid',
                gridUrl: IndexPortfolioMVC.URLs.list.url
            };
        },
        getQueryParams: function () {
            var name = $('#indexPortfolio-name').textbox('getValue');
            var issuerId = $('#indexPortfolio-issuerId').textbox('getValue');
            var mixedIndexName = $('#indexPortfolio-mixedIndexName').textbox('getValue');

            var params = {
                name: name,
                issuerId: issuerId,
                mixedIndexName: mixedIndexName
            };

            return params;
        },
        uploadImg: function () {
            var files = $("#issuerLogo-file").filebox('files');

            var formData = new FormData();
            formData.append("file", files[0]);

            //调用apicontroller后台action方法，将form数据传递给后台处理。contentType必须设置为false,否则chrome和firefox不兼容
            $.ajax({
                url: IndexPortfolioMVC.URLs.img.url,
                type: 'POST',
                data: formData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (data) {
                    if (data.code == 0) {
                        $('#issuerLogo').val(data.data.fileName);
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
        checkSwitch(sid, sval) {
            if (sval == 0) {
                $(sid).switchbutton('uncheck');
            } else {
                $(sid).switchbutton('check');
            }
        }
    }
};