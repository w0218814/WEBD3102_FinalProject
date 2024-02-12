<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.personalfinanceapp.model.personalfinanceapp.Category" %>
<html>
<head>
    <title>Categories</title>
</head>
<body>
<h1>Categories</h1>
<form action="${pageContext.request.contextPath}/categories" method="post">
    <label for="name">Category Name:</label>
    <input type="text" id="name" name="name" placeholder="Category Name">
    <button type="submit">Add Category</button>
</form>
<ul>
    <%
        List<Category> categories = (List<Category>) request.getAttribute("categories");
        for (Category category : categories) {
    %>
    <li><%= category.getName() %></li>
    <% } %>
</ul>
</body>
</html>
