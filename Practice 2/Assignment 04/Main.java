import java.util.*;

class Node implements Comparable<Node> {
    public int vertex;
    public int distance;

    public Node(int vertex, int distance){
        this.vertex = vertex;
        this.distance = distance;
    }

    @Override
    public String toString(){
        return "("+ vertex + ")";
    }

    @Override
    public int compareTo(Node other){
        return Integer.compare(this.distance, other.distance);
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
        return "(" + to + ":" + weight + ")";
    }
}


class Graph{
    public int numNodes;
    public ArrayList<ArrayList<Edge>> adjList;

    public Graph(int numNodes){
        adjList = new ArrayList<>();
        this.numNodes = numNodes;
        for(int i=0; i<numNodes; i++){
            adjList.add(new ArrayList<>());
        }
    }

    public void addEdge(int u, int v, int weight){
        adjList.get(u).add(new Edge(v, weight));
        adjList.get(v).add(new Edge(u, weight));
    }

    public void dijkstra(int start){
        int dist[] = new int[numNodes];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[start] = 0;

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(start, 0));

        while(!pq.isEmpty()){
            Node node = pq.poll();
            int u = node.vertex;
            int distOfNode = node.distance;

            for(Edge edge: adjList.get(u)){
                int v = edge.to;
                int weight = edge.weight;
                if(weight + distOfNode < dist[v]){
                    dist[v] = weight + distOfNode;
                    pq.offer(new Node(v, dist[v]));
                }
            }
        }

        for(int i=0; i<numNodes; i++){
            System.out.println(i + ":" + dist[i]);
        }

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
        g.dijkstra(0);
    }
}