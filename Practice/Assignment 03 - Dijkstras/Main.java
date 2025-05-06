import java.util.*;

class Node implements Comparable<Node> {
    public int vertex;
    public int distanceFromSource;

    public Node(int vertex, int distanceFromSource){
        this.vertex = vertex;
        this.distanceFromSource = distanceFromSource;
    }

    @Override
    public String toString(){
        return "(" + vertex + ")";
    }

    @Override
    public int compareTo(Node other){
        return Integer.compare(this.distanceFromSource, other.distanceFromSource);
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
        return "("+ to + " : " + weight +")";
    }
}

class Graph {
    private int numNodes;
    private ArrayList<ArrayList<Edge>> adjList;

    public Graph(int numNodes){
        this.numNodes = numNodes;
        adjList = new ArrayList<>();
        for(int i=0; i<numNodes; i++){
            adjList.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v, int weight){
        adjList.get(u).add(new Edge(v, weight));
        adjList.get(v).add(new Edge(u, weight));
    }

    public void printGraph(){
        for(int i=0; i<numNodes; i++){
            System.out.print(i + " -> ");
            for(Edge e : adjList.get(i)){
                System.out.print(e + " ");
            }
            System.out.println();
        }
    }


    public void dijkstras(int sourceVertex){
        if(sourceVertex < 0 || sourceVertex >= numNodes){
            System.out.println("Out of bounds : Source vertex : " + sourceVertex);
            return;
        }

        int dist[] = new int[numNodes];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[sourceVertex] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(sourceVertex, 0));

        while(!pq.isEmpty()){
            Node node = pq.poll();
            int u = node.vertex;
            int distanceFromSource = node.distanceFromSource;

            for(Edge edge: adjList.get(u)){
                int v = edge.to;
                int weight = edge.weight;
                int newDistanceFromSource = distanceFromSource + weight;
                if(dist[v] > newDistanceFromSource){
                    dist[v] = newDistanceFromSource;
                    pq.offer(new Node(v, dist[v]));
                }
            }
        }

        System.out.println("Distance from Source : " + sourceVertex + " to all nodes");
        for(int i=0; i<numNodes; i++){
            System.out.println(sourceVertex + " - " + i + " : " + dist[i]);
        }
        System.out.println("=== END ===");
    }

}


public class Main{
    public static void main(String[] args){
        Graph g = new Graph(5);
        g.addEdge(0, 1, 2);
        g.addEdge(0, 4, 1);
        g.addEdge(1, 4, 8);
        g.addEdge(1, 2, 3);
        g.addEdge(2, 4, 2);
        g.addEdge(4, 3, 7);
        g.addEdge(2, 3, 6);

        g.printGraph();

        g.dijkstras(0);
    }
}