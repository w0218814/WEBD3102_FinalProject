<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.personalfinanceapp.model.personalfinanceapp.Tag" %>
<!DOCTYPE html>
<html>
<head>
    <title>Tags</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script>
        $(document).ready(function() {
            $('#addTagForm').submit(function(e) {
                e.preventDefault();
                var formData = $(this).serialize();

                $.ajax({
                    type: 'POST',
                    url: "${pageContext.request.contextPath}/tags", // Adjust if necessary.
                    data: formData,
                    success: function(response) {
                        // Assuming 'response' is a JSON object that contains the tag name.
                        var tagName = response.name; // Adjust based on actual response structure.
                        $('#tagList').append('<li>' + tagName + '</li>');
                        $('#name').val(''); // Clear input field after successful addition.
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
<div class="container mt-3">
    <h1>Tags</h1>
    <form id="addTagForm">
        <div class="form-group">
            <label for="name">Tag Name:</label>
            <input type="text" class="form-control" id="name" name="name" placeholder="Enter tag name" required>
        </div>
        <button type="submit" class="btn btn-primary">Add Tag</button>
    </form>
    <ul id="tagList">
        <% List<Tag> tags = (List<Tag>) request.getAttribute("tags");
            if (tags != null) {
                for (Tag tag : tags) {
        %>
        <li><%= tag.getName() %></li>
        <%      }
        } %>
    </ul>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
