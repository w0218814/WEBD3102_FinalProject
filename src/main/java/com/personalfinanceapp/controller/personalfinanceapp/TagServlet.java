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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            String action = request.getParameter("action");
            if ("delete".equals(action)) {
                int tagId = Integer.parseInt(request.getParameter("id"));
                em.getTransaction().begin();
                Tag tag = em.find(Tag.class, tagId);
                if (tag != null) {
                    em.remove(tag);
                    em.getTransaction().commit();
                    response.sendRedirect("tags?success=true");
                } else {
                    response.sendRedirect("tags?success=false");
                }
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            String tagName = request.getParameter("name");
            if (tagName == null || tagName.trim().isEmpty()) {
                response.sendRedirect("tags?success=false");
                return;
            }

            em.getTransaction().begin();
            Tag tag = new Tag();
            tag.setName(tagName);
            em.persist(tag);
            em.getTransaction().commit();

            response.sendRedirect("tags?success=true");
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            response.sendRedirect("tags?success=false");
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
