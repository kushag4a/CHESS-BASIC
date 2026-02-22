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
    @Override
    public boolean isValidMove(Board board, int startX, int startY, int endX, int endY)
    {
        Piece getPiece=board.getPiece(endX, endY);
        if(getPiece!=null&&getPiece.isWhite()==this.isWhite())
        {
            return false;
        }
        if(startX==endX||startY==endY)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}