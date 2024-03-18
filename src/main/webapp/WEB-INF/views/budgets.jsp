<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.personalfinanceapp.model.personalfinanceapp.Budget" %>
<!DOCTYPE html>
<html>
<head>
    <title>Budgets</title>
    <link rel="stylesheet" href="//stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <h2>Budgets</h2>
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
            <label for="category_id">Category ID:</label>
            <input type="number" class="form-control" id="category_id" name="category_id" required>
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
    </form>
    <hr>
    <h3>Existing Budgets</h3>
    <ul>
        <% List<Budget> budgets = (List<Budget>) request.getAttribute("budgets");
            for (Budget budget : budgets) { %>
        <li>$<%= budget.getAmount() %> for <%= budget.getPeriod() %> in category ID <%= budget.getCategory().getId() %></li>
        <% } %>
    </ul>
</div>
</body>
</html>
