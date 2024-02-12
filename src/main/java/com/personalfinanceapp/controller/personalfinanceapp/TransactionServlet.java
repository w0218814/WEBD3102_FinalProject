package com.personalfinanceapp.controller.personalfinanceapp;

import com.personalfinanceapp.model.personalfinanceapp.Category;
import com.personalfinanceapp.model.personalfinanceapp.Transaction;
import com.personalfinanceapp.util.personalfinanceapp.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet("/transactions")
public class TransactionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            List<Transaction> transactions = em.createQuery("SELECT t FROM Transaction t", Transaction.class).getResultList();
            List<Category> categories = em.createQuery("SELECT c FROM Category c", Category.class).getResultList();

            request.setAttribute("transactions", transactions);
            request.setAttribute("categories", categories);

            request.getRequestDispatcher("/WEB-INF/views/transactions.jsp").forward(request, response);
        } catch (Exception e) {
            // Handle exceptions
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        } finally {
            em.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();

            String amountStr = request.getParameter("amount");
            String type = request.getParameter("type");
            String dateStr = request.getParameter("transactionDate");
            String description = request.getParameter("description");
            Integer categoryId = Integer.parseInt(request.getParameter("category"));

            if (amountStr == null || type == null || dateStr == null || description == null) {
                throw new IllegalArgumentException("Required fields are missing");
            }

            Transaction transaction = new Transaction();
            transaction.setAmount(new BigDecimal(amountStr));
            transaction.setType(type);
            transaction.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd").parse(dateStr));
            transaction.setDescription(description);

            Category category = em.find(Category.class, categoryId);
            transaction.setCategory(category);

            em.persist(transaction);
            em.getTransaction().commit();

            response.sendRedirect(request.getContextPath() + "/transactions");
        } catch (IllegalArgumentException | ParseException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid input: " + e.getMessage());
        } catch (PersistenceException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        } finally {
            em.close();
        }
    }
}
