import java.util.Random;
//Group member: Meisen Shu & Yiwei Jiang
//NetID: mshu4 & yjiang48
public class RandomSearch implements  SearchAlgorithm{
    Random generator = null;
    public  RandomSearch()
    {
        generator = new Random();
    }
    @Override
    public int[] GetOptimalMove(int[][] board, int player, Game G) {
        var locs = G.GetSuccessors(board,player);

        int randomIndex = generator.nextInt(locs.size());
        var move = locs.get(randomIndex);
        return move;

    }
}
