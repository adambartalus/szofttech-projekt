package scripts;
import javafx.util

public class Pathfinder (Pair start, Pair end, Pair current, int [][] map){
    
    private Pair<Integer, Integer> startPos = start;
    private Pair<Integer, Integer> endPost = end;
    private Pair<Integer, Integer> currentPos = current;
    int rows = map.length;
    int cols = map[0].length;
    Pair<Integer, String> [][] board = new Pair[rows][cols];
    
    public Pathfinder(){
        //I fill up the board with (1000,"x") to mark those positions which are occupied by an item blocking pathing
        for(int i=0;i<rows;i++){
            for(int n=0;n<cols;i++){
                if(map[i][n]==1){
                    board[i][n].setAt0(1000);
                    board[i][n].setAt1("x"); 
                }
            }
        }
    }    

    //Calcs the 4 possible movement options weight
    public void calcCost (){
        int currentX=currentPos.getKey();
        int currentY=currentPos.getValue();
        //TBD: Check if its in bounds to go any way
        if(board[currentX-1][currentY].getKey()!=1000){
            board[currentX-1][currentY].setAt0(abs(startPos.getKey()-(currentX-1))+abs(startPos.getValue()-(currentY)));
            board[currentX-1][currentY].setAt1("r");
        }//Check left
        if(board[currentX][currentY+1].getKey()!=1000){
            board[currentX][currentY+1].setAt0(abs(startPos.getKey()-(currentX))+abs(startPos.getValue()-(currentY+1)));
            board[currentX][currentY+1].setAt1("b");
        }//Check up
        if(board[currentX+1][currentY].getKey()!=1000){
            board[currentX+1][currentY].setAt0(abs(startPos.getKey()-(currentX+1))+abs(startPos.getValue()-(currentY)));
            board[currentX+1][currentY].setAt1("l");
        }//Check right
        if(board[currentX][currentY-1].getKey()!=1000){
            board[currentX][currentY-1].setAt0(abs(startPos.getKey()-(currentX))+abs(startPos.getValue()-(currentY-1)));
            board[currentX][currentY-1].setAt1("b");
        }//Check up
    }

    
}
