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
        em.getTransaction().begin();
        // Implementation of saving or updating a budget
        em.getTransaction().commit();
    }
}
