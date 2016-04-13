package pt.iart.a3_4.util;

import java.util.HashSet;

public class Location {
	
	private String name;
	private HashSet<String> tags;
	
	public Location(String name) {
		this.name = name;
		this.tags = new HashSet<String>();
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
