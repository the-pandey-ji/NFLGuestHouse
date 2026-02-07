package com.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.DB.DBConnect;
import com.entity.Billing;
import com.entity.Booking;

public class BillingDAO {

    /* ================= FETCH BILL DATA ================= */
    public Billing getBillingData(int bookingId) {

        Billing b = new Billing();

        try {
            Connection con = DBConnect.getConnection();

            String sql =
              "SELECT b.BOOKING_ID, b.GUEST_NAME, r.ROOM_NO, " +
              "b.CHECKIN_DATETIME, r.NFL_RENT " +
              "FROM PERSONNEL.GH_BOOKING_MAIN b " +
              "JOIN PERSONNEL.GH_ROOM_MASTER r " +
              "ON b.ROOM_ID = r.ROOM_ID " +
              "WHERE b.BOOKING_ID=?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, bookingId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                b.setBookingId(rs.getInt("BOOKING_ID"));
                b.setGuestName(rs.getString("GUEST_NAME"));
                b.setRoomNo(rs.getString("ROOM_NO"));
                b.setCheckinDatetime(rs.getTimestamp("CHECKIN_DATETIME"));
                b.setRoomRent(rs.getDouble("NFL_RENT"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    /* ================= FINAL CHECKOUT ================= */
    public boolean finalCheckout(
            int bookingId,
            Timestamp checkoutTs,
            double breakfast,
            double lunch,
            double dinner,
            double otherCharges,
            String paymentMode) {

        try {
            Connection con = DBConnect.getConnection();
            con.setAutoCommit(false);

            String sql =
            		  "UPDATE PERSONNEL.GH_BOOKING_MAIN SET " +

            		  "NO_OF_DAYS = TRUNC(CAST(? AS DATE) - CAST(CHECKIN_DATETIME AS DATE)) + 1, " +

            		  "BREAKFAST_CHARGE = ?, " +
            		  "LUNCH_CHARGE = ?, " +
            		  "DINNER_CHARGE = ?, " +
            		  "FOOD_TOTAL = (? + ? + ?), " +

            		  "ROOM_CHARGE_TOTAL = " +
            		  "(TRUNC(CAST(? AS DATE) - CAST(CHECKIN_DATETIME AS DATE)) + 1) * " +
            		  "(SELECT NFL_RENT FROM PERSONNEL.GH_ROOM_MASTER " +
            		  " WHERE ROOM_ID = GH_BOOKING_MAIN.ROOM_ID), " +

            		  "OTHER_CHARGES = ?, " +

            		  "GRAND_TOTAL = " +
            		  "((TRUNC(CAST(? AS DATE) - CAST(CHECKIN_DATETIME AS DATE)) + 1) * " +
            		  "(SELECT NFL_RENT FROM PERSONNEL.GH_ROOM_MASTER " +
            		  " WHERE ROOM_ID = GH_BOOKING_MAIN.ROOM_ID)) " +
            		  "+ (? + ? + ?) + ?, " +

            		  "PAYMENT_MODE = ?, " +
            		  "CHECKOUT_DATETIME = ?, " +
            		  "STATUS = 'CHECKED_OUT' " +
            		  "WHERE BOOKING_ID = ?";


            PreparedStatement ps = con.prepareStatement(sql);
            int i = 1;

            ps.setTimestamp(i++, checkoutTs);

            ps.setDouble(i++, breakfast);
            ps.setDouble(i++, lunch);
            ps.setDouble(i++, dinner);

            ps.setDouble(i++, breakfast);
            ps.setDouble(i++, lunch);
            ps.setDouble(i++, dinner);

            ps.setTimestamp(i++, checkoutTs);

            ps.setDouble(i++, otherCharges);

            ps.setTimestamp(i++, checkoutTs);

            ps.setDouble(i++, breakfast);
            ps.setDouble(i++, lunch);
            ps.setDouble(i++, dinner);
            ps.setDouble(i++, otherCharges);

            ps.setString(i++, paymentMode);
            ps.setTimestamp(i++, checkoutTs);

            ps.setInt(i++, bookingId);


            if (ps.executeUpdate() == 1) {
                con.commit();
                return true;
            }
            con.rollback();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    
    

}
