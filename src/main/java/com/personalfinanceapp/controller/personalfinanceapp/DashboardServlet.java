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

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            // Assuming you're using JPA to interact with your database
            // Fetch budgets
            List<Budget> budgets = em.createQuery("SELECT b FROM Budget b", Budget.class).getResultList();
            request.setAttribute("budgets", budgets);

            // Fetch categories if needed
            // List<Category> categories = em.createQuery("SELECT c FROM Category c", Category.class).getResultList();
            // request.setAttribute("categories", categories);

            // Forward to JSP
            request.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            // Proper error handling
            e.printStackTrace(); // Log it, consider logging framework
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server Error");
        } finally {
            em.close();
        }
    }
}
