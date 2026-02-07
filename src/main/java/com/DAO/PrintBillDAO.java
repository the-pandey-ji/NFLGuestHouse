package com.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.DB.DBConnect;
import com.entity.PrintBill;

public class PrintBillDAO {

    public PrintBill getPrintBill(int bookingId) {

        PrintBill b = new PrintBill();

        try {
            Connection con = DBConnect.getConnection();

            String sql =
              "SELECT b.BOOKING_ID, b.GUEST_NAME, b.MOBILE_NO, " +
              "b.ADDRESS, b.NO_OF_DAYS, b.ROOM_CHARGE_TOTAL, " +
              "b.FOOD_TOTAL,b.OTHER_CHARGES, b.GRAND_TOTAL, b.PAYMENT_MODE, " +
              "b.CHECKIN_DATETIME, b.CHECKOUT_DATETIME, " +
              "r.ROOM_NO " +
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
                b.setMobile(rs.getString("MOBILE_NO"));
                b.setAddress(rs.getString("ADDRESS"));
                b.setRoomNo(rs.getString("ROOM_NO"));
                b.setNoOfDays(rs.getInt("NO_OF_DAYS"));
                b.setRoomCharge(rs.getDouble("ROOM_CHARGE_TOTAL"));
                b.setFoodTotal(rs.getDouble("FOOD_TOTAL"));
                b.setOtherCharges(rs.getDouble("OTHER_CHARGES"));

                b.setGrandTotal(rs.getDouble("GRAND_TOTAL"));
                b.setPaymentMode(rs.getString("PAYMENT_MODE"));
                b.setCheckinDatetime(rs.getTimestamp("CHECKIN_DATETIME"));
                b.setCheckoutDatetime(rs.getTimestamp("CHECKOUT_DATETIME"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }
}
