<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.personalfinanceapp.model.personalfinanceapp.Budget" %>
<%@ page import="com.personalfinanceapp.model.personalfinanceapp.Category" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <link rel="stylesheet" href="//stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
<div class="container mt-5">
    <h1 class="text-center font-weight-bold text-primary mb-4">Dashboard</h1>

    <canvas id="budgetChart" width="400" height="400"></canvas>

</div>

<script>
    // Preload data into JavaScript variables
    var budgetData = [
        <% List<Budget> budgets = (List<Budget>) request.getAttribute("budgets");
           for (int i = 0; i < budgets.size(); i++) {
               Budget budget = budgets.get(i);
               %>
        {
            label: "<%= budget.getCategory().getName() %>",
            amount: <%= budget.getAmount() %>
        }<%= (i < budgets.size() - 1) ? "," : "" %>
        <% } %>
    ];

    // Prepare the data for Chart.js
    var labels = budgetData.map(function(budget) { return budget.label; });
    var data = budgetData.map(function(budget) { return budget.amount; });

    // Render the chart
    var ctx = document.getElementById('budgetChart').getContext('2d');
    var myChart = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: labels,
            datasets: [{
                label: 'Budget Amount',
                data: data,
                backgroundColor: 'rgba(255, 99, 132, 0.2)',
                borderColor: 'rgba(255, 99, 132, 1)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true
                }
            }
        }
    });
</script>
</body>
</html>
