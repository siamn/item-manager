<%@ page import="uk.ac.ucl.model.Item" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <jsp:include page="/meta.jsp"/>
    <title>Search Results</title>
</head>
<body>
<div style="text-align: center">
    <br><a class="b1" href="list.html?id=0">Main</a><br>
    <h2 class="title">Results:</h2>
    <%
        ArrayList<Item> results = (ArrayList<Item>) request.getAttribute("results");
        for (Item item : results)
        {
            if (item == null)
            {
                continue;
            }
            String name = item.getName();
            String href;
            if (item.getType().equals("itemList"))
            {
                href = "list.html?id=" + item.getID();
            } else
            {
                href = "item.html?id=" + item.getID();
            }
    %>
    <a class="b2" href="<%=href%>"><%=name%>
    </a><br>
    <%}%>
</div>
</body>
</html>


