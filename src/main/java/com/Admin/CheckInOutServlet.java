package com.Admin;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Timestamp;

import com.DAO.BookingDAO;

@WebServlet("/checkInOut")
public class CheckInOutServlet extends HttpServlet {

protected void doPost(HttpServletRequest req, HttpServletResponse resp)
throws IOException {

    String mode = req.getParameter("mode");
    BookingDAO dao = new BookingDAO();
    HttpSession session = req.getSession();
    boolean result = false;

    if ("BOOKING".equals(mode)) {

        int bookingId = Integer.parseInt(req.getParameter("bookingId"));
        String dt = req.getParameter("checkinDatetime");
        Timestamp ts = Timestamp.valueOf(dt.replace("T"," ") + ":00");

        result = dao.checkIn(bookingId, ts);

    } else if ("DIRECT".equals(mode)) {

        int roomId = Integer.parseInt(req.getParameter("roomId"));
        String guestName = req.getParameter("guestName");
        String mobile = req.getParameter("mobile");
        String category = req.getParameter("guestCategory");
        String type = req.getParameter("guestType");
        String dt = req.getParameter("checkinDatetime");
        Timestamp ts = Timestamp.valueOf(dt.replace("T"," ") + ":00");

        result = dao.directCheckIn(
            roomId, guestName, mobile, category, type, ts, "ADMIN"
        );

    } else if ("CHECKOUT".equals(mode)) {

        int bookingId = Integer.parseInt(req.getParameter("checkoutBookingId"));
        String dt = req.getParameter("checkoutDatetime");
        Timestamp ts = Timestamp.valueOf(dt.replace("T"," ") + ":00");

        result = dao.checkOut(bookingId, ts);
    }

    if (result) {
        session.setAttribute("succMsg", "Operation successful");
    } else {
        session.setAttribute("errorMsg", "Operation failed");
    }

    resp.sendRedirect("viewRooms");
}
}
