<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.personalfinanceapp.model.personalfinanceapp.Tag" %>
<!DOCTYPE html>
<html>
<head>
    <title>Tags</title>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script>
        $(document).ready(function() {
            $('#addTagForm').submit(function(e) {
                e.preventDefault(); // Prevent the default form submission.
                var formData = $(this).serialize(); // Serialize the form data for AJAX submission.

                $.ajax({
                    type: 'POST',
                    url: "${pageContext.request.contextPath}/addTag", // Endpoint for adding a tag.
                    data: formData,
                    success: function(response) {
                        // Assuming 'response' contains the added tag details.
                        // Update the tag list. Adjust based on your actual response structure.
                        $('#tagList').append('<li>' + response.name + '</li>');
                        // Clear the input field after successful submission.
                        $('#name').val('');
                    },
                    error: function() {
                        alert('Error adding tag.');
                    }
                });
            });
        });
    </script>
</head>
<body>
<h1>Tags</h1>
<form id="addTagForm" action="${pageContext.request.contextPath}/tags" method="post">
    <label for="name">Tag Name:</label>
    <input type="text" id="name" name="name" placeholder="Tag Name" required>
    <button type="submit">Add Tag</button>
</form>
<ul id="tagList">
    <%
        List<Tag> tags = (List<Tag>) request.getAttribute("tags");
        for (Tag tag : tags) {
    %>
    <li><%= tag.getName() %></li>
    <% } %>
</ul>
</body>
</html>
