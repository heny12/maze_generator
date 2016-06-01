// Henry Chipman
// This class reads in a maze and solves it efficiently. It prints the solution of the maze to 
// a file specified by the user.

package maze;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class mazeSolver {

	static ArrayList<char[][]> mazeChars; //keeps track of characters in maze
	static int rows; //keeps track of the number of rows of characters
	static int cols; //keeps track of the number of columns of characters
	
	public static void main(String[] args) {
		if (args.length != 2) { //gives message if wrong number of elements is given
            System.err.println(" Incorrect number of arguments");
            System.err.println(" Usage: ");
            System.err.println("\tjava  mazeSolver <inputFile> <output file>");
            System.exit(1);
        }
		try {
			//sets up file to read
			Scanner mazeScanner = new Scanner(new BufferedReader(new FileReader(args[0])));
			mazeChars = new ArrayList<char[][]>();
			rows = 0;
			//scans maze file into two dimensional array of characters
		    while(mazeScanner.hasNextLine()) {
		    	String line = mazeScanner.nextLine();
		    	char[] lineArray = line.toCharArray();
		    	char[][] lineMDArray = new char[lineArray.length][2];
		    	for(int i = 0; i < lineArray.length; i++){
		    		lineMDArray[i][0] = lineArray[i];
		    		lineMDArray[i][1] = 'n';
		    	}
		    	cols = lineArray.length;
		    	mazeChars.add(lineMDArray);
		    	rows++;
		    }
		    //closes input file
		    mazeScanner.close();
		    
		    
		    //calls method to find solution to maze starting at the top left corner
			findPath(1, 1);
			
			//sets up output file
			PrintWriter fileOut = new PrintWriter(new BufferedWriter(new FileWriter(args[1])));
			
			//prints maze to file, using x as traveled stops from the start to end of maze
			for(int i = 0; i < mazeChars.size(); i++) {
				for(int j = 0; j < mazeChars.get(i).length; j++) {
					char[][] row = mazeChars.get(i);
					if(row[j][1] == 'x') {
						fileOut.print(row[j][1]);
					} else {
						fileOut.print(row[j][0]);
					}
				}
				fileOut.println();
			}
			
			//closes output file
		    fileOut.close();
			
		} catch(IOException ioe) {
            System.err.println("Error opening/reading/writing input or output file.");
            System.exit(1);
        } catch(NumberFormatException nfe) {
            System.err.println(nfe.toString());
            System.err.println("Error in file format");
            System.exit(1);
        }
		
	}
	
	public mazeSolver(String fileName) {
	}
	
	
	//Recursively finds solution to maze
	private static boolean findPath(int row, int col) {
		if (row == rows-1 && col == cols-1) { //Ends method if end of maze is found
			return true;
		//determines if there is a wall of the maze
		} if (mazeChars.get(row)[col][0] == '+' || mazeChars.get(row)[col][0] == '|' 
				|| mazeChars.get(row)[col][0] == '-' || mazeChars.get(row)[col][1] != 'n') {
			return false;
		}
		
		//keeps track of space already traveled
		mazeChars.get(row)[col][1] = 't';
		
		if(row != 0) { //not on top edge
			if(findPath(row-1, col)) { //tries spot up
				mazeChars.get(row)[col][1] = 'x'; //marks as correct path
				return true;
			}
		} if(row != rows-1) { //not on bottom edge
			if(findPath(row+1, col)) { //tries spot down
				mazeChars.get(row)[col][1] = 'x'; //marks as correct path
				return true;
			}
		} if(col != 0) { //not on left edge
			if(findPath(row, col-1)) { //tries spot left
				mazeChars.get(row)[col][1] = 'x'; //marks as correct path
				return true;
			}
		} if(col != cols-1) { //not on right edge
			if(findPath(row, col+1)) { //tries spot right
				mazeChars.get(row)[col][1] = 'x'; //marks as correct path
				return true;
			}
		}
		return false;
	}
	
}


