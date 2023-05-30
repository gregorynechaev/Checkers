import java.util.ArrayList;

public class CheckersInfo {

    int[][] board;
    CheckersInfo() {
        board = new int[8][8];
        ready();
    }

    static final int
            EMPTY = 0,
            LIGHT = 1,
            LIGHT_QUEEN = 2,
            DARK = 3,
            DARK_QUEEN = 4;


    void ready() {
        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (row % 2 == column % 2) {
                    if (row < 3)
                        board[row][column] = DARK;
                    else if (row > 4)
                        board[row][column] = LIGHT;
                    else
                        board[row][column] = EMPTY;
                } else {
                    board[row][column] = EMPTY;
                }
            }
        }
    }




    Move[] getAvailableJumps(int player, int row, int column) {
        if (player != LIGHT && player != DARK)
            return null;
        int playerQueen;
        if (player == LIGHT)
            playerQueen = LIGHT_QUEEN;
        else
            playerQueen = DARK_QUEEN;
        ArrayList<Move> moves = new ArrayList<Move>();
        if (board[row][column] == player || board[row][column] ==  playerQueen) {

            if (canJump(player, row, column, row+1, column+1, row+2, column+2))
                moves.add(new Move(row, column, row+2, column+2));
            if (canJump(player, row, column, row-1, column+1, row-2, column+2))
                moves.add(new Move(row, column, row-2, column+2));
            if (canJump(player, row, column, row+1, column-1, row+2, column-2))
                moves.add(new Move(row, column, row+2, column-2));
            if (canJump(player, row, column, row-1, column-1, row-2, column-2))
                moves.add(new Move(row, column, row-2, column-2));
        }
        if (moves.size() == 0)
            return null;
        else {
            Move[] moveArray = new Move[moves.size()];
            for (int i = 0; i < moves.size(); i++)
                moveArray[i] = moves.get(i);
            return moveArray;
        }
    }



    Move[] getAvailableMoves(int player) {

        if (player != LIGHT && player != DARK)
            return null;

        int playerQueen;
        if (player == LIGHT)
            playerQueen = LIGHT_QUEEN;
        else
            playerQueen = DARK_QUEEN;

        ArrayList<Move> moves = new ArrayList<Move>();

        for (int row = 0; row < 8; row++) {
            for (int column = 0; column < 8; column++) {
                if (board[row][column] == player || board[row][column] == playerQueen) {
                    if (canJump(player, row, column, row + 1, column + 1, row + 2, column + 2))
                        moves.add(new Move(row, column, row + 2, column + 2));
                    if (canJump(player, row, column, row - 1, column + 1, row - 2, column + 2))
                        moves.add(new Move(row, column, row - 2, column + 2));
                    if (canJump(player, row, column, row + 1, column - 1, row + 2, column - 2))
                        moves.add(new Move(row, column, row + 2, column - 2));
                    if (canJump(player, row, column, row - 1, column - 1, row - 2, column - 2))
                        moves.add(new Move(row, column, row - 2, column - 2));
                }
            }
        }

        if (moves.size() == 0) {
            for (int row = 0; row < 8; row++) {
                for (int column = 0; column < 8; column++) {
                    if (board[row][column] == player || board[row][column]==playerQueen) {
                        if (canMove(player, row, column, row + 1, column + 1))
                            moves.add(new Move(row, column, row + 1, column + 1));

                        if (canMove(player, row, column, row - 1, column + 1))
                            moves.add(new Move(row, column, row - 1, column + 1));

                        if (canMove(player, row, column, row + 1, column - 1))
                            moves.add(new Move(row, column, row + 1, column - 1));

                        if (canMove(player, row, column, row - 1, column - 1))
                            moves.add(new Move(row, column, row - 1, column - 1));
                    }
                }
            }
        }

        if (moves.size() == 0) return null;
        else {
            Move[] moveAr = new Move[moves.size()];
            for (int i = 0; i < moves.size(); i++) moveAr[i] = moves.get(i);
            return moveAr;
        }
    }






    private boolean canJump(int player, int r1, int c1, int r2, int c2, int r3, int c3) {

        if (r3 < 0 || r3 >= 8 || c3 < 0 || c3 >= 8 || board[r3][c3] != EMPTY )
            return false;



        if (player == LIGHT) {
            if (board[r1][c1] == LIGHT && r3 > r1)
                return false;
            if (board[r2][c2] != DARK && board[r2][c2] != DARK_QUEEN)
                return false;
            return true;
        }
        else {
            if (board[r1][c1] == DARK && r3 < r1)
                return false;
            if (board[r2][c2] != LIGHT && board[r2][c2] !=LIGHT_QUEEN)
                return false;
            return true;
        }

    }


    private boolean canMove(int player, int r1, int c1, int r2, int c2) {
        if (r2 < 0 || r2 >= 8 || c2 < 0 || c2 >= 8) return false;

        if(board[r2][c2] != EMPTY) return false;


        if (player == LIGHT) {
            if (board[r1][c1] == LIGHT && r2 > r1)
                return false;
            return true;
        }
        else {
            if (board[r1][c1] == DARK && r2 < r1)
                return false;
            return true;
        }
    }



    void makeMove(Move move) {
        makeMove(move.fromRow, move.fromColumn, move.toRow, move.toColumn);
    }

    void makeMove(int fromRow, int fromColumn, int toRow, int toColumn) {
        board[toRow][toColumn] = board[fromRow][fromColumn];
        board[fromRow][fromColumn] = EMPTY;
        if (Math.abs(fromRow - toRow) == 2) {
            int jumpRow = (fromRow + toRow) / 2;
            int jumpColumn = (fromColumn + toColumn) / 2;
            board[jumpRow][jumpColumn] = EMPTY;
        }
        if (toRow == 0 && board[toRow][toColumn] == LIGHT) board[toRow][toColumn] = LIGHT_QUEEN;
        if (toRow == 7 && board[toRow][toColumn] == DARK) board[toRow][toColumn] = DARK_QUEEN;
    }


    int pieceAt(int row, int column) {
        return board[row][column];
    }




}