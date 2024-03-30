<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.personalfinanceapp.model.personalfinanceapp.Budget" %>
<!DOCTYPE html>
<html>
<head>
    <title>Budgets</title>
</head>
<body>
<div class="container">
    <h1>Budgets</h1>
    <% List<Budget> budgets = (List<Budget>) request.getAttribute("budgets");
        for (Budget budget : budgets) { %>
    <div>
        <span><%= budget.getCategory().getName() %> - $<%= budget.getAmount() %> - <%= budget.getPeriod() %></span>
        <form action="budgets" method="post" style="display:inline;">
            <input type="hidden" name="action" value="delete"/>
            <input type="hidden" name="id" value="<%= budget.getId() %>"/>
            <button type="submit">Delete</button>
        </form>
    </div>
    <% } %>
</div>
</body>
</html>
