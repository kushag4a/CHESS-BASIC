
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
        // Prevent friendly fire
        Piece destinationPiece = board.getPiece(endX, endY);
        if (destinationPiece != null && destinationPiece.isWhite() == this.isWhite()) {
            return false;
        }

        int diffX = Math.abs(startX - endX);
        int diffY = Math.abs(startY - endY);

        // 1. Normal King Move (1 square in any direction)
        if (diffX <= 1 && diffY <= 1) {
            return true;
        }

        // 2. CASTLING (Moving 2 squares horizontally)
        if (diffX == 0 && diffY == 2 && !this.hasMoved()) {
            
            // Rule: Cannot castle if currently in check!
            if (board.isInCheck(this.isWhite())) {
                return false;
            }

            // Kingside Castling (King moves Right)
            if (endY == startY + 2) {
                Piece rook = board.getPiece(startX, 7);
                if (rook != null && rook.getSymbol().equalsIgnoreCase("r") && !rook.hasMoved()) {
                    // Rule: Path must be empty
                    if (board.getPiece(startX, 5) == null && board.getPiece(startX, 6) == null) {
                        // Rule: Path must be safe from attack
                        if (!board.isSquareUnderAttack(startX, 5, this.isWhite()) && 
                            !board.isSquareUnderAttack(startX, 6, this.isWhite())) {
                            return true;
                        }
                    }
                }
            } 
            // Queenside Castling (King moves Left)
            else if (endY == startY - 2) {
                Piece rook = board.getPiece(startX, 0);
                if (rook != null && rook.getSymbol().equalsIgnoreCase("r") && !rook.hasMoved()) {
                    // Rule: Path must be empty
                    if (board.getPiece(startX, 1) == null && board.getPiece(startX, 2) == null && board.getPiece(startX, 3) == null) {
                        // Rule: Path must be safe (King only passes through Y=3 and lands on Y=2)
                        if (!board.isSquareUnderAttack(startX, 3, this.isWhite()) && 
                            !board.isSquareUnderAttack(startX, 2, this.isWhite())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}