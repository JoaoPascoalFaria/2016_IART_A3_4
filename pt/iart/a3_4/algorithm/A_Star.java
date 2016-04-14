package pt.iart.a3_4.algorithm;

import java.util.HashSet;
import java.util.TreeSet;

import pt.iart.a3_4.util.Edge;
import pt.iart.a3_4.util.Graph;
import pt.iart.a3_4.util.State;
import pt.iart.a3_4.util.Transportation;
import pt.iart.a3_4.util.Vertex;

public class A_Star {
	
	private Graph graph;
	private Graph mst;
	private Vertex start;
	private Vertex goal;
	
	// The set of nodes already evaluated
	//private HashSet<State> closedset;´
	// maybe I don't need entire states because I never want to go back to same vertex?
	private HashSet<Vertex> closedset;
	
	// The set of currently discovered nodes still to be evaluated.
    // Initially, only the start node is known.
	private TreeSet<State> openset;
	
	/**
	 * 
	 * @param graph where A* will run
	 * @param start vertex
	 * @param goal vertex
	 */
	public A_Star(Graph graph, Vertex start, Vertex goal) {
		//closedset = new HashSet<State>();
		closedset = new HashSet<Vertex>();
		openset = new TreeSet<State>();
		this.graph = graph;
		this.start = start;
		this.goal = goal;
		State initial = new State(0, heuristic_evaluation(this.start, this.goal), this.start);
		openset.add(initial);
		mst = this.graph.getMST();
	}

	public State getPath() {
		State current;
		Vertex cVertex;
		
		while(!openset.isEmpty()){
			
			current = openset.pollFirst();
			cVertex = current.currentVertex();
			
			if(cVertex.equals(this.goal))
				return current;
			closedset.add(cVertex);
			
			for( Edge e : cVertex.getEdges()){
				
				if( closedset.contains(e.otherVertex(cVertex)))
					continue;
				
				for( Transportation t : e.getTransportations()) {
					
					int h = heuristic_evaluation(cVertex, this.goal);
					State s = new State(current, e.otherVertex(cVertex), e, t, h);
					
					if( !openset.contains(s))
						openset.add(s);
					else {
						for( State st : openset) {
							if(st.equals(s)){
								if(st.getG() > s.getG()){
									openset.remove(st);
									openset.add(s);
								}
								break;
							}
						}
					}
				}
			}
		}
		return null;
	}
	
	private int heuristic_evaluation(Vertex v1, Vertex v2) {
		if( v1.equals(v2) || v1==null || v2==null) return 0;
		Vertex v12=null;
		Vertex v22=null;
		for( Vertex v : mst.getVertexes()) {
			if( v12==null && v.equals(v1))
				v12 = v;
			else if( v22==null && v.equals(v2))
				v22 = v;
			if(v12!=null && v22!=null)
				break;
		}
		return pathCostMST(v12,v22);
	}
	
	private int pathCostMST(Vertex v1, Vertex v2){
		HashSet<Vertex> visited = new HashSet<Vertex>();
		return pathCostMSTAux(v1, v2, visited);
	}
	
	private int pathCostMSTAux(Vertex current, Vertex goal, HashSet<Vertex> visited) {
		visited.add(current);
		for( Edge e : current.getEdges()){
			Vertex v = e.otherVertex(current);
			if( v.equals(goal))
				return e.getCost(e.getTransportations().iterator().next());
			if(!visited.contains(v)){
				int r = pathCostMSTAux(v, goal, visited);
				if(r>-1){
					return r + e.getCost(e.getTransportations().iterator().next());
				}
			}
		}
		return -1;
	}
}
