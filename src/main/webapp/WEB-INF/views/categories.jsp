<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.personalfinanceapp.model.personalfinanceapp.Category" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Categories</title>
    <link rel="stylesheet" href="//stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script>
        $(document).ready(function() {
            $('#addCategoryForm').submit(function(e) {
                e.preventDefault();
                var formData = $(this).serialize();
                $.ajax({
                    type: 'POST',
                    url: "${pageContext.request.contextPath}/categories", // Correct endpoint.
                    data: formData,
                    success: function(response) {
                        // Updated to append as a div instead of an <a> tag.
                        $('#categoryList').append('<div class="list-group-item list-group-item-action">' + response.name + '</div>'); // Update based on your response structure, wrapped in list-group-item for styling.
                        $('#name').val('');
                    },
                    error: function() {
                        alert('Error adding category.');
                    }
                });
            });
        });
    </script>
</head>
<body>
<div class="container mt-5">
    <h1 class="text-center font-weight-bold text-primary mb-4">Categories</h1>
    <div class="card bg-light mb-5">
        <div class="card-body">
            <h4 class="card-title text-secondary text-center">Add a New Category</h4>
            <form id="addCategoryForm">
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
        <!-- Updated to use div instead of an <a> tag -->
        <div class="list-group-item list-group-item-action"><%= category.getName() %></div>
        <% } %>
    </div>
</div>
</body>
</html>
