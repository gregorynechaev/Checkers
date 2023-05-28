public class Move {
    static int fromRow;
    static int toRow;
    static int fromColumn;
    static int toColumn;

    Move(int r1, int c1, int r2, int c2) {
        fromRow = r1;
        toRow = r2;
        fromColumn = c1;
        toColumn = c2;
    }
}