package udel.edu.wangyc.minesweeper;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import udel.edu.wangyc.minesweeper.adapter.GameAdapter;
import udel.edu.wangyc.minesweeper.Cell;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends AppCompatActivity {
    private Timer timer=new Timer();
    private Button startGame;
    private Handler handler;
    private int gameTime=0;
    private TextView showTime;
    private final static int MESSAGE_UPDATE_TIME=1;
    private GameAdapter adapter;
    private GridView gv;
    private int level=9;
    private boolean isGaming=false;
    private int lifes=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent=getIntent();
        level=Integer.parseInt(intent.getStringExtra("level"));
        gv=(GridView)findViewById(R.id.gv);
        adapter=new GameAdapter(level,gv,this);
        gv.setNumColumns(level);
        gv.setAdapter(adapter);
        inint();
        addListener();
        startGame();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       if (id == R.id.action_settings) {
            return true;
        }

       return super.onOptionsItemSelected(item);
    }

    //initialize the game
    public void inint(){
        lifes=2;
        startGame=(Button)findViewById(R.id.startGame);
        showTime = (TextView) findViewById(R.id.timeView);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MESSAGE_UPDATE_TIME) {
                    showTime.setText("Time Used：" +gameTime/60+"min"+ gameTime%60 + "sec");
                }
            }
        };
    }

    //statrt the game
    public void startGame(){
        adapter=new GameAdapter(level,gv,this);
        gv.setNumColumns(level);
        gv.setAdapter(adapter);
        isGaming=true;
        gameTime=0;
        timer.cancel();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameTime++;
                handler.sendEmptyMessage(MESSAGE_UPDATE_TIME);
            }
        }, 0, 1000);
    }

    /**
     * end the game
     * */
    public void stopGame(){
        isGaming=false;
        timer.cancel();
    }

    public void addListener(){
        startGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGame();
                startGame.setText("Restart");
                lifes=2;
            }
        });
        gv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if (!isGaming) {
                    return true;
                }
                Cell grid = adapter.getEntity(position);
                if (grid.IsSingleClicked()) {
                    return true;
                }
                grid.setIsLongClicked(!grid.IsLongClicked());
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!isGaming){
                    return;
                }
//                get the cell from the adapter
                Cell grid=adapter.getItem(position);
//                if the cell is flaged then click unsuccessful
                if(grid.IsLongClicked()){
                    return;
                }
                if(!grid.IsSingleClicked()){
                    if(grid.IsBomb()){
                        lifes--;
                        if(lifes<1) {
                            grid.setIsSingleClicked(true);
                            stopGame();
                            adapter.showAllBooms();
                            adapter.checkLongClick();
                            adapter.notifyDataSetChanged();
                            Toast.makeText(getApplicationContext(), "You lose！", Toast.LENGTH_SHORT).show();
                        }
                        else if(lifes>0){
                            Toast.makeText(getApplicationContext(), "Exploded!", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "You have only 1 chance！", Toast.LENGTH_SHORT).show();
                            grid.setIsLongClicked(true);
                            adapter.notifyDataSetChanged();
                        }
                        return;
                    }
                    if(grid.getBombAroundNum()==0&&!grid.IsBomb()){
                         //if ther is no bomb around the cell and the cell is not a bomb
                         //show nearby zone
                        adapter.showNotBoomsArea(position);
                    }
                    grid.setIsSingleClicked(true);
                    adapter.notifyDataSetChanged();
                    if(adapter.isWin()){
                        stopGame();
                        adapter.showAllBooms();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(),"Congratulation!"+showTime.getText(),Toast.LENGTH_LONG).show();
                        return;
                    }

                }
            }
        });
    }
}
