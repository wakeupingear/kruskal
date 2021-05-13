package kruskal;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ElectricTree {
    private CityNode powerPlantLocation;
    private int totalCost;
    
    private ArrayList<CityNode> cityList;
    public ArrayList<Integer> cityDistList;
    public ArrayList<String> cityKeyList;
    public HashMap<String,String> cityConnectedMap;

    public ElectricTree(String fileName) throws FileNotFoundException, IOException {
    	totalCost=0;
    	try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
    		int cityNum=Integer.parseInt(br.readLine());
    		cityList=new ArrayList<CityNode>();
    		cityDistList=new ArrayList<Integer>();
    		cityKeyList=new ArrayList<String>();
    		cityConnectedMap=new HashMap<String,String>();
    		for (int i=0;i<cityNum;i++) {
    			cityList.add(new CityNode(br.readLine()));
    		}
    		
    		String connection;
    	    while ((connection = br.readLine()) != null) {
    	    	int pos1=connection.indexOf(" ");
    	    	int pos2=connection.indexOf(" ",pos1+1);
    	    	String city1 = connection.substring(0,pos1);
    	    	String city2 = connection.substring(pos1,pos2);
    	    	int dist=Integer.parseInt(connection.substring(pos2+1));
    	    	int len=cityDistList.size();
    	    	if (len==0) addCity(city1,city2,dist,0);
    	    	else for (int i=1;i<len;i++) {
    	    		if (i>=len-1||dist>cityDistList.get(i-1)) {
    	    			addCity(city1,city2,dist,i);
    	    			break;
    	    		}
    	    	}
    	    }
    	    br.close();
    	}
    	
    	while (cityDistList.size()>0) {
    		int dist=cityDistList.remove(0);
    		String city1=cityKeyList.remove(0);
    		String city2=cityKeyList.remove(0);
    		if (!cityIsConnected(city1)&&!cityIsConnected(city2)) {
    			totalCost+=dist;
    			cityConnectedMap.put(city1,city2);
    		}
    	}
    	
    	System.out.println(totalCost);
    }
    
    public void addCity(String city1,String city2,int dist,int pos) {
    	cityDistList.add(pos,dist);
    	cityKeyList.add(pos*2,city1);
    	cityKeyList.add(pos*2+1,city2);
    }
    
    public boolean cityIsConnected(String city) {
    	if (cityConnectedMap.containsKey(city)) {
    		if (cityConnectedMap.get(city)==powerPlantLocation.getName()) return true;
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