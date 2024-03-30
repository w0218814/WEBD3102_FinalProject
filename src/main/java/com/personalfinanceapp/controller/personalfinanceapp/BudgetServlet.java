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
            // Fetch budgets
            List<Budget> budgets = em.createQuery("SELECT b FROM Budget b", Budget.class).getResultList();
            request.setAttribute("budgets", budgets);

            // Fetch categories for dropdown
            List<Category> categories = em.createQuery("SELECT c FROM Category c", Category.class).getResultList();
            request.setAttribute("categories", categories);

            request.getRequestDispatcher("/WEB-INF/views/budgets.jsp").forward(request, response);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            deleteBudget(request, em);
        } else {
            // Add or update budget
            saveOrUpdateBudget(request, em);
        }
        response.sendRedirect("budgets");
    }

    private void deleteBudget(HttpServletRequest request, EntityManager em) {
        int budgetId = Integer.parseInt(request.getParameter("id"));
        Budget budget = em.find(Budget.class, budgetId);
        em.getTransaction().begin();
        em.remove(budget);
        em.getTransaction().commit();
    }

    private void saveOrUpdateBudget(HttpServletRequest request, EntityManager em) {
        try {
            em.getTransaction().begin();

            Integer categoryId = Integer.parseInt(request.getParameter("category"));
            Double amount = Double.parseDouble(request.getParameter("amount"));
            String period = request.getParameter("period");

            Category category = em.find(Category.class, categoryId);
            Budget budget = new Budget();
            budget.setAmount(amount);
            budget.setPeriod(period);
            budget.setCategory(category);

            em.persist(budget);
            em.getTransaction().commit();

            // Add some form of success indicator, like setting a request attribute or session attribute
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace(); // Properly log the exception
            // Add some form of error handling, like setting a request attribute or session attribute
        }
    }
}