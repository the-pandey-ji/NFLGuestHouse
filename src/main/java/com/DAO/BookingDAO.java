package com.DAO;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.DB.DBConnect;
import com.entity.Booking;
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
    
    
    public List<Booking> getBookedBookings() {

        List<Booking> list = new ArrayList<>();

        try {
            Connection con = DBConnect.getConnection();

            String sql =
              "SELECT BOOKING_ID, GUEST_NAME, CREATED_DATE " +
              "FROM PERSONNEL.GH_BOOKING_MAIN " +
              "WHERE STATUS = 'BOOKED' " +
              "ORDER BY CREATED_DATE";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Booking b = new Booking();
                b.setBookingId(rs.getInt("BOOKING_ID"));
                b.setGuestName(rs.getString("GUEST_NAME"));
                b.setCreatedDate(rs.getTimestamp("CREATED_DATE"));

                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    
    public boolean cancelBooking(int bookingId) {

        try (Connection con = DBConnect.getConnection()) {

            String sql =
              "UPDATE PERSONNEL.GH_BOOKING_MAIN " +
              "SET STATUS='CANCELLED' " +
              "WHERE BOOKING_ID=? AND STATUS='BOOKED'";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, bookingId);

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


  

    public boolean checkOut(int bookingId, Timestamp checkoutTs) {

        try (Connection con = DBConnect.getConnection()) {

            con.setAutoCommit(false);

            String bookingSql =
              "UPDATE PERSONNEL.GH_BOOKING_MAIN " +
              "SET CHECKOUT_DATETIME=?, STATUS='CHECKED_OUT' " +
              "WHERE BOOKING_ID=? AND STATUS='CHECKED_IN'";

            PreparedStatement ps1 = con.prepareStatement(bookingSql);
            ps1.setTimestamp(1, checkoutTs);
            ps1.setInt(2, bookingId);

            int a = ps1.executeUpdate();

            String roomSql =
              "UPDATE PERSONNEL.GH_ROOM_MASTER SET STATUS='AVAILABLE' " +
              "WHERE ROOM_ID = (" +
              " SELECT ROOM_ID FROM PERSONNEL.GH_BOOKING_MAIN WHERE BOOKING_ID=?)";

            PreparedStatement ps2 = con.prepareStatement(roomSql);
            ps2.setInt(1, bookingId);

            int b = ps2.executeUpdate();

            if (a == 1 && b == 1) {
                con.commit();
                return true;
            }
            con.rollback();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    

    /* ================= CHECK-IN WITH BOOKING ================= */
    public boolean checkInWithBooking(
            int bookingId,
            String address,
            String idType,
            String idNumber,
            InputStream idPhoto,
            InputStream guestPhoto,
            Timestamp checkinTs,
            String createdBy) {

        try (Connection con = DBConnect.getConnection()) {

            String sql =
              "UPDATE PERSONNEL.GH_BOOKING_MAIN SET " +
              "ADDRESS=?, ID_TYPE=?, ID_NUMBER=?, " +
              "ID_PHOTO=?, GUEST_PHOTO=?, " +
              "CHECKIN_DATETIME=?, STATUS='CHECKED_IN' , CREATED_BY=? " +
              "WHERE BOOKING_ID=? AND STATUS='BOOKED'";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, address);
            ps.setString(2, idType);
            ps.setString(3, idNumber);
            ps.setBlob(4, idPhoto);
            ps.setBlob(5, guestPhoto);
            ps.setTimestamp(6, checkinTs);
            ps.setString(7, createdBy);
            ps.setInt(8, bookingId);
            

            return ps.executeUpdate() == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean directCheckIn(
            int roomId,
            String guestName,
            String mobile,
            String address,
            String guestCategory,
            String guestType,
            String idType,
            String idNumber,
            InputStream idPhoto,
            InputStream guestPhoto,
            Timestamp checkinTs,
            String createdBy) {
    	
		/*
		 * System.out.println("Direct Check-In: " + guestName + ", Room ID: " + roomId);
		 * System.out.println("ID Type: " + idType + ", ID Number: " + idNumber);
		 * System.out.println("Created by: " + createdBy);
		 */
        try {
            Connection con = DBConnect.getConnection();

            String sql =
              "INSERT INTO PERSONNEL.GH_BOOKING_MAIN " +
              "(BOOKING_ID, ROOM_ID, GUEST_NAME, MOBILE_NO, ADDRESS, " +
              "GUEST_CATEGORY, GUEST_TYPE, ID_TYPE, ID_NUMBER, " +
              "ID_PHOTO, GUEST_PHOTO,CHECKIN_DATE, CHECKIN_DATETIME, STATUS, CREATED_BY) " +
              "VALUES (GH_BOOKING_SEQ.NEXTVAL,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, roomId);
            ps.setString(2, guestName);
            ps.setString(3, mobile);
            ps.setString(4, address);
            ps.setString(5, guestCategory);
            ps.setString(6, guestType);
            ps.setString(7, idType);
            ps.setString(8, idNumber);
            ps.setBlob(9, idPhoto);
            ps.setBlob(10, guestPhoto);
            ps.setDate(11, new java.sql.Date(checkinTs.getTime()));

            ps.setTimestamp(12, checkinTs);
            ps.setString(13, "CHECKED_IN");
            ps.setString(14, createdBy);

            return ps.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    
    
    public boolean generateBill(
            int bookingId,
            double breakfast,
            double lunch,
            double dinner,
            String paymentMode) {

        try (Connection con = DBConnect.getConnection()) {

            // 1️⃣ Fetch booking + room + rent
            String fetchSql =
                "SELECT b.CHECKIN_DATETIME, b.CHECKOUT_DATETIME, b.GUEST_CATEGORY, " +
                "r.NFL_RENT, r.GOVT_RENT, r.PRIVATE_RENT " +
                "FROM PERSONNEL.GH_BOOKING_MAIN b " +
                "JOIN PERSONNEL.GH_ROOM_MASTER r ON b.ROOM_ID = r.ROOM_ID " +
                "WHERE b.BOOKING_ID=?";

            PreparedStatement ps1 = con.prepareStatement(fetchSql);
            ps1.setInt(1, bookingId);
            ResultSet rs = ps1.executeQuery();

            if (!rs.next()) return false;

            Timestamp in = rs.getTimestamp("CHECKIN_DATETIME");
            Timestamp out = rs.getTimestamp("CHECKOUT_DATETIME");
            String category = rs.getString("GUEST_CATEGORY");

            double rent;
            if ("NFL".equals(category)) {
                rent = rs.getDouble("NFL_RENT");
            } else if ("GOVT".equals(category)) {
                rent = rs.getDouble("GOVT_RENT");
            } else {
                rent = rs.getDouble("PRIVATE_RENT");
            }

            // 2️⃣ Calculate days
            long hours =
                (out.getTime() - in.getTime()) / (1000 * 60 * 60);
            long days = (long) Math.ceil(hours / 24.0);
            if (days == 0) days = 1;

            double roomTotal = days * rent;
            double foodTotal = breakfast + lunch + dinner;
            double grandTotal = roomTotal + foodTotal;

            // 3️⃣ Update billing
            String updateSql =
                "UPDATE PERSONNEL.GH_BOOKING_MAIN SET " +
                "NO_OF_DAYS=?, ROOM_CHARGE_TOTAL=?, FOOD_TOTAL=?, " +
                "GRAND_TOTAL=?, PAYMENT_MODE=? " +
                "WHERE BOOKING_ID=?";

            PreparedStatement ps2 = con.prepareStatement(updateSql);
            ps2.setLong(1, days);
            ps2.setDouble(2, roomTotal);
            ps2.setDouble(3, foodTotal);
            ps2.setDouble(4, grandTotal);
            ps2.setString(5, paymentMode);
            ps2.setInt(6, bookingId);

            return ps2.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    
    
    public List<Booking> getCheckedInBookings() {

        List<Booking> list = new ArrayList<>();

        try {
            Connection con = DBConnect.getConnection();

            String sql =
              "SELECT b.BOOKING_ID, b.GUEST_NAME, r.ROOM_NO, b.CHECKIN_DATETIME " +
              "FROM PERSONNEL.GH_BOOKING_MAIN b " +
              "JOIN PERSONNEL.GH_ROOM_MASTER r ON b.ROOM_ID = r.ROOM_ID " +
              "WHERE b.STATUS = 'CHECKED_IN' " +
              "ORDER BY b.CHECKIN_DATETIME";

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Booking b = new Booking();
                b.setBookingId(rs.getInt("BOOKING_ID"));
                b.setGuestName(rs.getString("GUEST_NAME"));
                b.setRoomNo(rs.getString("ROOM_NO"));
                b.setCheckinDatetime(rs.getTimestamp("CHECKIN_DATETIME"));
                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    	
    

}
