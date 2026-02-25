public class Bishop extends Piece {

    public Bishop(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public String getSymbol() {
        if (this.isWhite()) {
            return "B";
        } else {
            return "b";
        }
    }
    @Override
    public boolean isValidMove(Board board, int startX, int startY, int endX, int endY) {
        int diffx=(startX-endX)*(startX-endX);
        int diffy=(startY-endY)*(startY-endY);
        Piece getPiece=board.getPiece(endX, endY);
        if(getPiece!=null&&getPiece.isWhite()==this.isWhite())
        {
            return false;
        }

        if(startX<endX&&startY<endY&&diffx==diffy)
        {
            int j=startY+1;
            for (int i=startX+1;i<endX;i++)
            {
                if (board.getPiece(i,j)!=null)
                {
                    return false;
                }
                j++;
            }
            return true;
        }
        if(startX>endX&&startY<endY&&diffx==diffy)
        {
            int j=startY+1;
            for (int i=startX-1;i>endX;i--)
            {
                if (board.getPiece(i,j)!=null)
                {
                    return false;
                }
                j++;
            }
            return true;
        }
        if(startX>endX&&startY>endY&&diffx==diffy)
        {
            int j=startY-1;
            for (int i=startX-1;i>endX;i--)
            {
                if (board.getPiece(i,j)!=null)
                {
                    return false;
                }
                j--;
            }
            return true;
        }
        if(startX<endX&&startY>endY&&diffx==diffy)
        {
            int j=startY-1;
            for (int i=startX+1;i<endX;i++)
            {
                if (board.getPiece(i,j)!=null)
                {
                    return false;
                }
                j--;
            }
            return true;
        }
        else
        {
            return false;
        }
}
}