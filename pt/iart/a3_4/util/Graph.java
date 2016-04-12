package pt.iart.a3_4.util;

import java.util.ArrayList;
import java.util.List;


public class Graph<T> {
	
	private List<Vertex<T>> vertexes;
	private List<Edge<T>> edges;
	
	private int vertexCount;
	private int edgeCount;
	
	public Graph() {
		this.vertexes = new ArrayList<Vertex<T>>();
		this.edges = new ArrayList<Edge<T>>();
		this.vertexCount = 0;
		this.edgeCount = 0;
	}
	
	// Vertex
	public void removeVertex(Vertex<T> v) {
		// TODO nao apaga tudo
		for(Edge<T> e : v.getEdges()) {
			this.edges.remove(e);
		}
		v.clearEdges();
	}

	public void addVertex(T obj) {
		Vertex<T> v = new Vertex<T>(obj);
		vertexes.add(v);
	}
	
	public Vertex<T> addVertex2(T obj) {
		Vertex<T> v = new Vertex<T>(obj);
		vertexes.add(v);
		return v;
	}
	
	public void addVertex(Vertex<T> v) {
		vertexes.add(v);
	}

	public List<Vertex<T>> getVertexes() {
		return vertexes;
	}

	public int getVertexCount() {
		return vertexCount;
	}

	//Edge
	public boolean addEdge(Vertex<T> v1, Vertex<T> v2, int weight) {
		Edge<T> edge = new Edge<T>(v1, v2, weight);
		if (!edges.contains(edge)) {
			edges.add(edge);
			return true;
		}
		return false;
	}

	public List<Edge<T>> getEdges() {
		return edges;
	}

	public int getEdgeCount() {
		return edgeCount;
	}
	
	// Util	
	public void print() {
		System.out.println("Vertexes:");
		for (int i = 0; i < vertexes.size(); i++) {
			vertexes.get(i).print();
		}
		System.out.println("Edges:");
		for (int i = 0; i < edges.size(); i++) {
			edges.get(i).print();
		}
	}

}
