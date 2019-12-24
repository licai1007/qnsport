<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/console/head.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>qingniao-list</title>
</head>
<body>
	<div class="box-positon">
		<div class="rpos">当前位置: 商品管理 - 查看</div>
		<div class="clear"></div>
	</div>
	<div class="body-box">
		<form id="jvForm" action="v_list.do" method="post">
			<table cellspacing="1" cellpadding="2" border="0" width="100%" class="pn-ftable">
				<tbody>
					<tr>
						<td width="12%" class="pn-flabel pn-flabel-h">商品编号:</td>
						<td width="38%" colspan="1" class="pn-fcontent">${product.id}</td>
						<td width="12%" class="pn-flabel pn-flabel-h">商品名称:</td>
						<td width="38%" colspan="1" class="pn-fcontent">${product.name}</td>
					</tr>
					<tr>
						<td width="12%" class="pn-flabel pn-flabel-h">商品类型:</td>
						<td width="38%" colspan="1" class="pn-fcontent">${type.name}</td>
						<td width="12%" class="pn-flabel pn-flabel-h">所属品牌:</td>
						<td width="38%" colspan="1" class="pn-fcontent">${brand.name}</td>
					</tr>
					<tr>
						<td width="12%" class="pn-flabel pn-flabel-h">是否新品:</td>
						<td width="38%" colspan="1" class="pn-fcontent">
							<c:if test="${product.isNew==true}">新品</c:if>
							<c:if test="${product.isNew==false}">旧品</c:if>
						</td>
						<td width="12%" class="pn-flabel pn-flabel-h">是否热销:</td>
						<td width="38%" colspan="1" class="pn-fcontent">
							<c:if test="${product.isHot==true}">是</c:if>
							<c:if test="${product.isHot==false}">否</c:if>
						</td>
					</tr>
					<tr>
						<td width="12%" class="pn-flabel pn-flabel-h">推荐:</td>
						<td width="38%" colspan="1" class="pn-fcontent">
							<c:if test="${product.isCommend==true}">是</c:if>
							<c:if test="${product.isCommend==false}">否</c:if>
						</td>
						<td width="12%" class="pn-flabel pn-flabel-h">上下架:</td>
						<td width="38%" colspan="1" class="pn-fcontent">
							<c:if test="${product.isShow==true}">是</c:if>
							<c:if test="${product.isShow==false}">否</c:if>
						</td>
					</tr>
					<tr>
						<td width="12%" class="pn-flabel pn-flabel-h">重量:</td>
						<td width="38%" colspan="1" class="pn-fcontent">${product.weight}克</td>
						<td width="12%" class="pn-flabel pn-flabel-h">属性:</td>
						<td width="38%" colspan="1" class="pn-fcontent">
							<c:forEach items="${features}" var="feature" varStatus="vs">
								${feature}
								<c:if test="${vs.last==false}">
									，
								</c:if>
							</c:forEach>
						</td>
					</tr>
					<tr>
						<td width="12%" class="pn-flabel pn-flabel-h">颜色:</td>
						<td width="38%" colspan="1" class="pn-fcontent">
							<c:forEach items="${colors}" var="color" varStatus="vs">
								${color.name}
								<c:if test="${vs.last==false}">
									，
								</c:if>
							</c:forEach>
						</td>
						<td width="12%" class="pn-flabel pn-flabel-h">尺寸:</td>
						<td width="38%" colspan="1" class="pn-fcontent">${product.sizes}</td>
					</tr>
					<tr>
						<td width="12%" class="pn-flabel pn-flabel-h">销量:</td>
						<td width="38%" colspan="1" class="pn-fcontent">${sum}</td>
						<td width="12%" class="pn-flabel pn-flabel-h">创建时间:</td>
						<td width="38%" colspan="1" class="pn-fcontent">
							<fmt:formatDate value="${product.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
						</td>
					</tr>
				</tbody>
			</table>
			
		</form>
	</div>

</body>
</html>