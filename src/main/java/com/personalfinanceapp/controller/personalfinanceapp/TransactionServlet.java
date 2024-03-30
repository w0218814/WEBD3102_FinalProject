package com.personalfinanceapp.controller.personalfinanceapp;

import com.personalfinanceapp.model.personalfinanceapp.Category;
import com.personalfinanceapp.model.personalfinanceapp.Tag;
import com.personalfinanceapp.model.personalfinanceapp.Transaction;
import com.personalfinanceapp.util.personalfinanceapp.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
            List<Tag> tags = em.createQuery("SELECT t FROM Tag t", Tag.class).getResultList();

            request.setAttribute("transactions", transactions);
            request.setAttribute("categories", categories);
            request.setAttribute("tags", tags);

            request.getRequestDispatcher("/WEB-INF/views/transactions.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        } finally {
            em.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        String action = request.getParameter("action");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        if ("delete".equals(action)) {
            deleteTransaction(request, response, em);
        } else {
            try {
                em.getTransaction().begin();
                String amountStr = request.getParameter("amount");
                String type = request.getParameter("type");
                String dateStr = request.getParameter("transactionDate");
                String description = request.getParameter("description");
                String categoryIdStr = request.getParameter("category");
                String[] tagIds = request.getParameterValues("tags");

                if (amountStr == null || type == null || dateStr == null || description == null || categoryIdStr == null) {
                    response.getWriter().write("{\"error\": \"Required fields are missing\"}");
                    return;
                }

                Transaction transaction = new Transaction();
                transaction.setAmount(new BigDecimal(amountStr));
                transaction.setType(type);
                transaction.setDescription(description);
                transaction.setTransactionDate(String.valueOf(new SimpleDateFormat("yyyy-MM-dd").parse(dateStr)));

                Category category = em.find(Category.class, Long.parseLong(categoryIdStr));
                transaction.setCategory(category);

                Set<Tag> transactionTags = new HashSet<>();
                if (tagIds != null) {
                    for (String tagId : tagIds) {
                        Tag tag = em.find(Tag.class, Long.parseLong(tagId));
                        if (tag != null) {
                            transactionTags.add(tag);
                        }
                    }
                }
                transaction.setTags(transactionTags);

                em.persist(transaction);
                em.getTransaction().commit();
                response.getWriter().write("{\"message\": \"Transaction added successfully\"}");
            } catch (ParseException e) {
                response.getWriter().write("{\"error\": \"Error parsing date\"}");
            } catch (Exception e) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                response.getWriter().write("{\"error\": \"An unexpected error occurred: " + e.getMessage() + "\"}");
            } finally {
                em.close();
            }
        }
    }

    private void deleteTransaction(HttpServletRequest request, HttpServletResponse response, EntityManager em) throws IOException {
        Long transactionId = Long.parseLong(request.getParameter("id"));
        try {
            em.getTransaction().begin();
            Transaction transaction = em.find(Transaction.class, transactionId);
            if (transaction != null) {
                em.remove(transaction);
                em.getTransaction().commit();
                response.getWriter().write("{\"message\": \"Transaction deleted successfully\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{\"error\": \"Transaction not found\"}");
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"An error occurred while deleting the transaction: " + e.getMessage() + "\"}");
        } finally {
            // It's assumed em.close() is called by the caller if needed
        }
    }
}
