<!DOCTYPE html>
<html>
<head>
    <title>任务调度中心</title>
<#import "../common/common.macro.ftl" as netCommon>
<@netCommon.commonStyle />
    <!-- DataTables -->
    <link rel="stylesheet" href="${request.contextPath}/static/adminlte/plugins/datatables/dataTables.bootstrap.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
    <!-- header -->
<@netCommon.commonHeader />
    <!-- left -->
<@netCommon.commonLeft "jobrelation" />

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>权限管理
                <small>权限管理</small>
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">

            <div class="row">
                <div class="col-xs-12">
                    <div class="box">
                        <div class="box-header">
                            <h3 class="box-title">权限列表</h3>&nbsp;&nbsp;
                            <#if Session["xxlJobUser"].getUserName()?exists && Session["xxlJobUser"].getUserName() == "admin">
                                <button class="btn btn-info btn-xs pull-left2 add">+新增权限</button>
                            </#if>
                        </div>
                        <div class="box-body">
                            <table id="joblog_list" class="table table-bordered table-striped display" width="100%">
                                <thead>
                                <tr>
                                    <th name="id">ID</th>
                                    <th name="userName">用户名</th>
                                    <th name="title">执行说明</th>
                                    <th name="addressList">执行IP</th>
                                <#--<th name="addTime">新增时间</th>-->
                                    <th name="operate">操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#if jobRelation?exists && jobRelation?size gt 0>
                                    <#list jobRelation as relation>
                                    <tr>
                                        <td>${relation.id}</td>
                                        <td>${relation.userName}</td>
                                        <td>${relation.title}</td>
                                        <td>${relation.addressList}</td>
                                    <#--<td>${relation.addTime?datetime("yyyy-MM-dd HH:mm:ss")}</td>-->
                                        <td>
                                            <button class="btn btn-warning btn-xs update"
                                                    id="${relation.id}"
                                                    userName="${relation.userName}"
                                            >编辑
                                            </button>
                                            <button class="btn btn-danger btn-xs remove" id="${relation.id}">删除</button>
                                        </td>
                                    </tr>
                                    </#list>
                                </#if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    </div>

    <!-- 新增.模态框 -->
    <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog ">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title">新增权限</h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal form" role="form">
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label"> 用户名<font color="red">*</font></label>
                            <div class="col-sm-10">
                                <select class="form-control glueType" name="userId">
                                    <#list jobUser  as user>
                                        <option value="${user.id}">${user.userName}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="lastname" class="col-sm-2 control-label"> 执行器<font color="red">*</font></label>
                            <div class="col-sm-10">
                                <select class="form-control glueType" name="relationId">
                                    <#list jobGroup  as group>
                                        <option value="${group.id}">${group.title}</option>
                                    </#list>
                                </select>
                            </div>
                        </div>
                        <hr>
                        <div class="form-group">
                            <div class="col-sm-offset-3 col-sm-6">
                                <button type="submit" class="btn btn-primary">保存</button>
                                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>


    <!-- footer -->
<@netCommon.commonFooter />
</div>

<@netCommon.commonScript />
<!-- DataTables -->
<script src="${request.contextPath}/static/adminlte/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="${request.contextPath}/static/adminlte/plugins/datatables/dataTables.bootstrap.min.js"></script>
<#-- jquery.validate -->
<script src="${request.contextPath}/static/plugins/jquery/jquery.validate.min.js"></script>
<script src="${request.contextPath}/static/js/jobrelation.index.1.js"></script>
</body>
</html>
