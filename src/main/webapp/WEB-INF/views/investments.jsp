<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="com.personalfinanceapp.model.personalfinanceapp.Investment" %>
<!DOCTYPE html>
<html>
<head>
    <title>Investments</title>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script>
        $(document).ready(function() {
            $('#addInvestmentForm').submit(function(e) {
                e.preventDefault(); // Prevents the default form submission behavior
                var formData = $(this).serialize(); // Serializes form data for submission

                $.ajax({
                    type: 'POST',
                    url: "${pageContext.request.contextPath}/investments",
                    data: formData,
                    success: function(response) {
                        // Assume response includes 'amount', 'type', and 'date' (formatted)
                        $('#investmentList').append('<li>' + response.amount + ' - ' + response.type + ' - ' + response.date + '</li>');
                        // Resets form inputs after successful submission
                        $('#addInvestmentForm').find('input[type=number], input[type=text], input[type=date]').val('');
                    },
                    error: function() {
                        // Alerts if an error occurs
                        alert('Error adding investment.');
                    }
                });
            });
        });
    </script>
</head>
<body>
<div class="container">
    <h1>Investments</h1>
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
        <button type="submit" class="btn btn-primary">Add Investment</button>
    </form>
    <ul id="investmentList">
        <%
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            List<Investment> investments = (List<Investment>) request.getAttribute("investments");
            for (Investment investment : investments) {
        %>
        <li><%= investment.getAmount() %> - <%= investment.getType() %> - <%= investment.getInvestmentDate().format(formatter) %></li>
        <% } %>
    </ul>
</div>
</body>
</html>
