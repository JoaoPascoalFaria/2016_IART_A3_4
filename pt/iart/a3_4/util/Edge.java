package pt.iart.a3_4.util;

import java.util.HashMap;
import java.util.Set;

public class Edge {

	private Vertex v1, v2;
	private HashMap<Transportation, Cost> costs;
	
	/**
	 * 
	 * @param v1 vertex 1
	 * @param v2 vertex 2
	 * @param time walking time from v1 to v2
	 * @info time assumed to be 5km/h for walking
	 */
	public Edge(Vertex v1, Vertex v2, double time) {
		costs = new HashMap<Transportation, Cost>();
		this.v1 = v1;
		this.v2 = v2;
		if(time < getLength()/5*60) time=getLength()/5*60;//max 5km/h
		Cost c = new Cost(getLength(), time);
		costs.put(Transportation.WALK, c);
		
		v1.addEdge(this);
		v2.addEdge(this);
		v1.addNeighbor(v2);
		v2.addNeighbor(v1);
	}
	
	public Edge(Vertex v1, Vertex v2) {
		costs = new HashMap<Transportation, Cost>();
		this.v1 = v1;
		this.v2 = v2;
		
		v1.addEdge(this);
		v2.addEdge(this);
		v1.addNeighbor(v2);
		v2.addNeighbor(v1);
	}
	
	/**
	 * MST ONLY
	 */
	@SuppressWarnings("unchecked")
	public Edge(Vertex v1, Vertex v2, Edge e) {
		costs = (HashMap<Transportation, Cost>) e.costs.clone();
		this.v1 = v1;
		this.v2 = v2;
		
		v1.addEdge(this);
		v2.addEdge(this);
		v1.addNeighbor(v2);
		v2.addNeighbor(v1);
	}

	public Cost getCost(Transportation t) {
		if(!costs.containsKey(t)) return null;
		return costs.get(t);
	}
	
	public boolean haveTransport(Transportation t){
		if(costs.containsValue(t)) return true;
		return false;
	}
	
	public Set<Transportation> getTransportations(){
		return costs.keySet();
	}
	
	public void addTransportation(Transportation t){
		Cost c = new Cost(this.getLength(), this.getLength()/5*60);// if no time was set, slowest possible = walking speed
		this.costs.put(t, c);
	}
	
	/**
	 * 
	 * @param t transportation
	 * @param cost time that transportation takes to travel trough the edge
	 */
	public void addTransportation(Transportation t, double cost){
		// max speeds
		if( t==Transportation.WALK) {// 9km/h
			if( cost < getLength()/9*60) cost = getLength()/9*60;
		}
		else if( t==Transportation.TRAIN) {// 200km/h
			if( cost < getLength()/200*60) cost = getLength()/200*60;
		}
		else if( t==Transportation.BUS) {// 100km/h
			if( cost < getLength()/100*60) cost = getLength()/100*60;
		}
		else if( t==Transportation.METRO) {// 90km/h
			if( cost < getLength()/90*60) cost = getLength()/90*60;
		}
		else if( t==Transportation.BOAT) {// 50km/h
			if( cost < getLength()/50*60) cost = getLength()/50*60;
		}
		Cost c = new Cost(this.getLength(), cost);
		this.costs.put(t, c);
	}
	
	public Vertex otherVertex(Vertex v) {
		return (v.equals(v1)) ? v2 : v1;
	}
	
	public Vertex getV1(){
		return v1;
	}
	
	public Vertex getV2(){
		return v2;
	}
	
	// Util
	public double getLength() {
		return Math.sqrt( Math.pow(v2.getInfo().getX() - v1.getInfo().getX(), 2) + Math.pow(v2.getInfo().getY() - v1.getInfo().getY(), 2));
	}
	
	public void print() {
		System.out.print("["+this.v1.getInfo().getName()+"-"+this.v2.getInfo().getName()+" ");
		if(costs.containsKey(Transportation.BOAT))	System.out.print("boat="+costs.get(Transportation.BOAT).toString());
		if(costs.containsKey(Transportation.BUS)) 	System.out.print("bus="+costs.get(Transportation.BUS).toString());
		if(costs.containsKey(Transportation.METRO)) System.out.print("metro="+costs.get(Transportation.METRO).toString());
		if(costs.containsKey(Transportation.TRAIN)) System.out.print("train="+costs.get(Transportation.TRAIN).toString());
		if(costs.containsKey(Transportation.WALK)) 	System.out.print("walk="+costs.get(Transportation.WALK).toString());
		System.out.print("] ");
	}

	@Override
	public boolean equals(Object obj) {
		Edge e = (Edge) obj;
		return (this.v1.equals(e.v1) && this.v2.equals(e.v2) && this.costs.equals(e.costs)) ;
	}

	public boolean contains(Vertex v) {
		if (this.v1.equals(v) || this.v2.equals(v))
			return true;
		return false;
	}

}
