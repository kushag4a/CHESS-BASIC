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
    @Override
    public boolean isValidMove(Board board, int startX, int startY, int endX, int endY) {
    int difY=Math.abs(startX-endX);
    int difX=Math.abs(startY-endY);
    Piece getPiece=board.getPiece(endX, endY);
    if(getPiece!=null&&getPiece.isWhite()==this.isWhite())
    {
        return false;
    }

    if(difX==2&&difY==1||difX==1&&difY==2)
    {
        return true;
    }
    else
    {
        return false;
    }
}
}