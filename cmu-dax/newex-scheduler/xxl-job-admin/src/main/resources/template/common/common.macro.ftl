<#macro commonStyle>

	<#-- favicon -->
	<link rel="icon" href="favicon.ico" />

	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.5 -->
    <link rel="stylesheet" href="${request.contextPath}/static/adminlte/bootstrap/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css"> -->
    <link rel="stylesheet" href="${request.contextPath}/static/plugins/font-awesome-4.5.0/css/font-awesome.min.css">
    <!-- Ionicons -->
    <!-- <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css"> -->
    <link rel="stylesheet" href="${request.contextPath}/static/plugins/ionicons-2.0.1/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="${request.contextPath}/static/adminlte/dist/css/AdminLTE-local.min.css">
    <!-- AdminLTE Skins. Choose a skin from the css/skins folder instead of downloading all of them to reduce the load. -->
    <link rel="stylesheet" href="${request.contextPath}/static/adminlte/dist/css/skins/_all-skins.min.css">
      
	<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

	<!-- pace -->
	<link rel="stylesheet" href="${request.contextPath}/static/plugins/pace/themes/pace-theme-flash.css">
</#macro>

<#macro commonScript>
	<!-- jQuery 2.1.4 -->
	<script src="${request.contextPath}/static/adminlte/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<!-- Bootstrap 3.3.5 -->
	<script src="${request.contextPath}/static/adminlte/bootstrap/js/bootstrap.min.js"></script>
	<!-- FastClick -->
	<script src="${request.contextPath}/static/adminlte/plugins/fastclick/fastclick.min.js"></script>
	<!-- AdminLTE App -->
	<script src="${request.contextPath}/static/adminlte/dist/js/app.min.js"></script>
	<#-- jquery.slimscroll -->
	<script src="${request.contextPath}/static/adminlte/plugins/slimScroll/jquery.slimscroll.min.js"></script>

    <!-- pace -->
    <script src="${request.contextPath}/static/plugins/pace/pace.min.js"></script>
    <#-- jquery cookie -->
	<script src="${request.contextPath}/static/plugins/jquery/jquery.cookie.js"></script>

	<#-- layer -->
	<script src="${request.contextPath}/static/plugins/layer/layer.js"></script>

	<#-- common -->
    <script src="${request.contextPath}/static/js/common.1.js"></script>
    <script>var base_url = '${request.contextPath}';</script>

</#macro>

<#macro commonHeader>
	<header class="main-header">
		<a href="${request.contextPath}/" class="logo">
			<span class="logo-mini"><b>XXL</b></span>
			<span class="logo-lg"><b>任务调度中心</b></span>
		</a>
		<nav class="navbar navbar-static-top" role="navigation">
			<a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button"><span class="sr-only">切换导航</span></a>
          	<div class="navbar-custom-menu">
				<ul class="nav navbar-nav">
					<li class="dropdown user user-menu">
                        <a href=";" id="logoutBtn" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
							${Session["xxlJobUser"].getUserName()}&nbsp;&nbsp;<span class="hidden-xs">注销</span>
                        </a>
					</li>
				</ul>
			</div>
		</nav>
	</header>
</#macro>

<#macro commonLeft pageName >
	<!-- Left side column. contains the logo and sidebar -->
	<aside class="main-sidebar">
		<!-- sidebar: style can be found in sidebar.less -->
		<section class="sidebar">
			<!-- sidebar menu: : style can be found in sidebar.less -->
			<ul class="sidebar-menu">
				<li class="header">常用模块</li>
				<li class="nav-click <#if pageName == "jobinfo">active</#if>" ><a href="${request.contextPath}/jobinfo"><i class="fa fa-circle-o text-aqua"></i><span>任务管理</span></a></li>
				<li class="nav-click <#if pageName == "joblog">active</#if>" ><a href="${request.contextPath}/joblog"><i class="fa fa-circle-o text-yellow"></i><span>调度日志</span></a></li>
                <li class="nav-click <#if pageName == "jobgroup">active</#if>" ><a href="${request.contextPath}/jobgroup"><i class="fa fa-circle-o text-green"></i><span>执行器管理</span></a></li>
				<#if Session["xxlJobUser"].getUserName()?exists && Session["xxlJobUser"].getUserName() == "admin">
					<li class="nav-click <#if pageName == "jobrelation">active</#if>" ><a href="${request.contextPath}/jobrelation"><i class="fa fa-circle-o text-gray"></i><span>权限管理</span></a></li>
					<li class="nav-click <#if pageName == "jobusers">active</#if>" ><a href="${request.contextPath}/jobusers"><i class="fa fa-circle-o text-gray"></i><span>用户管理</span></a></li>
				</#if>
				<#--<li class="nav-click <#if pageName == "help">active</#if>" ><a href="${request.contextPath}/help"><i class="fa fa-circle-o text-gray"></i><span>使用教程</span></a></li>-->
			</ul>
		</section>
		<!-- /.sidebar -->
	</aside>
</#macro>

<#macro commonControl >
	<!-- Control Sidebar -->
	<aside class="control-sidebar control-sidebar-dark">
		<!-- Create the tabs -->
		<ul class="nav nav-tabs nav-justified control-sidebar-tabs">
			<li class="active"><a href="#control-sidebar-home-tab" data-toggle="tab"><i class="fa fa-home"></i></a></li>
			<li><a href="#control-sidebar-settings-tab" data-toggle="tab"><i class="fa fa-gears"></i></a></li>
		</ul>
		<!-- Tab panes -->
		<div class="tab-content">
			<!-- Home tab content -->
			<div class="tab-pane active" id="control-sidebar-home-tab">
				<h3 class="control-sidebar-heading">近期活动</h3>
				<ul class="control-sidebar-menu">
					<li>
						<a href="javascript::;">
							<i class="menu-icon fa fa-birthday-cake bg-red"></i>
							<div class="menu-info">
								<h4 class="control-sidebar-subheading"></h4>
								<p>2015-09-10</p>
							</div>
						</a>
					</li>
				</ul>
				<!-- /.control-sidebar-menu -->
			</div>
			<!-- /.tab-pane -->

			<!-- Settings tab content -->
			<div class="tab-pane" id="control-sidebar-settings-tab">
				<form method="post">
					<h3 class="control-sidebar-heading">个人设置</h3>
					<div class="form-group">
						<label class="control-sidebar-subheading"> 左侧菜单自适应
							<input type="checkbox" class="pull-right" checked>
						</label>
						<p>左侧菜单栏样式自适应</p>
					</div>
					<!-- /.form-group -->

				</form>
			</div>
			<!-- /.tab-pane -->
		</div>
	</aside>
	<!-- /.control-sidebar -->
	<!-- Add the sidebar's background. This div must be placed immediately after the control sidebar -->
	<div class="control-sidebar-bg"></div>
</#macro>

<#macro commonFooter >
	<footer class="main-footer">
        Powered by <b>XXL-JOB</b> 1.9.0
		<div class="pull-right hidden-xs">
            <strong>Copyright &copy;  &nbsp;
                <a href="https://www.coinmex.vip" target="_blank" >coinmex</a>
				&nbsp;${.now?string('yyyy-MM-dd')}
                <!--<a href="https://github.com/xuxueli/xxl-job" target="_blank" >github</a>-->
            </strong>
		</div>
	</footer>
</#macro>