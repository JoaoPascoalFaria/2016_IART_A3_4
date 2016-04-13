package pt.iart.a3_4.util;

import java.util.ArrayList;
import java.util.List;

public class Vertex implements Comparable<Vertex>{
	
	private static int count = 0;
	private int id;
	private Location info;
	private List<Edge> edges = new ArrayList<Edge>();
	private List<Vertex> neighbors = new ArrayList<Vertex>();
	
	// Location
	public Vertex(Location obj) {
		this.info = obj;
		id = ++count;
	}

	public Location getValue() {
		return info;
	}
	
	// Edges
	public List<Edge> getEdges() {
		return edges;
	}
	
	public void addEdge(Edge e) {
		edges.add(e);
	}

	public void clearEdges() {
		this.edges.clear();
	}
	
	// Vertexes
	public void addNeighbor(Vertex v) {
		neighbors.add(v);
	}

	public List<Vertex> getNeighbors() {
		return this.neighbors;
	}

	// Util
	public void print() {
		System.out.println("[obj="+info.toString()+"]");
	}
	
	public Edge getConnectingEdge(Vertex v) {
		for (Edge edge : edges) {
			if (edge.contains(this) && edge.contains(v)) {
				return edge;
			}
		}
		return null;
	}

	@Override
	public int compareTo(Vertex arg0) {
		if (this.id == arg0.id)
			return 0;
		return 0;
	}
	
	@Override
	public boolean equals(Object arg0) {
		Vertex v2 = (Vertex) arg0;
		return (this.id == v2.id);
	}
}
