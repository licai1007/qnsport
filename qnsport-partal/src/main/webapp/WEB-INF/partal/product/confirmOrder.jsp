<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML>
<html>
<head>
<title>成功提交订单</title>
<link rel="stylesheet" href="/jsutils/css/style.css" />
<script src="/jsutils/js/jquery.js"></script>
<script src="/jsutils/js/com.js"></script>
</head>
<body>
<div class="bar"><div class="bar_w">
	<p class="l">
		<span class="l">
			收藏本网站！北京<a href="#" title="更换">[更换]</a>
		</span>
	</p>
	<ul class="r uls">
		<li class="dev">
			您好,欢迎来到青鸟运动购物平台！
		</li>
	<li class="dev"><a href="javascript:void(0)" onclick="login()"  title="登陆">[登陆]</a></li>
	<li class="dev"><a href="javascript:void(0)" onclick="register()" title="免费注册">[免费注册]</a></li>
	<li class="dev"><a href="javascript:void(0)" onclick="logout()" title="退出">[退出]</a></li>
	<li class="dev"><a href="javascript:void(0)" onclick="myOrder()" title="我的订单">我的订单</a></li>
	<li class="dev"><a href="#" title="在线客服">在线客服</a></li>
	<li class="dev after"><a href="#" title="English">English</a></li>
	</ul>
</div></div>

<ul class="ul step st3_3">
<li title="1.我的购物车">1.我的购物车</li>
<li title="2.填写核对订单信息">2.填写核对订单信息</li>
<li title="3.成功提交订单" class="here">3.成功提交订单</li>
</ul>

<div class="w ofc case">

	<div class="confirm">
		<div class="tl"></div><div class="tr"></div>
		<div class="ofc">
			
			<p class="okMsg">您的订单已成功提交，完成支付后，我们将立即发货！</p>

			<table cellspacing="0" class="tab tab5" summary="">
			<tbody><tr>
			<th>您的订单号</th>
			<td><var class="blue"><b>${order.id}</b></var></td>
			<th>应付现金</th>
			<td><var class="red"><b>￥${order.totalFee}</b></var>&nbsp;元</td>
			<th>支付方式</th>
			<td>${order.paymentWayName}</td>
			</tr>
			<tr>
			<th>配送方式</th>
			<td>快递</td>
			<th>预计到货时间</th>
			<td>${order.computeTime}</td>
			<th></th>
			<td></td>
			</tr>
			</table>
		</div>
	</div>
	<div style="text-align:center;">
		<form action="${pageContext.request.contextPath}/aliPay.html" method="post">
			<input type="hidden" name="WIDout_trade_no" value="${order.id}"/>
			<input type="hidden" name="WIDtotal_amount" value="${order.totalFee}"/>
			<input type="hidden" name="WIDsubject" value="${order.id}"/>
			<input type="submit" style="font-size:30px;" value="支付"/>
		</form>
	</div>
	
</div>
</body>
</html>