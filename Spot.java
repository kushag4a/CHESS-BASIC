public class Spot {
    private int x;
    private int y;
    private Piece piece;

    public Spot(int x, int y, Piece piece) {
        this.x = x;
        this.y = y;
        this.piece = piece;
    }

    public Piece getPiece() { return this.piece; }
    public void setPiece(Piece p) { this.piece = p; }
    public int getX() { return this.x; }
    public int getY() { return this.y; }
}