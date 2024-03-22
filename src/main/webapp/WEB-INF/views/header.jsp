<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Header</title>
    <link rel="stylesheet" href="//stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <style>
        .navbar-custom {
            background-color: #007bff; /* Bootstrap primary color */
            border-bottom: 2px solid #0056b3; /* A slightly darker shade for the bottom border */
        }
        .navbar-custom .navbar-brand,
        .navbar-custom .nav-link {
            color: #ffffff !important; /* White text for better contrast */
        }
        .navbar-custom .nav-link:hover {
            color: #cce5ff !important; /* Lighter shade for hover effect */
        }
    </style>
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-custom">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">Personal Finance App</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav ml-auto"> <!-- ml-auto to align nav items to the right -->
                <a class="nav-item nav-link" href="${pageContext.request.contextPath}/transactions">Transactions</a>
                <a class="nav-item nav-link" href="${pageContext.request.contextPath}/categories">Categories</a>
                <a class="nav-item nav-link" href="${pageContext.request.contextPath}/tags">Tags</a>
                <a class="nav-item nav-link" href="${pageContext.request.contextPath}/budgets">Budgets</a>
                <a class="nav-item nav-link" href="${pageContext.request.contextPath}/investments">Investments</a>
            </div>
        </div>
    </div>
</nav>
</body>
</html>
