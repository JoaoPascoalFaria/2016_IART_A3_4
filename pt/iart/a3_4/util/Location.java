package pt.iart.a3_4.util;

import java.util.HashSet;

public class Location {
	
	private String name;
	private double x;
	private double y;
	private HashSet<String> tags;
	
	public Location(String name, double x, double y) {
		this.name = name;
		this.x = x;//latitude  decimal degrees
		this.y = y;//longitude decimal degrees
		this.tags = new HashSet<String>();
	}

	public double getX() {
		return x/0.009;
		//return x;
	}

	public double getY() {
		return y/0.009;
		//return y;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void addTag(String tag){
		this.tags.add(tag);
	}

	public boolean haveTag(String tag){
		if(this.tags.contains(tag)) return true;
		return false;
	}
	
	public void removeTag(String tag){
		this.tags.remove(tag);
	}
}
