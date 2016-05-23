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
	public State(State s, Vertex v, Edge e, Transportation t, double h, Heuristic heuristic){
		// TODO distance or time!? or..
		if( heuristic == Heuristic.DISTANCE)
			this.g = s.g + e.getCost(t).getDistance()+e.getCost(t).getTravelTime()/12;
		else if( heuristic == Heuristic.TIME)
			this.g = s.g + e.getCost(t).getTravelTime();
		else if( heuristic == Heuristic.PRICE)
			this.g = s.g + e.getCost(t).getTravelTime()/20+e.getCost(t).getPrice();//20mins is equivalent to 1€
		else if( heuristic == Heuristic.WALK_DISTANCE)//TODO
			this.g = s.g + e.getCost(t).getTravelTime();
		else//TODO SWAPS
			this.g = s.g + e.getCost(t).getTravelTime();
		this.h = h;
		this.path = (LinkedHashSet<Vertex>) s.path.clone();
		this.edgeTransport = (LinkedHashMap<Edge, Transportation>) s.edgeTransport.clone();
		this.path.add(v);
		this.edgeTransport.put(e, t);
		this.lastVertex = v;
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
		/*if(this.equals(s)) return 0;
		return 1;*/
		return 0;
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
				" Path:\n"+"FROM\tTO\tTRANSPORT\tTIME\t\tDISTANCE\tPRICE\t\tTOTAL TIME\tTOTAL DISTANCE\tTOTAL PRICE\n"+temp.getInfo().getName());
		for( Map.Entry<Edge, Transportation> e : this.edgeTransport.entrySet()){
			Vertex v = pathIt.next();
			time += temp.getConnectingEdge(v).getCost(e.getValue()).getTravelTime();
			distance += temp.getConnectingEdge(v).getCost(e.getValue()).getDistance();
			price += temp.getConnectingEdge(v).getCost(e.getValue()).getPrice();
			temp = v;
			//System.out.println(" to "+v.getInfo().getName()+" by "+e.getValue().toString());
			System.out.println("\t"+v.getInfo().getName()+"\t"+e.getValue().toString()+
					"\t\t"+String.format("%02d:%02dh", TimeUnit.MINUTES.toHours((long) temp.getConnectingEdge(v).getCost(e.getValue()).getTravelTime()),(long) temp.getConnectingEdge(v).getCost(e.getValue()).getTravelTime()-TimeUnit.HOURS.toMinutes(TimeUnit.MINUTES.toHours((long) temp.getConnectingEdge(v).getCost(e.getValue()).getTravelTime()))) +
					"\t\t"+String.format("%.2f", temp.getConnectingEdge(v).getCost(e.getValue()).getDistance())+"Km" +
					"\t\t"+temp.getConnectingEdge(v).getCost(e.getValue()).getPrice()+"€"+
					"\t\t"+String.format("%02d:%02dh", TimeUnit.MINUTES.toHours((long) time),(long) time-TimeUnit.HOURS.toMinutes(TimeUnit.MINUTES.toHours((long) time))) +
					"\t\t"+String.format("%.2f", distance)+"Km" +
					"\t\t"+price+"€");
			if( pathIt.hasNext()) System.out.print(v.getInfo().getName());
		}
		System.out.println("total travel time\t"+String.format("%02d:%02dh", TimeUnit.MINUTES.toHours((long) time),(long) time-TimeUnit.HOURS.toMinutes(TimeUnit.MINUTES.toHours((long) time))));
		System.out.println("total distance\t\t"+String.format("%.2f", distance)+"Km");
		System.out.println("total price\t\t"+price+"€");
		System.out.println();
	}
}
