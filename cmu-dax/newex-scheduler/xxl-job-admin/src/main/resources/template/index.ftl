<!DOCTYPE html>
<html>
<head>
  	<title>任务调度中心</title>
  	<#import "./common/common.macro.ftl" as netCommon>
	<@netCommon.commonStyle />
    <!-- daterangepicker -->
    <link rel="stylesheet" href="${request.contextPath}/static/adminlte/plugins/daterangepicker/daterangepicker.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">
	<!-- header -->
	<@netCommon.commonHeader />
	<!-- left -->
	<@netCommon.commonLeft "index" />
	
	<!-- Content Wrapper. Contains page content -->
	<div class="content-wrapper">
		<!-- Content Header (Page header) -->
		<section class="content-header">
			<h1>运行报表<small>任务调度中心</small></h1>
			<!--
			<ol class="breadcrumb">
				<li><a><i class="fa fa-dashboard"></i>调度中心</a></li>
				<li class="active">使用教程</li>
			</ol>
			-->
		</section>

		<!-- Main content -->
		<section class="content">

            <!-- 任务信息 -->
            <div class="row">

                <#-- 任务信息 -->
                <div class="col-md-4 col-sm-6 col-xs-12">
                    <div class="info-box bg-aqua">
                        <span class="info-box-icon"><i class="fa fa-flag-o"></i></span>

                        <div class="info-box-content">
                            <span class="info-box-text">任务数量</span>
                            <span class="info-box-number">${jobInfoCount}</span>

                            <div class="progress">
                                <div class="progress-bar" style="width: 100%"></div>
                            </div>
                            <span class="progress-description">调度中心运行的任务数量</span>
                        </div>
                    </div>
                </div>

                <#-- 调度信息 -->
                <div class="col-md-4 col-sm-6 col-xs-12" >
                    <div class="info-box bg-yellow">
                        <span class="info-box-icon"><i class="fa fa-calendar"></i></span>

                        <div class="info-box-content">
                            <span class="info-box-text">调度次数</span>
                            <span class="info-box-number">${jobLogCount}</span>

                            <div class="progress">
                                <div class="progress-bar" style="width: 100%" ></div>
                            </div>
                            <span class="progress-description">
                                调度中心触发的调度次数
                                <#--<#if jobLogCount gt 0>
                                    调度成功率：${(jobLogSuccessCount*100/jobLogCount)?string("0.00")}<small>%</small>
                                </#if>-->
                            </span>
                        </div>
                    </div>
                </div>

                <#-- 执行器 -->
                <div class="col-md-4 col-sm-6 col-xs-12">
                    <div class="info-box bg-green">
                        <span class="info-box-icon"><i class="fa ion-ios-settings-strong"></i></span>

                        <div class="info-box-content">
                            <span class="info-box-text">执行器数量</span>
                            <span class="info-box-number">${executorCount}</span>

                            <div class="progress">
                                <div class="progress-bar" style="width: 100%"></div>
                            </div>
                            <span class="progress-description">调度中心在线的执行器机器数量</span>
                        </div>
                    </div>
                </div>

            </div>

            <#-- 调度报表：时间区间筛选，左侧折线图 + 右侧饼图 -->
            <div class="row">
                <div class="col-md-12">
                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">调度报表</h3>
                            <#--<input type="text" class="form-control" id="filterTime" readonly >-->

                            <!-- tools box -->
                            <div class="pull-right box-tools">
                                <button type="button" class="btn btn-primary btn-sm daterange pull-right" data-toggle="tooltip" id="filterTime" >
                                    <i class="fa fa-calendar"></i>
                                </button>
                                <#--<button type="button" class="btn btn-primary btn-sm pull-right" data-widget="collapse" data-toggle="tooltip" title="" style="margin-right: 5px;" data-original-title="Collapse">
                                    <i class="fa fa-minus"></i>
                                </button>-->
                            </div>
                            <!-- /. tools -->

                        </div>
                        <div class="box-body">
                            <div class="row">
                                <#-- 左侧折线图 -->
                                <div class="col-md-8">
                                    <div id="lineChart" style="height: 350px;"></div>
                                </div>
                                <#-- 右侧饼图 -->
                                <div class="col-md-4">
                                    <div id="pieChart" style="height: 350px;"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

		</section>
		<!-- /.content -->
	</div>
	<!-- /.content-wrapper -->
	
	<!-- footer -->
	<@netCommon.commonFooter />
</div>
<@netCommon.commonScript />
<!-- daterangepicker -->
<script src="${request.contextPath}/static/adminlte/plugins/daterangepicker/moment.min.js"></script>
<script src="${request.contextPath}/static/adminlte/plugins/daterangepicker/daterangepicker.js"></script>
<#-- echarts -->
<script src="${request.contextPath}/static/plugins/echarts/echarts.common.min.js"></script>
<script src="${request.contextPath}/static/js/index.js"></script>
</body>
</html>
