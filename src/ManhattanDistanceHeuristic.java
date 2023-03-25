import java.util.HashMap;
import java.util.Map;

public class ManhattanDistanceHeuristic implements HeuristicFunction {


    public int calculate(int[][] board, int[][] goalBoard) {
        int distance = 0;
        Map<Integer, int[]> goalPositions = new HashMap<>();
        for (int i = 0; i < goalBoard.length; i++) {
            for (int j = 0; j < goalBoard[i].length; j++) {
                goalPositions.put(goalBoard[i][j], new int[] {i, j});
            }
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int value = board[i][j];
                if (value != 0) {
                    int[] goalPos = goalPositions.get(value);
                    distance += Math.abs(i - goalPos[0]) + Math.abs(j - goalPos[1]);
                }
            }
        }
        return distance;
    }



}
