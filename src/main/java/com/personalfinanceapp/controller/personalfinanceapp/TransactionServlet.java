package com.personalfinanceapp.controller.personalfinanceapp;

import com.personalfinanceapp.model.personalfinanceapp.Category;
import com.personalfinanceapp.model.personalfinanceapp.Tag;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet("/transactions")
public class TransactionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            List<Transaction> transactions = em.createQuery("SELECT t FROM Transaction t", Transaction.class).getResultList();
            List<Category> categories = em.createQuery("SELECT c FROM Category c", Category.class).getResultList();
            List<Tag> tags = em.createQuery("SELECT t FROM Tag t", Tag.class).getResultList(); // Fetch tags for display in form

            request.setAttribute("transactions", transactions);
            request.setAttribute("categories", categories);
            request.setAttribute("tags", tags); // Add tags to request attributes

            request.getRequestDispatcher("/WEB-INF/views/transactions.jsp").forward(request, response);
        } catch (Exception e) {
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
            String[] tagIds = request.getParameterValues("tags"); // Assume tags are passed as an array of tag IDs

            if (amountStr == null || type == null || dateStr == null || description == null || tagIds == null) {
                throw new IllegalArgumentException("Required fields are missing");
            }

            Transaction transaction = new Transaction();
            transaction.setAmount(new BigDecimal(amountStr));
            transaction.setType(type);
            transaction.setDescription(description);
            transaction.setTransactionDate(new SimpleDateFormat("yyyy-MM-dd").parse(dateStr));

            Category category = em.find(Category.class, Integer.parseInt(request.getParameter("category")));
            transaction.setCategory(category);

            Set<Tag> transactionTags = new HashSet<>();
            for (String tagId : tagIds) {
                Tag tag = em.find(Tag.class, Long.parseLong(tagId));
                if (tag != null) {
                    transactionTags.add(tag);
                }
            }
            transaction.setTags(transactionTags);

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
