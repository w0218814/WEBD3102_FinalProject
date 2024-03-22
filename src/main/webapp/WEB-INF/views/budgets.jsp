<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.personalfinanceapp.model.personalfinanceapp.Budget" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Budgets</title>
    <link rel="stylesheet" href="//stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <!-- Optional: Include custom CSS for further customization -->
</head>
<body>
<div class="container mt-5">
    <h2 class="mb-4 text-center font-weight-bold text-primary">Budgets</h2>
    <div class="card bg-light mb-5">
        <div class="card-body">
            <h4 class="card-title text-center text-secondary">Add a New Budget</h4>
            <form action="budgets" method="post">
                <div class="form-group">
                    <label for="amount">Amount:</label>
                    <input type="number" step="0.01" class="form-control" id="amount" name="amount" required>
                </div>
                <div class="form-group">
                    <label for="period">Period:</label>
                    <input type="text" class="form-control" id="period" name="period" required>
                </div>
                <div class="form-group">
                    <label for="category_id">Category:</label>
                    <input type="number" class="form-control" id="category_id" name="category_id" required>
                </div>
                <button type="submit" class="btn btn-success btn-block">Submit</button>
            </form>
        </div>
    </div>
    <h3 class="text-dark mb-3">Existing Budgets</h3>
    <div class="list-group">
        <% List<Budget> budgets = (List<Budget>) request.getAttribute("budgets");
            for (Budget budget : budgets) { %>
        <div class="list-group-item list-group-item-action flex-column align-items-start">
            <div class="d-flex w-100 justify-content-between">
                <h5 class="mb-1">$<%= budget.getAmount() %></h5>
                <small><%= budget.getCategory().getName() %></small> <!-- Updated to display category name -->
            </div>
            <p class="mb-1">For <%= budget.getPeriod() %></p>
        </div>
        <% } %>
    </div>
</div>
</body>
</html>
