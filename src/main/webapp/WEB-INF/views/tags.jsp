<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.personalfinanceapp.model.personalfinanceapp.Tag" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Tags</title>
    <!-- Include CSS and JS files -->
</head>
<body>
<div class="container">
    <h1>Tags</h1>
    <form action="tags" method="post">
        <input type="text" name="name" required placeholder="Enter tag name"/>
        <button type="submit">Add Tag</button>
    </form>

    <div class="tags-list">
        <% List<Tag> tags = (List<Tag>) request.getAttribute("tags");
            for (Tag tag : tags) { %>
        <div class="tag-item">
            <%= tag.getName() %>
            <form action="tags" method="post" style="display: inline;">
                <input type="hidden" name="action" value="delete"/>
                <input type="hidden" name="id" value="<%= tag.getId() %>"/>
                <button type="submit">Delete</button>
            </form>
        </div>
        <% } %>
    </div>
</div>
</body>
</html>
