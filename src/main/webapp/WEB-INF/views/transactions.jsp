<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.personalfinanceapp.model.personalfinanceapp.Transaction, com.personalfinanceapp.model.personalfinanceapp.Category" %>
<!DOCTYPE html>
<html>
<head>
    <title>Transactions</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script>
        $(document).ready(function() {
            // Function to add a new transaction dynamically
            $('#addTransactionForm').on('submit', function(e) {
                e.preventDefault(); // Prevent form from submitting in the traditional way
                var formData = $(this).serialize(); // Serialize form data for sending
                $.ajax({
                    type: "POST",
                    url: "${pageContext.request.contextPath}/addTransaction", // Adjust with your actual servlet URL
                    data: formData,
                    success: function(response) {
                        // Here, you should update the UI based on the response
                        // For simplicity, we're just reloading the page
                        location.reload(); // Reload to show the new transaction
                    },
                    error: function(xhr, status, error) {
                        // Handle errors
                        alert("An error occurred: " + xhr.status + " " + error);
                    }
                });
            });

            // Functionality to dynamically add a new category
            $('#addCategoryBtn').on('click', function() {
                var newCategoryName = prompt("Enter new category name:");
                if(newCategoryName) {
                    $.ajax({
                        type: "POST",
                        url: "${pageContext.request.contextPath}/addCategory", // Adjust with your actual servlet URL
                        data: {name: newCategoryName},
                        success: function(response) {
                            $('#category').append($('<option>', {
                                value: response.newCategoryId, // Assumes response contains new category ID
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
<div class="container mt-3">
    <h1>Transactions</h1>
    <form id="addTransactionForm" class="mb-3">
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
                <!-- Loop to populate categories -->
                <%
                    List<Category> categories = (List<Category>) request.getAttribute("categories");
                    for (Category category : categories) {
                %>
                <option value="<%= category.getId() %>"><%= category.getName() %></option>
                <%
                    }
                %>
            </select>
            <button type="button" id="addCategoryBtn" class="btn btn-info">Add New Category</button> <!-- Added button for dynamic category addition -->
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
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
