package com.personalfinanceapp.controller.personalfinanceapp;

import com.personalfinanceapp.model.personalfinanceapp.Budget;
import com.personalfinanceapp.util.personalfinanceapp.JPAUtil;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
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

            if (amountStr == null || period == null) {
                // Handle null parameters
                throw new ServletException("Amount and period parameters are required.");
            }

            BigDecimal amount;
            try {
                amount = new BigDecimal(amountStr);
            } catch (NumberFormatException e) {
                // Handle invalid number format
                throw new ServletException("Invalid format for amount.");
            }

            Budget budget = new Budget();
            budget.setAmount(amount);
            budget.setPeriod(period);
            // Additional fields and validation can be added here

            em.persist(budget);
            em.getTransaction().commit();

            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                // This is an AJAX request
                response.setContentType("application/json");
                response.getWriter().write(convertBudgetToJson(budget).toString());
            } else {
                // Regular request
                response.sendRedirect(request.getContextPath() + "/budgets");
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            // Log the exception and handle it
            e.printStackTrace();
            // Optionally, forward to an error page or return an error response
        } finally {
            em.close();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            List<Budget> budgets = em.createQuery("SELECT b FROM Budget b", Budget.class).getResultList();

            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                // This is an AJAX request
                response.setContentType("application/json");
                JsonArray jsonArray = convertBudgetListToJsonArray(budgets);
                response.getWriter().write(jsonArray.toString());
            } else {
                // Regular request
                request.setAttribute("budgets", budgets);
                request.getRequestDispatcher("/WEB-INF/views/budgets.jsp").forward(request, response);
            }
        } catch (Exception e) {
            // Log and handle the exception
            e.printStackTrace();
            // Optionally, forward to an error page or return an error response
        } finally {
            em.close();
        }
    }

    private JsonArray convertBudgetListToJsonArray(List<Budget> budgets) {
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (Budget budget : budgets) {
            arrayBuilder.add(convertBudgetToJson(budget));
        }
        return arrayBuilder.build();
    }

    private JsonObject convertBudgetToJson(Budget budget) {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("id", budget.getId());
        builder.add("amount", budget.getAmount().toString());
        builder.add("period", budget.getPeriod());
        // Add other budget fields as needed
        return builder.build();
    }
}
