package com.personalfinanceapp.controller.personalfinanceapp;

import com.personalfinanceapp.model.personalfinanceapp.Investment;
import com.personalfinanceapp.util.personalfinanceapp.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.json.JSONObject;

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
            LocalDate investmentDate = LocalDate.parse(request.getParameter("investmentDate"), DateTimeFormatter.ISO_LOCAL_DATE);

            if (isInvalidInput(amountParam, typeParam, request.getParameter("investmentDate"))) {
                jsonResponse.put("error", "Invalid input parameters");
                response.getWriter().write(jsonResponse.toString());
                return;
            }

            BigDecimal amount = new BigDecimal(amountParam);
            Investment investment = new Investment();
            investment.setAmount(amount);
            investment.setType(typeParam);
            investment.setInvestmentDate(investmentDate);

            em.persist(investment);
            em.getTransaction().commit();

            // Added detailed information for successful addition.
            jsonResponse.put("message", "Investment added successfully");
            jsonResponse.put("id", investment.getId());
            jsonResponse.put("amount", investment.getAmount().toString());
            jsonResponse.put("type", investment.getType());
            jsonResponse.put("date", investment.getInvestmentDate().toString());
            response.getWriter().write(jsonResponse.toString());
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace(); // Enhanced error logging
            jsonResponse.put("error", "An error occurred processing your request: " + e.getMessage());
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
            e.printStackTrace(); // Enhanced error logging
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to retrieve investments: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    private boolean isInvalidInput(String amountParam, String typeParam, String dateParam) {
        return amountParam == null || amountParam.trim().isEmpty() ||
                typeParam == null || typeParam.trim().isEmpty() ||
                dateParam == null || dateParam.trim().isEmpty();
    }
}
