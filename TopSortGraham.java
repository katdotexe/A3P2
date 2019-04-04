package a3p2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Stack;

public class TopSortGraham {
	/*
	 * Kathleen Graham Assignment 3 Part 2 Due: 4/4/2019 TopoSort Class
	 */
	String fileName;
	private static LinkedList<Integer>[] adjList; // making the adjacency list
	private static int nodes; // number of nodes
	private static int inDegree[]; // inDegree
	File dataFile; // dataFile
	static String output = " "; // output string
	static TopSortGraham s;

	TopSortGraham(String inputFile) throws FileNotFoundException {
		fileName = inputFile; // create file
		createDiGraph(dataFile);
	}

	void createDiGraph(File dataFile) throws FileNotFoundException {
		try {
			dataFile = new File(fileName);
			Scanner sc = new Scanner(dataFile);

			nodes = sc.nextInt(); // next int is the number of nodes

			adjList = new LinkedList[nodes + 1]; // creating length of the list
			inDegree = new int[nodes + 1]; // create inDegree

			for (int i = 0; i < adjList.length; i++) {
				adjList[i] = new LinkedList();
			}
			while (sc.hasNextInt()) {

				int n1 = sc.nextInt(); // next int is n1
				int n2 = sc.nextInt(); // next int is n2
				if (adjList[n1].contains(n2) == false) { // if node 1 contains node 2, then add n2 to the list
					adjList[n1].add(n2);
					inDegree[n2]++; // increment n2 by 1
					// System.out.println(inDegree[n2]);
				}
			}
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		// System.out.println(nodes);
	}

	void sort(File dataFile) throws FileNotFoundException {
		Stack<Integer> S = new Stack<Integer>(); // create stack
		PrintWriter pw = new PrintWriter(dataFile);
		for (int u = 1; u < inDegree.length; u++) { // for each u
			if (inDegree[u] == 0) // if inDegree is == 0
			{
				S.push(u);
			} // push u
		}
		int i = 0; // make i == 0
		// System.out.println(Arrays.toString(inDegree));
		while (!S.isEmpty()) { // while S is empty
			int u = S.pop(); // pop S and put into u
			output += " " + u; // put u into output string
			i++;
			System.out.print(" " + u); //to see if the output is right 

			for(int v : adjList[u]) { // for each neighbor of v of u
				inDegree[v]--; // reduce inDegree by 1
				// System.out.println(v + " " + inDegree[v]);
				if (inDegree[v] == 0) {
					S.push(v); // push v if equals 0
				}
			}
			
		}
		if (i >= nodes) {

			pw.println(output); // print output string
			pw.close(); // close pw
		} else {
			pw.println("G is cyclic..."); // else, G is cyclic
			pw.close(); // close pw
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		if (args.length != 2) {
			System.out.println("Error: Not Enough Arguments");
			System.exit(0);
		} else {
			s = new TopSortGraham(args[0]);
			s.sort(new File(args[1]));
		}
	}
}
