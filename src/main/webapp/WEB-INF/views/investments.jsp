<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.personalfinanceapp.model.personalfinanceapp.Investment" %>
<!DOCTYPE html>
<html>
<head>
    <title>Investments</title>
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script>
        $(document).ready(function() {
            $('#addInvestmentForm').submit(function(e) {
                e.preventDefault(); // Prevent the default form submission.
                var formData = $(this).serialize(); // Serialize the form data for AJAX submission.

                $.ajax({
                    type: 'POST',
                    url: "${pageContext.request.contextPath}/addInvestment", // Endpoint for adding an investment.
                    data: formData,
                    success: function(response) {
                        // Assuming 'response' contains the added investment details.
                        // Update the investment list. Adjust based on your actual response structure.
                        $('#investmentList').append('<li>' + response.amount + ' - ' + response.type + '</li>');
                        // Clear form fields after submission.
                        $('#addInvestmentForm').find('input[type=number], input[type=text]').val('');
                    },
                    error: function() {
                        alert('Error adding investment.');
                    }
                });
            });

            // Function to load investments dynamically (optional, based on your app design)
            function loadInvestments() {
                $.ajax({
                    type: 'GET',
                    url: "${pageContext.request.contextPath}/fetchInvestments", // Endpoint to fetch investments.
                    success: function(response) {
                        // Clear existing investments before loading new ones to avoid duplication.
                        $('#investmentList').empty();
                        $.each(response, function(index, investment) {
                            // Append each investment to the list. Adjust according to your response structure.
                            $('#investmentList').append('<li>' + investment.amount + ' - ' + investment.type + '</li>');
                        });
                    },
                    error: function() {
                        alert('Error loading investments.');
                    }
                });
            }

            // Optionally call loadInvestments on page load if you're implementing dynamic loading.
            // loadInvestments();
        });
    </script>
</head>
<body>
<h1>Investments</h1>
<form id="addInvestmentForm" action="${pageContext.request.contextPath}/investments" method="post">
    <label for="amount">Amount:</label>
    <input type="number" id="amount" name="amount" placeholder="Amount" required>
    <label for="type">Type:</label>
    <input type="text" id="type" name="type" placeholder="Type" required>
    <button type="submit">Add Investment</button>
</form>
<ul id="investmentList">
    <%
        List<Investment> investments = (List<Investment>) request.getAttribute("investments");
        for (Investment investment : investments) {
    %>
    <li><%= investment.getAmount() %> - <%= investment.getType() %></li>
    <% } %>
</ul>
</body>
</html>
