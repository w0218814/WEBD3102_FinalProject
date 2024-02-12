package com.personalfinanceapp.controller.personalfinanceapp;

import com.personalfinanceapp.model.personalfinanceapp.Investment;
import com.personalfinanceapp.util.personalfinanceapp.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.json.JSONObject; // Assuming you have a JSON library

@WebServlet("/investments")
public class InvestmentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        JSONObject jsonResponse = new JSONObject();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            em.getTransaction().begin();

            String amountParam = request.getParameter("amount");
            String typeParam = request.getParameter("type");

            if (isInvalidInput(amountParam, typeParam)) {
                jsonResponse.put("error", "Invalid input parameters");
                response.getWriter().write(jsonResponse.toString());
                return;
            }

            Investment investment = new Investment();
            investment.setAmount(new BigDecimal(amountParam));
            investment.setType(typeParam);

            em.persist(investment);
            em.getTransaction().commit();

            jsonResponse.put("message", "Investment added successfully");
            jsonResponse.put("id", investment.getId());
        } catch (NumberFormatException e) {
            handleException(em, jsonResponse, "Invalid amount format");
        } catch (Exception e) {
            handleException(em, jsonResponse, "An error occurred processing your request");
            // Log exception: e.printStackTrace();
        } finally {
            em.close();
        }

        response.getWriter().write(jsonResponse.toString());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

        try {
            List<Investment> investments = em.createQuery("SELECT i FROM Investment i", Investment.class).getResultList();
            request.setAttribute("investments", investments);
            request.getRequestDispatcher("/WEB-INF/views/investments.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to retrieve investments");
            // Log exception: e.printStackTrace();
        } finally {
            em.close();
        }
    }

    private boolean isInvalidInput(String amountParam, String typeParam) {
        return amountParam == null || amountParam.isEmpty() || typeParam == null || typeParam.isEmpty();
    }

    private void handleException(EntityManager em, JSONObject jsonResponse, String errorMessage) {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        jsonResponse.put("error", errorMessage);
    }
}
