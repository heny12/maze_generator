// Henry Chipman
// This class implements the DisjointSets interface provided for this assignment. This class provides
//functions to union disjoint sets, find which set an integer belongs to, find the number of sets, 
//determine if a value is a set name, find the number of elements in a set, print a set, and get the
//elements in a set in an array format. This class takes a integer from the user and creates that many
//disjoint sets.


package maze;

public class MyDisjSets implements DisjointSets {
	
	public static void main(String[] args){
	}
	
	public int[] up; //Stores up tree
	public int sets; //Keeps track of the number of sets
	
	//Constructor takes the number of element from the client and sets up the up tree
	public MyDisjSets(int numElements) {
		up = new int[numElements + 1];
		sets = numElements;
	} 
	
	/**
     * Union (combine) two disjoint sets into one set. 
     * No restriction on which set should be added to the other set.
     * @param set1 the name of set 1.
     * @param set2 the name of set 2.
     * @throws InvalidSetNameException if either of set1 or set2 are 
     * not the name of sets.
     * @throws InvalidElementException if either of set1 or set2
     * is not a valid element. 
     */
	public void union(int set1, int set2) {
		if(set1 > up.length-1 || set2 > up.length-1)
			throw new InvalidElementException();
		if(!(isSetName(set1)) || !(isSetName(set2)))
			throw new InvalidSetNameException();
		up[set2] = set1;
		sets--;
	}

	/**
     * Find which set element x belongs to.
     * @param x the element being searched for.
     * @return the name of the set containing x.
     * @throws InvalidElementException if x is not a valid element. 
     */
    public int find(int x){
		if(x > up.length-1)
			throw new InvalidElementException();
    	while(up[x] != 0)
    		x = up[x];
    	return x;
    }

    /**
     * Returns the current total number of sets.
     * @return the current number of sets.
     */
    public int numSets(){
		return sets;
    }

    /**
     * Determine if an element is the name of a set.
     * @param x an element
     * @return true if x is the name of a set
     * @throws InvalidElementException if x is not a valid element. 
     */
    public boolean isSetName(int x){
    	if(x > up.length-1)
			throw new InvalidElementException();
    	if(up[x] == 0)
    		return true;
    	return false;
    }

    /**
     * Returns the total number of elements in the given set.
     * @param setNum the name of a set
     * @throws InvalidSetNameException if setNum is not the name of a set.
     * @throws InvalidElementException if setNum is not a valid element. 
     */
    public int numElements(int setNum){
    	if(setNum > up.length-1)
			throw new InvalidElementException();
    	if(!(isSetName(setNum)))
			throw new InvalidSetNameException();
    	return 1 + childSum(setNum);
    }
    
    //This method returns the sum of the values of all of the children nodes from the parent node
    //specified by the client
    private int childSum(int x){
    	int levelSum = 0;
    	for(int i = 1; i < up.length; i++){
    		if(up[i] == x) {
    			levelSum++;
    			levelSum += childSum(i);
    		}
    	}
    	return levelSum;
    }

    /**
     * Prints out the elements in the given set.
     * setNum is assumed to be a root and represents the name of a set.
     * @param setNum the name of a set
     * @throws InvalidSetNameException if setNum is not the name of a set.
     * @throws InvalidElementException if setNum is not a valid element. 
     */
    public void printSet(int setNum){
    	if(setNum > up.length-1)
			throw new InvalidElementException();
    	if(!(isSetName(setNum)))
			throw new InvalidSetNameException();
    	int[] set = getElements(setNum);
    	System.out.print("{" + set[0]);
		for(int i = 1; i < set.length; i++)
			System.out.print(", " + set[i]);
		System.out.println("}");
    }

    /**
     * Returns an array containing the elements in the given set.
     * @param setNum the name of a set
     * @returns an array containing the elements in the given set.
     * @throws InvalidSetNameException if setNum is not the name of a set.
     * @throws InvalidElementException if setNum is not a valid element. 
     */
    public int [] getElements(int setNum){
    	if(setNum > up.length-1)
			throw new InvalidElementException();
    	if(!(isSetName(setNum)))
			throw new InvalidSetNameException();
    	int[] setElements = new int[numElements(setNum)];
    	setElements[0] = setNum;
    	storeChildren(setNum, setElements, 1); //calls helper method to fill the array with child nodes
    	return setElements;
    }
    
    //This method returns an integer array of all the children nodes of a parent node specified
    //by the user
    private int[] storeChildren(int x, int[] array, int index){
    	for(int i = 1; i < up.length; i++){
    		if(up[i] == x) {
    			array[index] = i;
    			array = storeChildren(i, array, index++);
    		}
    	}
    	return array;
    }
}
