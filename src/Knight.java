import java.awt.Color;

public class Knight extends Piece {
    public Knight(Color color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public String getSymbol() {
        return getColor() == Color.WHITE ? "♘" : "♞";
    }

    @Override
    public boolean isValidMove(int fromX, int fromY, int toX, int toY) {
        int dx = Math.abs(toX - fromX);
        int dy = Math.abs(toY - fromY);
        return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
    }

    @Override
    public boolean canEat(int fromX, int fromY, int toX, int toY){
        int dx = Math.abs(toX - fromX);
        int dy = Math.abs(toY - fromY);
        return (dx == 2 && dy == 1) || (dx == 1 && dy == 2);
    }
}