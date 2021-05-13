package kruskal;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Tester {
	public static void main(String[] args) throws FileNotFoundException, IOException {
		System.out.println("running");
		ElectricTree test = new ElectricTree("testCities.txt");
		System.out.println("done");
	}
}
