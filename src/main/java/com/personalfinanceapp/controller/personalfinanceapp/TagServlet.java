package com.personalfinanceapp.controller.personalfinanceapp;

import com.personalfinanceapp.model.personalfinanceapp.Tag;
import com.personalfinanceapp.util.personalfinanceapp.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;
import org.json.JSONObject; // Assuming you have a JSON library like org.json

@WebServlet("/tags")
public class TagServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        JSONObject jsonResponse = new JSONObject();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            String tagName = request.getParameter("name").trim();
            if (tagName.isEmpty()) {
                jsonResponse.put("error", "Tag name cannot be null or empty.");
                response.getWriter().write(jsonResponse.toString());
                return;
            }

            em.getTransaction().begin();
            // Use a single query to check if the tag exists and create if not
            Long existingTagCount = em.createQuery("SELECT COUNT(t) FROM Tag t WHERE t.name = :name", Long.class)
                    .setParameter("name", tagName)
                    .getSingleResult();

            if (existingTagCount > 0) {
                jsonResponse.put("error", "Tag already exists.");
                response.getWriter().write(jsonResponse.toString());
                em.getTransaction().rollback(); // Explicit rollback not strictly necessary here but included for clarity
                return;
            }

            Tag tag = new Tag();
            tag.setName(tagName);
            em.persist(tag);
            em.getTransaction().commit();

            jsonResponse.put("message", "Tag added successfully");
            jsonResponse.put("id", tag.getId());
            response.getWriter().write(jsonResponse.toString());
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            jsonResponse.put("error", "Error processing request: " + e.getMessage());
            response.getWriter().write(jsonResponse.toString());
        } finally {
            em.close();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            List<Tag> tags = em.createQuery("SELECT t FROM Tag t", Tag.class).getResultList();
            request.setAttribute("tags", tags);
            request.getRequestDispatcher("/WEB-INF/views/tags.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving tags: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}
