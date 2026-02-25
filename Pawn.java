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
        
        // --- WHITE PAWN LOGIC ---
        if (this.isWhite()) {
            // Normal 1-square move forward
            if (startX - endX == 1 && startY == endY && destinationPiece == null) {
                return true;
            }
            // 2-square move forward (ONLY allowed if starting at row 6)
            if (startX == 6 && startX - endX == 2 && startY == endY && destinationPiece == null && board.getPiece(endX+1,endY) == null) {
                return true;
            }
            // Diagonal Captures (Normal and En Passant)
            if (Math.abs(startY - endY) == 1 && (startX - endX == 1)) {
                // 1. Normal Capture
                if (destinationPiece != null && destinationPiece.isWhite() == false) {
                    return true;
                }
                // 2. EN PASSANT CAPTURE
                if (endX == board.getEnPassantRow() && endY == board.getEnPassantCol()) {
                    return true;
                }
            }
            return false;
        } 
        
        // --- BLACK PAWN LOGIC ---
        else {
            // Normal 1-square move forward
            if (startX - endX == -1 && startY == endY && destinationPiece == null) {
                return true;
            }
            // 2-square move forward (ONLY allowed if starting at row 1)
            if (startX == 1 && startX - endX == -2 && startY == endY && destinationPiece == null && board.getPiece(endX-1,endY) == null) {
                return true;
            }
            // Diagonal Captures (Normal and En Passant)
            if (Math.abs(startY - endY) == 1 && (startX - endX == -1)) {
                // 1. Normal Capture
                if (destinationPiece != null && destinationPiece.isWhite() == true) {
                    return true;
                }
                // 2. EN PASSANT CAPTURE
                if (endX == board.getEnPassantRow() && endY == board.getEnPassantCol()) {
                    return true;
                }
            }
            return false;
        }
    }
}