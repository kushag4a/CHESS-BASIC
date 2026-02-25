public class Queen extends Piece {
    private Rook cloneRook;
    private Bishop cloneBishop;
    public Queen(boolean isWhite) {
        super(isWhite);
        this.cloneRook=new Rook(isWhite);
        this.cloneBishop=new Bishop(isWhite);
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
        if (cloneRook.isValidMove(board, startX, startY, endX, endY) || cloneBishop.isValidMove(board, startX, startY, endX, endY))
       
       // if the move is valid for either a rook or a bishop, then it's valid for the queen
       //oop concept
       //used if we change the code of rook or bishop, the code of queen will automatically change without any modification
       
       
        {
            return true;
        }
    
        else{return false;}
}
}