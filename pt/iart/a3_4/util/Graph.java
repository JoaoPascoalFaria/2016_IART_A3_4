package pt.iart.a3_4.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.TreeSet;


public class Graph {
	
	private ArrayList<Vertex> vertexes;
	private ArrayList<Edge> edges;
	
	public Graph() {
		this.vertexes = new ArrayList<Vertex>();
		this.edges = new ArrayList<Edge>();
	}
	
	// Vertex
	public void removeVertex(Vertex v) {
		// TODO nao apaga tudo falta.. neighbors?.. vertex da lista..
		//não é utilizado
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
	
	//Edge
	public boolean addEdge(Vertex v1, Vertex v2, int time) {
		Edge edge = new Edge(v1, v2, time);
		return addEdge(edge);
	}
	
	public boolean addEdge(Vertex v1, Vertex v2) {
		Edge edge = new Edge(v1, v2);
		return addEdge(edge);
	}
	
	public Edge addEdge2(Vertex v1, Vertex v2, int time) {
		Edge edge = new Edge(v1, v2, time);
		addEdge(edge);
		return edge;
	}
	
	public Edge addEdge2(Vertex v1, Vertex v2) {
		Edge edge = new Edge(v1, v2);
		addEdge(edge);
		return edge;
	}
	
	private Edge addMSTEdge(Edge e){
		Vertex v1 = this.vertexes.get(this.vertexes.indexOf(e.getV1()));
		Vertex v2 = this.vertexes.get(this.vertexes.indexOf(e.getV2()));
		Edge edge = new Edge(v1,v2,e);
		addEdge(edge);
		return edge;
	}
	
	private void removeMSTEdge(Edge e){
		if(edges.contains(e)){
			e.getV1().removeMSTEdge(e);
			e.getV2().removeMSTEdge(e);
			e.getV1().removeMSTNeighbor(e.getV2());
			e.getV2().removeMSTNeighbor(e.getV1());
			edges.remove(e);
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
	
	// Util	
	public void print() {
		for (int i = 0; i < vertexes.size(); i++) {
			vertexes.get(i).print();
		}
		/*System.out.println("Edges:");
		for (double i = 0; i < edges.size(); i++) {
			edges.get(i).print();
		}/***/
	}
	
	public Graph getMST(){
		Graph mst = new Graph();
		for( Vertex v : this.vertexes){
			mst.vertexes.add(new Vertex(v));
		}
		
		TreeSet<Edge> edges = new TreeSet<Edge>(ecomp);
		edges.addAll(this.edges);
		
		for( Edge e : edges){
			Edge edge = mst.addMSTEdge(e);
			if( mst.checkMSTCycle()){
				mst.removeMSTEdge(edge);
			}
			if( mst.checkMSTCompleted()) break;
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

	private Comparator<Edge> ecomp = new Comparator<Edge>() {
		@Override
		public int compare(Edge e1, Edge e2) {
			double min = Integer.MAX_VALUE;
			double min2 = Integer.MAX_VALUE;
			for( Transportation t : e1.getTransportations()){
				if( e1.getCost(t).getDistance() < min) min = e1.getCost(t).getDistance();// TODO distance!?
			}
			for( Transportation t : e2.getTransportations()){
				if( e2.getCost(t).getDistance() < min2) min2 = e2.getCost(t).getDistance();// TODO distance!?
			}
			if(min<min2) return -1;
			//allow repeated values.
			if(e1.equals(e2)) return 0;
			return 1;
			/*if(min>min2) return 1;
			return 0;*/
		}
	};
}
