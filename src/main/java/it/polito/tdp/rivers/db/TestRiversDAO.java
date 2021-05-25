package it.polito.tdp.rivers.db;

import java.util.Map;

import it.polito.tdp.rivers.model.River;

public class TestRiversDAO {

	public static void main(String[] args) {
		
		RiversDAO dao = new RiversDAO();
		
		Map<Integer, River> rivers = dao.getAllRivers();
		
		System.out.println("FIUMI:");
		
		System.out.println(rivers);
		
		//System.out.println("Registrazioni di portate:");
		
		System.out.println(dao.getAllFlows(rivers).values());
	}

}
