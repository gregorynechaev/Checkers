public class Move {
    int fromRow, fromColumn, toRow, toColumn;


    Move(int r1, int c1, int r2, int c2) {
        fromRow = r1;
        fromColumn = c1;
        toRow = r2;
        toColumn = c2;
    }

    boolean jump() {
        return (Math.abs(fromRow - toRow) == 2);
    }
}