package it.polito.tdp.rivers.db;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RiversDAO {

	public Map<Integer,River> getAllRivers() {
		
		final String sql = "SELECT id, name FROM river";

		Map<Integer,River> rivers = new HashMap<Integer,River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				rivers.put(res.getInt("id"),new River(res.getInt("id"), res.getString("name")));
			}

			conn.close();
			st.close();
			res.close();
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}

		return rivers;
	}
	
	public Map<Integer,Flow> getAllFlows(Map<Integer,River> rivers) {
		
		final String sql= "SELECT id, day, flow, river "
						+ "FROM flow";
		
		Map<Integer,Flow> idFlows = new HashMap<Integer, Flow>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				idFlows.put(res.getInt("id"), new Flow(res.getDate("day").toLocalDate(), res.getDouble("flow"), rivers.get(res.getInt("river")))) ;
			}

			conn.close();
			st.close();
			res.close();
			
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		
		return idFlows;
	}
	
	//aggiunge a river flowAvg e flows, ritorna il totale delle misurazioni
	public int setAvgFlow(River river) {
		
		final String sql= "SELECT id, day, flow, river "
				+ "FROM flow "
				+ "WHERE river = ?";
		
		List<Flow> flows = new LinkedList<Flow>();
		
		double avg = 0.0;
		double sum = 0;
		int tot = 0;
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, river.getId());
			ResultSet res = st.executeQuery();

			while (res.next()) {
				flows.add(new Flow (res.getDate("day").toLocalDate(), res.getDouble("flow"), river));
				
				sum += res.getDouble("flow");
				tot++;
			}

			river.setFlows(flows);
			
			avg = sum/tot;
			river.setFlowAvg(avg);

			conn.close();
			st.close();
			res.close();
			
			return tot;
			
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		
	}
}
