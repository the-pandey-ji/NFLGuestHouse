package com.entity;

public class Room {

    private int roomId;
    private String roomNo;
    private String roomType;
    private int capacity;
    private String status;

    private double nflRent;
    private double govtRent;
    private double privateRent;
	public Room(int roomId, String roomNo, String roomType, int capacity, String status, double nflRent,
			double govtRent, double privateRent) {
		super();
		this.roomId = roomId;
		this.roomNo = roomNo;
		this.roomType = roomType;
		this.capacity = capacity;
		this.status = status;
		this.nflRent = nflRent;
		this.govtRent = govtRent;
		this.privateRent = privateRent;
	}
	public Room() {
		super();
		// TODO Auto-generated constructor stub
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
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getNflRent() {
		return nflRent;
	}
	public void setNflRent(double nflRent) {
		this.nflRent = nflRent;
	}
	public double getGovtRent() {
		return govtRent;
	}
	public void setGovtRent(double govtRent) {
		this.govtRent = govtRent;
	}
	public double getPrivateRent() {
		return privateRent;
	}
	public void setPrivateRent(double privateRent) {
		this.privateRent = privateRent;
	}
	@Override
	public String toString() {
		return "Room [roomId=" + roomId + ", roomNo=" + roomNo + ", roomType=" + roomType + ", capacity=" + capacity
				+ ", status=" + status + ", nflRent=" + nflRent + ", govtRent=" + govtRent + ", privateRent="
				+ privateRent + "]";
	}


	
}
