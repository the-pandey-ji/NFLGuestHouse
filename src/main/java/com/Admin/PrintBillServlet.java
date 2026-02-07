package com.Admin;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.DAO.PrintBillDAO;
import com.entity.PrintBill;

@WebServlet("/printBill")
public class PrintBillServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int bookingId = Integer.parseInt(req.getParameter("bookingId"));

        PrintBillDAO dao = new PrintBillDAO();
        PrintBill bill = dao.getPrintBill(bookingId);

        req.setAttribute("bill", bill);
        req.getRequestDispatcher("/admin/print_bill.jsp")
           .forward(req, resp);
    }
}
