<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.personalfinanceapp.model.personalfinanceapp.Tag" %>
<%@ include file="header.jsp" %>
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
                    url: "${pageContext.request.contextPath}/tags",
                    data: formData,
                    success: function(response) {
                        $('#tagList').append('<a href="#" class="list-group-item list-group-item-action">' + response.name + '</a>'); // Update this line to match the expected response structure.
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
<div class="container mt-5">
    <h1 class="mb-4 text-center font-weight-bold text-primary">Tags</h1>
    <div class="card bg-light mb-3">
        <div class="card-body">
            <h4 class="card-title text-secondary text-center">Add a New Tag</h4>
            <form id="addTagForm">
                <div class="form-group">
                    <label for="name">Tag Name:</label>
                    <input type="text" class="form-control" id="name" name="name" placeholder="Enter tag name" required>
                </div>
                <button type="submit" class="btn btn-success btn-block">Add Tag</button>
            </form>
        </div>
    </div>
    <div class="list-group" id="tagList">
        <% List<Tag> tags = (List<Tag>) request.getAttribute("tags");
            if (tags != null) {
                for (Tag tag : tags) { %>
        <a href="#" class="list-group-item list-group-item-action"><%= tag.getName() %></a>
        <%      }
        } %>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
