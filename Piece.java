public abstract class Piece {
    private boolean isWhite;

    public Piece(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public boolean isWhite() {
        return this.isWhite;
    }
    
    public abstract String getSymbol();


    public abstract boolean isValidMove(Board board, int startX, int startY, int endX, int endY);
        // This forces every piece to calculate if its move is legal.
    }
