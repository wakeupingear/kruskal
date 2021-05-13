package kruskal;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ElectricTree {
    private CityNode powerPlantLocation;
    private int totalCost;
    
    private ArrayList<CityNode> cityList;
    public ArrayList<CityDistance> cityDistList;
    public HashMap<String,String> cityConnectedMap;

    @SuppressWarnings("unchecked")
	public ElectricTree(String fileName) throws FileNotFoundException, IOException {
    	totalCost=0;
    	try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
    		int cityNum=Integer.parseInt(br.readLine());
    		cityList=new ArrayList<CityNode>();
    		cityDistList=new ArrayList<CityDistance>();
    		cityConnectedMap=new HashMap<String,String>();
    		for (int i=0;i<cityNum;i++) {
    			cityList.add(new CityNode(br.readLine()));
    		}
    		powerPlantLocation=cityList.get(0);
    		
    		String connection;
    	    while ((connection = br.readLine()) != null) {
    	    	int pos1=connection.indexOf(" ");
    	    	int pos2=connection.indexOf(" ",pos1+1);
    	    	String city1 = connection.substring(0,pos1);
    	    	String city2 = connection.substring(pos1,pos2);
    	    	int dist=Integer.parseInt(connection.substring(pos2+1));
    	    	cityDistList.add(new CityDistance(city1,city2,dist));
    	    }
    	    br.close();
    	}
    	Collections.sort(cityDistList);
    	while (cityDistList.size()>0) {
    		CityDistance distObj=cityDistList.remove(0);
    		if (!cityIsConnected(distObj.city1)&&!cityIsConnected(distObj.city2)) {
    			totalCost+=distObj.dist;
    			cityConnectedMap.put(distObj.city2,distObj.city1);
    		}
    	}
    	
    	System.out.println(totalCost);
    }
    
    public boolean cityIsConnected(String city) {
    	if (cityConnectedMap.containsKey(city)) {
    		if (cityConnectedMap.get(city).equals(powerPlantLocation.getName())) return true;
    		return cityIsConnected(cityConnectedMap.get(city));
    	}
    	return false;
    }

    public CityNode getPowerPlantLocation() {
            return powerPlantLocation;
    }

    public int getTotalCost() {
            return totalCost;
    }

}