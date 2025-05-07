import java.util.*;

class Node implements Comparable<Node>{
    public int x;
    public int y;
    public double g;
    public double h;
    public double f;
    public Node parent;

    public Node(int x, int y){
        this.x = x;
        this.y = y;
        g = Double.MAX_VALUE;
        h = 0;
        f = g + h;
        parent = null;
    }

    public void setGAndH(double g, double h){
        this.g = g;
        this.h = h;
        f = g+h;
    }


    @Override
    public int compareTo(Node other){
        return Double.compare(this.f, other.f);
    }

    @Override
    public int hashCode(){
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Node){
            Node other = (Node) obj;
            return this.x == other.x && this.y == other.y;
        }
        return false;
    }

    @Override
    public String toString(){
        return "(" + x + "," + y + ")";
    }
}

class AStarAlgorithm {
    private int ROW_MAX;
    private int COL_MAX;
    private int[] dx = {0,0,1,-1};
    private int[] dy = {1,-1,0,0};
    private int[][] maze;

    public AStarAlgorithm(int[][] maze, Node start, Node target) throws RuntimeException{
        this.maze = maze;
        this.ROW_MAX = maze.length;
        this.COL_MAX = maze[0].length;

        if(!isValidNode(start) || !isValidNode(target)){
            throw new RuntimeException("Start or Target supplied to A* are not valid");
        }

        start.setGAndH(0, heuristic(start, target));

        PriorityQueue<Node> openList = new PriorityQueue<>();
        HashSet<Node> closedList = new HashSet<>();
        openList.offer(start);

        while(!openList.isEmpty()){
            Node node = openList.poll();
            if(closedList.contains(node)) continue; // If we had already evaludated this node. no need to evaluate this branch again
            closedList.add(node);

            if(node.equals(target)){
                handleTargetFound(node);
                return;
            }

            ArrayList<Node> neighbourNodes = getNeighbours(node);

            for(Node neighbour : neighbourNodes){
                if(closedList.contains(neighbour)) continue;
                neighbour.setGAndH(node.g + 1, heuristic(neighbour, target));

                Node neighbourNodeInOpenList = openList.stream().filter(n -> n.equals(neighbour)).findFirst().orElse(null);
                if(neighbourNodeInOpenList != null){
                    if(neighbourNodeInOpenList.compareTo(neighbour) <= 0) continue;
                    // openList.remove(neighbourNodeInOpenList);    // This would take O(n) so instead, will keep it as it is and instead check if we had evaulated the node before to avoid recomputations   
                }
                openList.add(neighbour);
            }

        }

        System.out.println("Node not reachable");

    }   


    private ArrayList<Node> getNeighbours(Node node) {
        ArrayList<Node> neighbours = new ArrayList<>();
        
        for(int i=0; i<4; i++){
            int nx = node.x + dx[i];
            int ny = node.y + dy[i];
            if(!isValidCoordinate(nx, ny)) continue;
            Node neighbour = new Node(nx, ny);
            neighbour.parent = node;
            neighbours.add(neighbour);
        }

        return neighbours;
    }


    private void handleTargetFound(Node node) {
        ArrayList<Node> path = new ArrayList<>();
        while(node != null){
            path.add(node);
            node = node.parent;
        }
        Collections.reverse(path);

        System.out.println("Target node found! Tracing the path..");
        for (int i = 0; i < path.size(); i++) {
            System.out.print(path.get(i));
            if (i == path.size() - 1)
                System.out.println();
            else
                System.out.print(" -> ");
        }
        System.out.println("=== END ===");
    }


    private boolean isValidCoordinate(int x, int y){
        return x >= 0 && y >= 0 && x < ROW_MAX && y < COL_MAX && maze[x][y] != 1;
    }
    private boolean isValidNode(Node n){
        return n != null && isValidCoordinate(n.x, n.y);
    }
    private double heuristic(Node node, Node target) throws RuntimeException{
        if(node == null || target == null){
            throw new RuntimeException("Either Node or Target supplied to heuristic function is null");
        }
        int x1 = node.x;
        int y1 = node.y;
        int x2 = target.x;
        int y2 = target.y;
        return Math.abs(x1-x2) + Math.abs(y1-y2);
    }

}


public class Main {
    public static void main(String[] args){
        int[][] maze = new int[][]{
            {0,1,0,0,0},
            {0,1,0,1,0},
            {0,1,0,0,0},
            {0,1,0,1,0},
            {0,0,0,1,0}
        };

        Node start = new Node(0, 0);
        Node target = new Node(4, 4);
        new AStarAlgorithm(maze, start, target);
    }
}