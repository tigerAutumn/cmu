$(function () {
    Vlink.init();
});

var Vlink = {
    init: function () {
        VlinkMVC.View.initControl();
        VlinkMVC.View.bindEvent();
    }
};

var VlinkCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/extra/vlink/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    commonUrl: BossGlobal.ctxPath + '/v1/boss/common/'

};

var VlinkMVC = {
    URLs: {
        Search: {
            url: VlinkCommon.baseUrl + 'list',
            method: 'POST'
        },
        Currency: {
            url: VlinkCommon.commonUrl + 'spot/currency',
            method: 'GET'
        }
    },
    Model: {},
    View: {
        initControl: function () {
            $('#currency').combobox({
                url: VlinkMVC.URLs.Currency.url,
                method: 'get',
                valueField: "id",
                textField: "symbol",
                editable: true,
                value: '',
                prompt: '选择币种',
                panelHeight: 300
            });

            $('#vlink-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: false,
                rownumbers: true,
                sortOrder: 'desc',
                pageSize: 50,
                pageNumber: 1,
                url: VlinkMVC.URLs.Search.url,
                toolbar: [{
                    iconCls: 'icon-reload',
                    handler: function () {
                        EasyUIUtils.reloadDatagrid('#vlink-datagrid')
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
                        width: 50,
                        sortable: true,
                    },
                    {
                        field: 'userId',
                        title: '用户Id',
                        width: 100,
                        sortable: true
                    },
                    {
                        field: 'email',
                        title: 'vlink账号',
                        width: 130,
                        sortable: true,
                    },
                    {
                        field: 'contractId',
                        title: '算力合约Id',
                        width: 100
                    },
                    {
                        field: 'currencyId',
                        title: '币种Id',
                        width: 50
                    },
                    {
                        field: 'contractType',
                        title: '合约名称',
                        width: 100
                    },
                    {
                        field: 'quantity',
                        title: '购买数量',
                        width: 100,
                        sortable: true
                    }, {
                        field: 'total',
                        title: '总价',
                        width: 100,
                        sortable: true
                    },
                    {
                        field: 'serialId',
                        title: 'vlink系统订单号',
                        width: 150,
                        sortable: true
                    },  {
                        field: 'transactionId',
                        title: '转账记录',
                        width: 200,
                        sortable: true
                    }, {
                        field: 'activateDate',
                        title: '计算收益时间',
                        width: 100,
                        sortable: true,
                        formatter:function (val) {
                            if(val!=null)
                            return moment(new Date(val)).format("YYYY-MM-DD HH:mm:ss");
                        }
                    }, {
                        field: 'createdDate',
                        title: '创建时间',
                        width: 100,
                        sortable: true,
                        formatter:function (val) {
                            return moment(new Date(val)).format("YYYY-MM-DD HH:mm:ss");
                        }
                    },
                    {
                        field: 'status',
                        title: '状态',
                        width: 100,
                        formatter: function (val) {
                            if (val === 0) {
                                return "未转账"
                            }if (val === 1) {
                                return "转账成功"
                            }
                            if (val === 2) {
                                return "转账失败"
                            } if (val === 3) {
                                return "购买成功"
                            }if (val === 4) {
                                return "购买失败，资金已退回用户"
                            }if (val === 5) {
                                return "退回资金失败，手动操作"
                            }
                        }
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
        },
        bindEvent: function () {
            $('#btn-search').bind('click', VlinkMVC.Controller.find);
        }
    },
    Controller: {
        find: function () {
            var userId = $("#userId").textbox("getValue");
            var currency = $("#currency").combobox("getValue");
            var email = $("#email").textbox("getValue");
            var url = VlinkMVC.URLs.Search.url + '?userId=' + userId+"&currencyId="+currency+"&email="+email;
            console.log(url);
            EasyUIUtils.loadToDatagrid('#vlink-datagrid', url)
        }

    },
    Util: {
        getOptions: function () {
            return {
                dlgId: '#vlink-dlg',
                formId: '#vlink-form',
                actId: '#modal-action',
                rowId: '#roleId',
                title: '',
                iconCls: 'icon-add',
                data: {},
                callback: function (arg) {
                },
                gridId: null
            };
        }
    }
};