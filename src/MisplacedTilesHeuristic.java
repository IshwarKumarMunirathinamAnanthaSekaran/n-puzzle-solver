public class MisplacedTilesHeuristic implements HeuristicFunction{
    @Override
    public int calculate(int[][] board, int[][] goalBoard) {
        int misplaced = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != goalBoard[i][j]) {
                    misplaced++;
                }
            }
        }
        return misplaced;
    }
}
