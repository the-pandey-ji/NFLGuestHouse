package com.Admin;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import com.DAO.BookingDAO;
import com.entity.User;

@WebServlet("/checkInOut")
@MultipartConfig(
    maxFileSize = 5 * 1024 * 1024,      // 5 MB
    maxRequestSize = 10 * 1024 * 1024
)
public class CheckInOutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        BookingDAO dao = new BookingDAO();

        // ===== Logged-in user FULL NAME =====
        User user = (User) session.getAttribute("userObj");
        String createdBy = user != null ? user.getFullName() : "UNKNOWN";

        String mode = req.getParameter("mode");
        boolean result = false;

        // ===== Common fields =====
        String address = req.getParameter("address");
        String idType = req.getParameter("idType");
        String idNumber = req.getParameter("idNumber");

        // ===== File upload =====
        Part idPhotoPart = req.getPart("idPhoto");
        Part guestPhotoPart = req.getPart("guestPhoto");

        InputStream idPhoto = idPhotoPart != null ? idPhotoPart.getInputStream() : null;
        InputStream guestPhoto = guestPhotoPart != null ? guestPhotoPart.getInputStream() : null;

        // ===== BOOKING CHECK-IN =====
        if ("BOOKING".equals(mode)) {

            int bookingId = Integer.parseInt(req.getParameter("bookingId"));
            String dt = req.getParameter("checkinDatetime");
            Timestamp checkinTs =
                Timestamp.valueOf(dt.replace("T", " ") + ":00");

            result = dao.checkInWithBooking(
                bookingId,
                address,
                idType,
                idNumber,
                idPhoto,
                guestPhoto,
                checkinTs,
                createdBy
            );

        }
        // ===== DIRECT CHECK-IN (WALK-IN) =====
        else if ("DIRECT".equals(mode)) {

            int roomId = Integer.parseInt(req.getParameter("roomId"));
            String guestName = req.getParameter("guestName");
            String mobile = req.getParameter("mobile");
            String guestCategory = req.getParameter("guestCategory");
            String guestType = req.getParameter("guestType");

            String dt = req.getParameter("checkinDatetime");
            Timestamp checkinTs =
                Timestamp.valueOf(dt.replace("T", " ") + ":00");

            result = dao.directCheckIn(
                roomId,
                guestName,
                mobile,
                address,
                guestCategory,
                guestType,
                idType,
                idNumber,
                idPhoto,
                guestPhoto,
                checkinTs,
                createdBy
            );
        }
        // ===== CHECK-OUT =====
		/*
		 * else if ("CHECKOUT".equals(mode)) {
		 * 
		 * int bookingId = Integer.parseInt(req.getParameter("bookingId")); String dt =
		 * req.getParameter("checkoutDatetime"); Timestamp checkoutTs =
		 * Timestamp.valueOf(dt.replace("T", " ") + ":00");
		 * 
		 * result = dao.checkOut(bookingId, checkoutTs); }
		 */
        // ===== CANCEL BOOKING =====
        else if ("CANCEL".equals(mode)) {

            int bookingId = Integer.parseInt(req.getParameter("bookingId"));
            result = dao.cancelBooking(bookingId);
        }

        if (result) {
            session.setAttribute("succMsg", "Operation completed successfully");
        } else {
            session.setAttribute("errorMsg", "Operation failed");
        }

        resp.sendRedirect(req.getContextPath() + "/viewRooms");
    }
}
