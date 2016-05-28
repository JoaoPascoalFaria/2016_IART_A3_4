package pt.iart.a3_4.util;

import java.util.HashSet;

public class Location {
	
	private String name;
	private double x;
	private double y;
	private HashSet<String> tags;

	private static double x_min_offset = 999999;
	private static double x_max_offset = -999999;
	private static double y_min_offset = 999999;
	private static double y_max_offset = -999999;
	
	public Location(String name, double x, double y) {
		this.name = name;
		this.x = x;//latitude  decimal degrees
		this.y = y;//longitude decimal degrees
		this.tags = new HashSet<String>();
		if((x/0.009) < x_min_offset) x_min_offset = x/0.009;
		if((x/0.009) > x_max_offset) x_max_offset = x/0.009;
		if((y/0.009) < y_min_offset) y_min_offset = y/0.009;
		if((y/0.009) > y_max_offset) y_max_offset = y/0.009;
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

	public static double getX_min_offset() {
		return x_min_offset;
	}

	public static double getX_max_offset() {
		return x_max_offset;
	}

	public static double getY_min_offset() {
		return y_min_offset;
	}

	public static double getY_max_offset() {
		return y_max_offset;
	}
}
