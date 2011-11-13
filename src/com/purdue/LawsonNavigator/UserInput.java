/**
 * 
 */
package com.purdue.LawsonNavigator;

import java.io.Serializable;

/**
 * @author ong0
 *	class that contains all of the user input, consisting of:
 *		- user's current coordinates in latitude and longitude (x,y)
 *		- user's current floor; basement, first, second, third
 *		- user's method of moving; stairs or elevators
 *		- user's final destination; in terms of a room number
 *	INCOMPLETE
 */

public class UserInput implements Serializable {
	
	private Transport transport;
	private Floor floor;
	private Display displayOption;
	private double latitude;
	private double longitude;
	private String roomNumber;
	private String nonAcademicRoom;
	private String professorName;
	
	/**
	 * @category default constructor
	 */
	public UserInput() {
		transport = null;
		floor = null;
		displayOption = null;
		latitude = 0; 
		longitude = 0;
		roomNumber = null;
		nonAcademicRoom = null;
		professorName = null;
	}
	
	public UserInput(Transport transport, Floor floor, Display displayOption, double latitude, double longitude, String roomNumber, String nonAcademicRoom, String professorName) {
		this.transport = transport;
		this.floor = floor;
		this.displayOption = displayOption;
		this.latitude = latitude;
		this.longitude = longitude;
		this.roomNumber = roomNumber;
		this.nonAcademicRoom = nonAcademicRoom;
		this.professorName = professorName;
	}
	
	public Transport getTransport() { return transport; }
	
	public Floor getFloor() { return floor; }
	
	public double getLatitude() { return latitude; }
	
	public double getLongitude() { return longitude; }
	
	//Added by Ryan Start
	public String getRoomNumber() {return roomNumber;}
	
	public String getNonAcademicRoom() {return nonAcademicRoom;}
	
	public String getProfessorName() {return professorName;}
	
	public Display getDisplayOption() {return displayOption;}
	//Added by Ryan End
	
	
	public void setTransport(Transport transport) {
		this.transport = transport; 
	}
	
	public void setFloor(Floor floor) {
		this.floor = floor;
	}
	
	public void setDisplayOption(Display displayOption) {
		this.displayOption = displayOption;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	//Added by Ryan Start
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	
	public void setNonAcademicRoom(String nonAcademicRoom) {
		this.nonAcademicRoom = nonAcademicRoom;
	}
	
	public void setProfessorName(String professorName) {
		this.professorName = professorName;
	}
	//Added by Ryan End
}
