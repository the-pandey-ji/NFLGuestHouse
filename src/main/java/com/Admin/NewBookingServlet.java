package com.Admin;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

import com.DAO.BookingDAO;
import com.entity.Room;
import com.entity.User;

@WebServlet("/newBooking")
public class NewBookingServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        BookingDAO dao = new BookingDAO();
        List<Room> rooms = dao.getAvailableRooms();

        req.setAttribute("roomList", rooms);
        try {
            req.getRequestDispatcher("/booking/new_booking.jsp")
               .forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("userObj");

        BookingDAO dao = new BookingDAO();

        boolean f = dao.saveBooking(
                Integer.parseInt(req.getParameter("roomId")),
                req.getParameter("guestName"),
                req.getParameter("mobile"),
                req.getParameter("guestCategory"),
                req.getParameter("guestType"),
                req.getParameter("checkinDate"),
                user.getUsername()
        );

        if (f) {
            session.setAttribute("succMsg", "Booking created successfully");
        } else {
            session.setAttribute("errorMsg", "Booking failed");
        }

        resp.sendRedirect("newBooking");
    }
}
