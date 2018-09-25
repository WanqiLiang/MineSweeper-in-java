package udel.edu.wangyc.minesweeper;

import java.util.Objects;

import udel.edu.wangyc.minesweeper.framework.Action;

/**
 * Created by wenkiliang on 4/24/2018.
 */

public class MineSweepAction implements Action<MineSweepGame> {
    public static void main(String args[]){
        System.out.println("Test");
    }

    private int row;
    private int column;
    private String click = " ";
    private String singleclick = "single";
    private String doubleclick = "double";


    //click at the cell in the certain location
    public MineSweepAction(MineSweepGame game,int row, int column,String click) {
        this.row = row - 1;
        this.column = column - 1;
        this.click = click;

        if ( !isValid(game) ) {
            System.out.print("Action invalid if ");
            System.out.println(toString() + '\n');
            return;
        }

        setClicked(game, click);


        System.out.println(toString() + '\n');

        //if there is nothing in the cell(there is no bomb and there is no bomb around the cell)
        //flip the cell and flip the cell around it
        if ( game.getBoard()[this.row][this.column].getBombAroundNum() == 0 && !game.getBoard()[this.row][this.column].IsBomb() && click.equals(singleclick) ) {// 如果点击的是空格
            for (int i = this.row - 1; i <= this.row + 1; i++) {//对于它的上下两行
                for (int j = this.column - 1; j <= this.column + 1; j++) {//对于它的左右两行
                    if ( i >= 0 && j >= 0 && i < game.getRow() && j < game.getCol() ) {//如果上下、左右都不是边界
                        if ( !game.getBoard()[i][j].IsSingleClicked() && !game.getBoard()[i][j].IsLongClicked() ) {//如果周围的每个格子没被点过
                            MineSweepAction action = new MineSweepAction(game, i + 1, j + 1, "single");//就是一个新的action
                            game.perform(action);
                        }
                    }
                }
            }
        }


        if ( game.getBoard()[this.row][this.column].IsBomb() && click.equals(singleclick) ) {
            int tscore = game.getScore();
            int tBombNum = game.getBombNum() / 2;
            int tchance = game.getChance();
            if ( tscore >= tBombNum && tchance == 2 ) {
                game.getBoard()[this.row][this.column].setIsLongClicked(true);
                game.setChance(1);
                game.getBoard()[this.row][this.column].setIsSingleClicked(false);
                System.out.println(game.getStatus(game.getBoard()[this.row][this.column]));
            } else if ( tscore >= tBombNum && tchance == 1 ) {
                game.setChance(0);
                System.out.println(game.getStatus(game.getBoard()[this.row][this.column]));
            } else if ( tscore < tBombNum ) {
                System.out.println(game.getStatus(game.getBoard()[this.row][this.column]));
            } else {
                System.out.println(game.getStatus(game.getBoard()[this.row][this.column]));
            }
        } else {
            System.out.println(game.getStatus(game.getBoard()[this.row][this.column]));
        }
    }


    //set the condition of the cell that clicked
    public void setClicked(MineSweepGame game,String click) {
        if ( click.equals(singleclick)) {
            game.getBoard()[row][column].setIsSingleClicked(true);
        }
        if (click.equals(doubleclick)) {
            if(!game.getBoard()[row][column].IsLongClicked()) {
                game.getBoard()[row][column].setIsLongClicked(true);
            }
            else{
                game.getBoard()[row][column].setIsLongClicked(false);
            }
        }

    }


    //update the condition of the clicked cell
    @Override
    public void update(MineSweepGame mineSweepGame) {
        setClicked(mineSweepGame,click);
    }

    //check if the action is valid or not
    //the actio valid if the cell has not been clicked ever
    @Override
    public boolean isValid(MineSweepGame mineSweepGame){
        return !mineSweepGame.getBoard()[row][column].IsSingleClicked();
    }

    //print a string to show what movement the player did
    public String toString() {
        String words = " ";
        if ( click.equals(doubleclick) ){
            words = "Double Click at (" + (row+1) + "," + (column+1) + ").";
        }
    else if(click.equals(singleclick)){
            words =  "Click at ("+(row+1)+","+(column+1)+").";
        }
        return words;
    }
}
