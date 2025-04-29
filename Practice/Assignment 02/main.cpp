/*
A* Algorithm in C++
imo this is much easier if done in java
shiity pointers
*/

#include <iostream>
#include <queue>
#include <unordered_set>
#include <unordered_map>
#include <string>
#include <algorithm>
#include <functional>
using namespace std;

class Node {
// Public APIs 
public:
    int x, y;
    double g, h, f;
    Node* parent;

    Node(int x, int y){
        this->x = x;
        this->y = y;
        parent = nullptr;
    }

    bool equals(Node* other){
        return this->x == other->x && this->y == other->y;
    }

    string toString(){
        return "(" + to_string(this->x) + "," + to_string(this->y) + ")";
    }

};

// Custom Comparator For priority_queue
struct CompareNode { 
    bool operator()(const Node* n1,const Node* n2) const {
        return n1->f > n2->f;
    }
};

// Custom Function to remove element from queue
std::priority_queue<Node*, std::vector<Node*>, CompareNode> removeFromQueue(priority_queue<Node*, std::vector<Node*>, CompareNode> queue, Node* target){
    priority_queue<Node*, std::vector<Node*>, CompareNode> newQueue;

    while (!queue.empty()) {
        Node* node = queue.top();
        queue.pop();

        if (!(node->x == target->x && node->y == target->y)) {
            newQueue.push(node);
        }
    }

    return newQueue;
}

// Custom Hash Function for unordered_set
struct HashNode {
    size_t operator()(Node* node) const {
        return std::hash<std::string>()(node->toString());
    }
};

// Custom Equals Function for unordered_set
struct EqualNode {
    bool operator()(const Node* n1, const Node* n2) const {
        return n1->x == n2->x && n1->y == n2->y;
    }
};

class AStarAlgorithm{
// Private Data Members
private:
    priority_queue<Node*, vector<Node*>, CompareNode> openList;
    unordered_set<Node*, HashNode, EqualNode> openSet;
    unordered_set<Node*, HashNode, EqualNode> closedList;

    int ROW_MAX, COL_MAX;
    vector<vector<int>> arr; 
    int dx[4] = {0,0,1,-1};
    int dy[4] = {1,-1,0,0};
 
// Public APIs
public:

    AStarAlgorithm(vector<vector<int>> arr, Node* startNode, Node* targetNode){
        ROW_MAX = arr.size();
        COL_MAX = arr[0].size();
        this->arr = arr;
        
        startNode->g = 0;
        startNode->h = heuristic(startNode, targetNode);
        startNode->f = startNode->g + startNode->h;

        openList.push(startNode);
        openSet.insert(startNode);

        while(!openList.empty()){
            Node* currentNode = openList.top();
            openList.pop();

            if(currentNode->equals(targetNode)){
                printTracebackPath(currentNode);
                return;
            }

            closedList.insert(currentNode);

            for(int i=0; i<4; i++){
                int nx = currentNode->x + dx[i];
                int ny = currentNode->y + dy[i];
                if(!isValidCoordinate(nx,ny)) continue;

                Node* neighbour = new Node(nx,ny);
                neighbour->g = currentNode->g + 1;
                neighbour->h = heuristic(neighbour, targetNode);
                neighbour->f = neighbour->g + neighbour->h;
                neighbour->parent = currentNode;

                if(closedList.find(neighbour)!=closedList.end()) continue;

                auto it = openSet.find(neighbour);
                if (it != openSet.end()) {
                    Node* existingNeighbourNode = *it;
                    if(existingNeighbourNode->g <= neighbour->g) continue;
                    openList = removeFromQueue(openList,existingNeighbourNode);
                    openSet.erase(existingNeighbourNode);
                }

                openList.push(neighbour);
                openSet.insert(neighbour);
            }

        }

        cout << "No Path Found!" << endl;

    }


// Private APIs
private:
    double heuristic(Node* start, Node* target){
        return abs(start->x - target->x) + abs(start->y - target->y);
    }

    bool isValidCoordinate(int x, int y){
        return x >= 0 && y >= 0 && x < ROW_MAX && y < COL_MAX && arr[x][y] == 0;
    }

    void printTracebackPath(Node* node){
        vector<Node*> path;
        while(node != nullptr){
            path.push_back(node);
            node = node->parent;
        }
        reverse(path.begin(), path.end());
        cout << "Target Node Found" << endl;
        cout << "Tracing path.." << endl;
        for(int i=0; i<path.size(); i++){
            cout << path[i]->toString();
            if(i != path.size() -1) cout << " -> ";
        }
        cout << "\n== END ==" << endl;
    }

};



int main(){
    vector<vector<int>> arr;
    arr.push_back(vector<int>({0,1,0,0,0}));
    arr.push_back(vector<int>({0,1,0,1,0}));
    arr.push_back(vector<int>({0,0,0,1,0}));
    arr.push_back(vector<int>({0,1,0,1,0}));
    arr.push_back(vector<int>({0,1,0,0,0}));

    Node* startNode = new Node(0,0);
    Node* targetNode = new Node(4,4);
    AStarAlgorithm aStarAlgorithm = AStarAlgorithm(arr, startNode, targetNode);

    /*
        Target Node Found
        Tracing path..
        (0,0) -> (1,0) -> (2,0) -> (2,1) -> (2,2) -> (3,2) -> (4,2) -> (4,3) -> (4,4)
        == END ==
    */

}