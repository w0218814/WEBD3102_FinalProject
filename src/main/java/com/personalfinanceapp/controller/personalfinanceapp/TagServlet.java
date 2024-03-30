package com.personalfinanceapp.controller.personalfinanceapp;

import com.personalfinanceapp.model.personalfinanceapp.Tag;
import com.personalfinanceapp.util.personalfinanceapp.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/tags")
public class TagServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            String tagName = request.getParameter("name").trim();
            if (tagName.isEmpty()) {
                throw new ServletException("Tag name cannot be null or empty.");
            }

            em.getTransaction().begin();
            Long existingTagCount = em.createQuery("SELECT COUNT(t) FROM Tag t WHERE t.name = :name", Long.class)
                    .setParameter("name", tagName)
                    .getSingleResult();

            if (existingTagCount > 0) {
                throw new ServletException("Tag already exists.");
            }

            Tag tag = new Tag();
            tag.setName(tagName);
            em.persist(tag);
            em.getTransaction().commit();

            response.sendRedirect("tags");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new ServletException("Error processing request", e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            if ("delete".equals(request.getParameter("action"))) {
                int tagId = Integer.parseInt(request.getParameter("id"));
                em.getTransaction().begin();
                Tag tag = em.find(Tag.class, tagId);

                Long transactionCount = em.createQuery(
                                "SELECT COUNT(tt.transaction.id) FROM Transaction_Tags tt WHERE tt.tag.id = :tagId", Long.class)
                        .setParameter("tagId", tagId)
                        .getSingleResult();

                if (transactionCount > 0) {
                    request.setAttribute("errorMessage", "Tag is in use and cannot be deleted.");
                } else if (tag != null) {
                    em.remove(tag);
                    em.getTransaction().commit();
                } else {
                    request.setAttribute("errorMessage", "Tag not found.");
                }

                response.sendRedirect("tags");
                return;
            }

            List<Tag> tags = em.createQuery("SELECT t FROM Tag t", Tag.class).getResultList();
            request.setAttribute("tags", tags);
            request.getRequestDispatcher("/WEB-INF/views/tags.jsp").forward(request, response);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
