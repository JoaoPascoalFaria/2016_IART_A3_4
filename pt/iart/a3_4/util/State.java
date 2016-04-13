package pt.iart.a3_4.util;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/**
 * 
 * @description Estado que representa um dos estados visitados pelo A*
 */
public class State implements Comparable<State> {
	
	private int g;
	private int h;
	private LinkedHashSet<Vertex> path;
	private LinkedHashMap<Edge, Transportation> edgeTransport;

	/**
	 * @description Initialization of States (the very first one)
	 * @param g cost
	 * @param h cost
	 * @param initial vertex
	 */
	public State(int g, int h, Vertex current) {
		path = new LinkedHashSet<Vertex>();
		edgeTransport = new LinkedHashMap<Edge, Transportation>();
		this.g = g;
		this.h = h;
		path.add(current);
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
	public State(State s, Vertex v, Edge e, Transportation t, int h){
		this.g = s.g + e.getCost(t);
		this.h = h;
		this.path = (LinkedHashSet<Vertex>) s.path.clone();
		this.edgeTransport = (LinkedHashMap<Edge, Transportation>) s.edgeTransport.clone();
		this.path.add(v);
		this.edgeTransport.put(e, t);
	}
	
	public int getG() {
		return g;
	}

	public int getH() {
		return h;
	}

	public LinkedHashSet<Vertex> getPath() {
		return path;
	}

	public LinkedHashMap<Edge, Transportation> getEdgeTransport() {
		return edgeTransport;
	}

	@Override
	public int compareTo(State s) {
		int myF = this.g+this.h;
		int otherF = s.g+s.h;
		if(myF < otherF) return -1;
		if(myF > otherF) return 1;
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		State s = (State) obj;
		return this.g==s.g && this.h==s.h && this.path.equals(s.path) && this.edgeTransport.equals(s.edgeTransport);
	}
}
