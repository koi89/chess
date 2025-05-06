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
        int direction = getColor() == Color.WHITE ? -1 : 1;
        int startRow = getColor() == Color.WHITE ? 6 : 1;

        if (fromX == toX) {
            // Vertical
            if (toY - fromY == direction) {
                return true;
            } else if (fromY == startRow && toY - fromY == 2 * direction) {
                return true;
            }
        } else if (Math.abs(toX - fromX) == 1 && (toY - fromY) == direction) {
            // Diagonal
            return true;
        }
        return false;
    }

    @Override
    public boolean canEat(int fromX, int fromY, int toX, int toY){
        int direction = getColor() == Color.WHITE ? -1 : 1;

        return Math.abs(toX - fromX) == 1 && (toY - fromY) == direction;
    }

    @Override
    public String toString() {
        return "Peon";
    }
}