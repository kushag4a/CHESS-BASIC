
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

    @Override
    public boolean isValidMove(Board board, int startX, int startY, int endX, int endY) {
        int xDiff = (startX- endX)*(startX - endX);
        int yDiff = (startY - endY)*(startY - endY);
        Piece getPiece=board.getPiece(endX, endY);
        if(getPiece!=null)
        {
            if(getPiece.isWhite()==this.isWhite())
            {
                return false; // Can't move to a square occupied by a piece of the same color
            }
        }

        // King can move one square in any direction
        return (xDiff == 1 && yDiff == 1) || (xDiff == 0 && yDiff == 1) || (xDiff == 1 && yDiff == 0);
    }
}