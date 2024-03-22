<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.personalfinanceapp.model.personalfinanceapp.Transaction, com.personalfinanceapp.model.personalfinanceapp.Category" %>
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
                var formData = $(this).serialize();
                $.ajax({
                    type: "POST",
                    url: "${pageContext.request.contextPath}/addTransaction",
                    data: formData,
                    success: function(response) {
                        location.reload();
                    },
                    error: function(xhr, status, error) {
                        alert("An error occurred: " + xhr.status + " " + error);
                    }
                });
            });

            $('#addCategoryBtn').on('click', function() {
                var newCategoryName = prompt("Enter new category name:");
                if(newCategoryName) {
                    $.ajax({
                        type: "POST",
                        url: "${pageContext.request.contextPath}/addCategory",
                        data: {name: newCategoryName},
                        success: function(response) {
                            $('#category').append($('<option>', {
                                value: response.newCategoryId,
                                text: newCategoryName
                            }));
                        },
                        error: function(xhr, status, error) {
                            alert("Error adding category: " + xhr.status + " " + error);
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
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="amount">Amount</label>
                        <input type="text" class="form-control" id="amount" name="amount" required>
                    </div>
                    <div class="form-group col-md-6">
                        <label for="type">Type</label>
                        <select class="form-control" id="type" name="type">
                            <option value="income">Income</option>
                            <option value="expense">Expense</option>
                        </select>
                    </div>
                </div>
                <div class="form-group">
                    <label for="description">Description</label>
                    <input type="text" class="form-control" id="description" name="description" required>
                </div>
                <div class="form-group">
                    <label for="category">Category</label>
                    <div class="input-group">
                        <select class="form-control" id="category" name="category">
                            <% List<Category> categories = (List<Category>) request.getAttribute("categories");
                                for (Category category : categories) {
                            %>
                            <option value="<%= category.getId() %>"><%= category.getName() %></option>
                            <%
                                }
                            %>
                        </select>
                        <div class="input-group-append">
                            <button type="button" id="addCategoryBtn" class="btn btn-info">Add New Category</button>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="transactionDate">Date</label>
                    <input type="date" class="form-control" id="transactionDate" name="transactionDate" required>
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
<%
        List<Transaction> transactions = (List<Transaction>) request.getAttribute("transactions");
        if (                transactions != null) {
            for (Transaction transaction : transactions) {
%>
<tr>
    <td><%= transaction.getTransactionDate() %></td>
    <td><%= transaction.getDescription() %></td>
    <td><%= transaction.getCategory().getName() %></td>
    <td>$<%= transaction.getAmount() %></td>
    <td><button class="btn btn-primary btn-sm">Edit</button></td>
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
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

