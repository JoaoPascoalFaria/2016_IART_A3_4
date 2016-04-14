package pt.iart.a3_4.util;

import java.util.HashSet;

public class Location {
	
	private String name;
	private int x;
	private int y;
	private HashSet<String> tags;
	
	public Location(String name, int x, int y) {
		this.name = name;
		this.x = x;
		this.y = y;
		this.tags = new HashSet<String>();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
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
