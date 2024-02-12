<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.personalfinanceapp.model.personalfinanceapp.Transaction" %>
<%@ page import="com.personalfinanceapp.model.personalfinanceapp.Category" %>
<!DOCTYPE html>
<html>
<head>
    <title>Transactions</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container mt-3">
    <h1>Transactions</h1>
    <form action="${pageContext.request.contextPath}/transactions" method="post" class="mb-3">
        <div class="form-group">
            <label for="amount">Amount</label>
            <input type="text" class="form-control" id="amount" name="amount" required>
        </div>
        <div class="form-group">
            <label for="type">Type</label>
            <select class="form-control" id="type" name="type">
                <option value="income">Income</option>
                <option value="expense">Expense</option>
            </select>
        </div>
        <div class="form-group">
            <label for="description">Description</label>
            <input type="text" class="form-control" id="description" name="description" required>
        </div>
        <div class="form-group">
            <label for="category">Category</label>
            <select class="form-control" id="category" name="category">
                <%
                    List<Category> categories = (List<Category>) request.getAttribute("categories");
                    for (Category category : categories) {
                %>
                <option value="<%= category.getId() %>"><%= category.getName() %></option>
                <%
                    }
                %>
            </select>
        </div>
        <div class="form-group">
            <label for="transactionDate">Date</label>
            <input type="date" class="form-control" id="transactionDate" name="transactionDate" required>
        </div>
        <button type="submit" class="btn btn-primary">Add Transaction</button>
    </form>
    <div class="table-responsive">
        <table class="table">
            <thead>
            <tr>
                <th>Date</th>
                <th>Description</th>
                <th>Category</th>
                <th>Amount</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <%
                List<Transaction> transactions = (List<Transaction>) request.getAttribute("transactions");
                if (transactions != null) {
                    for (Transaction transaction : transactions) {
            %>
            <tr>
                <td><%= transaction.getTransactionDate() %></td>
                <td><%= transaction.getDescription() %></td>
                <td><%= transaction.getCategory().getName() %></td>
                <td><%= transaction.getAmount() %></td>
                <td><a href="#" class="btn btn-primary">Edit</a></td>
            </tr>
            <%
                }
            } else {
            %>
            <tr>
                <td colspan="5" class="text-center">No transactions available.</td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>
    </div>
</div>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
