package com.personalfinanceapp.controller.personalfinanceapp;

import com.google.gson.Gson;
import com.personalfinanceapp.model.personalfinanceapp.Budget;
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

    // doPost method remains unchanged

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            List<Budget> budgets = em.createQuery("SELECT b FROM Budget b JOIN FETCH b.category", Budget.class).getResultList();

            // Before serialization, manually set the categoryName for each Budget
            budgets.forEach(budget -> budget.setCategoryName(budget.getCategory().getName()));
            // Serialize the list to JSON, now including the categoryName
            String json = new Gson().toJson(budgets);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving budget list");
        } finally {
            em.close();
        }
    }

    // Additional methods as before
}
