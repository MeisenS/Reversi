import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
//Group member: Meisen Shu & Yiwei Jiang
//NetID: mshu4 & yjiang48


public class Game {

    public int row;
    public int col;
    public  int[][] Cboard = null;
    public Game(int r, int col)
    {
        this.row = r;
        this.col = col;
        Cboard = GetInitialPosition();



    }

    public int[][] GetInitialPosition()      //get the initial position. 

    {
        int[][] board = new int[this.row][this.col];

        board[this.row/2-1][this.col/2-1] = -1;  //white,left up corner

        board[this.row/2-1][this.col/2] = 1; //black,right up corner


        board[this.row/2][this.col/2-1] = 1; //black,left down corner

        board[this.row/2][this.col/2] = -1;  //white,right up corner

        return  board;
    }

    public List<int[]> GetSuccessors(int[][] board, int player)
    {
        List<int[]> successors = new ArrayList<int[]>();

        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {

                if(board[i][j] == 0)
                {
                    boolean isvalid = isValidH(board,player,i,j);
                    if(isvalid == false)
                    {
                        isvalid = isValidV(board,player,i,j);
                    }
                    if(isvalid == false)
                    {
                        isvalid = isValidD(board,player,i,j);
                    }

                    if(isvalid)
                    {
                        int[] pair = new int[2];
                        pair[0] = i;
                        pair[1] = j;
                        successors.add(pair);
                    }

                }
            }
        }

        return  successors;
    }

    public int[][] CopyBoard(int[][] board)
    {
        int[][] board_copy = new int[this.row][this.col];
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                board_copy[i][j] = board[i][j];
            }
        }
        return  board_copy;
    }

    public int[][] Move(int[][] board, int player,int x, int y)
    {
        var board_copy = CopyBoard(board);

        MoveH(board_copy,board,player,x,y);     //moveH is move horizontally

        MoveV(board_copy,board,player,x,y);        //moveV is move vertically

        MoveD(board_copy,board,player,x,y);       // moveD is move diagonal

        board_copy[x][y] = player   ;
        return  board_copy;

    }
    
//  The dark player must play pieces with the dark side up trapping a line of light pieces between the new piece and an existing dark piece. The light player must do the same with a light piece trapping a line of dark pieces.

    public void MoveH(int[][] board,int[][] refboard, int player,int x, int y)    //move horizontally

    {
        var cont = true;
        var count =0;
        List<Integer> updatedCells = new ArrayList<Integer>();   //set an int arraylist to store the updatedcells.

        for (int i = y-1; i >=0; i--) {     //move right

            if(refboard[x][i] == player*-1)     // opponents move

            {
                count++;
                updatedCells.add(i);      //put it in the stored array

            }
            else if(refboard[x][i] == player)
            {
                cont = false;
            }
            else
            {
                break;
            }

            if (cont==false)       //flip the pieces in the stored array(updatedcells)

            {
                if (count>0)
                {
                    for (var e:
                         updatedCells) {
                        board[x][e] = player;
                    }
                }
                break;
            }
        }

        cont = true;
        count =0;
        updatedCells = new ArrayList<Integer>();
        for (int i = y+1; i <this.col; i++) {      //move left

            if(refboard[x][i] == player*-1)       // opponents move

            {
                count++;
                updatedCells.add(i);
            }
            else if(refboard[x][i] == player)
            {
                cont = false;
            }
            else
            {
                break;
            }
            if (cont==false)          //flips the pieces up
            {
                if (count>0)
                {
                    for (var e:
                            updatedCells) {
                        board[x][e] = player;
                    }
                }
                break;
            }
        }

    }

    public void MoveV(int[][] board,int[][] refboard, int player,int x, int y)    //move vertical 

    {
        var cont = true;
        var count =0;
        List<Integer> updatedCells = new ArrayList<Integer>();
        for (int i = x-1; i >=0; i--) {       //move down
            if(refboard[i][y] == player*-1)      //opponent move 

            {
                count++;
                updatedCells.add(i);
            }
            else if(refboard[i][y] == player)
            {
                cont = false;
            }
            else
            {
                break;
            }

            if (cont==false)      //flip the pieces in the stored array(updatedcells)

            {
                if (count>0)
                {
                    for (var e:
                            updatedCells) {
                        board[e][y] = player;
                    }
                }
                break;
            }
        }

        cont = true;
        count =0;
        updatedCells = new ArrayList<Integer>();
        for (int i = x+1; i <this.row; i++) {     //move up

            if(refboard[i][y] == player*-1)      //opponent move 

            {
                count++;
                updatedCells.add(i);
            }
            else if(refboard[i][y] == player)
            {
                cont = false;
            }
            else
            {
                break;
            }
            if (cont==false)         //flip the pieces in the stored array(updatedcells)

            {
                if (count>0)
                {
                    for (var e:
                            updatedCells) {
                        board[e][y] = player;
                    }
                }
                break;
            }
        }

    }

    public void MoveD(int[][] board,int[][] refboard, int player,int x, int y)    //move diagonal

    {

        var cont = true;
        var count =0;
        var max = this.col;
        if(max<this.row)            //if current col<current row, set them equal

        {
            max= this.row;
        }

        List<Integer> updatedCells = new ArrayList<Integer>();
        for (int i = 1; i <= max; i++) {
            if (x-i<0 || y-i<0) {
                break;
            }
            if(refboard[x-i][y-i] == player*-1)       //if opponent move diagonally(left up)

            {
                count++;
                updatedCells.add(i);
            }
            else if(refboard[x-i][y-i] == player)
            {
                cont = false;
            }
            else
            {
                break;
            }
            if (cont==false)          //flip the pieces in the stored array(updatedcells)

            {
                if (count>0)
                {
                    for (var e:
                            updatedCells) {
                        board[x-e][y-e] = player;
                    }
                }

            }
        }

        cont = true;
        count =0;
        updatedCells = new ArrayList<Integer>();
        for (int i = 1; i <= max; i++) {
            if (x+i>=this.row || y+i>=this.col) {
                break;
            }
            if(refboard[x+i][y+i]== player*-1)       //if opponent move diagonally(right up)

            {
                count++;
                updatedCells.add(i);
            }
            else if(refboard[x+i][y+i] == player)
            {
                cont = false;
            }
            else
            {
                break;
            }
            if (cont==false) 
            {
                if (count>0)
                {
                    for (var e:
                            updatedCells) {
                        board[x+e][y+e] = player;
                    }
                }
                break;
            }
        }

        cont = true;
        count =0;
        updatedCells = new ArrayList<Integer>();
        for (int i = 1; i <= max; i++) {
            if (x-i<0 || y+i>=this.col) {
                break;
            }
            if(refboard[x-i][y+i]== player*-1)     //if opponent move diagonally(right up)

            {
                count++;
                updatedCells.add(i);
            }
            else if(refboard[x-i][y+i] == player)
            {
                cont = false;
            }
            else
            {
                break;
            }
            if (cont==false)
            {
                if (count>0)
                {
                    for (var e:
                            updatedCells) {
                        board[x-e][y+e] = player;
                    }
                }
                break;
            }
        }

        cont = true;
        count =0;
        updatedCells = new ArrayList<Integer>();
        for (int i = 1; i <= max; i++) {
            if (x+i>=this.row || y-i<0) {
                break;
            }
            if(refboard[x+i][y-i]== player*-1)           //if opponent move diagonally(right down)

            {
                count++;
                updatedCells.add(i);
            }
            else if(refboard[x+i][y-i] == player)
            {
                cont = false;
            }
            else
            {
                break;
            }
            if (cont==false)
            {
                if (count>0)
                {
                    for (var e:
                            updatedCells) {
                        board[x+e][y-e] = player;
                    }
                }
                break;
            }
        }

    }

    public boolean isValidH(int[][] board, int player,int x, int y)   //check whether move valid(horizontal)
    {
        var isvalid = false;

        var cont = true;
        var count =0;
        for (int i = y-1; i >=0; i--) {
            if(board[x][i] == player*-1)
            {
                count++;
            }
            else if(board[x][i] == player)
            {
                cont = false;
            }
            else
            {
                break;
            }
            if (cont==false)
            {
                if (count>0)
                {
                    isvalid = true;
                }
                break;
            }
        }

        if(isvalid) {
            return isvalid;

        }
        cont = true;
        count =0;
        for (int i = y+1; i <this.col; i++) {
            if(board[x][i] == player*-1)
            {
                count++;
            }
            else if(board[x][i] == player)
            {
                cont = false;
            }
            else
            {
                break;
            }
            if (cont==false)
            {
                if (count>0)
                {
                    isvalid = true;
                }
                break;
            }
        }

        return  isvalid;
    }

    public boolean isValidV(int[][] board, int player,int x, int y)
    {
        var isvalid = false;

        var cont = true;
        var count =0;
        for (int i = x-1; i >=0; i--) {
            if(board[i][y] == player*-1)
            {
                count++;
            }
            else if(board[i][y] == player)
            {
                cont = false;
            }
            else
            {
                break;
            }
            if (cont==false)
            {
                if (count>0)
                {
                    isvalid = true;
                }
                break;
            }
        }

        if(isvalid) {
            return isvalid;

        }
        cont = true;
        count =0;
        for (int i = x+1; i <this.row; i++) {
            if(board[i][y]== player*-1)
            {
                count++;
            }
            else if(board[i][y] == player)
            {
                cont = false;
            }
            else
            {
                break;
            }
            if (cont==false)
            {
                if (count>0)
                {
                    isvalid = true;
                }
                break;
            }
        }

        return  isvalid;
    }

    public boolean isValidD(int[][] board, int player,int x, int y)
    {
        var isvalid = false;

        var cont = true;
        var count =0;
        var max = this.col;
        if(max<this.row)
        {
            max= this.row;
        }
        for (int i = 1; i <= max; i++) {
            if (x-i<0 || y-i<0) {
                break;
            }
            if(board[x-i][y-i] == player*-1)
            {
                count++;
            }
            else if(board[x-i][y-i] == player)
            {
                cont = false;
            }
            else
            {
                break;
            }
            if (cont==false)
            {
                if (count>0)
                {
                    isvalid = true;
                }
                break;
            }
        }

        if(isvalid) {
            return isvalid;

        }
        cont = true;
        count =0;
        for (int i = 1; i <= max; i++) {
            if (x+i>this.row-1 || y-i<0) {
                break;
            }
            if(board[x+i][y-i]== player*-1)
            {
                count++;
            }
            else if(board[x+i][y-i] == player)
            {
                cont = false;
            }
            else
            {
                break;
            }
            if (cont==false)
            {
                if (count>0)
                {
                    isvalid = true;
                }
                break;
            }
        }
        if(isvalid) {
            return isvalid;

        }

        cont = true;
        count =0;
        for (int i = 1; i <= max; i++) {
            if (x-i<0 || y+i>this.col-1) {
                break;
            }
            if(board[x-i][y+i]== player*-1)
            {
                count++;
            }
            else if(board[x-i][y+i] == player)
            {
                cont = false;
            }
            else
            {
                break;
            }
            if (cont==false)
            {
                if (count>0)
                {
                    isvalid = true;
                }
                break;
            }
        }
        if(isvalid) {
            return isvalid;

        }

        cont = true;
        count =0;
        for (int i = 1; i <= max; i++) {
            if (x+i>this.row-1 || y+i>this.col-1) {
                break;
            }
            if(board[x+i][y+i]== player*-1)
            {
                count++;
            }
            else if(board[x+i][y+i] == player)
            {
                cont = false;
            }
            else
            {
                break;
            }
            if (cont==false)
            {
                if (count>0)
                {
                    isvalid = true;
                }
                break;
            }
        }

        return  isvalid;
    }

    public String DrawBoard(int[][] board, List<int[]> successors) {
        //int[][] board_copy = new int[this.row][this.col];

        var board_copy = CopyBoard(board);
        if (successors != null)
        {
            for (var s:
                 successors) {
                board_copy[s[0]][s[1]] = 2;
            }
        }
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase().toCharArray();

        String graph = "  ";
        for (int i = 0; i < this.col; i++) {
            graph += alphabet[i] + " ";
        }
        graph +="\n";
        for (int i = 0; i < this.row; i++) {
            graph += (i+1) + " ";
            for (int j = 0; j < this.col; j++) {
                    var sym = " ";
                    if(board_copy[i][j]==-1)
                    {
                        sym = "o";
                    }
                    else if (board_copy[i][j]==1)
                    {
                        sym = "x";
                    }
                    else if (board_copy[i][j]==2)
                    {
                        sym = "s";
                    }
                    graph +=  sym+ " ";
            }
            graph += (i+1) + " \n";
        }
        graph +="  ";
        for (int i = 0; i < this.col; i++) {
            graph += alphabet[i] + " ";
        }
        graph +="\n";

        return  graph;
    }

    public String GetWinner(int[][] board)
    {
        int countd = 0;
        int countl = 0;
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {

                if(board[i][j] == -1)
                {
                    countl+=1;

                }
                else if(board[i][j] == 1)
                {
                    countd+=1;
                }
            }
        }

        if(countd>countl)
        {
            return  "DARK";
        }
        else  if(countd<countl)
        {
            return  "LIGHT";
        }
        else
        {
            return  "TIE";
        }

    }

    public int GetScore(int[][] board ,int player)
    {
        int total = 0;
        for (int i = 0; i < this.row; i++)
            for (int j = 0; j < this.col; j++)
            {
                if (board[i][j] == player)
                    total++;
            }
        return total;
    }

    public int Heuristic(int[][] board,int player)
    {
        int playerScore = GetScore(board, player);
        int otherScore = GetScore(board, player*-1);
        return (playerScore- otherScore);
    }









}
