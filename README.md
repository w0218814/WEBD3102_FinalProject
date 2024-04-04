# Final Assignment – Personal Finance App

**Presented by:** Steven Crosby  
**Presented to:** Simranjit Singh  
**Date:** April 5, 2024  
**Course:** WEBD3102  
**Group:** 4850  
**Assignment:** Final Assignment  
**Student ID:** W0218814  

## Index

- [Technology Used](#technology-used)
- [How the Files Fit Together](#how-the-files-fit-together)
- [Setting Up The Database Connection](#setting-up-the-database-connection)
- [How the Application Works](#how-the-application-works)
- [Conclusion](#conclusion)

## Technology Used

This application is built using J2EE, ideal for creating large-scale applications. Key components include:

- **Java EE:** Utilizing Servlets, JSP, JPA, and AJAX to create a dynamic and interactive web application for personal finance management.
- **GlassFish:** A server application that ensures our web application runs smoothly, handling the technical aspects of web hosting.

## How the Files Fit Together

### Servlets

- `BudgetServlet.java`: Manages CRUD operations for budgets.
- `CategoryServlet.java`: Facilitates CRUD operations for budget and transaction categories.
- `DashboardServlet.java`: Aggregates data for the dashboard, providing a financial overview.
- `InvestmentServlet.java`: Manages CRUD operations for investments.
- `TagServlet.java`: Handles tagging for transactions.
- `TransactionServlet.java`: Manages all transaction-related activities.

### JSP Pages

- `header.jsp`: Common header for consistent navigation across all pages.
- `budgets.jsp`: Interface for interacting with budget data.
- `categories.jsp`: Manages categories for budgets and transactions.
- `dashboard.jsp`: Provides a dashboard view with interactive charts and summaries.
- `investments.jsp`: Interface for managing investment records.
- `tags.jsp`: Manages tags for better transaction organization.
- `transactions.jsp`: Interface for transaction management.

### Model Entities

- `Budget.java`: Entity for budget with attributes like amount and period.
- `Category.java`: Entity for categorizing budgets and transactions.
- `Investment.java`: Entity for investment records.
- `Tag.java`: Entity for transaction tags.
- `Transaction.java`: Entity for financial transactions.

## Setting Up The Database Connection

Configured in GlassFish server, using JPA for Object-Relational Mapping (ORM). The `persistence.xml` file contains connection pool and resource definitions, ensuring efficient database communication.

## How the Application Works

Navigating to any section (e.g., `/dashboard`) triggers the corresponding servlet, which interacts with the database and passes data to JSP pages. These pages then render the dynamic content, utilizing AJAX for smooth, asynchronous updates.

## Conclusion

This personal finance app leverages Java EE technologies to provide a comprehensive tool for managing finances. It's designed for scalability, reliability, and ease of use, ensuring a smooth experience for users managing their personal finances.
