package it.polito.tdp.PremierLeague.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private Graph<Player, DefaultWeightedEdge> grafo;
	private PremierLeagueDAO dao;
	private List<EfficienzaP> EA ;
	private Map<Integer,Player> idMap;
	 
	public Model() {
		dao=new PremierLeagueDAO();
		idMap =new HashMap<>();
		this.dao.listAllPlayers(idMap);
	}
	
	public List<Match> listAllMatches(){
		return this.dao.listAllMatches();
	}
	
	public List<EfficienzaP> getInfo(Match m,Map<Integer, Player>idMap)
	{
		EA =this.dao.getInfo(m, idMap);
		return EA;
	}
	
	public void creaGrafo(Match m )
	{
		grafo = new SimpleDirectedWeightedGraph<Player,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, this.dao.getVertici(m,idMap));
		
		for(EfficienzaP e :this.dao.getInfo(m, idMap))
		{
			if(e.getPeso() >= 0) {
				//p1 meglio di p2
				if(grafo.containsVertex(e.getPlayer1()) && grafo.containsVertex(e.getPlayer2())) {
					Graphs.addEdgeWithVertices(this.grafo, e.getPlayer1(), 
							e.getPlayer2(), e.getPeso());
				}
			} else {
				//p2 meglio di p1
				if(grafo.containsVertex(e.getPlayer1()) && grafo.containsVertex(e.getPlayer2())) {
					Graphs.addEdgeWithVertices(this.grafo, e.getPlayer2(), 
							e.getPlayer1(), (-1) * e.getPeso());
				}
				
			}
			
		}
		
	}
	
	public int getNVertici()
	{
		return this.grafo.vertexSet().size();
	}
	public int getNArchi()
	{
		return this.grafo.edgeSet().size();
	}
	
	public GiocatoreMigliore getMigliore() {
		
		Player best = null;
		Double efficienzaMax= (double)Integer.MIN_VALUE;
		
		for(Player p : this.grafo.vertexSet())
		{
			double PesoIn = 0.0;
			
			for(DefaultWeightedEdge dwe: this.grafo.incomingEdgesOf(p))
			{
				PesoIn+=this.grafo.getEdgeWeight(dwe);
			}
			
			double PesoOut = 0.0;
			for(DefaultWeightedEdge dwe: this.grafo.outgoingEdgesOf(p))
			{
				PesoOut+=this.grafo.getEdgeWeight(dwe);
			}
			
			double PesoTot = PesoOut-PesoIn;
			
			if(PesoTot>efficienzaMax)
			{
				best = p;
				efficienzaMax = PesoTot;
			}
			
		}
		
		return new GiocatoreMigliore(best, efficienzaMax);
		
		
		}
	
	
	
}
