import java.util.*;

public class PuzzleSolver {

    // Function to identify the index of the blank tile in the board
    public static int[] findBlankTile(int[][] board) {
        int[] blankIndex = new int[2];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 0) {
                    blankIndex[0] = i;
                    blankIndex[1] = j;
                    return blankIndex;
                }
            }
        }
        return null;
    }

    // Function to get a list of all possible actions on the board
    public static ArrayList<String> possibleActions(int[][] board) {
        ArrayList<String> actions = new ArrayList<>();
        int[] blankIndex = findBlankTile(board);
        int row = blankIndex[0];
        int col = blankIndex[1];
        if (row > 0) {
            actions.add("up");
        }
        if (row < board.length - 1) {
            actions.add("down");
        }
        if (col > 0) {
            actions.add("left");
        }
        if (col < board[0].length - 1) {
            actions.add("right");
        }
        return actions;
    }

    // Function to get the updated new board after taking an action
    public static int[][] result(String action, int[][] board) {
        int[] blankIndex = findBlankTile(board);
        int row = blankIndex[0];
        int col = blankIndex[1];
        int[][] newBoard = new int[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                newBoard[i][j] = board[i][j];
            }
        }
        if (action.equals("up")) {
            int newRow = row - 1;
            int newCol = col;
            newBoard[row][col] = newBoard[newRow][newCol];
            newBoard[newRow][newCol] = 0;
        } else if (action.equals("down")) {
            int newRow = row + 1;
            int newCol = col;
            newBoard[row][col] = newBoard[newRow][newCol];
            newBoard[newRow][newCol] = 0;
        } else if (action.equals("left")) {
            int newRow = row;
            int newCol = col - 1;
            newBoard[row][col] = newBoard[newRow][newCol];
            newBoard[newRow][newCol] = 0;
        } else if (action.equals("right")) {
            int newRow = row;
            int newCol = col + 1;
            newBoard[row][col] = newBoard[newRow][newCol];
            newBoard[newRow][newCol] = 0;
        }
        return newBoard;
    }

    // Function to get a list of all the states that can be reached in one action from the given state
    public static ArrayList<int[][]> expand(int[][] board) {
        ArrayList<int[][]> expandedStates = new ArrayList<>();
        ArrayList<String> actions = possibleActions(board);
        for (String action : actions) {
            int[][] newBoard = result(action, board);
            expandedStates.add(newBoard);
        }
        return expandedStates;
    }

    //Function to perform Iterative Deepening Search
    public static ArrayList<String> iterativeDeepeningSearch(int[][] initialState, int[][] goalState) {
        ArrayList<String> path = new ArrayList<>();
        int depthLimit = 0;
        while (true) {
            HashSet<String> visited = new HashSet<>();
            boolean result = depthLimitedSearch(initialState, goalState, depthLimit, path, visited);
            if (result) {
                return path;
            }
            depthLimit++;
        }
    }

    // support function for IDS to perform depth-limited search
    public static boolean depthLimitedSearch(int[][] currentState, int[][] goalState, int depthLimit, ArrayList<String> path, HashSet<String> visited) {
        if (depthLimit == 0 && Arrays.deepEquals(currentState, goalState)) {
            return true;
        } else if (depthLimit > 0) {
            visited.add(Arrays.deepToString(currentState));
            ArrayList<int[][]> successors = expand(currentState);
            for (int[][] successor : successors) {
                if (!visited.contains(Arrays.deepToString(successor))) {
                    path.add(getAction(currentState, successor));
                    boolean result = depthLimitedSearch(successor, goalState, depthLimit - 1, path, visited);
                    if (result) {
                        return true;
                    }
                    path.remove(path.size() - 1);
                }
            }
        }
        return false;
    }

    // Function to get the action taken to move from current state to successor state
    public static String getAction(int[][] currentState, int[][] successor) {
        int[] blankIndex = findBlankTile(currentState);
        int[] newBlankIndex = findBlankTile(successor);
        if (newBlankIndex[0] == blankIndex[0] - 1) {
            return "up";
        } else if (newBlankIndex[0] == blankIndex[0] + 1) {
            return "down";
        } else if (newBlankIndex[1] == blankIndex[1] - 1) {
            return "left";
        } else if (newBlankIndex[1] == blankIndex[1] + 1) {
            return "right";
        }
        return null;
    }

    //Function to perform BFS
    public static List<String> breadthFirstSearch(int[][] initialState, int[][] goalState) {
        Queue<int[][]> queue = new LinkedList<>();
        HashMap<String, String> pathMap = new HashMap<>();
        List<String> path = new ArrayList<>();
// Add the initial state to the queue
        queue.add(initialState);
        pathMap.put(Arrays.deepToString(initialState), "");

// Start BFS
        while (!queue.isEmpty()) {
            int[][] currentState = queue.poll();
            if (Arrays.deepEquals(currentState, goalState)) {
                // Build the path by traversing the pathMap backwards from the goal state
                String currentPath = pathMap.get(Arrays.deepToString(currentState));
                while (!currentPath.isEmpty()) {
                    path.add(0, currentPath);
                    currentState = result(getOppositeAction(currentPath), currentState);
                    currentPath = pathMap.get(Arrays.deepToString(currentState));
                }
                return path;
            }
            ArrayList<int[][]> successors = expand(currentState);
            for (int[][] successor : successors) {
                if (!pathMap.containsKey(Arrays.deepToString(successor))) {
                    queue.add(successor);
                    pathMap.put(Arrays.deepToString(successor), getAction(currentState, successor));
                }
            }
        }
        return null;
    }

    // Function to get the opposite action of the given action
    public static String getOppositeAction(String action) {
        if (action.equals("up")) {
            return "down";
        } else if (action.equals("down")) {
            return "up";
        } else if (action.equals("left")) {
            return "right";
        } else if (action.equals("right")) {
            return "left";
        }
        return null;
    }
    //Function to perform A* search using heuristic function, input array and goal array
    public static List<String> aStarSearch(int[][] startBoard, int[][] goalBoard, HeuristicFunction heuristic) {
        // Initialize the start and goal nodes
        Node startNode = new Node(startBoard, 0, heuristic.calculate(startBoard, goalBoard), null, null);
        Node goalNode = new Node(goalBoard, 0, 0, null, null);

        // Initialize the open and closed sets
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Map<String, Node> closedSet = new HashMap<>();

        // Add the start node to the open set
        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            // Remove the node with the lowest f-score from the open set
            Node current = openSet.poll();

            // Check if the current node is the goal node
            if (Arrays.deepEquals(current.board, goalNode.board)) {
                // Construct the list of actions leading
                // to the goal node by traversing the parent pointers
                List<String> actions = new ArrayList<>();
                Node node = current;
                while (node.parent != null) {
                    actions.add(node.action);
                    node = node.parent;
                }
                Collections.reverse(actions);

                // Return the search result
                return new SearchResult(true, actions).actions;
            }

            // Add the current node to the closed set
            closedSet.put(Arrays.deepToString(current.board), current);

            // Generate the successor nodes
            List<Node> successors = generateSuccessors(current, goalNode, heuristic);
            //Deque<Node> successors = generateSuccessors(current, goalNode, heuristic);

            // Add the successor nodes to the open set
            for (Node successor : successors) {
                String key = Arrays.deepToString(successor.board);
                if (!closedSet.containsKey(key) || successor.f < closedSet.get(key).f) {
                    openSet.add(successor);
                    closedSet.put(key, successor);
                }
            }
        }

        // If the goal node is not found, return a failure result
        return new SearchResult(false, null).actions;
    }

    // Function to generate successor nodes
    private static List<Node> generateSuccessors(Node node, Node goalNode, HeuristicFunction heuristic) {
        List<Node> successors = new ArrayList<>();

        // Find the row and column of the blank tile
        int row = 0;
        int col = 0;
        outerloop:
        for (row = 0; row < node.board.length; row++) {
            for (col = 0; col < node.board[row].length; col++) {
                if (node.board[row][col] == 0) {
                    break outerloop;
                }
            }
        }

        // Define the possible moves
        int[][] moves = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        String[] actions = {"up", "down", "left", "right"};

        // Apply each possible move to generate a successor node
        for (int i = 0; i < moves.length; i++) {
            int newRow = row + moves[i][0];
            int newCol = col + moves[i][1];
            if (newRow >= 0 && newRow < node.board.length && newCol >= 0 && newCol < node.board[newRow].length) {
                int[][] newBoard = new int[node.board.length][node.board[0].length];
                for (int j = 0; j < node.board.length; j++) {
                    for (int k = 0; k < node.board[j].length; k++) {
                        newBoard[j][k] = node.board[j][k];
                    }
                }
                newBoard[row][col] = newBoard[newRow][newCol];
                newBoard[newRow][newCol] = 0;
                Node successor = new Node(newBoard, node.g + 1, heuristic.calculate(newBoard, goalNode.board), actions[i], node);
                successors.add(successor);
            }
        }

        return successors;
    }





}