import java.util.ArrayList;
import java.util.PriorityQueue;

class Node implements Comparable<Node> {
    public int vertex;
    public int weight;
    public Node(int vertex, int weight){
        this.vertex = vertex;
        this.weight = weight;
    }

    @Override
    public String toString(){
        return "("+ vertex +")";
    }

    @Override
    public int compareTo(Node other){
        return Integer.compare(this.weight, other.weight);
    }
}

class Edge {
    public int to;
    public int weight;
    public Edge(int to, int weight){
        this.to = to;
        this.weight = weight;
    }

    @Override
    public String toString(){
        return "( " + to + ":" + weight +" )";
    }
}

class Graph {
    private int numNodes;
    private ArrayList<ArrayList<Edge>> adjList;


    public Graph(int numNodes){
        this.numNodes = numNodes;
        adjList = new ArrayList<>();
        for(int i = 0; i<numNodes; i++){
            adjList.add(new ArrayList<>());
        }        
    }

    public void addEdge(int u, int v, int weight){
        adjList.get(u).add(new Edge(v, weight));
        adjList.get(v).add(new Edge(u, weight));
    }

    public void primsMST(){
        int start = 0;
        boolean[] visited = new boolean[numNodes];
        int totalWeight = 0;
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(start, 0));

        while(!pq.isEmpty()){
            Node node = pq.poll();
            if(visited[node.vertex]) continue;
            visited[node.vertex] = true;
            totalWeight += node.weight;
            for(Edge edge: adjList.get(node.vertex)){
                pq.offer(new Node(edge.to, edge.weight));
            }
        }

        System.out.println("Total Weight : " + totalWeight);
    }

}

public class Main{
    public static void main(String[] args){
        Graph g = new Graph(5);
        g.addEdge(0, 1, 2);
        g.addEdge(0, 3, 6);
        g.addEdge(1, 2, 3);
        g.addEdge(1, 3, 8);
        g.addEdge(1, 4, 5);
        g.addEdge(2, 4, 7);
        g.addEdge(3, 4, 9);

        g.primsMST();
    }
}