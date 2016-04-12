package pt.iart.a3_4.util;

import java.util.HashMap;

public class Edge<T> {

	private Vertex<T> v1, v2;
	private HashMap<Transportation, Integer> costs;
	
	public Edge(Vertex<T> v1, Vertex<T> v2, int weight) {
		costs = new HashMap<Transportation, Integer>();
		this.v1 = v1;
		this.v2 = v2;
		costs.put(Transportation.WALK, weight);
		
		v1.addEdge(this);
		v2.addEdge(this);
		v1.addNeighbor(v2);
		v2.addNeighbor(v1);
	}

	public int getCost(Transportation t) {
		if(!costs.containsValue(t)) return -1;
		return costs.get(t);
	}
	
	public boolean haveTransport(Transportation t){
		if(costs.containsValue(t)) return true;
		return false;
	}
	
	public Vertex<?> otherVertex(Vertex<?> v) {
		return (v.equals(v1)) ? v2 : v1;
	}
	
	public void print() {
		if(costs.containsKey(Transportation.BOAT)) System.out.print("[boat="+costs.get(Transportation.BOAT)+"]");
		if(costs.containsKey(Transportation.BUS)) System.out.print("[bus="+costs.get(Transportation.BUS)+"]");
		if(costs.containsKey(Transportation.METRO)) System.out.print("[metro="+costs.get(Transportation.METRO)+"]");
		if(costs.containsKey(Transportation.TRAIN)) System.out.print("[train="+costs.get(Transportation.TRAIN)+"]");
		if(costs.containsKey(Transportation.WALK)) System.out.print("[walk="+costs.get(Transportation.WALK)+"]");
		System.out.println();
	}

	@Override
	public boolean equals(Object obj) {
		@SuppressWarnings("unchecked")
		Edge<T> e = (Edge<T>) obj;
		return (this.v1.equals(e.v1) && this.v2.equals(e.v2) && this.costs.equals(e.costs)) ;
	}

	public boolean contains(Vertex<T> v) {
		if (this.v1.equals(v) || this.v2.equals(v))
			return true;
		return false;
	}

}
