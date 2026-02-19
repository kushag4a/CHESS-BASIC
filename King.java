public class King extends Piece {

    public King(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public String getSymbol() {
        if (this.isWhite()) {
            return "K";
        } else {
            return "k";
        }
    }
}