import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

public class Board {
    // NEW: Tracks every move played so Stockfish can read it!
    private String moveHistory = ""; 

    public String getMoveHistory() {
        return moveHistory;
    }
    private Spot[][] spots = new Spot[8][8];
    private boolean whiteTurn = true; // Track whose turn it is
    private int enPassantRow = -1;// Memory for en passant: which row is vulnerable to en passant capture?
    private int enPassantCol = -1;// Memory for en passant: which column is the pawn that can be captured en passant in?
    private List<Piece> capturedWhite = new ArrayList<>();
    private List<Piece> capturedBlack = new ArrayList<>();

    public List<Piece> getCapturedWhite() { return capturedWhite; }
    public List<Piece> getCapturedBlack() { return capturedBlack; }

    // Add these two mini-methods so pieces can ask about the memory
    public int getEnPassantRow() { return enPassantRow; }
    public int getEnPassantCol() { return enPassantCol; }

    public Board() {
        initializeEmptyBoard();
    }
    // Lets the GUI ask whose turn it is right now!
    public boolean isWhiteTurn() {
        return this.whiteTurn;
    }

    private void initializeEmptyBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                spots[row][col] = new Spot(row, col, null);
            }
        }
    }

    public void setPiece(int x, int y, Piece piece) {
        spots[x][y].setPiece(piece);
    }

    public void printBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Spot currentSpot = spots[row][col];
                if (currentSpot.getPiece() == null) {
                    System.out.print("[ ]"); 
                } else {
                    System.out.print("[" + currentSpot.getPiece().getSymbol() + "]");
                }
            }
            System.out.println(); 
        }
    }

    public void movePiece(int startX, int startY, int endX, int endY) {
        Piece pieceToMove = spots[startX][startY].getPiece();
        if (pieceToMove == null) {
            System.out.println("No piece at that position!");
            return;  // stop move if there's no piece to move
        }
        if (pieceToMove.isWhite()!=whiteTurn) {     //alternation of turns
            String player = whiteTurn ? "White" : "Black";
            System.out.println("It's " + player + "'s turn! Please move your own piece.");
            return; // stop move if it's not the player's turn
            
        }

        if (pieceToMove.isValidMove(this, startX, startY, endX, endY)) {
            // THE GHOST MOVE (Simulate it) ---
            Piece capturedPiece = spots[endX][endY].getPiece(); // Remember what was on the destination!
            spots[endX][endY].setPiece(pieceToMove);            // Move our piece
            spots[startX][startY].setPiece(null);               // Empty the start spot
            
            if (pieceToMove.getSymbol().equalsIgnoreCase("k") && Math.abs(startY - endY) == 2) {
                if (endY > startY) { 
                    // Kingside
                    Piece rook = spots[startX][7].getPiece();
                    spots[startX][5].setPiece(rook);
                    spots[startX][7].setPiece(null);
                    rook.setHasMoved(true); // Mark rook as moved
                } else { 
                    // Queenside
                    Piece rook = spots[startX][0].getPiece();
                    spots[startX][3].setPiece(rook);
                    spots[startX][0].setPiece(null);
                    rook.setHasMoved(true); // Mark rook as moved
                }
            }

            pieceToMove.setHasMoved(true); // Mark the piece as having moved (important for pawns and castling)



            if(isInCheck(pieceToMove.isWhite())){
                // UNDO THE GHOST MOVE (Revert it) ---
                spots[startX][startY].setPiece(pieceToMove);    // Move the piece back
                spots[endX][endY].setPiece(capturedPiece);     // Restore whatever was on the destination

                System.out.println("Move would put your own king in check! Try again.");
                return; // stop move if it puts own king in check
            }

            
            
            spots[endX][endY].setPiece(pieceToMove);
            spots[startX][startY].setPiece(null);
            if (capturedPiece != null) {
                if (capturedPiece.isWhite()) {
                    capturedWhite.add(capturedPiece);
                } else {
                    capturedBlack.add(capturedPiece); // FIXED THIS LINE!
                }
            }


           // If a Pawn moved diagonally, but landed on an empty square, it MUST be En Passant!
            if (pieceToMove.getSymbol().equalsIgnoreCase("p") && startY != endY && capturedPiece == null) {
                Piece deadPawn = spots[startX][endY].getPiece();
                if (deadPawn != null) {
                    if (deadPawn.isWhite()) capturedWhite.add(deadPawn);
                    else capturedBlack.add(deadPawn);
                }
                spots[startX][endY].setPiece(null); 
                System.out.println("En Passant Capture!");
            }

            // --- 3. INTERACTIVE PAWN PROMOTION ---
            if (pieceToMove.getSymbol().equalsIgnoreCase("p")) { 
                if ((pieceToMove.isWhite() && endX == 0) || (!pieceToMove.isWhite() && endX == 7)) {
                    
                    // Show a popup asking what to promote to!
                    String[] options = {"Queen", "Rook", "Bishop", "Knight"};
                    int choice = JOptionPane.showOptionDialog(null, 
                            "Pawn Promotion! Choose a piece:", 
                            "Promotion", 
                            JOptionPane.DEFAULT_OPTION, 
                            JOptionPane.QUESTION_MESSAGE, 
                            null, options, options[0]);
                    
                    // Replace the pawn with the chosen piece
                    boolean isWhite = pieceToMove.isWhite();
                    if (choice == 1) spots[endX][endY].setPiece(new Rook(isWhite));
                    else if (choice == 2) spots[endX][endY].setPiece(new Bishop(isWhite));
                    else if (choice == 3) spots[endX][endY].setPiece(new Knight(isWhite));
                    else spots[endX][endY].setPiece(new Queen(isWhite)); // Default to Queen
                }
            }
            // If a pawn just double-jumped...
            if (pieceToMove.getSymbol().equalsIgnoreCase("p") && Math.abs(startX - endX) == 2) {
                enPassantRow = (startX + endX) / 2; // Save the ghost square row!
                enPassantCol = startY;              // Save the ghost square column!
            } else {
                // If they did literally ANY other move, erase the memory.
                enPassantRow = -1;
                enPassantCol = -1;
            }
            char startFile = (char) ('a' + startY);
            char startRank = (char) ('8' - startX);
            char endFile = (char) ('a' + endY);
            char endRank = (char) ('8' - endX);
            
            String uciMove = "" + startFile + startRank + endFile + endRank;
            
            // If it was a promotion, Stockfish needs a letter at the end (e.g., "e7e8q")
            if (pieceToMove.getSymbol().equalsIgnoreCase("p") && (endX == 0 || endX == 7)) {
                uciMove += "q"; // Assuming Queen promotion for simplicity right now
            }

            moveHistory += uciMove + " "; // Add it to the total list of moves!

            
            whiteTurn = !whiteTurn; // Switch turns after a successful move
            System.out.println("Move successful.");

            boolean opponentInCheck = isInCheck(whiteTurn); // Check if the opponent's king is in check after this move
            boolean nextPlayerHasMoves = hasAnyLegalMoves(whiteTurn); // Check if the opponent has any legal moves available
            if (opponentInCheck) {
                
                // Check for checkmate
                if (!hasAnyLegalMoves(whiteTurn)) {
                    String winner = !whiteTurn ? "White" : "Black";
                    System.out.println("Checkmate! " + winner + " wins!");
                    JOptionPane.showMessageDialog(null, "Checkmate! " + winner + " wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0); // End the game
                }
                else {
                    System.out.println("Check on "+(whiteTurn ? "White" : "Black")+" king!");
                }
            }
            else{// If they are NOT in check, but have no moves... it's a Stalemate (Draw)!
                if (!nextPlayerHasMoves) {
                    System.out.println("\n*** STALEMATE! It's a draw! ***");
                    JOptionPane.showMessageDialog(null, "Stalemate! It's a draw!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0); 
                }
            } 
        }
        else {
            System.out.println("Error: Invalid move for that piece!");
        }    
    }
// Allows pieces to ask the board what is sitting on a specific square
    public Piece getPiece(int x, int y) {
        return spots[x][y].getPiece();
    }
    private Spot findKing(boolean isWhite) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece piece = spots[row][col].getPiece();
                if (piece != null && piece.getSymbol().equalsIgnoreCase("k") && piece.isWhite() == isWhite) {
                    return spots[row][col];
                }
            }
        }
        return null; // King not found (impossible in a valid game, but we should handle it)
}
    // 2. Scans the board to see if ANY enemy piece can legally attack a specific square
    public boolean isSquareUnderAttack(int targetX, int targetY, boolean isWhiteDefender) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Piece attacker = spots[row][col].getPiece();
                // If the piece belongs to the enemy...
                if (attacker != null && attacker.isWhite() != isWhiteDefender) {
                    // Ask the enemy if it can legally move to our target square
                    if (attacker.isValidMove(this, row, col, targetX, targetY)) {
                        return true; // DANGER! Square is attacked.
                    }
                }
            }
        }
        return false; // Square is safe!
    }
    // 3. Uses the above two methods to determine if the king is in check
    public boolean isInCheck(boolean isWhiteKing) {
        Spot kingSpot = findKing(isWhiteKing);
        if (kingSpot == null) {
            return false; // This should never happen in a valid game
        }
        return isSquareUnderAttack(kingSpot.getX(), kingSpot.getY(), isWhiteKing);
        // If the king's square is under attack by any enemy piece, then the king is in check
    }
    public boolean hasAnyLegalMoves(boolean isWhite){
        for (int startX=0;startX<8;startX++)
        {
            for (int startY=0;startY<8;startY++)
            {
                Piece piece=spots[startX][startY].getPiece();
                // If it's your piece...
                if(piece!=null&&piece.isWhite()==isWhite)
                {
                    for (int endX=0;endX<8;endX++)
                    {
                        for (int endY=0;endY<8;endY++)
                        {
                            // Try moving it to literally every other square on the board!
                            if (piece.isValidMove(this, startX, startY, endX, endY)) {
                                
                                // Ghost move to see if it gets you out of check
                                Piece capturedPiece = spots[endX][endY].getPiece(); // Remember what was on the destination!
                                spots[endX][endY].setPiece(piece);            // Move our piece
                                spots[startX][startY].setPiece(null);               // Empty the start spot

                                //is king still in check after this move?
                                boolean stillInCheck = !isInCheck(isWhite);
                                // If the move gets you out of check, return true (there is a legal move)
                                
                                    // Restore the board state
                                    spots[startX][startY].setPiece(piece);
                                    spots[endX][endY].setPiece(capturedPiece);
                                if(stillInCheck)
                                {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }return false; // No legal moves found
    }
}

