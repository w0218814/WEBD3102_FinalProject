package com.personalfinanceapp.controller.personalfinanceapp;

import com.personalfinanceapp.model.personalfinanceapp.Investment;
import com.personalfinanceapp.util.personalfinanceapp.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.json.JSONObject; // Ensure you have this library or similar for JSON handling

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
            String dateParam = request.getParameter("investmentDate"); // Parameter for investment date

            if (isInvalidInput(amountParam, typeParam, dateParam)) { // Check if any parameter is invalid
                jsonResponse.put("error", "Invalid input parameters");
                response.getWriter().write(jsonResponse.toString());
                return;
            }

            BigDecimal amount = new BigDecimal(amountParam);
            Date investmentDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateParam); // Parse the investment date

            Investment investment = new Investment();
            investment.setAmount(amount);
            investment.setType(typeParam);
            investment.setInvestmentDate(investmentDate); // Set the parsed date

            em.persist(investment);
            em.getTransaction().commit();

            jsonResponse.put("message", "Investment added successfully");
            jsonResponse.put("id", investment.getId());
            response.getWriter().write(jsonResponse.toString());
        } catch (Exception e) { // Catch-all for parsing, database, etc. errors
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            jsonResponse.put("error", "An error occurred processing your request");
            response.getWriter().write(jsonResponse.toString());
        } finally {
            em.close();
        }
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
        } finally {
            em.close();
        }
    }

    private boolean isInvalidInput(String amountParam, String typeParam, String dateParam) {
        // Updated to check for invalid date parameter as well
        return amountParam == null || amountParam.isEmpty() ||
                typeParam == null || typeParam.isEmpty() ||
                dateParam == null || dateParam.isEmpty();
    }

    private void handleException(EntityManager em, JSONObject jsonResponse, String errorMessage) {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        jsonResponse.put("error", errorMessage);
    }
}
