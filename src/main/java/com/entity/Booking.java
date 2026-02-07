package com.entity;

import java.sql.Timestamp;

public class Booking {

    private int bookingId;
    private int roomId;
    private String roomNo;

    private String guestName;
    private String mobileNo;

    private String guestCategory;   // NFL / GOVT / PRIVATE
    private String guestType;       // OFFICIAL / NON_OFFICIAL

    private Timestamp checkinDatetime;
    private Timestamp checkoutDatetime;

    private String status;           // BOOKED / CHECKED_IN / CHECKED_OUT
    private String createdBy;
    private Timestamp createdDate;
    
    
    
    
    
    

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }


    /* ===== Getters & Setters ===== */

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getGuestCategory() {
        return guestCategory;
    }

    public void setGuestCategory(String guestCategory) {
        this.guestCategory = guestCategory;
    }

    public String getGuestType() {
        return guestType;
    }

    public void setGuestType(String guestType) {
        this.guestType = guestType;
    }

    public Timestamp getCheckinDatetime() {
        return checkinDatetime;
    }

    public void setCheckinDatetime(Timestamp checkinDatetime) {
        this.checkinDatetime = checkinDatetime;
    }

    public Timestamp getCheckoutDatetime() {
        return checkoutDatetime;
    }

    public void setCheckoutDatetime(Timestamp checkoutDatetime) {
        this.checkoutDatetime = checkoutDatetime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
