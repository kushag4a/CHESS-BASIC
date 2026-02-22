import java.util.*;


public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Chess Game...");
        Board myBoard = new Board();
        Scanner sc= new Scanner(System.in);
        boolean gameRunning = true;



       
        Rook whiteRook = new Rook(true);
        Rook blackRook = new Rook(false);
        Pawn whitePawn = new Pawn(true);
        Pawn blackPawn = new Pawn(false);
        King whiteKing = new King(true);
        King blackKing = new King(false);
        Queen whiteQueen = new Queen(true);
        Queen blackQueen = new Queen(false);
        Bishop whiteBishop = new Bishop(true);
        Bishop blackBishop = new Bishop(false);
        Knight whiteKnight = new Knight(true);
        Knight blackKnight = new Knight(false);





        myBoard.setPiece(0, 0, blackRook);
        myBoard.setPiece(0, 7, blackRook);
        myBoard.setPiece(7, 7, whiteRook);
        myBoard.setPiece(7, 0, whiteRook);


        myBoard.setPiece(0, 4, blackKing);
        myBoard.setPiece(7, 4, whiteKing);
        myBoard.setPiece(0, 3, blackQueen);
        myBoard.setPiece(7, 3, whiteQueen);

        myBoard.setPiece(0, 2, blackBishop);
        myBoard.setPiece(0, 5, blackBishop);
        myBoard.setPiece(7, 2, whiteBishop);
        myBoard.setPiece(7, 5, whiteBishop);


        myBoard.setPiece(0, 1, blackKnight);
        myBoard.setPiece(0, 6, blackKnight);
        myBoard.setPiece(7, 1, whiteKnight);
        myBoard.setPiece(7, 6, whiteKnight);


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

        while (gameRunning) {
            System.out.println("\nEnter your move (Row Col to Row Col). Format: FX FY TX TY");
            System.out.println("Type '9' as the first number to quit.");
            int startX = sc.nextInt();
            if (startX == 9) {
                System.out.println("Exiting game. Thanks for playing!");
                break;}
            int startY = sc.nextInt();
              int endX = sc.nextInt();
              int endY = sc.nextInt();




        
        // System.out.println("--- BEFORE MOVE ---");
        
        System.out.println("\n--- Board ---");
        myBoard.movePiece(startX, startY, endX, endY);
        myBoard.printBoard();
    

    }
  }
}