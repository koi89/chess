import java.awt.Color;

public abstract class Piece {
    private final Color color;
    private int x;
    private int y;

    public Piece(Color color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public Color getColor() { return color; }
    public int getX() { return x; }
    public int getY() { return y; }
    
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract String getSymbol();

    public abstract boolean isValidMove(int fromX, int fromY, int toX, int toY);

    public abstract boolean canEat(int fromX, int fromY, int toX, int toY);
    
}