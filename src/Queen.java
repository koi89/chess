import java.awt.Color;

public class Queen extends Piece {
    public Queen(Color color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public String getSymbol() {
        return getColor() == Color.WHITE ? "♕" : "♛";
    }

    @Override
    public boolean isValidMove(int fromX, int fromY, int toX, int toY) {
        int dx = Math.abs(toX - fromX);
        int dy = Math.abs(toY - fromY);
        return (dx == dy) || (fromX == toX || fromY == toY);
    }

    @Override
    public boolean canEat(int fromX, int fromY, int toX, int toY){
        int dx = Math.abs(toX - fromX);
        int dy = Math.abs(toY - fromY);
        return (dx == dy) || (fromX == toX || fromY == toY);
    }
} 
