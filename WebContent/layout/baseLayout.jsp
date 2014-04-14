<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:insertAttribute name="title" ignore="true" /></title>
</head>

<body style="background-color: #f6f4ff; font-family: cursive;">
	<tiles:insertAttribute name="banner" />

	<table width="100%" height="50px" style="background-color: #afafff;">
		<tr>
			<td align="center"><tiles:insertAttribute name="profileLink" /></td>
			<td align="center"><tiles:insertAttribute name="manageGroupsLink" /></td>
			<td align="center"><tiles:insertAttribute name="manageRolesLink" /></td>
			<td align="center"><tiles:insertAttribute name="documentsLink" /></td>
			<td align="center"><tiles:insertAttribute name="ManageDocuments" /></td>
			<td align="center"><tiles:insertAttribute name="manageDocumentTypesLink" /></td>
			<td align="center"><tiles:insertAttribute name="manageWorkflowsLink" /></td>
		</tr>
	</table>
	<br />

	<div>
		<tiles:insertAttribute name="body" />
	</div>
</body>
</html>
