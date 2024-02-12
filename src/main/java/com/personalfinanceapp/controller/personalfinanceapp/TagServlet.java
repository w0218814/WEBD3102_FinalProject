package com.personalfinanceapp.controller.personalfinanceapp;

import com.personalfinanceapp.model.personalfinanceapp.Tag;
import com.personalfinanceapp.util.personalfinanceapp.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/tags")
public class TagServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            String tagName = request.getParameter("name");
            if (tagName == null || tagName.trim().isEmpty()) {
                throw new IllegalArgumentException("Tag name cannot be null or empty.");
            }

            em.getTransaction().begin();
            List<Tag> existingTags = em.createQuery("SELECT t FROM Tag t WHERE t.name = :name", Tag.class)
                    .setParameter("name", tagName)
                    .getResultList();

            if (!existingTags.isEmpty()) {
                throw new IllegalArgumentException("Tag already exists.");
            }

            Tag tag = new Tag();
            tag.setName(tagName);
            em.persist(tag);
            em.getTransaction().commit();

            response.getWriter().write("{\"message\": \"Tag added successfully\"}");
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"Error processing request\"}");
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
