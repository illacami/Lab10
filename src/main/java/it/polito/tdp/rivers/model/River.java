package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.rivers.db.RiversDAO;

public class River {
	private int id;
	private String name;
	private double flowAvg;
	private List<Flow> flows;
	private RiversDAO dao;
	
	public River(int id) {
		this.id = id;
	}

	public River(int id, String name) {
		this.dao = new RiversDAO();
		this.id = id;
		this.name = name;
		this.flows = new LinkedList<Flow>();
		dao.setAvgFlow(this);
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public double getFlowAvg() {
		return flowAvg;
	}

	public void setFlowAvg(double flowAvg) {
		this.flowAvg = flowAvg;
	}

	public void setFlows(List<Flow> flows) {
		this.flows = flows;
	}

	public List<Flow> getFlows() {
		if (flows == null)
			flows = new ArrayList<Flow>();
		return flows;
	}

	@Override
	public String toString() {
		return name + "\n";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		River other = (River) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	public LocalDate getFirstDate() {
		
		if(flows.isEmpty())
			return null;
		
		LocalDate date = LocalDate.parse("2021-12-31");
		
		for(Flow f : flows) {
			if(f.getDay().isBefore(date))
				date = f.getDay();
		}
		return date;
	}
	
	public LocalDate getLastDate() {
		
		if(flows.isEmpty())
			return null;
		
		LocalDate date = LocalDate.parse("1900-12-31");
		
		for(Flow f : flows) {
			if(f.getDay().isAfter(date))
				date = f.getDay();
		}
		return date;
	}
}
