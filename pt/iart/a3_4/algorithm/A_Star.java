package pt.iart.a3_4.algorithm;

import java.util.HashSet;
import java.util.TreeSet;

import pt.iart.a3_4.util.*;

public class A_Star {
	
	private Graph graph;
	private Graph mst;
	private Options options = Options.getInstance();
	
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
	public A_Star(Graph graph) {
		//closedset = new HashSet<State>();
		closedset = new HashSet<Vertex>();
		openset = new TreeSet<State>();
		this.graph = graph;
		mst = this.graph.getMST();
		State initial = new State(0, heuristic_evaluation(options.getSource(), options.getDestination()), options.getSource());
		openset.add(initial);
	}

	public State getPath() {
		State current;
		Vertex cVertex;
		
		while(!openset.isEmpty()){
			
			current = openset.pollFirst();
			cVertex = current.currentVertex();
			
			/*System.out.println("analizing vertex "+cVertex.getInfo().getName()+" with cost g="+current.getG());
			System.out.print("openset: ");for( State s : this.openset) {
				System.out.print(s.currentVertex().getInfo().getName()+"+ ");
			}System.out.println();
			System.out.print("closedset: ");for( Vertex v : this.closedset){
				System.out.print(v.getInfo().getName()+"+ ");
			}System.out.println();
			*/
			if(cVertex.equals(options.getDestination()))
				return current;
			this.closedset.add(cVertex);
			
			for( Edge e : cVertex.getEdges()){
				
				if( closedset.contains(e.otherVertex(cVertex)))
					continue;
				
				for( Transportation t : e.getTransportations()) {
					
					double h = heuristic_evaluation(cVertex, options.getDestination());
					State s = new State(current, e.otherVertex(cVertex), e, t, h);
					
					//System.out.println("<neighbor "+s.currentVertex().getInfo().getName()+" g="+s.getG()+ " h=" + s.getH()+" by "+t.toString()+">");
					
					if(!openset_contains(s) /*!openset.contains(s)*/)
						openset.add(s);
					else {
						//System.out.println("<contains "+s.currentVertex().getInfo().getName()+">");
						for( State st : openset) {
							if(st.equals(s)){
								if(st.getG() > s.getG()){
									openset.remove(st);
									openset.add(s);
									if(!openset_contains(s)){
										System.err.println("Item failed to rejoin openset");
										System.err.print("openset: ");for( State s1 : this.openset) {
											System.err.print(s1.currentVertex().getInfo().getName()+" + ");
										}System.err.println();
									}
								}
								break;
							}
						}
					}
				}
			}
		}
		System.err.println("A* FAILED");
		System.err.print("openset: ");for( State s : this.openset) {
			System.err.print(s.currentVertex().getInfo().getName()+" + ");
		}System.err.println();
		System.err.print("closedset: ");for( Vertex v : this.closedset){
			System.err.print(v.getInfo().getName()+" + ");
		}System.err.println();
		return null;
	}

	private double heuristic_evaluation(Vertex v1, Vertex v2){
		if( v1==null || v2==null || v1.equals(v2)) return 999999;
		/*if(options.getChosen_heuristic() == Heuristic.TIME || options.getChosen_heuristic() == Heuristic.PRICE)
			return heuristic_evaluation_time(v1,v2);
		return heuristic_evaluation_distance(v1,v2);*/
		Heuristic heuristic = options.getChosen_heuristic();
		if( heuristic == Heuristic.DISTANCE || heuristic == Heuristic.WALK_DISTANCE || heuristic == Heuristic.SWAPS)
			return 0.5*heuristic_evaluation_distance(v1,v2) + 0.5*heuristic_evaluation_time(v1,v2)/12;//12min is equivalent to 1km
		else if( heuristic == Heuristic.TIME)
			return heuristic_evaluation_time(v1,v2);
		else if( heuristic == Heuristic.PRICE)
			return 0.5*heuristic_evaluation_time(v1,v2)/20;//20mins is equivalent to 1€
		System.err.println("heuristic failed");
		return 999999;
	}

	private double heuristic_evaluation_time(Vertex v1, Vertex v2) {
		// traint at 200 Km/h nothing should be faster than train
		return Math.sqrt( Math.pow(v2.getInfo().getX() - v1.getInfo().getX(), 2) + Math.pow(v2.getInfo().getY() - v1.getInfo().getY(), 2))/200*60;
		//return DistanceCalculator.distance(v1.getInfo().getX(), v1.getInfo().getY(), v2.getInfo().getX(), v2.getInfo().getY(), "k")/200*60;
	}
	
	private double heuristic_evaluation_distance(Vertex v1, Vertex v2) {
		return Math.sqrt( Math.pow(v2.getInfo().getX() - v1.getInfo().getX(), 2) + Math.pow(v2.getInfo().getY() - v1.getInfo().getY(), 2));
		//return DistanceCalculator.distance(v1.getInfo().getX(), v1.getInfo().getY(), v2.getInfo().getX(), v2.getInfo().getY(), "k");
	}
	
	/**
	 * not good. overestimates. could be used if we needed to pass trough some intermediate points
	 */
	@SuppressWarnings("unused")
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
