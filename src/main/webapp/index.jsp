<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Personal Finance App</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            padding-top: 20px;
        }
        /* Styling from header.jsp for consistent navbar appearance */
        .navbar-custom {
            background-color: #007bff; /* Bootstrap primary color for header */
            border-bottom: 2px solid #0056b3; /* A darker shade for a subtle contrast */
        }
        .navbar-custom .navbar-brand,
        .navbar-custom .nav-link {
            color: #ffffff !important; /* Ensures text is white for readability */
        }
        .navbar-custom .nav-link:hover {
            color: #cce5ff !important; /* Lighter blue on hover for interactivity */
        }
        .welcome-message {
            margin-bottom: 20px;
            color: #007bff; /* Theme color for the welcome message */
            font-weight: 700; /* Make the welcome message bold */
        }
        .hero-section {
            background-color: #007bff; /* Matching the navbar color */
            color: #ffffff;
            padding: 40px 0;
            border-radius: 8px;
            margin-top: 20px;
            text-align: center;
        }
        .hero-cta {
            margin-top: 20px;
        }
        .app-description {
            font-size: 18px;
            margin-bottom: 20px;
            color: #333; /* Dark color for readability */
        }
    </style>
</head>
<body>
<div class="container">
    <!-- Navbar with custom styling -->
    <nav class="navbar navbar-expand-lg navbar-custom">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">Home</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav ml-auto">
                <a class="nav-item nav-link" href="${pageContext.request.contextPath}/transactions">Transactions</a>
                <a class="nav-item nav-link" href="${pageContext.request.contextPath}/categories">Categories</a>
                <a class="nav-item nav-link" href="${pageContext.request.contextPath}/tags">Tags</a>
                <a class="nav-item nav-link" href="${pageContext.request.contextPath}/budgets">Budgets</a>
                <a class="nav-item nav-link" href="${pageContext.request.contextPath}/investments">Investments</a>
            </div>
        </div>
    </nav>
    <h1 class="text-center welcome-message">Welcome to Personal Finance App</h1>
    <div class="hero-section">
        <h2>Manage Your Finances with Ease</h2>
        <p>Get insights into your spending, set budgets, and track investments all in one place.</p>
        <div class="hero-cta">
            <a href="${pageContext.request.contextPath}/budgets" class="btn btn-light btn-lg">Get Started</a>
        </div>
    </div>
    <p class="text-center app-description">
        This is your one-stop solution for managing personal finances with ease and insight. Navigate through the app using the menu to start organizing your financial life today!
    </p>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
