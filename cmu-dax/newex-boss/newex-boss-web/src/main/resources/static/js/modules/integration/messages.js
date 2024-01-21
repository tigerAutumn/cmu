$(function () {
    MassageDataDs.init();
});

var MassageDataDs = {
    init: function () {
        MessageMVC.View.initControl();
        MessageMVC.View.bindEvent();
        MessageMVC.View.bindValidate();
    }
};

var DsCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/integration/messages/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    commonUrl: BossGlobal.ctxPath + '/v1/boss/common/'
};

var MessageMVC = {
    URLs: {

        list: {
            url: DsCommon.baseUrl + 'list',
            method: 'get'
        },
        remove: {
            url: DsCommon.baseUrl + 'remove',
            method: 'Delete'
        },
        detail: {
            url: DsCommon.baseUrl + 'getmessage',
            method: 'GET'
        },

        Language: {
            url: DsCommon.commonUrl + 'lang'
        }
    },
    Model: {
    },
    View: {
        initControl: function () {

            $('#locale').combobox({
                url: MessageMVC.URLs.Language.url,
                method: 'get',
                valueField: "code",
                textField: "code",
                editable: true,
                value: '',
                prompt: '选择语言',
                panelHeight: 300
            });

            $('#ds-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: false,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: MessageMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-remove1',
                    handler: function () {
                        MessageMVC.Controller.remove();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        $("#ds-datagrid").datagrid({
                            pageNumber: 1,
                            pageSize: 50,
                            url: MessageMVC.URLs.list.url
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
                    title: '信息id',
                    width: 100,
                    sortable: true
                }, {
                    field: 'subject',
                    title: '邮件主题',
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
                    field: 'phoneNumber',
                    title: '手机号码',
                    width: 100,
                    sortable: true
                }, {
                    field: 'emailAddress',
                    title: '邮件地址',
                    width: 150,
                    sortable: true
                }, {
                    field: 'templateCode',
                    title: '模版编码',
                    width: 300,
                    sortable: true
                }, {
                    field: 'locale',
                    title: '模板语言',
                    width: 80,
                    sortable: true
                }, {
                    field: 'sent',
                    title: '是否发送',
                    width: 80,
                    sortable: true,
                    formatter: function (value, row, index) {

                        if (value == true) {
                            return "是";
                        } else {
                            return "否";
                        }
                    }
                }, {
                    field: 'retryTimes',
                    title: '重复发送次数',
                    width: 100,
                    sortable: true
                }, {
                    field: 'options',
                    title: '操作',
                    width: 100,
                    formatter: function (value, row, index) {
                        var icons = [{
                            "name": "remove",
                            "title": "删除"
                        }];
                        var buttons = [];
                        for (var i = 0; i < icons.length; i++) {
                            var tmpl = '<a href="#" title ="${title}" '
                                + 'onclick="MessageMVC.Controller.doOption(\'${index}\',\'${name}\')">'
                                + '<img src="${imgSrc}" alt="${title}"/"></a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index,
                                imgSrc: DsCommon.baseIconUrl + icons[i].name + ".png"
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return MessageMVC.Controller.showDetail();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            var pager = $("#ds-datagrid").datagrid("getPager");
            if (pager) {
                $(pager).pagination({
                    onSelectPage: function (pageNumber, pageSize) {
                        var url = MessageMVC.Util.getSearchUrl();
                        $("#ds-datagrid").datagrid({
                            pageNumber: pageNumber,
                            pageSize: pageSize,
                            url: url
                        });

                    }
                });
            }

            // dialogs
            $('#mass-ds-dlg').dialog({
                closed: true,
                modal: false,
                width: 850,
                title: "信息管理详情",
                height: 450,
                iconCls: 'icon-info',
                buttons: []
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', MessageMVC.Controller.find);
        },
        bindValidate: function () {
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#ds-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return MessageMVC.Controller.edit();
            }
            if (name === "remove") {
                return MessageMVC.Controller.remove();
            }
        },
        find: function () {
            var url = MessageMVC.Util.getSearchUrl();
            $("#ds-datagrid").datagrid({
                pageNumber: 1,
                pageSize: 50,
                url: url
            });

        },
        showDetail: function () {
            MessageMVC.Util.isRowSelected(function (row) {
                $('#mass-ds-dlg').dialog('open').dialog('center');
                MessageMVC.Util.fillDetailLabels(row);
            });
        },
        remove: function () {
            var row = $('#ds-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: MessageMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#ds-datagrid',
                    gridUrl: MessageMVC.URLs.list.url,
                    callback: function (rows) {
                    }
                };
                EasyUIUtils.remove(options);
            }
        }
    },
    Util: {
        getSearchUrl: function () {
            var phoneNumber = $("#phoneNumber").textbox('getValue');
            var emailaddress = $("#emailaddress").textbox('getValue');
            var sent = $("#sent").combobox('getValue');
            var locale = $("#locale").combobox('getValue');
            return MessageMVC.URLs.list.url + '?phoneNumber=' + phoneNumber + '&emailaddress=' + emailaddress + '&sent=' + sent + '&locale=' + locale;

        },
        getOptions: function () {
            return {
                dlgId: '#ds-dlg',
                formId: '#ds-form',
                actId: '#modal-action',
                rowId: '#dsId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                gridId: null,
            };
        },
        fillDetailLabels: function (data) {
            var id = data.id;
            var url = MessageMVC.URLs.detail.url + "?id=" + id;
            $.ajax({
                url: url,
                type: 'GET',
                data: '',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (data) {
                    $("#email_subject").text(data.data.subject);
                    $("#email_templateCode").text(data.data.templateCode);
                    $("#email_locale").text(data.data.locale);
                    if (data.data.sent == true) {
                        $("#email_sent").text("是");
                    } else {
                        $("#email_sent").text("否");
                    }
                    $("#email_content").text(data.data.content);
                    $("#email_emailAddress").text(data.data.emailAddress);
                    $("#email_phoneNumber").text(data.data.phoneNumber);
                    $("#email_createdDate").text(moment(new Date(data.data.createdDate)).format("YYYY-MM-DD HH:mm:ss"));

                    $("#email_templateParams").text(data.data.templateParams);

                }
            })
        },
        isRowSelected: function (func) {
            var row = $('#ds-datagrid').datagrid('getSelected');
            if (row) {
                func(row);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        }
    }
};
