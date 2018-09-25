package udel.edu.wangyc.minesweeper.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import udel.edu.wangyc.minesweeper.Cell;
import udel.edu.wangyc.minesweeper.MineSweepGame;
import udel.edu.wangyc.minesweeper.R;
import udel.edu.wangyc.minesweeper.framework.Game;;
/**
 * Created by WanqiLiang on 5/7/2018.
 */
public class GameAdapter extends BaseAdapter{

    private int level;
    private int row;
    private int col;
    private GridView gv;
    private MineSweepGame gameGround;
    private Context context;
    /**
     * construcotr
     * */
    public GameAdapter(int level,GridView gv,Context context){
        this.level=9;//level;
        this.row=this.level;
        this.col=this.level;
        this.gv=gv;
        this.context=context;
        this.gameGround=new MineSweepGame();
    }
    @Override
    public int getCount() {
        return row*col;
    }
    /**
     * get the cell
     * */
    @Override
    public Cell getItem(int position) {
//       get the item in the list
        return gameGround.getCellAt(position);
    }
    /**
     * get the position of the cell
     * */
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        set the view
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.other,null);
        }
        ((ImageView)convertView).setImageResource(getRes(getItem(position)));
        AbsListView.LayoutParams params=new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,gv.getWidth()/level);
        convertView.setLayoutParams(params);
        return convertView;
    }
    /**
     * set the icon of the cell in different condition
     * */
    public int getRes(Cell grid){
//        set the ID of the background image to 0
        int resID=0;
//        if the cell is flaged and flaged in the right position
        if(grid.IsLongClicked()&&grid.IsBomb()){
            resID=R.drawable.flag;
        }
//        if the cell is flaged but in the wrong position
        else if(grid.IsLongClicked()&&!grid.IsBomb()){
            resID=R.drawable.flag;
        }
//        if the cell is clicked and not showed
        else if(!grid.IsSingleClicked()&&!grid.isAutoShow()){
            resID=R.drawable.unrevealed;
        }
//        if the cell is mine and not showed automatically
        else if(grid.IsBomb()&&grid.IsSingleClicked()){
            resID=R.drawable.clickedbomb;
        }
//        if the cell is mine and showd automatically
        else if(grid.IsBomb()&&grid.isAutoShow()){
            resID=R.drawable.i12;
        }
        else if(!grid.IsBomb() &&grid.IsLongClicked()){
            resID = R.drawable.i14;
        }

//        if it is a blank cell
        else if(grid.getBombAroundNum()==0){
            resID=R.drawable.i09;
        }
//        if there is(are) bomb(s) around the cell and the number is from 1 -8
        else if(grid.getBombAroundNum()!=0){
              // get the image from the darwble folder automatically with right name
            resID=context.getResources().getIdentifier("i0"+grid.getBombAroundNum(),"drawable",context.getPackageName());
        }
        return resID;
    }
    /**
     * check is the game win
     * */
    public boolean isWin(){
        return gameGround.isWin();
    }
    /**
     * show the bomb not clicked at the end of the game*/
    public void showAllBooms(){
        gameGround.showAllBombs();
        notifyDataSetChanged();
    }
    /**
     * show the cell that is not bomb
     * */
    public void showNotBoomsArea(int position){
        gameGround.showNotBombArea(position);
        notifyDataSetChanged();
    }
    /**
     * get the cell from the gameground
     * */
    public Cell getEntity(int position){
        return gameGround.getCellAt(position);
    }
    /**
     *check the condition of the longclick(flag)
     * */
    public void checkLongClick(){
        gameGround.checkLongClick();
        notifyDataSetChanged();
    }
}
