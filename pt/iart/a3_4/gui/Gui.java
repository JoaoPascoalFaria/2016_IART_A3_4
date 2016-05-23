package pt.iart.a3_4.gui;

import pt.iart.a3_4.algorithm.A_Star;
import pt.iart.a3_4.util.Edge;
import pt.iart.a3_4.util.Graph;
import pt.iart.a3_4.util.Heuristic;
import pt.iart.a3_4.util.Location;
import pt.iart.a3_4.util.Options;
import pt.iart.a3_4.util.Transportation;
import pt.iart.a3_4.util.Vertex;

public class Gui {

	
	public static void main(String[] args) {
		
		Options options = Options.getInstance();
		
		// TODO Auto-generated method stub
		Graph g = new Graph();
		Vertex v1  = g.addVertex2(new Location("a", 0, 8));
		Vertex v2  = g.addVertex2(new Location("b", 3, 8));
		Vertex v3  = g.addVertex2(new Location("c", 1, 6));
		Vertex v4  = g.addVertex2(new Location("d", 0, 3));
		Vertex v5  = g.addVertex2(new Location("e", 3, 4));
		Vertex v6  = g.addVertex2(new Location("f", 4, 5));
		Vertex v7  = g.addVertex2(new Location("g", 3, 1));
		Vertex v8  = g.addVertex2(new Location("h", 7, 1));
		Vertex v9  = g.addVertex2(new Location("i", 8, 2));
		Vertex v10 = g.addVertex2(new Location("j", 10, 1));

		g.addEdge(v1, v2);
		g.addEdge(v1, v4);
		g.addEdge(v1, v6);
		
		g.addEdge(v2, v3);
		g.addEdge(v2, v4);
		Edge e = g.addEdge2(v2, v5);
		e.addTransportationT(Transportation.METRO, 7);//metro
		g.addEdge(v2, v6);
		
		g.addEdge(v3, v4);
		g.addEdge(v3, v5);
		g.addEdge(v3, v7);
		
		g.addEdge(v4, v7);

		g.addEdge(v5, v6);
		g.addEdge(v5, v7);
		g.addEdge(v5, v9);
		g.addEdge(v5, v10);

		g.addEdge(v6, v10);

		g.addEdge(v7, v8);
		g.addEdge(v7, v9);

		g.addEdge(v8, v9);
		g.addEdge(v8, v10);
		
		g.addEdge(v9, v10);
		
		System.out.println("printing graph");
		g.print();
		
		options.setSource(v2);
		options.setDestination(v9);
		options.setChosen_heuristic(Heuristic.DISTANCE);
		
		A_Star as = new A_Star(g);
		as.getPath().print();
	}

}
