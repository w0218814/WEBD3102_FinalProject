<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.personalfinanceapp.model.personalfinanceapp.Category" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Categories</title>
    <link rel="stylesheet" href="//stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h1 class="text-center font-weight-bold text-primary mb-4">Categories</h1>
    <div class="card bg-light mb-5">
        <div class="card-body">
            <h4 class="card-title text-secondary text-center">Add a New Category</h4>
            <form action="categories" method="post">
                <div class="form-group">
                    <label for="name">Category Name:</label>
                    <input type="text" class="form-control" id="name" name="name" placeholder="Category Name" required>
                </div>
                <button type="submit" class="btn btn-success btn-block">Add Category</button>
            </form>
        </div>
    </div>
    <div class="list-group" id="categoryList">
        <% List<Category> categories = (List<Category>) request.getAttribute("categories");
            for (Category category : categories) { %>
        <form action="categories" method="post" class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
            <%= category.getName() %>
            <input type="hidden" name="action" value="delete"/>
            <input type="hidden" name="id" value="<%= category.getId() %>"/>
            <button type="submit" class="btn btn-danger btn-sm">Delete</button>
        </form>
        <% } %>
    </div>
</div>
</body>
</html>
