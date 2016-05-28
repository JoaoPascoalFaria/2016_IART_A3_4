package pt.iart.a3_4.algorithm;

import java.util.LinkedList;

import pt.iart.a3_4.util.Edge;
import pt.iart.a3_4.util.Options;
import pt.iart.a3_4.util.State;
import pt.iart.a3_4.util.Transportation;
import pt.iart.a3_4.util.Vertex;

public class BFS {
	
	private static Options options = Options.getInstance();
	
	private BFS() {
	}
	
	/**
	 * 
	 * @return first found state with Breadth First Search (BFS)
	 */
	public static State runBFS(){

		LinkedList<State> queue = new LinkedList<State>();
		State current = new State(0, 0, options.getSource());
		Vertex cVertex;
		queue.add(current);
		int dummy = 1;
		int i = 0;
		
		while(i<dummy){

			current = queue.get(i);
			cVertex = current.currentVertex();
			
			if(cVertex.equals(options.getDestination()))
				return current;
			
			for( Edge e : cVertex.getEdges()){
				for( Transportation t : e.getTransportations()) {
					State s = new State(current, e.otherVertex(cVertex), e, t, 0);
					if(!set_contains(queue, s)){
						queue.add(s);
						dummy++;
					}
				}
			}
			i++;
		}
		return null;
	}
	
	private static boolean set_contains(LinkedList<State> set, State s) {
		for(State st : set){
			if(st.equals(s)) return true;
		}
		return false;
	}
}
