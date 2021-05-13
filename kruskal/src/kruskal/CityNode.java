package kruskal;

import java.util.ArrayList;

public class CityNode {
	private String name;
	private ArrayList<CityNode> children;
	
	public CityNode(String name) {
		this.name = name;
		this.children = new ArrayList<CityNode>();
	}
	public ArrayList<CityNode> getChildren() {
		return children;
	}
	
	public void addChild(String name) {
		children.add(new CityNode(name));
	}
	
	public void addChild(CityNode node) {
		children.add(node);
	}
	
	public String getName() {
		return name;
	}	

	public String toString() {
		return name;
	}
}