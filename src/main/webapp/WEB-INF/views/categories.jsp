<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.personalfinanceapp.model.personalfinanceapp.Category" %>
<!DOCTYPE html>
<html>
<head>
    <title>Categories</title>
    <link rel="stylesheet" href="//stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h1 class="text-center font-weight-bold text-primary mb-4">Categories</h1>

    <!-- Displaying success or error messages based on operation results -->
    <% if (request.getParameter("success") != null) { %>
    <% if ("true".equals(request.getParameter("success"))) { %>
    <div class="alert alert-success" role="alert">Operation was successful.</div>
    <% } else { %>
    <div class="alert alert-danger" role="alert">Operation failed.</div>
    <% } %>
    <% } %>

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

    <!-- List existing categories with an option to delete -->
    <div class="list-group" id="categoryList">
        <% List<Category> categories = (List<Category>) request.getAttribute("categories");
            if (categories != null) {
                for (Category category : categories) { %>
        <div class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
            <%= category.getName() %>
            <form action="categories?action=delete" method="post">
                <input type="hidden" name="id" value="<%= category.getId() %>" />
                <button type="submit" class="btn btn-danger btn-sm">Delete</button>
            </form>
        </div>
        <%      }
        } %>
    </div>
</div>
</body>
</html>
