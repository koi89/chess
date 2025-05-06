import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import java.util.Random; 
import java.util.HashMap;

class ChessGame extends Frame {

    public ChessGame() {
        setTitle("Ajedrez");
        ChessBoard board = new ChessBoard();
        add(board);
        setSize(600, 600);
        setVisible(true);

        // Manejar el cierre de la ventana
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        new ChessGame();
    }
}

class ChessBoard extends Canvas implements MouseListener, Serializable{

    final Piece[][] board = new Piece[8][8];
    private Piece selectedPiece = null;
    private int selectedCol = -1;
    private int selectedRow = -1;
    private int move = 0;
    ArrayList<Piece> blancas = new ArrayList<>();
    ArrayList<Piece> negras = new ArrayList<>();
    ArrayList<String> log = new ArrayList<>();
    private Random random = new Random(); 
    private HashMap<Integer, Character> colToLetter = new HashMap<>();

    public ChessBoard() {
        setBackground(Color.LIGHT_GRAY);
        addMouseListener(this);
        initializeBoard();
        initializeColToLetterMap(); 
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

    private void initializeColToLetterMap() {
        colToLetter.put(0, 'a');
        colToLetter.put(1, 'b');
        colToLetter.put(2, 'c');
        colToLetter.put(3, 'd');
        colToLetter.put(4, 'e');
        colToLetter.put(5, 'f');
        colToLetter.put(6, 'g');
        colToLetter.put(7, 'h');
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
                    g2d.setColor(new Color(240, 217, 181)); // Cuadrado blanco
                } else {
                    g2d.setColor(new Color(181, 136, 99)); // Cuadrado negro
                }
                g2d.fillRect(col * squareWidth, row * squareHeight, squareWidth, squareHeight);
            }
        }

        // Dibujar piezas
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
        boolean RandomizePiece;

        removeHighlight(col, row);

        if (selectedPiece == null) {
            // Seleccionar
            if (isValidPosition(row, col) && piece != null) {
                selectedPiece = piece;
                selectedCol = col;
                selectedRow = row;
                repaint();
            }
        } else {
            // Deseleccionar
            if (col == selectedCol && row == selectedRow) {
                selectedPiece = null;
                selectedCol = -1;
                selectedRow = -1;
                repaint();
            } else if (isValidPosition(row, col) && isValidMove(col, row)) {
                Piece targetPiece = board[row][col];

                if ((selectedPiece.getColor() == Color.WHITE && whiteMove()) ||
                    (selectedPiece.getColor() == Color.BLACK && !whiteMove())) {

                    if (targetPiece != null) {
                        if (targetPiece.getColor() == Color.WHITE) {
                            blancas.remove(targetPiece);
                        } else {
                            negras.remove(targetPiece);
                        }
                    }

                    if(targetPiece != null)
                        logCapture(selectedPiece, targetPiece, col, row);
                    else
                        log(selectedPiece, col, row); 

                    //RandomizePiece = RandomizePiece(selectedPiece);
                    //Color movedPieceColor = selectedPiece.getColor();
                    movePiece(col, row);

                    /*
                    if(RandomizePiece == false)
                        randomice(movedPieceColor, col, row);
                    */
                    checkEnd();
                }
            }
        }
    }

    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    private boolean RandomizePiece(Piece selectedPiece){
        return (selectedPiece instanceof King) || (selectedPiece instanceof Pawn);
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

        // Verificar si el movimiento es valido
        if (targetPiece == null) {
            return selectedPiece.isValidMove(fromX, fromY, toX, toY);
        } else {
            return selectedPiece.canEat(fromX, fromY, toX, toY);
        }
    }

    private boolean isFriendly(Piece targetPiece, Piece selectedPiece){
        return targetPiece != null && targetPiece.getColor() == selectedPiece.getColor();
    }

    private void movePiece(int col, int row){
        board[selectedRow][selectedCol] = null;
        selectedPiece.setPosition(col, row);
        board[row][col] = selectedPiece;
        selectedPiece = null;
        selectedCol = -1;
        selectedRow = -1;
        repaint();
        // Incrementar el contador de movimientos
        move++;
    }

    private boolean whiteMove(){
        return move % 2 == 0;
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

        int x = selectedCol * squareWidth;
        int y = selectedRow * squareHeight;

        int buffer = 2; // Añadir un pequeño buffer para asegurar que todo el resaltado se vuelva a dibujar
        repaint(x - buffer, y - buffer,
                squareWidth + 2 * buffer,
                squareHeight + 2 * buffer);
    }

    private void randomice(Color color, int x, int y){
        int randomNum = random.nextInt(4);

        // Reemplazar la pieza en la nueva posición (y, x) con un tipo aleatorio
        switch (randomNum) {
            case 0:
                board[y][x] = new Rook(color, x, y);
                break;
            case 1:
                board[y][x] = new Queen(color, x, y);
                break;
            case 2:
                board[y][x] = new Bishop(color, x, y);
                break;
            case 3:
                board[y][x] = new Knight(color, x, y);
                break;
        }
    }

    private void log(Piece selectedPiece, int toCol, int toRow){
        int fromY = 8 - selectedPiece.getY();
        char fromXLetter = colToLetter.get(selectedPiece.getX());
        int toY = 8 - toRow;
        char toXLetter = colToLetter.get(toCol);

        String logg = selectedPiece.toString() + " en (" + fromXLetter + ", " + fromY + ") se mueve a (" + toXLetter + ", " + toY + ")";

        System.out.println(logg);
        log.add(logg);
    }

    private void logCapture(Piece selectedPiece, Piece targetPiece, int toCol, int toRow){
        int fromY = 8 - selectedPiece.getY();
        char fromXLetter = colToLetter.get(selectedPiece.getX());
        int targetY = 8 - targetPiece.getY();
        char targetXLetter = colToLetter.get(targetPiece.getX());

        String logg = selectedPiece.toString() + " en (" + fromXLetter + ", " + fromY + ") captura a " + targetPiece.toString() + " en (" + targetXLetter + ", " + targetY + ")";

        System.out.println(logg);
        log.add(logg);
    }

    public static void saveLog(ArrayList<String> log) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("partida.txt"))) {
            oos.writeObject(log);
            System.out.println("Log guardado correctamente en partida.txt");
        } catch (IOException e) {
            System.err.println("Error al guardar el log: " + e.getMessage());
        }
    }

    private void checkEnd(){
        // Verificar si alguno de los Reyes ha sido capturado
        boolean blackKingExists = false;
        for(Piece p : negras) {
            if (p instanceof King) {
                blackKingExists = true;
                break;
            }
        }

        boolean whiteKingExists = false;
        for(Piece p : blancas) {
            if (p instanceof King) {
                whiteKingExists = true;
                break;
            }
        }

        if (!blackKingExists) {
            endScreen("blancas");
        } else if (!whiteKingExists) {
            endScreen("negras");
        }
    }

    private void endScreen(String winner) {
        saveLog(log);

        int option = JOptionPane.showConfirmDialog(
            null,
            winner + " ganan. \n¿Jugar de nuevo?",
            "Fin del juego",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        if (option == JOptionPane.YES_OPTION) {
            // Desechar la ventana de juego actual e iniciar una nueva
            Frame currentFrame = (Frame) SwingUtilities.getWindowAncestor(this);
            currentFrame.dispose();
            new ChessGame(); // Reiniciar
        } else {
            System.exit(0); // Salir
        }
    }

    // mouselistener no utilizados
    @Override
    public void mousePressed(MouseEvent e){}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
