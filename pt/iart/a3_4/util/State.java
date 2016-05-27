package pt.iart.a3_4.util;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 
 * @description Estado que representa um dos estados visitados pelo A*
 */
public class State implements Comparable<State> {
	
	private double g;
	private double h;
	private LinkedHashSet<Vertex> path;
	private Vertex lastVertex;// prevents path.toArray()[last]
	private LinkedHashMap<Edge, Transportation> edgeTransport;
	private Options options = Options.getInstance();
	private double total_walked_distance = 0;
	private int total_swaps = 0;
	private Transportation previous_transport = null;
	private double total_price = 0;

	/**
	 * @description Initialization of States (the very first one)
	 * @param g cost
	 * @param h cost
	 * @param initial vertex
	 */
	public State(double g, double h, Vertex current) {
		path = new LinkedHashSet<Vertex>();
		edgeTransport = new LinkedHashMap<Edge, Transportation>();
		this.g = g;
		this.h = h;
		path.add(current);
		this.lastVertex = current;
	}
	
	/**
	 * 
	 * @param s previous state
	 * @param v new current vertex
	 * @param e edge used to travel
	 * @param t transportation used in the edge e
	 * @param h this state heuristic value
	 */
	@SuppressWarnings("unchecked")
	public State(State s, Vertex v, Edge e, Transportation t, double h){
		
		this.total_walked_distance = s.total_walked_distance + (t==Transportation.WALK ? e.getCost(t).getDistance() : 0);
		this.total_swaps = s.total_swaps + (t==s.previous_transport||s.previous_transport==null ? 0 : (t==Transportation.WALK ? 0 : 1));
		this.total_price = s.total_price + e.getCost(t).getPrice();
		
		Heuristic heuristic = options.getChosen_heuristic();
		if( heuristic == Heuristic.DISTANCE)
			this.g = s.g + e.getCost(t).getDistance()+e.getCost(t).getTravelTime()/12;//12min is equivalent to 1km
		else if( heuristic == Heuristic.TIME)
			this.g = s.g + e.getCost(t).getTravelTime();
		else if( heuristic == Heuristic.PRICE)
			this.g = s.g + e.getCost(t).getTravelTime()/20+e.getCost(t).getPrice();//20mins is equivalent to 1€
		else if( heuristic == Heuristic.WALK_DISTANCE)
			this.g = s.g + e.getCost(t).getDistance()+e.getCost(t).getTravelTime()/12+(this.total_walked_distance-s.total_walked_distance)*100;//walking have big cost
		else if( heuristic == Heuristic.SWAPS)
			this.g = s.g + e.getCost(t).getDistance()+e.getCost(t).getTravelTime()/12+(this.total_swaps-s.total_swaps)*250;//Swaps have big cost
		
		if(options.desireToAvoidTransportation(t))
			this.g += (this.g - s.g)*100;//Increase this Edge weight if we want to avoid this kind of transportation
		if(s.total_price <= options.getMax_price() && this.total_price > options.getMax_price())
			this.g += 999999;//allows payments above price if no other path possible.
		if(s.total_walked_distance <= options.getMax_walk_distance() && this.total_walked_distance > options.getMax_walk_distance())
			this.g += 999999;//allows walking above limit if no other path possible
		this.h = h;
		this.path = (LinkedHashSet<Vertex>) s.path.clone();
		this.edgeTransport = (LinkedHashMap<Edge, Transportation>) s.edgeTransport.clone();
		this.path.add(v);
		this.edgeTransport.put(e, t);
		this.lastVertex = v;
		this.previous_transport = t;
	}
	
	public double getG() {
		return g;
	}

	public double getH() {
		return h;
	}

	public LinkedHashSet<Vertex> getPath() {
		return path;
	}

	public LinkedHashMap<Edge, Transportation> getEdgeTransport() {
		return edgeTransport;
	}
	
	public Vertex currentVertex(){
		return this.lastVertex;
	}

	@Override
	public int compareTo(State s) {
		double myF = this.g+this.h;
		double otherF = s.g+s.h;
		if(myF < otherF) return -1;
		if(myF > otherF) return 1;
		//sometimes for some magical reason they end up with the exact same f, need to prevent set from rejecting them
		if(this.equals(s)) return 0;
		return 1;/**/
		/*return 0;/**/
	}

	@Override
	public boolean equals(Object obj) {
		State s = (State) obj;
		//return this.g==s.g && this.h==s.h && this.path.equals(s.path) && this.edgeTransport.equals(s.edgeTransport);
		//talvez funcione assim
		return this.lastVertex.equals(s.lastVertex);
	}

	public void print() {
		Iterator<Vertex> pathIt = this.path.iterator();
		double time=0, distance=0, price=0;
		Vertex temp = pathIt.next();
		System.out.print("Lowest "+Options.getInstance().getChosen_heuristic().toString()+
				" Path:\n"+String.format("%-20s %-20s", "FROM","TO")+"\tTRANSPORT\tTIME\t\tDISTANCE\tPRICE\t\tTOTAL TIME\tTOTAL DISTANCE\tTOTAL PRICE\n");
		for( Map.Entry<Edge, Transportation> e : this.edgeTransport.entrySet()){
			Vertex v = pathIt.next();
			time += temp.getConnectingEdge(v).getCost(e.getValue()).getTravelTime();
			distance += temp.getConnectingEdge(v).getCost(e.getValue()).getDistance();
			price += temp.getConnectingEdge(v).getCost(e.getValue()).getPrice();
			//System.out.println(" to "+v.getInfo().getName()+" by "+e.getValue().toString());
			System.out.print(
					String.format("%-20s %-20s", temp.getInfo().getName(), v.getInfo().getName())
			);
			temp = v;
			System.out.println("\t"+e.getValue().toString()+
					"\t\t"+String.format("%02d:%02dh", TimeUnit.MINUTES.toHours((long) temp.getConnectingEdge(v).getCost(e.getValue()).getTravelTime()),(long) temp.getConnectingEdge(v).getCost(e.getValue()).getTravelTime()-TimeUnit.HOURS.toMinutes(TimeUnit.MINUTES.toHours((long) temp.getConnectingEdge(v).getCost(e.getValue()).getTravelTime()))) +
					"\t\t"+String.format("%.2f", temp.getConnectingEdge(v).getCost(e.getValue()).getDistance())+"Km" +
					"\t\t"+String.format("%.2f", temp.getConnectingEdge(v).getCost(e.getValue()).getPrice())+"€"+
					"\t\t"+String.format("%02d:%02dh", TimeUnit.MINUTES.toHours((long) time),(long) time-TimeUnit.HOURS.toMinutes(TimeUnit.MINUTES.toHours((long) time))) +
					"\t\t"+String.format("%.2f", distance)+"Km" +
					"\t\t"+String.format("%.2f", price)+"€");
			//if( pathIt.hasNext()) System.out.print(v.getInfo().getName());
		}
		System.out.println("Total travel time\t"+String.format("%02d:%02dh", TimeUnit.MINUTES.toHours((long) time),(long) time-TimeUnit.HOURS.toMinutes(TimeUnit.MINUTES.toHours((long) time))));
		System.out.println("Total distance\t\t"+String.format("%.2f", distance)+"Km");
		System.out.println("Total distance by foot\t"+String.format("%.2f", this.total_walked_distance)+"Km");
		System.out.println("Total price\t\t"+String.format("%.2f", this.total_price)+"€");
		System.out.println("Total number of swaps\t"+this.total_swaps);
		System.out.println();
	}
}
