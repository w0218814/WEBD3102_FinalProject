<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="com.personalfinanceapp.model.personalfinanceapp.Investment" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Investments</title>
    <link rel="stylesheet" href="//stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script>
        $(document).ready(function() {
            $('#addInvestmentForm').submit(function(e) {
                e.preventDefault();
                var formData = $(this).serialize();
                $.ajax({
                    type: 'POST',
                    url: "${pageContext.request.contextPath}/investments",
                    data: formData,
                    success: function(response) {
                        $('#investmentList').append('<a href="#" class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">' + response.amount + ' - ' + response.type + ' - ' + response.date + '</a>');
                        $('#addInvestmentForm').find('input').val('');
                    },
                    error: function() {
                        alert('Error adding investment.');
                    }
                });
            });
        });
    </script>
</head>
<body>
<div class="container mt-5">
    <h1 class="mb-4 text-center font-weight-bold text-primary">Investments</h1>
    <div class="card bg-light mb-5">
        <div class="card-body">
            <h4 class="card-title text-center text-secondary">Add a New Investment</h4>
            <form id="addInvestmentForm">
                <div class="form-group">
                    <label for="amount">Amount:</label>
                    <input type="number" id="amount" name="amount" placeholder="Amount" required class="form-control">
                </div>
                <div class="form-group">
                    <label for="type">Type:</label>
                    <input type="text" id="type" name="type" placeholder="Type" required class="form-control">
                </div>
                <div class="form-group">
                    <label for="investmentDate">Date:</label>
                    <input type="date" id="investmentDate" name="investmentDate" required class="form-control">
                </div>
                <button type="submit" class="btn btn-success btn-block">Add Investment</button>
            </form>
        </div>
    </div>
    <div class="list-group" id="investmentList">
        <%
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            List<Investment> investments = (List<Investment>) request.getAttribute("investments");
            for (Investment investment : investments) {
        %>
        <a href="#" class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
            <%= investment.getAmount() %> - <%= investment.getType() %> - <%= investment.getInvestmentDate().format(formatter) %>
        </a>
        <% } %>
    </div>
</div>
</body>
</html>
