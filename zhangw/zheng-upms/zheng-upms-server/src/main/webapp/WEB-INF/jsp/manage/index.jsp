<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>Log System</title>
	<link href="${basePath}/resources/favicon.ico" rel="Shortcut Icon" />
	<link href="${basePath}/resources/zheng-admin/plugins/bootstrap-3.3.0/css/bootstrap.min.css" rel="stylesheet"/>
	<link href="${basePath}/resources/zheng-admin/plugins/material-design-iconic-font-2.2.0/css/material-design-iconic-font.min.css" rel="stylesheet"/>
	<link href="${basePath}/resources/zheng-admin/plugins/waves-0.7.5/waves.min.css" rel="stylesheet"/>
	<link href="${basePath}/resources/zheng-admin/plugins/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.min.css" rel="stylesheet"/>
	<link href="${basePath}/resources/zheng-admin/css/admin.css" rel="stylesheet"/>
	<link href="${basePath}/resources/zheng-admin/plugins/jquery-confirm/jquery-confirm.min.css" rel="stylesheet"/>
	<style>
		/** skins **/
		<c:forEach var="upmsSystem" items="${upmsSystems}">
		#${upmsSystem.name} #header {background: ${upmsSystem.theme};}
		#${upmsSystem.name} .content_tab{background: ${upmsSystem.theme};}
		#${upmsSystem.name} .s-profile>a{background: url(${basePath}${upmsSystem.banner}) left top no-repeat;}
		</c:forEach>
	</style>
</head>
<body>

<section id="main">
	<!-- 左侧导航区 -->
	<aside id="sidebar">
		<!-- 个人资料区 -->
		<div class="s-profile">
			<a class="waves-effect waves-light" href="javascript:;">
				<div class="sp-pic">
					<img src="${basePath}${upmsUser.avatar}"/>
				</div>
				<div class="sp-info">
					Hi, ${upmsUser.realname}！
                    <i class="zmdi zmdi-caret-down"></i>
				</div>
			</a>
            <ul class="main-menu">
                <li>
                    <a class="waves-effect" href="javascript:;" onclick="changePwd()"><i class="zmdi zmdi-account"></i>Change Password</a>
                </li>
            </ul>
		</div>
		<!-- /个人资料区 -->
		<!-- 菜单区 -->
		<ul class="main-menu">
			<li>
				<a class="waves-effect" href="javascript:Tab.addTab('home', 'home');"><i class="zmdi zmdi-home"></i> Home</a>
			</li>
			<c:forEach var="upmsPermission" items="${upmsPermissions}" varStatus="status">
				<c:if test="${upmsPermission.pid == 0}">
				<li class="sub-menu system_menus system_${upmsPermission.systemId} ${status.index}" <c:if test="${upmsPermission.systemId != 1}">style="display:none;"</c:if>>
					<a class="waves-effect" href="javascript:;"><i class="${upmsPermission.icon}"></i> ${upmsPermission.name}</a>
					<ul>
						<c:forEach var="subUpmsPermission" items="${upmsPermissions}">
							<c:if test="${subUpmsPermission.pid == upmsPermission.permissionId}">
								<c:forEach var="upmsSystem" items="${upmsSystems}">
									<c:if test="${subUpmsPermission.systemId == upmsSystem.systemId}">
									<c:set var="systemBasePath" value="${upmsSystem.basepath}"/></c:if>
								</c:forEach>
								<li><a class="waves-effect" href="javascript:Tab.addTab('${subUpmsPermission.name}', '${systemBasePath}${subUpmsPermission.uri}');">${subUpmsPermission.name}</a></li>
							</c:if>
						</c:forEach>
					</ul>
				</li>
				</c:if>
			</c:forEach>
			<li>
				<a class="waves-effect" href="${basePath}/sso/logout"><i class="zmdi zmdi-run"></i> Logout</a>
			</li>

			<li>
				<div class="upms-version">&copy; Author By JS-Link</div>
				<div class="upms-version"> Support : +61 426 282 952</div>
			</li>
		</ul>
		<!-- /菜单区 -->
	</aside>
	<!-- /右侧导航区 -->
	<section id="content">
		<div id="content_tab" class="content_tab">
			<div class="tab_left">
				<a class="waves-effect waves-light" href="javascript:;"><i class="zmdi zmdi-chevron-left"></i></a>
			</div>
			<div class="tab_right">
				<a class="waves-effect waves-light" href="javascript:;"><i class="zmdi zmdi-chevron-right"></i></a>
			</div>
			<ul id="tabs" class="tabs">
				<li id="tab_home" data-index="home" data-closeable="false" class="cur">
					<%--<a class="waves-effect waves-light" href="javascript:;">首页</a>--%>
				</li>
			</ul>
		</div>
		<div class="content_main" id="content_main">
			<div id="iframe_home" class="iframe cur">
				<p><h4>Schedule System</h4></p>
			</div>
		</div>
	</section>
</section>
<footer id="footer"></footer>

<script src="${basePath}/resources/zheng-admin/plugins/jquery.1.12.4.min.js"></script>
<script src="${basePath}/resources/zheng-admin/plugins/bootstrap-3.3.0/js/bootstrap.min.js"></script>
<script src="${basePath}/resources/zheng-admin/plugins/waves-0.7.5/waves.min.js"></script>
<script src="${basePath}/resources/zheng-admin/plugins/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="${basePath}/resources/zheng-admin/plugins/BootstrapMenu.min.js"></script>
<script src="${basePath}/resources/zheng-admin/plugins/device.min.js"></script>
<script src="${basePath}/resources/zheng-admin/plugins/jquery.cookie.js"></script>
<script src="${basePath}/resources/zheng-admin/js/admin.js"></script>
<script src="${basePath}/resources/zheng-admin/plugins/fullPage/jquery.fullPage.min.js"></script>
<script src="${basePath}/resources/zheng-admin/plugins/fullPage/jquery.jdirk.min.js"></script>
<script src="${basePath}/resources/zheng-admin/plugins/jquery-confirm/jquery-confirm.min.js"></script>
<script>
    var BASE_PATH = '${basePath}';
    $(function(){
        //初始化展开菜单
		$('.sub-menu a').click();
	})
    // 新增
    var changePwdDialog;
    function changePwd() {
        changePwdDialog = $.dialog({
            animationSpeed: 300,
            title: 'Change My Password',
            content: 'url:${basePath}/manage/user/changePwd',
            onContentReady: function () {
            }
        });
    }
</script>
</body>
</html>