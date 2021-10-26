<%@ page import="uk.ac.ucl.model.Item" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <jsp:include page="/meta.jsp"/>
    <meta charset="UTF-8">
    <%
        Item item = (Item) request.getAttribute("item");
    %>
    <title><%=item.getName()%>
    </title>
</head>
<body>
<h1 class="title"><%=item.getName()%>
</h1>
<div style="text-align: center">
    <%
    if (item.getType().equals("image"))
    {%>
        <img src="<%=item.getValue()%>" alt="<%=item.getName()%>"><br><br>
        <form action="list.html" method="post" enctype="multipart/form-data">
            <input type="file" name="file" id="chooseButton" accept="image/*" hidden/>
            <label for="chooseButton">Choose File</label><br><br>
    <%}
    else if (item.getType().equals("url"))
    {%>
        <a class="b2" href="<%=item.getValue()%>" class="links" target="_blank" rel="noopener noreferrer">
            Open Link
        </a><br>
        <form method="post" action="list.html">
            <input type="text" name="val" class="textBar" placeholder="<%=item.getValue()%>"/>
    <%} else
    {%>
        <form method="post" action="list.html">
            <textarea name="val" rows="12" cols="60"><%=item.getValue()%></textarea><br><br>
    <%}%>
            <input type="hidden" id="edit" name="edit" value="<%=item.getID()%>">
            <input type="submit" class="keyButton" value="Edit">
        </form><br>
    <form method="post" action="list.html">
        <input type="submit" class="keyButton" value="Remove">
        <input type="hidden" id="remove" name="remove" value="<%=item.getID()%>">
    </form><br>
    <a class="b2" href="list.html">Return</a>
</div>
</body>
</html>