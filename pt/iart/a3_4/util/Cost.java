package pt.iart.a3_4.util;

public class Cost {
	
	private double distance;
	private int time;
	
	public Cost(double length) {
		this.distance = length;
		this.time = -1;
	}
	
	public Cost(double length, int travelTime) {
		this.distance = length;
		this.time = travelTime;
	}
	
	public double getDistance() {
		return this.distance;
	}
	
	public int getTravelTime(){
		return this.time;
	}

	@Override
	public boolean equals(Object obj) {
		Cost c = (Cost) obj;
		return this.distance==c.distance && this.time==c.time;
	}

	@Override
	public String toString() {
		String s = "{dist "+String.format("%.2f", this.distance)+" time "+this.time;
		return s;
	}
}
