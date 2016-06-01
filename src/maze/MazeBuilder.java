// Henry Chipman
// This class is used to create a randomly generated maze with a height and width given by the user.
// The class uses the mydisjsets class to implement the union-find abstract data type.


package maze;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;

public class MazeBuilder {

	public static void main(String[] args) { 
		
		int height = 0; //Keeps track of the height of the maze
		int width = 0; //Keeps track of the width of the maze
		
		//If the wrong number of arguments is passed by the client, a message is given and the
		//program is exitted
        if (args.length != 3) {
            System.err.println(" Incorrect number of arguments");
            System.err.println(" Usage: ");
            System.err.println("\tjava  MazeBuilder <maze height> <maze width> <output file>");
            System.exit(1);
        }

        try {
        	width = Integer.parseInt(args[1]);
            height = Integer.parseInt(args[0]);
            
            //Sets up output file to write to
            PrintWriter fileOut = new PrintWriter(new BufferedWriter(new FileWriter(args[2])));
        	
            //Create a new set of disjoint sets to track the maze
            DisjointSets maze = new MyDisjSets(width*height);
            
            //Keeps track of all the walls in the maze and the wall that will be kept in the maze
            ArrayList<int[]> walls = findWalls(height, width);
            ArrayList<int[]> keptWalls = new ArrayList<int[]>();
            
            //While the maze is not complete, edges are taken away until there is one path for
            //the maze and no cycles are created
            while(maze.numSets() > 1) {
            	int randIndex = randInt(0, walls.size()-1);
            	int[] wall = walls.get(randIndex);
            	int u = maze.find(wall[0]);
            	int v = maze.find(wall[1]);
            	if(u==v) {
            		keptWalls.add(wall); //edges between cells in the same set are added to walls 
            		//kept in the maze so no cycles are made
            	} else {
            		maze.union(maze.find(wall[0]), maze.find(wall[1]));
            	}
            	walls.remove(randIndex);
            }
            while(!walls.isEmpty()){
            	keptWalls.add(walls.get(0));
            	walls.remove(0);
            }

            //Two dimensional array is created to store character that will visually
            //represent the maze
            String[][] mazeChars = mazeArray(keptWalls, height, width);
            
            //Two dimensional array is printed to the specified output file
            for(int i = 0; i < mazeChars.length; i++) {
    			for(int j = 0; j < mazeChars[i].length; j++) {
    				if(mazeChars[i][j] != null)
    					fileOut.print(mazeChars[i][j]);
    				else
    					fileOut.print(" ");
    			}
    			fileOut.println();
    		}
            
            //Closes file
            fileOut.close();
        
        
        } catch(IOException ioe) {
            System.err.println("Error opening/reading/writing output file.");
            System.exit(1);
        } catch(NumberFormatException nfe) {
            System.err.println(nfe.toString());
            System.err.println("Error in file format");
            System.exit(1);
        }
        
    }
	
	//This class takes the list of kept edges in the maze, the height, and the width of the maze
	//and returns a two dimensional array which stores values used to make a visual representation
	//of the maze
	public static String[][] mazeArray(ArrayList<int[]> walls, int height, int width){
		String[][] chars = new String[(height*2)+1][(width*2)+1];
		
		for(int i = 0; i <= height*2; i+=2) {
			for(int j = 0; j <= width*2; j+=2) {
				chars[i][j] = "+";
			}
		}

		for(int i = 0; i <= height*2; i+=height*2) {
			for(int j = 1; j <= width*2; j+=2) {
				chars[i][j] = "-";
			}
		}
		
		for(int i = 1; i <= height*2; i+=2) {
			for(int j = 0; j <= width*2; j+=width*2) {
				chars[i][j] = "|";
			}
		}
		
		for(int i = 0; i < walls.size(); i++) {
			int[] wall = walls.get(i);
			if(Math.abs(wall[0]-wall[1]) == 1) {
				chars[((wall[0]/(width))*2)+1][(((wall[0]%width))*2)] = "|";
			} else {
				chars[(((wall[0]-1)/(width))*2)+2][((wall[0]-(((wall[0]-1)/(width))*width))*2)-1] = "-";

			}
		}
		chars[1][0] = null;
		chars[(height*2)-1][(width*2)] = null;
		return chars;
	}
	
	//This method takes the height and width of the table and returns all of the edges in that table
	public static ArrayList<int[]> findWalls(int height, int width) {
        ArrayList<int[]> walls = new ArrayList<int[]>();
        for (int i = 0; i < height; i++) {
        	for(int j = 1; j < width; j++) {
        		int[] wall = {((i*width)+j), (((i*width)+j)+1)};
        		walls.add(wall);
        	}
        }
        for (int i = 0; i < height-1; i++) {
        	for(int j = 1; j <= width; j++) {
        		int[] wall = {((i*width)+j), ((((i+1)*width)+j))};
        		walls.add(wall);
        	}
        }
        
        return walls;
    }
	
	//This method takes a minimum and maximum value and returns a random integer between those values
	public static int randInt(int min, int max) {
	    Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;
	    return randomNum;
	}
}