$(function () {
    Kyc.init();
});

var Kyc = {
    init: function () {
        KycMVC.View.initControl();
        KycMVC.View.bindEvent();
        KycMVC.View.bindValidate();
    }
};

var KycCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/users/kyc/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/'
};

var KycMVC = {
    URLs: {
        kyc: {
            url: KycCommon.baseUrl + 'getKycUser',
            method: 'POST'
        },
        pass: {
            url: KycCommon.baseUrl + 'pass',
            method: 'POST'
        },
        unpass: {
            url: KycCommon.baseUrl + 'unpass',
            method: 'POST'
        },
        list: {
            url: KycCommon.baseUrl + 'list',
            method: 'GET'
        },
        listReason: {
            url: KycCommon.baseUrl + 'listReason',
            method: 'GET'
        },
        addReason: {
            url: KycCommon.baseUrl + 'addReason',
            method: 'POST'
        },
        removeKyc: {
            url: KycCommon.baseUrl + 'remove',
            method: 'GET'
        }
    },
    Model: {},
    View: {
        initControl: function () {

            $('#kyc-reason-locale').combobox({
                valueField: 'id',
                textField: 'value',
                data: [
                    {
                        id: 'zh-cn',
                        value: '中文'
                    }, {
                        id: 'en-us',
                        value: '英文'
                    }
                ],
                onSelect: function (record) {
                    KycMVC.Util.switchKycReasonLocale(record.id);
                }
            });

            $('#report-detail-dlg').dialog({
                closed: true,
                modal: true,
                striped: true,
                collapsible: true,
                resizable: true,
                width: 950,
                height: window.innerHeight - 100 || document.documentElement.clientHeight - 100 || document.body.clientHeight - 100,
                maximizable: true,
                iconCls: 'icon-info',
                buttons: [{
                    text: '通过',
                    iconCls: 'icon-ok',
                    handler: KycMVC.Controller.ok
                }, {
                    text: '拒绝',
                    iconCls: 'icon-no',
                    handler: KycMVC.Controller.no
                }, {
                    text: '返回',
                    iconCls: 'icon-back',
                    handler: function () {
                        $("#report-detail-dlg").dialog('close');
                    }
                }]
            });

            $('.kyc1-pictures').viewer();
            $('.kyc2-pictures').viewer();

            $('#add-reason-dlg').dialog({
                closed: true,
                modal: false,
                width: 800,
                height: 300,
                iconCls: 'icon-add',
                title: '添加新理由',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#add-reason-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: KycMVC.Controller.saveReason
                }]
            });

            $('#kyc-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                sortOrder: 'desc',
                pageSize: 50,
                url: KycMVC.URLs.list.url,
                toolbar: [{
                    text: '审核',
                    iconCls: 'icon-info',
                    handler: function () {
                        KycMVC.Controller.showDetail();
                    }
                }, '-', {
                    text: '全部',
                    iconCls: 'icon-reload',
                    handler: function () {
                        $('#kyc-datagrid').datagrid('load', {});
                    }
                }, '-', {
                    text: '删除',
                    iconCls: 'icon-remove',
                    handler: function () {
                        KycMVC.Controller.remove();
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
                        width: 100,
                        sortable: true

                    }, {
                        field: 'firstName',
                        title: '用户名',
                        width: 100,
                        sortable: true

                    }, {
                        field: 'country',
                        title: '地区',
                        width: 100,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return KycMVC.Util.getCountryStr(value);
                        }
                    },
                    {
                        field: 'status',
                        title: '状态',
                        width: 100,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return KycMVC.Util.getStatusStr(value);
                        }
                    },
                    {
                        field: 'level',
                        title: 'kyc级别',
                        width: 100,
                        sortable: true,
                        formatter: function (value, row, index) {
                            if (value === 1) {
                                return "KYC1";
                            }
                            else if (value == 2) {
                                return "KYC2";
                            }
                            else {
                                return "";
                            }

                        }

                    },
                    {
                        field: 'dealUserId',
                        title: '受理客服',
                        width: 100,
                        sortable: true

                    },
                    {
                        field: 'createdDate',
                        title: '申请时间',
                        width: 100,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss");
                        }

                    },
                    {
                        field: 'updatedDate',
                        title: '审核时间',
                        width: 100,
                        sortable: true,
                        formatter: function (value, row, index) {
                            return moment(new Date(value)).format("YYYY-MM-DD HH:mm:ss");
                        }

                    }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return KycMVC.Controller.showDetail();
                },
                onLoadSuccess: function () {
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

        },
        bindEvent: function () {
            $('#btn-search').bind('click', KycMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {

        }

    },
    Controller: {
        doOption: function (index, name) {
            $('#kyc-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return KycMVC.Controller.edit();
            }
        },
        showDetail: function () {
            KycMVC.Util.isRowSelected(function (row) {
                $('#report-detail-dlg').dialog('open').dialog('center');
                //根据条件调用接口返回的数据

                KycMVC.Util.fillDetailLabels(row);
            });
        },
        edit: function () {
            var row = $('#kyc-datagrid').datagrid('getSelected');
            if (row) {
                //id传给后台返回操作记录显示在前台
                var options = KycMVC.Util.getOptions();
                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '审核[' + options.data.code + ']';
                EasyUIUtils.openEditDlg(options);

            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#kyc-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: KycMVC.URLs.removeKyc.url,
                    data: {
                        userId: row.userId
                    },
                    gridId: '#kyc-datagrid',
                    gridUrl: KycMVC.URLs.list.url,
                    callback: function (rows) {
                    }
                };
                EasyUIUtils.remove(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        ok: function () {
            //根据当前页面参数，取相关需要的值，仍给后台
            var id = $("#uuId").val();
            var leavingMessage = $("#leavingMessage").textbox('getValue');
            var remarks = $("#remarks").textbox('getValue');
            var userId = $("#userId").val();
            var level = $("#level").val();
            var countryCode = $("#countryCode").val();
            var mobile = $("#mobile").val();
            var email = $("#email").val();

            $.ajax({
                url: KycMVC.URLs.pass.url,
                type: 'POST',
                data: {
                    id: id,
                    leavingMessage: leavingMessage,
                    remarks: remarks,
                    userId: userId,
                    level: level,
                    countryCode: countryCode,
                    mobile: mobile,
                    email: email
                },
                success: function (data) {
                    $("#report-detail-dlg").dialog('close');
                    EasyUIUtils.reloadDatagrid('#kyc-datagrid');
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
        no: function () {
            //根据当前页面参数，取相关需要的值，仍给后台
            var id = $("#uuId").val();
            var leavingMessage = $("#leavingMessage").textbox('getValue');
            var remarks = $("#remarks").textbox('getValue');
            var userId = $("#userId").val();
            var level = $("#level").val();
            var countryCode = $("#countryCode").val();
            var mobile = $("#mobile").val();
            var email = $("#email").val();

            if (leavingMessage == "") {
                $.messager.alert('提示', '拒绝状态,必须请填写理由！！', 'info');

                return;
            } else if (remarks == "") {
                $.messager.alert('提示', '拒绝状态,必须请填写备注！！', 'info');

                return;
            } else {
                $.ajax({
                    url: KycMVC.URLs.unpass.url,
                    type: 'POST',
                    data: {
                        id: id,
                        leavingMessage: leavingMessage,
                        remarks: remarks,
                        userId: userId,
                        level: level,
                        countryCode: countryCode,
                        mobile: mobile,
                        email: email
                    },
                    success: function (data) {
                        $("#report-detail-dlg").dialog('close');
                        EasyUIUtils.reloadDatagrid('#kyc-datagrid');
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

        },
        find: function () {
            var params = KycMVC.Util.getQueryParams();

            $('#kyc-datagrid').datagrid('load', params);
        },
        saveReason: function () {
            var options = {
                gridId: null,
                gridUrl: null,
                dlgId: "#add-reason-dlg",
                formId: "#add-reason-form",
                url: KycMVC.URLs.addReason.url,
                callback: function (result) {
                    if (!result.code) {
                        $.get(KycMVC.URLs.listReason.url, "", function (_datax) {

                            $("#kyc-reason-all").combobox({
                                data: _datax.data,
                                valueField: "id",
                                textField: "value",
                                onSelect: function (record) {
                                    $("#leavingMessage").textbox("setValue", record.value);
                                }
                            });

                        });
                    }
                }
            };

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
                gridId: null,
            };
        },
        getQueryParams: function () {
            var userProperty = $("#profile-userProperty").combobox('getValue');
            var userValue = $("#profile-userValue").textbox('getValue');
            var cardNumber = $("#profile-cardNumber").textbox('getValue');
            var country = $("#profile-country").combobox('getValue');
            var status = $("#profile-status").combobox('getValue');
            var level = $("#profile-level").combobox('getValue');
            var dealUserId = $("#profile-dealUserId").textbox('getValue');
            var range = $("#profile-range").combobox('getValue');
            var beginTime = $("#beginTime").datebox('getValue');
            var endTime = $("#endTime").datebox('getValue');

            var params = {
                userProperty: userProperty,
                userValue: userValue,
                cardNumber: cardNumber,
                country: country,
                status: status,
                level: level,
                dealUserId: dealUserId,
                range: range,
                beginTime: beginTime,
                endTime: endTime
            }

            return params;
        },
        isRowSelected: function (func) {
            var row = $('#kyc-datagrid').datagrid('getSelected');
            if (row) {
                func(row);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        fillDetailLabels: function (data) {
            $('#kyc-datagrid label').each(function (i) {
                $(this).text("");
            });

            $("#leavingMessage").textbox("setValue", "");
            $("#remarks").textbox("setValue", "");

            $("#profile-detail-userId").text("");
            $("#profile-detail-mobile").text("");
            $("#profile-detail-email").text("");
            $("#profile-detail-allName").text("");

            $("#profile-detail-status").text("");
            $("#profile-detail-requestId").text("");
            $("#profile-detail-cardType").text("");
            $("#profile-detail-country").text("");
            $("#profile-detail-gender").text("");
            $("#profile-detail-cardNumber").text("");
            $("#profile-detail-firstStatus").text("");
            $("#profile-detail-validDate").text("");
            $("#profile-detail-frontImg").attr("src", "");
            $("#profile-detail-backImg").attr("src", "");
            $("#report-detail-comment").html("");

            $("#profile-detail-addiess1").text("");
            $("#profile-detail-addiess2").text("");
            $("#profile-detail-addiess3").text("");
            $("#profile-detail-city").text("");
            $("#profile-detail-zipCode").text("");

            $("#profile-detail-cardImg").attr("src", "");
            $("#profile-detail-handsImg").attr("src", "");
            $("#profile-detail-addressImg").attr("src", "");

            $("#profile-detail-facePlusImg").attr("src", "");

            $("#kyc").combobox("setValue", "0");
            $("#kyc-reason-locale").combobox("setValue", "zh-cn");

            $("#hide1").attr("style", "display:none;");
            $("#hide2").attr("style", "display:none;");
            $("#hide5").attr("style", "display:none;");
            $("#hide5-1").attr("style", "display:none;");
            $("#hide6").attr("style", "display:none;");

            // 选择国家、地区，0：全部，1：国内，2：国际
            var country = data["country"];
            var level = data["level"];
            var userId = data["userId"];
            var uuid = data["id"];

            $("#uuId").val(uuid);
            $("#userId").val(userId);
            $("#level").val(level);
            $("#countryCode").val(country);

            $("#mobile").val("");
            $("#email").val("");

            if (userId) {
                $("#profile-detail-userId").text(userId);
            }

            var url = KycMVC.URLs.kyc.url + "?userId=" + userId + "&level=" + level;

            if (country == 156) {
                //国内
                if (level == 1) {
                    // 证件正面、反面
                    $("#hide6").attr("style", "display:black;");
                }

                if (level == 2) {
                    $("#hide6").attr("style", "display:black;");
                    $("#hide5").attr("style", "display:black;");
                    $("#hide5-1").attr("style", "display:black;");

                }
            } else {
                //国际
                if (level == 1) {

                }

                if (level == 2) {
                    $("#hide5").attr("style", "display:black;");
                    $("#hide1").attr("style", "display:black;");
                    $("#hide2").attr("style", "display:black;");
                }
            }

            $.ajax({
                url: url,
                type: 'POST',
                data: '',
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                success: function (data) {
                    KycMVC.Util.showKycDetailData(data);
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
        getLayoutName: function (layout) {
            if (layout === 1) {
                return "横向布局";
            }
            if (layout === 2) {
                return "纵向布局";
            }
            return "无";
        },
        loadRoleList: function () {
            $.getJSON(KycMVC.URLs.getRoleList.url, function (src) {
                KycMVC.Model.roles = src.data;
            });
        },
        getStatusStr: function (status) {
            var statusStr = '初始值';

            if (status == 0) {
                statusStr = '初始值';
            }

            if (status == 1) {
                statusStr = '审核通过';
            }

            if (status == 2) {
                statusStr = '驳回';
            }

            if (status == 3) {
                statusStr = '其他异常';
            }

            if (status == 4) {
                statusStr = '待审核';
            }

            return statusStr;
        },
        getCardTypeStr: function (cardType) {
            var cardTypeStr = '';

            if (cardType == 1) {
                cardTypeStr = '身份证';
            }

            if (cardType == 2) {
                cardTypeStr = '护照';
            }

            if (cardType == 3) {
                cardTypeStr = '驾驶证';
            }

            return cardTypeStr;
        },
        getCountryStr: function (country) {
            var countryStr = '';

            if (country == 156) {
                countryStr = '国内';
            } else {
                countryStr = '国际';
            }

            return countryStr;
        },
        switchKycReasonLocale: function (locale) {
            var url = KycMVC.URLs.listReason.url + "?locale=" + locale;

            $.get(url, "", function (_datax) {

                $("#kyc-reason-all").combobox({
                    data: _datax.data,
                    valueField: "id",
                    textField: "value",
                    onSelect: function (record) {
                        $("#leavingMessage").textbox("setValue", record.value);
                    },
                    onLoadSuccess: function () {
                        var allData = $("#kyc-reason-all").combobox("getData");

                        if (allData) {
                            $("#kyc-reason-all").combobox("setValue", allData[0]["id"]);
                        }
                    }
                });

            });
        },
        showKycDetailData: function (kycData) {
            $("#profile-detail-mobile").text(kycData.data.mobile);
            $("#mobile").val(kycData.data.mobile);

            $("#profile-detail-email").text(kycData.data.email);
            $("#email").val(kycData.data.email);

            $("#profile-detail-allName").text(kycData.data.firstName + " " + kycData.data.middleName + " " + kycData.data.lastName);

            if (kycData.data.userKycEventList) {
                var commenthtml = "<table class='table'><tr><th>时间</th><th>客服</th><th>理由</th><th>备注</th></tr>";

                for (var ii = 0; ii < kycData.data.userKycEventList.length; ii++) {
                    var datetime = moment(new Date(kycData.data.userKycEventList[ii].createdDate)).format("YYYY-MM-DD HH:mm:ss");

                    commenthtml += "<tr>"
                        + "<td>" + datetime + "</td>"
                        + "<td>" + kycData.data.userKycEventList[ii].dealUserName + "</td>"
                        + "<td>" + kycData.data.userKycEventList[ii].rejectReason + "</td>"
                        + "<td>" + kycData.data.userKycEventList[ii].remarks + "</td>"
                        + "</tr>";
                }

                commenthtml += "</table>";

                $("#report-detail-comment").html(commenthtml);
            }

            var status = KycMVC.Util.getStatusStr(kycData.data.status);
            var firstStatus = KycMVC.Util.getStatusStr(kycData.data.firstStatus);

            var level = kycData.data.level;
            if (level == 1) {
                $("#profile-detail-firstStatus").text(status);
            } else if (level == 2) {
                $("#profile-detail-firstStatus").text(firstStatus);
                $("#profile-detail-status").text(status);
            }

            var requestId = kycData.data.requestId;
            if (requestId) {
                $("#profile-detail-requestId").text(requestId);
            }

            var cardType = KycMVC.Util.getCardTypeStr(kycData.data.cardType);
            $("#profile-detail-cardType").text(cardType);

            var country = KycMVC.Util.getCountryStr(kycData.data.country) + "(" + kycData.data.country + ")";
            $("#profile-detail-country").text(country);

            $("#profile-detail-gender").text(kycData.data.gender);
            $("#profile-detail-cardNumber").text(kycData.data.cardNumber);

            $("#profile-detail-frontImg").attr("src", kycData.data.frontImg);
            $("#profile-detail-backImg").attr("src", kycData.data.backImg);
            $("#profile-detail-validDate").text(kycData.data.validDate);

            $("#profile-detail-addiess1").text(kycData.data.address1);
            $("#profile-detail-addiess2").text(kycData.data.address2);
            $("#profile-detail-addiess3").text(kycData.data.address3);
            $("#profile-detail-city").text(kycData.data.city);
            $("#profile-detail-zipCode").text(kycData.data.zipCode);

            $("#profile-detail-cardImg").attr("src", kycData.data.cardImg);
            $("#profile-detail-handsImg").attr("src", kycData.data.handsImg);
            $("#profile-detail-addressImg").attr("src", kycData.data.addressImg);

            $("#profile-detail-facePlusImg").attr("src", kycData.data.handsImg);
        }

    }
};