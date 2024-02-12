<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.personalfinanceapp.model.personalfinanceapp.Investment" %>
<html>
<head>
    <title>Investments</title>
</head>
<body>
<h1>Investments</h1>
<form action="${pageContext.request.contextPath}/investments" method="post">
    <label for="amount">Amount:</label>
    <input type="number" id="amount" name="amount" placeholder="Amount">
    <label for="type">Type:</label>
    <input type="text" id="type" name="type" placeholder="Type">
    <button type="submit">Add Investment</button>
</form>
<ul>
    <%
        List<Investment> investments = (List<Investment>) request.getAttribute("investments");
        for (Investment investment : investments) {
    %>
    <li><%= investment.getAmount() %> - <%= investment.getType() %></li>
    <% } %>
</ul>
</body>
</html>
