import java.awt.Color;

public class Pawn extends Piece {
    public Pawn(Color color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public String getSymbol() {
        return getColor() == Color.WHITE ? "♙" : "♟";
    }

    @Override
    public boolean isValidMove(int fromX, int fromY, int toX, int toY) {
        return isValidPawnMove(fromX, fromY, toX, toY) || isValidPawnCapture(fromX, fromY, toX, toY);
    }

    private boolean isValidPawnMove(int fromX, int fromY, int toX, int toY) {
        if (fromX != toX) {
            return false;
        }

        int direction = getColor() == Color.WHITE ? -1 : 1;
        int startRow = getColor() == Color.WHITE ? 6 : 1;

        int deltaY = toY - fromY;

        if (deltaY == direction) {
            return true;
        } else if (fromY == startRow && deltaY == 2 * direction) {
            return true;
        }

        return false;
    }

    private boolean isValidPawnCapture(int fromX, int fromY, int toX, int toY) {
        int direction = getColor() == Color.WHITE ? -1 : 1;

        int deltaX = Math.abs(toX - fromX);
        int deltaY = toY - fromY;

        return deltaX == 1 && deltaY == direction;
    }

    @Override
    public String toString() {
        return "Peón"; 
    }
}