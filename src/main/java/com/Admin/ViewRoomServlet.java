package com.Admin;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import com.DAO.RoomDAO;
import com.entity.Room;

@WebServlet("/viewRooms")
public class ViewRoomServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        RoomDAO dao = new RoomDAO();
        List<Room> list = dao.getRoomMasterStatus();

        req.setAttribute("roomList", list);
        try {
            req.getRequestDispatcher("/admin/room_master.jsp")
               .forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
