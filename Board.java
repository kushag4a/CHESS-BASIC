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
}