import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;

class Node extends Object {
    public int x, y;
    public double g, h, f;
    public Node parent;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.g = Double.MAX_VALUE;
        this.h = 0;
        this.f = Double.MAX_VALUE;
        this.parent = null;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof Node))
            return false;
        Node other = (Node) obj;
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

}

class AStarAlgo {
    private static final int[] dx = { 1, -1, 0, 0 };
    private static final int[] dy = { 0, 0, 1, -1 };
    private static int GRID_ROWS;
    private static int GRID_COLS;

    private static double heuristic(Node n, Node target) {
        return Math.abs(n.x - target.x) + Math.abs(n.y - target.y);
    }

    private static boolean isValid(int x, int y, int[][] grid) {
        return x >= 0 && x < GRID_ROWS && y >= 0 && y < GRID_COLS && grid[x][y] != 1;
    }

    public AStarAlgo(int[][] grid, Node start, Node target) {
        GRID_ROWS = grid.length;
        GRID_COLS = grid[0].length;

        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparingDouble(n -> n.f));
        Map<Integer, Node> openSet = new HashMap<>();
        Set<Node> closedList = new HashSet<>();

        start.g = 0;
        start.h = heuristic(start, target);
        start.f = start.g + start.h;

        openList.offer(start);
        openSet.put(start.hashCode(), start);

        while (!openList.isEmpty()) {
            Node current = openList.poll();
            openSet.remove(current.hashCode());

            if (current.equals(target)) {
                List<Node> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = current.parent;
                }
                Collections.reverse(path);

                for (Node n : path) {
                    if (!n.equals(target))
                        System.out.print(n + " -> ");
                    else
                        System.out.print(n + "\n");
                }
                return;
            }

            closedList.add(current);

            for (int i = 0; i < 4; i++) {
                int nx = current.x + dx[i];
                int ny = current.y + dy[i];

                if (!isValid(nx, ny, grid))
                    continue;

                Node neighborNode = openSet.getOrDefault(Objects.hash(nx, ny), new Node(nx, ny));
                if (closedList.contains(neighborNode))
                    continue;

                double tentativeG = current.g + 1;

                if (!openList.contains(neighborNode)) {
                    openList.add(neighborNode);
                    openSet.put(neighborNode.hashCode(), neighborNode);
                } else if (tentativeG > neighborNode.g) {
                    continue;
                }

                neighborNode.g = tentativeG;
                neighborNode.h = heuristic(neighborNode, target);
                neighborNode.f = neighborNode.g + neighborNode.h;
                neighborNode.parent = current;
            }
        }
        System.out.println("No Path Found");
    }

}

public class Main {
    public static int GRID_ROWS = 5;
    public static int GRID_COLS = 5;

    public static void main(String[] args) {
        int[][] grid = new int[GRID_ROWS][GRID_COLS];
        grid[0][1] = 1;
        grid[1][1] = 1;
        grid[3][0] = 1;
        grid[3][1] = 1;
        grid[1][3] = 1;
        grid[2][3] = 1;
        grid[3][3] = 1;

        for (int[] row : grid) {
            for (int num : row) {
                System.out.print(num + " ");
            }
            System.out.println();
        }

        /*
         * [0,1,0,0,0]
         * [0,1,0,1,0]
         * [0,0,0,1,0]
         * [1,1,0,1,0]
         * [0,0,0,0,0]
         */

        Node start = new Node(0, 0);
        Node target = new Node(4, 4);

        new AStarAlgo(grid, start, target);

    }

}
