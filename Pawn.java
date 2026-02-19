public class Pawn extends Piece {

    public Pawn(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public String getSymbol() {
        if (this.isWhite()) {
            return "P";
        } else {
            return "p";
        }
    }
}