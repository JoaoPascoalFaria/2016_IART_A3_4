package pt.iart.a3_4.algorithm;

import java.util.List;
import java.util.Map;

public class A_Star {
	// The set of nodes already evaluated
	private static List<> closedset;
	// The set of currently discovered nodes still to be evaluated.
    // Initially, only the start node is known.
	private static List<> openset;
	//f = g+h   g=custo ate ao local  h=estimativa do custo que falta ate a solucao
	private static Map<> g;
	private static Map<> f;
	
	public void run() {
		closedset = {};
		openset = {start};
		
	}
	
	private int h(){
		
		return 0;
	}
}
