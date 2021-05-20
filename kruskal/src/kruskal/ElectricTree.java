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
    public HashMap<String,ArrayList<String>> cityConnectedMap;

    @SuppressWarnings("unchecked")
	public ElectricTree(String fileName) throws FileNotFoundException, IOException {
    	totalCost=0;
    	try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
    		int cityNum=Integer.parseInt(br.readLine());
    		cityList=new ArrayList<CityNode>();
    		cityDistList=new ArrayList<CityDistance>();
    		cityConnectedMap=new HashMap<String,ArrayList<String>>();
    		for (int i=0;i<cityNum;i++) {
    			String name=br.readLine();
    			cityList.add(new CityNode(name));
    			cityConnectedMap.put(name, new ArrayList<String>());
    		}
    		powerPlantLocation=cityList.get(0);
    		
    		String connection;
    	    while ((connection = br.readLine()) != null) {
    	    	int pos1=connection.indexOf(" ");
    	    	int pos2=connection.indexOf(" ",pos1+1);
    	    	String city1 = connection.substring(0,pos1);
    	    	String city2 = connection.substring(pos1+1,pos2);
    	    	int dist=Integer.parseInt(connection.substring(pos2+1));
    	    	cityDistList.add(new CityDistance(city1,city2,dist));
    	    }
    	    br.close();
    	}
    	Collections.sort(cityDistList); //Sort the list based on how long each connection is
    	while (cityDistList.size()>0) { //Loop through the connection list
    		CityDistance distObj=cityDistList.remove(0); //Get/remove the first connection
    		if (!cityIsConnected(distObj.city1,"",distObj.city2)||!cityIsConnected(distObj.city2,"",distObj.city1)) { //Check if both cities are not connected
    			totalCost+=distObj.dist; //Add this distance to the total distance
    			cityConnectedMap.get(distObj.city2).add(distObj.city1); //Add a connection for city2
    			cityConnectedMap.get(distObj.city1).add(distObj.city2); //Add a connection for city1
    		}
    	}
    	
    	System.out.println("Total Cost: "+totalCost);
    	System.out.println(printTree(getPowerPlant(),""));
    }
    
    //Recursively checks to see if a city is connected back to the power plant
    //String city - the name of the current city to check
    //String lastCity - the name of the previous city, to make sure we don't move back in the opposite direction to a previously checked city
    public boolean cityIsConnected(String city, String lastCity, String endCity) {
    	if (city.equals(endCity)) return true;
    	if (cityConnectedMap.containsKey(city)) { //Checks if the city has been added in the first place
    		ArrayList<String> list=cityConnectedMap.get(city); //Get the list of connected cities for this city
    		for (int i=0;i<list.size();i++) { //Loop through the list of connected cities
    			if (list.get(i).equals(lastCity)) continue; //Skip this city if it has been checked in the previous recursion
    			if (cityIsConnected(list.get(i),city,endCity)) { //Return true if the city is the power plant or is recursively connected 
    				return true;
    			}
    		}
    	}
    	return false;
    }

    public String getPowerPlant() {
            return powerPlantLocation.getName();
    }

    public int getTotalCost() {
            return totalCost;
    }
    
    public String printTree(String city, String lastCity) {
    	StringBuilder str=new StringBuilder(city+": {");
    	ArrayList<String> list=cityConnectedMap.get(city);
    	for (int i=0;i<list.size();i++) {
    		if (list.get(i).equals(lastCity)) continue;
    		if (i>0) str.append(",");
    		str.append(printTree(list.get(i),city));
    	}
    	str.append("}");
    	return str.toString();
    }

}