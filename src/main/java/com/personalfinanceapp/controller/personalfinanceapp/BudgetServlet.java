package com.personalfinanceapp.controller.personalfinanceapp;

import com.personalfinanceapp.model.personalfinanceapp.Budget;
import com.personalfinanceapp.model.personalfinanceapp.Category;
import com.personalfinanceapp.util.personalfinanceapp.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/budgets")
public class BudgetServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            String amountStr = request.getParameter("amount");
            String period = request.getParameter("period");
            String categoryIdStr = request.getParameter("category_id"); // Assuming category_id is provided in the request

            if (amountStr == null || period == null || categoryIdStr == null) {
                throw new ServletException("Amount, period, and category_id parameters are required.");
            }

            BigDecimal amount = new BigDecimal(amountStr);
            Long categoryId = Long.parseLong(categoryIdStr);
            Category category = em.find(Category.class, categoryId); // Fetch the Category entity

            Budget budget = new Budget();
            budget.setAmount(amount);
            budget.setPeriod(period);
            budget.setCategory(category); // Set the Category entity

            em.persist(budget);
            em.getTransaction().commit();

            response.sendRedirect(request.getContextPath() + "/budgets.jsp"); // Redirect or handle the response as needed
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ServletException("Error processing budget request", e);
        } finally {
            em.close();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            List<Budget> budgets = em.createQuery("SELECT b FROM Budget b", Budget.class).getResultList();
            request.setAttribute("budgets", budgets);
            request.getRequestDispatcher("/WEB-INF/views/budgets.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException("Error retrieving budget list", e);
        } finally {
            em.close();
        }
    }
}
