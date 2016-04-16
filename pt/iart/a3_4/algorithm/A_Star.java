package pt.iart.a3_4.algorithm;

import java.util.HashSet;
import java.util.TreeSet;

import pt.iart.a3_4.util.Edge;
import pt.iart.a3_4.util.Graph;
import pt.iart.a3_4.util.Heuristic;
import pt.iart.a3_4.util.State;
import pt.iart.a3_4.util.Transportation;
import pt.iart.a3_4.util.Vertex;

public class A_Star {
	
	private Graph graph;
	private Graph mst;
	private Vertex start;
	private Vertex goal;
	private Heuristic heuristic;
	
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
	public A_Star(Graph graph, Vertex start, Vertex goal, Heuristic h) {
		//closedset = new HashSet<State>();
		closedset = new HashSet<Vertex>();
		openset = new TreeSet<State>();
		this.graph = graph;
		this.start = start;
		this.goal = goal;
		this.heuristic = h;
		mst = this.graph.getMST();
		State initial = new State(0, heuristic_evaluation(this.start, this.goal), this.start);
		openset.add(initial);
	}

	public State getPath() {
		State current;
		Vertex cVertex;
		
		while(!openset.isEmpty()){
			
			current = openset.pollFirst();
			cVertex = current.currentVertex();
			
			System.out.println("analizing vertex "+cVertex.getInfo().getName()+" with cost g="+current.getG());
			System.out.print("openset: ");for( State s : this.openset) {
				System.out.print(s.currentVertex().getInfo().getName()+"+ ");
			}System.out.println();
			System.out.print("closedset: ");for( Vertex v : this.closedset){
				System.out.print(v.getInfo().getName()+"+ ");
			}System.out.println();
			
			if(cVertex.equals(this.goal))
				return current;
			this.closedset.add(cVertex);
			
			for( Edge e : cVertex.getEdges()){
				
				if( closedset.contains(e.otherVertex(cVertex)))
					continue;
				
				for( Transportation t : e.getTransportations()) {
					
					double h = heuristic_evaluation(cVertex, this.goal);
					State s = new State(current, e.otherVertex(cVertex), e, t, h, this.heuristic);
					
					System.out.println("<neighbor "+s.currentVertex().getInfo().getName()+" "+(s.getG()+s.getH())+">");
					
					if(!openset_contains(s) /*!openset.contains(s)*/)
						openset.add(s);
					else {
						System.out.println("<contains "+s.currentVertex().getInfo().getName()+">");
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

	private double heuristic_evaluation(Vertex v1, Vertex v2){
		if( v1==null || v2==null || v1.equals(v2)) return 0;//infinity instea 0?
		if( this.heuristic == Heuristic.DISTANCE)
			return heuristic_evaluation_distance(v1,v2);
		else if( this.heuristic == Heuristic.TIME)
			return heuristic_evaluation_time(v1,v2);
		else if( this.heuristic == Heuristic.WALK_DISTANCE)//TODO
			return heuristic_evaluation_walk_distance(v1,v2);
		return heuristic_evaluation_swaps(v1,v2);//TODO SWAPS
		//return heuristic_evaluation_distance(v1,v2); // distance
	}
	
	private double heuristic_evaluation_swaps(Vertex v1, Vertex v2) {
		// TODO Auto-generated method stub
		return 0;
	}

	private double heuristic_evaluation_walk_distance(Vertex v1, Vertex v2) {
		// TODO Auto-generated method stub
		return 0;
	}

	private double heuristic_evaluation_time(Vertex v1, Vertex v2) {
		// walking at 5 Km/h nothing should be slower than walking
		return 0;//5*Math.sqrt( Math.pow(v2.getInfo().getX() - v1.getInfo().getX(), 2) + Math.pow(v2.getInfo().getY() - v1.getInfo().getY(), 2));
	}
	
	private double heuristic_evaluation_distance(Vertex v1, Vertex v2) {
		return Math.sqrt( Math.pow(v2.getInfo().getX() - v1.getInfo().getX(), 2) + Math.pow(v2.getInfo().getY() - v1.getInfo().getY(), 2));
	}
	
	/**
	 * not good. overestimates. could be used if we needed to pass trough some intermediate points
	 */
	private double heuristic_evaluation_mst(Vertex v1, Vertex v2) {
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
	
	private double pathCostMST(Vertex v1, Vertex v2){
		HashSet<Vertex> visited = new HashSet<Vertex>();
		return pathCostMSTAux(v1, v2, visited);
	}
	
	private double pathCostMSTAux(Vertex current, Vertex goal, HashSet<Vertex> visited) {
		visited.add(current);
		for( Edge e : current.getEdges()){
			Vertex v = e.otherVertex(current);
			if( v.equals(goal))
				return e.getCost(e.getTransportations().iterator().next()).getDistance();// TODO distance!?
			if(!visited.contains(v)){
				double r = pathCostMSTAux(v, goal, visited);
				if(r>-1){
					return r + e.getCost(e.getTransportations().iterator().next()).getDistance();// TODO distance!?
				}
			}
		}
		return -1;
	}
	
	/**
	 * 
	 * @description avoids using compareTo instead of equals
	 */
	private boolean openset_contains(State s) {
		for(State st : this.openset){
			if(st.equals(s)) return true;
		}
		return false;
	}
}
