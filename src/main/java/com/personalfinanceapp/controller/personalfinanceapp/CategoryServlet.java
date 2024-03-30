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
            String action = request.getParameter("action");
            if ("delete".equals(action)) {
                int categoryId = Integer.parseInt(request.getParameter("id"));
                em.getTransaction().begin();
                Category category = em.find(Category.class, categoryId);
                Long transactionsCount = 0L; // Declare outside of the if block
                Long budgetsCount = 0L; // Declare outside of the if block
                if (category != null) {
                    // Checking for dependencies before attempting to delete
                    transactionsCount = em.createQuery("SELECT COUNT(t.id) FROM Transaction t WHERE t.category.id = :categoryId", Long.class)
                            .setParameter("categoryId", categoryId)
                            .getSingleResult();
                    budgetsCount = em.createQuery("SELECT COUNT(b.id) FROM Budget b WHERE b.category.id = :categoryId", Long.class)
                            .setParameter("categoryId", categoryId)
                            .getSingleResult();
                    if (transactionsCount == 0 && budgetsCount == 0) {
                        em.remove(category);
                        em.getTransaction().commit();
                        request.setAttribute("successMessage", "Category deleted successfully.");
                    } else {
                        em.getTransaction().rollback();
                        request.setAttribute("errorMessage", "Category is in use and cannot be deleted.");
                    }
                } else {
                    request.setAttribute("errorMessage", "Category not found.");
                }
                // Redirecting or forwarding based on the operation result
                response.sendRedirect("categories?success=" + (category != null && transactionsCount == 0 && budgetsCount == 0));
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
            String categoryName = request.getParameter("name");
            if (categoryName == null || categoryName.trim().isEmpty()) {
                request.setAttribute("errorMessage", "Category name cannot be null or empty.");
                // Redirecting back to doGet to reuse the category listing logic
                doGet(request, response);
                return;
            }

            em.getTransaction().begin();
            Category category = new Category();
            category.setName(categoryName);
            em.persist(category);
            em.getTransaction().commit();

            response.sendRedirect("categories");
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            request.setAttribute("errorMessage", "Error processing request: " + e.getMessage());
            doGet(request, response);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
