package com.entity;

import java.sql.Timestamp;

public class PrintBill {

    private int bookingId;
    private String guestName;
    private String mobile;
    private String address;
    private String roomNo;
    private int noOfDays;
    private double roomCharge;
    private double foodTotal;
    private double otherCharges;
    private double grandTotal;
    private String paymentMode;
    private Timestamp checkinDatetime;
    private Timestamp checkoutDatetime;
	public PrintBill() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PrintBill(int bookingId, String guestName, String mobile, String address, String roomNo, int noOfDays,
			double roomCharge, double foodTotal, double otherCharges, double grandTotal, String paymentMode, Timestamp checkinDatetime,
			Timestamp checkoutDatetime) {
		super();
		this.bookingId = bookingId;
		this.guestName = guestName;
		this.mobile = mobile;
		this.address = address;
		this.roomNo = roomNo;
		this.noOfDays = noOfDays;
		this.roomCharge = roomCharge;
		this.foodTotal = foodTotal;
		this.otherCharges = otherCharges;
		this.grandTotal = grandTotal;
		this.paymentMode = paymentMode;
		this.checkinDatetime = checkinDatetime;
		this.checkoutDatetime = checkoutDatetime;
	}
	@Override
	public String toString() {
		return "PrintBill [bookingId=" + bookingId + ", guestName=" + guestName + ", mobile=" + mobile + ", address="
				+ address + ", roomNo=" + roomNo + ", noOfDays=" + noOfDays + ", roomCharge=" + roomCharge
				+ ", foodTotal=" + foodTotal +", otherCharges=" + otherCharges+ ", grandTotal=" + grandTotal + ", paymentMode=" + paymentMode
				+ ", checkinDatetime=" + checkinDatetime + ", checkoutDatetime=" + checkoutDatetime + "]";
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
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public int getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(int noOfDays) {
		this.noOfDays = noOfDays;
	}
	public double getRoomCharge() {
		return roomCharge;
	}
	public void setRoomCharge(double roomCharge) {
		this.roomCharge = roomCharge;
	}
	public double getFoodTotal() {
		return foodTotal;
	}
	public void setFoodTotal(double foodTotal) {
		this.foodTotal = foodTotal;
	}

	public double getOtherCharges() {
		return otherCharges;
	}

	public void setOtherCharges(double otherCharges) {
		this.otherCharges = otherCharges;
	}
	public double getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(double grandTotal) {
		this.grandTotal = grandTotal;
	}
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
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

    // getters & setters
}
