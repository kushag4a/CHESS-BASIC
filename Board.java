public class Board {
    private Spot[][] spots = new Spot[8][8];

    public Board() {
        initializeEmptyBoard();
    }

    private void initializeEmptyBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                spots[row][col] = new Spot(row, col, null);
            }
        }
    }

    public void setPiece(int x, int y, Piece piece) {
        spots[x][y].setPiece(piece);
    }

    public void printBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Spot currentSpot = spots[row][col];
                if (currentSpot.getPiece() == null) {
                    System.out.print("[ ]"); 
                } else {
                    System.out.print("[" + currentSpot.getPiece().getSymbol() + "]");
                }
            }
            System.out.println(); 
        }
    }

    public void movePiece(int startX, int startY, int endX, int endY) {
        Piece pieceToMove = spots[startX][startY].getPiece();
        if (pieceToMove == null) {
            System.out.println("No piece at that position!");
            return;  // stop move if there's no piece to move
        }

        if (pieceToMove.isValidMove(this, startX, startY, endX, endY)) {
            spots[endX][endY].setPiece(pieceToMove);
            spots[startX][startY].setPiece(null);
            System.out.println("Moved successful.");
        } else {
            System.out.println("Error: Invalid move for that piece!");
        }    
}
// Allows pieces to ask the board what is sitting on a specific square
    public Piece getPiece(int x, int y) {
        return spots[x][y].getPiece();
    }
}