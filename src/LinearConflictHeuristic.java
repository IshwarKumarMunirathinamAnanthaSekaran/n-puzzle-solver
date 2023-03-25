public class LinearConflictHeuristic implements HeuristicFunction{
    @Override
    public int calculate(int[][] board, int[][] goalBoard) {
        int conflict = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int value = board[i][j];
                if (value != 0 && value != goalBoard[i][j]) {
                    int goalRow = -1;
                    int goalCol = -1;
                    for (int k = 0; k < goalBoard.length; k++) {
                        for (int l = 0; l < goalBoard[k].length; l++) {
                            if (value == goalBoard[k][l]) {
                                goalRow = k;
                                goalCol = l;
                                break;
                            }
                        }
                    }
                    if (goalRow == i) {
                        for (int k = j + 1; k < board[i].length; k++) {
                            int nextValue = board[i][k];
                            if (nextValue != 0 && nextValue != goalBoard[i][k]) {
                                int nextGoalRow = -1;
                                int nextGoalCol = -1;
                                for (int l = 0; l < goalBoard.length; l++) {
                                    if (nextValue == goalBoard[i][l]) {
                                        nextGoalRow = i;
                                        nextGoalCol = l;
                                        break;
                                    }
                                }
                                if (nextGoalRow == i && goalCol > nextGoalCol) {
                                    conflict += 2;
                                }
                            }
                        }
                    }
                    if (goalCol == j) {
                        for (int k = i + 1; k < board.length; k++) {
                            int nextValue = board[k][j];
                            if (nextValue != 0 && nextValue != goalBoard[k][j]) {
                                int nextGoalRow = -1;
                                int nextGoalCol = -1;
                                for (int l = 0; l < goalBoard[k].length; l++) {
                                    if (nextValue == goalBoard[l][j]) {
                                        nextGoalRow = l;
                                        nextGoalCol = j;
                                        break;
                                    }
                                }
                                if (nextGoalCol == j && goalRow > nextGoalRow) {
                                    conflict += 2;
                                }
                            }
                        }
                    }
                }
            }
        }
        return conflict;
    }
}
