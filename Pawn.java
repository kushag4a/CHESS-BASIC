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

    @Override
    public boolean isValidMove(Board board, int startX, int startY, int endX, int endY) {

        Piece destinationPiece = board.getPiece(endX, endY);
        
        // White Pawn Logic
        if (this.isWhite()) {
            // Normal 1-square move forward
            if (startX - endX == 1 && startY == endY && destinationPiece == null) {
                return true;
            }
            // 2-square move forward (ONLY allowed if starting at row 6)
            if (startX == 6 && startX - endX == 2 && startY == endY && destinationPiece == null) {
                return true;
            }
            if (Math.abs(startY-endY)==1&&(startX-endX==1)&&destinationPiece!=null&&destinationPiece.isWhite()==false)
            {
                return true;
            }
            return false;
        } 
        
        // Black Pawn Logic
        else {
            // Normal 1-square move forward
            if (startX - endX == -1 && startY == endY && destinationPiece == null) {
                return true;
            }
            // 2-square move forward (ONLY allowed if starting at row 1)
            if (startX == 1 && startX - endX == -2 && startY == endY && destinationPiece == null) {
                return true;
            }

            
            if(Math.abs(startY-endY)==1&&(startX-endX==-1)&&destinationPiece!=null&&destinationPiece.isWhite()==true)
            {
                return true;
            }
            return false;
        }
    }
}