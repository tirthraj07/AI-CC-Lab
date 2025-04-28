/*
Assignment 01 : DFS and BFS

Implement depth first search algorithm and breath first search algorithm, use an undirected graph and develop a recursive algorithm for searching all the vertices of a graph or tree structure
*/

#include <iostream>
#include <vector>
#include <unordered_map>
#include <stack>
#include <queue>
#include <algorithm>
using namespace std;

class Graph{
// Public APIs
public:
    int numNodes;
    vector<vector<int>> adjList;
    Graph(int numNodes){
        this->numNodes = numNodes;
        this->adjList = vector<vector<int>>(numNodes, vector<int>(numNodes, 0));
    }

    // Add edge to Graph
    void add_edge(int u, int v){
        if(u < 0 || v < 0 || u >= numNodes || v >= numNodes){
            cout << "Error: Out of bounds" << endl;
            return;
        }

        adjList[u][v] = 1;
        adjList[v][u] = 1;
    }

    // Show all nodes
    void dfs(int start){
        if(start < 0 || start >= numNodes){
            cout << "Error: Out of bounds" << endl;
            return;
        }
        
        cout << "== DFS ==" << endl;
        vector<bool> visited = vector<bool>(numNodes, false);
        visited[start] = true;
        dfs_helper(start, visited);

        // Incase if the graph is disjoint
        for(int i=0; i<numNodes; i++){
            if(visited[i] == false){
                cout << endl;
                dfs_helper(i, visited);    
            }
        }
        cout << endl;
    }

    // Search Target Node
    void dfs(int start, int target){
        if(start < 0 || start >= numNodes){
            cout << "Error: Out of bounds" << endl;
            return;
        }
        cout << "== DFS Search ==" << endl;
        vector<bool> visited = vector<bool>(numNodes, false);
        visited[start] = true;
        vector<int> path;
        path.push_back(start);
        bool isFound = false;
        dfs_helper(start, target, visited, path, isFound);
        if(!isFound) cout << "Node not found or reachable";
        cout << endl;
    }

    // Show all Node
    void bfs(int start){
        if(start < 0 || start >= numNodes){
            cout << "Error: Out of bounds" << endl;
            return;
        }
        cout << "== BFS ==" << endl;
        vector<bool> visited = vector<bool>(numNodes, false);
        queue<int> q = queue<int>();
        q.push(start);
        visited[start] = true;
        bfs_helper(q, visited);

        for(int i=0; i<numNodes; i++){
            if(visited[i] == false){
                cout << endl;
                bfs_helper(q, visited);
            }
        }
        cout << endl;
    }

    // Search Target Node
    void bfs(int start, int target){
        if(start < 0 || start >= numNodes){
            cout << "Error: Out of bounds" << endl;
            return;
        }

        vector<bool> visited = vector<bool>(numNodes, false);
        visited[start] = true; 
        queue<int> q = queue<int>();
        q.push(start);
        vector<int> path;
        bool isFound = false;
        cout << "== BFS Search ==" << endl;
        bfs_helper(q,target, visited, path, isFound);
        if(!isFound) cout << "Node not reachable";
        cout << endl;
    }


    // Print Graph
    void printGraph(){
        cout << "=== Graph ===" << endl;
        for(int i=0; i<numNodes; i++){
            cout << i << " -> ";
            for(int j=0; j<numNodes; j++){
                if(adjList[i][j] == 1){
                    cout << j << " ";
                }
            }
            cout << endl;
        }
    }


// Private APIs
private:
    void dfs_helper(int node, vector<bool>& visited){
        cout << node << " ";
        for(int i=0; i<numNodes; i++){
            if(adjList[node][i] == 1 && visited[i] == false){
                visited[i] = true;
                dfs_helper(i, visited);
            }
        }
    }

    void dfs_helper(int node, int target, vector<bool>& visited, vector<int> path, bool& isFound){
        if(isFound) return;
        if(node == target){
            isFound = true;
            cout << "Target Node Found" << endl;
            for(int i=0; i<path.size(); i++){
                cout << path[i];
                if(i != path.size() -1) cout << " -> ";
            }
            return;
        }
        for(int i=0; i<numNodes; i++){
            if(adjList[node][i] == 1 && visited[i] == false){
                visited[i] = true;
                path.push_back(i);
                dfs_helper(i, target, visited, path, isFound);
                path.erase(path.end()-1);
            }
        }
    }

    void bfs_helper(queue<int> q, vector<bool>& visited){
        if(q.empty()){
            return;
        }

        int node = q.front();
        cout << node << " ";
        q.pop();

        for(int i=0; i<numNodes; i++){
            if(adjList[node][i] == 1 && visited[i] == false){
                visited[i] = true;
                q.push(i);
            }
        }
        bfs_helper(q, visited);
    }

    void bfs_helper(queue<int> q, int target, vector<bool>& visited, vector<int> path, bool& isFound){
        if(q.empty() || isFound){
            return;
        }

        int node = q.front();
        q.pop();

        path.push_back(node);

        if(node == target){
            isFound = true;
            cout << "Target Node Found" << endl;
            for(int i=0; i<path.size(); i++){
                cout << path[i];
                if(i != path.size() -1) cout << " -> ";
            }
            return;
        }

        for(int i=0; i<numNodes; i++){
            if(adjList[node][i] == 1 && visited[i] == false){
                visited[i] = true;
                q.push(i);
            }
        }

        bfs_helper(q, target, visited, path, isFound);

        path.erase(path.end() - 1);
    }


};

int main(){
    Graph graph = Graph(5);

    graph.add_edge(0,1);
    graph.add_edge(0,4);

    graph.add_edge(1,2);
    graph.add_edge(1,3);
    graph.add_edge(1,4);

    graph.add_edge(2,3);

    graph.add_edge(3,4);

    graph.printGraph();

    graph.dfs(0);

    graph.dfs(0,2);

    graph.bfs(0);

    graph.bfs(0,3);
}
