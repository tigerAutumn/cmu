$(function () {
    Lockasset.init();
});

var Lockasset = {
    init: function () {
        LockassetMVC.View.initControl();
        LockassetMVC.View.bindEvent();
    }
};

var LockassetCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/activity/lockasset/actlockassetconfig/',
    currencyBaseUrl: BossGlobal.ctxPath + '/v1/boss/asset/lockup/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    brokerBaseUrl: BossGlobal.ctxPath + '/v1/boss/admin/broker/',
    uploadUrl: BossGlobal.ctxPath + '/v1/boss/upload/',

};

var LockassetMVC = {
    URLs: {
        Search: {
            url: LockassetCommon.baseUrl + 'list',
            method: 'GET'
        },
        Save: {
            url: LockassetCommon.baseUrl + 'add',
            method: 'POST'
        },
        Edit: {
            url: LockassetCommon.baseUrl + 'edit',
            method: 'POST'
        },
        Code: {
            url: LockassetCommon.baseUrl + 'code',
            method: 'GET'
        },
        Remove: {
            url: LockassetCommon.baseUrl + 'delete',
            method: 'POST'
        },
        getBrokerList: {
            url: LockassetCommon.brokerBaseUrl + 'list',
            method: 'GET'
        },
        getAllCurrency: {
            url: LockassetCommon.currencyBaseUrl + 'listAllCurrency',
            method: 'GET'
        },
        img: {
            url: LockassetCommon.uploadUrl + 'activity/upload',
            method: 'GET'
        },
    },
    Model: {},
    View: {
        initControl: function () {

            $("#brokerId").combobox({
                url: LockassetMVC.URLs.getBrokerList.url,
                method: 'get',
                valueField: "id",
                textField: "brokerName",
                editable: true,
                value: '',
                prompt: '选择券商',
                panelHeight: 200
            });

            $.get(LockassetMVC.URLs.getAllCurrency.url, "", function (_datax) {

                $("#currencyId").combobox({
                    data: _datax.data,
                    valueField: "id",
                    textField: "symbol",
                    onLoadSuccess: function () {
                    }
                });

            });
            $('#bannerUrl-file').filebox({
                buttonText: '选择',
                buttonAlign: 'right',
                prompt: '选择图片',
                onChange: function (newVal, oldVal) {
                    if (newVal) {
                        LockassetMVC.Util.uploadImg();
                    }
                }
            });

            var optionsDatagrid = $('#lockasset-options-datagrid');
            optionsDatagrid.datagrid({
                scrollbarSize: 0,
                height: 200,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        optionsDatagrid.datagrid('appendRow', {
                            periodName: '',
                            periodValue: '',
                            rewardName: '',
                            rewardValue: '',
                            minAmount: ''
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
                    {field: 'periodName', title: '周期名称', width: 80, sortable: false, editor: 'text'},
                    {field: 'periodValue', title: '周期时长（天）', width: 80, resizable: false, editor: 'text'},
                    {field: 'rewardName', title: '收益说明', width: 80, resizable: false, editor: 'text'},
                    {field: 'rewardValue', title: '奖励系数（%）', width: 80, resizable: false, editor: 'text'},
                    {field: 'minAmount', title: '起购数量', width: 80, resizable: false, editor: 'text'}
                ]]
            });
            optionsDatagrid.datagrid('enableCellEditing');

            $("#type").combobox({
                data: [{
                    value: 1,
                    text: 'win锁仓'
                }, {
                    value: 2,
                    text: '一次性解锁'
                }, {
                    value: 3,
                    text: '分期解锁'
                }],
                valueField: 'value',
                textField: 'text',
                required: true,
                onChange: function () {
                    var type = $(this).val();
                    if (type == 3) {
                        $("#rateTr2").show();
                        $("#rateTr4").show();
                        $("#rateTr5").show();
                    } else if(type == 1){
                        $("#rateTr2").hide();
                        $("#rateTr4").hide();
                        $("#unlockRateRemainder").val("");
                        $("#period").val("");
                        $("#rateTr5").hide();
                    }else{
                        $("#rateTr2").hide();
                        $("#rateTr4").hide();
                        $("#unlockRateRemainder").val("");
                        $("#period").val("");
                        $("#rateTr5").show();
                    }
                }
            });

            $('#lockasset-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: false,
                sortOrder: 'desc',
                pageSize: 50,
                pageNumber: 1,
                url: LockassetMVC.URLs.Search.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#lockasset-datagrid')
                    }
                }, '-', {
                    iconCls: 'icon-add',
                    handler: function () {
                        LockassetMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit',
                    handler: function () {
                        LockassetMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-remove',
                    handler: function () {
                        LockassetMVC.Controller.remove();
                    }
                },'-', {
                    iconCls: 'icon-redo',
                    handler: function () {
                        LockassetMVC.Controller.copy();
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
                        field: 'actIdentify',
                        title: '活动标识',
                        width: 100,
                        sortable: true,
                    },
                    {
                        field: 'locale',
                        title: '语言',
                        width: 100,
                        sortable: true,
                    },
                    {
                        field: 'actName',
                        title: '活动名称',
                        width: 100,
                        sortable: true,
                    },
                    {
                        field: 'amoutTotal',
                        title: '锁仓总量',
                        width: 100,
                        sortable: true,
                    },
                    {
                        field: 'actStartTime',
                        title: '活动时间',
                        width: 100,
                        sortable: true,
                        formatter: function (val) {
                            return moment(new Date(val)).format('YYYY-MM-DD HH:mm:ss');
                        }
                    },{
                        field: 'actEndTime',
                        title: '截止时间',
                        width: 100,
                        sortable: true,
                        formatter: function (val) {
                            return moment(new Date(val)).format('YYYY-MM-DD HH:mm:ss');
                        }
                    }]],
                onDblClickRow: function (rowIndex, rowData) {
                    return LockassetMVC.Controller.edit();
                },
                onLoadSuccess: function () { //加载完成后,设置选中第一项
                    $('.pagination-page-list').hide();

                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });
            $('#lockasset-dlg').dialog({
                closed: true,
                modal: false,
                width: 1200,
                height: 600,
                iconCls: 'icon-add',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-no',
                    handler: function () {
                        $("#lockasset-dlg").dialog('close');
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-save',
                    handler: LockassetMVC.Controller.save
                }]
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', LockassetMVC.Controller.find);
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#lockasset-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return LockassetMVC.Controller.edit();
            }
        },
        find: function () {
            var actIdentify = $('#search-actIdentify').textbox('getValue');
            var actName = $('#search-actName').textbox('getValue');

            var url = LockassetMVC.URLs.Search.url + '?actIdentify=' + actIdentify + '&actName=' + actName;
            $("#lockasset-datagrid").datagrid({
                pageNumber: 1,
                pageSize: 50,
                url: url
            });
        },
        add: function () {
            var options = LockassetMVC.Util.getOptions();
            options.title = '新增活动';
            $("#modal-action").val("add");
            EasyUIUtils.openAddDlg(options);
            EasyUIUtils.clearDatagrid('#lockasset-options-datagrid');
            $("#actIdentify").textbox({readonly: false});
            $("#type").textbox({readonly: false});
        },
        copy:function(){
            var row = $('#lockasset-datagrid').datagrid('getSelected');
            row.endTime =  moment(new Date(row.actEndTime)).format('YYYY-MM-DD HH:mm:ss');
            row.startTime =  moment(new Date(row.actStartTime)).format('YYYY-MM-DD HH:mm:ss');
            $("#actIdentify").textbox({readonly: true});
            $("#type").textbox({readonly: true});
            if (row) {
                var options = LockassetMVC.Util.getOptions();
                options.iconCls = 'icon-redo';
                options.data = row;
                options.title = '复制[' + options.data.actIdentify + ']';
                options.data.id= "";
                options.data.locale="";
                options.data.actName="";
                options.data.bannerUrl="";
                options.data.rules="";
                EasyUIUtils.openEditDlg(options);
                $("#modal-action").val("copy");
                $('#bannerUrl-file').filebox('setText', row.bannerUrl);
                if(row.reward != null){
                    var reward2 =  $.toJSON(row.reward);
                    for(var i=0;i<reward2.length;i++){
                        reward2[i].periodName="";
                        reward2[i].rewardName="";
                    }
                    $('#lockasset-options-datagrid').datagrid('loadData', reward2);
                }
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        save: function () {
            var va = LockassetMVC.Util.validateForm();
            if(va !=null){
                $.messager.alert('提示', va, 'info');
                return ;
            }
            var rows = $('#lockasset-options-datagrid').datagrid('getRows');
            $('#reward').val(JSON.stringify(rows));
            var action = $('#modal-action').val();
            var options = {
                gridId: null,
                gridUrl: LockassetMVC.URLs.Search.url,
                dlgId: "#lockasset-dlg",
                formId: "#lockasset-form",
                url: null,
                callback: function () {
                }
            };
            options.url = (action === "edit" ? LockassetMVC.URLs.Edit.url : LockassetMVC.URLs.Save.url);
            options.gridId = '#lockasset-datagrid';
            return EasyUIUtils.save(options);
        },
        edit: function () {
            var row = $('#lockasset-datagrid').datagrid('getSelected');
            row.endTime =  moment(new Date(row.actEndTime)).format('YYYY-MM-DD HH:mm:ss');
            row.startTime =  moment(new Date(row.actStartTime)).format('YYYY-MM-DD HH:mm:ss');
            $("#actIdentify").textbox({readonly: true});
            $("#type").textbox({readonly: true});
            $("#modal-action").val("edit");
            if (row) {
                var options = LockassetMVC.Util.getOptions();
                options.iconCls = 'icon-edit';
                options.data = row;
                options.title = '修改[' + options.data.actIdentify + ']';
                EasyUIUtils.openEditDlg(options);
                $('#bannerUrl-file').filebox('setText', row.bannerUrl);
                if(row.reward != null){
                    $('#lockasset-options-datagrid').datagrid('loadData', $.toJSON(row.reward));
                }else{
                    var re= [];
                    $('#lockasset-options-datagrid').datagrid('loadData', re);
                }
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        remove: function () {
            var row = $('#lockasset-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: LockassetMVC.URLs.Remove.url,
                    data: {
                        id: row.id,
                        actIdentify: row.actIdentify
                    },
                    gridId: '#lockasset-datagrid',
                    gridUrl: LockassetMVC.URLs.Search.url,
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
        validateForm:function(){
            var amoutTotal = $('#amoutTotal').val();
            var amoutUsed = $('#amoutUsed').val();
            var arrTotal = amoutTotal.split('.');
            var arrUsed = amoutUsed.split('.');
            if(arrTotal[0].length>12){
                return "锁仓总额度不能大于12位";
            }
            if(arrTotal.length==2){
                if(arrTotal[1].length>8){
                    return "锁仓总额度小数位不能大于8位";
                }
            }
            if(arrUsed[0].length>12){
                return "已购数量不能大于12位";
            }
            if(arrUsed.length==2){
                if(arrUsed[1].length>8){
                    return "已购数量小数位不能大于8位";
                }
            }
            return null;
        },
        uploadImg: function () {
            var files = $("#bannerUrl-file").filebox('files');

            var formData = new FormData();
            formData.append("file", files[0]);

            //调用apicontroller后台action方法，将form数据传递给后台处理。contentType必须设置为false,否则chrome和firefox不兼容
            $.ajax({
                url: LockassetMVC.URLs.img.url,
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
                        $.messager.alert('提示', data.msg, 'info');
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
                dlgId: '#lockasset-dlg',
                formId: '#lockasset-form',
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
