package pt.iart.a3_4.gui;

import pt.iart.a3_4.algorithm.A_Star;
import pt.iart.a3_4.algorithm.BFS;
import pt.iart.a3_4.algorithm.DFS;
import pt.iart.a3_4.util.Edge;
import pt.iart.a3_4.util.Graph;
import pt.iart.a3_4.util.Heuristic;
import pt.iart.a3_4.util.Location;
import pt.iart.a3_4.util.Options;
import pt.iart.a3_4.util.State;
import pt.iart.a3_4.util.Transportation;
import pt.iart.a3_4.util.Vertex;

public class Gui {

	static Options options = Options.getInstance();
	
	public static void main(String[] args) {
		
		// TODO Auto-generated method stub
		Graph g = createGraph();
		
		
		System.out.println("Printing Graph");
		g.print();
		
		options.setChosen_heuristic(Heuristic.TIME);
		//options.setChosen_heuristic(Heuristic.PRICE);
		//options.setChosen_heuristic(Heuristic.SWAPS);
		//options.setChosen_heuristic(Heuristic.TIME);
		//options.setChosen_heuristic(Heuristic.WALK_DISTANCE);
		//options.avoidTransportation(Transportation.METRO, true);
		//options.avoidTransportation(Transportation.WALK, true);
		//options.setMax_price(0);
		
		long startTime, endTime;
		
		System.out.println("\nInitializing A*");
		A_Star as = new A_Star(g);
		System.out.println("Running A*");
		startTime = System.nanoTime();
		State result = as.getPath();
		endTime = System.nanoTime();
		System.out.println("A* took "+String.format("%.9f", (endTime-startTime)/1000000000f)+" seconds to execute\nPrinting Result");
		result.print();
		
		System.out.println("Running DFS");
		startTime = System.nanoTime();
		result = DFS.runDFS();
		endTime = System.nanoTime();
		System.out.println("DFS took "+String.format("%.9f", (endTime-startTime)/1000000000f)+" seconds to execute\nPrinting Result");
		result.print();
		
		System.out.println("Running BFS");
		startTime = System.nanoTime();
		result = BFS.runBFS();
		endTime = System.nanoTime();
		System.out.println("BFS took "+String.format("%.9f", (endTime-startTime)/1000000000f)+" seconds to execute\nPrinting Result");
		result.print();
	}
	
	@SuppressWarnings("unused")
	private static Graph createGraph(){
		
		Graph g = new Graph();

		Transportation metro = Transportation.METRO;
		Transportation bus = Transportation.BUS;
		Transportation train = Transportation.TRAIN;
		Transportation walk = Transportation.WALK;

		//metro
		Vertex trindade = g.addVertex2(new Location("Trindade", 41.152357, -8.609298));
		Vertex aeroporto = g.addVertex2(new Location("Aeroporto Francisco Sá Carneiro", 41.237101, -8.668853));
		Vertex senhor_matosinhos = g.addVertex2(new Location("Senhor de Matosinhos", 41.188278, -8.684844));
		Vertex senhora_hora = g.addVertex2(new Location("Senhora da Hora", 41.188115, -8.654467));
		Vertex hospital = g.addVertex2(new Location("Hospital São João", 41.183236, -8.601897));
		Vertex forum_maia = g.addVertex2(new Location("Fórum Maia", 41.234638, -8.623684));
		Vertex dragao = g.addVertex2(new Location("Estádio do Dragão", 41.161277, -8.581477));
		Vertex gaia = g.addVertex2(new Location("Câmara de Gaia", 41.129863, -8.606131));
		Vertex ovidio = g.addVertex2(new Location("Santo Ovidio", 41.115500, -8.606468));
		//bus
		Vertex forum_maia_b = g.addVertex2(new Location("Maia", 41.232805, -8.623769));
		Vertex dragao_b = g.addVertex2(new Location("Estádio do Dragão", 41.158883, -8.582854));
		Vertex gaia_b = g.addVertex2(new Location("Câmara de Gaia", 41.130332, -8.606570));
		Vertex ermesinde_b = g.addVertex2(new Location("Ermesinde", 41.215894, -8.552604));
		//train
		//to simplify, câmara de gaia and estádio do dragão metro stations replace general torres and campanha respectively
		Vertex ermesinde_t = g.addVertex2(new Location("Ermesinde", 41.216927, -8.553934));
		
		Edge.setWalkingByDefault(false);
		//metro
		Edge senhora_aeroporto = g.addEdge2(senhora_hora, aeroporto);
		Edge senhora_senhor = g.addEdge2(senhora_hora, senhor_matosinhos);
		Edge senhora_forum = g.addEdge2(senhora_hora, forum_maia);
		Edge senhora_dragao = g.addEdge2(senhora_hora, dragao);
		Edge trindade_senhora = g.addEdge2(trindade, senhora_hora);
		Edge trindade_hospital = g.addEdge2(trindade, hospital);
		Edge trindade_dragao = g.addEdge2(trindade, dragao);
		Edge trindade_gaia = g.addEdge2(trindade, gaia);
		Edge trindade_ovidio = g.addEdge2(trindade, ovidio);
		Edge trindade_aeroporto = g.addEdge2(trindade, aeroporto);
		Edge trindade_senhor = g.addEdge2(trindade, senhor_matosinhos);
		Edge trindade_forum = g.addEdge2(trindade, forum_maia);
		Edge gaia_ovidio = g.addEdge2(gaia, ovidio);
		Edge gaia_hospital = g.addEdge2(gaia, hospital);
		Edge ovidio_hospital = g.addEdge2(ovidio, hospital);
		Edge dragao_senhor = g.addEdge2(dragao, senhor_matosinhos);
		Edge dragao_aeroporto = g.addEdge2(dragao, aeroporto);
		//bus
		Edge trindade_forum_b = g.addEdge2(trindade, forum_maia_b);
		Edge trindade_dragao_b = g.addEdge2(trindade, dragao_b);
		Edge trindade_gaia_b = g.addEdge2(trindade, gaia_b);
		Edge gaia_ovidio_b = g.addEdge2(gaia_b, ovidio);
		Edge hospital_aeroporto = g.addEdge2(hospital, aeroporto);
		Edge forum_aeroporto = g.addEdge2(forum_maia_b, aeroporto);
		Edge senhor_aeroporto = g.addEdge2(senhor_matosinhos, aeroporto);
		Edge trindade_ermesinde = g.addEdge2(trindade, ermesinde_b);
		Edge hospital_ermesinde = g.addEdge2(hospital, ermesinde_b);
		//train
		Edge gaia_dragao = g.addEdge2(gaia, dragao);
		Edge gaia_ermesinde = g.addEdge2(gaia, ermesinde_t);
		Edge dragao_ermesinde = g.addEdge2(dragao, ermesinde_t);
		//walking
		Edge.setWalkingByDefault(true);
		Edge forum_forum_b = g.addEdge2(forum_maia_b, forum_maia);
		Edge dragao_dragao_b = g.addEdge2(dragao, dragao_b);
		Edge gaia_gaia_b = g.addEdge2(gaia, gaia_b);
		Edge ermesinde_t_ermesinde_b = g.addEdge2(ermesinde_t, ermesinde_b);
		
		// metro
		senhora_aeroporto.addTransportationTP(metro, 14, 1.5);
		senhora_senhor.addTransportationTP(metro, 19, 1.2);
		senhora_forum.addTransportationTP(metro, 14, 1.2);
		senhora_dragao.addTransportationTP(metro, 22, 1.2);
		trindade_senhora.addTransportationTP(metro, 13, 1.2);
		trindade_hospital.addTransportationTP(metro, 12, 1.2);
		trindade_dragao.addTransportationTP(metro, 10, 1.2);
		trindade_gaia.addTransportationTP(metro, 9, 1.2);
		trindade_ovidio.addTransportationTP(metro, 14, 1.2);
		trindade_aeroporto.addTransportationTP(metro, 28, 1.85);
		trindade_senhor.addTransportationTP(metro, 33, 1.5);
		trindade_forum.addTransportationTP(metro, 27, 1.5);
		gaia_ovidio.addTransportationTP(metro, 5, 1.2);
		gaia_hospital.addTransportationTP(metro, 22, 1.2);
		ovidio_hospital.addTransportationTP(metro, 27, 1.2);
		dragao_senhor.addTransportationTP(metro, 41, 1.5);
		dragao_aeroporto.addTransportationTP(metro, 35, 1.85);
		// bus
		forum_aeroporto.addTransportationTP(bus, 17, 1.2);
		hospital_aeroporto.addTransportationTP(bus, 50, 1.5);
		senhor_aeroporto.addTransportationTP(bus, 21, 1.5);
		trindade_ermesinde.addTransportationTP(bus, 34, 1.5);
		hospital_ermesinde.addTransportationTP(bus, 25, 1.2);
		senhora_senhor.addTransportationTP(bus, 19, 1.2);
		trindade_hospital.addTransportationTP(bus, 20, 1.2);
		trindade_dragao_b.addTransportationTP(bus, 12, 1.2);
		trindade_gaia_b.addTransportationTP(bus, 17, 1.2);
		trindade_ovidio.addTransportationTP(bus, 27, 1.2);
		trindade_aeroporto.addTransportationTP(bus, 28, 1.85);
		trindade_forum_b.addTransportationTP(bus, 36, 1.5);
		gaia_ovidio_b.addTransportationTP(bus, 11, 1.2);
		//train
		dragao_ermesinde.addTransportationTP(train, 11, 1.5);
		gaia_ermesinde.addTransportationTP(train, 15, 1.5);
		gaia_dragao.addTransportationTP(train, 4, 1.2);
		
		options.setSource(gaia);
		options.setDestination(forum_maia);
		
		return g;
	}
	
}
