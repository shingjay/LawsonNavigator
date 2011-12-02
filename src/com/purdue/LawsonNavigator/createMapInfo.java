package com.purdue.LawsonNavigator;

public class createMapInfo {
	private String name, usage, floor;
	
	public createMapInfo()
	{
		name = "name";
		usage = "usage";
		floor = "floor";
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setFloor(String floor)
	{
		this.floor = floor;
	}
	
	public void setUsage(String usage)
	{
		this.usage = usage;
	}
	
	public String returnName()
	{
		return name;
	}

	public String returnUsage()
	{
		return usage;
	}

	public String returnFloor()
	{
		return floor;
	}
}
