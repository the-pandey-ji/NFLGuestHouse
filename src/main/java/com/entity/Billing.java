package com.entity;

import java.sql.Timestamp;

public class Billing {

    private int bookingId;
    private String guestName;
    private String roomNo;
    private Timestamp checkinDatetime;
    private double roomRent;
	public Billing(int bookingId, String guestName, String roomNo, Timestamp checkinDatetime, double roomRent) {
		super();
		this.bookingId = bookingId;
		this.guestName = guestName;
		this.roomNo = roomNo;
		this.checkinDatetime = checkinDatetime;
		this.roomRent = roomRent;
	}
	public Billing() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getBookingId() {
		return bookingId;
	}
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}
	public String getGuestName() {
		return guestName;
	}
	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public Timestamp getCheckinDatetime() {
		return checkinDatetime;
	}
	public void setCheckinDatetime(Timestamp checkinDatetime) {
		this.checkinDatetime = checkinDatetime;
	}
	public double getRoomRent() {
		return roomRent;
	}
	public void setRoomRent(double roomRent) {
		this.roomRent = roomRent;
	}
	@Override
	public String toString() {
		return "Billing [bookingId=" + bookingId + ", guestName=" + guestName + ", roomNo=" + roomNo
				+ ", checkinDatetime=" + checkinDatetime + ", roomRent=" + roomRent + "]";
	}

    // getters & setters
    
}
