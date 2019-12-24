<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>product-left</title>
<link href="/jsutils/qingniao/css/admin.css" rel="stylesheet" type="text/css"/>
<link href="/jsutils/common/css/theme.css" rel="stylesheet" type="text/css"/>
<link href="/jsutils/common/css/jquery.validate.css" rel="stylesheet" type="text/css"/>
<link href="/jsutils/common/css/jquery.treeview.css" rel="stylesheet" type="text/css"/>
<link href="/jsutils/common/css/jquery.ui.css" rel="stylesheet" type="text/css"/>

<!-- <script src="/thirdparty/ckeditor/ckeditor.js" type="text/javascript"></script> -->
<!-- <script src="/thirdparty/My97DatePicker/WdatePicker.js" type="text/javascript"></script> -->
<script type="text/javascript" src="/jsutils/fckeditor/fckeditor.js"></script>
<script src="/jsutils/common/js/jquery.js" type="text/javascript"></script>
<script src="/jsutils/common/js/jquery.ext.js" type="text/javascript"></script>
<script src="/jsutils/common/js/jquery.form.js" type="text/javascript"></script>
<script src="/jsutils/common/js/qingniao.js" type="text/javascript"></script>
<script src="/jsutils/qingniao/js/admin.js" type="text/javascript"></script>
</head>
<body class="lbody">
<div class="left">
<%@ include file="/WEB-INF/console/date.jsp" %>
     <ul class="w-lful">
		<li><a target="rightFrame" href="/order/list.do?isPaiy=0">货到付款</a></li>
		<li><a target="rightFrame" href="/order/list.do?isPaiy=1">待付款</a></li>
		<li><a target="rightFrame" href="/order/list.do?">已取消</a></li>
		<li><a target="rightFrame" href="/order/list.do?isPaiy=2">已付款</a></li>
		<li><a target="rightFrame" href="/order/list.do?orderState=1">仓库配货</a></li>
		<li><a target="rightFrame" href="/order/list.do?orderState=2">商品出库</a></li>
		<li><a target="rightFrame" href="/order/list.do?orderState=3">等待收货</a></li>
		<li><a target="rightFrame" href="/order/list.do?orderState=4">已完成</a></li>
		<li><a target="rightFrame" href="/order/list.do?orderState=5">待退货</a></li>
		<li><a target="rightFrame" href="/order/list.do?isPaiy=3">待退款</a></li>
		<li><a target="rightFrame" href="/order/list.do?orderState=6">已退货</a></li>
		<li><a target="rightFrame" href="/order/list.do?isPaiy=4">已退款</a></li>
     </ul>
</div>
</body>
</html>