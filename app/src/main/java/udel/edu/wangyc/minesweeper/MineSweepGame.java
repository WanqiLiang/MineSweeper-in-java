package udel.edu.wangyc.minesweeper;

import android.content.Context;

import java.util.Random;

import udel.edu.wangyc.minesweeper.framework.Game;

/**
 * Created by wenkiliang on 4/24/2018.
 */

public class MineSweepGame extends Game {
    public static final int row = 9;
    public static final int col = 9;
    private int bombNum = 9;
    private Cell[][] board = new Cell[row][col];
    private int chance = 2;



    //start a new game
    public MineSweepGame() {

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                board[i][j] = new Cell();//context);//,i,j);
                board[i][j].setBombAroundNum(0);
                board[i][j].setIsBomb(false);
                board[i][j].setIsSingleClicked(false);
                board[i][j].setIsLongClicked(false);
            }
        }


        Random rand = new Random();
        for(int i=0;i<bombNum;) {
            int x = rand.nextInt(row);
            int y = rand.nextInt(col);
            if(! board[x][y].IsBomb()) {
                board[x][y].setIsBomb(true);
                i++;
            }
        }
        calculateAroundBomb();
        setStart();
    }


    //calculate the bomb around the cell
    public void calculateAroundBomb(){
        for(int i=0;i<row;i++) {
            for (int j = 0; j < col; j++) {
                int count = 0;
                if ( !board[i][j].IsBomb() ) {
                    for (int x = i - 1; x <= i + 1; x++) {
                        for (int y = j - 1; y <= j + 1; y++) {
                            if ( x >= 0 && y >= 0 && x < row && y < col && board[x][y].IsBomb() ) {
                                count++;
                            }
                        }
                    }
                }
                board[i][j].setBombAroundNum(count);
            }
        }
    }

    //set the start of the game:
    //there already has a empty cell that has been fliped
    //and it will flip the non-bomb cell around it
    public void setStart(){
        Random rand = new Random();
        int x = rand.nextInt(row);;
        int y = rand.nextInt(col);
        while(board[x][y].getBombAroundNum() != 0 || board[x][y].IsBomb()){
            x = rand.nextInt(row);
            y = rand.nextInt(col);
        }
        board[x][y].setIsSingleClicked(true);
        recursionCell(x,y);
    }


    public void recursionCell(int x,int y){
        board[x][y].setIsSingleClicked(true);
        if(board[x][y].getBombAroundNum() == 0 && !board[x][y].IsBomb()) {
            for (int i = x - 1; i <= x + 1; i++) {//对于它的上下两行
                for (int j = y - 1; j <= y + 1; j++) {//对于它的左右两行
                    if ( i >= 0 && j >= 0 && i < getRow() && j < getCol() ) {//如果上下、左右都不是边界
                        if ( !getBoard()[i][j].IsSingleClicked() && !getBoard()[i][j].IsLongClicked() ) {//如果周围的每个格子没被点过
                            recursionCell(i,j);
                        }
                    }
                }
            }
        }
    }

    //get the number of cell has been marked as score
    public int getScore(){
        int score = row * col;
        for(int i=0; i<row;i++){
            for(int j=0;j<col;j++){
                if(board[i][j].IsSingleClicked()&&!board[i][j].IsBomb()){
                    score--;
                }
            }
        }
        return score;
    }

    public Cell getCellAt(int position) {
        int x = position / row;
        int y = position % col;

        return board[x][y];
    }

    public Cell getCellAt( int x , int y ){
        return board[x][y];
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public int getBombNum() {
        return bombNum;
    }

    public void setBombNum(int anotherbombNum) {
        this.bombNum = anotherbombNum;
    }

    public Cell[][] getBoard(){
        return board;
    }

    //to check did the player won
    public boolean isWin(){
        if(getScore()== bombNum){
            return true;
        }
        return false;
    }

    //check if the cell that clicked is bomb or not
    public boolean isBombed(Cell click){

        if(click.IsSingleClicked() &&click.IsBomb()){
            return true;
        }
        return false;
    }


    public int getChance(){
        return chance;
    }

    public void setChance(int chance){
        this.chance = chance;
    }

    //print the words to show the game status
    public String getStatus(Cell click){
        String finals = "";
        if(isWin()){
            finals = "You Win!!!!";
        }
        else if(getScore() >= bombNum/2 &&chance == 2 && isBombed(click)){
            finals = "Game Continue...";
        }
        else if(getScore() >= bombNum/2 &&chance == 0 &&isBombed(click)){
            finals = "You lose :(";
        }
        else if(getScore() < bombNum/2 && isBombed(click)){
            finals = "You lose :(";
        }
        else{
            finals = "Game Continue...";
        }
        return finals;
    }


    //if the score is more than half of the number of the total bomb and there still is one more chance
    //game keep continue
    //if the player win, game end
    //if the chance has been used, game end
    //if the score is less than half of the total bomb, game end
    //else, game continue
    @Override
    public boolean isEnd() {
        if(chance == 0){
            return true;
        }
        else if(isWin()){
            return true;
        }
        return false;
    }


    //Print the game
    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        for (int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                if ( board[i][j].IsSingleClicked() /*|| board[i][j].IsLongClicked() */) {
                    if(board[i][j].IsBomb()){
                        buffer.append('M');
                    }
                    else if(board[i][j].getBombAroundNum() == 0){
                        buffer.append('E');
                    }
                    else if(board[i][j].IsLongClicked()){
                        buffer.append(board[i][j].getBombMark());
                    }
                    else{
                        buffer.append(board[i][j].getBombAroundNum());
                    }
                }
                else if(board[i][j].IsLongClicked()){
                    buffer.append(board[i][j].getBombMark());
                }
                else{
                    buffer.append('C');
                }
            }
            buffer.append("\n");
        }
        return buffer.toString();
    }


    public void longClick( int x,int y){
        boolean isLongClicked = getCellAt(x,y).IsLongClicked();
        getCellAt(x,y).setIsLongClicked(!isLongClicked);
    }
    /**
     * check if the flag set right
     * */
    public void checkLongClick() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j].IsLongClicked() && board[i][j].IsBomb()) {
                    board[i][j].setIsFlagWrong(false);
                } else if (board[i][j].IsLongClicked() && !board[i][j].IsBomb()) {
                    board[i][j].setIsFlagWrong(true);
                }
            }
        }
    }
    public void showAllBombs( ){
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
//                对象是雷且未被点击
                if(board[i][j].IsBomb()){
//                    展示该格子
                    board[i][j].setIsAutoShow(true);
                }
            }
        }
    }

    public void showNotBombArea(int position){
        if(position<0||(position>=(row*col))){
            return;
        }
        int x= position/col;
        int y= position%row;
        if(board[x][y].isSide()){
            return;
        }
        if(this.board[x][y].getBombAroundNum()!=0||board[x][y].IsSingleClicked()){
            board[x][y].setIsSingleClicked(true);
            return;
        }
        board[x][y].setIsSingleClicked(true);
        if(board[x][y].getBombAroundNum() == 0 && !board[x][y].IsBomb()){
            for(int i = x-1; i<=x+1;i++){
                for(int j =y-1; j<=y+1;j++){
                    if(i>=0 && j>=0 && i<getRow() && j<getCol()){
                        if(!board[i][j].IsSingleClicked() && !board[i][j].isLongClicked){
                            showNotBombArea(i*row+j);
                        }
                    }
                }
            }
        }
    }
}
