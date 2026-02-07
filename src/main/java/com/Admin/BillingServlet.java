package com.Admin;

import java.io.IOException;
import java.sql.Timestamp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.DAO.BillingDAO;
import com.entity.Billing;

@WebServlet("/billing")
public class BillingServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int bookingId = Integer.parseInt(req.getParameter("bookingId"));

        BillingDAO dao = new BillingDAO();
        
        
        String checkoutDt = req.getParameter("checkoutDatetime");
        Timestamp checkoutTs =
            Timestamp.valueOf(checkoutDt.replace("T", " ") + ":00");

        req.setAttribute("checkoutTs", checkoutTs);
        
        
        Billing bill = dao.getBillingData(bookingId);

        req.setAttribute("bill", bill);
        req.getRequestDispatcher("/admin/billing.jsp")
           .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        int bookingId = Integer.parseInt(req.getParameter("bookingId"));
        double otherCharges =
            Double.parseDouble(req.getParameter("otherCharges"));
        String paymentMode = req.getParameter("paymentMode");

        BillingDAO dao = new BillingDAO();
        
        String checkoutDt = req.getParameter("checkoutDatetime");
        Timestamp checkoutTs =
            Timestamp.valueOf(checkoutDt.replace("T", " ") + ":00");

        
        
        double breakfast =
        	    Double.parseDouble(req.getParameter("breakfast"));
        	double lunch =
        	    Double.parseDouble(req.getParameter("lunch"));
        	double dinner =
        	    Double.parseDouble(req.getParameter("dinner"));
        	

        	boolean result = dao.finalCheckout(
        		    bookingId,
        		    checkoutTs,
        		    breakfast,
        		    lunch,
        		    dinner,
        		    otherCharges,
        		    paymentMode
        		);



        HttpSession session = req.getSession();
        if (result) {
            session.setAttribute("succMsg",
                "Checkout completed successfully");
        } else {
            session.setAttribute("errorMsg",
                "Checkout failed");
        }

        resp.sendRedirect(req.getContextPath() +
            "/printBill?bookingId=" + bookingId);
    }
}
