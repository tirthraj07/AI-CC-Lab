#include <iostream>
#include <stack>
#include <queue>
#include <vector>
#include <unordered_map>
#include <string>
#include <algorithm>
using namespace std;

class Graph{
// private data members
private:
    int numNodes;   
    vector<vector<int>> adjMatrix;
 
// public APIs
public:
    Graph(int numNodes = 1){
        if(numNodes <= 0){
            cout << "Number of nodes need to be >= 1" << endl;
            throw invalid_argument("Number of nodes need to be >= 1");
            return;
        }
        
        this->numNodes = numNodes;
        for(int i=0; i<numNodes; i++){
            adjMatrix.push_back(vector<int>(numNodes, 0));
        }
    }

    void add_edge(int u, int v){
        if(isOutOfBounds(u) || isOutOfBounds(v)){
            cout << "Arguments supplied to add_edge function are out of bounds" << endl;
            throw invalid_argument("Arguments supplied to add_edge function are out of bounds");
            return;
        }

        adjMatrix[u][v] = 1;
        adjMatrix[v][u] = 1;
    }


    void printGraph(){
        cout << "== Printing Graph ==" << endl;
        cout << this->toString();
        cout << "== END ==" << endl;
    }

    void dfs(int start){
        if(isOutOfBounds(start)){
            cout << "Argument supplied to dfs function is out of bounds" << endl;
            throw invalid_argument("Argument supplied to dfs function is out of bounds");
            return;
        }

        vector<bool> visited(numNodes, false);
        visited[start] = true;
        cout << "=== DFS ===" << endl;
        dfs(start, visited);
        cout << endl;
        // Take care of disjoint graph

        for(int i=0; i<numNodes; i++){
            if(visited[i] == false){
                cout << "\nDisjoint graph at  " << i << ". Starting dfs again from " << i << " : " << endl;
                dfs(start, visited);
            }
        }   

        cout << "=== END ===" << endl;
    }


    void bfs(int start){
        if(isOutOfBounds(start)){
            cout << "Argument supplied to bfs function is out of bounds" << endl;
            throw invalid_argument("Argument supplied to bfs function is out of bounds");
            return;
        }

        vector<bool> visited(numNodes, false);
        queue<int> q;
        q.push(start);
        visited[start] = true;
        cout << "=== BFS ===" << endl;
        bfs(q, visited);
        cout << endl;   

        for(int i=0; i<numNodes; i++){
            if(visited[i] == false){
                cout << "\nDisjoint graph at  " << i << ". Starting bfs again from " << i << " : " << endl;
                visited[i] = true;
                q.push(i);
                bfs(q, visited);
            }
        }   

        cout << "=== END ===" << endl;  
    }   

    void bfs(int start, int target){
        if(isOutOfBounds(start) || isOutOfBounds(target)){
            cout << "Arguments supplied to bfs function are out of bounds" << endl;
            throw invalid_argument("Argument supplied to bfs function are out of bounds");
            return;
        }

        queue<int> q;
        vector<bool> visited(numNodes, false);
        unordered_map<int, int> childToParent;
        bool isPathFound = false;
        q.push(start);
        visited[start] = true;
        childToParent[start] =  -1;

        cout << "=== BFS Path Finder ===" << endl;
        bfs(q, target, visited, childToParent, isPathFound);
    }

    void dfs(int start, int target){
        if(isOutOfBounds(start) || isOutOfBounds(target)){
            cout << "Arguments supplied to dfs function are out of bounds" << endl;
            throw invalid_argument("Argument supplied to dfs function are out of bounds");
            return;
        }

        vector<bool> visited(numNodes, false);
        vector<int> currentPath;
        visited[start] = true;
        bool isTargetFound = false;

        cout << "=== DFS Path Finder ===" << endl;
        dfs(start, target, visited, currentPath, isTargetFound);
        if(!isTargetFound){
            cout << "No Path Found" << endl;
            cout << "=== END ===" << endl;
        }
    }


// private APIs
private:

    bool isOutOfBounds(int u){
        return u < 0 || u >= numNodes;
    }

    string toString(){
        string s = "";
        for(int i=0; i<numNodes; i++){
            s += to_string(i) + " -> ";
            for(int j=0; j<numNodes; j++){
                if(adjMatrix[i][j] == 1){
                    s += to_string(j) + " ";
                }
            }
            s += '\n';
        }
        return s;
    }

    void dfs(int node, vector<bool>& visited){
        cout << node << " ";
        for(int i=0; i<numNodes; i++){
            if(adjMatrix[node][i] == 1 && visited[i] == false){
                visited[i] = true;
                dfs(i, visited);
            }
        }
    }


    void bfs(queue<int>& q, vector<bool>& visited){
        if(q.empty()){
            return;
        }

        int node = q.front();
        cout << node << " ";
        q.pop();

        for(int i=0; i<numNodes; i++){
            if(adjMatrix[node][i] == 1 && visited[i] == false){
                visited[i] = true;
                q.push(i);
            }
        }

        bfs(q, visited);
    }

    void bfs(queue<int>& q, int target, vector<bool>& visited, unordered_map<int, int>& childToParent, bool& isPathFound){
        if(q.empty()){
            if(!isPathFound){
                cout << "Target Node not reachable" << endl;
            }
            return;
        }
        if(isPathFound) return;

        int node = q.front();
        q.pop();
        

        if(node == target){
            cout << "Target Found. Retracing the path.. " << endl;
            vector<int> path;

            while(node != -1){
                path.push_back(node);
                node = childToParent[node];
            }

            reverse(path.begin(), path.end());
            for(int i=0; i<path.size(); i++){
                cout << path[i]; 
                i == path.size() - 1 ? cout << endl : cout << " -> ";
            }
            cout << "=== END ===" << endl;
            isPathFound = true;
            return;
        }

        for(int i=0; i<numNodes; i++){
            if(adjMatrix[node][i] == 1 && visited[i] == false){
                visited[i] = true;
                q.push(i);
                childToParent[i] = node;
            }
        }

        bfs(q, target, visited, childToParent, isPathFound);
    }

    void dfs(int node, int target, vector<bool>& visited,vector<int>& currentPath,bool& isTargetFound){
        if(isTargetFound) return;

        currentPath.push_back(node);
        
        if(node == target){
            isTargetFound = true;
            cout << "\nTarget Node Found! Retracing the path.." << endl;
            for(int i=0; i<currentPath.size(); i++){
                cout << currentPath[i];
                i == currentPath.size() - 1 ? cout << endl : cout << " -> ";
            }
            cout << "=== END ===" << endl;
            return;
        }   

        for(int i=0; i<numNodes; i++){
            if(adjMatrix[node][i] == 1 && visited[i] == false){
                visited[i] = true;
                dfs(i, target, visited, currentPath, isTargetFound);
            }
        }

        currentPath.erase(currentPath.end() - 1);
    }

};  


int main(){
    Graph g(8);
    g.add_edge(0,1);
    g.add_edge(0,2);
    g.add_edge(1,3);
    g.add_edge(2,3);
    g.add_edge(2,5);
    g.add_edge(3,4);
    g.add_edge(4,6);
    g.add_edge(5,6);
    g.add_edge(7,4);

    g.printGraph();

    g.dfs(0);
    g.dfs(0, 7);

    g.bfs(0);
    g.bfs(0,7);
}