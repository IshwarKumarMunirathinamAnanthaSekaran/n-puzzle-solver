public class Node implements Comparable<Node>{

    //public int depth;
    int[][] board;
    int g;
    int h;
    int f;
    String action;
    Node parent;

    Node(int[][] board, int g, int h, String action, Node parent) {
        this.board = board;
        this.g = g;
        this.h = h;
        this.f = g + h;
        this.action = action;
        this.parent = parent;
    }

    // compareTo method to allow nodes to be compared based on f-score
    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.f, other.f);
    }
}
