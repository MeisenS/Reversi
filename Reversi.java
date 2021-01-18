import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
//Group member: Meisen Shu & Yiwei Jiang
//NetID: mshu4 & yjiang48

public class Reversi {   
    public static void main(String[] args) {  

        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase().toCharArray();
        Scanner in = new Scanner(System.in);
        System.out.println("Reversi by CSC242: Intro to AI");
        System.out.println("Choose your game:");
        System.out.println("1. Small 4x4 Reversi");
        System.out.println("2. Medium 6x6 Reversi");
        System.out.println("3. Standard 8x8 Reversi");
        System.out.println("Your choice:");

        int game_type = in.nextInt();
        System.out.println("Choose your opponent:");
        System.out.println("1. An agent that plays randomly");
        System.out.println("2. An agent that uses H-MINIMAX with fixed depth cutoff and a-b pruning");
        System.out.println("Your choice:");

        int opponent_type = in.nextInt();
        System.out.println("Do you want to play DARK (x) or LIGHT (o)?");
        String sym = in.next();
        String pcSym = "x";
        int player = 1;
        if (sym.equals("x"))
        {
            player = 1;
            pcSym="o";
        }
        else
        {
            player = -1;
            pcSym="x";
        }

        int dim = 4;
        if(game_type==2)
        {
            dim = 6;
        }else  if (game_type==3)
        {
            dim = 8;
        }

        var G = new Game(dim,dim);
        //var locs = G.GetSuccessors(G.Cboard,1);
        //System.out.println(G.DrawBoard(G.Cboard,locs));
        int turn =1;

        System.out.println(G.DrawBoard(G.Cboard,null));
        if(player==1)
            System.out.println("Next to play: DARK");
        else
            System.out.println("Next to play: LIGHT");
        boolean isComplete = false;
        SearchAlgorithm algorithm = null;
        SearchAlgorithm minimax = new Minimax();
        SearchAlgorithm random = new RandomSearch();
        algorithm = random;
        if(opponent_type == 2)
        {
            algorithm = minimax;
        }

        while (isComplete==false){


            if(turn==player)
            {
                var locs = G.GetSuccessors(G.Cboard,player);

                if(locs.size()>0) {

                    List<String> valid_moves = new ArrayList<String>();
                    for (var e : locs) {

                        valid_moves.add(Character.toString(alphabet[e[1]]) + Integer.toString(e[0] + 1));
                    }
                    System.out.println(valid_moves);
                    long finish = System.currentTimeMillis();
                    boolean isvalid = false;
                    String move = "";
                    long start = System.currentTimeMillis();
                    while (isvalid == false) {

                        System.out.println("Your move (? for help):");
                        move = in.next();
                        if (valid_moves.contains(move)) {
                            isvalid = true;
                        } else {
                            System.out.println("Invalid move!");
                            System.out.println("Valid moves are: " + valid_moves.toString());
                            System.out.println("Try again!");
                        }


                    }
                    long timeElapsed = finish - start;
                    System.out.println(String.format("Elapsed time: %.3f secs", timeElapsed / 1000.0));
                    System.out.println(String.format("%s:%s", sym, move));
                    int x = Integer.parseInt(Character.toString(move.charAt(1))) - 1;
                    int y = 0;

                    for (int i = 0; i < alphabet.length; i++) {
                        if (alphabet[i] == move.charAt(0)) {
                            y = i;
                            break;
                        }
                    }

                    G.Cboard = G.Move(G.Cboard, player, x, y);
                    System.out.println(G.DrawBoard(G.Cboard, null));
                    if(player==1)
                        System.out.println("Next to play: DARK");
                    else
                        System.out.println("Next to play: LIGHT");
                    System.out.println("");
                    turn *= -1;
                }
                else
                {
                    System.out.println("Winner: " + G.GetWinner(G.Cboard));
                    isComplete = true;
                }
            }
            else
            {

                var locs = G.GetSuccessors(G.Cboard,player*-1);
                if(locs.size()>0) {
                    List<String> valid_moves = new ArrayList<String>();
                    for (var e : locs) {

                        valid_moves.add(Character.toString(alphabet[e[1]]) + Integer.toString(e[0] + 1));
                    }
                    long start = System.currentTimeMillis();
                    if(opponent_type == 2)
                        System.out.println("I’m picking an optimal move...");
                    else
                        System.out.println("I’m picking a move randomly...");

                    int[] bestMove = algorithm.GetOptimalMove(G.Cboard,player*-1,G);
                    String move = Character.toString(alphabet[bestMove[1]]) + Integer.toString(bestMove[0] + 1);
                    long finish = System.currentTimeMillis();
                    long timeElapsed = finish - start;
                    System.out.println(String.format("Elapsed time: %.3f secs", timeElapsed / 1000.0));
                    System.out.println(String.format("%s:%s", pcSym, move));
                    int x = bestMove[0];
                    int y = bestMove[1];

                    G.Cboard = G.Move(G.Cboard, player * -1, x, y);
                    System.out.println(G.DrawBoard(G.Cboard, null));
                    if(player*-1==1)
                        System.out.println("Next to play: DARK");
                    else
                        System.out.println("Next to play: LIGHT");
                    System.out.println("");
                    turn *= -1;
                }
                else
                {
                    System.out.println("Winner: " + G.GetWinner(G.Cboard));
                    isComplete = true;
                }
            }

            //isComplete=true;
        }


    }
}