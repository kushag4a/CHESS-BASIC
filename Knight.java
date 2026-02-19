public class Knight extends Piece {

    public Knight(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public String getSymbol() {
        if (this.isWhite()) {
            return "G";
        } else {
            return "g";
        }
    }
}