package pt.iart.a3_4.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.TreeSet;


public class Graph {
	
	private ArrayList<Vertex> vertexes;
	private ArrayList<Edge> edges;
	
	private int vertexCount;
	private int edgeCount;
	
	public Graph() {
		this.vertexes = new ArrayList<Vertex>();
		this.edges = new ArrayList<Edge>();
		this.vertexCount = 0;
		this.edgeCount = 0;
	}
	
	// Vertex
	public void removeVertex(Vertex v) {
		// TODO nao apaga tudo
		for(Edge e : v.getEdges()) {
			this.edges.remove(e);
		}
		v.clearEdges();
	}

	public void addVertex(Location l) {
		Vertex v = new Vertex(l);
		vertexes.add(v);
	}
	
	public Vertex addVertex2(Location l) {
		Vertex v = new Vertex(l);
		vertexes.add(v);
		return v;
	}
	
	public void addVertex(Vertex v) {
		vertexes.add(v);
	}

	public ArrayList<Vertex> getVertexes() {
		return vertexes;
	}

	public int getVertexCount() {
		return vertexCount;
	}

	//Edge
	public boolean addEdge(Vertex v1, Vertex v2, int weight) {
		Edge edge = new Edge(v1, v2, weight);
		return addEdge(edge);
	}
	
	public boolean addEdge(Vertex v1, Vertex v2) {
		Edge edge = new Edge(v1, v2);
		return addEdge(edge);
	}
	
	public Edge addEdge2(Vertex v1, Vertex v2) {
		Edge edge = new Edge(v1, v2);
		addEdge(edge);
		return edge;
	}
	
	private void addMSTEdge(Edge e){
		Vertex v1 = this.vertexes.get(this.vertexes.indexOf(e.getV1()));
		Vertex v2 = this.vertexes.get(this.vertexes.indexOf(e.getV2()));
		Edge edge = new Edge(v1,v2,e);
		addEdge(edge);
	}
	
	private void removeMSTEdge(Edge e){
		if(edges.contains(e)){
			edges.remove(e);
			e.getV1().removeMSTEdge(e);
			e.getV2().removeMSTEdge(e);
			e.getV1().removeMSTNeighbor(e.getV2());
			e.getV2().removeMSTNeighbor(e.getV1());
		}
	}
	
	private boolean addEdge(Edge e){
		if (!edges.contains(e)) {
			edges.add(e);
			return true;
		}
		return false;
	}

	public ArrayList<Edge> getEdges() {
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
	
	public Graph getMST(){
		Graph mst = new Graph();
		mst.vertexCount = this.vertexCount;
		for( Vertex v : this.vertexes){
			mst.vertexes.add(new Vertex(v));
		}
		
		TreeSet<Edge> edges = new TreeSet<Edge>(ecomp);
		edges.addAll(this.edges);
		
		for( Edge e : edges){
			mst.addMSTEdge(e);
			if( checkMSTCycle()) mst.removeMSTEdge(e);
			if( checkMSTCompleted()) break;
		}
		
		return mst;
	}

	private boolean checkMSTCycle() {
		HashSet<Vertex> visited = new HashSet<Vertex>();
		for( Vertex v : this.vertexes){
			if(!visited.contains(v)){
				if(checkMSTCycleAux(v, visited, null))
					return true;
			}
		}
		return false;
	}
	
	private boolean checkMSTCycleAux(Vertex v, HashSet<Vertex> visited, Vertex previous) {
		visited.add(v);
		for( Vertex v2 : v.getNeighbors()){
			if(!visited.contains(v2)){
				if(checkMSTCycleAux(v2, visited, v))
					return true;
			}
			else if(!v2.equals(previous))
				return true;
		}
		return false;
	}

	private boolean checkMSTCompleted() {
		HashSet<Vertex> visited = new HashSet<Vertex>();
		checkMSTCompletedAux(this.vertexes.get(0), visited);
		for( Vertex v : this.vertexes){
			if(!visited.contains(v)){
				return false;
			}
		}
		return true;
	}

	private void checkMSTCompletedAux(Vertex i, HashSet<Vertex> visited) {
		visited.add(i);
		for( Vertex v : i.getNeighbors()){
			if(!visited.contains(v)){
				checkMSTCompletedAux(v, visited);
			}
		}
	}

	Comparator<Edge> ecomp = new Comparator<Edge>() {
		@Override
		public int compare(Edge e1, Edge e2) {
			int min = Integer.MAX_VALUE;
			int min2 = Integer.MAX_VALUE;
			for( Transportation t : e1.getTransportations()){
				if( e1.getCost(t) < min) min = e1.getCost(t);
			}
			for( Transportation t : e2.getTransportations()){
				if( e2.getCost(t) < min2) min2 = e2.getCost(t);
			}
			if(min<min2) return -1;
			if(min>min2) return 1;
			return 0;
		}
	};
}
