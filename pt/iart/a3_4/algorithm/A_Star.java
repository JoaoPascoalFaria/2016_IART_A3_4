package pt.iart.a3_4.algorithm;

import java.util.HashSet;
import java.util.TreeSet;

import pt.iart.a3_4.util.Graph;
import pt.iart.a3_4.util.State;
import pt.iart.a3_4.util.Vertex;

public class A_Star {
	
	private Graph graph;
	private Graph mst;
	private Vertex start;
	private Vertex goal;
	
	// The set of nodes already evaluated
	private static HashSet<State> closedset;
	
	// The set of currently discovered nodes still to be evaluated.
    // Initially, only the start node is known.
	private static TreeSet<State> openset;
	
	/**
	 * 
	 * @param graph where A* will run
	 * @param start vertex
	 * @param goal vertex
	 */
	public A_Star(Graph graph, Vertex start, Vertex goal) {
		closedset = new HashSet<State>();
		openset = new TreeSet<State>();
		this.graph = graph;
		this.start = start;
		this.goal = goal;
		State initial = new State(0, heuristic_evaluation(start, goal), start);
		openset.add(initial);
		mst = graph.getMST();
	}

	public State getPath() {
		// A*
		return null;
	}
	
	private int heuristic_evaluation(Vertex v1, Vertex v2) {
		// value of mst from v1 to v2
		return 0;
	}
}
