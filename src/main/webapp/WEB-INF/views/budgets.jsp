<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
    <title>Budgets</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script>
        $(document).ready(function() {
            // Example function to dynamically update budgets without a page refresh
            function updateBudgets() {
                $.ajax({
                    type: 'GET',
                    url: "${pageContext.request.contextPath}/fetchBudgets", // Adjust with your endpoint
                    success: function(response) {
                        // Assuming 'response' is a JSON object containing budget categories and their info
                        $('#budgetContainer').empty(); // Clear existing content
                        $.each(response, function(category, amount) {
                            // Dynamically add budget info. Adjust based on your actual response structure.
                            $('#budgetContainer').append(
                                '<div class="row">' +
                                '<div class="col-md-6"><h3>' + category + '</h3></div>' +
                                '<div class="col-md-6"><p>' + amount.used + ' of ' + amount.total + '</p></div>' +
                                '</div>'
                            );
                        });
                    },
                    error: function() {
                        alert('Error fetching budget information.');
                    }
                });
            }

            // Call the update function on page load or whenever needed
            updateBudgets();

            // Here you could also include functionality to add or update budgets, similar to previous examples
        });
    </script>
</head>
<body>
<div class="container mt-3">
    <h1>Budgets</h1>
    <!-- Dynamic Budgets Container -->
    <div id="budgetContainer">
        <!-- Budgets will be loaded dynamically here -->
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
