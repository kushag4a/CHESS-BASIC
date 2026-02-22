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
    @Override
    public boolean isValidMove(Board board, int startX, int startY, int endX, int endY) {
        int diffx=(startX-endX)*(startX-endX);
        int diffy=(startY-endY)*(startY-endY);
        Piece getPiece=board.getPiece(endX, endY);
        if(getPiece!=null&&getPiece.isWhite()==this.isWhite())
        {
            return false;
        }

        if((diffx==diffy)||(startX==endX||startY==endY))
        {
            return true;
        }
        else
        {
            return false;
        }
}
}