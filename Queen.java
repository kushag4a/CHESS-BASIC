public class Queen extends Piece {

    public Queen(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public String getSymbol() {
        if (this.isWhite()) {
            return "Q";
        } else {
            return "q";
        }
    }
}