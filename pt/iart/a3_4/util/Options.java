package pt.iart.a3_4.util;

public class Options {
	
	//Singleton
	private static Options instance = null;
	private Options() {}
	public static Options getInstance() {if(instance == null) {instance = new Options();}return instance;}
	
	private Heuristic chosen_path_heuristic = Heuristic.DISTANCE;
	private double max_price = 999999;
	private double max_walk_distance = 999999;
	private boolean avoidances[] = new boolean[] {false, false, false, false, false};
	private Vertex source;
	private Vertex destination;
	
	public Heuristic getChosen_heuristic() {
		return chosen_path_heuristic;
	}
	public double getMax_price() {
		return max_price;
	}
	public double getMax_walk_distance() {
		return max_walk_distance;
	}
	public boolean[] getAvoidances() {
		return avoidances;
	}
	public Vertex getSource() {
		return source;
	}
	public Vertex getDestination() {
		return destination;
	}
	public void setChosen_heuristic(Heuristic chosen_path) {
		this.chosen_path_heuristic = chosen_path;
	}
	public void setMax_price(double max_price) {
		this.max_price = max_price;
	}
	public void setMax_walk_distance(double max_walk_distance) {
		this.max_walk_distance = max_walk_distance;
	}
	public void avoidTransportation(Transportation t) {
		this.avoidances[t.ordinal()] = true;
	}
	public void avoidTransportation(Transportation t, boolean preference) {
		this.avoidances[t.ordinal()] = preference;
	}
	public void setSource(Vertex source) {
		this.source = source;
	}
	public void setDestination(Vertex destination) {
		this.destination = destination;
	}
	public boolean desireToAvoidTransportation( Transportation t) {
		return avoidances[t.ordinal()];
	}
}
