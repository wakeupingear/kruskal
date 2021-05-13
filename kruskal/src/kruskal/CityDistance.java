package kruskal;

public class CityDistance implements Comparable{
	public String city1;
	public String city2;
	public int dist;
	
	public CityDistance(String city1,String city2,int dist) {
		this.city1=city1;
		this.city2=city2;
		this.dist=dist;
	}
	
	public int getDist() {
		return dist;
	}
	
	public int compareTo(Object otherDist) {
		return dist-((CityDistance) otherDist).dist;
	}
}
