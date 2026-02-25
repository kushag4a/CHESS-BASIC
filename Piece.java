public abstract class Piece {
    private boolean isWhite;
    private boolean hasMoved = false; //Memory for castling!

    public Piece(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public boolean isWhite() { return this.isWhite; }
    
    // Getters and Setters for memory
    public boolean hasMoved() { return this.hasMoved; }
    public void setHasMoved(boolean moved) { this.hasMoved = moved; }
    
    public abstract String getSymbol();
    public abstract boolean isValidMove(Board board, int startX, int startY, int endX, int endY);
}