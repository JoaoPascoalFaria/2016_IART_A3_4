package pt.iart.a3_4.util;

import java.util.HashMap;
import java.util.Set;

public class Edge {

	private Vertex v1, v2;
	private HashMap<Transportation, Integer> costs;
	
	/**
	 * 
	 * @param v1 vertex 1
	 * @param v2 vertex 2
	 * @param weight WALK cost from v1 to v2
	 */
	public Edge(Vertex v1, Vertex v2, int weight) {
		costs = new HashMap<Transportation, Integer>();
		this.v1 = v1;
		this.v2 = v2;
		costs.put(Transportation.WALK, weight);
		
		v1.addEdge(this);
		v2.addEdge(this);
		v1.addNeighbor(v2);
		v2.addNeighbor(v1);
	}
	
	public Edge(Vertex v1, Vertex v2) {
		costs = new HashMap<Transportation, Integer>();
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
		costs = (HashMap<Transportation, Integer>) e.costs.clone();
		this.v1 = v1;
		this.v2 = v2;
		
		v1.addEdge(this);
		v2.addEdge(this);
		v1.addNeighbor(v2);
		v2.addNeighbor(v1);
	}

	public int getCost(Transportation t) {
		if(!costs.containsKey(t)) return -1;
		return costs.get(t);
	}
	
	public boolean haveTransport(Transportation t){
		if(costs.containsValue(t)) return true;
		return false;
	}
	
	public Set<Transportation> getTransportations(){
		return costs.keySet();
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
	
	public void print() {
		System.out.print("["+this.v1.getInfo().getName()+"-"+this.v2.getInfo().getName()+" ");
		if(costs.containsKey(Transportation.BOAT))	System.out.print("boat="+costs.get(Transportation.BOAT));
		if(costs.containsKey(Transportation.BUS)) 	System.out.print("bus="+costs.get(Transportation.BUS));
		if(costs.containsKey(Transportation.METRO)) System.out.print("metro="+costs.get(Transportation.METRO));
		if(costs.containsKey(Transportation.TRAIN)) System.out.print("train="+costs.get(Transportation.TRAIN));
		if(costs.containsKey(Transportation.WALK)) 	System.out.print("walk="+costs.get(Transportation.WALK));
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
