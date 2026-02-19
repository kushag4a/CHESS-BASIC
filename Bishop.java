public class Bishop extends Piece {

    public Bishop(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public String getSymbol() {
        if (this.isWhite()) {
            return "B";
        } else {
            return "b";
        }
    }
}