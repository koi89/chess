
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

class ChessGame extends Frame {

    public ChessGame() {
        setTitle("Java Chess");
        ChessBoard board = new ChessBoard();
        add(board);
        setSize(800, 800);
        setVisible(true);

        // Handle window closing
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        new ChessGame();
    }
}

class ChessBoard extends Canvas implements MouseListener {

    private Piece[][] board = new Piece[8][8];
    private Piece selectedPiece = null;
    private int selectedCol = -1;
    private int selectedRow = -1;
    ArrayList<Piece> blancas = new ArrayList<>();
    ArrayList<Piece> negras = new ArrayList<>();

    public ChessBoard() {
        setBackground(Color.LIGHT_GRAY);
        addMouseListener(this);
        initializeBoard();
    }

    private void initializeBoard() {
        // Inicializa las negras
        board[0][0] = new Rook(Color.BLACK, 0, 0);
        board[0][1] = new Knight(Color.BLACK, 1, 0);
        board[0][2] = new Bishop(Color.BLACK, 2, 0);
        board[0][3] = new Queen(Color.BLACK, 3, 0);
        board[0][4] = new King(Color.BLACK, 4, 0);
        board[0][5] = new Bishop(Color.BLACK, 5, 0);
        board[0][6] = new Knight(Color.BLACK, 6, 0);
        board[0][7] = new Rook(Color.BLACK, 7, 0);

        for (int col = 0; col < 8; col++) {
            board[1][col] = new Pawn(Color.BLACK, col, 1);
        }

        for(int i = 0; i <= 1; i++){
            for(int j = 0; j <= 7; j++){
                negras.add(board[i][j]);
            }
        }

        // Inicializa las blancas
        board[7][0] = new Rook(Color.WHITE, 0, 7);
        board[7][1] = new Knight(Color.WHITE, 1, 7);
        board[7][2] = new Bishop(Color.WHITE, 2, 7);
        board[7][3] = new Queen(Color.WHITE, 3, 7);
        board[7][4] = new King(Color.WHITE, 4, 7);
        board[7][5] = new Bishop(Color.WHITE, 5, 7);
        board[7][6] = new Knight(Color.WHITE, 6, 7);
        board[7][7] = new Rook(Color.WHITE, 7, 7);

        for (int col = 0; col < 8; col++) {
            board[6][col] = new Pawn(Color.WHITE, col, 6);
        }

        for(int i = 6; i <= 7; i++){
            for(int j = 0; j <= 7; j++){
                blancas.add(board[i][j]);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();
        int squareWidth = width / 8;
        int squareHeight = height / 8;

        // Dibuja el tablero
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if ((row + col) % 2 == 0) {
                    g2d.setColor(new Color(240, 217, 181)); //Cuadrado blanco
                } else {
                    g2d.setColor(new Color(181, 136, 99)); // Cuadrado negro
                }
                g2d.fillRect(col * squareWidth, row * squareHeight, squareWidth, squareHeight);
            }
        }

        // Draw pieces
        g2d.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 40));
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = board[row][col];
                if (piece != null) {
                    g2d.setColor(piece.getColor());
                    String symbol = piece.getSymbol();
                    int x = col * squareWidth + squareWidth / 4;
                    int y = row * squareHeight + squareHeight - squareHeight / 4;
                    g2d.drawString(symbol, x, y);
                }
            }
        }

        // Resalta la pieza seleccionada
        if (selectedPiece != null) {
            Highlight(g2d, squareHeight, squareWidth);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int col = e.getX() / (getWidth() / 8);
        int row = e.getY() / (getHeight() / 8);
        Piece piece = board[row][col];
        
        removeHighlight(col, row);

        if (selectedPiece == null) {
            // Selecciona pieza
            if (isValidPosition(row, col) && piece != null) {
                selectedPiece = piece;
                selectedCol = col;
                selectedRow = row;
                repaint();
            }
        } else {
            // Verifica posicion y deselecciona
            if (col == selectedCol && row == selectedRow) {
                selectedPiece = null;
                selectedCol = -1;
                selectedRow = -1;
                repaint();

            } else if (isValidPosition(row, col) && isValidMove(col, row)) {

                Piece targetPiece = board[row][col];

                // Eliminar pieza del arrayList
                if (targetPiece != null) {
                    if (targetPiece.getColor() == Color.WHITE) {
                        blancas.remove(targetPiece);
                    } else {
                        negras.remove(targetPiece);
                    }
                    log(selectedPiece, targetPiece);
                }

                checkEnd();

                // Mover pieza
                board[selectedRow][selectedCol] = null;
                selectedPiece.setPosition(col, row);
                board[row][col] = selectedPiece;
                selectedPiece = null;
                selectedCol = -1;
                selectedRow = -1;
                repaint();
            }
        }
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    private boolean isValidMove(int toCol, int toRow) {
        if (selectedPiece == null) {
            return false;
        }

        int fromX = selectedCol;
        int fromY = selectedRow;
        int toX = toCol;
        int toY = toRow;

        Piece targetPiece = board[toY][toX];

        if (isFriendly(targetPiece, selectedPiece)) {
            return false;
        }

        return selectedPiece.isValidMove(fromX, fromY, toX, toY);
    }

    private boolean isFriendly(Piece targetPiece, Piece selectedPiece){
        return targetPiece != null && targetPiece.getColor() == selectedPiece.getColor();
    }

    private void Highlight(Graphics2D g2d, int squareHeight, int squareWidth){
        g2d.setColor(Color.YELLOW);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawRect(selectedCol * squareWidth, selectedRow * squareHeight, squareWidth, squareHeight);
    }

    private void removeHighlight(int toCol, int toRow) {
        if (selectedRow == -1 || selectedCol == -1) {
            return; 
        }

        int width = getWidth();
        int height = getHeight();
        int squareWidth = width / 8;
        int squareHeight = height / 8;

        Piece targetPiece = board[toRow][toCol];

        if(targetPiece == selectedPiece){
            int x = selectedCol * squareWidth;
            int y = selectedRow * squareHeight;
            
            int buffer = 2;
            repaint(x - buffer, y - buffer, 
                    squareWidth + 2 * buffer, 
                    squareHeight + 2 * buffer);
        }
    }

    public void log(Piece selectedPiece, Piece targetPiece){
        if(selectedPiece.getColor() == Color.BLACK)
            System.out.println(selectedPiece.toString() + " negra se come a " + targetPiece.toString() + " negra");
        if(selectedPiece.getColor() == Color.WHITE)
            System.out.println(selectedPiece.toString() + " blanca se come a " + targetPiece.toString() + " blanca");
    }

    private void checkEnd(){
        if(negras.toString().contains("Rey") == false){
            endScreen("blancas");
        }
        if(blancas.toString().contains("Rey") == false){
            endScreen("negras");
        }
    }

    private void endScreen(String winner) {
        int option = JOptionPane.showConfirmDialog(
            null,
            winner + " gana. \n¿Jugar de nuevo?",
            "Fin del juego", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
    
        if (option == JOptionPane.YES_OPTION) {
            Window parentWindow = SwingUtilities.getWindowAncestor(this);
            parentWindow.dispose(); 
            new ChessGame(); 
        } else {
            System.exit(0); 
        }
    }

    // Unused mouse listener methods
    public void mousePressed(MouseEvent e){}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}
}
