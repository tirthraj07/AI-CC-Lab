import java.util.*;

class ChessBoard {
    public char[][] chessBoard;
    public int N;

    public ChessBoard(char[][] board) {
        this.N = board.length;
        this.chessBoard = new char[N][N];
        for (int i = 0; i < N; i++) {
            this.chessBoard[i] = board[i].clone(); 
        }
    }

    public void printChessBoard() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(chessBoard[i][j] + " ");
            }
            System.out.println();
        }
    }
}


class Solution {
    private int N;
    private char[][] chessBoard;
    private ArrayList<ChessBoard> nQueensSolutions;

    public Solution(int N){
        this.N = N;    
        nQueensSolutions = new ArrayList<>();
        chessBoard = new char[N][N];
        for(int i=0; i<N; i++){
            chessBoard[i] = new char[N];
            for(int j=0; j<N; j++){
                chessBoard[i][j] = '.';
            }
        }
    }

    public void solveNQueens(){
        solve(0);
        if(nQueensSolutions.size()==0){
            System.out.println("No Solutions Found");
        }
        else{
            System.out.println("Total Solutions found: " + nQueensSolutions.size());
            for(ChessBoard board: nQueensSolutions){
                System.out.println("==== SOLUTION ===");
                board.printChessBoard();
                System.out.println("==== END ===");
            }
            System.out.println("Total Solutions found: " + nQueensSolutions.size());
            
        }

    }


    private void solve(int row){
        if(row >= N){
            nQueensSolutions.add(new ChessBoard(chessBoard));
            return;
        }

        for(int col = 0; col < N; col++){
            if(isSafe(row, col)){
                chessBoard[row][col] = 'Q';

                solve(row + 1);

                chessBoard[row][col] = '.';
            }   
        }
    }


    private boolean isSafe(int row, int col){
        if(chessBoard[row][col] == 'Q') return false;

        // UP
        for(int k=row; k>=0; k--){
            if(chessBoard[k][col] == 'Q') return false;
        }

        // TOP-LEFT
        for(int i=row, j = col; i>=0 && j>=0; i--, j--){
            if(chessBoard[i][j] == 'Q') return false;
        }

        // TOP-RIGHT
        for(int i=row, j=col; i>=0 && j<N; i--, j++){
            if(chessBoard[i][j] == 'Q') return false;
        }

        return true;
    }
}

public class Main {
    public static void main(String[] args){
        Solution sol = new Solution(10);
        sol.solveNQueens();
    }
}