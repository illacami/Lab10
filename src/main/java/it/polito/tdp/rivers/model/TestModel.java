package it.polito.tdp.rivers.model;

public class TestModel {

	public static void main(String[] args) {
		
		Model model = new Model();
		
		River river = model.getAllRivers().get(1);
    	
		System.out.println(model.getTot(river)+ " ");
    	System.out.println(model.getAvg(river)+" ");
    	System.out.println(river.getFirstDate()+" ");
    	System.out.println(river.getLastDate()+" ");
    

    	River river2 = model.getAllRivers().get(2);
    	
		System.out.println(model.getTot(river2)+ " ");
    	System.out.println(model.getAvg(river2)+" ");
    	System.out.println(river2.getFirstDate()+" ");
    	System.out.println(river2.getLastDate()+" ");

    	River river3 = model.getAllRivers().get(3);
    	
		System.out.println(model.getTot(river3)+ " ");
    	System.out.println(model.getAvg(river3)+" ");
    	System.out.println(river3.getFirstDate()+" ");
    	System.out.println(river3.getLastDate()+" ");
	}

}
