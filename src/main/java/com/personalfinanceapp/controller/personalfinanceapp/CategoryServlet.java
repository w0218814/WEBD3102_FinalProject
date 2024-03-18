package com.personalfinanceapp.controller.personalfinanceapp;

import com.google.gson.Gson;
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

@WebServlet("/categories")
public class CategoryServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            em.getTransaction().begin();
            String categoryName = request.getParameter("name");
            if (categoryName == null || categoryName.trim().isEmpty()) {
                throw new ServletException("Category name cannot be null or empty.");
            }

            Category category = new Category();
            category.setName(categoryName);
            em.persist(category);
            em.getTransaction().commit();

            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                new Gson().toJson(category, response.getWriter());
            } else {
                response.sendRedirect(request.getContextPath() + "/categories.jsp");
            }
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ServletException("Error processing request", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = null;
        try {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            List<Category> categories = em.createQuery("SELECT c FROM Category c", Category.class).getResultList();

            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                new Gson().toJson(categories, response.getWriter());
            } else {
                request.setAttribute("categories", categories);
                request.getRequestDispatcher("/WEB-INF/views/categories.jsp").forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException("Error retrieving categories", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
