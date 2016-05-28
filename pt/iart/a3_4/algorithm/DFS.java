package pt.iart.a3_4.algorithm;

import java.util.HashSet;

import pt.iart.a3_4.util.Edge;
import pt.iart.a3_4.util.Options;
import pt.iart.a3_4.util.State;
import pt.iart.a3_4.util.Transportation;
import pt.iart.a3_4.util.Vertex;

public class DFS {
	
	private static Options options = Options.getInstance();
	
	private DFS() {
	}
	
	/**
	 * 
	 * @return first found state with Depth First Search (DFS)
	 */
	public static State runDFS(){
		HashSet<State> visited = new HashSet<State>();
		State initial = new State(0, 0, options.getSource());
		return DFSAux(initial, options.getSource(), visited);
	}
	
	private static State DFSAux(State s, Vertex current, HashSet<State> visited) {
		visited.add(s);
		for( Edge e : current.getEdges()){
			Vertex v = e.otherVertex(current);
			for( Transportation t : e.getTransportations()) {
				State s2 = new State(s, e.otherVertex(current), e, t, 0);
				if( v.equals(options.getDestination()))
					return s2;
				if(!set_contains(visited, s2)){
					State s3 = DFSAux(s2, v, visited);
					if(s3 != null){
						return s3;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @description avoids using compareTo instead of equals
	 */
	private static boolean set_contains(HashSet<State> set, State s) {
		for(State st : set){
			if(st.equals(s)) return true;
		}
		return false;
	}
}
