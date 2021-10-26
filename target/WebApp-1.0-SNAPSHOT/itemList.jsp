<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="/meta.jsp"/>
    <meta charset="UTF-8">
    <%
        String listName = (String) request.getAttribute("name");
        String listID = request.getParameter("id");
    %>
    <title><%=listName%>
    </title>
</head>
<body>
<h1 class="title"><%=listName%>
</h1>
<div class="itemLists">
    <div style="text-align: center">
        <form method="post" action="list.html">
            <input type="text" name="val" class="textBar" placeholder="<%=listName%>"/>
            <input type="hidden" id="action" name="action" value="edit">
            <input type="hidden" id="name" name="name" value="<%=listID%>">
            <input type="submit" class="keyButton" value="Rename">
        </form>
        <br>
        <form method="post" action="list.html">
            <input type="submit" class="keyButton" value="Delete"/>
            <input type="hidden" id="delete" name="delete" value="<%=listID%>">
        </form>
        <br>
    </div>
</div>
<%@include file="buttons.html" %>
<jsp:include page="list.jsp"/>
</body>
</html>