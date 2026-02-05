package com.Admin;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Timestamp;

import com.DAO.BookingDAO;

@WebServlet("/checkInOut")
public class CheckInOutServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        int bookingId = Integer.parseInt(req.getParameter("bookingId"));
        String action = req.getParameter("action");

        BookingDAO dao = new BookingDAO();
        boolean result = false;

        if ("checkin".equals(action)) {
            String dt = req.getParameter("checkinDatetime");
            Timestamp checkinTs = Timestamp.valueOf(dt.replace("T", " ") + ":00");
            result = dao.checkIn(bookingId, checkinTs);
        }

        if ("checkout".equals(action)) {
            String dt = req.getParameter("checkoutDatetime");
            Timestamp checkoutTs = Timestamp.valueOf(dt.replace("T", " ") + ":00");
            result = dao.checkOut(bookingId, checkoutTs);
        }

        resp.sendRedirect("checkinList");
    }
}
