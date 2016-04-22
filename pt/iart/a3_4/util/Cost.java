package pt.iart.a3_4.util;

public class Cost {
	
	private double distance;// KM
	private double time;// min
	private double price;// €
	
	/**
	 * 
	 * @param length
	 * @param travelTime
	 * @param price
	 */
	public Cost(double length, double travelTime, double price) {
		this.distance = length;
		this.time = travelTime;
		this.price = price;
	}
	
	public double getDistance() {
		return this.distance;
	}
	
	public double getTravelTime(){
		return this.time;
	}
	
	public double getPrice(){
		return this.price;
	}

	@Override
	public boolean equals(Object obj) {
		Cost c = (Cost) obj;
		return this.distance==c.distance && this.time==c.time && this.price==c.price;
	}

	@Override
	public String toString() {
		String s = "{dist "+String.format("%.2f", this.distance)+" time "+String.format("%.2f", this.time)+" price "+String.format("%.2f", this.price);
		return s;
	}
}
