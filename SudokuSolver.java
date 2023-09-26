import java.util.Scanner;
import java.io.IOException;
import java.io.File;
import java.util.StringTokenizer;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.util.Arrays;

public class SudokuSolver{
	public static int idxConvertor(int i,int j){return 9*i + j;}
	public static final int GRID_SIZE = 9;
	public static final int SQUARE_SIZE = 3;
	public static boolean isDone = false;

	public static void main(String[] args) throws IOException{
		int[][] grid = initGrid("Test.txt");
		ArrayList[] possible = initPossible();
		boolean stuck = false;	
		boolean flag;
		int changes;

		while(!isDone && !stuck){
			changes = 0;
			for(int i = 0 ; i < GRID_SIZE ; i++){
				for (int j = 0 ; j < GRID_SIZE ; j++){
					checkRow(possible,grid,i,j);
					checkCol(possible,grid,i,j);
					checkBox(possible,grid,i,j);
					
					flag = checkPossible(possible,grid,i,j);
					if(flag) changes++;
						
				}
			}
			checkDone(grid);
			stuck = (changes == 0);
		}

		if(stuck && !isDone) System.out.println("#######Stuck#######\nCould not solve it.");

		FileWriter fw = new FileWriter("Solution.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter pw = new PrintWriter(bw);

		for (int[] arr : grid){
			for (int nb : arr){
				pw.print(nb + "|");
			}
			pw.print("\n");
		} 
		pw.close();
	}

	public static int[][] initGrid1(String fileName) throws IOException{

		//Scanner sc = new Scanner(System.in);
		//System.out.println("File name :");
		//String fileName = sc.nextLine();

		int[][] grid = new int[GRID_SIZE][GRID_SIZE];
		Scanner fileSc = new Scanner(new File(fileName));
		int rowIdx = 0, colIdx;		
		StringTokenizer st ;

		while(fileSc.hasNext()){
			colIdx = 0;
			st = new StringTokenizer(fileSc.nextLine(),"|");
			while(st.hasMoreTokens()){
				grid[rowIdx][colIdx] = Integer.parseInt(st.nextToken());
				colIdx++;
			}
			rowIdx++;
		}	
		return grid;
	}

	public static int[][] initGrid(String fileName){
		try{int[][] grid = initGrid1(fileName);return grid;}
		catch(IOException e){e.printStackTrace();return new int[][]{};}
	}

	public static ArrayList[] initPossible(){
		ArrayList<Integer>[] possible = new ArrayList[GRID_SIZE * GRID_SIZE];
		for (int i = 0 ; i < GRID_SIZE * GRID_SIZE ; i++){
			possible[i] = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9));
		}
		return possible;
	}

	public static void checkRow(ArrayList[] possible, int[][] grid ,int i , int j){
		if(grid[i][j] == 0){
			int nbIdx = idxConvertor(i , j);
			int nb;
			for (int idx = 0 ; idx <  GRID_SIZE ; idx++ ){
				nb = grid[i][idx];
				if(possible[nbIdx].contains(nb)){
					possible[nbIdx].remove(possible[nbIdx].indexOf(nb));
				}
			}
		}
		
	}

	public static void checkCol(ArrayList[] possible, int[][] grid ,int i , int j){
		if(grid[i][j] == 0){
			int nbIdx = idxConvertor(i , j);
			int nb;
			for (int idx = 0 ; idx <  GRID_SIZE ; idx++ ){
				nb = grid[idx][j];
				if(possible[nbIdx].contains(nb)){
					possible[nbIdx].remove(new Integer(nb));
				}
			}
		}
		
	}

	public static void checkBox(ArrayList[] possible, int[][] grid,int i , int j){
		if(grid[i][j] == 0){
			int nbIdx = idxConvertor(i - i % SQUARE_SIZE , j - j % SQUARE_SIZE);
			int nb;
			for (int rIdx = 0 ; rIdx < SQUARE_SIZE ; rIdx++){
				for (int cIdx = 0 ; cIdx < SQUARE_SIZE ; cIdx++){
					nb = grid[i - i % SQUARE_SIZE + rIdx][j - j % SQUARE_SIZE + cIdx];
					if(possible[nbIdx].contains(nb)){
						possible[nbIdx].remove(new Integer(nb));
					}
				}
			}
		} 
	}

	public static void checkDone(int[][] grid){
		for (int[] arr : grid){
			for (int nb : arr){
				if (nb == 0) return;
			}
		}
		isDone = true;
	}

	public static boolean checkPossible(ArrayList[] possible, int[][] grid, int i,int j){
		int idx = idxConvertor(i,j);
		if (possible[idx].size() == 1 && grid[i][j] == 0){
			grid[i][j] = (int) possible[idx].get(0);
			return true;
		}
		return false;
	}

}










