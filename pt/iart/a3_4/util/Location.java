package pt.iart.a3_4.util;

import java.util.Set;

public class Location {
	
	private String name;
	private Set<String> tags;
	
	public Location(String name) {
		this.name = name;
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
