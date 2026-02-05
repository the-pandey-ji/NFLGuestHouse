package com.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.DB.DBConnect;
import com.entity.Room;

public class RoomDAO {

    public List<Room> getAllRooms() {

        List<Room> list = new ArrayList<>();

        try {
            Connection con = DBConnect.getConnection();

            String sql = "SELECT * FROM PERSONNEL.GH_ROOM_MASTER ORDER BY TO_NUMBER(REGEXP_SUBSTR(ROOM_NO, '^[0-9]+')), ROOM_NO";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Room r = new Room();

                r.setRoomId(rs.getInt("ROOM_ID"));
                r.setRoomNo(rs.getString("ROOM_NO"));
                r.setRoomType(rs.getString("ROOM_TYPE"));
                r.setCapacity(rs.getInt("CAPACITY"));
                r.setStatus(rs.getString("STATUS"));

                r.setNflRent(rs.getDouble("NFL_RENT"));
                r.setGovtRent(rs.getDouble("GOVT_RENT"));
                r.setPrivateRent(rs.getDouble("PRIVATE_RENT"));

                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
//System.out.println("Room List Size: " + list.size());
        return list;
    }
    
   
    public List<Room> getRoomMasterStatus() {

        List<Room> list = new ArrayList<>();

        try {
            Connection con = DBConnect.getConnection();

            String sql =
                "SELECT r.ROOM_ID, r.ROOM_NO, r.ROOM_TYPE, r.CAPACITY, " +
                "r.NFL_RENT, r.GOVT_RENT, r.PRIVATE_RENT, " +
                "CASE " +
                " WHEN r.STATUS='MAINTENANCE' THEN 'MAINTENANCE' " +
                " WHEN EXISTS (SELECT 1 FROM PERSONNEL.GH_BOOKING_MAIN b " +
                "              WHERE b.ROOM_ID=r.ROOM_ID AND b.STATUS='CHECKED_IN') THEN 'OCCUPIED' " +
                " WHEN EXISTS (SELECT 1 FROM PERSONNEL.GH_BOOKING_MAIN b " +
                "              WHERE b.ROOM_ID=r.ROOM_ID AND b.STATUS='BOOKED') THEN 'BOOKED' " +
                " ELSE 'AVAILABLE' END FINAL_STATUS, " +

                "(SELECT b.BOOKING_ID FROM PERSONNEL.GH_BOOKING_MAIN b " +
                " WHERE b.ROOM_ID=r.ROOM_ID AND b.STATUS IN ('BOOKED','CHECKED_IN') " +
                " AND ROWNUM=1) BOOKING_ID, " +

                "(SELECT b.GUEST_NAME FROM PERSONNEL.GH_BOOKING_MAIN b " +
                " WHERE b.ROOM_ID=r.ROOM_ID AND b.STATUS IN ('BOOKED','CHECKED_IN') " +
                " AND ROWNUM=1) GUEST_NAME, " +

                "(SELECT b.CHECKIN_DATETIME FROM PERSONNEL.GH_BOOKING_MAIN b " +
                " WHERE b.ROOM_ID=r.ROOM_ID AND b.STATUS='CHECKED_IN' " +
                " AND ROWNUM=1) CHECKIN_DATETIME " +

                "FROM PERSONNEL.GH_ROOM_MASTER r " +
                "ORDER BY TO_NUMBER(REGEXP_SUBSTR(r.ROOM_NO,'^[0-9]+')), r.ROOM_NO";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Room r = new Room();
                r.setRoomId(rs.getInt("ROOM_ID"));
                r.setRoomNo(rs.getString("ROOM_NO"));
                r.setRoomType(rs.getString("ROOM_TYPE"));
                r.setCapacity(rs.getInt("CAPACITY"));
                r.setNflRent(rs.getDouble("NFL_RENT"));
                r.setGovtRent(rs.getDouble("GOVT_RENT"));
                r.setPrivateRent(rs.getDouble("PRIVATE_RENT"));
                r.setStatus(rs.getString("FINAL_STATUS"));
                r.setBookingId(rs.getInt("BOOKING_ID"));
                r.setGuestName(rs.getString("GUEST_NAME"));
                r.setCheckinDatetime(rs.getTimestamp("CHECKIN_DATETIME"));

                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
