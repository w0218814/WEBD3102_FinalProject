package com.personalfinanceapp.controller.personalfinanceapp;

import com.personalfinanceapp.model.personalfinanceapp.Category;
import com.personalfinanceapp.model.personalfinanceapp.Tag;
import com.personalfinanceapp.model.personalfinanceapp.Transaction;
import com.personalfinanceapp.util.personalfinanceapp.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.persistence.PersistenceException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet("/transactions")
public class TransactionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            // Fetch all transactions, categories, and tags to display on the transactions page.
            List<Transaction> transactions = em.createQuery("SELECT t FROM Transaction t", Transaction.class).getResultList();
            List<Category> categories = em.createQuery("SELECT c FROM Category c", Category.class).getResultList();
            List<Tag> tags = em.createQuery("SELECT t FROM Tag t", Tag.class).getResultList();

            // Add fetched data to request scope to be accessible on the JSP page.
            request.setAttribute("transactions", transactions);
            request.setAttribute("categories", categories);
            request.setAttribute("tags", tags);

            // Forward request to transactions.jsp view.
            request.getRequestDispatcher("/WEB-INF/views/transactions.jsp").forward(request, response);
        } catch (Exception e) {
            // If an exception occurs, send an error response.
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        } finally {
            // Ensure EntityManager is closed after operation.
            em.close();
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            deleteTransaction(request, response);
        } else {
            EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
            try {
                em.getTransaction().begin();
                // Parse and validate input parameters.
                String amountStr = request.getParameter("amount");
                String type = request.getParameter("type");
                String dateStr = request.getParameter("transactionDate");
                String description = request.getParameter("description");
                String[] tagIds = request.getParameterValues("tags");

                // If any of the parameters are missing, throw an exception.
                if (amountStr == null || type == null || dateStr == null || description == null || tagIds == null) {
                    throw new IllegalArgumentException("Required fields are missing");
                }

                // Create and set properties for the new transaction.
                Transaction transaction = new Transaction();
                transaction.setAmount(new BigDecimal(amountStr));
                transaction.setType(type);
                transaction.setDescription(description);
                transaction.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd").parse(dateStr));

                // Find and set the category for the new transaction.
                Category category = em.find(Category.class, Integer.parseInt(request.getParameter("category")));
                transaction.setCategory(category);

                // Create a set of tags for the new transaction.
                Set<Tag> transactionTags = new HashSet<>();
                for (String tagId : tagIds) {
                    Tag tag = em.find(Tag.class, Long.parseLong(tagId));
                    if (tag != null) {
                        transactionTags.add(tag);
                    }
                }
                transaction.setTags(transactionTags);

                // Persist the new transaction entity.
                em.persist(transaction);
                em.getTransaction().commit();

                // Redirect to the transactions page to show the updated list.
                response.sendRedirect(request.getContextPath() + "/transactions");
            } catch (IllegalArgumentException | ParseException e) {
                // Handle input validation exceptions and parse exceptions.
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Invalid input: " + e.getMessage());
                response.getWriter().flush();
                response.getWriter().close();
            } catch (PersistenceException e) {
                // Handle database-related exceptions.
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("Database error occurred: " + e.getMessage());
                response.getWriter().flush();
                response.getWriter().close();
            } catch (Exception e) {
                // Handle all other exceptions.
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                response.getWriter().write("An unexpected error occurred: " + e.getMessage());
                response.getWriter().flush();
                response.getWriter().close();
            } finally {
                // Ensure EntityManager is closed.
                em.close();
            }
        }
    }
    // Helper method to delete a transaction by its ID.
    private void deleteTransaction(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Convert the transaction ID from String to Long.
        Long transactionId = Long.parseLong(request.getParameter("id"));
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            // Begin transaction.
            em.getTransaction().begin();

            // Find the Transaction entity by its ID.
            Transaction transaction = em.find(Transaction.class, transactionId);
            if (transaction != null) {
                // If the transaction exists, remove it.
                em.remove(transaction);
                em.getTransaction().commit();
            } else {
                // If no transaction is found with the ID, send a 'not found' error.
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Transaction not found");
                return;
            }
        } catch (Exception e) {
            // If any exceptions occur, roll back the transaction.
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            // Send an error message to the client.
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("An error occurred while deleting the transaction: " + e.getMessage());
            response.getWriter().flush();
            response.getWriter().close();
        } finally {
            // Close the EntityManager.
            em.close();
        }

        // Redirect to the 'transactions' path to refresh the list after deletion.
        response.sendRedirect(request.getContextPath() + "/transactions");
    }
}
