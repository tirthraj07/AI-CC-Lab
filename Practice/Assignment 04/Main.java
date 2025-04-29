import java.util.ArrayList;

@SuppressWarnings("unused")
class Solution{
    private final ArrayList<ArrayList<String>> solutions = new ArrayList<>();
    private int N;
    private char[][] board;

    public void solveNQueen(int N){
        this.N = N;
        createNewBoard();
        solve(0);
        printSolutions();
    }

    private void solve(int row){
        if(row >= N){
            ArrayList<String> sol = new ArrayList<>();
            for(int i=0; i<N; i++){
                String boardRow = joinRow(board[i]);
                sol.add(boardRow);
            }
            solutions.add(sol);
            return;
        }

        for(int i=0; i<N; i++){
            if(isSafe(row, i)){
                board[row][i] = 'Q';
                
                solve(row + 1);

                board[row][i] = '.';
            }

        }

    }

    private boolean isSafe(int x, int y){
        // Check up
        for(int i=0; i<x; i++){
            if(board[i][y] == 'Q') return false;
        }

        // Check diagonal : up - left
        int i = x;
        int j = y;
        while(i>=0 && j>=0){
            if(board[i][j] == 'Q') return false;
            i--;
            j--;
        }

        // Check diagonal : up - right

        i = x;
        j = y;
        while(i >= 0 && j < N){
            if(board[i][j] == 'Q') return false;
            i--;
            j++;
        }

        return true;
    }



    private void createNewBoard(){
        board = new char[N][N];
        for(int i=0; i<N; i++){
            for(int j=0; j<N; j++){
                board[i][j] = '.';
            }
        }
    }

    private String joinRow(char[] row) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < row.length; i++) {
            sb.append(row[i]);
            if (i < row.length - 1) {
                sb.append("  ");
            }
        }
        return sb.toString();
    }

    private void printSolutions(){
        if(solutions.size() == 0){
            System.out.println("No Solutions Found!");
            return;
        }

        System.out.println(Integer.toString(solutions.size()) + " solutions found!");
        for(int i=0; i < solutions.size(); i++){
            System.out.println("====== Solution "+ (i+1) + " ======");
            printBoard(solutions.get(i));
            System.out.println("\n\n");
        }
    }


    private void printBoard(ArrayList<String> board){
        String horizontalBorder = new String("-").repeat(board.get(0).length() + 4);
        System.out.println(horizontalBorder);
        for(int i=0; i<board.size(); i++){
            System.out.print("| ");
            System.out.print(board.get(i));
            System.out.println(" |");
        }
        System.out.println(horizontalBorder);
    }

}


public class Main {
    public static void main(String[] args){
        int N = 10;
        new Solution().solveNQueen(N);
    }    
}
