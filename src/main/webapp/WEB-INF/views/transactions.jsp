<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="com.personalfinanceapp.model.personalfinanceapp.Transaction, com.personalfinanceapp.model.personalfinanceapp.Category, com.personalfinanceapp.model.personalfinanceapp.Tag" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Transactions</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script>
        $(document).ready(function() {
            $('#addTransactionForm').on('submit', function(e) {
                e.preventDefault();
                var formData = $(this).serializeArray();
                var formattedData = {};

                formData.forEach(function(item) {
                    if(item.name === 'transactionDate') {
                        console.log("Original date:", item.value); // Log the original date
                        var dateParts = item.value.split('/');
                        if (dateParts.length === 3) {
                            item.value = dateParts[2] + '-' + dateParts[1] + '-' + dateParts[0];
                            console.log("Formatted date:", item.value); // Log the formatted date
                        }
                    }
                    formattedData[item.name] = item.value;
                });

                console.log("Data to be sent:", formattedData); // Log all the data to be sent

                $.ajax({
                    type: "POST",
                    url: "${pageContext.request.contextPath}/transactions",
                    data: formattedData,
                    success: function(response) {
                        location.reload();
                    },
                    error: function(xhr, status, error) {
                        alert("Error: " + xhr.status + " " + error);
                    }
                });
            });

            $('.delete-transaction').on('click', function() {
                var transactionId = $(this).data('id');
                if(confirm('Are you sure you want to delete this transaction?')) {
                    $.ajax({
                        type: "POST",
                        url: "${pageContext.request.contextPath}/transactions",
                        data: {id: transactionId, action: "delete"},
                        success: function(response) {
                            location.reload();
                        },
                        error: function(xhr, status, error) {
                            alert("Error deleting transaction: " + xhr.status + " " + error);
                        }
                    });
                }
            });
        });
    </script>
</head>
<body>
<div class="container mt-5">
    <h1 class="mb-4 text-center font-weight-bold text-primary">Transactions</h1>
    <div class="card bg-light mb-3">
        <div class="card-body">
            <h4 class="card-title text-secondary text-center">Add a New Transaction</h4>
            <form id="addTransactionForm">
                <div class="form-group">
                    <label for="amount">Amount</label>
                    <input type="number" class="form-control" id="amount" name="amount" required>
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
                    <label for="transactionDate">Date</label>
                    <input type="date" class="form-control" id="transactionDate" name="transactionDate"
                           value="<%= LocalDate.now().toString() %>" required>
                </div>
                <div class="form-group">
                    <label for="category">Category</label>
                    <select class="form-control" id="category" name="category">
                        <% List<Category> categories = (List<Category>) request.getAttribute("categories");
                            for (Category category : categories) { %>
                        <option value="<%= category.getId() %>"><%= category.getName() %></option>
                        <% } %>
                    </select>
                </div>
                <button type="submit" class="btn btn-success btn-block">Add Transaction</button>
            </form>
        </div>
    </div>
    <div class="table-responsive">
        <table class="table table-hover">
            <thead class="thead-light">
            <tr>
                <th>Date</th>
                <th>Description</th>
                <th>Category</th>
                <th>Amount</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <% List<Transaction> transactions = (List<Transaction>) request.getAttribute("transactions");
                for (Transaction transaction : transactions) { %>
            <tr>
                <td><%= transaction.getTransactionDate().toString() %></td>
                <td><%= transaction.getDescription() %></td>
                <td><%= transaction.getCategory().getName() %></td>
                <td>$<%= transaction.getAmount().toString() %></td>
                <td>
                    <button class="btn btn-danger btn-sm delete-transaction" data-id="<%= transaction.getId() %>">Delete</button>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
