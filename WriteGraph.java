/**
 * WriteGraph.java
 * @author Ryan Oliveira
 * @author Tina Nemati
 * CIS 22C, Lab 8
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class WriteGraph {
	
	/**
	 * read from the input file and write the output in the output file
	 * @param inFile the input file to read from
	 * @param outFile the output file to write to
	 * @throws FileNotFoundException if the input file does not exist 
	 */
	public static void readFile(File inFile, File outFile) throws FileNotFoundException {
		Scanner input = new Scanner(inFile);
		PrintWriter out = new PrintWriter(outFile);
		int vertices = input.nextInt();
		Graph g = new Graph(vertices);
		while (input.hasNextLine()) {
			int u = input.nextInt();
			int v = input.nextInt();
			if (u != 0 && v != 0) {
				g.addUndirectedEdge(u, v);
			} else {
				input.nextLine();
				break;
			}
		}
		for(String adj : g.toString().split("\n")) {
			out.write(adj + "\r\n");
		}
		while (input.hasNextLine()) {
			int u = input.nextInt();
			int v = input.nextInt();
			if (u != 0 && v != 0) {
				g.BFS(u);
				out.println("The distance from " + u + " to " + v + ": " + g.getDistance(v));
				out.println("Shortest path from " + u + " to " + v + ": " + g.printPath(u, v, ""));
			} else {
				out.close();
				break;
			}
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("Welcome to WriteGraph!\n");
		Scanner input = new Scanner(System.in);
		boolean loop = true;
		System.out.print("Enter the name of the file containing the graph information: ");
		while (loop) {
			File inFile = new File(input.nextLine());
			if (inFile.exists()) {
				System.out.println("\n***Reading from " + inFile + "***\n");
				System.out.print("Please enter the name of the output file: ");
				File outFile = new File(input.nextLine());
				readFile(inFile, outFile);
				System.out.print("Thank you! Your Graph is now written to " + outFile + "!");
				loop = false;
			} else {
				System.out.println("Invalid file name!\n");
				System.out.print("Please enter the name of the file: ");
			}
		}
	}
}
