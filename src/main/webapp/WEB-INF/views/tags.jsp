<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.personalfinanceapp.model.personalfinanceapp.Tag" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Tags</title>
    <link rel="stylesheet" href="//stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h1 class="text-center font-weight-bold text-primary mb-4">Tags</h1>

    <!-- Display success/error message -->
    <% if (request.getParameter("success") != null) { %>
    <% if ("true".equals(request.getParameter("success"))) { %>
    <div class="alert alert-success" role="alert">Operation was successful.</div>
    <% } else { %>
    <div class="alert alert-danger" role="alert">Operation failed. Please try again.</div>
    <% } %>
    <% } %>

    <div class="card bg-light mb-5">
        <div class="card-body">
            <h4 class="card-title text-secondary text-center">Add a New Tag</h4>
            <form action="tags" method="post">
                <div class="form-group">
                    <input type="text" class="form-control" name="name" required placeholder="Enter tag name"/>
                </div>
                <button type="submit" class="btn btn-success btn-block">Add Tag</button>
            </form>
        </div>
    </div>

    <div class="list-group" id="tagList">
        <% List<Tag> tags = (List<Tag>) request.getAttribute("tags");
            for (Tag tag : tags) { %>
        <div class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
            <%= tag.getName() %>
            <form action="tags" method="post">
                <input type="hidden" name="action" value="delete"/>
                <input type="hidden" name="id" value="<%= tag.getId() %>"/>
                <button type="submit" class="btn btn-danger btn-sm">Delete</button>
            </form>
        </div>
        <% } %>
    </div>
</div>
</body>
</html>
