
<%@ page import="com.pagecreation.PageHandler" %>
<html>
<head>
	<link rel="stylesheet" href="style.css">
	<meta charset="ISO-8859-1">
	<title>To Do Page</title>
</head>

<!-- I didn't write this script. It was taken from WordPress. It is used to prevent double database entry upon a browser refresh. -->
<script>
    if ( window.history.replaceState ) {
        window.history.replaceState( null, null, window.location.href );
    }
</script>

<%
	String delete = request.getParameter("deleteID");
	PageHandler.deleteItem(delete);
	String description = request.getParameter("description");
	String priority = request.getParameter("priority");
	String completeDate = request.getParameter("completedate");
	PageHandler.addItem(priority, description, completeDate);
%>

<body>
<table border="1">
<caption>To Do Table</caption>
<tr>
	<td>Task Number</td>
	<td>Description</td>
	<td>Priority</td>
	<td>Completion Date</td>
	<td>Action</td>
</tr>
	<%=PageHandler.addRows()%>
<form name="inputData" action="index.jsp" method="post">
<tr>
	<td></td>
	<td><input type="text" name="description" value="" size="20" /></td>
	<td><select name="priority">
					<option value="">Choose priority...</option>
					<option value="low">Low</option>
					<option value="medium">Medium</option>
					<option value="high">High</option>
	</select></td>
	<td><input type="date" name="completedate" value="" size="" /></td>
	<td><input type="submit" value="Submit" name="submit" /></td>
</tr>
</form>	
</table>
</body>
</html>