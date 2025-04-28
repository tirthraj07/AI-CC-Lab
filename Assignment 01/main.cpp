/*
 Implement depth first search algorithm and Breadth First Search algorithm, use an undirected
 graph and develop a recursive algorithm for searching all the vertices of a graph or tree data
 structure.
*/

#include <iostream>
#include <vector>
#include <stack>
#include <queue>
#include <unordered_map>
#include <algorithm>
using namespace std;

class Graph
{
public:
    int numNodes;
    vector<vector<int>> adjacency_matrix;

    Graph(int numNodes)
    {
        if (numNodes <= 0)
        {
            cout << "Cannot create graph with 0 or less nodes" << endl;
            delete this;
        }

        this->numNodes = numNodes;
        for (int i = 0; i < numNodes; i++)
        {
            vector<int> row = vector<int>(numNodes);
            adjacency_matrix.push_back(row);
        }
    }

    ~Graph()
    {
        cout << "Deleting Graph..";
    }

    void add_edge(int u, int v)
    {
        if (u < 0 || v < 0 || u >= numNodes || v >= numNodes)
        {
            cout << "Error: Out of bounds. u = " << u << " v = " << v << endl;
            return;
        }

        adjacency_matrix[u][v] = 1;
        adjacency_matrix[v][u] = 1;
    }

    void dfs(int start = 0)
    {
        if (start < 0 || start >= numNodes)
        {
            cout << "Error: Out of bounds. Start = " << start << endl;
            return;
        }

        vector<bool> visited = vector<bool>(numNodes);
        stack<int> st = stack<int>();
        st.push(start);
        visited[start] = true;
        cout << "==== DFS ====" << endl;
        while (!st.empty())
        {
            int node = st.top();
            cout << node << " ";
            st.pop();
            for (int i = adjacency_matrix[node].size() - 1; i >= 0; i--)
            {
                if (adjacency_matrix[node][i] == true && visited[i] == false)
                {
                    st.push(i);
                    visited[i] = true;
                }
            }
        }
        cout << "\n==== END OF DFS ====" << endl;
    }

    void bfs(int start = 0)
    {
        if (start < 0 || start >= numNodes)
        {
            cout << "Error: Out of bounds. Start = " << start << endl;
            return;
        }

        vector<int> visited = vector<int>(numNodes);
        queue<int> q = queue<int>();
        q.push(start);
        visited[start] = true;
        cout << "==== BFS ====" << endl;
        while (!q.empty())
        {
            int node = q.front();
            cout << node << " ";
            q.pop();

            for (int i = 0; i < numNodes; i++)
            {
                if (adjacency_matrix[node][i] == 1 && visited[i] == false)
                {
                    q.push(i);
                    visited[i] = true;
                }
            }
        }
        cout << "\n==== END OF BFS ====" << endl;
    }

    void find_node_dfs(int start, int target)
    {
        if (start < 0 || target < 0 || start >= numNodes || target >= numNodes)
        {
            cout << "Error: Out of bounds. start = " << start << " target = " << target << endl;
            return;
        }

        unordered_map<int, int> child_parent_map = unordered_map<int, int>();
        stack<int> st = stack<int>();
        st.push(start);
        child_parent_map[start] = -1;

        while (!st.empty())
        {
            int curr = st.top();
            st.pop();
            if (curr == target)
            {
                cout << "Target element found" << endl;
                vector<int> route;

                while (curr != -1)
                {
                    route.push_back(curr);
                    curr = child_parent_map.at(curr);
                }

                reverse(route.begin(), route.end());
                for (int i = 0; i < route.size(); i++)
                {
                    cout << route[i];
                    if (i != route.size() - 1)
                        cout << " -> ";
                }
                cout << endl;
                return;
            }

            for (int i = 0; i < adjacency_matrix[curr].size(); i++)
            {
                if (adjacency_matrix[curr][i] == 1 && child_parent_map.find(i) == child_parent_map.end())
                {
                    st.push(i);
                    child_parent_map[i] = curr;
                }
            }
        }

        cout << "Target element not found or not reachable from start node" << endl;
    }

    void find_node_bfs(int start, int target)
    {
        if (start < 0 || target < 0 || start >= numNodes || target >= numNodes)
        {
            cout << "Error: Out of bounds. start = " << start << " target = " << target << endl;
            return;
        }

        unordered_map<int, int> child_parent_map = unordered_map<int, int>();
        queue<int> q = queue<int>();
        q.push(start);
        child_parent_map[start] = -1;

        while (!q.empty())
        {
            int curr = q.front();
            q.pop();
            if (curr == target)
            {
                cout << "Target element found" << endl;
                vector<int> route;

                while (curr != -1)
                {
                    route.push_back(curr);
                    curr = child_parent_map.at(curr);
                }

                reverse(route.begin(), route.end());
                for (int i = 0; i < route.size(); i++)
                {
                    cout << route[i];
                    if (i != route.size() - 1)
                        cout << " -> ";
                }
                cout << endl;
                return;
            }

            for (int i = 0; i < adjacency_matrix[curr].size(); i++)
            {
                if (adjacency_matrix[curr][i] == 1 && child_parent_map.find(i) == child_parent_map.end())
                {
                    q.push(i);
                    child_parent_map[i] = curr;
                }
            }
        }

        cout << "Target element not found or not reachable from start node" << endl;
    }

    void print_graph()
    {
        for (int i = 0; i < numNodes; i++)
        {
            cout << i << "-> ";
            for (int j = 0; j < numNodes; j++)
            {
                if (adjacency_matrix[i][j] == 1)
                {
                    cout << j << " ";
                }
            }
            cout << endl;
        }
    }
};

int main()
{

    Graph g = Graph(6);

    g.add_edge(1, 2);
    g.add_edge(1, 3);
    g.add_edge(2, 3);
    g.add_edge(2, 5);
    g.add_edge(2, 4);
    g.add_edge(3, 5);
    g.add_edge(4, 5);

    g.print_graph();
    g.dfs(1);
    g.bfs(1);
    g.find_node_dfs(1, 5);
    g.find_node_bfs(1, 5);
}