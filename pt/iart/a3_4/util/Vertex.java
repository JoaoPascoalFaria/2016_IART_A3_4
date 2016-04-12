package pt.iart.a3_4.util;

import java.util.ArrayList;
import java.util.List;

public class Vertex<T> implements Comparable<Vertex<T>>{
	
	private static int count = 0;
	private int id;
	private T obj;
	private List<Edge<T>> edges = new ArrayList<Edge<T>>();
	private List<Vertex<T>> neighbors = new ArrayList<Vertex<T>>();
	
	// T
	public Vertex(T obj) {
		this.obj = obj;
		id = ++count;
	}

	public T getValue() {
		return obj;
	}
	
	// Edges
	public List<Edge<T>> getEdges() {
		return edges;
	}
	
	public void addEdge(Edge<T> e) {
		edges.add(e);
	}

	public void clearEdges() {
		this.edges.clear();
	}
	
	// Vertexes
	public void addNeighbor(Vertex<T> v) {
		neighbors.add(v);
	}

	public List<Vertex<T>> getNeighbors() {
		return this.neighbors;
	}

	// Util
	public void print() {
		System.out.println("[obj="+obj.toString()+"]");
	}
	
	public Edge<T> getConnectingEdge(Vertex<T> v) {
		for (Edge<T> edge : edges) {
			if (edge.contains(this) && edge.contains(v)) {
				return edge;
			}
		}
		return null;
	}

	@Override
	public int compareTo(Vertex<T> arg0) {
		if (this.id == arg0.id)
			return 0;
		return 1;
	}
	
	@Override
	public boolean equals(Object arg0) {
		@SuppressWarnings("unchecked")
		Vertex<T> v2 = (Vertex<T>) arg0;
		return (this.id == v2.id);
	}
}
