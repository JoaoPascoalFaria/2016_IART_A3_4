package pt.iart.a3_4.gui;

import pt.iart.a3_4.algorithm.A_Star;
import pt.iart.a3_4.util.Graph;
import pt.iart.a3_4.util.Location;
import pt.iart.a3_4.util.Vertex;

public class Gui {

	public static void main(String[] args) {
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

		g.addEdge2(v1, v2, 3);
		g.addEdge2(v1, v4, 6);
		g.addEdge2(v1, v6, 9);
		
		g.addEdge2(v2, v3, 2);
		g.addEdge2(v2, v4, 4);
		g.addEdge2(v2, v5, 9);
		g.addEdge2(v2, v6, 9);
		
		g.addEdge2(v3, v4, 2);
		g.addEdge2(v3, v5, 8);
		g.addEdge2(v3, v7, 9);
		
		g.addEdge2(v4, v7, 9);

		g.addEdge2(v5, v6, 8);
		g.addEdge2(v5, v7, 7);
		g.addEdge2(v5, v9, 9);
		g.addEdge2(v5, v10, 10);

		g.addEdge2(v6, v10, 18);

		g.addEdge2(v7, v8, 4);
		g.addEdge2(v7, v9, 5);

		g.addEdge2(v8, v9, 1);
		g.addEdge2(v8, v10, 4);
		
		g.addEdge2(v9, v10, 3);
		
		System.out.println("printing graph");
		g.print();
		Graph mst = g.getMST();
		System.out.println("printing mst");
		mst.print();
		
		A_Star as = new A_Star(g, v2, v9);
		as.getPath().print();
	}

}
