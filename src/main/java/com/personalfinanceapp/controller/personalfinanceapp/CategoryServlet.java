package com.personalfinanceapp.controller.personalfinanceapp;

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            if ("delete".equals(request.getParameter("action"))) {
                int categoryId = Integer.parseInt(request.getParameter("id"));
                em.getTransaction().begin();
                Category category = em.find(Category.class, categoryId);

                Long transactionCount = em.createQuery(
                                "SELECT COUNT(t.id) FROM TransactionsTable t WHERE t.category.id = :categoryId", Long.class)
                        .setParameter("categoryId", categoryId)
                        .getSingleResult();

                if (transactionCount > 0) {
                    request.setAttribute("errorMessage", "Category is in use and cannot be deleted.");
                } else if (category != null) {
                    em.remove(category);
                    em.getTransaction().commit();
                } else {
                    request.setAttribute("errorMessage", "Category not found.");
                }
                response.sendRedirect("categories");
                return;
            }

            List<Category> categories = em.createQuery("SELECT c FROM Category c", Category.class).getResultList();
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/WEB-INF/views/categories.jsp").forward(request, response);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            em.getTransaction().begin();
            String categoryName = request.getParameter("name");
            if (categoryName == null || categoryName.trim().isEmpty()) {
                throw new ServletException("Category name cannot be null or empty.");
            }

            Category category = new Category();
            category.setName(categoryName);
            em.persist(category);
            em.getTransaction().commit();

            response.sendRedirect("categories"); // Redirect to refresh the page
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
}
