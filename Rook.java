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
        if(startX==endX)
        {
            if (startY<endY)
            {
                for(int i=startY+1;i<endY;i++)
                {
                    if (board.getPiece(startX, i)!=null)
                    {
                        return false;
                    }
                    
                }
                return true;
            }
            else
            {
                for(int i=endY+1;i<startY;i++)
                {
                    if (board.getPiece(startX, i)!=null)
                    {
                        return false;
                    }
                    
                }
                return true;
            }
            
        }
        else if(startY==endY)
        {
            if (startX<endX)
            {
                for(int i=startX+1;i<endX;i++)
                {
                    if (board.getPiece(i, startY)!=null)
                    {
                        return false;
                    }
                }
                return true;
            }
            else
            {
                for(int i=endX+1;i<startX;i++)
                {
                    if (board.getPiece(i, startY)!=null)
                    {
                        return false;
                    }
                }
                return true;
            }
        }
        else
        {
            return false;
        }
    }
}