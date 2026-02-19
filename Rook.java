public class Rook extends Piece {

    public Rook(boolean isWhite) {
        super(isWhite); 
    }

    @Override
    public String getSymbol() {
        if (this.isWhite()) {
            return "R"; // White Rook
        } else {
            return "r"; // Black Rook
        }
    }
}