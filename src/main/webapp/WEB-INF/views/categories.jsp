<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.personalfinanceapp.model.personalfinanceapp.Category" %>
<!DOCTYPE html>
<html>
<head>
    <title>Categories</title>
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
                        $('#categoryList').append('<li>' + response.name + '</li>'); // Update based on your response structure.
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
<h1>Categories</h1>
<form id="addCategoryForm">
    <label for="name">Category Name:</label>
    <input type="text" id="name" name="name" placeholder="Category Name" required>
    <button type="submit">Add Category</button>
</form>
<ul id="categoryList">
    <%
        List<Category> categories = (List<Category>) request.getAttribute("categories");
        for (Category category : categories) {
    %>
    <li><%= category.getName() %></li>
    <% } %>
</ul>
</body>
</html>
