package pt.iart.a3_4.gui;

import pt.iart.a3_4.algorithm.A_Star;
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

		System.out.println("Initializing A*");
		A_Star as = new A_Star(g);
		System.out.println("Running A*");
		State result = as.getPath();
		System.out.println("Printing Result");
		result.print();
	}
	
	private static Graph createGraph(){
		
		Graph g = new Graph();

		Transportation metro = Transportation.METRO;
		Transportation boat = Transportation.BOAT;
		Transportation bus = Transportation.BUS;
		Transportation train = Transportation.TRAIN;
		Transportation walk = Transportation.WALK;

		Vertex trindade = g.addVertex2(new Location("Trindade", 41.152357, -8.609298));
		Vertex aeroporto = g.addVertex2(new Location("Aeroporto Francisco S� Carneiro", 41.237101, -8.668853));
		Vertex senhor_matosinhos = g.addVertex2(new Location("Senhor de Matosinhos", 41.188278, -8.684844));
		Vertex senhora_hora = g.addVertex2(new Location("Senhora da Hora", 41.188115, -8.654467));
		Vertex hospital = g.addVertex2(new Location("Hospital S�o Jo�o", 41.183236, -8.601897));
		Vertex forum_maia = g.addVertex2(new Location("F�rum Maia", 41.234638, -8.623684));
		Vertex dragao = g.addVertex2(new Location("Est�dio do Drag�o", 41.161277, -8.581477));
		Vertex gaia = g.addVertex2(new Location("C�mara de Gaia", 41.129863, -8.606131));
		Vertex ovidio = g.addVertex2(new Location("Santo Ovidio", 41.115500, -8.606468));
		
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
		Edge hospital_aeroporto = g.addEdge2(hospital, aeroporto);
		Edge forum_aeroporto = g.addEdge2(forum_maia, aeroporto);
		
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
		senhora_aeroporto.addTransportationTP(metro, 14, 1.5);
		forum_aeroporto.addTransportationTP(bus, 17, 1.2);
		hospital_aeroporto.addTransportationTP(bus, 50, 1.5);
		senhora_senhor.addTransportationTP(metro, 19, 1.2);
		senhora_forum.addTransportationTP(metro, 14, 1.2);
		senhora_dragao.addTransportationTP(metro, 22, 1.2);
		trindade_senhora.addTransportationTP(metro, 13, 1.2);
		trindade_hospital.addTransportationTP(metro, 12, 1.2);
		trindade_dragao.addTransportationTP(metro, 10, 1.2);
		trindade_gaia.addTransportationTP(metro, 9, 1.2);
		trindade_ovidio.addTransportationTP(metro, 14, 1.2);
		trindade_aeroporto.addTransportationTP(bus, 28, 1.85);
		trindade_senhor.addTransportationTP(metro, 33, 1.5);
		trindade_forum.addTransportationTP(bus, 25, 1.5);
		gaia_ovidio.addTransportationTP(metro, 5, 1.2);
		gaia_hospital.addTransportationTP(metro, 22, 1.2);
		ovidio_hospital.addTransportationTP(metro, 27, 1.2);
		dragao_senhor.addTransportationTP(metro, 41, 1.5);
		dragao_aeroporto.addTransportationTP(metro, 35, 1.85);
		
		
		options.setSource(gaia);
		options.setDestination(forum_maia);
		
		return g;
	}
	
}
