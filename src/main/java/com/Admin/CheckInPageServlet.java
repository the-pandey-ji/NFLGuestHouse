package com.Admin;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.DAO.BookingDAO;
import com.entity.Booking;
import com.entity.Room;

@WebServlet("/checkin")
public class CheckInPageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        BookingDAO dao = new BookingDAO();

        // BOOKED bookings for check-in
        List<Booking> bookedList = dao.getBookedBookings();

        // AVAILABLE rooms for direct check-in
        List<Room> availableRooms = dao.getAvailableRooms();

        req.setAttribute("bookedList", bookedList);
        req.setAttribute("availableRooms", availableRooms);

        req.getRequestDispatcher(
            "/booking/checkin_checkout.jsp"
        ).forward(req, resp);
    }
}
