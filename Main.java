public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Chess Game...");
        Board myBoard = new Board();
        
        Rook whiteRook = new Rook(true);
        Rook blackRook = new Rook(false);
        
        myBoard.setPiece(0, 0, blackRook);
        myBoard.setPiece(0, 7, blackRook);
        myBoard.setPiece(7, 7, whiteRook);
        myBoard.setPiece(7, 0, whiteRook);



        Pawn whitePawn = new Pawn(true);
        Pawn blackPawn = new Pawn(false);

        myBoard.setPiece(1, 0, blackPawn);
        myBoard.setPiece(1, 1, blackPawn);
        myBoard.setPiece(1, 2, blackPawn);
        myBoard.setPiece(1, 3, blackPawn);
        myBoard.setPiece(1, 4, blackPawn);
        myBoard.setPiece(1, 5, blackPawn);
        myBoard.setPiece(1, 6, blackPawn);
        myBoard.setPiece(1, 7, blackPawn);
        myBoard.setPiece(6, 0, whitePawn);
        myBoard.setPiece(6, 1, whitePawn);
        myBoard.setPiece(6, 2, whitePawn);
        myBoard.setPiece(6, 3, whitePawn);
        myBoard.setPiece(6, 4, whitePawn);
        myBoard.setPiece(6, 5, whitePawn);
        myBoard.setPiece(6, 6, whitePawn);
        myBoard.setPiece(6, 7, whitePawn);
        
        myBoard.printBoard();
    }
}