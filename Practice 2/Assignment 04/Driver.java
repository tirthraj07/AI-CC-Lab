import java.util.*;

class Node implements Comparable<Node> {
    public int vertex;
    public int weight;

    public Node(int vertex, int weight){
        this.vertex = vertex;
        this.weight = weight;
    }

    @Override
    public String toString(){
        return "("+ vertex + ")";
    }

    @Override
    public int compareTo(Node other){
        return Integer.compare(this.weight, other.weight);
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Node){
            return this.vertex == ((Node) obj).vertex;
        }
        return false;
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

    public void primsMST(int start){
        int totalDistance = 0;
        boolean[] visited = new boolean[numNodes];

        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(start, 0));

        while(!pq.isEmpty()){
            Node node = pq.poll();
            int current = node.vertex;
            if(visited[current]) continue;

            visited[current] = true;
            totalDistance += node.weight;

            for(Edge edge: adjList.get(current)){
                int v = edge.to;
                int weight = edge.weight;
                pq.offer(new Node(v, weight));
            }
        }

        System.out.println("Total Weight = " + totalDistance);

    }

}


public class Driver{
    public static void main(String[] args){
        Graph g = new Graph(5);
        g.addEdge(0, 1, 2);
        g.addEdge(0, 3, 6);
        g.addEdge(1, 2, 3);
        g.addEdge(1, 3, 8);
        g.addEdge(1, 4, 5);
        g.addEdge(2, 4, 7);
        g.addEdge(3, 4, 9);

        g.primsMST(0);
    }
}