<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/console/head.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>qnsport-edit</title>
		<script type="text/javascript">
			// 图片上传 使用 jquery的来异步提交
			function ImgUpload() {
				var opts = {
					url: "/upload/uploadImgtoDFS.do",
					type: "post",
					dataType: "json",
					success: function(data) {
						$("#allimg").attr("src", data.url);
						$("#path").val(data.path);
					}
				};
				$("#jvForm").ajaxSubmit(opts);
			}
			function resetInfo(url,path){
				$("#allimg").attr("src",url);
				$("#path").val(path);
			}
		</script>
	</head>
	<body>
		<div class="box-positon">
			<div class="rpos">当前位置: 品牌管理 - 修改</div>
			<form class="ropt">
				<input type="button" onclick="javascript:window.location.href='/brand/list.do';" value="返回列表" class="return-button" />
			</form>
			<div class="clear"></div>
		</div>
		<div class="body-box" style="float:right">
			<form id="jvForm" action="/brand/edit.do" method="post">
				<input type="hidden" value="${brands.id}" name="id"/>
				<table cellspacing="1" cellpadding="2" width="100%" border="0" class="pn-ftable">
					<tr>
						<td width="20%" class="pn-flabel pn-flabel-h">
							<span class="pn-frequired">*</span> 品牌名称:
						</td>
						<td width="80%" class="pn-fcontent">
							<input type="text" class="required" name="name" value="${brands.name}" maxlength="100" />
						</td>
					</tr>
					<tr>
						<td width="20%" class="pn-flabel pn-flabel-h">
							<span class="pn-frequired">*</span> 品牌LOGO:
						</td>
						<td width="80%" class="pn-fcontent">
							注:该尺寸图片必须为90x150。
						</td>
					</tr>
					<tr>
						<td width="20%" class="pn-flabel pn-flabel-h"></td>
						<td width="80%" class="pn-fcontent">
							<img width="100" height="100" id="allimg" src="${brands.img}" />
							<input type="file" name="picture" onchange="ImgUpload();" />
							<input type="hidden" name="logo" id="path" value="${brands.logo}"/>
						</td>
					</tr>
					<tr>
						<td width="20%" class="pn-flabel pn-flabel-h">
							品牌描述:</td>
						<td width="80%" class="pn-fcontent">
							<input type="text" class="required" value="${brands.description}" name="description" maxlength="80" size="60" />
						</td>
					</tr>

					<tr>
						<td width="20%" class="pn-flabel pn-flabel-h">
							官方网站:</td>
						<td width="80%" class="pn-fcontent">
							<input type="text" class="required" value="${brands.url}" name="url" maxlength="80" size="60"/>
						</td>
					</tr>
					<tr>
						<td width="20%" class="pn-flabel pn-flabel-h">
							品牌的状态:</td>
						<td width="80%" class="pn-fcontent">
							<input type="radio" name="status" value="1" <c:if test="${brands.status==1}">checked="checked"</c:if> />在售
							<input type="radio" name="status" value="0" <c:if test="${brands.status==0}">checked="checked"</c:if> />停售
						</td>
					</tr>
					<tr>
						<td class="pn-fbutton" colspan="2">
							<input type="submit" class="submit" value="提交"/> &nbsp; 
							<input type="reset" onclick="javascript:resetInfo('${brands.img}','${brands.logo}');" class="submit" value="重置"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>