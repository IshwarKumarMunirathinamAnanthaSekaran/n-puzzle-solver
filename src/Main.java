import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
public class Main {
    public static void main(String[] args) throws IOException {
        int n;
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the dimension of initial array: ");
        n = sc.nextInt();

        int array[][] = new int[n][n];
// Read the matrix values
        System.out.println("Enter the elements of the initial array: ");
//loop for row
        for (int i = 0; i < n; i++)
//inner for loop for column
            for (int j = 0; j < n; j++)
                array[i][j] = sc.nextInt();
//accessing array elements
        System.out.println("Elements of the initial array are: ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
//prints the array elements
                System.out.print(array[i][j] + " ");
//throws the cursor to the next line
            System.out.println();
        }
        PuzzleSolver ob = new PuzzleSolver();
        List<String> actions = new ArrayList<>();
        List<int[][]> states = new ArrayList<>();
        int arrayResultExpand[][] = new int[n][n];
        //goal state
        int m;
        System.out.print("Enter the dimension of the final array: ");
        m = sc.nextInt();
        int goalArray[][] = new int[m][m];
        // Read the matrix values
        System.out.println("Enter the elements of the goal array: ");
//loop for row
        for (int i = 0; i < m; i++)
//inner for loop for column
            for (int j = 0; j < m; j++)
                goalArray[i][j] = sc.nextInt();
        System.out.println("Elements of the goal array are: ");
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++)
//prints the array elements
                System.out.print(goalArray[i][j] + " ");
//throws the cursor to the next line
            System.out.println();
        }
        //List<String> path = ob.iterativeDeepeningSearch(array,goalArray);
        //System.out.println("IDS path"+path);
        //List<String> pathBFS = ob.breadthFirstSearch(array, goalArray);
        //System.out.println("BFS path"+pathBFS);

        //HeuristicFunction heuristicMD = new ManhattanDistanceHeuristic();
        HeuristicFunction heuristicLCplusMD = new LinearConflictPlusManhattanHeuristic();
        //HeuristicFunction heuristicMT = new MisplacedTilesHeuristic();
        //HeuristicFunction heuristicDefault = new DefaultHeuristic();
        //HeuristicFunction heuristicLC= new LinearConflictHeuristic();

        //List<String> pathAStarMD = ob.aStarSearch(array, goalArray, heuristicMD);
        //System.out.println("A* path"+pathAStarMD);
        List<String> pathAStarLCplusMD = ob.aStarSearch(array, goalArray, heuristicLCplusMD);
        System.out.println("A* path"+pathAStarLCplusMD);
        //List<String> pathAStarMT = ob.aStarSearch(array, goalArray, heuristicMT);
        //System.out.println("A* path"+pathAStarMT);
        //List<String> pathAStarDefault = ob.aStarSearch(array, goalArray, heuristicDefault);
        //System.out.println("A* path"+pathAStarDefault);
        //List<String> pathAStarLC = ob.aStarSearch(array, goalArray, heuristicLC);
        //System.out.println("A* path"+pathAStarLC);





    }
}