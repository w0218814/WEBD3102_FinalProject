<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.personalfinanceapp.model.personalfinanceapp.Tag" %>
<html>
<head>
    <title>Tags</title>
</head>
<body>
<h1>Tags</h1>
<form action="${pageContext.request.contextPath}/tags" method="post">
    <label for="name">Tag Name:</label>
    <input type="text" id="name" name="name" placeholder="Tag Name">
    <button type="submit">Add Tag</button>
</form>
<ul>
    <%
        List<Tag> tags = (List<Tag>) request.getAttribute("tags");
        for (Tag tag : tags) {
    %>
    <li><%= tag.getName() %></li>
    <% } %>
</ul>
</body>
</html>
