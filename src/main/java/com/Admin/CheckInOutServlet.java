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
    maxFileSize = 5 * 1024 * 1024,
    maxRequestSize = 10 * 1024 * 1024
)
public class CheckInOutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        BookingDAO dao = new BookingDAO();

        User user = (User) session.getAttribute("userObj");
        String createdBy = user != null ? user.getFullName() : "UNKNOWN";

        String mode = req.getParameter("mode");
        boolean result = false;

        if (mode == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request");
            return;
        }

        // ===== Common fields =====
        String address = req.getParameter("address");
        String idType = req.getParameter("idType");
        String idNumber = req.getParameter("idNumber");

        InputStream idPhoto = null;
        InputStream guestPhoto = null;

        // ===== FILE UPLOAD ONLY FOR THESE MODES =====
        if ("BOOKING".equals(mode) || "DIRECT".equals(mode)) {

            Part idPhotoPart = req.getPart("idPhoto");
            Part guestPhotoPart = req.getPart("guestPhoto");

            if (idPhotoPart != null && idPhotoPart.getSize() > 0) {
                idPhoto = idPhotoPart.getInputStream();
            }

            if (guestPhotoPart != null && guestPhotoPart.getSize() > 0) {
                guestPhoto = guestPhotoPart.getInputStream();
            }
        }

        // ===== BOOKING CHECK-IN =====
        if ("BOOKING".equals(mode)) {

            int bookingId = Integer.parseInt(req.getParameter("bookingId"));
            Timestamp checkinTs = Timestamp.valueOf(
                req.getParameter("checkinDatetime").replace("T", " ") + ":00"
            );

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

        // ===== DIRECT CHECK-IN =====
        else if ("DIRECT".equals(mode)) {

            int roomId = Integer.parseInt(req.getParameter("roomId"));
            String guestName = req.getParameter("guestName");
            String mobile = req.getParameter("mobile");
            String guestCategory = req.getParameter("guestCategory");
            String guestType = req.getParameter("guestType");

            Timestamp checkinTs = Timestamp.valueOf(
                req.getParameter("checkinDatetime").replace("T", " ") + ":00"
            );

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
