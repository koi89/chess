import java.awt.Color;

public class Rook extends Piece {
    public Rook(Color color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public String getSymbol() {
        return getColor() == Color.WHITE ? "♖" : "♜";
    }

    @Override
    public boolean isValidMove(int fromX, int fromY, int toX, int toY) {
        return (fromX == toX) != (fromY == toY);
    }

    @Override
    public boolean canEat(int fromX, int fromY, int toX, int toY){
        return (fromX == toX) != (fromY == toY);
    }

    @Override
    public String toString() {
        return "Torre";
    }
}
