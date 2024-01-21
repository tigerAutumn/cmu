$(function () {
    CountryDs.init();
});

var CountryDs = {
    init: function () {
        CountryMVC.View.initControl();
        CountryMVC.View.bindEvent();
        CountryMVC.View.bindValidate();
        CountryMVC.View.initData();
    }
};

var DsCommon = {
    baseUrl: BossGlobal.ctxPath + '/v1/boss/spot/country/',
    baseIconUrl: BossGlobal.ctxPath + '/custom/easyui/themes/icons/',
    basecomboxUrl: BossGlobal.ctxPath + '/v1/boss/workMenu/',
    keys: {
        dbType: 'dbType',
        dbPoolType: 'dbPoolType'
    }
};

var CountryMVC = {
    URLs: {
        add: {
            url: DsCommon.baseUrl + 'add',
            method: 'POST'
        },
        edit: {
            url: DsCommon.baseUrl + 'edit',
            method: 'POST'
        },
        list: {
            url: DsCommon.baseUrl + 'list',
            method: 'get'
        },
        remove: {
            url: DsCommon.baseUrl + 'remove',
            method: 'POST'
        },
        getMenuTree: {
            url: DsCommon.basecomboxUrl + 'getWorkMenuTree',
            method: 'get'
        }
    },
    Model: {
        dbTypes: {},
        dbPoolTypes: {}
    },
    View: {
        initControl: function () {

            $.get(CountryMVC.URLs.getMenuTree.url, "", function (_data) {
                $("#menuId").combotree({
                    data: _data.data,
                    valueField: "id",
                    required: true,
                    textField: "text",
                    panelHeight: 300,
                    editable: true
                });
            });

            $('#ds-datagrid').datagrid({
                method: 'get',
                fit: true,
                fitColumns: true,
                singleSelect: true,
                pagination: true,
                rownumbers: true,
                pageSize: 50,
                url: CountryMVC.URLs.list.url,
                toolbar: [{
                    iconCls: 'icon-add',
                    handler: function () {
                        CountryMVC.Controller.add();
                    }
                }, '-', {
                    iconCls: 'icon-edit1',
                    handler: function () {
                        CountryMVC.Controller.edit();
                    }
                }, '-', {
                    iconCls: 'icon-remove1',
                    handler: function () {
                        CountryMVC.Controller.remove();
                    }
                }, '-', {
                    iconCls: 'icon-reload',
                    handler: function () {
                        $("#ds-datagrid").datagrid({
                            pageNumber: 1,
                            pageSize: 50,
                            url: CountryMVC.URLs.list.url
                        });
                        //EasyUIUtils.reloadDatagrid('#ds-datagrid');
                    }
                }],
                loadFilter: function (src) {
                    if (!src.code) {
                        return src.data;
                    }
                    return $.messager.alert('失败', src.msg, 'error');
                },
                columns: [[{

                    field: 'locale',
                    title: '语言',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value == 'zh-cn') {
                            return "中文";
                        } else if (value == 'en-us') {
                            return "英文";
                        }
                    }
                }, {
                    field: 'abbr',
                    title: '缩写',
                    width: 150,
                    sortable: true
                }, {
                    field: 'name',
                    title: '国家名称',
                    width: 150,
                    sortable: true
                }, {
                    field: 'capital',
                    title: '首都',
                    width: 150,
                    sortable: true
                }, {
                    field: 'areaCode',
                    title: '地区代码',
                    width: 100,
                    sortable: true
                }, {
                    field: 'countryCode',
                    title: '国家编码',
                    width: 100,
                    sortable: true
                }, {
                    field: 'currencyName',
                    title: '货币名称',
                    width: 100,
                    sortable: true
                }, {
                    field: 'currencyCode',
                    title: '货币代码',
                    width: 100,
                    sortable: true
                }, {
                    field: 'domainCode',
                    title: '国家顶级域名',
                    width: 100,
                    sortable: true
                }, {
                    field: 'letterCode2',
                    title: '字符代码2',
                    width: 100,
                    sortable: true
                }, {
                    field: 'letterCode3',
                    title: '字符代码3',
                    width: 100,
                    sortable: true
                }, {
                    field: 'status',
                    title: '状态',
                    width: 100,
                    sortable: true,
                    formatter: function (value, row, index) {
                        if (value == '1') {
                            return "可注册";
                        } else if (value == '0') {
                            return "不可注册";
                        }
                    }
                }, {
                    field: 'options',
                    title: '操作',
                    width: 100,
                    formatter: function (value, row, index) {
                        var icons = [{
                            "name": "edit",
                            "title": "编辑"
                        }, {
                            "name": "remove",
                            "title": "删除"
                        }];
                        var buttons = [];
                        for (var i = 0; i < icons.length; i++) {
                            var tmpl = '<a href="#" title ="${title}" '
                                + 'onclick="CountryMVC.Controller.doOption(\'${index}\',\'${name}\')">'
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
                    return CountryMVC.Controller.edit();
                },
                onLoadError: function (data) {
                    return $.messager.alert('失败', data.responseJSON.msg, 'error');
                }
            });

            var pager = $("#ds-datagrid").datagrid("getPager");
            if (pager) {
                $(pager).pagination({
                    onSelectPage: function (pageNumber, pageSize) {

                        var url = CountryMVC.Util.getSearchUrl();
                        $("#ds-datagrid").datagrid({
                            pageNumber: pageNumber,
                            pageSize: pageSize,
                            url: url
                        });

                    }
                });
            }

            $('#ds-dlg-detail').dialog({
                closed: true,
                modal: true,
                striped: true,
                collapsible: true,
                resizable: true,
                width: 910,
                height: window.screen.height - 350,
                maximizable: true,
                iconCls: 'icon-info',
                buttons: [{
                    text: '关闭',
                    iconCls: 'icon-back',
                    handler: function () {
                        $("#ds-dlg-detail").dialog('close');
                    }
                }]
            });

            // dialogs
            $('#ds-dlg').dialog({
                closed: true,
                modal: false,
                width: 750,
                height: 500,
                iconCls: 'icon-add',
                buttons: [
                    {
                        text: '关闭',
                        iconCls: 'icon-no',
                        handler: function () {
                            $("#ds-dlg").dialog('close');
                        }
                    }, {
                        text: '保存',
                        iconCls: 'icon-save',
                        handler: CountryMVC.Controller.save
                    }]
            });

            $('#ds-options-pg').propertygrid({
                scrollbarSize: 0,
                height: 200,
                columns: [[
                    {field: 'name', title: '配置项', width: 200, sortable: true},
                    {field: 'value', title: '配置值', width: 100, resizable: false}
                ]]
            });
        },
        bindEvent: function () {
            $('#btn-search').bind('click', CountryMVC.Controller.find);
        },
        bindValidate: function () {
        },
        initData: function () {
            CountryMVC.Util.loadConfigItems();
        }
    },
    Controller: {
        doOption: function (index, name) {
            $('#ds-datagrid').datagrid('selectRow', index);
            if (name === "edit") {
                return CountryMVC.Controller.edit();
            }
            if (name === "remove") {
                return CountryMVC.Controller.remove();
            }
        },
        add: function () {
            var options = CountryMVC.Util.getOptions();
            options.title = '新增国家信息';
            EasyUIUtils.openAddDlg(options);
        },
        edit: function () {
            var row = $('#ds-datagrid').datagrid('getSelected');
            if (row) {
                var options = CountryMVC.Util.getOptions();
                options.iconCls = 'icon-edit1';
                options.data = row;
                options.title = '修改国家信息';
                EasyUIUtils.openEditDlg(options);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
        },
        find: function () {
            var url = CountryMVC.Util.getSearchUrl();
            $("#ds-datagrid").datagrid({
                pageNumber: 1,
                pageSize: 50,
                url: url
            });


            // var locale = $("#count_locale").combobox('getValue');
            // var name = $("#count_name").textbox('getValue');
            // var status = $("#count_status").combobox('getValue');
            // var url = CountryMVC.URLs.list.url + '?name=' + name + '&status=' + status + '&locale=' + locale;
            // EasyUIUtils.loadToDatagrid('#ds-datagrid', url)
        },
        remove: function () {
            var row = $('#ds-datagrid').datagrid('getSelected');
            if (row) {
                var options = {
                    rows: [row],
                    url: CountryMVC.URLs.remove.url,
                    data: {
                        id: row.id
                    },
                    gridId: '#ds-datagrid',
                    gridUrl: CountryMVC.URLs.list.url,
                    callback: function (rows) {
                    }
                };
                EasyUIUtils.remove(options);
            }
        },
        save: function () {
            var action = $('#modal-action').val();
            var options = {
                gridId: null,
                gridUrl: CountryMVC.URLs.list.url,
                dlgId: "#ds-dlg",
                formId: "#ds-form",
                url: null,
                callback: function () {
                }
            };
            options.url = (action === "edit" ? CountryMVC.URLs.edit.url : CountryMVC.URLs.add.url);
            options.gridId = '#ds-datagrid';
            return EasyUIUtils.save(options);
        }
    },
    Util: {

        getSearchUrl: function () {
            var locale = $("#count_locale").combobox('getValue');
            var name = $("#count_name").textbox('getValue');
            var status = $("#count_status").combobox('getValue');
            return CountryMVC.URLs.list.url + '?name=' + name + '&status=' + status + '&locale=' + locale;

        },

        isRowSelected: function (func) {
            var row = $('#ds-datagrid').datagrid('getSelected');
            if (row) {
                func(row);
            } else {
                $.messager.alert('警告', '请选中一条记录!', 'info');
            }
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
        fillCombox: function (id, act, map, fieldName, value) {
            $(id).combobox('clear');
            var data = [];
            var i = 0;
            for (var key in map) {
                var item = map[key];
                data.push({
                    "value": item.key,
                    "name": item.name,
                    "selected": i === 0
                });
                i++;
            }
            $(id).combobox('loadData', data);
            if (act === "edit") {
                var key = CountryMVC.Util.findKey(map, fieldName, value);
                $(id).combobox('setValues', key);
            }
        },
        loadConfigItems: function () {
            $.getJSON(CountryMVC.URLs.getConfItems.url + "?key=" + DsCommon.keys.dbType, function (result) {
                CountryMVC.Util.toMap(CountryMVC.Model.dbTypes, result.data);
            });
            $.getJSON(CountryMVC.URLs.getConfItems.url + "?key=" + DsCommon.keys.dbPoolType, function (result) {
                CountryMVC.Util.toMap(CountryMVC.Model.dbPoolTypes, result.data);
            });
        },
        toMap: function (srcMap, data) {
            if (!data || data.length === 0) return {};
            for (var i = 0; i < data.length; i++) {
                var item = data[i];
                item.value = $.toJSON(item.value);
                srcMap[item.key] = item;
            }
            return srcMap;
        },
        findKey: function (map, fieldName, value) {
            for (var key in map) {
                if (value === map[key].value[fieldName]) {
                    return key;
                }
            }
            return "";
        }
    }
};
