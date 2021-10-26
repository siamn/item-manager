<%@ page import="uk.ac.ucl.model.Item" %>
<%@ page import="java.util.LinkedHashMap" %>
<div style="text-align: center">
    <h2 class="title">Items:</h2>
    <%
        LinkedHashMap<String, Item> items = (LinkedHashMap<String, Item>) request.getAttribute("items");
        for (Item item : items.values())
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