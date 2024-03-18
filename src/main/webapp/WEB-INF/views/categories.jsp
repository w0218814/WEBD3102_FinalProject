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
                e.preventDefault(); // Prevent the form from submitting via the browser.
                var formData = $(this).serialize(); // Serialize the form data.
                $.ajax({
                    type: 'POST',
                    url: "${pageContext.request.contextPath}/addCategory", // Your endpoint here.
                    data: formData,
                    success: function(response) {
                        // Assuming the response contains the added category.
                        // Update the UI to reflect the addition.
                        $('ul#categoryList').append('<li>' + response.name + '</li>'); // Adjust according to your response structure.
                        $('#name').val(''); // Clear the input field after successful submission.
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
<form id="addCategoryForm" action="${pageContext.request.contextPath}/categories" method="post">
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
