$(function () {
    Invite.init();
});

var Invite = {
    init: function () {
        InviteMVC.View.initControl();
        InviteMVC.View.bindEvent();

    }
};

var InviteCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/user/invite/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',

};

var InviteMVC = {
    URLs: {
        Info: {
            url: InviteCommon.baseUrl + 'user/',
            method: 'GET'
        },
        Search: {
            url: InviteCommon.baseUrl + 'userList',
            method: 'GET'
        },
        Invite: {
            url: InviteCommon.baseUrl + 'inviteList/',
            method: 'GET'
        },
        Reward: {
            url: InviteCommon.baseUrl + "rewardList/",
            method: 'GET'
        },
        AddCoWord: {
            url: InviteCommon.baseUrl + "coWork",
            method: 'POST'
        },
        Operation: {
            url: InviteCommon.baseUrl + "operationLevel",
            method: 'POST'
        }


    },
    Model: {},
    View: {
        initControl: function () {
            $('#user-detail-dlg').dialog({
                closed: true,
                modal: true,
                striped: true,
                collapsible: true,
                resizable: true,
                width: 900,
                height: 600,
                maximizable: true,
                iconCls: 'icon-info',
                buttons: [{
                    text: '返回',
                    iconCls: 'icon-back',
                    handler: function () {
                        $("#user-detail-dlg").dialog('close');
                        $("#user-detail-dlg").dialog('refresh');
                    }
                }]
            });
            $('#user-co-work-dlg').dialog({
                closed: true,
                modal: true,
                striped: true,
                collapsible: true,
                //resizable: true,
                width: 400,
                height: 300,
                //maximizable: true,
                iconCls: 'icon-add',
                title: '添加合伙人',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#user-co-work-dlg").dialog('close');
                    }
                }, {
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: InviteMVC.Controller.save
                }]
            });
            $('#user-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                sortOrder: 'desc',
                pageSize: 50,
                pageNumber: 1,
                url: InviteMVC.URLs.Search.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        $("#user-datagrid").datagrid({
                            pageNumber: 1,
                            pageSize: 50,
                            url: InviteMVC.URLs.Search.url
                        });
                    }
                }, '-', {
                    iconCls: 'icon-add',
                    text: '添加合伙人',
                    handler: function () {
                        InviteMVC.Controller.addco_work();
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
                        field: 'userId',
                        title: '用户ID',
                        width: 100
                    }, {
                        field: 'email',
                        title: 'Email',
                        width: 100

                    }, {
                        field: 'mobile',
                        title: '手机号',
                        width: 100
                    }, {
                        field: 'userLevel',
                        title: '用户身份',
                        width: 100,
                        formatter: function (val) {
                            if (val === 'vip') {
                                return '会员'
                            }
                            if (val === 'angel') {
                                return '天使'
                            }
                            if (val === 'partner') {
                                return '合伙人'
                            }
                        }
                    }
                    , {
                        field: 'updateDate',
                        title: '更新时间',
                        width: 100,
                        formatter: function (val) {
                            return moment(new Date(val)).format('YYYY-MM-DD HH:mm:ss');
                        }
                    }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return InviteMVC.Controller.detailInfo();
                },
                onLoadSuccess: function () { //加载完成后,设置选中第一项
                    $('.pagination-page-list').hide();

                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            var pager = $("#user-datagrid").datagrid("getPager");
            if (pager) {
                $(pager).pagination({
                    onSelectPage: function (pageNumber, pageSize) {
                        var url = InviteMVC.Util.getSearchUrl();
                        $("#user-datagrid").datagrid({
                            pageNumber: pageNumber,
                            pageSize: pageSize,
                            url: url
                        });

                    }
                });
            }
        },
        bindEvent: function () {
            $('#btn-search').bind('click', InviteMVC.Controller.find);
            $('#btn-operation').bind('click', InviteMVC.Controller.delete);

        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#user-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return InviteMVC.Controller.edit();
            }
        },
        find: function () {
            var url = InviteMVC.Util.getSearchUrl();
            $("#user-datagrid").datagrid({
                pageNumber: 1,
                pageSize: 50,
                url: url
            });

        },
        addco_work: function () {
            $('#user-co-work-dlg').dialog('open').dialog('center');
        },
        detailInfo: function () {
            InviteMVC.Util.getRowSelected(function (row) {
                $('#user-detail-dlg').dialog('open').dialog('center');
                InviteMVC.Util.fillDetailInfo(row);
            })
        },
        save: function () {
            var userId = $('#userId-co-work').textbox('getValue');
            $.post(InviteMVC.URLs.AddCoWord.url, {userId: userId}, function (result) {
                if (!result.code) {
                    $.messager.alert('成功', "请求发送成功", 'success');
                } else {
                    $.messager.alert('失败', "失败", 'error');
                }
            }, 'json');
            $('#user-co-work-dlg').dialog('close');
        },
        delete: function () {
            var userId = $('#title-user-id').val();
            $.post(InviteMVC.URLs.Operation.url, {userId: userId}, function (result) {
                if (!result.code) {
                    $.messager.alert('成功', "请求发送成功", 'success');
                } else {
                    $.messager.alert('失败', "失败", 'error');
                }
            }, 'json');
        }
    },
    Util: {
        getSearchUrl: function () {
            var userId = $("#search-id").textbox('getValue');
            var email = $("#search-email").textbox('getValue');
            var mobile = $("#search-mobile").textbox('getValue');
            var title = $("#search-title").combobox('getValue');
            return url = InviteMVC.URLs.Search.url + '?userId=' + userId + '&email=' + email + '&mobile=' + mobile + '&title=' + title;
        },
        getRowSelected: function (func) {
            var row = $('#user-datagrid').datagrid('getSelected');
            if (row) {
                func(row);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        fillDetailInfo: function (row) {
            var userId = row.userId;
            //alert(userId);
            if (userId !== '') {
                // 用户信息和邀请人信息
                var userInfoUrl = InviteMVC.URLs.Info.url + userId;
                $.getJSON(userInfoUrl, function (result) {
                    $('#userId').text(result.data.userId);
                    $('#email').text(result.data.email);
                    $('#mobile').text(result.data.mobile);
                    $('#name').text(result.data.userName);
                    $('#user-title').text(result.data.userLevel);
                    $('#invite-userId').text(result.data.inviteUserId);
                    $('#invite-mobile').text(result.data.inviteMobile);
                    $('#invite-name').text(result.data.inviteUserName);
                    $('#invite-title').text(result.data.inviteUserLevel);
                });
                $('#user-tab').tabs({
                    border: false,
                    onSelect: function () {
                        var tab = $('#user-tab').tabs('getSelected');
                        var index = $('#user-tab').tabs('getTabIndex', tab);
                        if (index == 1) {
                            $('#invite-datagrid').datagrid({
                                method: 'get',
                                fit: true,
                                fitColumns: true,
                                singleSelect: true,
                                pagination: true,
                                rownumbers: true,
                                sortOrder: 'desc',
                                pageSize: 50,
                                pageNumber: 1,
                                url: InviteMVC.URLs.Invite.url + userId,
                                toolbar: [{
                                    iconCls: 'icon-reload',
                                    handler: function () {

                                    }
                                }],
                                loadFilter: function (src) {
                                    if (!src.code) {
                                        return src.data;
                                    }
                                    return $.messager.alert('失败', 'error', 'error');
                                },
                                columns: [[
                                    {
                                        field: 'account',
                                        title: '账号',
                                        width: 100,
                                        sortable: true

                                    }, {
                                        field: 'isActivate',
                                        title: '状态',
                                        width: 100,
                                        formatter: function (val) {
                                            if (val === false) {
                                                return "未激活"
                                            }
                                            else {
                                                return "已激活"
                                            }
                                        }
                                    }, {
                                        field: 'createdDate',
                                        title: '邀请时间',
                                        width: 100,
                                        formatter: function (val) {
                                            return moment(new Date(val)).format('YYYY-MM-DD HH:mm:ss');
                                        }
                                    }]],
                                onDblClickRow: function (rowIndex, rowData) {
                                },
                                onLoadSuccess: function () { //加载完成后,设置选中第一项
                                    $('.pagination-page-list').hide();

                                },
                                onLoadError: function (data) {
                                    return $.messager.alert('失败', 'error', 'error');
                                }
                            });
                        }
                        if (index == 2) {
                            $('#reward-datagrid').datagrid({
                                method: 'get',
                                fit: true,
                                fitColumns: true,
                                singleSelect: true,
                                pagination: true,
                                rownumbers: true,
                                sortOrder: 'desc',
                                pageSize: 50,
                                pageNumber: 1,
                                url: InviteMVC.URLs.Reward.url + userId,
                                toolbar: [{
                                    iconCls: 'icon-reload',
                                    handler: function () {
                                    }
                                }],
                                loadFilter: function (src) {
                                    if (!src.code) {
                                        return src.data;
                                    }
                                    return $.messager.alert('失败', 'error', 'error');
                                },
                                columns: [[
                                    {
                                        field: 'createdDate',
                                        title: '奖励时间',
                                        width: 100,
                                        formatter: function (val) {
                                            return moment(new Date(val)).format('YYYY-MM-DD HH:mm:ss');
                                        }


                                    }, {
                                        field: 'userId',
                                        title: '账号',
                                        width: 100,

                                    }, {
                                        field: 'currencyCode',
                                        title: '奖励币种',
                                        width: 100,
                                    }, {
                                        field: 'currencyNum',
                                        title: '奖励金额',
                                        width: 100

                                    }]],
                                onDblClickRow: function (rowIndex, rowData) {
                                },
                                onLoadSuccess: function () { //加载完成后,设置选中第一项
                                    $('.pagination-page-list').hide();

                                },
                                onLoadError: function (data) {
                                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                                }
                            });
                        }
                    }
                });

                $('#title-user-id').val(userId);
                if (row.userLevel == 'vip') {
                    $('#btn-operation').attr({'hidden': true});
                }
                else {
                    $('#btn-operation').attr({'hidden': false});
                }
            }

        }
    }
};