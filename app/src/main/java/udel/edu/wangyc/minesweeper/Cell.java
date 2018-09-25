package udel.edu.wangyc.minesweeper;

import android.view.View;
import android.content.Context;

/**
 * Created by wenkiliang on 4/24/2018.
 */

public class Cell {//extends View {
    private int posx,posy;  //position
    private int BombAroundNum; //bomb around this cell
    private char BombMark = ' '; //Mark as flag
    private boolean isBomb; // is this cell a bomb
    private boolean isSide; // is a side cell
    private boolean isAutoShow; // if it should be show automatically
    private boolean isSingleClicked; // did we clicked it(is it open?)
    private boolean isFlagWrong; // wrong flag, not bomb
    boolean isLongClicked; //did we marked
    private int position;

    public Cell(){}


    public void setCell(int x, int y){
        this.posx = x;
        this.posy = y;
        BombAroundNum = 0;
        isBomb = false;
        isSingleClicked = false;
        isLongClicked = false;
    }

    public boolean IsSingleClicked() {
        return isSingleClicked;
    }


    public void setIsFlagWrong(boolean isFlagWrong) {
        this.isFlagWrong = isFlagWrong;
    }

    public void setIsSingleClicked(boolean clicked) {
        isSingleClicked = clicked;
    }

    public boolean IsLongClicked() {
        return isLongClicked;
    }

    public void setIsLongClicked(boolean rightClicked) {
        isLongClicked = rightClicked;
    }

    public boolean IsBomb() {
        return isBomb;
    }

    public boolean isSide() {
        return isSide;
    }

    public boolean isAutoShow() {
        return isAutoShow;
    }

    public void setIsAutoShow(boolean isAutoShow) {
        this.isAutoShow = isAutoShow;
    }


    public void setIsBomb(boolean isBomb) {
        this.isBomb = isBomb;
    }

    public int getBombAroundNum() {
        return BombAroundNum;
    }

    public void setBombAroundNum(int bombAroundNum) {
        BombAroundNum = bombAroundNum;
    }


    public char getBombMark(){
        return BombMark;
    }

}
