<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.Map" %>
<!DOCTYPE html>
<html>
<head>
    <title>Budgets</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-3">
    <h1>Budgets</h1>
    <!-- Budgets table -->
    <div class="row">
        <div class="col-md-6">
            <h3>Income</h3>
            <%-- Loop through income budgets here --%>
            <p>Income: $0 of $362</p>
        </div>
        <div class="col-md-6">
            <h3>Spending</h3>
            <%-- Loop through spending budgets here --%>
            <p>Auto & Transport: $0 of $500</p>
            <!-- More categories -->
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
