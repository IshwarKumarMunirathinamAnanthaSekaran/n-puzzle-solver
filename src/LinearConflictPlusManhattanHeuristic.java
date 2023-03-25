public class LinearConflictPlusManhattanHeuristic implements HeuristicFunction {
    public int calculate(int[][] current, int[][] goal) {
    PuzzleSolver ob = new PuzzleSolver();
    HeuristicFunction heuristicMD = new ManhattanDistanceHeuristic();
    HeuristicFunction heuristicLC= new LinearConflictHeuristic();
    int md = heuristicMD.calculate(current, goal);
    int lc = heuristicLC.calculate(current, goal);
    //return md + lc;
    return md + 2 * lc ;
}




}

