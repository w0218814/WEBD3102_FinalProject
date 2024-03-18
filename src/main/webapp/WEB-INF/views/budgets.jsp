<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.personalfinanceapp.model.personalfinanceapp.Budget" %>
<!DOCTYPE html>
<html>
<head>
    <title>Budgets</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script>
        $(document).ready(function() {
            function updateBudgets() {
                $.ajax({
                    type: 'GET',
                    url: "${pageContext.request.contextPath}/budgets",
                    success: function(data) {
                        $('#budgetContainer').empty();
                        $.each(data, function(index, budget) {
                            $('#budgetContainer').append(
                                '<div class="row">' +
                                '<div class="col-md-6"><h3>' + budget.category.name + '</h3></div>' +
                                '<div class="col-md-6"><p>$' + budget.amount + ' for ' + budget.period + '</p></div>' +
                                '</div>'
                            );
                        });
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        console.log("AJAX error: " + textStatus + ' : ' + errorThrown);
                    }
                });
            }

            updateBudgets();
        });
    </script>
</head>
<body>
<div class="container mt-3">
    <h1>Budgets</h1>
    <div id="budgetContainer">
        <!-- Budgets will be loaded here dynamically -->
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
