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
import java.util.List;

@WebServlet("/budgets")
public class BudgetServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            List<Budget> budgets = em.createQuery("SELECT b FROM Budget b JOIN FETCH b.category", Budget.class).getResultList();
            request.setAttribute("budgets", budgets);
            // Corrected the forward path to match the JSP file's location within WEB-INF/views
            request.getRequestDispatcher("/WEB-INF/views/budgets.jsp").forward(request, response);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            Double amount = Double.parseDouble(request.getParameter("amount"));
            String period = request.getParameter("period");
            Integer categoryId = Integer.parseInt(request.getParameter("category_id"));
            Category category = em.find(Category.class, categoryId);

            Budget budget = new Budget();
            budget.setAmount(amount);
            budget.setPeriod(period);
            budget.setCategory(category);

            em.persist(budget);
            em.getTransaction().commit();
            response.sendRedirect("budgets"); // This redirects to the servlet URL pattern, causing doGet to be called.
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
