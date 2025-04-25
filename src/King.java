import java.awt.Color;

public class King extends Piece {
    public King(Color color, int x, int y) {
        super(color, x, y);
    }

    @Override
    public String getSymbol() {
        return getColor() == Color.WHITE ? "♔" : "♚";
    }

    @Override
    public boolean isValidMove(int fromX, int fromY, int toX, int toY) {
        int dx = Math.abs(toX - fromX);
        int dy = Math.abs(toY - fromY);
        return dx <= 1 && dy <= 1;
    }

    @Override
    public boolean canEat(int fromX, int fromY, int toX, int toY){
        int dx = Math.abs(toX - fromX);
        int dy = Math.abs(toY - fromY);
        return dx <= 1 && dy <= 1;
    }
} 
