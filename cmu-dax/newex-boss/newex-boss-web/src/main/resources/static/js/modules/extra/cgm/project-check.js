$(function () {
    Project.init();
});

var Project = {
    init: function () {
        ProjectCheckMVC.View.initControl();
    }
};

var ProjectCheckCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/extra/cgm/project/check/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    uploadUrl: BossGlobal.ctxPath + '/v1/boss/upload/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/'
};

var ProjectCheckMVC = {
    URLs: {
        add: {
            url: ProjectCheckCommon.baseUrl + 'saveProjectInfo',
            method: 'POST'
        },
        edit: {
            url: ProjectCheckCommon.baseUrl + 'updateProjectInfo',
            method: 'POST'
        },
        img: {
            url: ProjectCheckCommon.uploadUrl + 'public/upload',
            method: 'GET'
        },
        getBrokerList: {
            url: ProjectCheckCommon.brokerBaseUrl + 'list',
            method: 'GET'
        }

    },
    View: {

        initControl: function () {

            // 初始化券商数据
            $("#brokerId").combobox({
                url: ProjectCheckMVC.URLs.getBrokerList.url,
                method: 'get',
                valueField: "id",
                textField: "brokerName",
                editable: true,
                value: '',
                prompt: '选择券商',
                panelHeight: 300
            });

            $("#see").combobox({
                data: [{
                    value: 1,
                    text: '是'
                }, {
                    value: 0,
                    text: '否'
                }],
                valueField: 'value',
                textField: 'text',
                required: true,
                onChange: function () {
                    var isSee = $(this).val();
                    if (isSee == 1) {
                        $("#productAddress").textbox({disabled: false});
                        $('#productAddress').textbox({required: true});
                    } else if (isSee == 0) {
                        $("#productAddress").textbox({disabled: true});
                        $('#productAddress').textbox({required: false});
                        $("#productAddress").textbox("setValue", "");
                    }
                }
            });

            $('#whitePaper-file').filebox({
                buttonText: '选择',
                buttonAlign: 'right',
                prompt: '选择图片',
                onChange: function (newVal, oldVal) {
                    if (newVal) {
                        ProjectCheckMVC.Util.uploadImg();
                    }
                }
            });

            $('#imgName-file').filebox({
                buttonText: '选择',
                buttonAlign: 'right',
                prompt: '选择图片',
                onChange: function (newVal, oldVal) {
                    if (newVal) {
                        ProjectCheckMVC.Util.uploadImg2();
                    }
                }
            });

            // dialogs
            $('#projectInfo-dlg').dialog({
                closed: true,
                modal: false,
                width: 1200,
                height: 800,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#projectInfo-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: ProjectCheckMVC.Controller.save
                }]
            });

            // 待审核表格
            $("#wait-datagrid").datagrid({
                url: WaitMVC.URLs.List.url,
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        ProjectCheckMVC.Controller.add();
                    }
                }, '-', {
                    text: '编辑',
                    iconCls: 'icon-edit',
                    handler: function () {
                        ProjectCheckMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#wait-datagrid').datagrid('load', {});
                    }
                }, '-', {
                    iconCls: 'icon-info',
                    text: '查看详情',
                    handler: function () {
                        ProjectCheckMVC.Controller.getInfo()
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
                    hidden: true
                }, {
                    field: 'createdDate',
                    title: '申请时间',
                    width: 100,
                    formatter: function (val) {
                        return moment(new Date(val)).format('YYYY-MM-DD HH:mm:ss');
                    }
                }, {
                    field: 'name',
                    title: '申请人',
                    width: 100
                }, {
                    field: 'email',
                    title: '申请邮箱',
                    width: 100
                }, {
                    field: 'email',
                    title: '联系电话',
                    width: 100

                }, {
                    field: 'tokenSymbol',
                    title: '币种简称',
                    width: 100
                }, {
                    field: 'token',
                    title: '币种全称',
                    width: 100
                }, {
                    field: 'options',
                    title: '操作',
                    width: 100,
                    formatter: function () {
                        return '<a href="#" onclick="WaitMVC.Controller.pass()">通过</a>&nbsp;&nbsp;|' +
                            '<a href="#" onclick="WaitMVC.Controller.reject()">拒绝</a>'
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    ProjectCheckMVC.Controller.getInfo();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // 初始审核
            $("#check-datagrid").datagrid({
                url: CheckMVC.URLs.List.url,
                method: 'get',
                fit: true,
                fitColumns: true,
                pagination: true,
                rownumbers: true,
                singleSelect: true,
                pageSize: 50,
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#check-datagrid').datagrid('load', {});
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    text: '修改',
                    handler: function () {
                        ProjectCheckMVC.Controller.edit()
                    }
                }, '-', {
                    iconCls: 'icon-info',
                    text: '查看详情',
                    handler: function () {
                        ProjectCheckMVC.Controller.getInfo()
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
                    hidden: true
                }, {
                    field: 'updatedDate',
                    title: '初始审核时间',
                    width: 120,
                    formatter: function (val) {
                        return moment(new Date(val)).format('YYYY-MM-DD HH:mm:ss');
                    }
                }, {
                    field: 'name',
                    title: '申请人',
                    width: 100
                }, {
                    field: 'email',
                    title: '邮箱地址',
                    width: 100
                }, {
                    field: 'mobile',
                    title: '联系电话',
                    width: 100

                }, {
                    field: 'tokenSymbol',
                    title: '币种简称',
                    width: 100
                }, {
                    field: 'token',
                    title: '币种全称',
                    width: 100
                }, {
                    field: 'deposit',
                    title: 'CT保证金',
                    width: 100
                }, {
                    field: 'tokenNumber',
                    title: '代币糖果数量',
                    width: 100
                }, {
                    field: 'contacts',
                    title: '商务对接人',
                    width: 100
                }, {
                    field: 'wechat',
                    title: '微信号',
                    width: 100
                }, {
                    field: 'deposit',
                    title: '保证金',
                    width: 100
                }, {
                    field: 'tokenUrl',
                    title: '糖果地址',
                    width: 100
                }, {
                    field: 'depositStatus',
                    title: '保证金支付状态',
                    width: 100,
                    formatter: function (val) {
                        if (val == 0) {
                            return "待支付"
                        } else {
                            return "已支付"
                        }
                    }
                }, {
                    field: 'sweetsStatus',
                    title: '糖果支付',
                    width: 100,
                    formatter: function (val) {
                        if (val == 0) {
                            return "待支付"
                        } else {
                            return "已支付"
                        }
                    }
                }, {
                    field: 'onlineTime',
                    title: '排期',
                    width: 100,
                    formatter: function (value, row, index) {
                        var icons = [{
                            "name": "edit",
                            "title": "排期"
                        }];
                        var buttons = [];
                        for (var i = 0; i < icons.length; i++) {
                            var tmpl = '<a href="#" title ="${title}" '
                                + 'onclick="CheckMVC.Controller.plan(\'${index}\',\'${name}\')">'
                                + '${title}</a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }

                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    ProjectCheckMVC.Controller.getInfo();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // 待上线表格
            $("#pre-datagrid").datagrid({
                url: PreMVC.URLs.List.url,
                method: 'get',
                fit: true,
                singleSelect: true,
                fitColumns: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#pre-datagrid').datagrid('load', {});
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    text: '修改',
                    handler: function () {
                        ProjectCheckMVC.Controller.edit()
                    }
                }, '-', {
                    iconCls: 'icon-info',
                    text: '查看详情',
                    handler: function () {
                        ProjectCheckMVC.Controller.getInfo()
                    }
                }],
                loadFilter: function (src) {
                    if (!src.code) {
                        return src.data;
                    }
                    return $.messager.alert('失败', src.msg, 'error');
                },
                columns: [[{

                    field: 'onlineTime',
                    title: '排期时间',
                    width: 150,
                    formatter: function (val) {
                        return moment(new Date(val)).format('YYYY-MM-DD HH:mm:ss');
                    }
                }, {
                    field: 'name',
                    title: '申请人',
                    width: 100
                }, {
                    field: 'email',
                    title: '邮箱地址',
                    width: 150
                }, {
                    field: 'mobile',
                    title: '联系电话',
                    width: 100
                }, {
                    field: 'tokenSymbol',
                    title: '币种简称',
                    width: 100
                }, {
                    field: 'token',
                    title: '币种全称',
                    width: 100
                }, {
                    field: 'deposit',
                    title: 'CT保证金',
                    width: 100
                }, {
                    field: 'tokenNumber',
                    title: '代币糖果数量',
                    width: 100
                }, {
                    field: 'contacts',
                    title: '商务对接人',
                    width: 100
                }, {
                    field: 'wechat',
                    title: '微信号',
                    width: 100
                }, {
                    field: 'option',
                    title: '操作',
                    width: 100,
                    formatter: function (value, row, index) {
                        var icons = [{
                            "name": "edit",
                            "title": "完成上线"
                        }];
                        var buttons = [];
                        for (var i = 0; i < icons.length; i++) {
                            var tmpl = '<a href="#" title ="${title}" '
                                + 'onclick="PreMVC.Controller.online(\'${index}\')">'
                                + '${title}</a>';
                            var data = {
                                title: icons[i].title,
                                name: icons[i].name,
                                index: index
                            };
                            buttons.push(juicer(tmpl, data));
                        }
                        return buttons.join(' ');
                    }
                }]],
                onDblClickRow: function (rowIndex, rowData) {
                    ProjectCheckMVC.Controller.getInfo();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // 上线数据表格
            $("#online-datagrid").datagrid({
                url: OnlineMVC.URLs.List.url,
                method: 'get',
                fit: true,
                singleSelect: true,
                fitColumns: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#online-datagrid').datagrid('load', {});
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    text: '修改',
                    handler: function () {
                        ProjectCheckMVC.Controller.edit()
                    }
                }, '-', {
                    iconCls: 'icon-info',
                    text: '查看详情',
                    handler: function () {
                        ProjectCheckMVC.Controller.getInfo()
                    }
                }],
                columns: [[
                    {
                        field: 'id',
                        title: 'ID',
                        hidden: true
                    }, {

                        field: 'onlineTime',
                        title: '排期时间',
                        width: 150,
                        formatter: function (val) {
                            return moment(new Date(val)).format('YYYY-MM-DD HH:mm:ss');
                        }
                    }, {
                        field: 'name',
                        title: '申请人',
                        width: 100
                    }, {
                        field: 'email',
                        title: '邮箱地址',
                        width: 150
                    }, {
                        field: 'mobile',
                        title: '联系电话',
                        width: 100
                    }, {
                        field: 'tokenSymbol',
                        title: '币种简称',
                        width: 150
                    }, {
                        field: 'token',
                        title: '币种全称',
                        width: 100
                    }, {
                        field: 'deposit',
                        title: 'CT保证金',
                        width: 100
                    }, {
                        field: 'tokenNumber',
                        title: '代币糖果数量',
                        width: 100
                    }, {
                        field: 'contacts',
                        title: '商务对接人',
                        width: 100
                    }, {
                        field: 'wechat',
                        title: '微信号',
                        width: 100
                    }]],
                loadFilter: function (src) {
                    if (!src.code) {
                        return src.data;
                    }
                    return $.messager.alert('失败', src.msg, 'error');
                },
                onDblClickRow: function (rowIndex, rowData) {
                    ProjectCheckMVC.Controller.getInfo();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // 淘汰表格
            $("#fail-datagrid").datagrid({
                url: FailMVC.URLs.List.url,
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#fail-datagrid').datagrid('load', {});
                    }
                }, '-', {
                    iconCls: 'icon-info',
                    text: '查看详情',
                    handler: function () {
                        ProjectCheckMVC.Controller.getInfo()
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
                        hidden: true
                    }, {
                        field: 'createdDate',
                        title: '申请时间',
                        width: 150,
                        formatter: function (val) {
                            return moment(new Date(val)).format('YYYY-MM-DD HH:mm:ss');
                        }
                    }, {
                        field: 'name',
                        title: '申请人',
                        width: 100
                    }, {
                        field: 'email',
                        title: '邮箱地址',
                        width: 100
                    }, {
                        field: 'mobile',
                        title: '联系电话',
                        width: 100
                    }, {
                        field: 'updatedDate',
                        title: '更新时间',
                        width: 100,
                        formatter: function (val) {
                            return moment(new Date(val)).format('YYYY-MM-DD HH:mm:ss');
                        }
                    }, {
                        field: 'reject',
                        title: '拒绝原因',
                        width: 100,
                        formatter: function (value, row, index) {
                            var icons = [{
                                "name": "edit",
                                "title": "查看"
                            }];
                            var buttons = [];
                            for (var i = 0; i < icons.length; i++) {
                                var tmpl = '<a href="#" title ="${title}" '
                                    + 'onclick="FailMVC.Controller.rejectInfo(\'${index}\')">'
                                    + '${title}</a>';
                                var data = {
                                    title: icons[i].title,
                                    name: icons[i].name,
                                    index: index
                                };
                                buttons.push(juicer(tmpl, data));
                            }
                            return buttons.join(' ');
                        }
                    }]],
                onDblClickRow: function (rowIndex, rowData) {
                    ProjectCheckMVC.Controller.getInfo();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            // 切换tab
            $('#tabl').tabs({
                border: false,
                onSelect: function (title) {
                    var tab = $('#tabl').tabs('getSelected');
                    var index = $('#tabl').tabs('getTabIndex', tab);
                    $('#tabIndex').val(index);
                    if (index === 0) {
                        $('#grididname').val('#wait-datagrid');
                        $('#currentGridUrl').val(WaitMVC.URLs.List.url);
                        WaitMVC.Controller.getInitData();
                    }
                    if (index === 1) {
                        $('#grididname').val('#check-datagrid');
                        $('#currentGridUrl').val(CheckMVC.URLs.List.url);
                        CheckMVC.Controller.getInitData();
                    }
                    if (index === 2) {
                        $('#grididname').val('#pre-datagrid');
                        $('#currentGridUrl').val(PreMVC.URLs.List.url);
                        PreMVC.Controller.getInitData();
                    }
                    if (index === 3) {
                        $('#grididname').val('#online-datagrid');
                        $('#currentGridUrl').val(OnlineMVC.URLs.List.url);
                        OnlineMVC.Controller.getInitData();
                    }
                    if (index === 4) {
                        $('#grididname').val('#fail-datagrid');
                        $('#currentGridUrl').val(FailMVC.URLs.List.url);
                        FailMVC.Controller.getInitData();
                    }
                }
            });

            $("#wait-dlg").dialog({
                closed: true,
                modal: false,
                width: 550,
                height: 400,
                iconCls: 'icon-info',
                title: '通过审核',
                buttons: [{
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: WaitMVC.Controller.save
                }, {
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#wait-dlg").dialog('close');
                    }
                }]
            });

            $("#reject-dlg").dialog({
                closed: true,
                modal: false,
                width: 500,
                height: 300,
                iconCls: 'icon-info',
                title: '拒绝申请',
                buttons: [{
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: WaitMVC.Controller.saveReject
                }, {
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#reject-dlg").dialog('close');
                    }
                }]
            });

            $("#check-dlg").dialog({
                closed: true,
                modal: false,
                width: 750,
                height: 400,
                iconCls: 'icon-info',
                title: '安排排期',
                buttons: [{
                    text: '保存',
                    iconCls: 'icon-save',
                    handler: CheckMVC.Controller.save
                }, {
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#check-dlg").dialog('close');
                    }
                }]
            });

            $("#info-dlg").dialog({
                closed: true,
                modal: false,
                width: 900,
                height: 500,
                maximizable: true,
                collapsible: true,
                resizable: true,
                title: '项目详情',
                iconCls: 'icon-info',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#info-dlg").dialog('close');
                    }
                }]
            });

            $('#fail-dlg').dialog({
                closed: true,
                modal: false,
                width: 400,
                height: 300,
                iconCls: 'icon-info',
                title: '拒绝信息',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#fail-dlg").dialog('close');
                    }
                }]
            });

            $('#grididname').val('#wait-datagrid');
        }


    },
    Controller: {
        getInfo: function () {
            //获取当前点击的datagrid id
            var idname = $('#grididname').val();
            var row = $(idname).datagrid('getSelected');
            if (row) {
                $('#info-dlg').dialog('open');
                var infoUrl = ProjectCheckCommon.baseUrl + 'info';
                ProjectCheckMVC.Controller.clearFill();
                $.getJSON(infoUrl, {id: row.id}, function (d) {
                    ProjectCheckMVC.Controller.fillInfo(d);
                });
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        fillInfo: function (d) {
            if (d.data.projectTokenInfoDTO != null) {
                var token = d.data.projectTokenInfoDTO;
                $('#name').text(token.name);
                $('#mobile').text(token.mobile);
                $('#email').text(token.email);
                $('#company').text(token.company);
                $('#token').text(token.token);
                $('#tokenSymbol').text(token.tokenSymbol);
                $('#imgName').attr('href', token.imgName);
                $('#issue').text(token.issue);
                $('#coinmarketcap').text(token.coinmarketcap);
                $('#exchangeName').text(token.exchangeName);
                $('#tradeVolume').text(token.tradeVolume);
                $('#startTime').text(moment(new Date(token.startTime)).format('YYYY-MM-DD HH:mm:ss'));
                $('#price').text(token.price);
                $('#circulating').text(token.circulating);
                $('#possessUsers').text(token.possessUsers);
            }
            if (d.data.projectApplyInfoDTO != null) {
                var apply = d.data.projectApplyInfoDTO;
                $('#companyPosition').text(apply.companyPosition);
                $('#company').text(apply.company);
                $('#website').text(apply.website);
                $('#whitePaper').attr('href', apply.whitePaper);
                $('#projectInfo').text(apply.projectInfo);
                $('#projectStage').text(apply.projectStage);
                $('#projectObjective').text(apply.projectObjective);
                $('#projectProgress').text(apply.projectProgress);
                $('#productAddress').text(apply.productAddress);
                $('#githubUrl').text(apply.githubUrl);
                $('#team').text(apply.team);
                $('#teamCounselor').text(apply.teamCounselor);
                $('#fulltimeNumber').text(apply.fulltimeNumber);
                $('#noFulltimeNumber').text(apply.noFulltimeNumber);
                $('#raiseTotal').text(apply.raiseTotal);
                $('#raisePrice').text(apply.raisePrice);
                $('#raiseInvest').text(apply.raiseInvest);
                $('#raiseRatio').text(apply.raiseRatio);
                $('#raiseDate').text(moment(new Date(apply.raiseDate)).format('YYYY-MM-DD HH:mm:ss'));
                $('#raiseRule').text(apply.raiseRule);
                $('#icoTotal').text(apply.icoTotal);
                $('#icoPrice').text(apply.icoPrice);
                $('#icoInvest').text(apply.icoInvest);
                $('#icoRatio').text(apply.icoRatio);
                $('#icoDate').text(moment(new Date(apply.icoDate)).format('YYYY-MM-DD HH:mm:ss'));
                $('#icoRule').text(apply.icoRule);
                $('#telegramLink').text(apply.telegramLink);
                $('#telegramLinkMembers').text(apply.telegramLinkMembers);
                $('#wechatLink').text(apply.wechatLink);
                $('#wechatLinkMembers').text(apply.wechatLinkMembers);
                $('#qq').text(apply.qq);
                $('#kakaoTalk').text(apply.kakaoTalk);
                $('#twitter').text(apply.twitter);
                $('#facebook').text(apply.facebook);
                $('#weibo').text(apply.weibo);
                $('#reddit').text(apply.reddit);
                $('#others').text(apply.others);
                $('#contract').text(apply.contract);
                $('#wallet').text(apply.wallet);
                if (apply.walletType == 1) {
                    $('#walletType').text('ERC20');
                } else {
                    $('#walletType').text('其他');
                }
            }
        },
        clearFill: function () {
            var list = $(".info");
            for (var i = 0; i < list.length; i++) {
                list[i].innerHTML = "";
            }
            $('#imgName').attr('href', '');
            $('#whitePaper').attr('href', '');
        },
        add: function () {
            var options = ProjectCheckMVC.Util.getOptions();
            options.title = '新增申请';
            EasyUIUtils.openAddDlg(options);

        },

        edit: function () {
            var gridId = $('#grididname').val();
            var row = $(gridId).datagrid('getSelected');
            if (row) {

                var infoUrl = ProjectCheckCommon.baseUrl + 'getProjectInfo';
                $.get(infoUrl, {id: row.id}, function (d) {
                    var options = ProjectCheckMVC.Util.getOptions();
                    options.iconCls = 'icon-edit1';
                    options.data = d.data;
                    options.title = '修改申请';
                    options.data.startTime = ProjectCheckMVC.Util.dateToString(new Date(d.data.startTime));
                    if (options.data) {
                        $(options.formId).form('clear');
                        $(options.actId).val("edit");
                        $(options.rowId).val(options.data.tokenInfoId);
                        $(options.dlgId).dialog({iconCls: options.iconCls})
                            .dialog('open')
                            .dialog('center')
                            .dialog('setTitle', options.title);
                        $(options.formId).form('load', options.data);
                        $('#imgName-file').filebox('setText', d.data.imgName);
                        $("#whitePaper-file").filebox('setText', d.data.whitePaper);
                        options.callback(options.data);
                        $('#projectInfo-dlg').dialog('open');
                    } else {
                        EasyUIUtils.showMsg("请您先选择一个选项!");
                    }
                });

            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },

        save: function () {
            var action = $('#modal-action').val();
            var gridId = $('#grididname').val();
            var currentGridUrl = $('#currentGridUrl').val();
            var options = ProjectCheckMVC.Util.getOptions();

            if (action === "edit") {
                options.url = ProjectCheckMVC.URLs.edit.url;
            } else {
                //添加操作
                options.url = ProjectCheckMVC.URLs.add.url;
            }
            options.gridId = gridId;
            options.gridUrl = currentGridUrl;
            //修改后刷新列表
            EasyUIUtils.save(options);
            return;
        },

        listenSee: function () {
            $("#see").change(function () {
                var selected = $(this).children('option:selected').val();
                alert(selected)
                if (selected == 1) {
                    $("#productAddress").attr("disabled", false);
                } else if (selected == "0") {
                    $("#productAddress").attr("disabled", true);
                }
            });
        }


    },
    Util: {

        getOptions: function () {
            return {
                dlgId: '#projectInfo-dlg',
                formId: '#projectInfo-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                url: null,
                gridId: null,
                gridUrl: null
            };
        },

        uploadImg: function () {
            var files = $("#whitePaper-file").filebox('files');

            var formData = new FormData();
            formData.append("file", files[0]);

            //调用apicontroller后台action方法，将form数据传递给后台处理。contentType必须设置为false,否则chrome和firefox不兼容
            $.ajax({
                url: ProjectCheckMVC.URLs.img.url,
                type: 'POST',
                data: formData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (data) {
                    if (data.code == 0) {
                        $('#whitePaper').val(data.data.fileName);
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

        uploadImg2: function () {
            var files = $("#imgName-file").filebox('files');

            var formData = new FormData();
            formData.append("file", files[0]);

            //调用apicontroller后台action方法，将form数据传递给后台处理。contentType必须设置为false,否则chrome和firefox不兼容
            $.ajax({
                url: ProjectCheckMVC.URLs.img.url,
                type: 'POST',
                data: formData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (data) {
                    if (data.code == 0) {
                        $('#imgName').val(data.data.fileName);
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

        dateToString: function (date) {
            var year = date.getFullYear();
            var month = (date.getMonth() + 1).toString();
            var day = (date.getDate()).toString();
            if (month.length == 1) {
                month = "0" + month;
            }
            if (day.length == 1) {
                day = "0" + day;
            }
            var dateTime = year + "-" + month + "-" + day;
            return dateTime;
        },
    }
};


var WaitMVC = {
    URLs: {
        List: {
            url: ProjectCheckCommon.baseUrl + '0/list'
        },
        Pass: {
            url: ProjectCheckCommon.baseUrl + 'pass'
        },
        Reject: {
            url: ProjectCheckCommon.baseUrl + 'reject'
        }
    },
    Controller: {
        getInitData: function () {
            $('#wait-datagrid').datagrid('load', {});
        },
        pass: function () {
            $("#wait-dlg").dialog('open');
            $('#deposit').textbox('setValue', '');
            $('#tokenNumber').textbox('setValue', '');
            $('#tokenUrl').textbox('setValue', '');
            $('#contacts').textbox('setValue', '');
            $('#wechat').textbox('setValue', '');
        },
        save: function () {
            var row = $('#wait-datagrid').datagrid('getSelected');
            var deposit = $('#deposit').textbox('getValue');
            var token = $('#tokenNumber').textbox('getValue');
            var token_url = $('#tokenUrl').textbox('getValue');
            var contact = $('#contacts').textbox('getValue');
            var wechat = $('#wechat').textbox('getValue');

            var data = {
                id: row.id,
                deposit: deposit,
                tokenNumber: token,
                tokenUrl: token_url,
                contacts: contact,
                wechat: wechat
            };

            $.post(WaitMVC.URLs.Pass.url, data, function (d) {
                if (!d.code) {
                    // $.messager.alert('success', "success", 'success');
                    $('#wait-datagrid').datagrid('load', {});
                } else {
                    $.messager.alert('fail', "fail", 'error');
                }
            }, 'json');

            $('#wait-dlg').dialog('close');
        },
        reject: function () {
            $('#reject-dlg').dialog('open');
            $('#reason').textbox('setValue', '');
        },
        saveReject: function () {
            var row = $('#wait-datagrid').datagrid('getSelected');
            var reasonDesc = $('#reason').textbox('getValue');

            var data = {
                tokenInfoId: row.id,
                reason: reasonDesc
            };

            $.post(WaitMVC.URLs.Reject.url, data, function (d) {
                if (!d.code) {
                    // $.messager.alert('success', "success", 'success');
                    $('#wait-datagrid').datagrid('load', {});
                } else {
                    $.messager.alert('fail', "fail", 'error');
                }
            }, 'json');

            $('#reject-dlg').dialog('close');
        }

    }
};

var CheckMVC = {
    URLs: {
        List: {
            url: ProjectCheckCommon.baseUrl + '1/list'
        },
        Schedule: {
            url: ProjectCheckCommon.baseUrl + 'schedule'
        },
        TokenResult: {
            url: ProjectCheckCommon.baseUrl + 'getTokenResult'
        },
        DepositResult: {
            url: ProjectCheckCommon.baseUrl + 'getDepositResult'
        }
    },
    Controller: {
        getInitData: function () {
            $('#check-datagrid').datagrid('load', {});
        },
        save: function () {
            var data = {
                id: $('#scheduleId').val(),
                onlineTime: $('#schedule').datetimebox('getValue')
            };

            $.post(CheckMVC.URLs.Schedule.url, data, function (d) {
                if (!d.code) {
                    // $.messager.alert('success', "success", 'success');
                    $('#check-datagrid').datagrid('load', {});
                } else {
                    $.messager.alert('error', "error", 'error');
                }
            }, 'json');

            $('#check-dlg').dialog('close');
        },
        plan: function (index, name) {
            $('#check-datagrid').datagrid('selectRow', index);
            var row = $('#check-datagrid').datagrid('getSelected');

            $('#schedule').datetimebox('setValue', '');

            if (row) {
                $('#check-dlg').dialog('open');

                $('#scheduleId').val(row.id);

                $.getJSON(CheckMVC.URLs.DepositResult.url, {id: row.id}, function (d) {
                    if (!d.code) {
                        var t = d.data[0];
                        if (t) {
                            $('#deposit_tradeNo').text(t.tradeNo);
                            $('#deposit_number').text(t.amount);
                            $('#deposit_time').text(moment(new Date(t.createdDate)).format('YYYY-MM-DD HH:mm:ss'));
                        }
                    }
                });

                var data = {
                    userId: row.userId,
                    tokenUrl: row.tokenUrl,
                    tokenSymbol: row.tokenSymbol
                };

                $.getJSON(CheckMVC.URLs.TokenResult.url, data, function (d) {
                    if (!d.code) {
                        var t = d.data.rows[0];
                        if (t) {
                            $('#token_tradeNo').text(t.txId);
                            $('#token_number').text(t.amount);
                            $('#token_address').text(t.address);
                            $('#token_time').text(moment(new Date(t.createDate)).format('YYYY-MM-DD HH:mm:ss'));
                        }
                    }
                });
            }
        }

    }
};

var PreMVC = {
    URLs: {
        List: {
            url: ProjectCheckCommon.baseUrl + '2/list'
        },
        Online: {
            url: ProjectCheckCommon.baseUrl + 'online'
        }
    },
    Controller: {
        getInitData: function () {
            $('#pre-datagrid').datagrid('load', {});
        },
        online: function (index) {
            $('#pre-datagrid').datagrid('selectRow', index);
            var row = $('#pre-datagrid').datagrid('getSelected');

            if (row) {
                $.messager.confirm('确认', '请确认您已经完成在币币管理中上线币对', function (r) {
                    if (r) {
                        $.post(PreMVC.URLs.Online.url, {id: row.id}, function (d) {
                            if (!d.code) {
                                // $.messager.alert("success", "success", "success");
                                $('#pre-datagrid').datagrid('load', {});
                            } else {
                                $.messager.alert("error", "error", "error");
                            }
                        });
                    }
                });
            }

        }
    }
};

var OnlineMVC = {
    URLs: {
        List: {
            url: ProjectCheckCommon.baseUrl + '3/list',
            method: 'GET'
        }
    },
    Controller: {
        getInitData: function () {
            $('#online-datagrid').datagrid('load', {});
        }
    }
};

var FailMVC = {
        URLs: {
            List: {
                url: ProjectCheckCommon.baseUrl + '-1/list',
            },
            Reason: {
                url: ProjectCheckCommon.baseUrl + 'getRejectReason'
            }
        },
        Controller: {
            getInitData: function () {
                $('#fail-datagrid').datagrid('load', {});
            },
            rejectInfo: function (index) {
                $('#fail-datagrid').datagrid('selectRow', index);
                var row = $('#fail-datagrid').datagrid('getSelected');

                if (row) {
                    $('#fail-reason').text('');

                    $('#fail-dlg').dialog('open');

                    $.getJSON(FailMVC.URLs.Reason.url, {id: row.id}, function (d) {
                        if (!d.code) {
                            $('#fail-reason').text(d.data.reason)
                        } else {
                            $.messager.alert('error', "error", 'error');
                        }
                    });
                } else {
                    $.messager.alert('警告', '请选中一条记录!', 'info');
                }
            }
        }
    }
;