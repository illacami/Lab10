package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {

	private RiversDAO dao;
	private Map<Integer, River> rivers;
	private Map<Integer, Flow> idFlows;
	
	
	public Model() {
		super();
		 this.dao = new RiversDAO();
		
		this.rivers = new HashMap<Integer, River>();
		this.rivers = this.getAllRivers();
		
		this.idFlows = new HashMap<Integer, Flow>();
	}



	public Map<Integer, River> getAllRivers(){
		return this.dao.getAllRivers();
	}
	
	public Map<Integer, Flow> getAllFlows(){
		return this.dao.getAllFlows(rivers);
	}
	
	public int getTot(River river) {
		 return dao.setAvgFlow(river);
	}
	
	public double getAvg(River river) {
		return river.getFlowAvg();
	}
	
	public LocalDate getFirstDate(River river) {
		return river.getFirstDate();
	}
	
	public LocalDate getLastDate(River river) {
		return river.getLastDate();
	}
	
	
	
}
