package backTracking;

import java.util.Scanner;

public class NQueen {
    public static void main(String args[]) throws Exception
    {
        Scanner scan = new Scanner(System.in);
        int N = scan.nextInt();
        int[][] board = new int[N][N];
        int Q = 0;
    }

    public static boolean NQueens(int[][] board, int Q,int N)
    {
        if(Q == N)//All Queens are places
            return true;

        for(int i = 0; i < N; i++)
        {
            if(possibleToPlace(board,Q,i))//Check if we can place queens
            {
                board[Q][i] = 1;
                if(NQueens(board, Q + 1, N))
                    return true;
                board[Q][i] = 0;
            }
        }

        return false;
    }

    private static boolean possibleToPlace(int[][] board, int row, int column) {
        //Check Vertical placement
        for(int i = 0; i < column; i++)
        {
            if(board[i][row] == 1)
                return false;
        }

        //check diagonal placement \
        int startI = 0;
        for(int i = 0; i < column; i++)
        {
            if(board[i][startI++] == 1)
                return false;
        }

        //check diagonal placement /
        startI = board.length - 1;
        for(int i = board.length - 1; i > column; i--)
        {
            if(board[i][startI--] == 1)
                return false;
        }
        return true;
    }
}
