package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {

	private RiversDAO dao;
	private Map<Integer, River> rivers;
	private Map<Integer, Flow> idFlows;
	private PriorityQueue<Flow> queue;
	
	public Model() {
		super();
		 this.dao = new RiversDAO();
		
		this.rivers = new HashMap<Integer, River>();
		this.rivers = this.getAllRivers();
		
		this.idFlows = new HashMap<Integer, Flow>();
	}


	//I FIUMI
	public Map<Integer, River> getAllRivers(){
		return this.dao.getAllRivers();
	}
	
	//LE MISURAZIONI
	public Map<Integer, Flow> getAllFlows(){
		return this.dao.getAllFlows(rivers);
	}
	
	public int getTot(River river) {
		 return river.getTotMeasurements();
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
	
	
	
	//SIMULAZIONE
	public SimulationResult simulate(River river, double k) {
		
		//inizializzo la coda
		this.queue = new PriorityQueue<Flow>();
		queue.addAll(river.getFlows());
		
		List<Double> capacity = new LinkedList<Double>();
		
		double Q =  k * 30 * convertM3SecToM3Day(river.getFlowAvg());
		double C = Q / 2;
		double fOutMin = convertM3SecToM3Day(0.8 * river.getFlowAvg());
		int numberOfDays = 0;

		System.out.println("Q: " + Q);
		
		Flow flow;
		while((flow = this.queue.poll()) != null) {
			System.out.println("Date: " + flow.getDay());
			
			double fOut = fOutMin;

			// C'è il 5% di possibilità che fOut sia 10 volte fOutMin
			if (Math.random() > 0.95) {
				fOut = 10 * fOutMin;
				System.out.println("10xfOutMin");
			}

			System.out.println("fOut: " + fOut);
			System.out.println("fIn: " + convertM3SecToM3Day(flow.getFlow()));

			// Aggiungo fIn a C
			C += convertM3SecToM3Day(flow.getFlow());

			// Se C è maggiore della capacità massima
			if (C > Q) {
				// Tracimazione
				// La quantità in più esce.
				C = Q;
			}

			if (C < fOut) {
				// Non riesco a garantire la quantità minima.
				numberOfDays++;
				// Il bacino comunque si svuota
				C = 0;
			} else {
				// Faccio uscire la quantità giornaliera
				C -= fOut;
			}

			System.out.println("C: " + C + "'\n");

			// Mantengo un lista della capacità giornaliere del bacino
			capacity.add(C);
		}
		

		// Calcolo la media della capacità
		double CAvg = 0;
		for (Double d : capacity) {
			CAvg += d;
		}
		CAvg = CAvg / capacity.size();
		return new SimulationResult(CAvg, numberOfDays);
		
		
	}
	
	public double convertM3SecToM3Day(double input) {
		return input * 60 * 60 * 24;
	}

	public double convertM3DayToM3Sec(double input) {
		return input / 60 / 60 / 24;
	}
	
	
	
	
}
