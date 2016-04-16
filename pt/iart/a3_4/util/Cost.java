package pt.iart.a3_4.util;

public class Cost {
	
	private double distance;// KM
	private double time;// min
	
	public Cost(double length) {
		this.distance = length;
		this.time = -1;
	}
	
	public Cost(double length, double travelTime) {
		this.distance = length;
		this.time = travelTime;
	}
	
	public double getDistance() {
		return this.distance;
	}
	
	public double getTravelTime(){
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
