public class Minimax implements  SearchAlgorithm{
    public  int depth = 5;
//Group member: Meisen Shu & Yiwei Jiang
//NetID: mshu4 & yjiang48

    public Minimax()
    {

    }

    @Override
    public int[] GetOptimalMove(int[][] board,int player, Game G)
    {
        int alpha = -1000000;
        int beta = 1000000;

        var locs = G.GetSuccessors(board,player);
        if (locs.size()==0) // if no moves return -1
        {
            return  new int[]{-1,-1};
        }
        else
        {
            int bestMoveVal = -99999;
            int bestX = locs.get(0)[0], bestY = locs.get(0)[1];
            for (int i = 0; i < locs.size(); i++)
            {

                var tempBoard = G.Move(board,player,locs.get(i)[0],locs.get(i)[1]);
                int val = minimaxValue(tempBoard, player, player*-1, 1,alpha,beta,G);
                if (val > bestMoveVal)
                {
                    bestMoveVal = val;
                    bestX = locs.get(i)[0];
                    bestY = locs.get(i)[1];
                }
            }
            return new int[]{bestX,bestY};
        }
    }

    int minimaxValue(int board[][], int player, int turn, int searchPly,int alpha, int beta, Game G)
    {
        if ((searchPly == depth)) // Change to desired ply lookahead
        {
            return G.Heuristic(board, player);
        }
        var locs = G.GetSuccessors(board,turn);
        if (locs.size()==0) // if no moves skip to next player's turn
        {
            return minimaxValue(board, player, turn*-1, searchPly + 1,alpha,beta,G);
        }
        else
        {
            int bestMoveVal = -99999; // set to negative infinite
            if (player != turn)
                bestMoveVal = 99999; // pos infinite
            for (int i = 0; i < locs.size(); i++)
            {
                var tempBoard = G.Move(board,turn,locs.get(i)[0],locs.get(i)[1]);
                int val = minimaxValue(tempBoard, player, turn*-1 , searchPly + 1,alpha,beta,G);
                if (player == turn)
                {
                    if (val > alpha)
                        alpha = val;
                    //return  alpha;
                }
                else
                {
                    if (val < beta)
                        beta  = val;
                    //return  beta;
                }

                if (alpha >= beta)
                    break;  // beta cut-off
            }
            if (player == turn)
            {
                return  alpha;
            }
            else
            {
                return  beta;
            }
        }
    }

}
