package com.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.DB.DBConnect;
import com.entity.Room;

public class BookingDAO {

    // Get only AVAILABLE rooms
    public List<Room> getAvailableRooms() {

        List<Room> list = new ArrayList<>();

        try {
            Connection con = DBConnect.getConnection();

            String sql =
                    "SELECT r.ROOM_ID, r.ROOM_NO, r.ROOM_TYPE, r.CAPACITY, " +
                    "r.NFL_RENT, r.GOVT_RENT, r.PRIVATE_RENT " +
                    "FROM PERSONNEL.GH_ROOM_MASTER r " +
                    "WHERE r.STATUS <> 'MAINTENANCE' " +
                    "AND NOT EXISTS ( " +
                    "   SELECT 1 FROM PERSONNEL.GH_BOOKING_MAIN b " +
                    "   WHERE b.ROOM_ID = r.ROOM_ID " +
                    "   AND b.STATUS IN ('BOOKED','CHECKED_IN') " +
                    ") " +
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
                list.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Save booking
    public boolean saveBooking(
            int roomId,
            String guestName,
            String mobile,
            String guestCategory,
            String guestType,
            String checkinDate,
            String createdBy) {

        boolean f = false;

        try {
            Connection con = DBConnect.getConnection();

            String sql =
              "INSERT INTO PERSONNEL.GH_BOOKING_MAIN " +
              "(BOOKING_ID, ROOM_ID, GUEST_NAME, MOBILE_NO, " +
              " GUEST_CATEGORY, GUEST_TYPE, CHECKIN_DATE, STATUS, CREATED_BY) " +
              "VALUES (GH_BOOKING_SEQ.NEXTVAL,?,?,?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, roomId);
            ps.setString(2, guestName);
            ps.setString(3, mobile);
            ps.setString(4, guestCategory);
            ps.setString(5, guestType);
            ps.setDate(6, java.sql.Date.valueOf(checkinDate));
            ps.setString(7, "BOOKED");
            ps.setString(8, createdBy);

            f = ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }
    
 // Check-in booking
    public boolean checkIn(int bookingId, Timestamp checkinTs) {

        try (Connection con = DBConnect.getConnection()) {

            String sql =
              "UPDATE PERSONNEL.GH_BOOKING_MAIN " +
              "SET CHECKIN_DATETIME=?, STATUS='CHECKED_IN' " +
              "WHERE BOOKING_ID=? AND STATUS='BOOKED'";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setTimestamp(1, checkinTs);
            ps.setInt(2, bookingId);

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkOut(int bookingId, Timestamp checkoutTs) {

        try (Connection con = DBConnect.getConnection()) {

            String sql =
              "UPDATE PERSONNEL.GH_BOOKING_MAIN " +
              "SET CHECKOUT_DATETIME=?, STATUS='CHECKED_OUT' " +
              "WHERE BOOKING_ID=? AND STATUS='CHECKED_IN'";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setTimestamp(1, checkoutTs);
            ps.setInt(2, bookingId);

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
