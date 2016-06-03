package com.sword.util.poi;

import java.util.Date;

public class TestModel {
	@PoiCell(cellIndex = 0,dataType=DataTypeEnum.INT)
	private int userid;
	
	@PoiCell(cellIndex = 1)
	private String userName;
	
	@PoiCell(cellIndex = 2)
	private String orderId;
	
	@PoiCell(cellIndex = 3,dataType=DataTypeEnum.DATE)
	private Date orderDate;
	
	@PoiCell(cellIndex = 4,dataType=DataTypeEnum.DATE)
	private Date outDate;
	
	@PoiCell(cellIndex = 5,dataType=DataTypeEnum.DATE)
	private Date recieveDate;
	
	@PoiCell(cellIndex=6,dataType=DataTypeEnum.DOUBLE)
	private double amount;

	
	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getOutDate() {
		return outDate;
	}

	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}

	public Date getRecieveDate() {
		return recieveDate;
	}

	public void setRecieveDate(Date recieveDate) {
		this.recieveDate = recieveDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	
}
