<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.personalfinanceapp.model.personalfinanceapp.Budget" %>
<%@ page import="com.personalfinanceapp.model.personalfinanceapp.Category" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Budgets</title>
    <link rel="stylesheet" href="//stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h1 class="text-center font-weight-bold text-primary mb-4">Budgets</h1>

    <!-- Display success/error message -->
    <% if (request.getParameter("success") != null) { %>
    <% if ("true".equals(request.getParameter("success"))) { %>
    <div class="alert alert-success" role="alert">Operation was successful.</div>
    <% } else { %>
    <div class="alert alert-danger" role="alert">Operation failed. Please try again.</div>
    <% } %>
    <% } %>

    <div class="card bg-light mb-5">
        <div class="card-body">
            <h4 class="card-title text-secondary text-center">Manage a Budget</h4>
            <form action="budgets" method="post">
                <div class="form-group">
                    <label for="amount">Amount:</label>
                    <input type="number" class="form-control" id="amount" name="amount" required placeholder="Enter budget amount"/>
                </div>
                <div class="form-group">
                    <label for="period">Period:</label>
                    <input type="text" class="form-control" id="period" name="period" required placeholder="Enter budget period"/>
                </div>
                <div class="form-group">
                    <label for="category">Category:</label>
                    <select class="form-control" id="category" name="category">
                        <% List<Category> categories = (List<Category>) request.getAttribute("categories"); %>
                        <% for (Category category : categories) { %>
                        <option value="<%= category.getId() %>"><%= category.getName() %></option>
                        <% } %>
                    </select>
                </div>
                <input type="hidden" name="action" value="saveOrUpdate"/>
                <button type="submit" class="btn btn-success btn-block">Submit Budget</button>
            </form>
        </div>
    </div>

    <div class="list-group" id="budgetList">
        <% List<Budget> budgets = (List<Budget>) request.getAttribute("budgets");
            if (budgets != null && !budgets.isEmpty()) {
                for (Budget budget : budgets) { %>
        <div class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
            <%= budget.getCategory().getName() %> - $<%= budget.getAmount() %> - <%= budget.getPeriod() %>
            <form action="budgets" method="post">
                <input type="hidden" name="action" value="delete"/>
                <input type="hidden" name="id" value="<%= budget.getId() %>"/>
                <button type="submit" class="btn btn-danger btn-sm">Delete</button>
            </form>
        </div>
        <% }
        } else { %>
        <div class="list-group-item">No budgets found.</div>
        <% } %>
    </div>
</div>
</body>
</html>
