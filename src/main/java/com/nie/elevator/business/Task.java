package com.nie.elevator.business;

/**
 * Encapsulate the customer's request details.
 * 
 * @author lnie
 *
 */
public class Task {

	private int noOfpeople;
	private int fromFloorNo;
	private int toFloorNo;
	
	public int getNoOfpeople() {
		return noOfpeople;
	}
	public void setNoOfpeople(int noOfpeople) {
		this.noOfpeople = noOfpeople;
	}
	public int getFromFloorNo() {
		return fromFloorNo;
	}
	public void setFromFloorNO(int fromFloorNo) {
		this.fromFloorNo = fromFloorNo;
	}
	public int getToFloorNo() {
		return toFloorNo;
	}
	public void setToFloorNo(int toFloorNo) {
		this.toFloorNo = toFloorNo;
	}
	
}
