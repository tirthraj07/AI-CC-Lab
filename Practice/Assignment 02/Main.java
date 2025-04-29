import java.util.*;

class Node implements Comparable<Node> {

    public int x,y;
    public double f, g, h;
    public Node parent;         // To Retrace the path

    public Node (int x, int y){
        this.x = x;
        this.y = y;
        this.g = Double.MAX_VALUE;
        this.h = 0;
        this.f = Double.MAX_VALUE;
        this.parent = null;
    }

    @Override
    public int compareTo(Node other){
        return Double.compare(this.f, other.f);
    }

    @Override
    public boolean equals(Object obj){
        if (obj instanceof Node other) {
            return this.x == other.x && this.y == other.y;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

}

class AStartAlgorithm {
    // Data Members
    private int ROW_MAX,COLUMN_MAX;
    private PriorityQueue<Node> openList;
    private HashSet<Node> closedList;
    private int[] dx = new int[]{1, -1, 0, 0};
    private int[] dy = new int[]{0, 0, -1, 1};
    private int[][] arr;

    // Public APIs
    public AStartAlgorithm(int[][] arr, Node startNode, Node targetNode){
        this.arr = arr;
        ROW_MAX = arr.length;
        COLUMN_MAX = arr[0].length;
        openList = new PriorityQueue<>();
        closedList = new HashSet<>();

        startNode.g = 0;
        startNode.h = heuristic(startNode, targetNode);
        startNode.f = startNode.g + startNode.h;
        
        openList.offer(startNode);

        while(!openList.isEmpty()){
            Node currentNode = openList.poll();
            
            if(currentNode.equals(targetNode)){
                handleTargetNodeFound(currentNode);
                return;
            }

            closedList.add(currentNode);

            for(int i=0; i<4; i++){
                
                int nx = currentNode.x + dx[i];
                int ny = currentNode.y + dy[i];

                if(!isValidCoordinate(nx, ny)) continue;

                Node neighbour = new Node(nx, ny);
                neighbour.g = currentNode.g + 1;
                neighbour.h = heuristic(neighbour, targetNode);
                neighbour.f = neighbour.g + neighbour.h;
                neighbour.parent = currentNode;
                
                if(closedList.contains(neighbour)) continue;

                Node exisitingNeighbourInOpenList = openList.stream().filter(n -> n.equals(neighbour)).findFirst().orElse(null);
                
                if(exisitingNeighbourInOpenList != null){
                    if(exisitingNeighbourInOpenList.g <= neighbour.g) continue;
                    openList.remove(exisitingNeighbourInOpenList);
                }
                openList.add(neighbour);
            }
        }

        System.out.println("No Path Found");
    }


    // Private APIs
    private double heuristic(Node currentNode, Node targetNode){
        // Using Euclidian Distance
        int x1 = currentNode.x;
        int x2 = targetNode.x;
        int y1 = currentNode.y;
        int y2 = targetNode.y;
        return Math.abs(x1-x2) + Math.abs(y1-y2);
    }

    private void handleTargetNodeFound(Node node){
        System.out.println("Target Node Found!");
        List<Node> path = new LinkedList<>();
        while(node != null){
            path.add(node);
            node = node.parent;
        }
        Collections.reverse(path);
        System.out.println("Printing the Path..");
        for(int i=0; i<path.size(); i++){
            System.out.print(path.get(i));
            if(i != path.size() - 1) System.out.print(" -> ");
        }
        System.out.println("\n== END ==");
    }

    private boolean isValidCoordinate(int x, int y){
        return x >= 0 && y >= 0 && x < ROW_MAX && y < COLUMN_MAX && arr[x][y] == 0;
    }

}


public class Main{
    public static void main(String[] args){
        
        int[][] arr = new int[][]{
            {0,1,0,0,0},
            {0,1,0,1,0},
            {0,0,0,1,0},
            {0,1,0,1,0},
            {0,1,0,0,0}
        };

        Node startNode = new Node(0, 0);
        Node targetNode = new Node(4,4);
        new AStartAlgorithm(arr, startNode, targetNode);
        /*
            Target Node Found!
            Printing the Path..
            (0,0) -> (1,0) -> (2,0) -> (2,1) -> (2,2) -> (3,2) -> (4,2) -> (4,3) -> (4,4)
            == END ==
        */
    }
}